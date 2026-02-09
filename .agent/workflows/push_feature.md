---
description: Commit and push changes to the current git branch (Safe Guard against Main)
---

This workflow commits your current changes and pushes them to the remote repository, ensuring you are NOT pushing to main directly without checks.

1. Check current status.
// turbo
2. run_command git status

3. Check current branch name.
// turbo
4. run_command git branch --show-current

5. Ask user for commit message.
   "請輸入 Commit Message (繁體中文): "

6. Execute Add, Commit, and Push.
   (Agent will run: `git add .`, `git commit -m "..."`, `git push origin <branch_name>`)

> [!IMPORTANT]
> Agent MUST verify the branch is NOT `main` or `master`. If it is, ask for double confirmation.
