package com.enterprise.admin.service;

import com.enterprise.admin.dto.GroupDTO;
import com.enterprise.admin.entity.KcGroup;

import java.util.List;
import java.util.UUID;

/**
 * Service for Group management (Admin operations)
 */
public interface GroupService {

    KcGroup createGroup(GroupDTO.CreateRequest request);

    KcGroup updateGroup(UUID id, GroupDTO.UpdateRequest request);

    void deleteGroup(UUID id);

    GroupDTO.Response getGroup(UUID id);

    GroupDTO.Response getGroupByCode(String code);

    List<GroupDTO.Response> getAllGroups();

    List<GroupDTO.Response> getRootGroups();

    // Menu assignments
    void assignMenusToGroup(UUID groupId, List<UUID> menuIds);

    void removeMenusFromGroup(UUID groupId, List<UUID> menuIds);

    // Permission assignments
    void assignPermissionsToGroup(UUID groupId, List<UUID> permissionIds);

    void removePermissionsFromGroup(UUID groupId, List<UUID> permissionIds);
}
