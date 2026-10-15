package harjoitustyo.huoltovahti;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import harjoitustyo.huoltovahti.domain.Category;
import harjoitustyo.huoltovahti.domain.CategoryRepository;
import harjoitustyo.huoltovahti.domain.Contractor;
import harjoitustyo.huoltovahti.domain.ContractorRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTarget;
import harjoitustyo.huoltovahti.domain.MaintenanceTargetRepository;
import harjoitustyo.huoltovahti.domain.MaintenanceTask;
import harjoitustyo.huoltovahti.domain.MaintenanceTaskRepository;

@SpringBootApplication
public class HuoltovahtiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HuoltovahtiApplication.class, args);
	}

	// Add demo data to H2 database
	@Bean
	public CommandLineRunner demo(CategoryRepository categoryRepository, MaintenanceTaskRepository maintenanceTaskRepository, 
			MaintenanceTargetRepository maintenanceTargetRepository, ContractorRepository contractorRepository) {
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

			// Add Maintenance Targets
			MaintenanceTarget target1 = new MaintenanceTarget("Talo A", false);
			maintenanceTargetRepository.save(target1);

			MaintenanceTarget target2 = new MaintenanceTarget("Talo B", false);
			maintenanceTargetRepository.save(target2);

			MaintenanceTarget target3 = new MaintenanceTarget("Talo C", false);
			maintenanceTargetRepository.save(target3);

			MaintenanceTarget target4 = new MaintenanceTarget("Pysäköintialue", true);
			maintenanceTargetRepository.save(target4);

			MaintenanceTarget target5 = new MaintenanceTarget("Jätekatos", true);
			maintenanceTargetRepository.save(target5);

			// Add Maintenance Tasks
			MaintenanceTask task1 = new MaintenanceTask("Ulkoseinien maalaus", "Maalattu kaikki A-talon ulkoseinät", 2024, 
				"kesä-heinäkuu", 0.0, 0.0, "valmis", 
				"Osakas", false, true, category2);
			task1.getTargets().add(target1);
			maintenanceTaskRepository.save(task1);

			MaintenanceTask task2 = new MaintenanceTask("Sähkösaneeraus", "Uusittu B-talon sähköjärjestelmä", 2025, 
				"tammikuu", 0.0, 0.0, "valmis", 
				"Osakas", false, false, category4);
			task2.getTargets().add(target2);
			maintenanceTaskRepository.save(task2);

			MaintenanceTask task3 = new MaintenanceTask("Kattoremontti", "Uusittu kaikkien talojen katot", 2026, 
				"joulu-tammikuu", 0.0, 0.0, "valmis", 
				"Taloyhtiö", false, false, category5);
			task3.getTargets().add(target1);
			task3.getTargets().add(target2);
			task3.getTargets().add(target3);
			maintenanceTaskRepository.save(task3);

			// Add Contractors
			Contractor contractor1 = new Contractor("Kattoremppa Oy", "1234567-8", 
				"Rakennusurakointi", "040 123 4567", "rakennusliike@oy.fi", "www.rakennusliike.oy", task3);
			contractorRepository.save(contractor1);

			Contractor contractor2 = new Contractor("Sähkötyöt Oy", "9876543-2", 
				"Sähköasennus", "050 987 6543", "sahko@oy.fi", "www.sahko.oy", task2);
			contractorRepository.save(contractor2);

		};
	}
}
