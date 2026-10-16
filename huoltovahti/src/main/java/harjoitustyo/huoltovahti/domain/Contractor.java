package harjoitustyo.huoltovahti.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity  // Represents a table in relational database 
public class Contractor {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO) // Automatically generates a unique primary key for every new entity object
    private Long id;

    private String name;

    private String businessId;

    private String role;

    private String phone;

    private String email;

    private String website;

    // Many-to-One: Contractor -> MaintenanceTask
    @ManyToOne
    @JoinColumn(name = "maintenance_task_id")
    private MaintenanceTask task; 

    // Default constructor
    public Contractor() {
    }

    // Constructor
    public Contractor(String name, String businessId, String role, String phone, String email, String website, MaintenanceTask task) {
        this.name = name;
        this.businessId = businessId;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.task = task;
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

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public MaintenanceTask getTask() {
        return task;
    }

    public void setTask(MaintenanceTask task) {
        this.task = task;
    }

    // toString
    @Override
    public String toString() {
        return "Contractor [id=" + id + ", name=" + name + ", businessId=" + businessId + ", role=" + role + ", phone="
                + phone + ", email=" + email + ", website=" + website + "]";
    }
    
}