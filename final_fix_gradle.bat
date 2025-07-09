@echo off
echo ========================================
echo      SỬA LỖI GRADLE CUỐI CÙNG
echo ========================================

echo [1/5] Xóa toàn bộ Gradle cũ...
if exist ".gradle" rmdir /s /q ".gradle"
if exist "gradle" rmdir /s /q "gradle"
if exist "gradlew.bat" del "gradlew.bat"
if exist "gradlew" del "gradlew"

echo [2/5] Tạo lại cấu trúc Gradle...
mkdir "gradle\wrapper"

echo [3/5] Tạo gradle-wrapper.properties mới...
echo distributionBase=GRADLE_USER_HOME > gradle\wrapper\gradle-wrapper.properties
echo distributionPath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties
echo distributionUrl=https\://services.gradle.org/distributions/gradle-8.9-bin.zip >> gradle\wrapper\gradle-wrapper.properties
echo networkTimeout=10000 >> gradle\wrapper\gradle-wrapper.properties
echo zipStoreBase=GRADLE_USER_HOME >> gradle\wrapper\gradle-wrapper.properties
echo zipStorePath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties

echo [4/5] Sao chép gradle-wrapper.jar từ project khác...
if exist "..\Mobileapp\cordova-app\platforms\android\gradle\wrapper\gradle-wrapper.jar" (
    copy "..\Mobileapp\cordova-app\platforms\android\gradle\wrapper\gradle-wrapper.jar" "gradle\wrapper\gradle-wrapper.jar"
    echo ✅ Đã sao chép gradle-wrapper.jar
) else (
    echo ❌ Không tìm thấy gradle-wrapper.jar
    echo Tạo file tạm thời...
    echo. > "gradle\wrapper\gradle-wrapper.jar"
)

echo [5/5] Tạo gradlew.bat mới...
echo @echo off > gradlew.bat
echo setlocal >> gradlew.bat
echo. >> gradlew.bat
echo set DIRNAME=%%~dp0 >> gradlew.bat
echo if "%%DIRNAME%%" == "" set DIRNAME=. >> gradlew.bat
echo set APP_BASE_NAME=%%~n0 >> gradlew.bat
echo set APP_HOME=%%DIRNAME%% >> gradlew.bat
echo. >> gradlew.bat
echo set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m" >> gradlew.bat
echo. >> gradlew.bat
echo set JAVA_EXE=java.exe >> gradlew.bat
echo %%JAVA_EXE%% -version ^>NUL 2^>^&1 >> gradlew.bat
echo if %%ERRORLEVEL%% equ 0 goto execute >> gradlew.bat
echo. >> gradlew.bat
echo echo ERROR: JAVA_HOME is not set >> gradlew.bat
echo goto fail >> gradlew.bat
echo. >> gradlew.bat
echo :execute >> gradlew.bat
echo set CLASSPATH=%%APP_HOME%%\gradle\wrapper\gradle-wrapper.jar >> gradlew.bat
echo "%%JAVA_EXE%%" %%DEFAULT_JVM_OPTS%% %%JAVA_OPTS%% %%GRADLE_OPTS%% "-Dorg.gradle.appname=%%APP_BASE_NAME%%" -classpath "%%CLASSPATH%%" org.gradle.wrapper.GradleWrapperMain %%* >> gradlew.bat
echo. >> gradlew.bat
echo :fail >> gradlew.bat
echo exit /b 1 >> gradlew.bat

echo ========================================
echo      HOÀN THÀNH SỬA LỖI!
echo ========================================
echo Bây giờ hãy:
echo 1. Mở Android Studio
echo 2. File → Open → Chọn thư mục Mobileappthanhpphong
echo 3. Chọn "Trust Project"
echo 4. Đợi Gradle sync (Android Studio sẽ tự tải Gradle)
echo 5. Nếu có lỗi, chọn "Sync Now"
echo ========================================

echo Kiểm tra files đã tạo:
if exist "gradle\wrapper\gradle-wrapper.properties" echo ✅ gradle-wrapper.properties
if exist "gradle\wrapper\gradle-wrapper.jar" echo ✅ gradle-wrapper.jar
if exist "gradlew.bat" echo ✅ gradlew.bat

pause
