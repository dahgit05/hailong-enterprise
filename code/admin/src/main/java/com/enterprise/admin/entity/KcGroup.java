package com.enterprise.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * KcGroup - Synced from Keycloak groups
 * Group quyết định "THẤY GÌ" (menu) và "LÀM ĐƯỢC GÌ" (permission)
 */
@Entity
@Table(name = "kc_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KcGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Keycloak Group ID (synced from Keycloak)
     */
    @Column(name = "keycloak_id", unique = true, nullable = false)
    private String keycloakId;

    /**
     * Group code (e.g., "HR", "WAREHOUSE", "ADMIN")
     */
    @Column(name = "code", unique = true, nullable = false, length = 50)
    private String code;

    /**
     * Group name for display
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Description
     */
    @Column(name = "description", length = 500)
    private String description;

    /**
     * Parent group (for hierarchy)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private KcGroup parent;

    /**
     * Child groups
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<KcGroup> children = new HashSet<>();

    /**
     * Menus accessible by this group
     */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<GroupMenu> groupMenus = new HashSet<>();

    /**
     * Permissions assigned to this group
     */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<GroupPermission> groupPermissions = new HashSet<>();

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
