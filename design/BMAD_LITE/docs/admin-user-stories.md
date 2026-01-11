# 📖 USER STORIES - Admin App

> **Dự án**: Hải Long Enterprise - Admin App  
> **Version**: 1.0  
> **Ngày tạo**: 2026-01-10

---

## 🎯 Epic 1: Quản Lý Users

> **Mô tả**: CRUD users trong hệ thống, gán vào apps và roles

### US-001: Xem danh sách Users

| Thuộc tính | Giá trị |
|------------|---------|
| **Priority** | 🔴 P0 |
| **Persona** | Super Admin |
| **Epic** | Quản Lý Users |

**User Story**:
```
As a Super Admin
I want to view all users in the system
So that I can manage user access
```

**Acceptance Criteria**:

| # | Scenario | Given | When | Then | Status |
|---|----------|-------|------|------|--------|
| AC1 | View users | Admin đã login | Mở trang Users | Thấy danh sách users với pagination | ⬜ |
| AC2 | Search users | Đang ở trang Users | Nhập keyword | Filter users theo username/email | ⬜ |
| AC3 | Filter by app | Đang ở trang Users | Chọn filter App | Chỉ hiện users thuộc app đó | ⬜ |

---

### US-002: Thêm User mới

| Thuộc tính | Giá trị |
|------------|---------|
| **Priority** | 🔴 P0 |
| **Persona** | Super Admin |
| **Epic** | Quản Lý Users |

**User Story**:
```
As a Super Admin
I want to create new users
So that employees can access the system
```

**Acceptance Criteria**:

| # | Scenario | Given | When | Then | Status |
|---|----------|-------|------|------|--------|
| AC1 | Happy path | Form hợp lệ | Submit | User được tạo, sync Keycloak | ⬜ |
| AC2 | Duplicate username | Username đã tồn tại | Submit | Hiện lỗi "Username đã tồn tại" | ⬜ |
| AC3 | Duplicate email | Email đã tồn tại | Submit | Hiện lỗi "Email đã tồn tại" | ⬜ |
| AC4 | Missing required | Thiếu field bắt buộc | Submit | Hiện validation errors | ⬜ |

**Form Fields**:
| Field | Type | Required | Validation |
|-------|------|----------|------------|
| username | text | ✅ | 3-50 chars, alphanumeric |
| email | email | ✅ | Valid email format |
| fullName | text | ✅ | 2-100 chars |
| phone | text | ❌ | VN phone format |
| apps | multiselect | ✅ | Chọn ít nhất 1 app |
| roles | multiselect | ❌ | Roles thuộc apps đã chọn |

---

### US-003: Sửa thông tin User

| Thuộc tính | Giá trị |
|------------|---------|
| **Priority** | 🔴 P0 |
| **Persona** | Super Admin |
| **Epic** | Quản Lý Users |

**User Story**:
```
As a Super Admin
I want to edit user information
So that I can update user details and permissions
```

**Acceptance Criteria**:

| # | Scenario | Given | When | Then | Status |
|---|----------|-------|------|------|--------|
| AC1 | Edit info | User tồn tại | Sửa và Submit | Thông tin được cập nhật | ⬜ |
| AC2 | Change apps | User đang có apps | Thay đổi apps | Roles không thuộc apps bị remove | ⬜ |
| AC3 | Disable user | User đang active | Toggle disable | User không thể login | ⬜ |

---

### US-004: Xóa User

| Thuộc tính | Giá trị |
|------------|---------|
| **Priority** | 🟠 P1 |
| **Persona** | Super Admin |
| **Epic** | Quản Lý Users |

**User Story**:
```
As a Super Admin
I want to delete users
So that I can remove inactive accounts
```

**Acceptance Criteria**:

| # | Scenario | Given | When | Then | Status |
|---|----------|-------|------|------|--------|
| AC1 | Confirm delete | Click delete | Confirm dialog | User bị soft delete | ⬜ |
| AC2 | Cancel delete | Click delete | Cancel dialog | User không bị xóa | ⬜ |

---

## 🎯 Epic 2: Quản Lý Roles & Permissions

> **Mô tả**: Tạo roles, define permissions, assign cho users

### US-005: Xem danh sách Roles

| Thuộc tính | Giá trị |
|------------|---------|
| **Priority** | 🔴 P0 |
| **Persona** | Super Admin |
| **Epic** | Quản Lý Roles |

**User Story**:
```
As a Super Admin
I want to view all roles per app
So that I can manage access control
```

**Acceptance Criteria**:

| # | Scenario | Given | When | Then | Status |
|---|----------|-------|------|------|--------|
| AC1 | View by app | Admin đã login | Chọn App filter | Hiện roles thuộc app đó | ⬜ |
| AC2 | See permissions | Đang xem roles | Click 1 role | Hiện danh sách permissions của role | ⬜ |

---

### US-006: Tạo Role mới

| Thuộc tính | Giá trị |
|------------|---------|
| **Priority** | 🔴 P0 |
| **Persona** | Super Admin |
| **Epic** | Quản Lý Roles |

**User Story**:
```
As a Super Admin
I want to create new roles
So that I can define access levels for different user groups
```

**Acceptance Criteria**:

| # | Scenario | Given | When | Then | Status |
|---|----------|-------|------|------|--------|
| AC1 | Create role | Form hợp lệ | Submit | Role được tạo cho app | ⬜ |
| AC2 | Assign permissions | Tạo role | Chọn permissions | Role có permissions được chọn | ⬜ |
| AC3 | Duplicate name | Role name đã tồn tại trong app | Submit | Hiện lỗi | ⬜ |

