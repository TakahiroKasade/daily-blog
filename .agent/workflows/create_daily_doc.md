---
description: Create a new technical documentation file in docs/ with the format yyyyMMdd_Description.md
---

This workflow helps you quickly create a standard documentation file for your daily tasks.

1.  Get the current date in yyyyMMdd format.
2.  Ask the user for the "Description" part of the filename (e.g., "EMRFix", "LoginPage").
3.  Create the file in `docs/` folder with a template header.

// turbo
1. run_command powershell -Command "Get-Date -Format 'yyyyMMdd'"

2. Ask user for the description:
   "請輸入文件描述 (例如: EMR拋轉功能調整): "

3. Create the file:
   (Agent will use `write_to_file` to create `docs/<DATE>_<INPUT>.md` with a standard header)
