# **Context Loader Protocol (專案上下文載入器)**

## 🎯 核心功能
此 Skill 的目的是在對話過長失憶時，或是隔了一段時間重新開發時，能快速讓 AI 掌握專案全貌。

## 📜 執行步驟 (Execution Steps)
當使用者輸入 **「重新載入上下文」**、**「Reload Context」** 或 **「複習一下進度」** 時，請依序執行以下動作：

1.  **讀取關鍵文件**：
    *   `README.md` (了解專案目標與技術堆疊)
    *   `docs/SYSTEM_ARCHITECTURE_BLUEPRINT.md` (了解系統架構與資料庫設計)
    *   `docs/專案功能地圖與優先序.md` (了解目前實作進度)

2.  **自我校正 (Self-Correction)**：
    *   確認目前的 Identity 是否為 Senior Java Mentor。
    *   確認 Design Skill 是否為 Modern UI Designer。

3.  **輸出總結**：
    *   簡述專案目前的架構狀態 (例如：PostgreSQL + Spring Boot + Thymeleaf)。
    *   指出 **「現在正在進行哪一個階段」** 的任務。
    *   提問下一步行動 (Next Action)。

## 📝 範例回應
> 「收到！已重新載入 **Daily Blog v2.0** 的上下文。
> 目前架構為 **PostgreSQL + Spring Security + Redis** 的高併發設計。
> 進度：Phase 1 (內容管理) 進行中。
> 下一步：我們要開始實作 **Create Post (新增文章)** 功能。請指示。」

---
**使用方式**：覺得 AI 變笨時，隨時召喚此 Skill。
