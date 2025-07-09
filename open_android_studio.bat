@echo off
echo ========================================
echo      MỞ ANDROID STUDIO VÀ SYNC PROJECT
echo ========================================

echo [1/3] Tìm Android Studio...
set STUDIO_PATH=""

REM Tìm Android Studio trong đường dẫn của bạn
if exist "D:\Androidstudio\bin\studio64.exe" (
    set STUDIO_PATH="D:\Androidstudio\bin\studio64.exe"
    echo Tìm thấy Android Studio tại: D:\Androidstudio\
) else if exist "D:\Androidstudio\bin\studio.exe" (
    set STUDIO_PATH="D:\Androidstudio\bin\studio.exe"
    echo Tìm thấy Android Studio tại: D:\Androidstudio\
) else if exist "C:\Program Files\Android\Android Studio\bin\studio64.exe" (
    set STUDIO_PATH="C:\Program Files\Android\Android Studio\bin\studio64.exe"
    echo Tìm thấy Android Studio tại: C:\Program Files\Android\Android Studio\
) else (
    echo Không tìm thấy Android Studio!
    echo Kiểm tra đường dẫn: D:\Androidstudio\bin\studio64.exe
    echo Hoặc cài đặt Android Studio từ: https://developer.android.com/studio
    pause
    exit /b 1
)

echo [2/3] Mở Android Studio...
if not %STUDIO_PATH%=="" (
    start "" %STUDIO_PATH% "%CD%"
    echo Android Studio đang khởi động...
    echo Project sẽ được mở tại: %CD%
) else (
    echo Lỗi: Không thể mở Android Studio
    pause
    exit /b 1
)

echo [3/3] Hướng dẫn tiếp theo...
echo ========================================
echo      HƯỚNG DẪN SYNC GRADLE
echo ========================================
echo 1. Đợi Android Studio khởi động hoàn toàn
echo 2. Nếu được hỏi "Trust project", chọn "Trust"
echo 3. Gradle sẽ tự động sync
echo 4. Nếu có lỗi, chọn "Try Again" hoặc "Sync Now"
echo 5. Đợi quá trình sync hoàn thành
echo ========================================

pause
