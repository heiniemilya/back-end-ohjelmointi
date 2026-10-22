package harjoitustyo.huoltovahti.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MaintenanceTaskRepository extends CrudRepository<MaintenanceTask, Long> {
     List<MaintenanceTask> findByTitle(String title);

    // find 10 most recent old tasks / Index
    List<MaintenanceTask> findTop8ByIsPlannedFalseOrderByTaskYearDescIdDesc();

    // find planned tasks / Index
    List<MaintenanceTask> findByIsPlannedTrueOrderByTaskYearAsc();

    // find all old tasks / MaintenanceTaskHistory
    List<MaintenanceTask> findAllByIsPlannedFalseOrderByTaskYearDescIdDesc();

    // find old tasks with specific category and target / MaintenanceTaskHistory
    List<MaintenanceTask> findByIsPlannedFalseAndCategoryIdAndTargetsIdOrderByTaskYearDescIdDesc(Long categoryId, Long targetId);

    // find old tasks with specific category / MaintenanceTaskHistory
    List<MaintenanceTask> findByIsPlannedFalseAndCategoryIdOrderByTaskYearDescIdDesc(Long categoryId);

    // find old tasks with specific target / MaintenanceTaskHistory
    List<MaintenanceTask> findByIsPlannedFalseAndTargetsIdOrderByTaskYearDescIdDesc(Long targetId);

    // find specific houses old tasks / MyMaintenanceTasks
    List<MaintenanceTask> findByTargetsContainsAndIsPlannedFalseOrderByTaskYearDescIdDesc(MaintenanceTarget target);

    // find specific houses old tasks with specific category / MyMaintenanceTasks
    List<MaintenanceTask> findByTargetsContainsAndIsPlannedFalseAndCategoryIdOrderByTaskYearDescIdDesc(MaintenanceTarget target, Long categoryId);

}
