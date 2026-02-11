# Daily Blog 🖥️

一個基於 **Spring Boot 4** 與 **Thymeleaf** 開發的個人技術部落格系統。
採用 **MVC 分層架構**，搭配 **PostgreSQL** 資料庫與 **Spring Security** 身份驗證機制。

## 📋 功能一覽

| 功能 | 說明 | 狀態 |
|------|------|------|
| 📝 文章 CRUD | 新增、編輯、刪除技術筆記 | ✅ 完成 |
| 🎨 科技風 UI | Bootstrap 5 深色主題 + 動態動畫 | ✅ 完成 |
| 🗃️ PostgreSQL | 持久化資料儲存 | ✅ 完成 |
| 🔐 Spring Security | 身份驗證與授權 (Role-Based) | ✅ 完成 |
| 🔑 登入/註冊 | 自訂登入頁面、會員註冊、密碼加密 | ✅ 完成 |
| 🏗️ 作品集展示 | Side Projects 卡片展示 | ✅ 完成 |

## 🛠️ 技術堆疊

| 類別 | 技術 | 版本 | 用途 |
|------|------|------|------|
| **Backend** | Java | 17 (LTS) | 核心程式語言 |
| **Framework** | Spring Boot | 4.x | REST API / DI |
| **Database** | PostgreSQL | 17.x | 持久化資料庫 |
| **ORM** | Spring Data JPA | Hibernate | 物件關聯對映 |
| **Security** | Spring Security | 6.x | 身份驗證與授權 |
| **View** | Thymeleaf | 3.x | 伺服器端渲染 (SSR) |
| **Frontend** | Bootstrap | 5.3 (CDN) | RWD 響應式切版 |
| **Build** | Maven | 3.x | 依賴管理與建置 |

## 📁 專案結構

```
src/main/java/com/dailycoding/blog/
├── config/
│   ├── DataSeeder.java              # 資料播種機 (含管理員初始化)
│   └── SecurityConfig.java          # Spring Security 設定
├── controller/
│   ├── IndexController.java         # 首頁控制器
│   ├── LoginController.java         # 登入頁面控制器
│   ├── PostController.java          # 文章 CRUD 控制器
│   └── RegisterController.java      # 註冊頁面控制器
├── entity/
│   ├── Post.java                    # 文章實體
│   ├── Project.java                 # 作品集實體
│   └── User.java                    # 使用者實體 (Security)
├── repository/
│   ├── PostRepository.java          # 文章資料存取
│   ├── ProjectRepository.java       # 作品資料存取
│   └── UserRepository.java          # 使用者資料存取
└── service/
    ├── CustomUserDetailsService.java # 自訂認證服務
    ├── PostService.java             # 文章業務邏輯
    └── ProjectService.java          # 作品業務邏輯
```

## 🚀 環境建置與啟動

### 前置需求
- **Java 17** 以上
- **Maven 3.x**
- **PostgreSQL 17.x**

---

### 步驟 1：安裝 PostgreSQL

#### Windows
1. 前往 [PostgreSQL 官方下載頁面](https://www.postgresql.org/download/windows/)
2. 下載 **Windows x86-64** 安裝檔
3. 安裝過程中的重點設定：
   - **Superuser 密碼**：設定 `postgres` 使用者的密碼（請記住！）
   - **Port**：預設 `5432`，不需更改
   - **Locale**：選擇預設即可
4. 安裝完成後會自動安裝 **pgAdmin 4**（圖形化管理工具）

#### macOS (Homebrew)
```bash
brew install postgresql@17
brew services start postgresql@17
```

---

### 步驟 2：建立資料庫

#### 方法 A：使用 pgAdmin 4（圖形化介面）
1. 開啟 **pgAdmin 4**
2. 連接到本地 Server（密碼為安裝時設定的密碼）
3. 右鍵點擊 **Databases** → **Create** → **Database**
4. 名稱輸入：`dailyblog`
5. 點擊 **Save**

#### 方法 B：使用命令列 (psql)
```bash
# 登入 PostgreSQL
psql -U postgres

# 建立資料庫
CREATE DATABASE dailyblog;

# 確認建立成功
\l

# 退出
\q
```

---

### 步驟 3：設定 application.properties

確認 `src/main/resources/application.properties` 內容如下：
```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/dailyblog
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=你的密碼

# JPA 設定：自動根據 Entity 建立/更新 Table
spring.jpa.hibernate.ddl-auto=update

# 在 Console 顯示 SQL 語句 (方便除錯)
spring.jpa.show-sql=true
```

> ⚠️ **注意**：請將 `spring.datasource.password` 改為你在安裝 PostgreSQL 時設定的密碼。

---

### 步驟 4：啟動專案

```bash
# Clone 專案
git clone https://github.com/TakahiroKasade/daily-blog.git
cd daily-blog

# 使用 Maven 啟動
mvn spring-boot:run
```

### 步驟 5：瀏覽網站
- **首頁**：[http://localhost:8080](http://localhost:8080)

## 📖 技術文件

詳細的開發紀錄與學習筆記請參考 `docs/` 資料夾：
- [CRUD 功能實作](docs/20260210_CRUD功能實作.md)
- [Spring Security 安全機制實作](docs/20260210_SpringSecurity安全機制實作.md)
- [系統架構藍圖](docs/SYSTEM_ARCHITECTURE_BLUEPRINT.md)
- [未來開發路線圖](docs/FUTURE_ROADMAP.md)

## 📝 License
本專案僅供學習用途。
