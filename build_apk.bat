@echo off
echo ========================================
echo      BUILD APK ANDROID APP
echo ========================================

echo [1/5] Kiểm tra môi trường...
if not exist "gradlew.bat" (
    echo ❌ gradlew.bat không tồn tại
    pause
    exit /b 1
)

if not exist "app\build.gradle" (
    echo ❌ app\build.gradle không tồn tại
    pause
    exit /b 1
)

echo ✅ Môi trường build sẵn sàng

echo [2/5] Clean project...
call gradlew clean
if %errorlevel% neq 0 (
    echo ❌ Clean project thất bại
    pause
    exit /b 1
)
echo ✅ Clean project thành công

echo [3/5] Build debug APK...
call gradlew assembleDebug
if %errorlevel% neq 0 (
    echo ❌ Build debug APK thất bại
    echo Kiểm tra lỗi ở trên và sửa
    pause
    exit /b 1
)
echo ✅ Build debug APK thành công

echo [4/5] Tìm file APK...
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo ✅ APK debug: app\build\outputs\apk\debug\app-debug.apk
    for %%A in ("app\build\outputs\apk\debug\app-debug.apk") do echo    Kích thước: %%~zA bytes
) else (
    echo ❌ Không tìm thấy APK debug
)

echo [5/5] Build release APK (optional)...
echo Bạn có muốn build release APK? (y/n)
set /p choice=
if /i "%choice%"=="y" (
    echo Building release APK...
    call gradlew assembleRelease
    if %errorlevel% neq 0 (
        echo ❌ Build release APK thất bại
    ) else (
        echo ✅ Build release APK thành công
        if exist "app\build\outputs\apk\release\app-release.apk" (
            echo ✅ APK release: app\build\outputs\apk\release\app-release.apk
            for %%A in ("app\build\outputs\apk\release\app-release.apk") do echo    Kích thước: %%~zA bytes
        )
    )
)

echo ========================================
echo      🎉 BUILD HOÀN THÀNH!
echo ========================================
echo 📱 APK files được tạo tại:
echo    app\build\outputs\apk\debug\
echo    app\build\outputs\apk\release\
echo 
echo 📋 BƯỚC TIẾP THEO:
echo 1. Cài đặt APK trên thiết bị Android
echo 2. Test ứng dụng
echo 3. Nếu cần, sửa lỗi và build lại
echo ========================================

pause
