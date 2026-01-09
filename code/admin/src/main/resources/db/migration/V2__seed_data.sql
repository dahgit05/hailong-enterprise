-- =====================================================
-- V2__seed_data.sql
-- Seed initial data: Groups, Menus, Permissions
-- =====================================================

-- =====================================================
-- 1. INSERT GROUPS (synced from Keycloak structure)
-- =====================================================
INSERT INTO kc_group (id, keycloak_id, code, name, description) VALUES
    -- Super Admin
    ('11111111-1111-1111-1111-111111111111', 'kc-admin-001', 'SUPER_ADMIN', 'Super Admin', 'Full system access'),
    
    -- HRM Groups
    ('22222222-2222-2222-2222-222222222221', 'kc-hr-001', 'HR', 'Phòng Nhân sự', 'Human Resources Department'),
    ('22222222-2222-2222-2222-222222222222', 'kc-hr-002', 'HR_MANAGER', 'Quản lý Nhân sự', 'HR Managers'),
    ('22222222-2222-2222-2222-222222222223', 'kc-hr-003', 'PAYROLL', 'Bộ phận Lương', 'Payroll Team'),
    
    -- Factory Groups
    ('33333333-3333-3333-3333-333333333331', 'kc-fac-001', 'WAREHOUSE', 'Bộ phận Kho', 'Warehouse Staff'),
    ('33333333-3333-3333-3333-333333333332', 'kc-fac-002', 'PRODUCTION', 'Bộ phận Sản xuất', 'Production Team'),
    
    -- Equipment Groups
    ('44444444-4444-4444-4444-444444444441', 'kc-eqp-001', 'TECHNICIAN', 'Kỹ thuật viên', 'Equipment Technicians'),
    ('44444444-4444-4444-4444-444444444442', 'kc-eqp-002', 'EQUIPMENT_MANAGER', 'Quản lý Thiết bị', 'Equipment Managers');

-- =====================================================
-- 2. INSERT MENUS FOR HRM APPLICATION
-- =====================================================
-- Root menus
INSERT INTO menu (id, application_code, code, name, path, icon, sort_order, menu_type) VALUES
    ('aaaa1111-0000-0000-0000-000000000001', 'hrm', 'DASHBOARD', 'Dashboard', '/dashboard', 'dashboard', 1, 'MENU'),
    ('aaaa1111-0000-0000-0000-000000000002', 'hrm', 'EMPLOYEE', 'Hồ sơ nhân viên', '/employees', 'people', 2, 'MENU'),
    ('aaaa1111-0000-0000-0000-000000000003', 'hrm', 'CONTRACT', 'Hợp đồng', '/contracts', 'description', 3, 'MENU'),
    ('aaaa1111-0000-0000-0000-000000000004', 'hrm', 'ATTENDANCE', 'Chấm công', '/attendance', 'schedule', 4, 'MENU'),
    ('aaaa1111-0000-0000-0000-000000000005', 'hrm', 'PAYROLL', 'Bảng lương', '/payroll', 'payments', 5, 'MENU'),
    ('aaaa1111-0000-0000-0000-000000000006', 'hrm', 'REPORT', 'Báo cáo', '/reports', 'assessment', 6, 'MENU');

-- =====================================================
-- 3. INSERT MENUS FOR FACTORY APPLICATION
-- =====================================================
INSERT INTO menu (id, application_code, code, name, path, icon, sort_order, menu_type) VALUES
    ('bbbb1111-0000-0000-0000-000000000001', 'factory', 'DASHBOARD', 'Dashboard', '/dashboard', 'dashboard', 1, 'MENU'),
    ('bbbb1111-0000-0000-0000-000000000002', 'factory', 'WAREHOUSE_RECEIPT', 'Phiếu nhập kho', '/warehouse/receipts', 'archive', 2, 'MENU'),
    ('bbbb1111-0000-0000-0000-000000000003', 'factory', 'WAREHOUSE_EXPORT', 'Phiếu xuất kho', '/warehouse/exports', 'unarchive', 3, 'MENU'),
    ('bbbb1111-0000-0000-0000-000000000004', 'factory', 'INVENTORY', 'Tồn kho', '/inventory', 'inventory', 4, 'MENU'),
    ('bbbb1111-0000-0000-0000-000000000005', 'factory', 'PRODUCTION_ORDER', 'Lệnh sản xuất', '/production-orders', 'factory', 5, 'MENU'),
    ('bbbb1111-0000-0000-0000-000000000006', 'factory', 'REPORT', 'Báo cáo', '/reports', 'assessment', 6, 'MENU');

