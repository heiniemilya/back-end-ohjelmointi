package harjoitustyo.huoltovahti;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import harjoitustyo.huoltovahti.domain.MaintenanceTask;
import harjoitustyo.huoltovahti.domain.MaintenanceTaskRepository;

@SpringBootApplication
public class HuoltovahtiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuoltovahtiApplication.class, args);
	}

	// Add demo data to H2 database
	@Bean
	public CommandLineRunner demo(MaintenanceTaskRepository maintenanceTaskRepository) {
		return (args) -> {

			// Add Maintenance Tasks
			MaintenanceTask task1 = new MaintenanceTask("Ulkoseinien maalaus", "Maalattu kaikki A-talon ulkoseinät", 2024, 
			"kesä-heinäkuu", 0.0, 0.0, "valmis", 
			"Osakas", false);
			maintenanceTaskRepository.save(task1);

			MaintenanceTask task2 = new MaintenanceTask("Sähkösaneeraus", "Uusittu B-talon sähköjärjestelmä", 2025, 
			"tammikuu", 0.0, 0.0, "valmis", 
			"Osakas", false);
			maintenanceTaskRepository.save(task2);
		};
	}
}
