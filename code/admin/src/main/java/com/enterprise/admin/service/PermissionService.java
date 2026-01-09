package com.enterprise.admin.service;

import com.enterprise.admin.dto.PermissionDTO;
import com.enterprise.admin.entity.Permission;

import java.util.List;
import java.util.UUID;

/**
 * Service for Permission management (Admin operations)
 */
public interface PermissionService {

    Permission createPermission(PermissionDTO.CreateRequest request);

    Permission updatePermission(UUID id, PermissionDTO.UpdateRequest request);

    void deletePermission(UUID id);

    PermissionDTO.Response getPermission(UUID id);

    PermissionDTO.Response getPermissionByCode(String code);

    List<PermissionDTO.Response> getAllPermissions();

    List<PermissionDTO.Response> getPermissionsByApplication(String applicationCode);

    List<PermissionDTO.Response> getPermissionsByResource(String resource);
}