-- =====================================================
-- 4. INSERT PERMISSIONS FOR HRM
-- =====================================================
INSERT INTO permission (id, code, name, description, application_code, permission_type, resource) VALUES
    -- Employee permissions
    ('dddd1111-0000-0000-0000-000000000001', 'EMPLOYEE_VIEW', 'Xem nhân viên', 'View employee records', 'hrm', 'VIEW', 'employee'),
    ('dddd1111-0000-0000-0000-000000000002', 'EMPLOYEE_CREATE', 'Thêm nhân viên', 'Create new employees', 'hrm', 'CREATE', 'employee'),
    ('dddd1111-0000-0000-0000-000000000003', 'EMPLOYEE_EDIT', 'Sửa nhân viên', 'Edit employee records', 'hrm', 'EDIT', 'employee'),
    ('dddd1111-0000-0000-0000-000000000004', 'EMPLOYEE_DELETE', 'Xóa nhân viên', 'Delete employees', 'hrm', 'DELETE', 'employee'),
    ('dddd1111-0000-0000-0000-000000000005', 'EMPLOYEE_EXPORT', 'Xuất Excel nhân viên', 'Export employee data', 'hrm', 'EXPORT', 'employee'),
    
    -- Contract permissions
    ('dddd2222-0000-0000-0000-000000000001', 'CONTRACT_VIEW', 'Xem hợp đồng', 'View contracts', 'hrm', 'VIEW', 'contract'),
    ('dddd2222-0000-0000-0000-000000000002', 'CONTRACT_CREATE', 'Tạo hợp đồng', 'Create contracts', 'hrm', 'CREATE', 'contract'),
    ('dddd2222-0000-0000-0000-000000000003', 'CONTRACT_EDIT', 'Sửa hợp đồng', 'Edit contracts', 'hrm', 'EDIT', 'contract'),
    ('dddd2222-0000-0000-0000-000000000004', 'CONTRACT_DELETE', 'Xóa hợp đồng', 'Delete contracts', 'hrm', 'DELETE', 'contract'),
    
    -- Payroll permissions
    ('dddd3333-0000-0000-0000-000000000001', 'PAYROLL_VIEW', 'Xem bảng lương', 'View payroll', 'hrm', 'VIEW', 'payroll'),
    ('dddd3333-0000-0000-0000-000000000002', 'PAYROLL_CREATE', 'Tạo bảng lương', 'Create payroll', 'hrm', 'CREATE', 'payroll'),
    ('dddd3333-0000-0000-0000-000000000003', 'PAYROLL_APPROVE', 'Duyệt bảng lương', 'Approve payroll', 'hrm', 'APPROVE', 'payroll');

-- =====================================================
-- 5. INSERT PERMISSIONS FOR FACTORY
-- =====================================================
INSERT INTO permission (id, code, name, description, application_code, permission_type, resource) VALUES
    -- Warehouse Receipt
    ('eeee1111-0000-0000-0000-000000000001', 'RECEIPT_VIEW', 'Xem phiếu nhập', 'View receipts', 'factory', 'VIEW', 'receipt'),
    ('eeee1111-0000-0000-0000-000000000002', 'RECEIPT_CREATE', 'Tạo phiếu nhập', 'Create receipts', 'factory', 'CREATE', 'receipt'),
    ('eeee1111-0000-0000-0000-000000000003', 'RECEIPT_EDIT', 'Sửa phiếu nhập', 'Edit receipts', 'factory', 'EDIT', 'receipt'),
    ('eeee1111-0000-0000-0000-000000000004', 'RECEIPT_DELETE', 'Xóa phiếu nhập', 'Delete receipts', 'factory', 'DELETE', 'receipt'),
    
    -- Warehouse Export
    ('eeee2222-0000-0000-0000-000000000001', 'EXPORT_VIEW', 'Xem phiếu xuất', 'View exports', 'factory', 'VIEW', 'export'),
    ('eeee2222-0000-0000-0000-000000000002', 'EXPORT_CREATE', 'Tạo phiếu xuất', 'Create exports', 'factory', 'CREATE', 'export'),
    ('eeee2222-0000-0000-0000-000000000003', 'EXPORT_EDIT', 'Sửa phiếu xuất', 'Edit exports', 'factory', 'EDIT', 'export'),
    
    -- Inventory
    ('eeee3333-0000-0000-0000-000000000001', 'INVENTORY_VIEW', 'Xem tồn kho', 'View inventory', 'factory', 'VIEW', 'inventory'),
    ('eeee3333-0000-0000-0000-000000000002', 'INVENTORY_EXPORT', 'Xuất báo cáo tồn kho', 'Export inventory', 'factory', 'EXPORT', 'inventory');

