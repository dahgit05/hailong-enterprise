package com.enterprise.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * DTO for menu access response to Frontend
 * Contains menu tree with actions for each menu item
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuAccessResponse {

    private String applicationCode;
    private String applicationName;
    private List<MenuItemDTO> menus;
    private List<String> permissions; // All permission codes user has

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MenuItemDTO {
        private UUID id;
        private String code;
        private String name;
        private String path;
        private String icon;
        private Integer sortOrder;
        private String menuType;

        /**
         * Actions available on this menu
         * Key: action type (view, create, edit, delete, export, import, approve)
         * Value: true/false
         */
        @Builder.Default
        private Map<String, Boolean> actions = new HashMap<>();

        /**
         * Child menu items
         */
        private List<MenuItemDTO> children;
    }
}
