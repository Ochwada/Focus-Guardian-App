package org.example.focusguardian.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * **********************************************************************+
 * Package Name          :org.example.focusguardian.model
 * Author                :ochwada
 * Name of the Project   :focus-guardian
 * Date                  :Friday,27. Jun.2025 at 10:21
 * Description           : Represents a Focus Entity with an auto-generated ID, reason, Status and created time.
 * Objective             :
 * /** ***********************************************************************+
 */

/**
 * Entity representing a focus entry record in the database.
 * Each entry captures a reason for focus, its current status, and the timestamp of creation.
 */
@Entity
public class FocusEntry {

    // ------- Fields -------
    /**
     * The unique identifier for this focus entry.
     * It is automatically generated when the entity is persisted.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The reason associated with this focus entry.
     * This field captures the purpose or context of focus.
     */
    @Column(nullable = false)
    private String reason;

    /**
     * The status of the focus entry.
     * Typically used to indicate whether the focus reason is active (true) or completed/inactive (false).
     */
    @Column(nullable = false)
    private Boolean status;

    /**
     * The timestamp indicating when this focus entry was created.
     * Stored as a LocalDateTime.
     */
    private LocalDateTime createdAt;

    //BONUS
    @Column(nullable = false)
    private String category;

    // ------- Constructors -------

    /**
     * Default constructor required by JPA.
     * Initializes a new instance of FocusEntry with no initial values.
     */
    public FocusEntry() {
    }

    /**
     * Constructs a new FocusEntry with the given reason, status, and creation time.
     * The ID is not set explicitly and will be generated upon persistence.
     *
     * @param reason    The reason associated with the focus entry.
     * @param status    The current status of the focus entry (e.g., active or inactive).
     * @param createdAt The date and time when the focus entry was created.
     */
    public FocusEntry(String reason, Boolean status, LocalDateTime createdAt) {
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
    }

    /**
     * Automatically sets the creation timestamp before the entity is persisted.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    // ------- Getters and Setters -------
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
