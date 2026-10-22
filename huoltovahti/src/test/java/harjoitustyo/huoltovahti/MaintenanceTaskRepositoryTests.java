package harjoitustyo.huoltovahti;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import harjoitustyo.huoltovahti.domain.Category;
import harjoitustyo.huoltovahti.domain.CategoryRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTarget;
import harjoitustyo.huoltovahti.domain.MaintenanceTargetRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTask;
import harjoitustyo.huoltovahti.domain.MaintenanceTaskRepository;

@DataJpaTest 
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use the real database instead of an in-memory database
public class MaintenanceTaskRepositoryTests {
    @Autowired
    private MaintenanceTaskRepository taskRepository;
    @Autowired
    private MaintenanceTargetRepository targetRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // Test to create a new task and save it to the repository
    @Test
    public void createNewTask() {
        Category category = new Category("Testi kategoria");
        categoryRepository.save(category);
        MaintenanceTarget target = new MaintenanceTarget("Testikohde", true);
        targetRepository.save(target);
        MaintenanceTask task = new MaintenanceTask("Testi huoltotyö", "Testing testing", 2026, 
				"Kesä", true, "Taloyhtiö", true, 
				false, "Remppa Oy", "7654321-8", category, null);
        task.setTargets(List.of(target));
        taskRepository.save(task);
        assertThat(task.getId()).isNotNull();
    }

    // Test to find a task by its title
    @Test
    public void findTaskByTitleShouldReturnCorrectTask() {
        List<MaintenanceTask> tasks = taskRepository.findByTitle("Kattoremontti");
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTaskYear()).isEqualTo(2026);
    }

    // Test to delete a task from the repository
    @Test
    public void deleteTask() {
        List<MaintenanceTask> tasks = taskRepository.findByTitle("Kattoremontti");
        MaintenanceTask task = tasks.get(0);
        taskRepository.delete(task);
        List<MaintenanceTask> deletedTasks = taskRepository.findByTitle("Kattoremontti");
        assertThat(deletedTasks).isEmpty();
    }
}
