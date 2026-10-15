package harjoitustyo.huoltovahti.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

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

    // Default constructor
    public MaintenanceTask() {
    }

    // Constructor
    public MaintenanceTask(String title, String description, Integer year,
            String timeframe, Double estimatedCost, Double actualCost, String status, String responsibility, boolean isPaidByCompany) {
        this.title = title;
        this.description = description;
        this.taskYear = year;
        this.timeframe = timeframe;
        this.estimatedCost = estimatedCost;
        this.actualCost = actualCost;
        this.status = status;
        this.responsibility = responsibility;
        this.isPaidByCompany = isPaidByCompany;
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

    @Override
    public String toString() {
        return "MaintenanceTask [id=" + id + ", title=" + title + ", description=" + description + ", taskYear=" + taskYear
                + ", timeframe=" + timeframe + ", estimatedCost=" + estimatedCost + ", actualCost=" + actualCost
                + ", status=" + status + ", responsibility=" + responsibility + ", isPaidByCompany=" + isPaidByCompany + "]";
    }

}
