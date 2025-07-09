@echo off
echo ========================================
echo      CÀI ĐẶT ANDROID STUDIO
echo ========================================

echo [1/4] Kiểm tra kết nối internet...
ping -n 1 google.com >nul 2>&1
if errorlevel 1 (
    echo Lỗi: Không có kết nối internet!
    echo Vui lòng kiểm tra kết nối và thử lại.
    pause
    exit /b 1
)
echo Kết nối internet OK!

echo [2/4] Tải Android Studio...
echo Đang tải Android Studio từ Google...
echo Vui lòng đợi, file khoảng 1GB...

REM Tạo thư mục tạm
if not exist "temp" mkdir temp

REM Tải Android Studio
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://redirector.gvt1.com/edgedl/android/studio/install/2023.1.1.28/android-studio-2023.1.1.28-windows.exe' -OutFile 'temp\android-studio-installer.exe'}"

if not exist "temp\android-studio-installer.exe" (
    echo Lỗi: Không thể tải Android Studio!
    echo Vui lòng tải thủ công từ: https://developer.android.com/studio
    pause
    exit /b 1
)

echo [3/4] Cài đặt Android Studio...
echo Đang khởi chạy trình cài đặt...
echo Vui lòng làm theo hướng dẫn trên màn hình.

start /wait "temp\android-studio-installer.exe"

echo [4/4] Dọn dẹp...
if exist "temp\android-studio-installer.exe" del "temp\android-studio-installer.exe"
if exist "temp" rmdir "temp"

echo ========================================
echo      CÀI ĐẶT HOÀN THÀNH!
echo ========================================
echo Bây giờ hãy:
echo 1. Khởi động Android Studio
echo 2. Làm theo Setup Wizard
echo 3. Cài đặt Android SDK
echo 4. Mở project Mobileappthanhpphong
echo ========================================

pause
