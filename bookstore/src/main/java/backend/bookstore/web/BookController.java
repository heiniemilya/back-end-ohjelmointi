package backend.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import backend.bookstore.domain.Book;
import backend.bookstore.domain.BookRepository;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class BookController {
    @GetMapping("/index")
    public String showIndex() {
        return "index";
    }   
    

public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    private final BookRepository bookRepository;

    @GetMapping("/booklist")
    public String showBookList(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

    @GetMapping("/addbook")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }

    @PostMapping("/save")
    public String save(Book book) {
        bookRepository.save(book);
        return "redirect:booklist";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
        bookRepository.deleteById(bookId);
        return "redirect:../booklist";
}
}