@echo off
echo ========================================
echo    HOÀN TẤT PUSH SAU MERGE
echo ========================================

echo [1/4] Kiểm tra Git status...
git status

echo [2/4] Add workflow files...
git add .github/workflows/build-apk.yml
git add HOW_TO_GET_APK.md
git add *.md
git add *.bat

echo [3/4] Commit workflow...
git commit -m "🚀 Add GitHub Actions workflow for automatic APK build"

echo [4/4] Push to GitHub...
git push origin main

if %errorlevel% equ 0 (
    echo ✅ PUSH THÀNH CÔNG!
    echo.
    echo 🎉 WORKFLOW ĐÃ ĐƯỢC PUSH LÊN GITHUB!
    echo.
    echo 📋 Bước tiếp theo:
    echo 1. Vào: https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game
    echo 2. Refresh trang (F5)
    echo 3. Vào tab "Actions"
    echo 4. Bạn sẽ thấy workflow "🚀 Build MathKids APK"
    echo 5. Nhấp "Run workflow" để build APK
    echo 6. Chờ 10-20 phút để build hoàn tất
    echo 7. Tải APK từ phần "Artifacts"
    echo.
    echo 🎮 APK sẽ sẵn sàng để cài đặt trên Android!
) else (
    echo ❌ PUSH THẤT BẠI
    echo Kiểm tra lỗi và thử lại
)

echo ========================================
pause
