package backend.bookstore.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;
import backend.bookstore.domain.Category;
import backend.bookstore.domain.CategoryRepository;

@RestController 
public class RestBookController {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    // constructor injection
    public RestBookController(BookRepository bookRepository, CategoryRepository categoryRepository){
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    // Logger for logging information and errors
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    // get all categories
    @GetMapping("/categories")
    public Iterable<Category> findAllCategories() {
        return categoryRepository.findAll();
    } 

    // get all books
    @GetMapping("/books")
    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    } 

    // get one book by id
    @GetMapping("/books/{id}")
    public Optional<Book> findBookById(@PathVariable("id") Long id ) {
        log.info("findBookById() called with ID: {}", id);
        return bookRepository.findById(id);
    } 

    // add new book
    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }
    
    // edit a book
    @PutMapping("books/{id}")
    public Book saveEditedBook(@PathVariable Long id, @RequestBody Book editedBook) {
        log.info("saveEditedBook() called with ID: {}", id);
        editedBook.setId(id); 
        return bookRepository.save(editedBook);
    }

    // delete book
    @DeleteMapping("books/{id}")
    public Iterable<Book> deleteBook(@PathVariable Long id) {
        log.info("deleteBook() called with ID: {}", id);
        bookRepository.deleteById(id);
        return bookRepository.findAll();
    }
}
