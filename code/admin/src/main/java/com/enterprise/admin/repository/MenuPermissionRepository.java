package com.enterprise.admin.repository;

import com.enterprise.admin.entity.MenuPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuPermissionRepository extends JpaRepository<MenuPermission, UUID> {

    Optional<MenuPermission> findByMenuIdAndPermissionId(UUID menuId, UUID permissionId);

    List<MenuPermission> findByMenuIdAndIsActiveTrue(UUID menuId);

    List<MenuPermission> findByPermissionId(UUID permissionId);

    @Query("SELECT mp FROM MenuPermission mp " +
            "WHERE mp.menu.id = :menuId " +
            "AND mp.permission.code IN :permissionCodes " +
            "AND mp.isActive = true " +
            "ORDER BY mp.sortOrder")
    List<MenuPermission> findByMenuIdAndPermissionCodes(
            @Param("menuId") UUID menuId,
            @Param("permissionCodes") List<String> permissionCodes);

    @Query("SELECT mp FROM MenuPermission mp " +
            "JOIN mp.menu m " +
            "WHERE m.applicationCode = :appCode " +
            "AND mp.isActive = true")
    List<MenuPermission> findByApplicationCode(@Param("appCode") String applicationCode);

    void deleteByMenuIdAndPermissionId(UUID menuId, UUID permissionId);

    boolean existsByMenuIdAndPermissionId(UUID menuId, UUID permissionId);
}
