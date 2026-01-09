package com.enterprise.admin.repository;

import com.enterprise.admin.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    Optional<Permission> findByCode(String code);

    List<Permission> findByApplicationCodeAndIsActiveTrue(String applicationCode);

    List<Permission> findByResourceAndIsActiveTrue(String resource);

    List<Permission> findByPermissionTypeAndIsActiveTrue(String permissionType);

    @Query("SELECT DISTINCT p FROM Permission p " +
            "JOIN p.groupPermissions gp " +
            "WHERE gp.group.code IN :groupCodes " +
            "AND gp.isGranted = true " +
            "AND p.isActive = true")
    List<Permission> findGrantedPermissionsByGroups(@Param("groupCodes") List<String> groupCodes);

    @Query("SELECT DISTINCT p FROM Permission p " +
            "JOIN p.groupPermissions gp " +
            "WHERE gp.group.code IN :groupCodes " +
            "AND p.applicationCode = :appCode " +
            "AND gp.isGranted = true " +
            "AND p.isActive = true")
    List<Permission> findGrantedPermissionsByGroupsAndApp(
            @Param("groupCodes") List<String> groupCodes,
            @Param("appCode") String applicationCode);

    boolean existsByCode(String code);
}
