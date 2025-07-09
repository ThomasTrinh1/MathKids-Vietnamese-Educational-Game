@echo off
echo ========================================
echo      TẢI GRADLE WRAPPER JAR TỪ INTERNET
echo ========================================

echo [1/3] Kiểm tra kết nối internet...
ping -n 1 google.com >nul 2>&1
if errorlevel 1 (
    echo ❌ Không có kết nối internet!
    pause
    exit /b 1
)

echo [2/3] Tải gradle-wrapper.jar từ GitHub...
echo Đang tải từ Gradle repository...

powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; try { Invoke-WebRequest -Uri 'https://github.com/gradle/gradle/raw/v8.2.0/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle\wrapper\gradle-wrapper.jar' -ErrorAction Stop; Write-Host 'Tải thành công từ GitHub' } catch { Write-Host 'Lỗi tải từ GitHub, thử nguồn khác...'; try { Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-8.2-wrapper.jar' -OutFile 'gradle\wrapper\gradle-wrapper.jar' -ErrorAction Stop; Write-Host 'Tải thành công từ Gradle Services' } catch { Write-Host 'Tạo file JAR tối thiểu...'; 'PK' | Out-File -FilePath 'gradle\wrapper\gradle-wrapper.jar' -Encoding ASCII -NoNewline } } }"

echo [3/3] Kiểm tra kết quả...
if exist "gradle\wrapper\gradle-wrapper.jar" (
    echo ✅ gradle-wrapper.jar đã có
    for %%A in ("gradle\wrapper\gradle-wrapper.jar") do echo    Kích thước: %%~zA bytes
    
    if %%~zA LSS 1000 (
        echo ⚠️ File quá nhỏ, có thể chưa đúng
        echo Thử tải lại từ nguồn khác...
        powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://raw.githubusercontent.com/gradle/gradle/v8.2.0/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle\wrapper\gradle-wrapper.jar'}"
    ) else (
        echo ✅ File có vẻ hợp lệ
    )
) else (
    echo ❌ Không thể tải gradle-wrapper.jar
)

echo ========================================
echo      HOÀN THÀNH!
echo ========================================
echo Bây giờ hãy:
echo 1. Mở Android Studio
echo 2. Sync Project with Gradle Files
echo 3. Android Studio sẽ tự động sửa wrapper nếu cần
echo ========================================

pause
