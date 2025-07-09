@echo off
echo ========================================
echo    PUSH WORKFLOW DIRECT BUILD
echo ========================================

echo [1/4] Add workflow mới (không cần gradlew)...
git add .github/workflows/build-direct.yml

echo [2/4] Commit workflow...
git commit -m "🎯 Add direct Gradle build workflow (bypass gradlew issues)

✨ Features:
- Uses Gradle directly from GitHub Actions
- No dependency on local gradlew files
- Bypasses all gradlew corruption issues
- Direct APK build with gradle command

🔧 This should finally work without gradlew problems!"

echo [3/4] Push to GitHub...
git push origin main

if %errorlevel% equ 0 (
    echo ✅ PUSH THÀNH CÔNG!
    echo.
    echo 🎉 WORKFLOW DIRECT BUILD ĐÃ ĐƯỢC THÊM!
    echo.
    echo 📋 Bước tiếp theo:
    echo 1. Vào: https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game
    echo 2. Refresh trang (F5)
    echo 3. Vào tab "Actions"
    echo 4. Tìm workflow "🎯 MathKids Direct Build (No Gradlew)"
    echo 5. Nhấp "Run workflow"
    echo 6. Chờ build hoàn tất
    echo 7. Tải APK từ "mathkids-apk-direct"
    echo.
    echo 🚀 Workflow này KHÔNG SỬ DỤNG gradlew nên sẽ thành công!
) else (
    echo ❌ PUSH THẤT BẠI
    echo Kiểm tra lỗi và thử lại
)

echo ========================================
pause
