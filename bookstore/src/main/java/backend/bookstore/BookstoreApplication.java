package backend.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;
import backend.bookstore.domain.Category;
import backend.bookstore.domain.CategoryRepository;
import backend.bookstore.domain.AppUser;
import backend.bookstore.domain.AppUserRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	// Add demo data to H2 database
	@Bean
	public CommandLineRunner demo(BookRepository bookRepository, CategoryRepository categoryRepository, AppUserRepository appUserRepository) {
		return (args) -> {
			// Add categories
			Category category1 = new Category("Fantasy");
			categoryRepository.save(category1);

			Category category2 = new Category("Science Fiction");
			categoryRepository.save(category2);

			Category category3 = new Category("Romance");
			categoryRepository.save(category3);

			Category category4 = new Category("Other");
			categoryRepository.save(category4);

			// Add books
			Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "9780743273565", 10.99, category3);
			bookRepository.save(book1);

			Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", 1960, "9780061120084", 12.99, category2);
			bookRepository.save(book2);

			Book book3 = new Book("1984", "George Orwell", 1949, "9780451524935", 9.99, category1);
			bookRepository.save(book3);

			Book book4 = new Book("Pride and Prejudice", "Jane Austen", 1813, "9780141439518", 8.99, category3);
			bookRepository.save(book4);

			Book book5 = new Book("The Catcher in the Rye", "J.D. Salinger", 1951, "9780316769488", 11.99, category2);
			bookRepository.save(book5);

			// Add Users
			AppUser user1 = new AppUser("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			AppUser user2 = new AppUser("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			appUserRepository.save(user1);
			appUserRepository.save(user2);
		};
	}
}
