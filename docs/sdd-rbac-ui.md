# SDD: 動態 RBAC 管理介面 (Dynamic RBAC UI)

## 1. 目標 (Objective)
提供管理員一個直觀的網頁介面，用於管理系統使用者、角色及其權限，無需修改程式碼或直接操作資料庫。

## 2. 需求分析 (Requirements)

### 使用者管理 (User Management)
- 管理員可以查看所有使用者列表。
- 管理員可以點擊「編輯」，並透過勾選框 (Checkbox) 為使用者指派一個或多個角色。

### 角色管理 (Role Management)
- 管理員可以新增、編輯或刪除角色。
- 管理員可以為角色勾選所屬的權限（如 `POST_WRITE`, `RESUME_EDIT`）。

### 權限檢視 (Permission View)
- 管理員可以查看系統目前定義的所有權限（目前設計為唯讀，由程式碼初始化）。

## 3. 設計方案 (Design)

### 3.1 路由設計 (Endpoints)
| 方法 | 路徑 | 說明 |
| :--- | :--- | :--- |
| `GET` | `/admin/users` | 使用者管理頁面 |
| `POST` | `/admin/users/{id}/roles` | 更新使用者的角色 |
| `GET` | `/admin/roles` | 角色管理頁面 |
| `POST` | `/admin/roles` | 新增/更新角色與權限關聯 |
| `DELETE` | `/admin/roles/{id}` | 刪除角色 |

### 3.2 UI 組件設計
- **使用者列表表格**：顯示 Username, 當前角色。
- **角色編輯彈窗 (Modal)**：列出所有 `Role`，使用多選框。
- **權限分配矩陣**：在角色編輯時，列出所有 `Permission` 供勾選。

### 3.3 技術挑戰：即時生效
- 當管理員修改權限後，已登入的使用者其 `SecurityContext` 仍保有舊的權限。
- **方案**：目前先實作「下次登入生效」。進階方案可考慮在更新權限後，透過 `SessionRegistry` 強制使用者重新驗證（選配）。

## 4. 實作計畫 (Phase 2 Detail)
1. **建立 `AdminController`**：處理 `/admin/**` 相關路由。
2. **建立 DTOs**：`UserRoleUpdateDTO`, `RolePermissionDTO` 確保數據傳輸安全。
3. **撰寫 Thymeleaf 模板**：`admin/user_list.html`, `admin/role_list.html`。
4. **整合 `@LogOperation`**：確保所有管理動作都被紀錄。

## 5. 定義完成 (Definition of Done)
- [ ] 管理員能成功在頁面上將一個 User 從 `ROLE_USER` 提升為 `ROLE_ADMIN`。
- [ ] 管理員能建立一個新角色（如 `GUEST`）並指派特定權限。
- [ ] 所有的權限變更動作都有記錄在 `audit_logs` 中。
