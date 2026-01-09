package com.enterprise.admin.controller;

import com.enterprise.admin.dto.ApiResponse;
import com.enterprise.admin.dto.PermissionDTO;
import com.enterprise.admin.entity.Permission;
import com.enterprise.admin.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Admin controller for Permission management
 * Best Practice: ApiResponse wrapper, @ResponseStatus, no try-catch
 */
@RestController
@RequestMapping("/api/admin/permissions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Permission Management", description = "Admin APIs for managing permissions")
@PreAuthorize("hasRole('ADMIN') or hasRole('GROUP_ADMIN')")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new permission")
    public ApiResponse<PermissionDTO.Response> createPermission(
            @Valid @RequestBody PermissionDTO.CreateRequest request) {
        log.info("Creating permission: {}", request.getCode());
        Permission permission = permissionService.createPermission(request);
        return ApiResponse.created(permissionService.getPermission(permission.getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a permission")
    public ApiResponse<PermissionDTO.Response> updatePermission(
            @PathVariable UUID id,
            @Valid @RequestBody PermissionDTO.UpdateRequest request) {
        log.info("Updating permission: {}", id);
        permissionService.updatePermission(id, request);
        return ApiResponse.success(permissionService.getPermission(id), "Permission updated successfully");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete (deactivate) a permission")
    public ApiResponse<Void> deletePermission(@PathVariable UUID id) {
        log.info("Deleting permission: {}", id);
        permissionService.deletePermission(id);
        return ApiResponse.noContent();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get permission by ID")
    public ApiResponse<PermissionDTO.Response> getPermission(@PathVariable UUID id) {
        return ApiResponse.success(permissionService.getPermission(id));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get permission by code")
    public ApiResponse<PermissionDTO.Response> getPermissionByCode(@PathVariable String code) {
        return ApiResponse.success(permissionService.getPermissionByCode(code));
    }

    @GetMapping
    @Operation(summary = "Get all permissions")
    public ApiResponse<List<PermissionDTO.Response>> getAllPermissions(
            @RequestParam(name = "app", required = false) String applicationCode,
            @RequestParam(name = "resource", required = false) String resource) {
        if (applicationCode != null) {
            return ApiResponse.success(permissionService.getPermissionsByApplication(applicationCode));
        }
        if (resource != null) {
            return ApiResponse.success(permissionService.getPermissionsByResource(resource));
        }
        return ApiResponse.success(permissionService.getAllPermissions());
    }
}
