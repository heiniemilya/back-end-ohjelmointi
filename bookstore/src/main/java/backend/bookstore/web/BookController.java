package backend.bookstore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;
import backend.bookstore.domain.CategoryRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class BookController {

    // Logger for logging information and errors
    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    // Constructor injection for repositories
    public BookController(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    private final BookRepository bookRepository; 
    private final CategoryRepository categoryRepository;

    // Login
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
    
    // Index page
    @GetMapping("/index")
    public String showIndex() {
        log.info("showIndex() called"); 
        return "index";
    }   

    // Book list page
    @GetMapping("/booklist")
    public String showBookList(Model model) {
        log.info("showBookList() called");
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

    // Add new book
    @GetMapping("/addbook")
    @PreAuthorize("hasRole('ADMIN')")
    public String addBook(Model model) {
        log.info("addBook() called");
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addbook";
    }

    // Delete book by ID
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable Long id) {
        log.info("deleteBook() called with ID: {}", id);
        bookRepository.deleteById(id);
        return "redirect:/booklist";
    }

    // Edit book by ID
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editBook(@PathVariable Long id, Model model) {
        log.info("editBook() called with ID: {}", id);
        Book book = bookRepository.findById(id).get();
        model.addAttribute("bookToEdit", book);
        model.addAttribute("categories", categoryRepository.findAll());
        return "editbook";
    }

    // Save and validate new book
    @PostMapping("/saveandvalidate")
    public String saveAndValidate(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {
        log.info("saveAndValidate() called");
        if (bindingResult.hasErrors()) {
            log.error("Validation error happened, book: " + book);
            model.addAttribute("book", book);
            model.addAttribute("categories", categoryRepository.findAll());
            return "addbook";
        }
        log.info("Validation passed, saving book: " + book);
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    // Save and validate edited book
    @PostMapping("/saveeditedbook")
    public String saveEditedBook(@Valid @ModelAttribute("bookToEdit") Book bookToEdit, BindingResult bindingResult, Model model) {
        log.info("saveEditedBook() called");
        if (bindingResult.hasErrors()) {
            log.error("Validation error happened, edited book: " + bookToEdit);
            model.addAttribute("categories", categoryRepository.findAll());
            return "editbook";
        }
        log.info("Validation passed, saving edited book: " + bookToEdit);
        bookRepository.save(bookToEdit);
        return "redirect:/booklist";
    }

}