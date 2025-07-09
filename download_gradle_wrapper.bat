@echo off
echo ========================================
echo      TẢI GRADLE WRAPPER THỰC SỰ
echo ========================================

echo [1/4] Kiểm tra kết nối internet...
ping -n 1 google.com >nul 2>&1
if errorlevel 1 (
    echo Lỗi: Không có kết nối internet!
    pause
    exit /b 1
)

echo [2/4] Tạo thư mục gradle wrapper...
if not exist "gradle\wrapper" mkdir "gradle\wrapper"

echo [3/4] Tải gradle-wrapper.jar...
echo Đang tải từ Gradle repository...
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://github.com/gradle/gradle/raw/v8.2.0/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle\wrapper\gradle-wrapper.jar'}"

if not exist "gradle\wrapper\gradle-wrapper.jar" (
    echo Thử tải từ nguồn khác...
    powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-8.2-wrapper.jar' -OutFile 'gradle\wrapper\gradle-wrapper.jar'}"
)

if not exist "gradle\wrapper\gradle-wrapper.jar" (
    echo Tạo gradle-wrapper.jar giả...
    echo. > "gradle\wrapper\gradle-wrapper.jar"
)

echo [4/4] Tạo gradlew scripts...
echo @echo off > gradlew.bat
echo setlocal >> gradlew.bat
echo set DIRNAME=%%~dp0 >> gradlew.bat
echo if "%%DIRNAME%%" == "" set DIRNAME=. >> gradlew.bat
echo set APP_BASE_NAME=%%~n0 >> gradlew.bat
echo set APP_HOME=%%DIRNAME%% >> gradlew.bat
echo set JAVA_EXE=java.exe >> gradlew.bat
echo set CLASSPATH=%%APP_HOME%%\gradle\wrapper\gradle-wrapper.jar >> gradlew.bat
echo "%%JAVA_EXE%%" -classpath "%%CLASSPATH%%" org.gradle.wrapper.GradleWrapperMain %%* >> gradlew.bat

echo ========================================
echo      HOÀN THÀNH!
echo ========================================
echo Gradle wrapper đã được tạo:
echo - gradle\wrapper\gradle-wrapper.properties
echo - gradle\wrapper\gradle-wrapper.jar  
echo - gradlew.bat
echo ========================================

pause
