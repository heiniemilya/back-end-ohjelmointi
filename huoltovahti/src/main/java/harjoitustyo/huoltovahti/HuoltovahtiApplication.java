package harjoitustyo.huoltovahti;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import harjoitustyo.huoltovahti.domain.Category;
import harjoitustyo.huoltovahti.domain.CategoryRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTask;
import harjoitustyo.huoltovahti.domain.MaintenanceTaskRepository;

@SpringBootApplication
public class HuoltovahtiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuoltovahtiApplication.class, args);
	}

	// Add demo data to H2 database
	@Bean
	public CommandLineRunner demo(CategoryRepository categoryRepository, MaintenanceTaskRepository maintenanceTaskRepository ) {
		return (args) -> {

			// Add categories
			Category category1 = new Category("Sisätilat");
			categoryRepository.save(category1);

			Category category2 = new Category("Ulkopuoli");
			categoryRepository.save(category2);

			Category category3 = new Category("LVI-työt");
			categoryRepository.save(category3);

			Category category4 = new Category("Sähkötyöt");
			categoryRepository.save(category4);

			Category category5 = new Category("Yläpohja/katto");
			categoryRepository.save(category5);

			Category category6 = new Category("Alapohja");
			categoryRepository.save(category6);

			Category category7 = new Category("Piha");
			categoryRepository.save(category7);

			// Add Maintenance Tasks
			MaintenanceTask task1 = new MaintenanceTask("Ulkoseinien maalaus", "Maalattu kaikki A-talon ulkoseinät", 2024, 
			"kesä-heinäkuu", 0.0, 0.0, "valmis", 
			"Osakas", false, category2);
			maintenanceTaskRepository.save(task1);

			MaintenanceTask task2 = new MaintenanceTask("Sähkösaneeraus", "Uusittu B-talon sähköjärjestelmä", 2025, 
			"tammikuu", 0.0, 0.0, "valmis", 
			"Osakas", false, category4);
			maintenanceTaskRepository.save(task2);

		};
	}
}
