package com.enterprise.admin.repository;

import com.enterprise.admin.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID> {

        Optional<Menu> findByApplicationCodeAndCode(String applicationCode, String code);

        List<Menu> findByApplicationCodeAndIsActiveTrueOrderBySortOrderAsc(String applicationCode);

        List<Menu> findByApplicationCodeAndParentIsNullAndIsActiveTrueOrderBySortOrderAsc(String applicationCode);

        @Query("SELECT m FROM Menu m LEFT JOIN FETCH m.children WHERE m.applicationCode = :appCode AND m.parent IS NULL AND m.isActive = true ORDER BY m.sortOrder")
        List<Menu> findRootMenusWithChildren(@Param("appCode") String applicationCode);

        @Query("SELECT DISTINCT m FROM Menu m " +
                        "JOIN m.groupMenus gm " +
                        "WHERE gm.group.code IN :groupCodes " +
                        "AND m.applicationCode = :appCode " +
                        "AND m.isActive = true " +
                        "AND gm.isVisible = true " +
                        "ORDER BY m.sortOrder")
        List<Menu> findAccessibleMenusByGroups(
                        @Param("groupCodes") List<String> groupCodes,
                        @Param("appCode") String applicationCode);

        @Query("SELECT m FROM Menu m WHERE m.parent.id = :parentId AND m.isActive = true ORDER BY m.sortOrder")
        List<Menu> findByParentId(@Param("parentId") UUID parentId);

        boolean existsByApplicationCodeAndCode(String applicationCode, String code);

        /**
         * Get all root menus with children for an application (no permission filtering)
         * Uses LEFT JOIN FETCH for performance (avoiding N+1 queries)
         */
        @Query("SELECT DISTINCT m FROM Menu m " +
                        "LEFT JOIN FETCH m.children c " +
                        "WHERE m.applicationCode = :appCode " +
                        "AND m.parent IS NULL " +
                        "AND m.isActive = true " +
                        "ORDER BY m.sortOrder")
        List<Menu> findAllRootMenusWithChildren(@Param("appCode") String applicationCode);
}
