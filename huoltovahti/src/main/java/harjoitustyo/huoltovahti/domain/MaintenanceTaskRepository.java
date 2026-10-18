package harjoitustyo.huoltovahti.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MaintenanceTaskRepository extends CrudRepository<MaintenanceTask, Long> {
    List<MaintenanceTask> findTop10ByIsPlannedFalseOrderByTaskYearDescIdDesc();

    List<MaintenanceTask> findAllByIsPlannedFalseOrderByTaskYearDescIdDesc();

    List<MaintenanceTask> findByIsPlannedFalseAndCategoryIdAndTargetsIdOrderByTaskYearDescIdDesc(Long categoryId, Long targetId);

    List<MaintenanceTask> findByIsPlannedFalseAndCategoryIdOrderByTaskYearDescIdDesc(Long categoryId);

    List<MaintenanceTask> findByIsPlannedFalseAndTargetsIdOrderByTaskYearDescIdDesc(Long targetId);

    List<MaintenanceTask> findByIsPlannedTrueOrderByTaskYearAsc();
}
