# SDD: 系統管理後台與行為分析系統 (Admin Dashboard & Audit Log)

## 1. 目標 (Objective)
建立一個具備視覺化數據分析、動態權限管理以及自動化操作審計功能的管理後台，提升系統的可維護性與安全性。

## 2. 需求分析 (Requirements)

### 功能需求 (Functional)
- **行為追蹤**：自動紀錄管理員與使用者的敏感操作（CRUD）。
- **動態 RBAC**：提供 UI 介面，無需重啟程式即可修改角色與權限的綁定關係。
- **數據分析**：統計系統活躍度、文章分類比例、操作成功率等，並以圖表呈現。

### 非功能需求 (Non-Functional)
- **非侵入性**：日誌功能應透過 AOP 實作，避免污染業務邏輯。
- **回應速度**：後台報表查詢應在 500ms 內完成。

## 3. 設計方案 (Design)

### 3.1 數據模型 (Data Model)
新增 `AuditLog` 實體：
- `id`: PK
- `operator`: 操作者帳號
- `operation`: 動作描述
- `method`: 呼叫的方法路徑
- `params`: 請求參數 (JSON 格式)
- `status`: 執行結果 (SUCCESS/FAIL)
- `executionTime`: 耗時 (ms)
- `createdAt`: 紀錄時間

### 3.2 核心技術棧
- **Spring AOP**: 用於橫切攔截操作日誌。
- **Spring Security**: 用於動態權限載入。
- **Chart.js**: 用於前端報表繪製。

## 4. 實作路徑 (Implementation Roadmap)
1. **Phase 1**: 建立 Audit Log 基礎設施 (Entity, Aspect, Annotation)。
2. **Phase 2**: 實作管理員專屬的動態 RBAC 介面。
3. **Phase 3**: 實作統計 API 與 Chart.js 儀表板。

## 5. 定義完成 (Definition of Done)
- [ ] 所有敏感 API 均有日誌紀錄。
- [ ] 管理員可於網頁直接修改角色權限並立即生效。
- [ ] 儀表板可正確顯示至少三種數據圖表。
