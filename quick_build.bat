@echo off
echo ========================================
echo      QUICK BUILD APK
echo ========================================

echo [1/3] Clean project...
gradlew clean
if %errorlevel% neq 0 (
    echo ❌ Clean failed
    pause
    exit /b 1
)

echo [2/3] Build debug APK...
gradlew assembleDebug
if %errorlevel% neq 0 (
    echo ❌ Build failed
    pause
    exit /b 1
)

echo [3/3] Check APK...
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo ✅ APK created successfully!
    echo 📱 Location: app\build\outputs\apk\debug\app-debug.apk
) else (
    echo ❌ APK not found
)

pause
