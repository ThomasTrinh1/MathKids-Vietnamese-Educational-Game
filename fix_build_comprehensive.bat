@echo off
echo ========================================
echo    COMPREHENSIVE BUILD FIX SCRIPT
echo ========================================

echo [1/6] Cleaning build cache...
if exist "app\build" (
    rmdir /s /q "app\build"
    echo ✅ Removed app build cache
)

if exist ".gradle" (
    rmdir /s /q ".gradle"
    echo ✅ Removed gradle cache
)

if exist "build" (
    rmdir /s /q "build"
    echo ✅ Removed project build cache
)

echo [2/6] Checking Gradle Wrapper...
if not exist "gradlew.bat" (
    echo ❌ gradlew.bat missing, regenerating...
    gradle wrapper
    if %errorlevel% neq 0 (
        echo ❌ Failed to generate gradle wrapper
        pause
        exit /b 1
    )
)
echo ✅ Gradle wrapper OK

echo [3/6] Validating resource files...
echo Checking strings.xml...
findstr /C:"You\'" "app\src\main\res\values\strings.xml" >nul
if %errorlevel% equ 0 (
    echo ❌ Found illegal apostrophe escape in strings.xml
    echo This should have been fixed to use &apos; instead
)

echo [4/6] Checking font references...
if exist "app\src\main\res\font\roboto_medium.xml" (
    findstr /C:"roboto_medium_ttf" "app\src\main\res\font\roboto_medium.xml" >nul
    if %errorlevel% equ 0 (
        echo ❌ Found missing font file reference
    )
)

echo [5/6] Building APK...
echo Running gradle build...
call gradlew.bat clean assembleDebug --stacktrace
if %errorlevel% neq 0 (
    echo ❌ Build failed
    echo Check the error messages above
    pause
    exit /b 1
)

echo [6/6] Checking APK output...
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo ✅ BUILD SUCCESSFUL!
    echo 📱 APK Location: app\build\outputs\apk\debug\app-debug.apk
    for %%A in ("app\build\outputs\apk\debug\app-debug.apk") do (
        echo 📊 Size: %%~zA bytes
        echo 📅 Created: %%~tA
    )
) else (
    echo ❌ APK not found
    echo Build may have failed
)

echo ========================================
echo           BUILD COMPLETE
echo ========================================
pause
