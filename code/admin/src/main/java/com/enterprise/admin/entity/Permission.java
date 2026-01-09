package com.enterprise.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Permission entity - Defines actions/operations
 * Permission quyết định "LÀM ĐƯỢC GÌ" (buttons, actions, data access)
 */
@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Permission code (e.g., "EMPLOYEE_VIEW", "EMPLOYEE_CREATE", "EMPLOYEE_DELETE")
     */
    @Column(name = "code", unique = true, nullable = false, length = 100)
    private String code;

    /**
     * Display name
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Description of what this permission allows
     */
    @Column(name = "description", length = 500)
    private String description;

    /**
     * Application this permission belongs to (e.g., "hrm", "factory")
     */
    @Column(name = "application_code", nullable = false, length = 50)
    private String applicationCode;

    /**
     * Permission type: VIEW, CREATE, EDIT, DELETE, EXPORT, IMPORT, APPROVE, etc.
     */
    @Column(name = "permission_type", length = 50)
    private String permissionType;

    /**
     * Resource this permission applies to (e.g., "employee", "warehouse_receipt")
     */
    @Column(name = "resource", length = 100)
    private String resource;

    /**
     * Groups that have this permission
     */
    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<GroupPermission> groupPermissions = new HashSet<>();

    /**
     * Menus where this permission is applicable
     */
    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<MenuPermission> menuPermissions = new HashSet<>();

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
