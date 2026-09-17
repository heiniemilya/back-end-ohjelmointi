package backend.bookstore.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity // Represents a table in relational database 
public class Book {
    @Id // Creates id column to the table
    @GeneratedValue(strategy = GenerationType.AUTO) // Automatically  generates a unique primary key for every new entity object
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @Min(value = 1000, message = "Publication year must be 4 digits")
    @Max(value = 9999, message = "Publication year must be 4 digits")
    private Integer publicationYear;
    @Size (min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    private String isbn;
    @Min(value = 0, message = "Price must be a positive number")
    private Double price; 
    
    @NotNull(message = "Select category")
    @ManyToOne // Defines a one-to-many/many-to-one relationship between two entities
    @JoinColumn(name="categoryid") // Defines the owner of the relationship 
    private Category category;

    public Book() {}

    public Book(String title, String author, Integer publicationYear, String isbn, Double price, Category category) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.price = price;
        this.category = category;
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

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", publicationYear=" + publicationYear
                + ", isbn=" + isbn + ", price=" + price + ", category=" + category + "]";
    }
}