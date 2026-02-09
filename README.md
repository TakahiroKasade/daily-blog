# Daily Blog Project (1-Day MVP)

## 1. 專案概述 (Project Overview)
這是一個基於 **Java Spring Boot 3** 與 **Thymeleaf** 開發的個人部落格與作品集系統 (Daily Blog)。
主要目標是建立一個輕量級、全端的個人網站，具備動態文章展示與作品集連結功能。

**核心功能：**
*   **動態部落格 (Posts)**：顯示最新的技術筆記。
*   **作品集展示 (Projects)**：以卡片形式展示 Side Projects，並提供外部連結 (Portal Pattern)。
*   **自動化資料載入 (Data Seeding)**：啟動時自動寫入測試資料，方便開發與展示。

## 2. 技術堆疊 (Technology Stack)

| 類別 | 技術 | 版本 | 用途 |
| :--- | :--- | :--- | :--- |
| **Backend** | Java | 17 (LTS) | 核心程式語言 |
| **Framework** | Spring Boot | 3.x (Web) | REST API 與 依賴注入 (DI) |
| **Database** | H2 Database | In-Memory | 開發用資料庫 (重啟即消失) |
| **ORM** | Spring Data JPA | (Hibernate) | 物件關聯對映 (Java Object <-> SQL Table) |
| **View Engine** | Thymeleaf | 3.x | 伺服器端渲染 (Server-Side Rendering) |
| **Frontend** | Bootstrap | 5.3 (CDN) | RWD 響應式切版 |
| **Build Tool** | Maven | 3.x | 專案建置與依賴管理 |

## 3. 系統架構 (System Architecture)
本專案採用經典的 **MVC (Model-View-Controller)** 分層架構，確保職責分離。

### 3.1 資料層 (Data Layer)
*   **Entities (實體類別)**：定義資料庫結構。
    *   `Post.java`：id, title, content (TEXT), createdTime
    *   `Project.java`：id, name, description (TEXT), imageUrl, websiteUrl
*   **Repositories (資料存取介面)**：
    *   介面繼承 `JpaRepository<T, ID>`，自動獲得 CRUD 能力。
    *   `PostRepository`：提供文章查詢。
    *   `ProjectRepository`：提供作品查詢 (包含自定義搜尋 `findByNameContaining`)。

### 3.2 業務邏輯層 (Service Layer)
*   **Services**：封裝業務邏輯，不直接曝露 Repository 給 Controller。
    *   `PostService`：負責提供文章列表 (`getAllPosts`)。
    *   `ProjectService`：負責提供作品列表 (`getAllProjects`)。
*   **依賴注入 (Dependency Injection)**：使用 Constructor Injection 注入 Repository。

### 3.3 控制層 (Controller Layer)
*   `IndexController`：
    *   **路徑**：`@GetMapping("/")`
    *   **職責**：呼叫兩個 Service 取得資料 -> 放入 `Model` -> 回傳 `index.html`。
    *   **設計理念**：大廳經理模式，協調不同部門 (Service) 的產出。

### 3.4 視圖層 (View Layer)
*   `src/main/resources/templates/index.html`
*   使用 **Thymeleaf** 語法 (`th:each`, `th:text`, `th:src`, `th:href`) 動態渲染資料。
*   使用 **Bootstrap 5** Grid System 進行排版。

## 4. 關鍵組件詳解 (Key Components)

### DataSeeder (資料播種機)
*   **位置**：`com.dailycoding.blog.config.DataSeeder`
*   **介面**：實作 `CommandLineRunner`。
*   **功能**：在 Spring Boot 啟動完成後自動執行。檢查 Repository 如果為空 (`count() == 0`)，則寫入預設的假資料 (Mock Data)。
*   **效益**：解決 In-Memory DB 每次重啟資料遺失的問題，加速開發驗證。

## 5. 專案啟動與設定 (Setup & Run)
1.  **Clone 專案**：`git clone https://github.com/your-username/daily-blog.git`
2.  **資料庫設定** (`application.properties`)：
    ```properties
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.username=sa
    spring.datasource.password=password
    spring.h2.console.enabled=true
    ```
3.  **執行**：透過 Maven 或 IntelliJ 執行 `DailyBlogApplication.java`。
4.  **瀏覽**：
    *   首頁：[http://localhost:8080](http://localhost:8080)
    *   H2 Console：[http://localhost:8080/h2-console](http://localhost:8080/h2-console)
