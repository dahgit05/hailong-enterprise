package com.enterprise.admin.service.impl;

import com.enterprise.admin.dto.MenuDTO;
import com.enterprise.admin.dto.PermissionDTO;
import com.enterprise.admin.entity.Menu;
import com.enterprise.admin.entity.MenuPermission;
import com.enterprise.admin.entity.Permission;
import com.enterprise.admin.exception.DuplicateResourceException;
import com.enterprise.admin.exception.ResourceNotFoundException;
import com.enterprise.admin.repository.MenuPermissionRepository;
import com.enterprise.admin.repository.MenuRepository;
import com.enterprise.admin.repository.PermissionRepository;
import com.enterprise.admin.service.MenuService;
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
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final PermissionRepository permissionRepository;
    private final MenuPermissionRepository menuPermissionRepository;

    @Override
    public Menu createMenu(MenuDTO.CreateRequest request) {
        if (menuRepository.existsByApplicationCodeAndCode(request.getApplicationCode(), request.getCode())) {
            throw new DuplicateResourceException("Menu", "code", request.getCode());
        }

        Menu menu = Menu.builder()
                .applicationCode(request.getApplicationCode())
                .code(request.getCode())
                .name(request.getName())
                .path(request.getPath())
                .icon(request.getIcon())
                .sortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0)
                .menuType(request.getMenuType() != null ? request.getMenuType() : "MENU")
                .isActive(true)
                .build();

        if (request.getParentId() != null) {
            Menu parent = menuRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent Menu", request.getParentId()));
            menu.setParent(parent);
        }

        return menuRepository.save(menu);
    }

    @Override
    public Menu updateMenu(UUID id, MenuDTO.UpdateRequest request) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", id));

        menu.setName(request.getName());
        menu.setPath(request.getPath());
        menu.setIcon(request.getIcon());
        if (request.getSortOrder() != null)
            menu.setSortOrder(request.getSortOrder());
        if (request.getMenuType() != null)
            menu.setMenuType(request.getMenuType());
        if (request.getIsActive() != null)
            menu.setIsActive(request.getIsActive());

        return menuRepository.save(menu);
    }

    @Override
    public void deleteMenu(UUID id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", id));
        menu.setIsActive(false);
        menuRepository.save(menu);
    }

    @Override
    @Transactional(readOnly = true)
    public MenuDTO.Response getMenu(UUID id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", id));
        return mapToResponse(menu);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO.Response> getMenusByApplication(String applicationCode) {
        return menuRepository.findByApplicationCodeAndIsActiveTrueOrderBySortOrderAsc(applicationCode)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO.Response> getMenuTree(String applicationCode) {
        return menuRepository.findByApplicationCodeAndParentIsNullAndIsActiveTrueOrderBySortOrderAsc(applicationCode)
                .stream()
                .map(this::mapToResponseWithChildren)
                .collect(Collectors.toList());
    }

    @Override
    public void assignPermissionsToMenu(UUID menuId, List<MenuDTO.MenuPermissionAssignment> assignments) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu", menuId));

        for (MenuDTO.MenuPermissionAssignment assignment : assignments) {
            if (!menuPermissionRepository.existsByMenuIdAndPermissionId(menuId, assignment.getPermissionId())) {
                Permission permission = permissionRepository.findById(assignment.getPermissionId())
                        .orElseThrow(() -> new ResourceNotFoundException("Permission", assignment.getPermissionId()));

                MenuPermission mp = MenuPermission.builder()
                        .menu(menu)
                        .permission(permission)
                        .actionType(assignment.getActionType())
                        .sortOrder(assignment.getSortOrder() != null ? assignment.getSortOrder() : 0)
                        .isActive(true)
                        .build();
                menuPermissionRepository.save(mp);
            }
        }
    }

    @Override
    public void removePermissionsFromMenu(UUID menuId, List<UUID> permissionIds) {
        for (UUID permissionId : permissionIds) {
            menuPermissionRepository.deleteByMenuIdAndPermissionId(menuId, permissionId);
        }
    }

    private MenuDTO.Response mapToResponse(Menu menu) {
        List<PermissionDTO.Response> permissions = menuPermissionRepository.findByMenuIdAndIsActiveTrue(menu.getId())
                .stream()
                .map(mp -> PermissionDTO.Response.builder()
                        .id(mp.getPermission().getId())
                        .code(mp.getPermission().getCode())
                        .name(mp.getPermission().getName())
                        .permissionType(mp.getActionType())
                        .build())
                .collect(Collectors.toList());

        return MenuDTO.Response.builder()
                .id(menu.getId())
                .applicationCode(menu.getApplicationCode())
                .code(menu.getCode())
                .name(menu.getName())
                .path(menu.getPath())
                .icon(menu.getIcon())
                .parentId(menu.getParent() != null ? menu.getParent().getId() : null)
                .parentName(menu.getParent() != null ? menu.getParent().getName() : null)
                .sortOrder(menu.getSortOrder())
                .menuType(menu.getMenuType())
                .isActive(menu.getIsActive())
                .createdAt(menu.getCreatedAt())
                .updatedAt(menu.getUpdatedAt())
                .permissions(permissions)
                .build();
    }

    private MenuDTO.Response mapToResponseWithChildren(Menu menu) {
        MenuDTO.Response response = mapToResponse(menu);
        if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
            response.setChildren(
                    menu.getChildren().stream()
                            .filter(Menu::getIsActive)
                            .map(this::mapToResponseWithChildren)
                            .collect(Collectors.toList()));
        }
        return response;
    }
}
