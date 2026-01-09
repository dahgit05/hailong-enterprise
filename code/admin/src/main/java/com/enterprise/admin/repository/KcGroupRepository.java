package com.enterprise.admin.repository;

import com.enterprise.admin.entity.KcGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface KcGroupRepository extends JpaRepository<KcGroup, UUID> {

    Optional<KcGroup> findByKeycloakId(String keycloakId);

    Optional<KcGroup> findByCode(String code);

    List<KcGroup> findByIsActiveTrue();

    List<KcGroup> findByParentIsNull();

    @Query("SELECT g FROM KcGroup g WHERE g.code IN :codes AND g.isActive = true")
    List<KcGroup> findActiveByCodeIn(@Param("codes") List<String> codes);

    @Query("SELECT g FROM KcGroup g LEFT JOIN FETCH g.children WHERE g.parent IS NULL AND g.isActive = true")
    List<KcGroup> findRootGroupsWithChildren();

    boolean existsByCode(String code);

    boolean existsByKeycloakId(String keycloakId);
}
