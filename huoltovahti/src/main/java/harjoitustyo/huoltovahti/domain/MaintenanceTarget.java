package harjoitustyo.huoltovahti.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity  // Represents a table in relational database 
public class MaintenanceTarget {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO) // Automatically generates a unique primary key for every new entity object
    private Long id;

    @NotBlank(message = "Target is required")
    private String name; // esim. "Talo A", "Jätekatos", "Pysäköintialue"

    private Boolean isCommonArea; // Kuuluuko kohde yhteisiin vai yksityisiin tiloihin

    @JsonIgnore
    @ManyToMany(mappedBy = "targets")
    private List<MaintenanceTask> tasks = new ArrayList<>();

    // Default constructor
    public MaintenanceTarget() {
    }

    // Constructor
    public MaintenanceTarget(String name, Boolean isCommonArea) {
        this.name = name;
        this.isCommonArea = isCommonArea;
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

    public Boolean isCommonArea() {
        return isCommonArea;
    }

    public void setCommonArea(Boolean isCommonArea) {
        this.isCommonArea = isCommonArea;
    }

    public List<MaintenanceTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<MaintenanceTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "MaintenanceTarget [id=" + id + ", name=" + name + ", isCommonArea=" + isCommonArea + "]";
    }
}