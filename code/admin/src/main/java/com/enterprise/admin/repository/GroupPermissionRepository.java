package com.enterprise.admin.repository;

import com.enterprise.admin.entity.GroupPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupPermissionRepository extends JpaRepository<GroupPermission, UUID> {

    Optional<GroupPermission> findByGroupIdAndPermissionId(UUID groupId, UUID permissionId);

    List<GroupPermission> findByGroupId(UUID groupId);

    List<GroupPermission> findByPermissionId(UUID permissionId);

    @Query("SELECT gp FROM GroupPermission gp WHERE gp.group.code = :groupCode AND gp.isGranted = true")
    List<GroupPermission> findGrantedByGroupCode(@Param("groupCode") String groupCode);

    @Query("SELECT gp FROM GroupPermission gp WHERE gp.group.code IN :groupCodes AND gp.isGranted = true")
    List<GroupPermission> findGrantedByGroupCodes(@Param("groupCodes") List<String> groupCodes);

    void deleteByGroupIdAndPermissionId(UUID groupId, UUID permissionId);

    boolean existsByGroupIdAndPermissionId(UUID groupId, UUID permissionId);
}
