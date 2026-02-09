# **Modern UI/UX Designer Protocol (現代化前端設計師模式)**

## 🎨 核心設計哲學
本專案追求 **"Premium & Dynamic"** (高級與動態) 的視覺體驗。
當啟動此模式時，請遵照以下設計準則進行前端開發：

### 1. 視覺風格 (Visual Style)
*   **色彩系統 (Color System)**：
    *   **主色調**：使用深邃的靛藍色 `Primary: #4e73df` 與紫色 `Secondary: #858796` 漸層。
    *   **背景**：避免純白 `#FFFFFF`，使用微灰 `#F8F9FC` 或玻璃擬態 (Glassmorphism)。
    *   **陰影**：使用多層次柔和陰影 `box-shadow: 0 .15rem 1.75rem 0 rgba(58,59,69,.15)`。
*   **字體排印 (Typography)**：
    *   英文使用 `Inter` 或 `Roboto`。
    *   中文使用 `Noto Sans TC` (思源黑體)。
    *   標題與內文對比度需充足，行距使用 `line-height: 1.6`。

### 2. 互動體驗 (Interaction)
*   **微動畫 (Micro-interactions)**：所有按鈕、卡片 Hover 時必須有位移或縮放效果 (`transform: translateY(-5px)`).
*   **過場效果**：頁面載入與元素出現需有 `Fade-in` 動畫。
*   **回饋感**：表單送出或按鈕點擊後，必須有 Loading Spinner 或 Toast 通知。

### 3. 技術規範 (Tech Rules)
*   **CSS 框架**：基於 **Bootstrap 5**，但必須覆寫其預設變數 (Root Variables)。
*   **圖示庫**：使用 **FontAwesome 6** 或 **Bootstrap Icons**。
*   **JavaScript**：
    *   ❌ 禁止使用 jQuery (太肥大)。
    *   ✅ 使用 Vanilla JS (原生 ES6+) 撰寫互動邏輯。

---

## 🛠 實作檢核表 (Checklist)
在設計任何頁面時，請自我檢查：
- [ ] RWD 響應式：手機版 (Mobile) 選單是否正常摺疊？
- [ ] Accessibility：圖片是否有 `alt`？按鈕是否有 `aria-label`？
- [ ] Performance：是否引入了過大的圖片或無用的 CSS？
- [ ] Hierarchy：視覺動線是否清晰 (H1 -> H2 -> 內文)？

---
**使用方式**：需要美化頁面時，請對 AI 說：「召喚 UI Designer Skill」。
