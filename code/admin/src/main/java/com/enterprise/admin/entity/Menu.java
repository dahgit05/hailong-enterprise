package com.enterprise.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Menu entity - Navigation items for applications
 * Group → Menu relationship defines "WHAT USER SEES"
 */
@Entity
@Table(name = "menu", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "application_code", "code" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Application this menu belongs to (e.g., "hrm", "factory", "equipment")
     */
    @Column(name = "application_code", nullable = false, length = 50)
    private String applicationCode;

    /**
     * Menu code (e.g., "EMPLOYEE", "WAREHOUSE_RECEIPT")
     */
    @Column(name = "code", nullable = false, length = 100)
    private String code;

    /**
     * Display name
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Route path (e.g., "/employees", "/warehouse/receipts")
     */
    @Column(name = "path", length = 255)
    private String path;

    /**
     * Icon name (Material Icons or custom)
     */
    @Column(name = "icon", length = 50)
    private String icon;

    /**
     * Parent menu for hierarchy
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Menu parent;

    /**
     * Child menus
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @OrderBy("sortOrder ASC")
    @Builder.Default
    private Set<Menu> children = new HashSet<>();

    /**
     * Sort order for display
     */
    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;

    /**
     * Menu type: MENU, SUBMENU, ACTION
     */
    @Column(name = "menu_type", length = 20)
    @Builder.Default
    private String menuType = "MENU";

    /**
     * Groups that can access this menu
     */
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<GroupMenu> groupMenus = new HashSet<>();

    /**
     * Permissions available on this menu (buttons/actions)
     */
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
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
