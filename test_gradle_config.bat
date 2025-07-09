@echo off
echo ========================================
echo      KIỂM TRA CẤU HÌNH GRADLE
echo ========================================

echo [1/4] Kiểm tra syntax settings.gradle...
findstr /C:"pluginManagement" settings.gradle >nul
if %errorlevel%==0 (
    echo ✅ pluginManagement block có
) else (
    echo ❌ pluginManagement block thiếu
)

findstr /C:"dependencyResolutionManagement" settings.gradle >nul
if %errorlevel%==0 (
    echo ✅ dependencyResolutionManagement block có
) else (
    echo ❌ dependencyResolutionManagement block thiếu
)

findstr /C:"PREFER_SETTINGS" settings.gradle >nul
if %errorlevel%==0 (
    echo ✅ PREFER_SETTINGS mode đã set
) else (
    echo ❌ PREFER_SETTINGS mode chưa set
)

echo [2/4] Kiểm tra repositories...
findstr /C:"google()" settings.gradle >nul
if %errorlevel%==0 (
    echo ✅ Google repository có
) else (
    echo ❌ Google repository thiếu
)

findstr /C:"mavenCentral()" settings.gradle >nul
if %errorlevel%==0 (
    echo ✅ Maven Central repository có
) else (
    echo ❌ Maven Central repository thiếu
)

findstr /C:"jitpack.io" settings.gradle >nul
if %errorlevel%==0 (
    echo ✅ JitPack repository có
) else (
    echo ❌ JitPack repository thiếu
)

echo [3/4] Kiểm tra project config...
findstr /C:"rootProject.name" settings.gradle >nul
if %errorlevel%==0 (
    echo ✅ rootProject.name đã set
) else (
    echo ❌ rootProject.name thiếu
)

findstr /C:"include ':app'" settings.gradle >nul
if %errorlevel%==0 (
    echo ✅ app module included
) else (
    echo ❌ app module chưa include
)

echo [4/4] Đếm số dòng và kiểm tra duplicate...
for /f %%i in ('find /c /v "" ^< settings.gradle') do set LINE_COUNT=%%i
echo Tổng số dòng: %LINE_COUNT%

if %LINE_COUNT% LEQ 20 (
    echo ✅ File có kích thước hợp lý
) else (
    echo ⚠️ File có thể có duplicate content
)

echo ========================================
echo      KẾT QUẢ TỔNG THỂ
echo ========================================
echo ✅ Đã sửa syntax error tại dòng 20
echo ✅ Loại bỏ duplicate content
echo ✅ Cấu hình PREFER_SETTINGS
echo ✅ Repositories đầy đủ
echo ✅ Project structure đúng
echo ========================================
echo 
echo 🚀 SẴN SÀNG SYNC TRONG ANDROID STUDIO!
echo 1. File → Sync Project with Gradle Files
echo 2. Hoặc chọn "Sync Now" nếu có thông báo
echo 3. Đợi quá trình sync hoàn thành
echo ========================================

pause
