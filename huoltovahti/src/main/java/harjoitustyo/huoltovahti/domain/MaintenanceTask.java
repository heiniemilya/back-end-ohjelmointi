package harjoitustyo.huoltovahti.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity  // Represents a table in relational database 
public class MaintenanceTask {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO) // Automatically  generates a unique primary key for every new entity object
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @Min(value = 1000, message = "Year must be 4 digits")
    @Max(value = 9999, message = "Year must be 4 digits")
    private Integer taskYear;

    private String timeframe;

    private Double estimatedCost;

    private Double actualCost;

    private String status;

    private String responsibility;
    
    private boolean isPaidByCompany;

    private boolean isSelfPerformed; 


    // Many-to-One: MaintenanceTask -> Category
    @NotNull(message = "Select category for maintenance task")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; 

    // Many-to-Many: MaintanenceTask -> MaintenanceTarget
    @NotEmpty(message = "Select at least one target for maintenance task")
    @ManyToMany
    @JoinTable(
        name = "task_target",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "target_id")
    )
    private List<MaintenanceTarget> targets = new ArrayList<>(); 

    // Default constructor
    public MaintenanceTask() {
    }

    // Constructor
    public MaintenanceTask(String title, String description, Integer year, String timeframe, Double estimatedCost, 
            Double actualCost, String status, String responsibility, boolean isPaidByCompany, boolean isSelfPerformed, Category category) {
        this.title = title;
        this.description = description;
        this.taskYear = year;
        this.timeframe = timeframe;
        this.estimatedCost = estimatedCost;
        this.actualCost = actualCost;
        this.status = status;
        this.responsibility = responsibility;
        this.isPaidByCompany = isPaidByCompany;
        this.isSelfPerformed = isSelfPerformed;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTaskYear() {
        return taskYear;
    }

    public void setTaskYear(Integer taskYear) {
        this.taskYear = taskYear;
    }

    public String getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(String timeframe) {
        this.timeframe = timeframe;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Double getActualCost() {
        return actualCost;
    }

    public void setActualCost(Double actualCost) {
        this.actualCost = actualCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public boolean isPaidByCompany() {
        return isPaidByCompany;
    }

    public void setPaidByCompany(boolean isPaidByCompany) {
        this.isPaidByCompany = isPaidByCompany;
    }

    public boolean isSelfPerformed() {
        return isSelfPerformed;
    }

    public void setSelfPerformed(boolean isSelfPerformed) {
        this.isSelfPerformed = isSelfPerformed;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<MaintenanceTarget> getTargets() {
        return targets;
    }

    public void setTargets(List<MaintenanceTarget> targets) {
        this.targets = targets;
    }


    @Override
    public String toString() {
        return "MaintenanceTask [id=" + id + ", title=" + title + ", description=" + description + ", taskYear=" + taskYear
                + ", timeframe=" + timeframe + ", estimatedCost=" + estimatedCost + ", actualCost=" + actualCost
                + ", status=" + status + ", responsibility=" + responsibility + ", isPaidByCompany=" + isPaidByCompany 
                + ", isSelfPerformed=" + isSelfPerformed + ", category=" + category + ", targets=" + targets + "]";
    }

}
