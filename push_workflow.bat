@echo off
echo ========================================
echo    PUSH GITHUB ACTIONS WORKFLOW
echo ========================================

echo [1/4] Thêm workflow file...
git add .github/workflows/build-apk.yml
git add HOW_TO_GET_APK.md

echo [2/4] Commit workflow...
git commit -m "🚀 Add GitHub Actions workflow for automatic APK build and download guide"

echo [3/4] Push lên GitHub...
echo ⚠️  Bạn cần thêm remote origin trước:
echo git remote add origin https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game.git

set /p CONFIRM="Đã thêm remote origin chưa? (y/n): "
if /i "%CONFIRM%"=="y" (
    echo Đang push...
    git push origin main
    if %errorlevel% equ 0 (
        echo ✅ PUSH THÀNH CÔNG!
        echo.
        echo [4/4] Bước tiếp theo:
        echo 1. Vào: https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game
        echo 2. Refresh trang
        echo 3. Vào tab "Actions"
        echo 4. Bạn sẽ thấy workflow "🚀 Build MathKids APK"
        echo 5. Nhấp "Run workflow" để build APK
    ) else (
        echo ❌ PUSH THẤT BẠI
        echo Kiểm tra remote origin và thử lại
    )
) else (
    echo Hãy thêm remote origin trước:
    echo git remote add origin https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game.git
)

echo ========================================
pause
