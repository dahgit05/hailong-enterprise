package com.enterprise.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for getting menu tree by software code
 * POST /api/menus/by-software
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuBySoftwareRequest {

    /**
     * Application/Software code (e.g., "hrm", "factory", "equipment", "admin")
     */
    @NotBlank(message = "softwareCode is required")
    private String softwareCode;

    /**
     * Whether to filter menus by user's permissions
     * - true: Filter by user's groups (only return menus user has access to)
     * - false: Return all menus for the application (no permission filtering)
     */
    @NotNull(message = "requirePermission is required")
    private Boolean requirePermission;
}
