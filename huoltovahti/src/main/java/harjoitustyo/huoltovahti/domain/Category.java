package harjoitustyo.huoltovahti.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity  // Represents a table in relational database 
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Automatically generates a unique primary key for every new entity object
    private Long id;

    private String name;

    // One-to-Many: Category -> MaintenanceTask
    @JsonIgnore 
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="category")
    private List<MaintenanceTask> tasks;

    // Default constructor
    public Category() {
    }

    // Constuctor
    public Category(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MaintenanceTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<MaintenanceTask> tasks) {
        this.tasks = tasks;
    }

    // toString
    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
    
}