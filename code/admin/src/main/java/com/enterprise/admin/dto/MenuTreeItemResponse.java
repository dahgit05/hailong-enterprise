package com.enterprise.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Minimal response DTO for menu tree items
 * Optimized for performance - only includes essential fields
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuTreeItemResponse {

    private UUID id;

    private String code;

    private String name;

    private String path;

    private String icon;

    private Integer sortOrder;

    /**
     * Child menu items (recursive structure)
     * Null if no children
     */
    private List<MenuTreeItemResponse> children;
}
