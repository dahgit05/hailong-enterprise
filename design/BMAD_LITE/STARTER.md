# 🚀 PROJECT STARTER

> **Template dự án với Stateful Single-Agent Workflow**
> 
> 📅 Version: 5.0 | 📆 Updated: 2026-01-10
> 
> 🎯 Philosophy: **AI first → Docs second → Code third → Quality last**

---

## 🏢 HỆ SINH THÁI HẢI LONG ENTERPRISE

### 📦 3 Apps Đang Phát Triển:

| # | App | Mô tả | Tech Stack |
|---|-----|-------|------------|
| 1 | **🏭 Nhà Máy** | Quản lý sản xuất, kho vật tư, xuất nhập hàng, lệnh sản xuất | Angular + Spring Boot + MongoDB |
| 2 | **👥 HRM** | Quản lý nhân sự, chấm công, lương thưởng, đánh giá KPI | Angular + Spring Boot + MongoDB |
| 3 | **⚙️ Quản Lý Máy Móc** | Quản lý thiết bị, bảo trì định kỳ, lịch sử hoạt động | Angular + Spring Boot + MongoDB |

### 🔗 Tích Hợp Chung:
- **Keycloak SSO** - Đăng nhập một lần cho tất cả apps
- **MongoDB** - Database chung
- **Shared Components** - UI/UX thống nhất

---

## ⚡ Quick Start

### Bước 1: Copy Template

```powershell
# Copy toàn bộ folder này thành dự án mới
Copy-Item -Path "SingleAgentTemplate" -Destination "TenDuAnMoi" -Recurse
```

### Bước 2: Mở Workspace

```powershell
# Mở folder mới trong VSCode/Cursor/Antigravity
code "TenDuAnMoi"
```

### Bước 3: Nói với AI

**🆕 Scale-Adaptive**: AI tự động chọn workflow phù hợp:

| Bạn nói | AI chọn Flow |
|---------|-------------|
| "Fix bug [X]" | ⚡ Quick Flow (phút) |
| "Thêm [feature nhỏ]" | ⚡ Quick Flow (phút) |
| "Tôi muốn làm [feature vừa]" | 📋 Standard Flow (giờ) |
| "Tôi muốn làm [dự án lớn]" | 🏗️ Full Flow (ngày) |
| "Analyze codebase" | 🔍 Brownfield Flow |

AI sẽ:
1. Đóng **7 vai chuyên biệt** (3 Core + 4 Supporting Agents)
2. Tạo docs (PRD, User Stories, Data Model, UI/API Specs)
3. Tạo `test-cases.md` với test cases từ requirements
4. Tạo `project-plan.md` + `task-queue.md`
5. Hỏi bạn approve trước khi bắt đầu

---

## 🌟 Điểm Khác Biệt

### So Với Multi-Agent (Cũ)

| Multi-Agent (Cũ) | Stateful Single-Agent (Mới) |
|------------------|----------------------------|
| Mở nhiều IDE | **1 IDE duy nhất** |
| Chat với nhiều workers | **Chat 1 chỗ** |
| Phức tạp khi sync | **Đơn giản** |
| Parallel nhưng tốn công | **Sequential nhưng dễ dùng** |

### So Với Single-Agent (Thường)

| Single-Agent (Thường) | Stateful Single-Agent (Mới) |
|----------------------|----------------------------|
| Chat dài → Lag | **Mỗi session = 1-2 tasks** |
| Restart → Mất hết | **Restart → Tiếp tục ngay** |
| AI bối rối | **Tasks rõ ràng** |
| Không track progress | **Task queue chi tiết** |

---

## 📁 Cấu Trúc Template

```
📁 YourProject/
│
├── 🚀 STARTER.md          ← BẠN ĐANG Ở ĐÂY (xóa sau khi hiểu)
│
├── 🔴 CORE STATE
│   ├── context.md         ← Trạng thái dự án (AI đọc đầu tiên)
│   └── task-queue.md      ← Danh sách tasks
│
├── 📘 PROJECT DOCS
│   ├── readme.md          ← Entry point cho AI
│   ├── about.md           ← Thông tin dự án
│   └── project-plan.md    ← Kế hoạch (tạo khi planning)
│
├── 📗 PROCESS DOCS
│   ├── workflow.md        ← Quy trình làm việc
│   ├── thinking.md        ← Framework tư duy
│   ├── quality.md         ← Checklist chất lượng
│   └── system.md          ← Profile năng lực AI
│
└── 📁 src/                ← Source code (tạo khi implement)
```

---

## 🎯 Workflow Tổng Quan

