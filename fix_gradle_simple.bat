@echo off
echo ========================================
echo      SỬA LỖI GRADLE SYNC ĐƠN GIẢN
echo ========================================

echo [1/4] Đóng Android Studio nếu đang mở...
taskkill /f /im "studio64.exe" 2>nul

echo [2/4] Dọn dẹp cache Gradle...
if exist ".gradle" rmdir /s /q ".gradle"
if exist "app\.gradle" rmdir /s /q "app\.gradle"
if exist "build" rmdir /s /q "build"
if exist "app\build" rmdir /s /q "app\build"

echo [3/4] Tạo Gradle wrapper đơn giản...
if not exist "gradle\wrapper" mkdir "gradle\wrapper"

echo [4/4] Tạo gradle wrapper properties...
echo distributionBase=GRADLE_USER_HOME > gradle\wrapper\gradle-wrapper.properties
echo distributionPath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties
echo distributionUrl=https\://services.gradle.org/distributions/gradle-8.2-bin.zip >> gradle\wrapper\gradle-wrapper.properties
echo networkTimeout=10000 >> gradle\wrapper\gradle-wrapper.properties
echo zipStoreBase=GRADLE_USER_HOME >> gradle\wrapper\gradle-wrapper.properties
echo zipStorePath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties

echo ========================================
echo      HOÀN THÀNH!
echo ========================================
echo Bây giờ hãy:
echo 1. Mở Android Studio
echo 2. Chọn "Open an existing project"
echo 3. Chọn thư mục Mobileappthanhpphong
echo 4. Chờ Gradle sync tự động
pause
