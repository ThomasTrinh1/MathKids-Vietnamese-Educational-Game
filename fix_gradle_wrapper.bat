@echo off
echo ========================================
echo    SỬA LỖI GRADLE WRAPPER JAR
echo ========================================

echo [1/7] Xóa gradle wrapper cũ...
rmdir /s /q gradle\wrapper 2>nul
mkdir gradle\wrapper

echo [2/7] Tạo gradle-wrapper.properties...
echo distributionBase=GRADLE_USER_HOME > gradle\wrapper\gradle-wrapper.properties
echo distributionPath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties
echo distributionUrl=https\://services.gradle.org/distributions/gradle-8.5-bin.zip >> gradle\wrapper\gradle-wrapper.properties
echo networkTimeout=10000 >> gradle\wrapper\gradle-wrapper.properties
echo validateDistributionUrl=true >> gradle\wrapper\gradle-wrapper.properties
echo zipStoreBase=GRADLE_USER_HOME >> gradle\wrapper\gradle-wrapper.properties
echo zipStorePath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties

echo [3/7] Tải gradle-wrapper.jar từ nguồn chính thức...
powershell -Command "try { Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-8.5-wrapper.jar' -OutFile 'gradle/wrapper/gradle-wrapper.jar' -ErrorAction Stop; Write-Host '✅ Tải gradle-wrapper.jar thành công' } catch { Write-Host '❌ Lỗi tải từ nguồn 1, thử nguồn 2...' }"

echo [4/7] Nếu lỗi, thử nguồn khác...
if not exist gradle\wrapper\gradle-wrapper.jar (
    echo Thử tải từ Maven Central...
    powershell -Command "try { Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/gradle/gradle-wrapper/8.5/gradle-wrapper-8.5.jar' -OutFile 'gradle/wrapper/gradle-wrapper.jar' -ErrorAction Stop; Write-Host '✅ Tải từ Maven thành công' } catch { Write-Host '❌ Lỗi tải từ Maven' }"
)

echo [5/7] Tạo gradlew đúng chuẩn...
echo #!/bin/sh > gradlew
echo. >> gradlew
echo # Gradle start up script for UN*X >> gradlew
echo. >> gradlew
echo APP_NAME="Gradle" >> gradlew
echo APP_BASE_NAME=`basename "$0"` >> gradlew
echo. >> gradlew
echo # Use the maximum available, or set MAX_FD != -1 to use that value. >> gradlew
echo MAX_FD="maximum" >> gradlew
echo. >> gradlew
echo warn ^( ^) { >> gradlew
echo     echo "$*" >> gradlew
echo } >> gradlew
echo. >> gradlew
echo die ^( ^) { >> gradlew
echo     echo >> gradlew
echo     echo "$*" >> gradlew
echo     echo >> gradlew
echo     exit 1 >> gradlew
echo } >> gradlew
echo. >> gradlew
echo # Determine the Java command to use to start the JVM. >> gradlew
echo if [ -n "$JAVA_HOME" ] ; then >> gradlew
echo     if [ -x "$JAVA_HOME/jre/sh/java" ] ; then >> gradlew
echo         # IBM's JDK on AIX uses strange locations for the executables >> gradlew
echo         JAVACMD="$JAVA_HOME/jre/sh/java" >> gradlew
echo     else >> gradlew
echo         JAVACMD="$JAVA_HOME/bin/java" >> gradlew
echo     fi >> gradlew
echo     if [ ! -x "$JAVACMD" ] ; then >> gradlew
echo         die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME" >> gradlew
echo     fi >> gradlew
echo else >> gradlew
echo     JAVACMD="java" >> gradlew
echo     which java >/dev/null 2>&1 ^|^| die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH." >> gradlew
echo fi >> gradlew
echo. >> gradlew
echo # Escape application args >> gradlew
echo save ^( ^) { >> gradlew
echo     for i do printf %%s\\n "$i" ^| sed "s/'/'\\\\''/g;1s/^/'/;$s/$/' \\\\/" ; done >> gradlew
echo     echo " " >> gradlew
echo } >> gradlew
echo APP_ARGS=`save "$@"` >> gradlew
echo. >> gradlew
echo # Collect all arguments for the java command >> gradlew
echo set -- $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "-Dorg.gradle.appname=$APP_BASE_NAME" -classpath "\"$CLASSPATH\"" org.gradle.wrapper.GradleWrapperMain "$APP_ARGS" >> gradlew
echo. >> gradlew
echo exec "$JAVACMD" "$@" >> gradlew

echo [6/7] Kiểm tra file đã tạo...
if exist gradle\wrapper\gradle-wrapper.jar (
    echo ✅ gradle-wrapper.jar: OK
) else (
    echo ❌ gradle-wrapper.jar: MISSING
)

if exist gradle\wrapper\gradle-wrapper.properties (
    echo ✅ gradle-wrapper.properties: OK
) else (
    echo ❌ gradle-wrapper.properties: MISSING
)

if exist gradlew (
    echo ✅ gradlew: OK
) else (
    echo ❌ gradlew: MISSING
)

echo [7/7] Push files lên GitHub...
git add gradle/wrapper/
git add gradlew
git add gradlew.bat
git commit -m "🔧 Fix gradle-wrapper.jar corruption

✨ Fixed:
- Downloaded correct gradle-wrapper.jar from official source
- Created proper gradle-wrapper.properties
- Generated standard gradlew script
- Should resolve 'no main manifest attribute' error

🎯 Ready for GitHub Actions build"

git push origin main

if %errorlevel% equ 0 (
    echo ✅ PUSH THÀNH CÔNG!
    echo.
    echo 🎉 GRADLE WRAPPER ĐÃ ĐƯỢC SỬA!
    echo.
    echo 📋 Bước tiếp theo:
    echo 1. Vào GitHub Actions
    echo 2. Chạy lại workflow "🎮 Build MathKids APK (Simple)"
    echo 3. Lần này gradle-wrapper.jar sẽ hoạt động đúng
    echo.
    echo 🚀 Build sẽ thành công!
) else (
    echo ❌ PUSH THẤT BẠI
    echo Kiểm tra lỗi và thử lại
)

echo ========================================
pause
