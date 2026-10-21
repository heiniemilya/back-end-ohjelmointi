package harjoitustyo.huoltovahti;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import harjoitustyo.huoltovahti.domain.AppUser;
import harjoitustyo.huoltovahti.domain.AppUserRepository;
import harjoitustyo.huoltovahti.domain.Category;
import harjoitustyo.huoltovahti.domain.CategoryRepository;
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
			MaintenanceTargetRepository maintenanceTargetRepository, AppUserRepository appUserRepository) {
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
			MaintenanceTarget target1 = new MaintenanceTarget("A", false);
			maintenanceTargetRepository.save(target1);

			MaintenanceTarget target2 = new MaintenanceTarget("B", false);
			maintenanceTargetRepository.save(target2);

			MaintenanceTarget target3 = new MaintenanceTarget("C", false);
			maintenanceTargetRepository.save(target3);

			MaintenanceTarget target4 = new MaintenanceTarget("Pysäköintialue", true);
			maintenanceTargetRepository.save(target4);

			MaintenanceTarget target5 = new MaintenanceTarget("Jätekatos", true);
			maintenanceTargetRepository.save(target5);

			// Add users
			AppUser user1 = new AppUser("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER", target2);
			AppUser user2 = new AppUser("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN", null);
			appUserRepository.save(user1);
			appUserRepository.save(user2);

			// Add Maintenance Tasks
			MaintenanceTask task1 = new MaintenanceTask("Ulkoseinien maalaus", "Maalattu kaikki A-talon ulkoseinät", 2024, 
				"kesä-heinäkuu", false, "Osakas", false, 
				true, null, null, category2, user2);
			task1.getTargets().add(target1);
			maintenanceTaskRepository.save(task1);

			MaintenanceTask task2 = new MaintenanceTask("Sähkösaneeraus", "Uusittu B-talon sähköjärjestelmä", 2025, 
				"tammikuu", false, "Osakas", false, 
				false, "Sähkötyöt Oy", "9876543-2", category4, user1);
			task2.getTargets().add(target2);
			maintenanceTaskRepository.save(task2);

			MaintenanceTask task3 = new MaintenanceTask("Kattoremontti", "Uusittu kaikkien talojen katot", 2026, 
				"joulu-tammikuu",  false, "Osakas", true, 
				false, "Kattoremppa Oy", "1234567-8", category5, user2);
			task3.getTargets().add(target1);
			task3.getTargets().add(target2);
			task3.getTargets().add(target3);
			maintenanceTaskRepository.save(task3);

			// Add Planned Maintenance Tasks
			MaintenanceTask task4 = new MaintenanceTask("Jätekatoksen uusinta", null, 2029, 
				null, true, "Taloyhtiö", true, 
				null, null, null, category7, user2);
			task4.getTargets().add(target4);
			maintenanceTaskRepository.save(task4);

			MaintenanceTask task5 = new MaintenanceTask("Parkkipaikan asfaltointi", null, 2028, 
				"kesä", true, "Taloyhtiö", true, 
				null, null, null, category7, user2);
			task5.getTargets().add(target5);
			maintenanceTaskRepository.save(task5);
		};
	}
}
