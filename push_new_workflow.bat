@echo off
echo ========================================
echo    PUSH WORKFLOW MỚI (ĐƠN GIẢN HƠN)
echo ========================================

echo [1/4] Add workflow mới...
git add .github/workflows/build-simple.yml

echo [2/4] Commit workflow...
git commit -m "🔧 Add simple workflow to fix build issues

✨ Improvements:
- Simplified build process
- Better error handling
- No caching issues
- Direct APK build
- Reduced complexity

🎯 This should resolve the previous build failures"

echo [3/4] Push to GitHub...
git push origin main

if %errorlevel% equ 0 (
    echo ✅ PUSH THÀNH CÔNG!
    echo.
    echo 🎉 WORKFLOW MỚI ĐÃ ĐƯỢC THÊM!
    echo.
    echo 📋 Bước tiếp theo:
    echo 1. Vào: https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game
    echo 2. Refresh trang (F5)
    echo 3. Vào tab "Actions"
    echo 4. Tìm workflow "🎮 Build MathKids APK (Simple)"
    echo 5. Nhấp "Run workflow"
    echo 6. Chờ build hoàn tất
    echo 7. Tải APK từ Artifacts
    echo.
    echo 🚀 Workflow mới đơn giản hơn, ít lỗi hơn!
) else (
    echo ❌ PUSH THẤT BẠI
    echo Kiểm tra lỗi và thử lại
)

echo ========================================
pause
