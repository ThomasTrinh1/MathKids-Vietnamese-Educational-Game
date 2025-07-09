@echo off
echo ========================================
echo    MathKids Android App Builder
echo    Automated Build Script for Windows
echo ========================================
echo.

:: Set variables
set PROJECT_DIR=%~dp0
set ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\Sdk
set JAVA_HOME=C:\Program Files\Java\jdk-11.0.16

:: Check if Android SDK is installed
if not exist "%ANDROID_HOME%" (
    echo [ERROR] Android SDK not found at %ANDROID_HOME%
    echo Please install Android Studio and SDK first
    pause
    exit /b 1
)

:: Set environment variables
set PATH=%ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools;%JAVA_HOME%\bin;%PATH%

echo [INFO] Starting MathKids Android App build process...
echo [INFO] Project Directory: %PROJECT_DIR%
echo [INFO] Android SDK: %ANDROID_HOME%
echo.

:: Navigate to project directory
cd /d "%PROJECT_DIR%"

:: Clean previous builds
echo [STEP 1/6] Cleaning previous builds...
if exist "app\build" (
    rmdir /s /q "app\build"
    echo [SUCCESS] Previous builds cleaned
)
echo.

:: Check Gradle wrapper
echo [STEP 2/6] Checking Gradle wrapper...
if not exist "gradlew.bat" (
    echo [INFO] Creating Gradle wrapper...
    gradle wrapper --gradle-version 7.4.2
)
echo [SUCCESS] Gradle wrapper ready
echo.

:: Download dependencies
echo [STEP 3/6] Downloading dependencies...
call gradlew.bat --no-daemon dependencies
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Failed to download dependencies
    echo Check your internet connection and try again
    pause
    exit /b 1
)
echo [SUCCESS] Dependencies downloaded
echo.

:: Build debug APK
echo [STEP 4/6] Building debug APK...
call gradlew.bat --no-daemon assembleDebug
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Debug build failed
    pause
    exit /b 1
)
echo [SUCCESS] Debug APK built successfully
echo.

:: Build release APK
echo [STEP 5/6] Building release APK...
call gradlew.bat --no-daemon assembleRelease
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Release build failed
    pause
    exit /b 1
)
echo [SUCCESS] Release APK built successfully
echo.

:: Copy APKs to output directory
echo [STEP 6/6] Copying APKs to output directory...
if not exist "output" mkdir "output"

if exist "app\build\outputs\apk\debug\app-debug.apk" (
    copy "app\build\outputs\apk\debug\app-debug.apk" "output\MathKids-debug.apk"
    echo [SUCCESS] Debug APK copied to output\MathKids-debug.apk
)

if exist "app\build\outputs\apk\release\app-release.apk" (
    copy "app\build\outputs\apk\release\app-release.apk" "output\MathKids-release.apk"
    echo [SUCCESS] Release APK copied to output\MathKids-release.apk
)

echo ========================================
echo           BUILD COMPLETED!
echo ========================================
echo.
echo Output files:
if exist "output\MathKids-debug.apk" (
    echo   ✓ Debug APK: output\MathKids-debug.apk
)
if exist "output\MathKids-release.apk" (
    echo   ✓ Release APK: output\MathKids-release.apk
)
echo.

echo Build process completed successfully!
echo Press any key to exit...
pause >nul
