@echo off
echo ========================================
echo      SỬA LỖI GRADLE SYNC
echo ========================================

echo [1/6] Dọn dẹp cache Gradle...
if exist ".gradle" rmdir /s /q ".gradle"
if exist "app\.gradle" rmdir /s /q "app\.gradle"
if exist "build" rmdir /s /q "build"
if exist "app\build" rmdir /s /q "app\build"

echo [2/6] Dọn dẹp cache Android Studio...
if exist "%USERPROFILE%\.gradle\caches" rmdir /s /q "%USERPROFILE%\.gradle\caches"

echo [3/6] Tạo lại Gradle Wrapper...
if not exist "gradle\wrapper" mkdir "gradle\wrapper"

echo [4/6] Tải Gradle Wrapper...
powershell -Command "Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-8.2-bin.zip' -OutFile 'gradle-wrapper-temp.zip'"
if exist "gradle-wrapper-temp.zip" (
    powershell -Command "Expand-Archive -Path 'gradle-wrapper-temp.zip' -DestinationPath 'gradle-temp' -Force"
    if exist "gradle-temp\gradle-8.2\lib\gradle-wrapper.jar" (
        copy "gradle-temp\gradle-8.2\lib\gradle-wrapper.jar" "gradle\wrapper\gradle-wrapper.jar"
    )
    rmdir /s /q "gradle-temp"
    del "gradle-wrapper-temp.zip"
)

echo [5/6] Cấp quyền thực thi...
if exist "gradlew.bat" (
    echo Gradle wrapper đã sẵn sàng
)

echo [6/6] Sync lại Gradle...
call gradlew.bat clean
call gradlew.bat build --refresh-dependencies

echo ========================================
echo      HOÀN THÀNH SỬA LỖI GRADLE!
echo ========================================
echo Bây giờ hãy mở lại Android Studio và sync project
pause
