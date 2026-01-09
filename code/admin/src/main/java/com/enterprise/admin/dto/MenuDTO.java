package com.enterprise.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTOs for Menu management
 */
public class MenuDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateRequest {
        @NotBlank(message = "Application code is required")
        @Size(max = 50)
        private String applicationCode;

        @NotBlank(message = "Menu code is required")
        @Size(max = 100)
        private String code;

        @NotBlank(message = "Menu name is required")
        @Size(max = 100)
        private String name;

        @Size(max = 255)
        private String path;

        @Size(max = 50)
        private String icon;

        private UUID parentId;
        private Integer sortOrder;
        private String menuType; // MENU, SUBMENU, ACTION
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {
        @NotBlank(message = "Menu name is required")
        @Size(max = 100)
        private String name;

        @Size(max = 255)
        private String path;

        @Size(max = 50)
        private String icon;

        private Integer sortOrder;
        private String menuType;
        private Boolean isActive;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private UUID id;
        private String applicationCode;
        private String code;
        private String name;
        private String path;
        private String icon;
        private UUID parentId;
        private String parentName;
        private Integer sortOrder;
        private String menuType;
        private Boolean isActive;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<Response> children;
        private List<PermissionDTO.Response> permissions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AssignPermissionsRequest {
        private List<MenuPermissionAssignment> permissions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MenuPermissionAssignment {
        private UUID permissionId;
        private String actionType; // view, create, edit, delete, export, import
        private Integer sortOrder;
    }
}
