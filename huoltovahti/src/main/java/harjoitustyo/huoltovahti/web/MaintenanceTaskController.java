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
        
        // Filter maintenance tasks
        List<MaintenanceTask> tasks;
        if (categoryId != null && targetId != null) {
            tasks = maintenanceTaskRepository.findByCategoryIdAndTargetsIdOrderByTaskYearDesc(categoryId, targetId);
            log.info("showIndex() called with category id: " + categoryId + " and target id: " + targetId);
        } else if (categoryId != null) {
            tasks = maintenanceTaskRepository.findByCategoryIdOrderByTaskYearDesc(categoryId);
            log.info("showIndex() called with category id: " + categoryId);
        } else if (targetId != null) {
            tasks = maintenanceTaskRepository.findByTargetsIdOrderByTaskYearDesc(targetId);
            log.info("showIndex() called with target id: " + targetId);
        } else {
            tasks = maintenanceTaskRepository.findAllByOrderByTaskYearDesc();
            log.info("showIndex() called with all categories and targets");
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("targets", maintenanceTargetRepository.findAll());

        // Save selected filter values 
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedTargetId", targetId);

        return "/index";
    }   

    // Add new maintenance task
    @GetMapping("/addMaintenanceTask")
    public String addMaintenanceTask(Model model) {
        model.addAttribute("task", new MaintenanceTask());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("targets", maintenanceTargetRepository.findAll());
        return "/addMaintenanceTask";
    }

    // Save and validate new maintenance task
    @PostMapping("/saveAndValidate")
    public String saveAndValidate(@Valid @ModelAttribute("task") MaintenanceTask task, BindingResult bindingResult, Model model) {
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
