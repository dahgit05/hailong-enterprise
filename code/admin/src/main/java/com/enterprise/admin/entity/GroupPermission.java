package com.enterprise.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * GroupPermission - Many-to-Many relation between Group and Permission
 * Defines which actions a group can PERFORM (action level)
 */
@Entity
@Table(name = "group_permission", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "group_id", "permission_id" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private KcGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    /**
     * Whether this permission is granted (can be used for explicit deny)
     */
    @Column(name = "is_granted")
    @Builder.Default
    private Boolean isGranted = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
