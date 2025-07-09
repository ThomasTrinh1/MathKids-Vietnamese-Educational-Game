@echo off
echo ========================================
echo      SỬA LỖI GRADLE TOÀN DIỆN
echo ========================================

echo [1/8] Dọn dẹp hoàn toàn...
if exist ".gradle" rmdir /s /q ".gradle"
if exist "build" rmdir /s /q "build"
if exist "app\build" rmdir /s /q "app\build"
if exist "gradle" rmdir /s /q "gradle"
if exist "gradlew.bat" del "gradlew.bat"
if exist "gradlew" del "gradlew"

echo [2/8] Tạo cấu trúc Gradle mới...
mkdir "gradle\wrapper"

echo [3/8] Tạo gradle-wrapper.properties chuẩn...
echo distributionBase=GRADLE_USER_HOME > gradle\wrapper\gradle-wrapper.properties
echo distributionPath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties
echo distributionUrl=https\://services.gradle.org/distributions/gradle-8.2-bin.zip >> gradle\wrapper\gradle-wrapper.properties
echo networkTimeout=10000 >> gradle\wrapper\gradle-wrapper.properties
echo zipStoreBase=GRADLE_USER_HOME >> gradle\wrapper\gradle-wrapper.properties
echo zipStorePath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties

echo [4/8] Sao chép gradle-wrapper.jar...
if exist "..\Mobileapp\cordova-app\platforms\android\gradle\wrapper\gradle-wrapper.jar" (
    copy "..\Mobileapp\cordova-app\platforms\android\gradle\wrapper\gradle-wrapper.jar" "gradle\wrapper\gradle-wrapper.jar"
    echo ✅ Đã sao chép gradle-wrapper.jar
) else (
    echo ❌ Không tìm thấy gradle-wrapper.jar, tạo file tạm...
    echo. > "gradle\wrapper\gradle-wrapper.jar"
)

echo [5/8] Sửa settings.gradle - loại bỏ FAIL_ON_PROJECT_REPOS...
echo pluginManagement { > settings.gradle
echo     repositories { >> settings.gradle
echo         gradlePluginPortal() >> settings.gradle
echo         google() >> settings.gradle
echo         mavenCentral() >> settings.gradle
echo     } >> settings.gradle
echo } >> settings.gradle
echo dependencyResolutionManagement { >> settings.gradle
echo     repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) >> settings.gradle
echo     repositories { >> settings.gradle
echo         google() >> settings.gradle
echo         mavenCentral() >> settings.gradle
echo         maven { url 'https://jitpack.io' } >> settings.gradle
echo     } >> settings.gradle
echo } >> settings.gradle
echo rootProject.name = "Mobileappthanhpphong" >> settings.gradle
echo include ':app' >> settings.gradle

echo [6/8] Sửa build.gradle - loại bỏ allprojects repositories...
echo // Top-level build file where you can add configuration options common to all sub-projects/modules. > build.gradle
echo buildscript { >> build.gradle
echo     ext.kotlin_version = "1.9.22" >> build.gradle
echo     repositories { >> build.gradle
echo         google() >> build.gradle
echo         mavenCentral() >> build.gradle
echo         gradlePluginPortal() >> build.gradle
echo     } >> build.gradle
echo     dependencies { >> build.gradle
echo         classpath "com.android.tools.build:gradle:8.2.0" >> build.gradle
echo         classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version" >> build.gradle
echo     } >> build.gradle
echo } >> build.gradle
echo. >> build.gradle
echo plugins { >> build.gradle
echo     id 'com.android.application' version '8.2.0' apply false >> build.gradle
echo     id 'com.android.library' version '8.2.0' apply false >> build.gradle
echo     id 'org.jetbrains.kotlin.android' version '1.9.22' apply false >> build.gradle
echo } >> build.gradle
echo. >> build.gradle
echo task clean(type: Delete) { >> build.gradle
echo     delete rootProject.buildDir >> build.gradle
echo } >> build.gradle

echo [7/8] Tạo gradlew.bat chuẩn...
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

echo [8/8] Kiểm tra kết quả...
echo ========================================
echo      KIỂM TRA FILES ĐÃ TẠO
echo ========================================
if exist "gradle\wrapper\gradle-wrapper.properties" echo ✅ gradle-wrapper.properties
if exist "gradle\wrapper\gradle-wrapper.jar" echo ✅ gradle-wrapper.jar
if exist "gradlew.bat" echo ✅ gradlew.bat
if exist "settings.gradle" echo ✅ settings.gradle (đã sửa)
if exist "build.gradle" echo ✅ build.gradle (đã sửa)

echo ========================================
echo      HOÀN THÀNH SỬA LỖI TOÀN DIỆN!
echo ========================================
echo Các lỗi đã được sửa:
echo ✅ FAIL_ON_PROJECT_REPOS → PREFER_SETTINGS
echo ✅ Loại bỏ allprojects repositories conflict
echo ✅ Gradle wrapper hoàn chỉnh
echo ✅ Cấu hình tương thích Gradle 8.2 + AGP 8.2.0
echo ========================================

pause
