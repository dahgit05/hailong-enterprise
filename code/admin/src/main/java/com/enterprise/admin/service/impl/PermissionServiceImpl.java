package com.enterprise.admin.service.impl;

import com.enterprise.admin.dto.PermissionDTO;
import com.enterprise.admin.entity.Permission;
import com.enterprise.admin.exception.DuplicateResourceException;
import com.enterprise.admin.exception.ResourceNotFoundException;
import com.enterprise.admin.repository.PermissionRepository;
import com.enterprise.admin.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(PermissionDTO.CreateRequest request) {
        if (permissionRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Permission", "code", request.getCode());
        }

        Permission permission = Permission.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .applicationCode(request.getApplicationCode())
                .permissionType(request.getPermissionType())
                .resource(request.getResource())
                .isActive(true)
                .build();

        return permissionRepository.save(permission);
    }

    @Override
    public Permission updatePermission(UUID id, PermissionDTO.UpdateRequest request) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", id));

        permission.setName(request.getName());
        permission.setDescription(request.getDescription());
        if (request.getPermissionType() != null)
            permission.setPermissionType(request.getPermissionType());
        if (request.getResource() != null)
            permission.setResource(request.getResource());
        if (request.getIsActive() != null)
            permission.setIsActive(request.getIsActive());

        return permissionRepository.save(permission);
    }

    @Override
    public void deletePermission(UUID id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", id));
        permission.setIsActive(false);
        permissionRepository.save(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionDTO.Response getPermission(UUID id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", id));
        return mapToResponse(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionDTO.Response getPermissionByCode(String code) {
        Permission permission = permissionRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "code", code));
        return mapToResponse(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO.Response> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .filter(Permission::getIsActive)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO.Response> getPermissionsByApplication(String applicationCode) {
        return permissionRepository.findByApplicationCodeAndIsActiveTrue(applicationCode)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionDTO.Response> getPermissionsByResource(String resource) {
        return permissionRepository.findByResourceAndIsActiveTrue(resource)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PermissionDTO.Response mapToResponse(Permission permission) {
        return PermissionDTO.Response.builder()
                .id(permission.getId())
                .code(permission.getCode())
                .name(permission.getName())
                .description(permission.getDescription())
                .applicationCode(permission.getApplicationCode())
                .permissionType(permission.getPermissionType())
                .resource(permission.getResource())
                .isActive(permission.getIsActive())
                .createdAt(permission.getCreatedAt())
                .updatedAt(permission.getUpdatedAt())
                .build();
    }
}