-- =====================================================
-- 6. ASSIGN MENUS TO GROUPS (GROUP_MENU)
-- =====================================================
-- HR Group can see HRM menus
INSERT INTO group_menu (group_id, menu_id, is_visible, created_by) VALUES
    ('22222222-2222-2222-2222-222222222221', 'aaaa1111-0000-0000-0000-000000000001', true, 'system'),
    ('22222222-2222-2222-2222-222222222221', 'aaaa1111-0000-0000-0000-000000000002', true, 'system'),
    ('22222222-2222-2222-2222-222222222221', 'aaaa1111-0000-0000-0000-000000000003', true, 'system'),
    ('22222222-2222-2222-2222-222222222221', 'aaaa1111-0000-0000-0000-000000000006', true, 'system');

-- HR Manager can see all HRM menus
INSERT INTO group_menu (group_id, menu_id, is_visible, created_by) VALUES
    ('22222222-2222-2222-2222-222222222222', 'aaaa1111-0000-0000-0000-000000000001', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'aaaa1111-0000-0000-0000-000000000002', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'aaaa1111-0000-0000-0000-000000000003', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'aaaa1111-0000-0000-0000-000000000004', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'aaaa1111-0000-0000-0000-000000000005', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'aaaa1111-0000-0000-0000-000000000006', true, 'system');

-- Warehouse Group can see Factory menus
INSERT INTO group_menu (group_id, menu_id, is_visible, created_by) VALUES
    ('33333333-3333-3333-3333-333333333331', 'bbbb1111-0000-0000-0000-000000000001', true, 'system'),
    ('33333333-3333-3333-3333-333333333331', 'bbbb1111-0000-0000-0000-000000000002', true, 'system'),
    ('33333333-3333-3333-3333-333333333331', 'bbbb1111-0000-0000-0000-000000000003', true, 'system'),
    ('33333333-3333-3333-3333-333333333331', 'bbbb1111-0000-0000-0000-000000000004', true, 'system');

-- =====================================================
-- 7. ASSIGN PERMISSIONS TO GROUPS (GROUP_PERMISSION)
-- =====================================================
-- HR Group: view + create employees
INSERT INTO group_permission (group_id, permission_id, is_granted, created_by) VALUES
    ('22222222-2222-2222-2222-222222222221', 'dddd1111-0000-0000-0000-000000000001', true, 'system'),
    ('22222222-2222-2222-2222-222222222221', 'dddd1111-0000-0000-0000-000000000002', true, 'system'),
    ('22222222-2222-2222-2222-222222222221', 'dddd1111-0000-0000-0000-000000000003', true, 'system'),
    ('22222222-2222-2222-2222-222222222221', 'dddd2222-0000-0000-0000-000000000001', true, 'system');

-- HR Manager: full employee + contract permissions
INSERT INTO group_permission (group_id, permission_id, is_granted, created_by) VALUES
    ('22222222-2222-2222-2222-222222222222', 'dddd1111-0000-0000-0000-000000000001', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'dddd1111-0000-0000-0000-000000000002', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'dddd1111-0000-0000-0000-000000000003', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'dddd1111-0000-0000-0000-000000000004', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'dddd1111-0000-0000-0000-000000000005', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'dddd2222-0000-0000-0000-000000000001', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'dddd2222-0000-0000-0000-000000000002', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'dddd2222-0000-0000-0000-000000000003', true, 'system'),
    ('22222222-2222-2222-2222-222222222222', 'dddd2222-0000-0000-0000-000000000004', true, 'system');

