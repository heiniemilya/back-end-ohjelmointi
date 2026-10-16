package harjoitustyo.huoltovahti.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MaintenanceTaskRepository extends CrudRepository<MaintenanceTask, Long> {
    List<MaintenanceTask> findAllByOrderByTaskYearDesc();

    List<MaintenanceTask> findByCategoryIdAndTargetsIdOrderByTaskYearDesc(Long categoryId, Long targetId);

    List<MaintenanceTask> findByCategoryIdOrderByTaskYearDesc(Long categoryId);

    List<MaintenanceTask> findByTargetsIdOrderByTaskYearDesc(Long targetId);
}
