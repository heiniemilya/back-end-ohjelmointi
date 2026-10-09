package backend.bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import backend.bookstore.web.BookController;
import backend.bookstore.web.RestBookController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookstoreApplicationTests {

	@Autowired 
	private BookController bookController;
	
	@Autowired 
	private RestBookController restBookController;
	

	@Test
	void contextLoads() {
	}

	@Test 
	public void bookControllerLoad() throws Exception{
		assertThat(bookController).isNotNull();
	}


	@Test 
	public void restBookControllerLoad() throws Exception{
		assertThat(restBookController).isNotNull();
	}
}
