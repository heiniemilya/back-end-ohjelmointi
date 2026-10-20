package harjoitustyo.huoltovahti.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity(name= "users")
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String passwordHash;
	
	@Column(name = "role", nullable = false)
	private String role;

    @JsonIgnore 
    @OneToMany(cascade = CascadeType.ALL, mappedBy ="createdBy")
    private List<MaintenanceTask> maintanenceTasks;

    // Default constructor
    public AppUser() {
    }

    // Constructor
    public AppUser(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<MaintenanceTask> getMaintanenceTasks() {
        return maintanenceTasks;
    }

    public void setMaintanenceTasks(List<MaintenanceTask> maintanenceTasks) {
        this.maintanenceTasks = maintanenceTasks;
    }

}
