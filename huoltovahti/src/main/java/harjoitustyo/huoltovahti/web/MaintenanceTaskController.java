package harjoitustyo.huoltovahti.web;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import harjoitustyo.huoltovahti.domain.AppUser;
import harjoitustyo.huoltovahti.domain.AppUserRepository;
import harjoitustyo.huoltovahti.domain.CategoryRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTarget;
import harjoitustyo.huoltovahti.domain.MaintenanceTargetRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTask;
import harjoitustyo.huoltovahti.domain.MaintenanceTaskRepository;
import jakarta.validation.Valid;

@Controller 
public class MaintenanceTaskController {
    
    // Logger for logging information and errors
    private static final Logger log = LoggerFactory.getLogger(MaintenanceTaskController.class);

    // Constructor injection for repositories
    public MaintenanceTaskController(MaintenanceTaskRepository maintenanceTaskRepository, CategoryRepository categoryRepository, 
            MaintenanceTargetRepository maintenanceTargetRepository, AppUserRepository appUserRepository) {
        this.maintenanceTaskRepository = maintenanceTaskRepository;
        this.categoryRepository = categoryRepository;
        this.maintenanceTargetRepository = maintenanceTargetRepository;
        this.appUserRepository = appUserRepository;
    }

    private final MaintenanceTaskRepository maintenanceTaskRepository;
    private final CategoryRepository categoryRepository;
    private final MaintenanceTargetRepository maintenanceTargetRepository;
    private final AppUserRepository appUserRepository;
    
    /////////////////////////   INDEX PAGE  /////////////////////////
    
    @GetMapping({"/", "/index"})
    public String showIndex(Model model) {
        List<MaintenanceTask> tasks;
        tasks = maintenanceTaskRepository.findAllByIsPlannedFalseOrderByTaskYearDescIdDesc();
        model.addAttribute("tasks", tasks);
        model.addAttribute("plannedTasks", maintenanceTaskRepository.findByIsPlannedTrueOrderByTaskYearAsc());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("targets", maintenanceTargetRepository.findAll());
        return "index";
    }   

    /////////////////////////   MAINTENANCE TASK HISTORY PAGE   /////////////////////////
    
