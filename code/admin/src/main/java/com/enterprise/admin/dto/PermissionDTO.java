package com.enterprise.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTOs for Permission management
 */
public class PermissionDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateRequest {
        @NotBlank(message = "Permission code is required")
        @Size(max = 100)
        private String code;

        @NotBlank(message = "Permission name is required")
        @Size(max = 100)
        private String name;

        @Size(max = 500)
        private String description;

        @NotBlank(message = "Application code is required")
        @Size(max = 50)
        private String applicationCode;

        @Size(max = 50)
        private String permissionType; // VIEW, CREATE, EDIT, DELETE, EXPORT, IMPORT, APPROVE

        @Size(max = 100)
        private String resource; // employee, warehouse_receipt, etc.
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {
        @NotBlank(message = "Permission name is required")
        @Size(max = 100)
        private String name;

        @Size(max = 500)
        private String description;

        @Size(max = 50)
        private String permissionType;

        @Size(max = 100)
        private String resource;

        private Boolean isActive;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private UUID id;
        private String code;
        private String name;
        private String description;
        private String applicationCode;
        private String permissionType;
        private String resource;
        private Boolean isActive;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
