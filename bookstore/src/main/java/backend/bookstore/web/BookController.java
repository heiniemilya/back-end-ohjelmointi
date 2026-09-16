package backend.bookstore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private final BookRepository bookRepository;

    @GetMapping("/index")
    public String showIndex() {
        log.info("showIndex() called"); 
        return "index";
    }   

    @GetMapping("/booklist")
    public String showBookList(Model model) {
        log.info("showBookList() called");
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

    @GetMapping("/addbook")
    public String addBook(Model model) {
        log.info("addBook() called");
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        log.info("deleteBook() called with ID: {}", id);
        bookRepository.deleteById(id);
        return "redirect:/booklist";
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, Model model) {
        log.info("editBook() called with ID: {}", id);
        Book book = bookRepository.findById(id).get();
        model.addAttribute("bookToEdit", book);
        return "editbook";
    }

    @PostMapping("/saveandvalidate")
    public String saveAndValidate(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {
        log.info("saveAndValidate() called");
        if (bindingResult.hasErrors()) {
            log.error("Validation error happened, book: " + book);
            model.addAttribute("book", book);
            return "addbook";
        }
        log.info("Validation passed, saving book: " + book);
        bookRepository.save(book);
        return "redirect:/booklist";
    }

    @PostMapping("/saveeditedbook")
    public String saveEditedBook(@Valid @ModelAttribute("bookToEdit") Book bookToEdit, BindingResult bindingResult) {
        log.info("saveEditedBook() called");
        if (bindingResult.hasErrors()) {
            log.error("Validation error happened, edited book: " + bookToEdit);
            return "editbook";
        }
        log.info("Validation passed, saving edited book: " + bookToEdit);
        bookRepository.save(bookToEdit);
        return "redirect:/booklist";
    }

}