# 📋 PROJECT CONTEXT

> 🔄 **File duy trì trạng thái dự án qua các phiên làm việc**
> 
> ⚠️ **QUAN TRỌNG**: AI Agent PHẢI đọc file này đầu tiên khi bắt đầu session mới
>
> 📅 **Last Updated**: 2026-01-10

---

## 🏢 HỆ SINH THÁI HẢI LONG ENTERPRISE

### Tổng Quan Apps:

| # | App | Mô tả | Status |
|---|-----|-------|--------|
| 0 | **🔐 Admin** | Quản lý cấu hình chung cho 3 apps | 🔄 IN PROGRESS |
| 1 | **🏭 Nhà Máy** | Quản lý sản xuất, kho vật tư | ⏳ Pending |
| 2 | **👥 HRM** | Quản lý nhân sự, chấm công | ⏳ Pending |
| 3 | **⚙️ Quản Lý Máy Móc** | Quản lý thiết bị, bảo trì | ⏳ Pending |

---

## 🎯 Dự Án Hiện Tại

**Tên**: Admin App  
**Mô tả**: App quản lý cấu hình trung tâm cho hệ sinh thái Hải Long Enterprise  
**Trạng thái**: DISCOVERY

### 🔐 Admin App - Các Chức Năng Chính:

| # | Module | Mô tả | Priority |
|---|--------|-------|----------|
| 1 | **� Quản lý User** | CRUD users, assign roles, gán quyền apps | P0 |
| 2 | **🔑 Phân quyền** | Roles, Permissions, RBAC cho từng app | P0 |
| 3 | **📋 Cấu hình Menu** | Config menu động cho từng app (Nhà Máy, HRM, Máy Móc) | P0 |

---

## 📌 CURRENT STATE

### Phase: `DISCOVERY`

<!--
PHASE VALUES:
- NOT_STARTED  : Chưa bắt đầu dự án
- DISCOVERY    : Đang phân tích và tạo BA docs
- PLANNING     : Đang lên kế hoạch kỹ thuật
- EXECUTING    : Đang implement
- PAUSED       : Tạm dừng (có thể có issue)
- COMPLETED    : Hoàn thành
-->

### Current Task: `Tạo BA Specifications cho Admin App`

### What Was Done Last Session:
```
- Khởi tạo template BMAD_LITE
- Xác định 3 apps trong hệ sinh thái
- Xác định Admin App là app khởi đầu
```

### What To Do Next:
```
1. Tạo docs/admin-prd.md (Product Requirements)
2. Tạo docs/admin-user-stories.md (User Stories + AC)
3. Tạo docs/admin-data-model.md (Database Design)
4. Tạo docs/admin-api-specs.md (API Specifications)
5. Review và approve specs
```

---

## 📊 PROGRESS SUMMARY

| Metric | Value |
|--------|-------|
| Total Tasks | 5 |
| Completed | 0 |
| In Progress | 1 |
| Remaining | 4 |
| Progress | 0% |

### 🧪 Test Cases Status

| Category | Total | Passed | Failed |
|----------|-------|--------|--------|
| Functional | 0 | 0 | 0 |
| UI/UX | 0 | 0 | 0 |
| API | 0 | 0 | 0 |
| Security | 0 | 0 | 0 |

### 🎯 3 Tiêu Chí Chất Lượng

| Tiêu Chí | Status |
|----------|--------|
| ✅ Đúng đủ yêu cầu | ⬜ Chưa verify |
| 🎨 Giao diện đẹp, dễ dùng | ⬜ Chưa verify |
| 🔒 Bảo mật code tốt | ⬜ Chưa verify |

---

## 📁 BA Specifications (Admin App)

| Document | Vai trò | Trạng thái | Notes |
|----------|---------|------------|-------|
| `docs/admin-prd.md` | BA | ✅ Đã tạo | Product Requirements |
| `docs/admin-user-stories.md` | BA | ✅ Đã tạo | User Stories + AC |
| `docs/admin-data-model.md` | Tech Lead | ⏳ Chưa tạo | Database Design |
| `docs/admin-ui-specs.md` | Tech Lead | ⏳ Chưa tạo | UI Specifications |
| `docs/admin-api-specs.md` | Tech Lead | ⏳ Chưa tạo | API Specifications |

---

## 🏗️ Tech Stack

| Layer | Technology |
|-------|------------|
| **Frontend** | Angular 21 |
| **Backend** | Spring Boot 3.x |
| **Database** | MongoDB |
| **Auth** | Keycloak SSO |
| **UI Library** | NG-Zorro Antd |

---

## 📁 Project Files

| File | Mục đích | Trạng thái |
|------|----------|------------|
| `project-plan.md` | Kế hoạch kỹ thuật | ⏳ Chưa tạo |
| `task-queue.md` | Danh sách tasks | ⏳ Chưa tạo |
| `src/` | Source code | ⏳ Chưa tạo |

---

## ⚠️ BLOCKERS & ISSUES

```
[Không có issues]
```

---

## 📝 SESSION LOG

| # | Timestamp | Phase | Action | Result |
|---|-----------|-------|--------|--------|
| 1 | 2026-01-10 | INIT | Khởi tạo template | ✅ Done |
| 2 | 2026-01-10 | DISCOVERY | Xác định Admin App | ✅ Done |

---

## 🔄 RESTART INSTRUCTIONS

Khi bắt đầu session mới, AI Agent nên:

1. **Đọc file này** (`context.md`) để hiểu trạng thái hiện tại
2. **Đọc `docs/admin-*`** để hiểu specifications
3. **Đọc `task-queue.md`** (nếu có) để biết task tiếp theo
4. **Báo user**: "Tôi đã đọc context. [Tóm tắt trạng thái]. Tiếp tục không?"
5. **Làm 1 task** rồi cập nhật lại file này

---

> 🔄 *File này được cập nhật sau MỖI task hoàn thành*
