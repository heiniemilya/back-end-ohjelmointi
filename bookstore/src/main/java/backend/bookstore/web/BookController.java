package backend.bookstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import backend.bookstore.domain.BookRepository;


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
}