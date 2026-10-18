package harjoitustyo.huoltovahti.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import harjoitustyo.huoltovahti.domain.CategoryRepository;
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
            MaintenanceTargetRepository maintenanceTargetRepository) {
        this.maintenanceTaskRepository = maintenanceTaskRepository;
        this.categoryRepository = categoryRepository;
        this.maintenanceTargetRepository = maintenanceTargetRepository;
    }

    private final MaintenanceTaskRepository maintenanceTaskRepository;
    private final CategoryRepository categoryRepository;
    private final MaintenanceTargetRepository maintenanceTargetRepository;
    
    // Index page
    @GetMapping({"/", "/index"})
    public String showIndex(@RequestParam(required = false) Long categoryId,
                            @RequestParam(required = false) Long targetId,
                            Model model) {
        List<MaintenanceTask> tasks;
        tasks = maintenanceTaskRepository.findAllByIsPlannedFalseOrderByTaskYearDescIdDesc();
        model.addAttribute("tasks", tasks);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("targets", maintenanceTargetRepository.findAll());
        return "index";
    }   

    // Maintenance task history page
    @GetMapping({"/maintenanceTaskHistory"})
    public String showMaintenanceTaskHistory(@RequestParam(required = false) Long categoryId,
                            @RequestParam(required = false) Long targetId,
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

    // Add new maintenance task
    @GetMapping("/addMaintenanceTask")
    public String addMaintenanceTask(@RequestParam(required = false, defaultValue = "false") boolean planned, Model model) {
        MaintenanceTask task = new MaintenanceTask();
        // Set value for isPlanned
        task.setIsPlanned(planned); 
        
        model.addAttribute("task", task);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("targets", maintenanceTargetRepository.findAll());
        return "addMaintenanceTask";
    }

    // Save and validate new maintenance task
    @PostMapping("/saveAndValidate")
    public String saveAndValidate(@Valid @ModelAttribute("task") MaintenanceTask task, BindingResult bindingResult, Model model) {
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
        if (bindingResult.hasErrors()) {
            log.error("Validation error happened, task: " + task);
            model.addAttribute("task", task);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("targets", maintenanceTargetRepository.findAll());
            return "/addMaintenanceTask";
        }
        log.info("Validation passed, saving task: " + task);
        maintenanceTaskRepository.save(task);
        return "redirect:/maintenanceTaskHistory";
    }

    // Edit maintenance task by ID
    @GetMapping("/edit/{id}")
    public String editMaintenanceTask(@PathVariable Long id, Model model) {
        log.info("editMaintenanceTask() called with ID: {}", id);
        MaintenanceTask task = maintenanceTaskRepository.findById(id).get();
        model.addAttribute("taskToEdit", task);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("targets", maintenanceTargetRepository.findAll());
        return "/editMaintenanceTask";
    }

    // Save and validate edited maintenance task
    @PostMapping("/saveEditedTask")
    public String saveEditedTask(@Valid @ModelAttribute("taskToEdit") MaintenanceTask taskToEdit, BindingResult bindingResult, Model model) {
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

    // Delete maintenance task by ID
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        log.info("deleteTask() called with ID: {}", id);
        maintenanceTaskRepository.deleteById(id);
        return "redirect:/index";
    }

}