    @GetMapping({"/maintenanceTaskHistory"})
    public String showMaintenanceTaskHistory(@RequestParam(required = false) Long categoryId, // for filtering
                            @RequestParam(required = false) Long targetId, // for filtering
                            Model model) {
        // Filter maintenance tasks:
        List<MaintenanceTask> tasks;
        // Category + target selected
        if (categoryId != null && targetId != null) {
            tasks = maintenanceTaskRepository.findByIsPlannedFalseAndCategoryIdAndTargetsIdOrderByTaskYearDescIdDesc(categoryId, targetId);
            log.info("maintenanceTaskHistory called with category id: " + categoryId + " and target id: " + targetId);
        // Category selected
        } else if (categoryId != null) {
            tasks = maintenanceTaskRepository.findByIsPlannedFalseAndCategoryIdOrderByTaskYearDescIdDesc(categoryId);
            log.info("maintenanceTaskHistory called with category id: " + categoryId);
        // Target selected
        } else if (targetId != null) {
            tasks = maintenanceTaskRepository.findByIsPlannedFalseAndTargetsIdOrderByTaskYearDescIdDesc(targetId);
            log.info("maintenanceTaskHistory called with target id: " + targetId);
        // Nothing selected
        } else {
            tasks = maintenanceTaskRepository.findAllByIsPlannedFalseOrderByTaskYearDescIdDesc();
            log.info("maintenanceTaskHistory called with all categories and targets");
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("targets", maintenanceTargetRepository.findAll());
        // Save selected filter values 
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedTargetId", targetId);
        return "maintenanceTaskHistory";
    }   

    /////////////////////////   MY MAINTENANCE TASKS PAGE   /////////////////////////  
    
    @GetMapping("/myMaintenanceTasks")
    public String getMyTasks(Principal principal,
                            @RequestParam(required = false) Long categoryId, // for filtering
                            Model model) {
        AppUser user = appUserRepository.findByUsername(principal.getName());
        MaintenanceTarget userHouse = user.getUserHouse(); // house connected to user
        List<MaintenanceTask> userTasks;                      
        // Filter maintenance tasks:
        if (categoryId != null) {   // Category selected
            userTasks = maintenanceTaskRepository.findByTargetsContainsAndIsPlannedFalseAndCategoryIdOrderByTaskYearDescIdDesc(userHouse, categoryId);
            log.info("maintenanceTaskHistory called with category id: " + categoryId);
        } else { // Nothing selected
            userTasks = maintenanceTaskRepository.findByTargetsContainsAndIsPlannedFalseOrderByTaskYearDescIdDesc(userHouse);
            log.info("maintenanceTaskHistory called with all categories and targets");
        }
        model.addAttribute("selectedCategoryId", categoryId); // Save selected filter value
        model.addAttribute("tasks", userTasks);
        model.addAttribute("categories", categoryRepository.findAll());
        return "myMaintenanceTasks";
    }

    /////////////////////////  ADD NEW MAINTENANCE TASK   /////////////////////////  
    
    @GetMapping("/addMaintenanceTask")
    public String addMaintenanceTask(@RequestParam(required = false, defaultValue = "false") boolean planned, // false if "+ Huoltotyö" or true if "+ Suunniteltu huoltotyö"- button has been pressed in nav
                                    Model model) {
        MaintenanceTask task = new MaintenanceTask();
        task.setIsPlanned(planned);  // Take parameter and set it's value for isPlanned 
        model.addAttribute("task", task);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("targets", maintenanceTargetRepository.findAll());
        return "addMaintenanceTask";
    }

    /////////////////////////   SAVE AND VALIDATE NEW MAINTENANCE TASK   /////////////////////////
    
    @PostMapping("/saveAndValidate")
    public String saveAndValidate(@Valid @ModelAttribute("task") MaintenanceTask task, 
                                BindingResult bindingResult, 
                                Principal principal,
                                Model model) {
        // if saving new task set current user to createdBy
        if (task.getId() == null) {
            AppUser currentUser = appUserRepository.findByUsername(principal.getName());
            task.setCreatedBy(currentUser);
        } else {
            // if saving existing task get original creator
            MaintenanceTask existingTask = maintenanceTaskRepository.findById(task.getId()).orElse(null);
            if (existingTask != null) {
                task.setCreatedBy(existingTask.getCreatedBy());
            }
        }
        // Check task performer information only if task is not planned
        if (!Boolean.TRUE.equals(task.getIsPlanned())) {
            // Validation for task performer
            boolean selfPerformed = Boolean.TRUE.equals(task.getIsSelfPerformed());
            boolean hasContractor = task.getContractor() != null && !task.getContractor().trim().isEmpty();
            boolean hasBusinessId = task.getContractorBusinessId() != null && !task.getContractorBusinessId().trim().isEmpty();
            // Error if both options are selected 
            if (selfPerformed && (hasContractor || hasBusinessId)) {
                bindingResult.rejectValue("contractor", "error.contractor", "Valitse joko 'Tehty itse' tai ilmoita urakoitsija, ei molempia.");
            }
            // Error if nothing is selected
            if (!selfPerformed && !hasContractor && !hasBusinessId) {
                bindingResult.rejectValue("contractor", "error.contractor", "Ilmoita urakoitsija tai merkitse työ itse tehdyksi.");
            }
            // Error if business id but no contractor
            if ((!selfPerformed && hasBusinessId && !hasContractor) || (selfPerformed && hasBusinessId && !hasContractor)) {
                bindingResult.rejectValue("contractor", "error.contractor", "Ilmoita myös urakoitsijan nimi, jos annat Y-tunnuksen.");
            }
        }
        if (bindingResult.hasErrors()) {
            log.error("Validation error happened, task: " + task);
            model.addAttribute("task", task);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("targets", maintenanceTargetRepository.findAll());
            return "/addMaintenanceTask";
        }
        log.info("Validation passed, saving task: " + task);
        maintenanceTaskRepository.save(task);
        return "redirect:/index";
    }

    /////////////////////////  EDIT MAINTENANCE TASK BY ID  /////////////////////////  
    
    @PreAuthorize("hasRole('ADMIN') or @maintenanceTaskRepository.findById(#id).orElse(null)?.createdBy?.username == authentication.name") // Allow access to edit only if user has created the task or user is Admin 
    @GetMapping("/edit/{id}")
    public String editMaintenanceTask(@PathVariable Long id,
                                    @RequestParam(required = false, defaultValue = "false") boolean markCompleted, // "Merkitse valmiiksi" -button in editMaintenanceTask sets markCompleted to true
                                    Model model) {
        log.info("editMaintenanceTask() called with ID: {}", id);
        MaintenanceTask task = maintenanceTaskRepository.findById(id).orElseThrow();
        // if markCompleted is true set isPlanned to false to open normal edit form to complete necessary information
        if (markCompleted) {
            task.setIsPlanned(false);
        }
        model.addAttribute("taskToEdit", task);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("targets", maintenanceTargetRepository.findAll());
        return "editMaintenanceTask";
    }

    /////////////////////////   SAVE AND VALIDATE EDITED MAINTENANCE TASK   /////////////////////////  
    
    @PostMapping("/saveEditedTask")
    public String saveEditedTask(@Valid @ModelAttribute("taskToEdit") MaintenanceTask taskToEdit, 
                                BindingResult bindingResult, 
                                Model model) {
        // Check task performer information only if task is not planned
        if (!Boolean.TRUE.equals(taskToEdit.getIsPlanned())) {
            // Validation for task performer
            boolean selfPerformed = Boolean.TRUE.equals(taskToEdit.getIsSelfPerformed());
            boolean hasContractor = taskToEdit.getContractor() != null && !taskToEdit.getContractor().trim().isEmpty();
            boolean hasBusinessId = taskToEdit.getContractorBusinessId() != null && !taskToEdit.getContractorBusinessId().trim().isEmpty();
            // Error if both options are selected 
            if (selfPerformed && (hasContractor || hasBusinessId)) {
                bindingResult.rejectValue("contractor", "error.contractor", "Valitse joko 'Tehty itse' tai ilmoita urakoitsija, ei molempia.");
            }
            // Error if nothing is selected
            if (!selfPerformed && !hasContractor && !hasBusinessId) {
                bindingResult.rejectValue("contractor", "error.contractor", "Ilmoita urakoitsija tai merkitse työ itse tehdyksi.");
            }
            // Error if business id but no contractor
            if ((!selfPerformed && hasBusinessId && !hasContractor) || (selfPerformed && hasBusinessId && !hasContractor)) {
                bindingResult.rejectValue("contractor", "error.contractor", "Ilmoita myös urakoitsijan nimi, jos annat Y-tunnuksen.");
            }
        }
        if (bindingResult.hasErrors()) {
            log.error("Validation error happened, edited task: " + taskToEdit);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("targets", maintenanceTargetRepository.findAll());
            return "editMaintenanceTask";
        }
        log.info("Validation passed, saving edited task: " + taskToEdit);
        maintenanceTaskRepository.save(taskToEdit);
        return "redirect:/index";
    }

    /////////////////////////   DELETE MAINTENANCE TASK BY ID   /////////////////////////  
    
    @PreAuthorize("hasRole('ADMIN') or @maintenanceTaskRepository.findById(#id).orElse(null)?.createdBy?.username == authentication.name") // Allow access to delete only if user has created the task or user is Admin 
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        log.info("deleteTask() called with ID: {}", id);
        maintenanceTaskRepository.deleteById(id);
        return "redirect:/index";
    }

}
