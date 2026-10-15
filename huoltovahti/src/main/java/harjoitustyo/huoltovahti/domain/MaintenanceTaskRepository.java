package harjoitustyo.huoltovahti.domain;

import org.springframework.data.repository.CrudRepository;

public interface MaintenanceTaskRepository extends CrudRepository<MaintenanceTask, Long> {
    
}
