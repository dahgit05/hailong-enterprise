package com.enterprise.admin.controller;

import com.enterprise.admin.dto.ApiResponse;
import com.enterprise.admin.dto.MenuDTO;
import com.enterprise.admin.entity.Menu;
import com.enterprise.admin.service.MenuService;
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
 * Admin controller for Menu management
 * Best Practice: ApiResponse wrapper, @ResponseStatus, no try-catch
 */
@RestController
@RequestMapping("/api/admin/menus")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Menu Management", description = "Admin APIs for managing menus")
@PreAuthorize("hasRole('ADMIN') or hasRole('GROUP_ADMIN')")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new menu")
    public ApiResponse<MenuDTO.Response> createMenu(
            @Valid @RequestBody MenuDTO.CreateRequest request) {
        log.info("Creating menu: {} for app: {}", request.getCode(), request.getApplicationCode());
        Menu menu = menuService.createMenu(request);
        return ApiResponse.created(menuService.getMenu(menu.getId()));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a menu")
    public ApiResponse<MenuDTO.Response> updateMenu(
            @PathVariable UUID id,
            @Valid @RequestBody MenuDTO.UpdateRequest request) {
        log.info("Updating menu: {}", id);
        menuService.updateMenu(id, request);
        return ApiResponse.success(menuService.getMenu(id), "Menu updated successfully");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete (deactivate) a menu")
    public ApiResponse<Void> deleteMenu(@PathVariable UUID id) {
        log.info("Deleting menu: {}", id);
        menuService.deleteMenu(id);
        return ApiResponse.noContent();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get menu by ID")
    public ApiResponse<MenuDTO.Response> getMenu(@PathVariable UUID id) {
        return ApiResponse.success(menuService.getMenu(id));
    }

    @GetMapping
    @Operation(summary = "Get all menus for an application")
    public ApiResponse<List<MenuDTO.Response>> getMenusByApplication(
            @RequestParam(name = "app") String applicationCode) {
        return ApiResponse.success(menuService.getMenusByApplication(applicationCode));
    }

    @GetMapping("/tree")
    @Operation(summary = "Get menu tree for an application")
    public ApiResponse<List<MenuDTO.Response>> getMenuTree(
            @RequestParam(name = "app") String applicationCode) {
        return ApiResponse.success(menuService.getMenuTree(applicationCode));
    }

    // === Permission (Button) Assignments ===

    @PostMapping("/{menuId}/permissions")
    @Operation(summary = "Assign permissions (buttons) to menu")
    public ApiResponse<Void> assignPermissions(
            @PathVariable UUID menuId,
            @RequestBody MenuDTO.AssignPermissionsRequest request) {
        log.info("Assigning permissions to menu {}", menuId);
        menuService.assignPermissionsToMenu(menuId, request.getPermissions());
        return ApiResponse.success(null, "Permissions assigned to menu successfully");
    }

    @DeleteMapping("/{menuId}/permissions")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove permissions from menu")
    public ApiResponse<Void> removePermissions(
            @PathVariable UUID menuId,
            @RequestBody List<UUID> permissionIds) {
        log.info("Removing permissions from menu {}", menuId);
        menuService.removePermissionsFromMenu(menuId, permissionIds);
        return ApiResponse.noContent();
    }
}
