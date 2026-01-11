# 📋 PRD - Admin App

> **Dự án**: Hải Long Enterprise - Admin App  
> **Version**: 1.0  
> **Ngày tạo**: 2026-01-10  
> **Trạng thái**: Draft

---

## 1. 🎯 Tổng Quan

### 1.1 Problem Statement
> Hệ sinh thái Hải Long Enterprise gồm 3 apps độc lập (Nhà Máy, HRM, Quản Lý Máy Móc) cần một hệ thống quản lý tập trung để:

```
- Quản lý users và phân quyền thống nhất
- Cấu hình menu động cho từng app
- Đảm bảo bảo mật và kiểm soát truy cập (RBAC)
- Tích hợp SSO (Keycloak) cho tất cả apps
```

### 1.2 Proposed Solution
> Admin App - Ứng dụng quản lý trung tâm

```
- Quản lý toàn bộ users trong hệ thống
- Phân quyền theo Role-Based Access Control (RBAC)
- Cấu hình menu động cho từng app con
- Tích hợp với Keycloak để SSO
```

### 1.3 Goals (Mục tiêu)
| # | Mục tiêu | Đo lường thành công |
|---|----------|---------------------|
| G1 | Quản lý users tập trung | 100% users được quản lý qua Admin App |
| G2 | Phân quyền RBAC | Users chỉ thấy menu/features được phân quyền |
| G3 | Menu động | Mỗi app có thể config menu riêng |

### 1.4 Non-Goals (Không làm)
> Phase 1 không bao gồm:

- ❌ Dashboard thống kê phức tạp
- ❌ Audit log chi tiết (để Phase 2)
- ❌ Multi-tenant (mỗi nhà máy là 1 tenant riêng)

---

## 2. 👤 Target Users

### 2.1 User Personas

#### Persona 1: Super Admin
| Thuộc tính | Chi tiết |
|------------|----------|
| **Vai trò** | Quản trị viên hệ thống |
| **Tech savvy** | High |
| **Mục tiêu** | Quản lý toàn bộ hệ thống, users, phân quyền |
| **Pain points** | Phải quản lý riêng lẻ từng app |
| **Use case chính** | Tạo users, gán roles, config menu |

#### Persona 2: App Admin
| Thuộc tính | Chi tiết |
|------------|----------|
| **Vai trò** | Quản trị viên từng app |
| **Tech savvy** | Medium |
| **Mục tiêu** | Quản lý users và menu trong phạm vi app của mình |
| **Pain points** | Cần quyền hạn phù hợp |
| **Use case chính** | Cấu hình menu, gán users vào app |

---

## 3. ✨ Functional Requirements

### 3.1 Feature List

| ID | Feature | Priority | Mô tả |
|----|---------|----------|-------|
| F001 | Quản lý Users | P0 | CRUD users, search, filter |
| F002 | Quản lý Roles | P0 | Tạo roles, assign permissions |
| F003 | Phân quyền Permissions | P0 | Định nghĩa permissions, assign to roles |
| F004 | Gán User-App | P0 | Assign users vào apps cụ thể |
| F005 | Cấu hình Menu | P0 | Config menu động cho từng app |
| F006 | Keycloak Sync | P1 | Đồng bộ users từ Keycloak |

> **Priority Legend**:
> - P0 = Must have (MVP)
> - P1 = Should have
> - P2 = Nice to have

### 3.2 Feature Details

#### F001: Quản lý Users

**Mô tả**: CRUD users trong hệ thống

**User Flow**:
```
1. Admin mở trang Users
2. Admin click "Thêm User"
3. Điền thông tin: username, email, fullName, phone
4. Chọn apps được phép truy cập
5. Gán roles
6. Submit → User được tạo
```

**Business Rules**:
- BR1: Username phải unique
- BR2: Email phải unique
- BR3: User phải thuộc ít nhất 1 app

---

#### F002: Quản lý Roles

**Mô tả**: Tạo và quản lý roles trong hệ thống

**User Flow**:
```
1. Admin mở trang Roles
2. Click "Thêm Role"
3. Điền: roleName, description, appId (thuộc app nào)
4. Assign permissions
5. Submit → Role được tạo
```

**Business Rules**:
- BR1: Role thuộc về 1 app cụ thể
- BR2: Role có nhiều permissions
- BR3: Mỗi app có roles riêng

---

#### F003: Phân quyền Permissions

**Mô tả**: Định nghĩa permissions và gán vào roles

**Structure**:
```
App → Modules → Permissions
```

**Ví dụ**:
| App | Module | Permission |
|-----|--------|------------|
| Nhà Máy | Kho Vật Tư | warehouse:view |
| Nhà Máy | Kho Vật Tư | warehouse:create |
| Nhà Máy | Kho Vật Tư | warehouse:edit |
| Nhà Máy | Kho Vật Tư | warehouse:delete |
| HRM | Chấm Công | attendance:view |
| HRM | Chấm Công | attendance:approve |

---

#### F005: Cấu hình Menu

**Mô tả**: Config menu động cho từng app

**Menu Structure**:
```json
{
  "appId": "factory",
  "menuItems": [
    {
      "id": "warehouse",
      "label": "Kho Vật Tư",
      "icon": "warehouse",
      "path": "/warehouse",
      "permissions": ["warehouse:view"],
      "children": [
        {
          "id": "receipt",
          "label": "Phiếu Nhập",
          "path": "/warehouse/receipt",
          "permissions": ["warehouse:receipt:view"]
        }
      ]
    }
  ]
}
```

**Business Rules**:
- BR1: Menu items có thể nested (parent-child)
- BR2: Mỗi menu item có permissions required
- BR3: User chỉ thấy menu items mà họ có permission

---

## 4. 🔧 Non-Functional Requirements

### 4.1 Performance
| Metric | Target |
|--------|--------|
| Page load time | < 2s |
| API response time | < 500ms |
| Concurrent users | 50 |

### 4.2 Security
- [x] Authentication: Keycloak SSO
- [x] Authorization: RBAC
- [x] Data encryption: HTTPS
- [x] OWASP compliance: Yes

---

## 5. 🛠️ Technical Constraints

### 5.1 Tech Stack
| Layer | Technology | Lý do |
|-------|------------|-------|
| Frontend | Angular 21 | Đồng bộ với apps khác |
| Backend | Spring Boot 3.x | Mature, production-ready |
| Database | MongoDB | Flexible schema |
| Auth | Keycloak | Enterprise SSO |
| UI | NG-Zorro Antd | Consistent design |

### 5.2 Integration Requirements
| System | Type | Purpose |
|--------|------|---------|
| Keycloak | OAuth2/OIDC | SSO Authentication |
| Nhà Máy App | REST API | Menu & Permissions sync |
| HRM App | REST API | Menu & Permissions sync |
| Máy Móc App | REST API | Menu & Permissions sync |

---

## 6. 📅 Timeline & Milestones

| Milestone | Deliverables | Target Date |
|-----------|--------------|-------------|
| M1: Discovery Complete | PRD, User Stories approved | Week 1 |
| M2: Planning Complete | Data Model, API Specs | Week 2 |
| M3: MVP Ready | User + Role + Permission + Menu | Week 4 |
| M4: Production | Full release with Keycloak sync | Week 6 |

---

## 7. ✅ Approval

| Role | Name | Date | Status |
|------|------|------|--------|
| Product Owner | Hải Long | 2026-01-10 | ⏳ Pending |
| Tech Lead | AI Agent | 2026-01-10 | ⏳ Pending |

---

> 📝 **Ghi chú**: Document này sẽ được update liên tục trong quá trình phát triển.
