@echo off
echo ========================================
echo      SỬA LỖI PUSH GITHUB
echo ========================================

echo [1/5] Pull changes từ GitHub...
git pull origin main --allow-unrelated-histories
if %errorlevel% neq 0 (
    echo ⚠️  Có conflict, đang resolve...
    echo Chọn strategy: ours (giữ local changes)
    git pull origin main --allow-unrelated-histories -X ours
)

echo [2/5] Add workflow files...
git add .github/workflows/build-apk.yml
git add HOW_TO_GET_APK.md
git add *.md
git add *.bat

echo [3/5] Commit changes...
git commit -m "🚀 Add GitHub Actions workflow for automatic APK build

✨ Features:
- Automatic APK build on push
- Download APK from Artifacts
- Release APK to GitHub Releases
- Complete build and deployment pipeline

📱 Usage:
1. Go to Actions tab
2. Run 'Build MathKids APK' workflow
3. Download APK from Artifacts
4. Install on Android device"

echo [4/5] Push to GitHub...
git push origin main
if %errorlevel% equ 0 (
    echo ✅ PUSH THÀNH CÔNG!
    echo.
    echo [5/5] Bước tiếp theo:
    echo 1. Vào: https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game
    echo 2. Refresh trang (F5)
    echo 3. Vào tab "Actions"
    echo 4. Bạn sẽ thấy workflow "🚀 Build MathKids APK"
    echo 5. Nhấp "Run workflow" để build APK
    echo 6. Chờ 10-20 phút
    echo 7. Tải APK từ Artifacts
    echo.
    echo 🎉 HOÀN TẤT! Workflow đã sẵn sàng!
) else (
    echo ❌ PUSH VẪN THẤT BẠI
    echo.
    echo 🔧 Thử giải pháp khác:
    echo 1. Force push (cẩn thận):
    echo    git push origin main --force
    echo.
    echo 2. Hoặc tạo branch mới:
    echo    git checkout -b workflow-setup
    echo    git push origin workflow-setup
)

echo ========================================
pause
