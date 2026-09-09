package backend.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	// Demodata H2 tietokantaan
	@Bean
	public CommandLineRunner demo(BookRepository bookRepository) {
		return (args) -> {
		 Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 10.99);
		 bookRepository.save(book1);

		 Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084", 12.99);
		 bookRepository.save(book2);

		 Book book3 = new Book("1984", "George Orwell", 1949, "9780451524935", 9.99);
		 bookRepository.save(book3);

		 Book book4 = new Book("Pride and Prejudice", "Jane Austen", 1813, "9780141439518", 8.99);
		 bookRepository.save(book4);

		 Book book5 = new Book("The Catcher in the Rye", "J.D. Salinger", 1951, "9780316769488", 11.99);
		 bookRepository.save(book5);
		};
	}
}
