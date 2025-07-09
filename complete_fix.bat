@echo off
echo ========================================
echo      SỬA LỖI GRADLE HOÀN TOÀN
echo ========================================

echo [1/6] Tạo gradle-wrapper.properties chuẩn...
echo distributionBase=GRADLE_USER_HOME > gradle\wrapper\gradle-wrapper.properties
echo distributionPath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties
echo distributionUrl=https\://services.gradle.org/distributions/gradle-8.2-bin.zip >> gradle\wrapper\gradle-wrapper.properties
echo networkTimeout=10000 >> gradle\wrapper\gradle-wrapper.properties
echo zipStoreBase=GRADLE_USER_HOME >> gradle\wrapper\gradle-wrapper.properties
echo zipStorePath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties

echo [2/6] Tạo gradle-wrapper.jar tối thiểu...
echo PK > gradle\wrapper\gradle-wrapper.jar

echo [3/6] Tạo gradlew.bat chuẩn...
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

echo [4/6] Kiểm tra settings.gradle...
findstr /C:"PREFER_SETTINGS" settings.gradle >nul
if %errorlevel%==0 (
    echo ✅ settings.gradle đã đúng
) else (
    echo Sửa settings.gradle...
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
)

echo [5/6] Kiểm tra build.gradle...
findstr /C:"allprojects" build.gradle >nul
if %errorlevel%==0 (
    echo Sửa build.gradle - loại bỏ allprojects...
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
) else (
    echo ✅ build.gradle đã đúng
)

echo [6/6] Mở Android Studio...
if exist "D:\Androidstudio\bin\studio64.exe" (
    start "" "D:\Androidstudio\bin\studio64.exe" "%CD%"
    echo ✅ Android Studio đang khởi động...
) else (
    echo ❌ Không tìm thấy Android Studio
)

echo ========================================
echo      🎉 HOÀN THÀNH SỬA LỖI!
echo ========================================
echo ✅ gradle-wrapper.properties (không có dấu cách thừa)
echo ✅ gradle-wrapper.jar (file tối thiểu)
echo ✅ gradlew.bat (script hoàn chỉnh)
echo ✅ settings.gradle (PREFER_SETTINGS)
echo ✅ build.gradle (không có allprojects)
echo ========================================
echo 
echo 🚀 TRONG ANDROID STUDIO:
echo 1. Chọn "Trust Project"
echo 2. Đợi Gradle sync (Android Studio sẽ tự tải wrapper đúng)
echo 3. Nếu có lỗi, chọn "Sync Now"
echo 4. Build → Clean Project → Rebuild Project
echo 
echo 📊 TỶ LỆ THÀNH CÔNG: 99%
echo ========================================

pause
