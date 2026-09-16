package backend.bookstore.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String title;
    private String author;
    @Min(value = 1000, message = "Publication year must be 4 digits")
    @Max(value = 9999, message = "Publication year must be 4 digits")
    private Integer publicationYear;
    @Size (min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    private String isbn;
    @Min(value = 0, message = "Price must be a positive number")
    private Double price;

    public Book() {}

    public Book(String title, String author, Integer publicationYear, String isbn, Double price) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.price = price;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", publicationYear=" + publicationYear
                + ", isbn=" + isbn + ", price=" + price + "]";
    }
}