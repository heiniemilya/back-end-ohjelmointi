package backend.bookstore;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
// import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;
import backend.bookstore.domain.Category;
import backend.bookstore.domain.CategoryRepository;

@DataJpaTest 
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use the real database instead of an in-memory database
public class BookRepositoryTests {
    @Autowired
    private BookRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;

    // Test to find a book by its title
    @Test
    public void findBookByTitleShouldReturnCorrectBook() {
        List<Book> books = repository.findByTitle("The Great Gatsby");
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor()).isEqualTo("F. Scott Fitzgerald");
    }

    // Test to create a new book and save it to the repository
    @Test
    public void createNewBook() {
        Category category = new Category("Test Category");
        categoryRepository.save(category);
        Book book = new Book("Test Book", "Test Author", 2023, "1234567890", 19.99, category);
        repository.save(book);
        assertThat(book.getId()).isNotNull();
    }

    // Test to delete a book from the repository
    @Test
    public void deleteBook() {
        List<Book> books = repository.findByTitle("The Great Gatsby");
        Book book = books.get(0);
        repository.delete(book);
        List<Book> deletedBooks = repository.findByTitle("The Great Gatsby");
        assertThat(deletedBooks).isEmpty();
    }
}
