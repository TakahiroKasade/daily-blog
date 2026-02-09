---
name: github-deploy
description: GitHub 版本控管與部署流程 - 標準化的 Git 工作流程
---

# GitHub Deploy Skill

## 概述
標準化的 GitHub 版本控管與部署流程，確保程式碼品質與部署一致性。

## Git 工作流程

### 分支策略 (Git Flow)
```
main (production)
  └── develop
       ├── feature/xxx
       ├── bugfix/xxx
       └── hotfix/xxx
```

### 分支命名規範
- `feature/功能名稱` - 新功能開發
- `bugfix/問題描述` - 錯誤修復
- `hotfix/緊急修復` - 生產環境緊急修復
- `release/版本號` - 發布準備

## 使用方式

### 初始化專案
```
請幫我初始化 GitHub repository
```

### 每日提交
```
請幫我進行今日 Git 提交
```

### 發布版本
```
請幫我發布新版本: v1.0.0
```

## Commit Message 規範

格式: `<type>(<scope>): <subject>`

### Type 類型
| Type | 說明 |
|------|-----|
| `feat` | 新功能 |
| `fix` | 修復 Bug |
| `docs` | 文件更新 |
| `style` | 程式碼格式 (不影響功能) |
| `refactor` | 重構 (不是新功能也不是修 Bug) |
| `perf` | 性能優化 |
| `test` | 新增或修改測試 |
| `chore` | 建置工具或輔助工具變更 |

### 範例
```
feat(wallet): 新增匯率轉換功能
fix(auth): 修復登入後 redirect 問題
docs(readme): 更新安裝說明
perf(query): 優化使用者查詢 N+1 問題
```

## 標準提交流程

```bash
# 1. 確認狀態
git status

# 2. 加入變更
git add .

# 3. 提交 (遵循 commit 規範)
git commit -m "feat(module): 功能說明"

# 4. 推送
git push origin <branch>
```

## Pull Request 檢查清單

提交 PR 前確認：
- [ ] 符合 commit message 規範
- [ ] 通過所有測試
- [ ] 無 lint 錯誤
- [ ] 更新相關文件
- [ ] 無敏感資訊 (API Key, 密碼等)
- [ ] Code Review 完成

## 部署流程

### 開發環境
```bash
# develop 分支自動部署到 staging
git push origin develop
```

### 生產環境
```bash
# 1. 建立 release 分支
git checkout -b release/v1.0.0

# 2. 合併到 main
git checkout main
git merge release/v1.0.0

# 3. 建立 tag
git tag -a v1.0.0 -m "Release v1.0.0"
git push origin v1.0.0

# 4. 部署到生產環境
# (依據 CI/CD 設定自動觸發)
```

## .gitignore 標準配置

```gitignore
# IDE
.idea/
.vscode/
*.iml

# Build
target/
build/
dist/

# Dependencies
node_modules/

# Environment
.env
.env.local
*.env

# Logs
*.log
logs/

# OS
.DS_Store
Thumbs.db

# Secrets
**/secrets/
*.pem
*.key
```

## GitHub Actions 範例

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn clean package
      - name: Run tests
        run: mvn test
```
