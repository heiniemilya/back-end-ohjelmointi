package harjoitustyo.huoltovahti;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import harjoitustyo.huoltovahti.web.MaintenanceTaskController;
import harjoitustyo.huoltovahti.web.MaintenanceTaskRestController;

@SpringBootTest
class HuoltovahtiApplicationTests {

	@Autowired 
    MaintenanceTaskController taskController;
	@Autowired 
    MaintenanceTaskRestController taskRestController;
	
	// Smoke tests 
	@Test
	void contextLoads() {
	}

	@Test
    public void taskControllerLoads() throws Exception {
      assertThat(taskController).isNotNull();
    }

	@Test
    public void restControllerLoads() throws Exception {
      assertThat(taskRestController).isNotNull();
    }
}
