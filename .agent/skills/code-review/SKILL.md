---
name: code-review
description: 全方位程式碼審查 - 包含 Clean Code、資安檢測、性能優化建議
---

# Code Review Skill

## 概述
這個 skill 提供全面的程式碼審查服務，涵蓋 Clean Code 原則、資安漏洞檢測、性能優化建議。

## 審查項目

### 1. Clean Code 檢查
- [ ] **命名規範**: 變數、函式、類別命名是否清晰易懂
- [ ] **函式長度**: 單一函式不超過 20-30 行
- [ ] **單一職責**: 每個函式/類別只做一件事
- [ ] **DRY 原則**: 避免重複程式碼
- [ ] **註解品質**: 註解是否有意義，而非解釋顯而易見的程式碼
- [ ] **Magic Number**: 是否使用常數取代魔術數字
- [ ] **程式碼格式**: 縮排、空白、換行是否一致

### 2. 資安檢測
- [ ] **SQL Injection**: 檢查是否使用參數化查詢
- [ ] **XSS 防護**: 檢查輸出是否有適當 escape
- [ ] **CSRF 防護**: 檢查是否有 CSRF Token
- [ ] **敏感資訊**: 檢查密碼、API Key 是否硬編碼
- [ ] **認證授權**: 檢查權限控制是否完整
- [ ] **Input Validation**: 檢查輸入驗證是否完善
- [ ] **依賴套件**: 檢查是否有已知漏洞的套件

### 3. 性能優化
- [ ] **N+1 查詢**: 檢查 ORM 是否有 N+1 問題
- [ ] **索引使用**: 檢查資料庫查詢是否有使用索引
- [ ] **快取策略**: 檢查是否有適當的快取機制
- [ ] **延遲載入**: 檢查是否有適當的 Lazy Loading
- [ ] **批次處理**: 大量資料是否使用批次處理
- [ ] **非同步處理**: 耗時操作是否使用非同步
- [ ] **資源釋放**: 檢查資源是否有正確釋放 (try-with-resources)

## 使用方式

```
請幫我進行 code review: [檔案路徑或目錄]
```

## 輸出格式

審查報告將包含：
1. **嚴重程度分級**: 🔴 Critical / 🟠 Major / 🟡 Minor / 🟢 Info
2. **問題位置**: 檔案名稱與行號
3. **問題描述**: 清楚說明問題
4. **修復建議**: 提供具體的修改方案
5. **程式碼範例**: 修改前後的對比

## 範例輸出

```markdown
## Code Review Report

### 🔴 Critical Issues

#### [SecurityController.java:45] SQL Injection 風險
**問題**: 直接拼接 SQL 字串
```java
// ❌ Before
String sql = "SELECT * FROM users WHERE id = " + userId;
```

**建議**: 使用參數化查詢
```java
// ✅ After
@Query("SELECT u FROM User u WHERE u.id = :userId")
User findByUserId(@Param("userId") Long userId);
```

---

### 🟠 Major Issues

#### [UserService.java:78] 違反單一職責原則
**問題**: 此方法同時處理驗證、儲存、發送郵件
**建議**: 拆分為獨立的方法
```
