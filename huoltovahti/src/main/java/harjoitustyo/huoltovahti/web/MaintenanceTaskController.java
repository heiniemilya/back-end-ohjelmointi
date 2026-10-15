package harjoitustyo.huoltovahti.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import harjoitustyo.huoltovahti.domain.CategoryRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTaskRepository;

@Controller 
public class MaintenanceTaskController {
    
    // Logger for logging information and errors
    private static final Logger log = LoggerFactory.getLogger(MaintenanceTaskController.class);

    // Constructor injection for repositories
    public MaintenanceTaskController(MaintenanceTaskRepository maintenanceTaskRepository, CategoryRepository categoryRepository) {
        this.maintenanceTaskRepository = maintenanceTaskRepository;
        this.categoryRepository = categoryRepository;
    }

    private final MaintenanceTaskRepository maintenanceTaskRepository;
    private final CategoryRepository categoryRepository;

    // Index page
    @GetMapping({"/", "/index"})
    public String showIndex(Model model) {
        log.info("showIndex() called");
        model.addAttribute("maintenanceTasks", maintenanceTaskRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "/index";
    }   

}
