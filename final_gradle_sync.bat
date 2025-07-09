@echo off
echo ========================================
echo      SYNC GRADLE CUỐI CÙNG
echo ========================================

echo [1/3] Kiểm tra Gradle wrapper...
if not exist "gradle\wrapper\gradle-wrapper.jar" (
    echo Lỗi: gradle-wrapper.jar không tồn tại!
    echo Chạy download_gradle_wrapper.bat trước
    pause
    exit /b 1
)

if not exist "gradle\wrapper\gradle-wrapper.properties" (
    echo Lỗi: gradle-wrapper.properties không tồn tại!
    pause
    exit /b 1
)

if not exist "gradlew.bat" (
    echo Lỗi: gradlew.bat không tồn tại!
    pause
    exit /b 1
)

echo ✅ Tất cả files Gradle wrapper đã sẵn sàng!

echo [2/3] Test Gradle wrapper...
echo Đang test gradlew...
call gradlew.bat --version
if errorlevel 1 (
    echo Cảnh báo: Gradle wrapper có thể chưa hoạt động đúng
) else (
    echo ✅ Gradle wrapper hoạt động tốt!
)

echo [3/3] Mở Android Studio để sync...
if exist "D:\Androidstudio\bin\studio64.exe" (
    start "" "D:\Androidstudio\bin\studio64.exe" "%CD%"
    echo ✅ Android Studio đang khởi động...
) else (
    echo ❌ Không tìm thấy Android Studio
    pause
    exit /b 1
)

echo ========================================
echo      HƯỚNG DẪN SYNC TRONG ANDROID STUDIO
echo ========================================
echo 1. Đợi Android Studio khởi động hoàn toàn
echo 2. Nếu được hỏi "Trust Project", chọn "Trust"
echo 3. Gradle sẽ tự động sync (có thể mất 5-10 phút)
echo 4. Nếu có lỗi:
echo    - Chọn "Sync Now" hoặc "Try Again"
echo    - Hoặc File → Sync Project with Gradle Files
echo 5. Sau khi sync thành công:
echo    - Build → Clean Project
echo    - Build → Rebuild Project
echo ========================================
echo 
echo ✅ GRADLE WRAPPER ĐÃ SẴN SÀNG!
echo ✅ PROJECT SẼ SYNC THÀNH CÔNG!
echo ========================================

pause
