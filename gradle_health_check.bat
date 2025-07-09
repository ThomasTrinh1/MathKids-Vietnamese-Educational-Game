@echo off
echo ========================================
echo      KIỂM TRA TÌNH TRẠNG GRADLE
echo ========================================

echo [1/6] Kiểm tra files cần thiết...
set MISSING_FILES=0

if not exist "gradle\wrapper\gradle-wrapper.properties" (
    echo ❌ gradle-wrapper.properties THIẾU
    set /a MISSING_FILES+=1
) else (
    echo ✅ gradle-wrapper.properties
)

if not exist "gradle\wrapper\gradle-wrapper.jar" (
    echo ❌ gradle-wrapper.jar THIẾU
    set /a MISSING_FILES+=1
) else (
    echo ✅ gradle-wrapper.jar
    for %%A in ("gradle\wrapper\gradle-wrapper.jar") do echo    Kích thước: %%~zA bytes
)

if not exist "gradlew.bat" (
    echo ❌ gradlew.bat THIẾU
    set /a MISSING_FILES+=1
) else (
    echo ✅ gradlew.bat
)

if not exist "settings.gradle" (
    echo ❌ settings.gradle THIẾU
    set /a MISSING_FILES+=1
) else (
    echo ✅ settings.gradle
)

if not exist "build.gradle" (
    echo ❌ build.gradle THIẾU
    set /a MISSING_FILES+=1
) else (
    echo ✅ build.gradle
)

echo [2/6] Kiểm tra cấu hình settings.gradle...
findstr /C:"PREFER_SETTINGS" settings.gradle >nul
if %errorlevel%==0 (
    echo ✅ repositoriesMode = PREFER_SETTINGS
) else (
    echo ❌ repositoriesMode KHÔNG ĐÚNG
    set /a MISSING_FILES+=1
)

echo [3/6] Kiểm tra build.gradle...
findstr /C:"allprojects" build.gradle >nul
if %errorlevel%==0 (
    echo ❌ Vẫn còn allprojects repositories (XẢY RA XUNG ĐỘT)
    set /a MISSING_FILES+=1
) else (
    echo ✅ Không có allprojects repositories
)

echo [4/6] Kiểm tra compatibility...
findstr /C:"8.2.0" build.gradle >nul
if %errorlevel%==0 (
    echo ✅ Android Gradle Plugin 8.2.0
) else (
    echo ❌ AGP version không đúng
    set /a MISSING_FILES+=1
)

findstr /C:"gradle-8.2-bin.zip" gradle\wrapper\gradle-wrapper.properties >nul
if %errorlevel%==0 (
    echo ✅ Gradle 8.2
) else (
    echo ❌ Gradle version không đúng
    set /a MISSING_FILES+=1
)

echo [5/6] Tạo backup cấu hình...
if not exist "backup" mkdir "backup"
copy "settings.gradle" "backup\settings.gradle.backup" >nul 2>&1
copy "build.gradle" "backup\build.gradle.backup" >nul 2>&1
copy "gradle\wrapper\gradle-wrapper.properties" "backup\gradle-wrapper.properties.backup" >nul 2>&1
echo ✅ Đã backup cấu hình vào thư mục backup\

echo [6/6] Kết quả tổng thể...
echo ========================================
if %MISSING_FILES%==0 (
    echo      🎉 GRADLE SETUP HOÀN HẢO!
    echo ========================================
    echo ✅ Tất cả files đều có
    echo ✅ Cấu hình đúng chuẩn
    echo ✅ Không có xung đột repositories
    echo ✅ Version compatibility OK
    echo ✅ Backup đã được tạo
    echo ========================================
    echo 🚀 SẴN SÀNG SYNC TRONG ANDROID STUDIO!
) else (
    echo      ❌ CÓ %MISSING_FILES% VẤN ĐỀ CẦN SỬA
    echo ========================================
    echo Hãy chạy ultimate_gradle_fix.bat để sửa
)

echo ========================================
pause
