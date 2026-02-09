---
name: project-overview
description: 專案架構分析與註解生成 - MVC 架構檢視、自動生成程式碼註解
---

# Project Overview Skill

## 概述
分析專案架構，確保 MVC 分層明確，並為程式碼自動生成專業註解，讓開發者快速掌握專案脈絡。

## MVC 架構檢查

### 標準目錄結構 (Java Spring Boot)
```
src/main/java/com/project/
├── config/           # 設定檔
│   └── SecurityConfig.java
├── controller/       # Controller 層 - 處理 HTTP 請求
│   ├── UserController.java
│   └── ApiController.java
├── service/          # Service 層 - 業務邏輯
│   ├── UserService.java
│   └── impl/
│       └── UserServiceImpl.java
├── repository/       # Repository 層 - 資料存取
│   └── UserRepository.java
├── model/            # Model 層 - 資料模型
│   ├── entity/       # JPA Entity
│   ├── dto/          # Data Transfer Object
│   └── vo/           # Value Object
├── exception/        # 例外處理
├── util/             # 工具類別
└── Application.java  # 主程式進入點
```

### 前端結構 (JavaScript)
```
src/main/resources/static/
├── css/
│   └── style.css
├── js/
│   ├── app.js        # 主程式
│   ├── api.js        # API 呼叫
│   ├── components/   # 元件
│   └── utils/        # 工具函式
└── images/
```

## 使用方式

### 生成專案總覽
```
請幫我分析專案架構
```

### 為檔案添加註解
```
請幫 [檔案路徑] 添加專業註解
```

### 檢查 MVC 分層
```
請檢查專案的 MVC 架構是否分明
```

## 註解規範

### Java 類別註解
```java
/**
 * 使用者控制器
 * <p>
 * 處理所有與使用者相關的 HTTP 請求，包括：
 * - 使用者註冊
 * - 使用者登入/登出
 * - 個人資料管理
 * </p>
 *
 * @author [作者名稱]
 * @version 1.0
 * @since 2026-01-17
 * @see UserService
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    // ...
}
```

### Java 方法註解
```java
/**
 * 根據 ID 查詢使用者資訊
 *
 * @param id 使用者 ID (必填)
 * @return 使用者詳細資訊的 DTO
 * @throws UserNotFoundException 當使用者不存在時拋出
 * @since 1.0
 */
public UserDTO findById(Long id) {
    // ...
}
```

### JavaScript 函式註解
```javascript
/**
 * 發送 API 請求的通用函式
 * 
 * @description 封裝 fetch API，自動處理認證 token 和錯誤回應
 * @param {string} url - API 端點 URL
 * @param {Object} options - 請求選項
 * @param {string} [options.method='GET'] - HTTP 方法
 * @param {Object} [options.body] - 請求 body
 * @returns {Promise<Object>} API 回應資料
 * @throws {ApiError} 當 API 回應錯誤時
 * 
 * @example
 * const user = await apiRequest('/api/users/1');
 * 
 * @example
 * const newUser = await apiRequest('/api/users', {
 *   method: 'POST',
 *   body: { name: 'John' }
 * });
 */
async function apiRequest(url, options = {}) {
    // ...
}
```

## MVC 分層檢查清單

### Controller 層
- [ ] 只處理 HTTP 請求/回應
- [ ] 不包含業務邏輯
- [ ] 呼叫 Service 層處理業務
- [ ] 適當的 HTTP 狀態碼回應
- [ ] 輸入驗證 (@Valid)

### Service 層
- [ ] 包含所有業務邏輯
- [ ] 交易管理 (@Transactional)
- [ ] 呼叫 Repository 存取資料
- [ ] 不直接處理 HTTP 相關物件

### Repository 層
- [ ] 只負責資料存取
- [ ] 使用 JPA/JDBC 規範
- [ ] 適當的查詢優化
- [ ] 不包含業務邏輯

### Model 層
- [ ] Entity: 對應資料庫表
- [ ] DTO: API 傳輸用
- [ ] VO: 顯示層用

## 輸出範例

### 專案架構報告
```markdown
# 專案架構分析報告

## 📊 整體評分: 85/100

## ✅ 良好實踐
- Controller 層職責明確
- Service 層業務邏輯集中
- 使用 DTO 隔離 Entity

## ⚠️ 待改善項目
1. **UserController.java:45** - 包含業務邏輯，應移至 Service
2. **OrderService.java** - 缺少交易管理註解
3. **utils/ 目錄** - 工具類別職責不明確

## 📁 目錄結構建議
[建議的目錄結構調整]
```
