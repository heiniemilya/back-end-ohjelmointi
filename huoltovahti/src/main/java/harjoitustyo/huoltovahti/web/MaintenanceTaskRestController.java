package harjoitustyo.huoltovahti.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import harjoitustyo.huoltovahti.domain.Category;
import harjoitustyo.huoltovahti.domain.CategoryRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTarget;
import harjoitustyo.huoltovahti.domain.MaintenanceTargetRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTask;
import harjoitustyo.huoltovahti.domain.MaintenanceTaskRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
public class MaintenanceTaskRestController {
    
    private final MaintenanceTaskRepository maintenanceTaskRepository;
    private final MaintenanceTargetRepository maintenanceTargetRepository;
    private final CategoryRepository categoryRepository;

    // constructor injection
    public MaintenanceTaskRestController(MaintenanceTaskRepository maintenanceTaskRepository, 
                                        MaintenanceTargetRepository maintenanceTargetRepository,
                                        CategoryRepository categoryRepository){
        this.maintenanceTaskRepository = maintenanceTaskRepository;
        this.maintenanceTargetRepository = maintenanceTargetRepository;
        this.categoryRepository = categoryRepository;
    }

    // Logger for logging information and errors
    private static final Logger log = LoggerFactory.getLogger(MaintenanceTaskController.class);

    // get all tasks
    @GetMapping("/tasks")
    public Iterable<MaintenanceTask> findAllTasks() {
        return maintenanceTaskRepository.findAll();
    } 

    // get all targets
    @GetMapping("/targets")
    public Iterable<MaintenanceTarget> findAllTargets() {
        return maintenanceTargetRepository.findAll();
    } 

    // get all categories
    @GetMapping("/categories")
    public Iterable<Category> findAllCategories() {
        return categoryRepository.findAll();
    } 

    // get task by ID
    @GetMapping("/tasks/{id}")
    public Optional<MaintenanceTask> findTaskById(@PathVariable("id") Long id) {
        log.info("findMaintenanceTaskById() called with ID: {}", id);
        return maintenanceTaskRepository.findById(id);
    } 

    // get target by ID
    @GetMapping("/targets/{id}")
    public Optional<MaintenanceTarget> findTargetById(@PathVariable("id") Long id) {
        return maintenanceTargetRepository.findById(id);
    }

    // get category by ID
    @GetMapping("/categories/{id}")
    public Optional<Category> findCategoryById(@PathVariable("id") Long id) {
        return categoryRepository.findById(id);
    }
    
    // add new task
    @PostMapping("/tasks")
    public MaintenanceTask addTask(@RequestBody MaintenanceTask maintenanceTask) {
        return maintenanceTaskRepository.save(maintenanceTask);
    }
    
    // edit task
    @PutMapping("/tasks/{id}")
    public MaintenanceTask saveEditedBook(@PathVariable Long id, @RequestBody MaintenanceTask editedTask) {
        log.info("saveEditedTask() called with ID: {}", id);
        editedTask.setId(id); 
        return maintenanceTaskRepository.save(editedTask);
    }

    // delete task
    @DeleteMapping("/tasks/{id}")
    public Iterable<MaintenanceTask> deleteTask(@PathVariable Long id) {
        log.info("deleteTask() called with ID: {}", id);
        maintenanceTaskRepository.deleteById(id);
        return maintenanceTaskRepository.findAll();
    }
}
