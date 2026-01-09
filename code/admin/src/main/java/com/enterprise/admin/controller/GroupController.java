package com.enterprise.admin.controller;

import com.enterprise.admin.dto.ApiResponse;
import com.enterprise.admin.dto.GroupDTO;
import com.enterprise.admin.entity.KcGroup;
import com.enterprise.admin.service.GroupService;
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
 * Admin controller for Group management
 * Best Practice:
 * - No try-catch (handled by GlobalExceptionHandler)
 * - Use ApiResponse wrapper
 * - Use @ResponseStatus instead of ResponseEntity where possible
 */
@RestController
@RequestMapping("/api/admin/groups")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Group Management", description = "Admin APIs for managing groups")
@PreAuthorize("hasRole('ADMIN') or hasRole('GROUP_ADMIN')")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new group")
    public ApiResponse<GroupDTO.Response> createGroup(
            @Valid @RequestBody GroupDTO.CreateRequest request) {
        log.info("Creating group: {}", request.getCode());
        KcGroup group = groupService.createGroup(request);
        return ApiResponse.created(groupService.getGroup(group.getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a group")
    public ApiResponse<GroupDTO.Response> updateGroup(
            @PathVariable UUID id,
            @Valid @RequestBody GroupDTO.UpdateRequest request) {
        log.info("Updating group: {}", id);
        groupService.updateGroup(id, request);
        return ApiResponse.success(groupService.getGroup(id), "Group updated successfully");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete (deactivate) a group")
    public ApiResponse<Void> deleteGroup(@PathVariable UUID id) {
        log.info("Deleting group: {}", id);
        groupService.deleteGroup(id);
        return ApiResponse.noContent();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get group by ID")
    public ApiResponse<GroupDTO.Response> getGroup(@PathVariable UUID id) {
        return ApiResponse.success(groupService.getGroup(id));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get group by code")
    public ApiResponse<GroupDTO.Response> getGroupByCode(@PathVariable String code) {
        return ApiResponse.success(groupService.getGroupByCode(code));
    }

    @GetMapping
    @Operation(summary = "Get all active groups")
    public ApiResponse<List<GroupDTO.Response>> getAllGroups() {
        return ApiResponse.success(groupService.getAllGroups());
    }

    @GetMapping("/tree")
    @Operation(summary = "Get groups as tree (hierarchy)")
    public ApiResponse<List<GroupDTO.Response>> getGroupTree() {
        return ApiResponse.success(groupService.getRootGroups());
    }

    // === Menu Assignments ===

    @PostMapping("/{groupId}/menus")
    @Operation(summary = "Assign menus to group")
    public ApiResponse<Void> assignMenus(
            @PathVariable UUID groupId,
            @RequestBody GroupDTO.AssignMenusRequest request) {
        log.info("Assigning {} menus to group {}", request.getMenuIds().size(), groupId);
        groupService.assignMenusToGroup(groupId, request.getMenuIds());
        return ApiResponse.success(null, "Menus assigned successfully");
    }

    @DeleteMapping("/{groupId}/menus")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove menus from group")
    public ApiResponse<Void> removeMenus(
            @PathVariable UUID groupId,
            @RequestBody GroupDTO.AssignMenusRequest request) {
        log.info("Removing {} menus from group {}", request.getMenuIds().size(), groupId);
        groupService.removeMenusFromGroup(groupId, request.getMenuIds());
        return ApiResponse.noContent();
    }

    // === Permission Assignments ===

    @PostMapping("/{groupId}/permissions")
    @Operation(summary = "Assign permissions to group")
    public ApiResponse<Void> assignPermissions(
            @PathVariable UUID groupId,
            @RequestBody GroupDTO.AssignPermissionsRequest request) {
        log.info("Assigning {} permissions to group {}", request.getPermissionIds().size(), groupId);
        groupService.assignPermissionsToGroup(groupId, request.getPermissionIds());
        return ApiResponse.success(null, "Permissions assigned successfully");
    }

    @DeleteMapping("/{groupId}/permissions")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove permissions from group")
    public ApiResponse<Void> removePermissions(
            @PathVariable UUID groupId,
            @RequestBody GroupDTO.AssignPermissionsRequest request) {
        log.info("Removing {} permissions from group {}", request.getPermissionIds().size(), groupId);
        groupService.removePermissionsFromGroup(groupId, request.getPermissionIds());
        return ApiResponse.noContent();
    }
}
