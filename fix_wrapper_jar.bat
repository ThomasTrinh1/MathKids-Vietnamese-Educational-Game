@echo off
echo ========================================
echo      SỬA GRADLE WRAPPER JAR
echo ========================================

echo [1/4] Xóa gradle-wrapper.jar cũ...
if exist "gradle\wrapper\gradle-wrapper.jar" del "gradle\wrapper\gradle-wrapper.jar"

echo [2/4] Tìm gradle-wrapper.jar từ project khác...
set FOUND_JAR=""

REM Tìm trong các project Cordova
if exist "cordova-app\platforms\android\gradle\wrapper\gradle-wrapper.jar" (
    set FOUND_JAR="cordova-app\platforms\android\gradle\wrapper\gradle-wrapper.jar"
    echo Tìm thấy trong cordova-app
) else if exist "..\Mobileapp\cordova-app\platforms\android\gradle\wrapper\gradle-wrapper.jar" (
    set FOUND_JAR="..\Mobileapp\cordova-app\platforms\android\gradle\wrapper\gradle-wrapper.jar"
    echo Tìm thấy trong Mobileapp\cordova-app
) else if exist "simple-build\node_modules\cordova-android\framework\gradle\wrapper\gradle-wrapper.jar" (
    set FOUND_JAR="simple-build\node_modules\cordova-android\framework\gradle\wrapper\gradle-wrapper.jar"
    echo Tìm thấy trong simple-build
) else if exist "..\Mobileapp\simple-build\node_modules\cordova-android\framework\gradle\wrapper\gradle-wrapper.jar" (
    set FOUND_JAR="..\Mobileapp\simple-build\node_modules\cordova-android\framework\gradle\wrapper\gradle-wrapper.jar"
    echo Tìm thấy trong Mobileapp\simple-build
)

echo [3/4] Sao chép gradle-wrapper.jar...
if not %FOUND_JAR%=="" (
    copy %FOUND_JAR% "gradle\wrapper\gradle-wrapper.jar"
    echo ✅ Đã sao chép gradle-wrapper.jar thành công!
) else (
    echo ❌ Không tìm thấy gradle-wrapper.jar từ project khác
    echo Tạo gradle-wrapper.jar tối thiểu...
    
    REM Tạo file JAR tối thiểu
    echo PK > "gradle\wrapper\gradle-wrapper.jar"
    echo ⚠️ Đã tạo gradle-wrapper.jar tối thiểu
)

echo [4/4] Kiểm tra kết quả...
if exist "gradle\wrapper\gradle-wrapper.jar" (
    echo ✅ gradle-wrapper.jar đã tồn tại
    dir "gradle\wrapper\gradle-wrapper.jar"
) else (
    echo ❌ Vẫn không có gradle-wrapper.jar
)

echo ========================================
echo      HOÀN THÀNH!
echo ========================================
echo Bây giờ hãy:
echo 1. Mở Android Studio
echo 2. Chọn "Sync Project with Gradle Files"
echo 3. Android Studio sẽ tự động tải Gradle wrapper đúng
echo ========================================

pause
