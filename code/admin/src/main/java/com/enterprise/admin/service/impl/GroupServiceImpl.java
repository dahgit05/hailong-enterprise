package com.enterprise.admin.service.impl;

import com.enterprise.admin.dto.GroupDTO;
import com.enterprise.admin.entity.*;
import com.enterprise.admin.exception.DuplicateResourceException;
import com.enterprise.admin.exception.ResourceNotFoundException;
import com.enterprise.admin.repository.*;
import com.enterprise.admin.service.GroupService;
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
public class GroupServiceImpl implements GroupService {

    private final KcGroupRepository groupRepository;
    private final MenuRepository menuRepository;
    private final PermissionRepository permissionRepository;
    private final GroupMenuRepository groupMenuRepository;
    private final GroupPermissionRepository groupPermissionRepository;

    @Override
    public KcGroup createGroup(GroupDTO.CreateRequest request) {
        if (groupRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Group", "code", request.getCode());
        }

        KcGroup group = KcGroup.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .keycloakId(request.getKeycloakId())
                .isActive(true)
                .build();

        if (request.getParentId() != null) {
            KcGroup parent = groupRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent Group", request.getParentId()));
            group.setParent(parent);
        }

        return groupRepository.save(group);
    }

    @Override
    public KcGroup updateGroup(UUID id, GroupDTO.UpdateRequest request) {
        KcGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", id));

        group.setName(request.getName());
        group.setDescription(request.getDescription());
        if (request.getIsActive() != null) {
            group.setIsActive(request.getIsActive());
        }

        return groupRepository.save(group);
    }

    @Override
    public void deleteGroup(UUID id) {
        KcGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", id));

        // Soft delete - just deactivate
        group.setIsActive(false);
        groupRepository.save(group);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupDTO.Response getGroup(UUID id) {
        KcGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", id));
        return mapToResponse(group);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupDTO.Response getGroupByCode(String code) {
        KcGroup group = groupRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "code", code));
        return mapToResponse(group);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupDTO.Response> getAllGroups() {
        return groupRepository.findByIsActiveTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupDTO.Response> getRootGroups() {
        return groupRepository.findRootGroupsWithChildren().stream()
                .map(this::mapToResponseWithChildren)
                .collect(Collectors.toList());
    }

    @Override
    public void assignMenusToGroup(UUID groupId, List<UUID> menuIds) {
        KcGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", groupId));

        for (UUID menuId : menuIds) {
            if (!groupMenuRepository.existsByGroupIdAndMenuId(groupId, menuId)) {
                Menu menu = menuRepository.findById(menuId)
                        .orElseThrow(() -> new ResourceNotFoundException("Menu", menuId));

                GroupMenu groupMenu = GroupMenu.builder()
                        .group(group)
                        .menu(menu)
                        .isVisible(true)
                        .build();
                groupMenuRepository.save(groupMenu);
            }
        }
    }

    @Override
    public void removeMenusFromGroup(UUID groupId, List<UUID> menuIds) {
        for (UUID menuId : menuIds) {
            groupMenuRepository.deleteByGroupIdAndMenuId(groupId, menuId);
        }
    }

    @Override
    public void assignPermissionsToGroup(UUID groupId, List<UUID> permissionIds) {
        KcGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", groupId));

        for (UUID permissionId : permissionIds) {
            if (!groupPermissionRepository.existsByGroupIdAndPermissionId(groupId, permissionId)) {
                Permission permission = permissionRepository.findById(permissionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Permission", permissionId));

                GroupPermission groupPermission = GroupPermission.builder()
                        .group(group)
                        .permission(permission)
                        .isGranted(true)
                        .build();
                groupPermissionRepository.save(groupPermission);
            }
        }
    }

    @Override
    public void removePermissionsFromGroup(UUID groupId, List<UUID> permissionIds) {
        for (UUID permissionId : permissionIds) {
            groupPermissionRepository.deleteByGroupIdAndPermissionId(groupId, permissionId);
        }
    }

    private GroupDTO.Response mapToResponse(KcGroup group) {
        return GroupDTO.Response.builder()
                .id(group.getId())
                .keycloakId(group.getKeycloakId())
                .code(group.getCode())
                .name(group.getName())
                .description(group.getDescription())
                .parentId(group.getParent() != null ? group.getParent().getId() : null)
                .parentName(group.getParent() != null ? group.getParent().getName() : null)
                .isActive(group.getIsActive())
                .createdAt(group.getCreatedAt())
                .updatedAt(group.getUpdatedAt())
                .build();
    }

    private GroupDTO.Response mapToResponseWithChildren(KcGroup group) {
        GroupDTO.Response response = mapToResponse(group);
        if (group.getChildren() != null && !group.getChildren().isEmpty()) {
            response.setChildren(
                    group.getChildren().stream()
                            .filter(KcGroup::getIsActive)
                            .map(this::mapToResponseWithChildren)
                            .collect(Collectors.toList()));
        }
        return response;
    }
}
