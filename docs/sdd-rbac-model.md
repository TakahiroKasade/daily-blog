# SDD: RBAC 權限管理模型升級

## 1. 目標 (Objective)
將目前的簡單角色系統（單一 String 欄位）升級為靈活的 RBAC（角色存取控制）模型。
這將允許：
- 一個使用者擁有多個角色。
- 一個角色擁有多個權限（Permission）。
- 更細粒度的安全控管（例如：`POST_CREATE`, `POST_DELETE`, `RESUME_EDIT`）。

## 2. 需求分析 (Requirements)

### 功能需求 (Functional)
- 系統應支援定義權限（Permission），例如 `READ_POST`, `WRITE_POST`, `DELETE_POST`。
- 系統應支援定義角色（Role），角色是一組權限的集合，例如 `ADMIN`, `EDITOR`, `USER`。
- 使用者可以被賦予一個或多個角色。
- Spring Security 應能根據使用者擁有的權限進行存取控制。

### 非功能需求 (Non-Functional)
- **擴展性**：未來新增功能時，只需增加權限與角色的關聯，不需修改核心邏輯。
- **效能**：權限檢查應快速，考慮使用 Eager Loading 或緩存常用權限。

## 3. 設計方案 (Design)

### 3.1 實體模型 (Entity Model)

```mermaid
erDiagram
    User ||--o{ User_Role : has
    Role ||--o{ User_Role : assigned_to
    Role ||--o{ Role_Permission : has
    Permission ||--o{ Role_Permission : assigned_to

    User {
        Long id
        String username
        String password
    }

    Role {
        Long id
        String name (e.g., "ROLE_ADMIN")
    }

    Permission {
        Long id
        String name (e.g., "POST_WRITE")
    }
```

- **User**: 移除 `role` 欄位，改用 `@ManyToMany` 與 `Role` 關聯。
- **Role**: 儲存角色名稱。
- **Permission**: 儲存具體權限名稱。

### 3.2 類別設計 (Class Design)
- `com.dailycoding.blog.entity.Role`
- `com.dailycoding.blog.entity.Permission`
- 修改 `com.dailycoding.blog.entity.User`

### 3.3 數據遷移 (Data Migration)
1. 建立 `roles` 與 `permissions` 表。
2. 建立中間表 `user_roles` 與 `role_permissions`。
3. 將舊有的 `User.role` 資料遷移至 `roles` 表，並建立關聯。
4. 刪除 `User.role` 欄位。

## 4. 測試策略 (Test Strategy)
- **單元測試**:
  - `UserRoleTest`: 驗證使用者與角色的關聯是否正確保存與讀取。
  - `RolePermissionTest`: 驗證角色與權限的關聯。
- **集成測試**:
  - `SecurityIntegrationTest`: 驗證擁有特定權限的使用者是否能訪問受保護的 API。

## 5. 定義完成 (Definition of Done)
- [ ] 實體關係圖與程式碼實作一致。
- [ ] 通過 TDD 測試（單元測試覆蓋率 > 80%）。
- [ ] `DataSeeder` 成功初始化預設角色與權限。
- [ ] Git 提交符合規範。
