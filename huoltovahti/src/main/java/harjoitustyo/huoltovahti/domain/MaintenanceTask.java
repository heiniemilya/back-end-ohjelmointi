package harjoitustyo.huoltovahti.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @NotBlank(message = "Anna huoltotyölle ostikko")
    private String title;

    private String description;

    @NotNull(message = "Lisää valmistumisvuosi")
    @Min(value = 1000, message = "Tarkista vuosiluku")
    @Max(value = 9999, message = "Tarkista vuosiluku")
    private Integer taskYear;

    private String timeframe;

    private String status;

    @NotNull(message = "Kenellä on kunnossapitovastuu?")
    private String responsibility;
    
    @NotNull(message = "Onko taloyhtiö rahoittanut huoltotyön?")
    private Boolean isPaidByCompany;

    @NotNull(message = "Valitse kuka työn on suorittanut")
    private Boolean isSelfPerformed; 

    // Many-to-One: MaintenanceTask -> Category
    @NotNull(message = "Valitse kategoria")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; 

    // One-to-Many: MaintenanceTask -> Contractor
    @JsonIgnore 
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="task")
    private List<Contractor> contractors;

    // Many-to-Many: MaintenanceTask -> MaintenanceTarget
    @NotEmpty(message = "Valitse huollettu kohde")
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
    public MaintenanceTask(String title, String description, Integer year, String timeframe, String status, 
            String responsibility, boolean isPaidByCompany, boolean isSelfPerformed, Category category) {
        this.title = title;
        this.description = description;
        this.taskYear = year;
        this.timeframe = timeframe;
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

    public Boolean getIsPaidByCompany() {
        return isPaidByCompany;
    }

    public void setIsPaidByCompany(boolean isPaidByCompany) {
        this.isPaidByCompany = isPaidByCompany;
    }

    public Boolean getIsSelfPerformed() {
        return isSelfPerformed;
    }

    public void setIsSelfPerformed(boolean isSelfPerformed) {
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

    public List<Contractor> getContractors() {
        return contractors;
    }

    public void setContractors(List<Contractor> contractors) {
        this.contractors = contractors;
    }

    @Override
    public String toString() {
        return "MaintenanceTask [id=" + id + ", title=" + title + ", description=" + description + ", taskYear=" + taskYear
                + ", timeframe=" + timeframe + ", estimatedCost=" + ", status=" + status + ", responsibility=" + responsibility 
                + ", isPaidByCompany=" + isPaidByCompany + ", isSelfPerformed=" + isSelfPerformed + ", category=" + category 
                + ", targets=" + targets + ", contractors=" + contractors + "]";
    }

}
