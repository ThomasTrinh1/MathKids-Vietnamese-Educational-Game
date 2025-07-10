@echo off
echo ========================================
echo    CLEAN KOTLIN CACHE VÀ BUILD CACHE
echo ========================================

echo [1/6] Clean Gradle build cache...
gradlew cleanBuildCache

echo [2/6] Clean project...
gradlew clean

echo [3/6] Xóa .gradle cache...
rmdir /s /q .gradle 2>nul

echo [4/6] Xóa build directories...
rmdir /s /q build 2>nul
rmdir /s /q app\build 2>nul

echo [5/6] Xóa Kotlin metadata cache...
rmdir /s /q "%USERPROFILE%\.gradle\caches\modules-2\metadata-*" 2>nul
rmdir /s /q "%USERPROFILE%\.gradle\caches\*kotlin*" 2>nul

echo [6/6] Rebuild project...
gradlew build --no-daemon

echo ========================================
echo    CLEAN HOÀN TẤT
echo ========================================
pause
