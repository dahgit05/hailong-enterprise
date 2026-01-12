package com.enterprise.admin.service.impl;

import com.enterprise.admin.dto.MenuAccessResponse;
import com.enterprise.admin.dto.MenuAccessResponse.MenuItemDTO;
import com.enterprise.admin.dto.MenuTreeItemResponse;
import com.enterprise.admin.entity.Menu;
import com.enterprise.admin.entity.MenuPermission;
import com.enterprise.admin.entity.Permission;
import com.enterprise.admin.repository.MenuPermissionRepository;
import com.enterprise.admin.repository.MenuRepository;
import com.enterprise.admin.repository.PermissionRepository;
import com.enterprise.admin.service.UserMenuAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of UserMenuAccessService
 * Core logic for runtime permission checking:
 * 
 * 1. User login → JWT contains groups
 * 2. FE calls /menus/me
 * 3. This service:
 * - Gets menus accessible by user's groups
 * - Gets permissions granted to user's groups
 * - For each menu, checks which actions (buttons) are enabled
 * 4. Returns menu tree with actions map
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserMenuAccessServiceImpl implements UserMenuAccessService {

        private final MenuRepository menuRepository;
        private final PermissionRepository permissionRepository;
        private final MenuPermissionRepository menuPermissionRepository;

        // Standard action types
        private static final List<String> STANDARD_ACTIONS = List.of(
                        "view", "create", "edit", "delete", "export", "import", "approve");

        @Override
        public MenuAccessResponse getAccessibleMenus(List<String> userGroups, String applicationCode) {
                log.debug("Getting accessible menus for groups: {} in app: {}", userGroups, applicationCode);

                if (userGroups == null || userGroups.isEmpty()) {
                        return MenuAccessResponse.builder()
                                        .applicationCode(applicationCode)
                                        .menus(Collections.emptyList())
                                        .permissions(Collections.emptyList())
                                        .build();
                }

                // 1. Get all menus accessible by user's groups for this application
                List<Menu> accessibleMenus = menuRepository.findAccessibleMenusByGroups(userGroups, applicationCode);
                log.debug("Found {} accessible menus", accessibleMenus.size());

                // 2. Get all permissions granted to user's groups for this application
                List<Permission> grantedPermissions = permissionRepository
                                .findGrantedPermissionsByGroupsAndApp(userGroups, applicationCode);
                Set<String> permissionCodes = grantedPermissions.stream()
                                .map(Permission::getCode)
                                .collect(Collectors.toSet());
                log.debug("Found {} granted permissions", permissionCodes.size());

                // 3. Get all menu-permission mappings for this application
                List<MenuPermission> menuPermissions = menuPermissionRepository.findByApplicationCode(applicationCode);
                Map<UUID, List<MenuPermission>> menuPermissionMap = menuPermissions.stream()
                                .collect(Collectors.groupingBy(mp -> mp.getMenu().getId()));

                // 4. Build menu tree with actions
                List<MenuItemDTO> menuTree = buildMenuTree(accessibleMenus, permissionCodes, menuPermissionMap);

                return MenuAccessResponse.builder()
                                .applicationCode(applicationCode)
                                .applicationName(getApplicationName(applicationCode))
                                .menus(menuTree)
                                .permissions(new ArrayList<>(permissionCodes))
                                .build();
        }

        @Override
        public List<String> getUserPermissions(List<String> userGroups, String applicationCode) {
                if (userGroups == null || userGroups.isEmpty()) {
                        return Collections.emptyList();
                }

                List<Permission> permissions = permissionRepository
                                .findGrantedPermissionsByGroupsAndApp(userGroups, applicationCode);

                return permissions.stream()
                                .map(Permission::getCode)
                                .collect(Collectors.toList());
        }

        /**
         * Build hierarchical menu tree with actions
         */
        private List<MenuItemDTO> buildMenuTree(
                        List<Menu> menus,
                        Set<String> permissionCodes,
                        Map<UUID, List<MenuPermission>> menuPermissionMap) {

                // Separate root and child menus
                Map<UUID, Menu> menuMap = menus.stream()
                                .collect(Collectors.toMap(Menu::getId, m -> m));

                List<Menu> rootMenus = menus.stream()
                                .filter(m -> m.getParent() == null)
                                .sorted(Comparator.comparing(Menu::getSortOrder,
                                                Comparator.nullsLast(Comparator.naturalOrder())))
                                .toList();

                return rootMenus.stream()
                                .map(menu -> buildMenuItemDTO(menu, permissionCodes, menuPermissionMap, menuMap))
                                .filter(Objects::nonNull)
                                .toList();
        }

        /**
         * Build single MenuItemDTO with actions and children
         */
        private MenuItemDTO buildMenuItemDTO(
                        Menu menu,
                        Set<String> permissionCodes,
                        Map<UUID, List<MenuPermission>> menuPermissionMap,
                        Map<UUID, Menu> allMenus) {

                // Calculate actions for this menu
                Map<String, Boolean> actions = calculateActions(menu.getId(), permissionCodes, menuPermissionMap);

                // Build children recursively
                List<MenuItemDTO> children = menu.getChildren().stream()
                                .filter(child -> allMenus.containsKey(child.getId())) // Only accessible children
                                .sorted(Comparator.comparing(Menu::getSortOrder,
                                                Comparator.nullsLast(Comparator.naturalOrder())))
                                .map(child -> buildMenuItemDTO(child, permissionCodes, menuPermissionMap, allMenus))
                                .filter(Objects::nonNull)
                                .toList();

                return MenuItemDTO.builder()
                                .id(menu.getId())
                                .code(menu.getCode())
                                .name(menu.getName())
                                .path(menu.getPath())
                                .icon(menu.getIcon())
                                .sortOrder(menu.getSortOrder())
                                .menuType(menu.getMenuType())
                                .actions(actions)
                                .children(children.isEmpty() ? null : children)
                                .build();
        }

        /**
         * Calculate which actions are available on a menu based on user's permissions
         * 
         * Logic:
         * 1. Get all menu-permission mappings for this menu
         * 2. For each action type (view, create, edit, delete, etc.)
         * 3. Check if user has the required permission
         * 4. Return map of action -> true/false
         */
        private Map<String, Boolean> calculateActions(
                        UUID menuId,
                        Set<String> permissionCodes,
                        Map<UUID, List<MenuPermission>> menuPermissionMap) {

                Map<String, Boolean> actions = new HashMap<>();

                // Initialize all actions to false
                STANDARD_ACTIONS.forEach(action -> actions.put(action, false));

                // Get menu-permission mappings for this menu
                List<MenuPermission> mappings = menuPermissionMap.getOrDefault(menuId, Collections.emptyList());

                for (MenuPermission mp : mappings) {
                        if (mp.getIsActive() && mp.getPermission() != null) {
                                String actionType = mp.getActionType();
                                String permissionCode = mp.getPermission().getCode();

                                // If user has this permission, enable the action
                                if (permissionCodes.contains(permissionCode)) {
                                        actions.put(actionType, true);
                                }
                        }
                }

                return actions;
        }

        private String getApplicationName(String code) {
                return switch (code.toLowerCase()) {
                        case "hrm" -> "Quản lý Nhân sự";
                        case "factory" -> "Quản lý Nhà máy";
                        case "equipment" -> "Quản lý Máy móc";
                        case "admin" -> "Quản trị Hệ thống";
                        default -> code;
                };
        }

        @Override
        public List<MenuTreeItemResponse> getMenuTreeBySoftware(
                        String softwareCode,
                        boolean requirePermission,
                        List<String> userGroups) {

                log.debug("Getting menu tree for software: {}, requirePermission: {}", softwareCode, requirePermission);

                List<Menu> menus;

                if (requirePermission) {
                        // Filter by user's groups - only return menus user has access to
                        if (userGroups == null || userGroups.isEmpty()) {
                                log.debug("No user groups provided, returning empty menu list");
                                return Collections.emptyList();
                        }
                        menus = menuRepository.findAccessibleMenusByGroups(userGroups, softwareCode);
                        log.debug("Found {} accessible menus for groups: {}", menus.size(), userGroups);
                } else {
                        // Return all menus for the application (no permission filtering)
                        menus = menuRepository.findAllRootMenusWithChildren(softwareCode);
                        log.debug("Found {} root menus (no permission filter)", menus.size());
                        // For no-permission case, we get root menus directly, so build tree from them
                        return menus.stream()
                                        .sorted(Comparator.comparing(Menu::getSortOrder,
                                                        Comparator.nullsLast(Comparator.naturalOrder())))
                                        .map(this::mapToTreeItemResponse)
                                        .toList();
                }

                // For permission-filtered case, need to build tree from flat list
                return buildTreeFromFlatList(menus);
        }

        /**
         * Build hierarchical tree from flat list of accessible menus
         */
        private List<MenuTreeItemResponse> buildTreeFromFlatList(List<Menu> menus) {
                // Create lookup map
                Map<UUID, Menu> menuMap = menus.stream()
                                .collect(Collectors.toMap(Menu::getId, m -> m));

                // Find root menus (parent is null or parent not in accessible list)
                List<Menu> rootMenus = menus.stream()
                                .filter(m -> m.getParent() == null || !menuMap.containsKey(m.getParent().getId()))
                                .sorted(Comparator.comparing(Menu::getSortOrder,
                                                Comparator.nullsLast(Comparator.naturalOrder())))
                                .toList();

                return rootMenus.stream()
                                .map(menu -> mapToTreeItemResponseWithAccessibleChildren(menu, menuMap))
                                .toList();
        }

        /**
         * Map menu to minimal response DTO with recursive children
         */
        private MenuTreeItemResponse mapToTreeItemResponse(Menu menu) {
                List<MenuTreeItemResponse> children = null;

                if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                        children = menu.getChildren().stream()
                                        .filter(Menu::getIsActive)
                                        .sorted(Comparator.comparing(Menu::getSortOrder,
                                                        Comparator.nullsLast(Comparator.naturalOrder())))
                                        .map(this::mapToTreeItemResponse)
                                        .toList();

                        if (children.isEmpty()) {
                                children = null;
                        }
                }

                return MenuTreeItemResponse.builder()
                                .id(menu.getId())
                                .code(menu.getCode())
                                .name(menu.getName())
                                .path(menu.getPath())
                                .icon(menu.getIcon())
                                .sortOrder(menu.getSortOrder())
                                .children(children)
                                .build();
        }

        /**
         * Map menu with only accessible children (for permission-filtered case)
         */
        private MenuTreeItemResponse mapToTreeItemResponseWithAccessibleChildren(Menu menu,
                        Map<UUID, Menu> accessibleMenus) {
                List<MenuTreeItemResponse> children = null;

                if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                        children = menu.getChildren().stream()
                                        .filter(child -> accessibleMenus.containsKey(child.getId()))
                                        .sorted(Comparator.comparing(Menu::getSortOrder,
                                                        Comparator.nullsLast(Comparator.naturalOrder())))
                                        .map(child -> mapToTreeItemResponseWithAccessibleChildren(child,
                                                        accessibleMenus))
                                        .toList();

                        if (children.isEmpty()) {
                                children = null;
                        }
                }

                return MenuTreeItemResponse.builder()
                                .id(menu.getId())
                                .code(menu.getCode())
                                .name(menu.getName())
                                .path(menu.getPath())
                                .icon(menu.getIcon())
                                .sortOrder(menu.getSortOrder())
                                .children(children)
                                .build();
        }
}
