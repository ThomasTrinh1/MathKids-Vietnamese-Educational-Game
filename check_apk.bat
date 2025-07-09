@echo off
echo ========================================
echo      KIỂM TRA APK FILE
echo ========================================

echo [1/3] Tìm kiếm APK files...
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo ✅ APK Debug tìm thấy!
    echo 📱 Vị trí: app\build\outputs\apk\debug\app-debug.apk
    for %%A in ("app\build\outputs\apk\debug\app-debug.apk") do (
        echo 📊 Kích thước: %%~zA bytes
        echo 📅 Ngày tạo: %%~tA
    )
) else (
    echo ❌ APK Debug không tìm thấy
)

echo.
if exist "app\build\outputs\apk\release\app-release.apk" (
    echo ✅ APK Release tìm thấy!
    echo 📱 Vị trí: app\build\outputs\apk\release\app-release.apk
    for %%A in ("app\build\outputs\apk\release\app-release.apk") do (
        echo 📊 Kích thước: %%~zA bytes
        echo 📅 Ngày tạo: %%~tA
    )
) else (
    echo ❌ APK Release không tìm thấy
)

echo.
echo [2/3] Tìm kiếm tất cả APK files...
dir /s "app\build\*.apk" 2>nul
if %errorlevel% neq 0 (
    echo ❌ Không tìm thấy APK files nào
)

echo.
echo [3/3] Kiểm tra build outputs...
if exist "app\build\outputs" (
    echo ✅ Build outputs directory tồn tại
    dir "app\build\outputs" /s
) else (
    echo ❌ Build outputs directory không tồn tại
)

echo.
echo ========================================
echo      KẾT QUẢ KIỂM TRA
echo ========================================
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo ✅ BUILD THÀNH CÔNG!
    echo 📱 APK sẵn sàng để cài đặt
    echo 📂 Đường dẫn: app\build\outputs\apk\debug\app-debug.apk
) else (
    echo ❌ BUILD CHƯA HOÀN THÀNH
    echo 🔧 Cần kiểm tra lỗi build
)
echo ========================================

pause
