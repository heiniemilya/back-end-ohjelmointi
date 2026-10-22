package harjoitustyo.huoltovahti.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity  // Represents a table in relational database 
@Table(name = "maintenance_task")
public class MaintenanceTask {
    @Id 
   @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @NotBlank(message = "Anna huoltotyölle ostikko")
    private String title;

    private String description;

    @NotNull(message = "Lisää valmistumisvuosi")
    @Min(value = 1000, message = "Tarkista vuosiluku")
    @Max(value = 9999, message = "Tarkista vuosiluku")
    @Column(name = "task_year")
    private Integer taskYear;

    private String timeframe;

    @Column(name = "is_planned")
    private Boolean isPlanned;

    @NotNull(message = "Kenellä on kunnossapitovastuu?")
    private String responsibility;
    
    @NotNull(message = "Onko taloyhtiö rahoittanut huoltotyön?")
    @Column(name = "is_paid_by_company")
    private Boolean isPaidByCompany;

    // Validation in MaintenanceTaskController
    @Column(name = "is_self_performed")
    private Boolean isSelfPerformed; 

    // Validation in MaintenanceTaskController
    private String contractor;

    @Pattern(regexp = "^$|^\\d{7}-\\d$", message = "Y-tunnuksen muoto on 1234567-8")
    @Column(name = "contractor_business_id")
    private String contractorBusinessId;

    // Many-to-One: MaintenanceTask -> Category
    @NotNull(message = "Valitse kategoria")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; 

    // Many-to-Many: MaintenanceTask -> MaintenanceTarget
    @NotEmpty(message = "Valitse kohde")
    @ManyToMany
    @JoinTable(
        name = "task_target",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "target_id")
    )
    private List<MaintenanceTarget> targets = new ArrayList<>(); 
    
    // Many-to-One: MaintenanceTask -> AppUser
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private AppUser createdBy; 

    // Default constructor
    public MaintenanceTask() {
    }

    // Constructor
    public MaintenanceTask(String title, String description, Integer year, String timeframe, Boolean isPlanned, 
            String responsibility, Boolean isPaidByCompany, Boolean isSelfPerformed, String contractor, String contractorBusinessId, Category category, AppUser createdBy) {
        this.title = title;
        this.description = description;
        this.taskYear = year;
        this.timeframe = timeframe;
        this.isPlanned = isPlanned;
        this.responsibility = responsibility;
        this.isPaidByCompany = isPaidByCompany;
        this.isSelfPerformed = isSelfPerformed;
        this.contractor = contractor;
        this.contractorBusinessId = contractorBusinessId;
        this.category = category;
        this.createdBy = createdBy;
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

    public Boolean getIsPlanned() {
        return isPlanned;
    }

    public void setIsPlanned(Boolean isPlanned) {
        this.isPlanned = isPlanned;
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

    public void setIsPaidByCompany(Boolean isPaidByCompany) {
        this.isPaidByCompany = isPaidByCompany;
    }

    public Boolean getIsSelfPerformed() {
        return isSelfPerformed;
    }

    public void setIsSelfPerformed(Boolean isSelfPerformed) {
        this.isSelfPerformed = isSelfPerformed;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getContractorBusinessId() {
        return contractorBusinessId;
    }

    public void setContractorBusinessId(String contractorBusinessId) {
        this.contractorBusinessId = contractorBusinessId;
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

    public AppUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AppUser createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "MaintenanceTask [id=" + id + ", title=" + title + ", description=" + description + ", taskYear=" + taskYear
                + ", timeframe=" + timeframe + ", estimatedCost=" + ", isPlanned=" + isPlanned + ", responsibility=" + responsibility 
                + ", isPaidByCompany=" + isPaidByCompany + ", isSelfPerformed=" + isSelfPerformed + ", contractor=" + contractor 
                + "contractorBusinessId=" + contractorBusinessId + ", category=" + category + ", targets=" + targets + "]";
    }

}
