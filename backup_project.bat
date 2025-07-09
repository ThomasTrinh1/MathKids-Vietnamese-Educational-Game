@echo off
echo ========================================
echo      BACKUP MATHKIDS PROJECT
echo ========================================

set BACKUP_DIR=D:\Augment\Backup\MathKids_%date:~-4,4%%date:~-10,2%%date:~-7,2%_%time:~0,2%%time:~3,2%
set BACKUP_DIR=%BACKUP_DIR: =0%

echo [1/3] Tạo thư mục backup...
mkdir "%BACKUP_DIR%"
echo ✅ Thư mục backup: %BACKUP_DIR%

echo.
echo [2/3] Copy toàn bộ project...
xcopy /E /I /H /Y "D:\Augment\Mobileappthanhpphong" "%BACKUP_DIR%"

echo.
echo [3/3] Tạo file thông tin backup...
echo MathKids Vietnamese Educational Game > "%BACKUP_DIR%\BACKUP_INFO.txt"
echo Backup Date: %date% %time% >> "%BACKUP_DIR%\BACKUP_INFO.txt"
echo Source: D:\Augment\Mobileappthanhpphong >> "%BACKUP_DIR%\BACKUP_INFO.txt"
echo Status: Complete Android educational game >> "%BACKUP_DIR%\BACKUP_INFO.txt"
echo Build Status: Ready for Android Studio >> "%BACKUP_DIR%\BACKUP_INFO.txt"

echo.
echo ✅ BACKUP HOÀN THÀNH!
echo 📁 Vị trí: %BACKUP_DIR%
echo 💾 Dung lượng: 
for /f %%A in ('dir "%BACKUP_DIR%" /s /-c ^| find "bytes"') do echo %%A

echo.
echo ========================================
echo       BACKUP THÀNH CÔNG
echo ========================================
pause
