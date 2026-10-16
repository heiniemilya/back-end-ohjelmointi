package harjoitustyo.huoltovahti.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import harjoitustyo.huoltovahti.domain.CategoryRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTargetRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTask;
import harjoitustyo.huoltovahti.domain.MaintenanceTaskRepository;

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
        
        // Filter maintenance tasks
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

        model.addAttribute("maintenanceTasks", tasks);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("targets", maintenanceTargetRepository.findAll());

        // Save selected filter values 
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedTargetId", targetId);

        return "/index";
    }   

}
