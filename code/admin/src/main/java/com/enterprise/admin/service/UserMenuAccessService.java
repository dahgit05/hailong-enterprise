package com.enterprise.admin.service;

import com.enterprise.admin.dto.MenuAccessResponse;

import java.util.List;

/**
 * Service for handling user menu access
 * This is the main API for Frontend to get accessible menus with actions
 */
public interface UserMenuAccessService {

    /**
     * Get accessible menus for current user based on their groups
     * This is called by FE after login: GET /api/menus/me
     *
     * @param userGroups      List of group codes from JWT
     * @param applicationCode Application code (hrm, factory, equipment)
     * @return MenuAccessResponse with menu tree and actions
     */
    MenuAccessResponse getAccessibleMenus(List<String> userGroups, String applicationCode);

    /**
     * Get all permissions for current user based on their groups
     *
     * @param userGroups      List of group codes from JWT
     * @param applicationCode Application code
     * @return List of permission codes
     */
    List<String> getUserPermissions(List<String> userGroups, String applicationCode);
}