**Form Fields**:
| Field | Type | Required | Notes |
|-------|------|----------|-------|
| roleName | text | ✅ | Unique trong app |
| description | textarea | ❌ | Mô tả role |
| appId | select | ✅ | App mà role thuộc về |
| permissions | tree-checkbox | ✅ | Danh sách permissions |

---

### US-007: Gán Permissions cho Role

| Thuộc tính | Giá trị |
|------------|---------|
| **Priority** | 🔴 P0 |
| **Persona** | Super Admin |
| **Epic** | Quản Lý Roles |

**User Story**:
```
As a Super Admin
I want to assign permissions to roles
So that users with that role have specific access
```

**Acceptance Criteria**:

| # | Scenario | Given | When | Then | Status |
|---|----------|-------|------|------|--------|
| AC1 | View permissions tree | Mở role edit | Load form | Hiện permission tree grouped by module | ⬜ |
| AC2 | Select all in module | Click checkbox module | Toggle | Tất cả permissions trong module được chọn | ⬜ |
| AC3 | Save permissions | Chọn permissions | Submit | Role được update, realtime cho users | ⬜ |

---

## 🎯 Epic 3: Cấu Hình Menu

> **Mô tả**: Config menu động cho từng app

### US-008: Xem cấu hình Menu

| Thuộc tính | Giá trị |
|------------|---------|
| **Priority** | 🔴 P0 |
| **Persona** | Super Admin / App Admin |
| **Epic** | Cấu Hình Menu |

**User Story**:
```
As an Admin
I want to view menu configuration for each app
So that I can understand the current menu structure
```

**Acceptance Criteria**:

| # | Scenario | Given | When | Then | Status |
|---|----------|-------|------|------|--------|
| AC1 | View menu tree | Chọn 1 App | Load trang Menu Config | Hiện menu dạng tree | ⬜ |
| AC2 | See permissions | Xem menu item | Hover/click | Thấy required permissions | ⬜ |

---

### US-009: Thêm/Sửa Menu Item

| Thuộc tính | Giá trị |
|------------|---------|
| **Priority** | 🔴 P0 |
| **Persona** | Super Admin |
| **Epic** | Cấu Hình Menu |

**User Story**:
```
As a Super Admin
I want to add/edit menu items
So that I can customize navigation for each app
```

**Acceptance Criteria**:

| # | Scenario | Given | When | Then | Status |
|---|----------|-------|------|------|--------|
| AC1 | Add root menu | Đang ở trang Menu | Click "Thêm Menu" | Form tạo menu item hiện | ⬜ |
| AC2 | Add child menu | Đang ở trang Menu | Right-click parent → Add child | Tạo menu con | ⬜ |
| AC3 | Edit menu | Đang ở trang Menu | Click menu item → Edit | Sửa thông tin menu | ⬜ |
| AC4 | Drag & drop | Đang ở trang Menu | Drag menu item | Reorder menu | ⬜ |

**Menu Item Fields**:
| Field | Type | Required | Notes |
|-------|------|----------|-------|
| label | text | ✅ | Tên hiển thị |
| icon | icon-picker | ❌ | Icon from NG-Zorro |
| path | text | ✅ | URL path |
| permissions | multiselect | ✅ | Required permissions |
| isVisible | toggle | ✅ | Ẩn/hiện menu |
| order | number | ✅ | Thứ tự hiển thị |

---

### US-010: Xóa Menu Item

| Thuộc tính | Giá trị |
|------------|---------|
| **Priority** | 🟠 P1 |
| **Persona** | Super Admin |
| **Epic** | Cấu Hình Menu |

**User Story**:
```
As a Super Admin
I want to delete menu items
So that I can remove unused navigation
```

**Acceptance Criteria**:

| # | Scenario | Given | When | Then | Status |
|---|----------|-------|------|------|--------|
| AC1 | Delete leaf | Menu không có children | Confirm delete | Menu bị xóa | ⬜ |
| AC2 | Delete parent | Menu có children | Try delete | Warning: xóa cả children | ⬜ |

---

## 📋 Summary Table

| ID | User Story | Epic | Priority | Status |
|----|------------|------|----------|--------|
| US-001 | Xem danh sách Users | Quản Lý Users | 🔴 P0 | ⬜ Todo |
| US-002 | Thêm User mới | Quản Lý Users | 🔴 P0 | ⬜ Todo |
| US-003 | Sửa thông tin User | Quản Lý Users | 🔴 P0 | ⬜ Todo |
| US-004 | Xóa User | Quản Lý Users | 🟠 P1 | ⬜ Todo |
| US-005 | Xem danh sách Roles | Roles & Permissions | 🔴 P0 | ⬜ Todo |
| US-006 | Tạo Role mới | Roles & Permissions | 🔴 P0 | ⬜ Todo |
| US-007 | Gán Permissions | Roles & Permissions | 🔴 P0 | ⬜ Todo |
| US-008 | Xem cấu hình Menu | Cấu Hình Menu | 🔴 P0 | ⬜ Todo |
| US-009 | Thêm/Sửa Menu Item | Cấu Hình Menu | 🔴 P0 | ⬜ Todo |
| US-010 | Xóa Menu Item | Cấu Hình Menu | 🟠 P1 | ⬜ Todo |

---

## 🔗 Related Documents

- [Admin PRD](admin-prd.md)
- [Admin Data Model](admin-data-model.md)
- [Admin API Specs](admin-api-specs.md)

---

> 📝 **Note**: Mỗi User Story được hoàn thành phải có tất cả Acceptance Criteria pass.
