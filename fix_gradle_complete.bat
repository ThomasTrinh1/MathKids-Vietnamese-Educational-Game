@echo off
echo ========================================
echo      SỬA LỖI GRADLE HOÀN CHỈNH
echo ========================================

echo [1/6] Đóng Android Studio nếu đang mở...
taskkill /f /im "studio64.exe" 2>nul
taskkill /f /im "studio.exe" 2>nul

echo [2/6] Dọn dẹp cache Gradle...
if exist ".gradle" rmdir /s /q ".gradle"
if exist "app\.gradle" rmdir /s /q "app\.gradle"
if exist "build" rmdir /s /q "build"
if exist "app\build" rmdir /s /q "app\build"

echo [3/6] Tạo Gradle wrapper...
if not exist "gradle\wrapper" mkdir "gradle\wrapper"

echo distributionBase=GRADLE_USER_HOME > gradle\wrapper\gradle-wrapper.properties
echo distributionPath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties
echo distributionUrl=https\://services.gradle.org/distributions/gradle-8.2-bin.zip >> gradle\wrapper\gradle-wrapper.properties
echo networkTimeout=10000 >> gradle\wrapper\gradle-wrapper.properties
echo zipStoreBase=GRADLE_USER_HOME >> gradle\wrapper\gradle-wrapper.properties
echo zipStorePath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties

echo [4/6] Kiểm tra cấu hình build.gradle...
if not exist "build.gradle" (
    echo Lỗi: Không tìm thấy build.gradle!
    pause
    exit /b 1
)

echo [5/6] Kiểm tra cấu hình settings.gradle...
if not exist "settings.gradle" (
    echo Lỗi: Không tìm thấy settings.gradle!
    pause
    exit /b 1
)

echo [6/6] Mở Android Studio...
if exist "D:\Androidstudio\bin\studio64.exe" (
    start "" "D:\Androidstudio\bin\studio64.exe" "%CD%"
    echo Android Studio đang khởi động...
) else if exist "D:\Androidstudio\bin\studio.exe" (
    start "" "D:\Androidstudio\bin\studio.exe" "%CD%"
    echo Android Studio đang khởi động...
) else (
    echo Không tìm thấy Android Studio tại D:\Androidstudio\
    echo Vui lòng kiểm tra đường dẫn cài đặt.
    pause
    exit /b 1
)

echo ========================================
echo      HOÀN THÀNH SỬA LỖI GRADLE!
echo ========================================
echo Bây giờ trong Android Studio:
echo 1. Đợi khởi động hoàn toàn
echo 2. Chọn "Trust Project" nếu được hỏi
echo 3. Đợi Gradle sync tự động
echo 4. Nếu có lỗi, chọn "Sync Now" hoặc "Try Again"
echo 5. Kiểm tra Build → Clean Project
echo 6. Sau đó Build → Rebuild Project
echo ========================================

pause
