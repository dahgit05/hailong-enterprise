package com.enterprise.admin.controller;

import com.enterprise.admin.config.KeycloakGrantedAuthoritiesConverter;
import com.enterprise.admin.dto.ApiResponse;
import com.enterprise.admin.dto.MenuAccessResponse;
import com.enterprise.admin.dto.MenuBySoftwareRequest;
import com.enterprise.admin.dto.MenuTreeItemResponse;
import com.enterprise.admin.service.UserMenuAccessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Controller for user menu access
 * This is the main API for Frontend to get accessible menus after login
 * 
 * Best Practice:
 * - No try-catch (handled by GlobalExceptionHandler)
 * - ApiResponse wrapper for consistent response
 * - No ResponseEntity, just return object directly
 * 
 * Flow:
 * 1. User login → Keycloak returns JWT with groups
 * 2. FE calls GET /api/menus/me?app=hrm
 * 3. This controller extracts groups from JWT
 * 4. Service returns menu tree with actions
 * 5. FE renders menu and enables/disables buttons based on actions
 */
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Menu Access", description = "APIs for frontend to get user's accessible menus")
public class UserAccessController {

    private final UserMenuAccessService userMenuAccessService;

    /**
     * Get accessible menus for current user
     * 
     * Response format:
     * {
     * "status": 200,
     * "message": "Success",
     * "data": {
     * "applicationCode": "hrm",
     * "applicationName": "Quản lý Nhân sự",
     * "menus": [
     * {
     * "code": "EMPLOYEE",
     * "name": "Hồ sơ nhân viên",
     * "path": "/employee",
     * "actions": {
     * "view": true,
     * "create": false,
     * "edit": true,
     * "delete": false
     * },
     * "children": [...]
     * }
     * ],
     * "permissions": ["EMPLOYEE_VIEW", "EMPLOYEE_EDIT", ...]
     * }
     * }
     */
    @GetMapping("/me")
    @Operation(summary = "Get accessible menus for current user", description = "Returns menu tree with actions based on user's groups. "
            +
            "FE should call this after login to render navigation.")
    public ApiResponse<MenuAccessResponse> getMyMenus(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "app", required = true) String applicationCode) {
        List<String> groups = KeycloakGrantedAuthoritiesConverter.getGroupCodes(jwt);
        log.info("User {} requesting menus for app: {}, groups: {}",
                jwt.getSubject(), applicationCode, groups);

        MenuAccessResponse response = userMenuAccessService.getAccessibleMenus(groups, applicationCode);
        return ApiResponse.success(response);
    }

    /**
     * Get all permissions for current user in an application
     * Useful for FE to cache and check permissions without menu context
     */
    @GetMapping("/permissions")
    @Operation(summary = "Get all permissions for current user", description = "Returns list of permission codes granted to user in the specified application")
    public ApiResponse<List<String>> getMyPermissions(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "app", required = true) String applicationCode) {
        List<String> groups = KeycloakGrantedAuthoritiesConverter.getGroupCodes(jwt);
        List<String> permissions = userMenuAccessService.getUserPermissions(groups, applicationCode);
        return ApiResponse.success(permissions);
    }

    /**
     * Check if current user has a specific permission
     */
    @GetMapping("/check")
    @Operation(summary = "Check if user has specific permission", description = "Quick check for a single permission, useful for dynamic UI elements")
    public ApiResponse<Boolean> checkPermission(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(name = "app", required = true) String applicationCode,
            @RequestParam(name = "permission", required = true) String permissionCode) {
        List<String> groups = KeycloakGrantedAuthoritiesConverter.getGroupCodes(jwt);
        List<String> permissions = userMenuAccessService.getUserPermissions(groups, applicationCode);
        boolean hasPermission = permissions.contains(permissionCode);
        return ApiResponse.success(hasPermission);
    }

    /**
     * Get menu tree by software code with optional permission filtering
     *
     * Request body:
     * {
     * "softwareCode": "factory",
     * "requirePermission": true
     * }
     *
     * Response:
     * {
     * "status": 200,
     * "message": "Success",
     * "data": [
     * {
     * "id": "uuid",
     * "code": "WAREHOUSE",
     * "name": "Kho vật tư",
     * "path": "/warehouse",
     * "icon": "warehouse",
     * "sortOrder": 1,
     * "children": [...]
     * }
     * ]
     * }
     */
    @PostMapping("/by-software")
    @Operation(summary = "Get menu tree by software code", description = "Returns hierarchical menu tree for an application. "
            +
            "If requirePermission=true, filters by current user's groups. " +
            "If requirePermission=false, returns all menus without permission filtering.")
    public ApiResponse<List<MenuTreeItemResponse>> getMenuBySoftware(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody MenuBySoftwareRequest request) {

        List<String> groups = Boolean.TRUE.equals(request.getRequirePermission())
                ? KeycloakGrantedAuthoritiesConverter.getGroupCodes(jwt)
                : Collections.emptyList();

        log.info("Getting menu tree for software: {}, requirePermission: {}, groups: {}",
                request.getSoftwareCode(), request.getRequirePermission(), groups);

        List<MenuTreeItemResponse> menus = userMenuAccessService.getMenuTreeBySoftware(
                request.getSoftwareCode(),
                request.getRequirePermission(),
                groups);

        return ApiResponse.success(menus);
    }
}