-- Warehouse: view + create receipts
INSERT INTO group_permission (group_id, permission_id, is_granted, created_by) VALUES
    ('33333333-3333-3333-3333-333333333331', 'eeee1111-0000-0000-0000-000000000001', true, 'system'),
    ('33333333-3333-3333-3333-333333333331', 'eeee1111-0000-0000-0000-000000000002', true, 'system'),
    ('33333333-3333-3333-3333-333333333331', 'eeee2222-0000-0000-0000-000000000001', true, 'system'),
    ('33333333-3333-3333-3333-333333333331', 'eeee2222-0000-0000-0000-000000000002', true, 'system'),
    ('33333333-3333-3333-3333-333333333331', 'eeee3333-0000-0000-0000-000000000001', true, 'system');

-- =====================================================
-- 8. ASSIGN PERMISSIONS TO MENUS (MENU_PERMISSION)
-- This maps buttons to menus
-- =====================================================
-- Employee menu buttons
INSERT INTO menu_permission (menu_id, permission_id, action_type, sort_order) VALUES
    ('aaaa1111-0000-0000-0000-000000000002', 'dddd1111-0000-0000-0000-000000000001', 'view', 1),
    ('aaaa1111-0000-0000-0000-000000000002', 'dddd1111-0000-0000-0000-000000000002', 'create', 2),
    ('aaaa1111-0000-0000-0000-000000000002', 'dddd1111-0000-0000-0000-000000000003', 'edit', 3),
    ('aaaa1111-0000-0000-0000-000000000002', 'dddd1111-0000-0000-0000-000000000004', 'delete', 4),
    ('aaaa1111-0000-0000-0000-000000000002', 'dddd1111-0000-0000-0000-000000000005', 'export', 5);

-- Contract menu buttons
INSERT INTO menu_permission (menu_id, permission_id, action_type, sort_order) VALUES
    ('aaaa1111-0000-0000-0000-000000000003', 'dddd2222-0000-0000-0000-000000000001', 'view', 1),
    ('aaaa1111-0000-0000-0000-000000000003', 'dddd2222-0000-0000-0000-000000000002', 'create', 2),
    ('aaaa1111-0000-0000-0000-000000000003', 'dddd2222-0000-0000-0000-000000000003', 'edit', 3),
    ('aaaa1111-0000-0000-0000-000000000003', 'dddd2222-0000-0000-0000-000000000004', 'delete', 4);

-- Payroll menu buttons
INSERT INTO menu_permission (menu_id, permission_id, action_type, sort_order) VALUES
    ('aaaa1111-0000-0000-0000-000000000005', 'dddd3333-0000-0000-0000-000000000001', 'view', 1),
    ('aaaa1111-0000-0000-0000-000000000005', 'dddd3333-0000-0000-0000-000000000002', 'create', 2),
    ('aaaa1111-0000-0000-0000-000000000005', 'dddd3333-0000-0000-0000-000000000003', 'approve', 3);

-- Warehouse Receipt menu buttons
INSERT INTO menu_permission (menu_id, permission_id, action_type, sort_order) VALUES
    ('bbbb1111-0000-0000-0000-000000000002', 'eeee1111-0000-0000-0000-000000000001', 'view', 1),
    ('bbbb1111-0000-0000-0000-000000000002', 'eeee1111-0000-0000-0000-000000000002', 'create', 2),
    ('bbbb1111-0000-0000-0000-000000000002', 'eeee1111-0000-0000-0000-000000000003', 'edit', 3),
    ('bbbb1111-0000-0000-0000-000000000002', 'eeee1111-0000-0000-0000-000000000004', 'delete', 4);

-- Inventory menu buttons
INSERT INTO menu_permission (menu_id, permission_id, action_type, sort_order) VALUES
    ('bbbb1111-0000-0000-0000-000000000004', 'eeee3333-0000-0000-0000-000000000001', 'view', 1),
    ('bbbb1111-0000-0000-0000-000000000004', 'eeee3333-0000-0000-0000-000000000002', 'export', 2);
