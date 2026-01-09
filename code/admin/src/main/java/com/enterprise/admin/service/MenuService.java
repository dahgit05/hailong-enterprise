package com.enterprise.admin.service;

import com.enterprise.admin.dto.MenuDTO;
import com.enterprise.admin.entity.Menu;

import java.util.List;
import java.util.UUID;

/**
 * Service for Menu management (Admin operations)
 */
public interface MenuService {

    Menu createMenu(MenuDTO.CreateRequest request);

    Menu updateMenu(UUID id, MenuDTO.UpdateRequest request);

    void deleteMenu(UUID id);

    MenuDTO.Response getMenu(UUID id);

    List<MenuDTO.Response> getMenusByApplication(String applicationCode);

    List<MenuDTO.Response> getMenuTree(String applicationCode);

    // Permission assignments for buttons
    void assignPermissionsToMenu(UUID menuId, List<MenuDTO.MenuPermissionAssignment> assignments);

    void removePermissionsFromMenu(UUID menuId, List<UUID> permissionIds);
}
