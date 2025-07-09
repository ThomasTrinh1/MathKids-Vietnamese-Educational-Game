@echo off
echo ========================================
echo      SỬA LỖI BUILD VÀ TẠO APK
echo ========================================

echo [1/5] Kiểm tra Gradle Wrapper...
if not exist "gradlew.bat" (
    echo ❌ gradlew.bat không tồn tại
    echo Đang tạo lại Gradle Wrapper...
    gradle wrapper
)

echo [2/5] Xóa cache build...
if exist "app\build" (
    rmdir /s /q "app\build"
    echo ✅ Đã xóa cache build
)

if exist ".gradle" (
    rmdir /s /q ".gradle"
    echo ✅ Đã xóa cache gradle
)

echo [3/5] Kiểm tra resource files...
echo Checking critical resource files...
if not exist "app\src\main\res\values\strings.xml" (
    echo ❌ strings.xml missing
    exit /b 1
)
if not exist "app\src\main\res\values\colors.xml" (
    echo ❌ colors.xml missing
    exit /b 1
)
echo ✅ Resource files OK

echo [4/5] Build project...
echo Đang build project với Android Studio...
echo.
echo ⚠️  VUI LÒNG:
echo 1. Mở Android Studio
echo 2. Chọn Build → Clean Project
echo 3. Chọn Build → Rebuild Project
echo 4. Chọn Build → Build Bundle(s) / APK(s) → Build APK(s)
echo.

echo [5/5] Kiểm tra APK sau khi build...
echo Sau khi build xong, chạy: check_apk.bat

pause
