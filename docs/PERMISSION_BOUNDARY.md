# 🛡️ 權限邊界與功能地圖 (Permission Boundary Map)

本文件詳列了系統中各個角色的操作權限範圍，作為未來開發與安全性稽核的依據。

## 1. 角色定義 (Role Definitions)

| 角色名稱 | 程式碼標記 | 說明 | 預設分配 |
| :--- | :--- | :--- | :--- |
| **匿名訪客** | `ROLE_ANONYMOUS` | 未登入的使用者，僅能瀏覽。 | 預設 |
| **一般使用者** | `ROLE_USER` | 已註冊並登入的使用者，可參與互動。 | 註冊時自動分配 |
| **管理員** | `ROLE_ADMIN` | 系統擁有者，具備最高管理權限。 | `DataSeeder` 手動建立 |

---

## 2. 權限邊界矩陣 (Access Matrix)

| 功能模組 | 操作路徑 (Endpoint) | 匿名訪客 | 一般使用者 (USER) | 管理員 (ADMIN) | 核心權限要求 |
| :--- | :--- | :---: | :---: | :---: | :--- |
| **文章系統** | 查看首頁、列表 (`/`) | ✅ | ✅ | ✅ | `permitAll()` |
| | 閱讀文章內容 (`/posts/{id}`) | ✅ | ✅ | ✅ | `permitAll()` |
| | 新增文章頁面 (`/posts/new`) | ❌ | ❌ | ✅ | `POST_WRITE` |
| | 儲存文章 (`POST /posts`) | ❌ | ❌ | ✅ | `POST_WRITE` |
| | 編輯文章 (`/posts/edit/**`) | ❌ | ❌ | ✅ | `POST_WRITE` |
| | 刪除文章 (`/posts/delete/**`) | ❌ | ❌ | ✅ | `POST_WRITE` |
| **留言系統** | 新增留言 (`POST /posts/*/comments`) | ❌ | ✅ | ✅ | `isAuthenticated()` |
| **作品集** | 瀏覽專案列表 (`/projects`) | ✅ | ✅ | ✅ | `permitAll()` |
| | 管理專案 (`/projects/new`, `/projects/edit/**`, `/projects/delete/**`) | ❌ | ❌ | ✅ | `ROLE_ADMIN` |
| **履歷系統** | 瀏覽履歷 (`/resume`) | ✅ | ✅ | ✅ | `permitAll()` |
| | 管理履歷 (`/experiences/**`) | ❌ | ❌ | ✅ | `ROLE_ADMIN` |
| **系統操作** | 登入、註冊、靜態資源 (`/login`, `/register`, `/css/**`) | ✅ | ✅ | ✅ | `permitAll()` |

---

## 3. 安全配置說明 (Security Configuration)

### 3.1 路徑保護 (Path-based)
所有的管理員路徑均已在 `SecurityConfig.java` 中透過 `.requestMatchers()` 進行攔截。如果嘗試未經授權訪問，系統將拋出 **403 Forbidden** 或導向登入頁面。

### 3.2 權限 vs 角色
- **角色 (Role)**：如 `ADMIN`, `USER`。用於粗粒度的身份劃分。
- **權限 (Authority)**：如 `POST_WRITE`。用於細粒度的功能劃分，未來可將此權限分配給多個角色。

### 3.3 未來擴充方向
- **留言管理**：未來若要增加「刪除留言」功能，應新增 `COMMENT_DELETE` 權限，並僅分配給 `ADMIN`。
- **多管理員制度**：可建立 `EDITOR` 角色，僅分配 `POST_WRITE` 權限，但不給予 `ROLE_ADMIN` (無法修改作品集與履歷)。

---

## 4. 稽核記錄
- **2026-05-13**：RBAC 模型升級，修正 `/posts/new` 攔截失效 Bug。
