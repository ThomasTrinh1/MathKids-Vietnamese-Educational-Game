@echo off
echo ========================================
echo    MathKids Compilation Test
echo ========================================
echo.

:: Set variables
set PROJECT_DIR=%~dp0
set ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\Sdk

:: Check if Android SDK is installed
if not exist "%ANDROID_HOME%" (
    echo [ERROR] Android SDK not found at %ANDROID_HOME%
    echo Please install Android Studio and SDK first
    pause
    exit /b 1
)

:: Set environment variables
set PATH=%ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools;%PATH%

echo [INFO] Testing MathKids project compilation...
echo [INFO] Project Directory: %PROJECT_DIR%
echo.

:: Navigate to project directory
cd /d "%PROJECT_DIR%"

:: Test Gradle wrapper
echo [STEP 1/4] Testing Gradle wrapper...
if not exist "gradlew.bat" (
    echo [ERROR] Gradle wrapper not found
    pause
    exit /b 1
)
echo [SUCCESS] Gradle wrapper found
echo.

:: Test dependencies resolution
echo [STEP 2/4] Testing dependencies resolution...
call gradlew.bat --no-daemon dependencies --configuration debugCompileClasspath
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Dependencies resolution failed
    pause
    exit /b 1
)
echo [SUCCESS] Dependencies resolved
echo.

:: Test compilation
echo [STEP 3/4] Testing compilation...
call gradlew.bat --no-daemon compileDebugKotlin
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Kotlin compilation failed
    echo.
    echo Common issues to check:
    echo - Missing import statements
    echo - Undefined resource references
    echo - Syntax errors in Kotlin files
    echo - Missing dependencies
    pause
    exit /b 1
)
echo [SUCCESS] Kotlin compilation successful
echo.

:: Test resource compilation
echo [STEP 4/4] Testing resource compilation...
call gradlew.bat --no-daemon processDebugResources
if %ERRORLEVEL% neq 0 (
    echo [ERROR] Resource compilation failed
    echo.
    echo Common issues to check:
    echo - Missing drawable resources
    echo - Invalid XML syntax
    echo - Missing string resources
    echo - Undefined color/style references
    pause
    exit /b 1
)
echo [SUCCESS] Resource compilation successful
echo.

echo ========================================
echo       COMPILATION TEST PASSED!
echo ========================================
echo.
echo The project should now compile successfully.
echo You can proceed with building the APK using:
echo   build-android-app.bat
echo.
pause
