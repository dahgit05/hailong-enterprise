package com.enterprise.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * MenuPermission - Many-to-Many relation between Menu and Permission
 * Defines which permissions/actions are available on a specific menu
 * This is crucial for FE to know which buttons to show on each menu
 */
@Entity
@Table(name = "menu_permission", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "menu_id", "permission_id" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    /**
     * Action type for this permission on the menu
     * Maps to FE actions: "view", "create", "edit", "delete", "export", "import",
     * "approve"
     */
    @Column(name = "action_type", length = 50)
    private String actionType;

    /**
     * Display order for buttons
     */
    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
