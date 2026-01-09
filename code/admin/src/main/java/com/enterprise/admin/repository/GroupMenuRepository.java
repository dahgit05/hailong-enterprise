package com.enterprise.admin.repository;

import com.enterprise.admin.entity.GroupMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupMenuRepository extends JpaRepository<GroupMenu, UUID> {

    Optional<GroupMenu> findByGroupIdAndMenuId(UUID groupId, UUID menuId);

    List<GroupMenu> findByGroupId(UUID groupId);

    List<GroupMenu> findByMenuId(UUID menuId);

    @Query("SELECT gm FROM GroupMenu gm WHERE gm.group.code = :groupCode AND gm.isVisible = true")
    List<GroupMenu> findVisibleByGroupCode(@Param("groupCode") String groupCode);

    void deleteByGroupIdAndMenuId(UUID groupId, UUID menuId);

    boolean existsByGroupIdAndMenuId(UUID groupId, UUID menuId);
}
