-- =====================================================
-- V1__create_schema.sql
-- Admin Service Database Schema
-- Flyway Migration: Creates all tables for permission system
-- =====================================================

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =====================================================
-- 1. KC_GROUP - Synced from Keycloak
-- Group quyết định "THẤY GÌ" và "LÀM ĐƯỢC GÌ"
-- =====================================================
CREATE TABLE kc_group (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    keycloak_id VARCHAR(255) UNIQUE NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    parent_id UUID REFERENCES kc_group(id) ON DELETE SET NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_kc_group_code ON kc_group(code);
CREATE INDEX idx_kc_group_keycloak_id ON kc_group(keycloak_id);
CREATE INDEX idx_kc_group_parent_id ON kc_group(parent_id);
CREATE INDEX idx_kc_group_active ON kc_group(is_active);

COMMENT ON TABLE kc_group IS 'Groups synced from Keycloak - determines menu visibility and permissions';
COMMENT ON COLUMN kc_group.keycloak_id IS 'ID from Keycloak for sync';
COMMENT ON COLUMN kc_group.code IS 'Unique code like HR, WAREHOUSE, ADMIN';

-- =====================================================
-- 2. MENU - Navigation items
-- Group → Menu: defines what user SEES
-- =====================================================
CREATE TABLE menu (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    application_code VARCHAR(50) NOT NULL,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    path VARCHAR(255),
    icon VARCHAR(50),
    parent_id UUID REFERENCES menu(id) ON DELETE SET NULL,
    sort_order INTEGER DEFAULT 0,
    menu_type VARCHAR(20) DEFAULT 'MENU',
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT uk_menu_app_code UNIQUE (application_code, code)
);

CREATE INDEX idx_menu_application ON menu(application_code);
CREATE INDEX idx_menu_parent ON menu(parent_id);
CREATE INDEX idx_menu_sort ON menu(sort_order);
CREATE INDEX idx_menu_active ON menu(is_active);

COMMENT ON TABLE menu IS 'Menu items for each application (hrm, factory, equipment)';
COMMENT ON COLUMN menu.application_code IS 'Application: hrm, factory, equipment, admin';
COMMENT ON COLUMN menu.menu_type IS 'Type: MENU, SUBMENU, ACTION';

-- =====================================================
-- 3. PERMISSION - Action-level authorization
-- Permission quyết định "LÀM ĐƯỢC GÌ"
-- =====================================================
CREATE TABLE permission (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    application_code VARCHAR(50) NOT NULL,
    permission_type VARCHAR(50),
    resource VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_permission_code ON permission(code);
CREATE INDEX idx_permission_application ON permission(application_code);
CREATE INDEX idx_permission_type ON permission(permission_type);
CREATE INDEX idx_permission_resource ON permission(resource);
CREATE INDEX idx_permission_active ON permission(is_active);

COMMENT ON TABLE permission IS 'Action permissions like VIEW, CREATE, EDIT, DELETE';
COMMENT ON COLUMN permission.permission_type IS 'Type: VIEW, CREATE, EDIT, DELETE, EXPORT, IMPORT, APPROVE';
COMMENT ON COLUMN permission.resource IS 'Resource: employee, warehouse_receipt, etc.';

-- =====================================================
-- 4. GROUP_MENU - Many-to-Many: Group → Menu
-- Defines which menus a group can SEE
-- =====================================================
CREATE TABLE group_menu (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    group_id UUID NOT NULL REFERENCES kc_group(id) ON DELETE CASCADE,
    menu_id UUID NOT NULL REFERENCES menu(id) ON DELETE CASCADE,
    is_visible BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    
    CONSTRAINT uk_group_menu UNIQUE (group_id, menu_id)
);

CREATE INDEX idx_group_menu_group ON group_menu(group_id);
CREATE INDEX idx_group_menu_menu ON group_menu(menu_id);

COMMENT ON TABLE group_menu IS 'Group to Menu mapping - determines menu visibility';

-- =====================================================
-- 5. GROUP_PERMISSION - Many-to-Many: Group → Permission
-- Defines which actions a group can PERFORM
-- =====================================================
CREATE TABLE group_permission (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    group_id UUID NOT NULL REFERENCES kc_group(id) ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES permission(id) ON DELETE CASCADE,
    is_granted BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    
    CONSTRAINT uk_group_permission UNIQUE (group_id, permission_id)
);

CREATE INDEX idx_group_permission_group ON group_permission(group_id);
CREATE INDEX idx_group_permission_permission ON group_permission(permission_id);
CREATE INDEX idx_group_permission_granted ON group_permission(is_granted);

COMMENT ON TABLE group_permission IS 'Group to Permission mapping - determines action capabilities';

-- =====================================================
-- 6. MENU_PERMISSION - Many-to-Many: Menu → Permission
-- Maps which buttons/actions are available on each menu
-- THIS IS CRUCIAL for FE to know which buttons to show
-- =====================================================
CREATE TABLE menu_permission (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    menu_id UUID NOT NULL REFERENCES menu(id) ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES permission(id) ON DELETE CASCADE,
    action_type VARCHAR(50),
    sort_order INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT uk_menu_permission UNIQUE (menu_id, permission_id)
);

CREATE INDEX idx_menu_permission_menu ON menu_permission(menu_id);
CREATE INDEX idx_menu_permission_permission ON menu_permission(permission_id);
CREATE INDEX idx_menu_permission_action ON menu_permission(action_type);

COMMENT ON TABLE menu_permission IS 'Menu to Permission mapping - defines buttons per menu';
COMMENT ON COLUMN menu_permission.action_type IS 'Action: view, create, edit, delete, export, import, approve';

-- =====================================================
-- Trigger for updated_at
-- =====================================================
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_kc_group_updated_at BEFORE UPDATE ON kc_group
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_menu_updated_at BEFORE UPDATE ON menu
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_permission_updated_at BEFORE UPDATE ON permission
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
