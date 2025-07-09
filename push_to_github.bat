@echo off
echo ========================================
echo    PUSH MATHKIDS LÊN GITHUB
echo ========================================

echo [1/4] Kiểm tra Git status...
git status

echo.
echo [2/4] Tạo repository trên GitHub...
echo ⚠️  HƯỚNG DẪN MANUAL:
echo 1. Mở https://github.com/new
echo 2. Repository name: MathKids-Vietnamese-Educational-Game
echo 3. Description: 🎮 Educational Android game for Vietnamese children to learn math - Ứng dụng học toán cho trẻ em Việt Nam
echo 4. Chọn Public hoặc Private
echo 5. KHÔNG tích "Add a README file"
echo 6. Nhấn "Create repository"
echo.

pause
echo.

echo [3/4] Nhập URL repository GitHub:
set /p GITHUB_URL="Nhập GitHub repository URL (https://github.com/username/repo.git): "

if "%GITHUB_URL%"=="" (
    echo ❌ Chưa nhập URL repository
    pause
    exit /b 1
)

echo.
echo [4/4] Push code lên GitHub...
echo Đang thêm remote origin...
git remote add origin %GITHUB_URL%

echo Đang push lên GitHub...
git branch -M main
git push -u origin main

if %errorlevel% equ 0 (
    echo.
    echo ✅ PUSH THÀNH CÔNG!
    echo 🎉 Code đã được push lên GitHub
    echo 📱 Repository: %GITHUB_URL%
    echo.
    echo 🔗 Bạn có thể xem tại:
    echo %GITHUB_URL:~0,-4%
) else (
    echo.
    echo ❌ PUSH THẤT BẠI
    echo Có thể do:
    echo - URL repository không đúng
    echo - Chưa đăng nhập GitHub
    echo - Repository đã tồn tại với nội dung khác
    echo.
    echo 🔧 Giải pháp:
    echo 1. Kiểm tra URL repository
    echo 2. Đăng nhập GitHub: git config --global credential.helper manager
    echo 3. Hoặc dùng GitHub Desktop
)

echo.
echo ========================================
echo        HOÀN THÀNH
echo ========================================
pause
