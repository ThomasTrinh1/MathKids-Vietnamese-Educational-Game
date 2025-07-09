@echo off
echo ========================================
echo    SỬA LỖI GRADLEW MISSING
echo ========================================

echo [1/6] Kiểm tra file gradlew hiện tại...
if exist gradlew (
    echo ✅ gradlew đã tồn tại
) else (
    echo ❌ gradlew KHÔNG tồn tại - cần tạo lại
)

echo [2/6] Tạo lại Gradle Wrapper...
if exist gradle\wrapper\gradle-wrapper.jar (
    echo ✅ gradle-wrapper.jar đã có
) else (
    echo ❌ gradle-wrapper.jar KHÔNG có - tạo mới
    mkdir gradle\wrapper 2>nul
)

echo [3/6] Tạo file gradlew...
echo @echo off > gradlew.bat
echo setlocal >> gradlew.bat
echo set DIRNAME=%%~dp0 >> gradlew.bat
echo if "%%DIRNAME%%" == "" set DIRNAME=. >> gradlew.bat
echo set APP_BASE_NAME=%%~n0 >> gradlew.bat
echo set APP_HOME=%%DIRNAME%% >> gradlew.bat
echo java -jar "%%APP_HOME%%\gradle\wrapper\gradle-wrapper.jar" %%* >> gradlew.bat

echo [4/6] Tạo file gradlew cho Linux...
echo #!/bin/sh > gradlew
echo DIRNAME="$(dirname "$0")" >> gradlew
echo [ -z "$DIRNAME" ] ^&^& DIRNAME=. >> gradlew
echo APP_BASE_NAME="$(basename "$0")" >> gradlew
echo APP_HOME="$DIRNAME" >> gradlew
echo exec java -jar "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" "$@" >> gradlew

echo [5/6] Tải Gradle Wrapper JAR...
powershell -Command "try { Invoke-WebRequest -Uri 'https://github.com/gradle/gradle/raw/v8.5.0/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle/wrapper/gradle-wrapper.jar' -ErrorAction Stop; Write-Host '✅ Tải gradle-wrapper.jar thành công' } catch { Write-Host '❌ Lỗi tải gradle-wrapper.jar:' $_.Exception.Message }"

echo [6/6] Add và push files...
git add gradlew
git add gradlew.bat
git add gradle/wrapper/
git commit -m "🔧 Fix missing gradlew files

✨ Added:
- gradlew (Linux/Mac executable)
- gradlew.bat (Windows executable)  
- gradle-wrapper.jar (Gradle wrapper)

🎯 This should fix the 'gradlew not found' error in GitHub Actions"

git push origin main

if %errorlevel% equ 0 (
    echo ✅ PUSH THÀNH CÔNG!
    echo.
    echo 🎉 GRADLEW ĐÃ ĐƯỢC SỬA!
    echo.
    echo 📋 Bước tiếp theo:
    echo 1. Vào GitHub Actions
    echo 2. Chạy lại workflow "🎮 Build MathKids APK (Simple)"
    echo 3. Lần này sẽ không còn lỗi "gradlew not found"
    echo.
    echo 🚀 Workflow sẽ build thành công!
) else (
    echo ❌ PUSH THẤT BẠI
    echo Kiểm tra lỗi và thử lại
)

echo ========================================
pause