```
┌─────────────────────────────────────────────────────────────────┐
│                         HOW IT WORKS v5.0                        │
├─────────────────────────────────────────────────────────────────┤
│  🆕 SCALE-ADAPTIVE INTELLIGENCE                                  │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ ⚡ Quick Flow: Bug fix → Spec → Dev → Done (phút)        │   │
│  │ 📋 Standard: Full 4-phase workflow (giờ)                 │   │
│  │ 🏗️ Full Flow: Enterprise + extra docs (ngày)            │   │
│  └─────────────────────────────────────────────────────────┘   │
│                            │                                     │
│                            ▼                                     │
│   Phase 0: DISCOVERY & ANALYSIS (7 Agent Roles)                  │
│   ┌─────────────────────────────────────────────────────────┐   │
│   │ 🎯 BA: Tạo PRD, User Stories                              │   │
│   │ 🛠️ Tech Lead: Tạo Data Model, UI/API Specs               │   │
│   │ 🧪 Tester Lead: Tạo Test Cases từ User Stories          │   │
│   │ 🎨🔒🚀📊 Supporting Agents: UX, Security, DevOps, Data  │   │
│   └─────────────────────────────────────────────────────────┘   │
│                            │                                     │
│                            ▼                                     │
│   Phase 1: PLANNING                                              │
│   ┌─────────────────────────────────────────────────────────┐   │
│   │ AI: Đọc docs → Tạo project-plan.md + task-queue.md      │   │
│   │ AI: "Approve plan kỹ thuật?"                             │   │
│   └─────────────────────────────────────────────────────────┘   │
│                            │                                     │
│                            ▼                                     │
│   Phase 2: EXECUTING (TDD)                                       │
│   ┌─────────────────────────────────────────────────────────┐   │
│   │ AI: Đọc specs + test cases → Implement                   │   │
│   │ AI: Code phải PASS test cases trước khi tiếp tục        │   │
│   │ AI: Update docs + test cases nếu có thay đổi             │   │
│   └─────────────────────────────────────────────────────────┘   │
│                            │                                     │
│                            ▼                                     │
│   Phase 3: VERIFICATION (3 Tiêu Chí)                             │
│   ┌─────────────────────────────────────────────────────────┐   │
│   │ ✅ Đúng đủ yêu cầu (Functional tests PASSED)              │   │
│   │ 🎨 Giao diện đẹp, dễ dùng (UI/UX tests PASSED)           │   │
│   │ 🔒 Bảo mật code tốt (Security tests PASSED)             │   │
│   └─────────────────────────────────────────────────────────┘   │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

---

## 📞 Commands Thường Dùng

| Command | Khi nào dùng |
|---------|--------------|
| **Standard** | |
| "Tôi muốn làm app Nhà Máy" | Bắt đầu module sản xuất/kho |
| "Tôi muốn làm app HRM" | Bắt đầu module nhân sự |
| "Tôi muốn làm app Quản Lý Máy Móc" | Bắt đầu module thiết bị |
| "Đọc context.md và tiếp tục" | Sau restart / session mới |
| "Tiếp tục" / "Next" | Làm task tiếp theo |
| "Status" | Xem progress |
| "Dừng" | Kết thúc session |
| **🆕 Quick Flow** | |
| "Fix bug [X]" | Quick Flow cho bug fix |
| "Quick: [task]" | Quick Flow cho task nhỏ |
| **🆕 Brownfield** | |
| "Analyze codebase" | Phân tích dự án có sẵn |
| "Add [feature] to [module]" | Thêm feature vào code có sẵn |

---

## ✅ Checklist Khởi Tạo

```
[ ] 1. Chọn app để phát triển (Nhà Máy / HRM / Quản Lý Máy Móc)
[ ] 2. Mở workspace trong IDE
[ ] 3. Nói với AI: "Tôi muốn làm app [tên app]"
[ ] 4. Review và approve plan
[ ] 5. Nói "Tiếp tục" để bắt đầu implement
[ ] 6. Xóa file STARTER.md này (optional)
```

---

## 💡 Tips

1. **Mỗi session làm ít thôi**: 1-3 tasks để tránh lag
2. **Restart thoải mái**: "Đọc context.md và tiếp tục" là đủ
3. **Tracks progress**: Xem `task-queue.md` để biết còn bao nhiêu việc
4. **Không sợ mất**: Mọi thứ lưu trong files

---

## 🔗 Tài Liệu Chi Tiết

| Cần gì | Xem file |
|--------|----------|
| Trạng thái dự án | [context.md](context.md) |
| Danh sách tasks | [task-queue.md](task-queue.md) |
| Quy trình chi tiết | [workflow.md](workflow.md) |
| Cách đặt yêu cầu | [thinking.md](thinking.md) |

---

> 🌟 **Ready?** Nói với AI:
> - "Tôi muốn làm app Nhà Máy"
> - "Tôi muốn làm app HRM"
> - "Tôi muốn làm app Quản Lý Máy Móc"
