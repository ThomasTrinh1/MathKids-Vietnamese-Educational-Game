@echo off
echo ========================================
echo    PUSH WORKFLOW TRỰC TIẾP
echo ========================================

echo [1/5] Tạo thư mục .github/workflows...
if not exist .github mkdir .github
if not exist .github\workflows mkdir .github\workflows

echo [2/5] Tạo workflow build-now.yml...
echo name: 🎮 Build MathKids APK Now > .github\workflows\build-now.yml
echo. >> .github\workflows\build-now.yml
echo on: >> .github\workflows\build-now.yml
echo   workflow_dispatch: >> .github\workflows\build-now.yml
echo. >> .github\workflows\build-now.yml
echo jobs: >> .github\workflows\build-now.yml
echo   build: >> .github\workflows\build-now.yml
echo     runs-on: ubuntu-latest >> .github\workflows\build-now.yml
echo. >> .github\workflows\build-now.yml
echo     steps: >> .github\workflows\build-now.yml
echo     - name: 📥 Checkout >> .github\workflows\build-now.yml
echo       uses: actions/checkout@v4 >> .github\workflows\build-now.yml
echo. >> .github\workflows\build-now.yml
echo     - name: ☕ Setup JDK 17 >> .github\workflows\build-now.yml
echo       uses: actions/setup-java@v4 >> .github\workflows\build-now.yml
echo       with: >> .github\workflows\build-now.yml
echo         java-version: '17' >> .github\workflows\build-now.yml
echo         distribution: 'temurin' >> .github\workflows\build-now.yml
echo. >> .github\workflows\build-now.yml
echo     - name: 🔧 Setup Gradle >> .github\workflows\build-now.yml
echo       uses: gradle/gradle-build-action@v2 >> .github\workflows\build-now.yml
echo       with: >> .github\workflows\build-now.yml
echo         gradle-version: '8.2' >> .github\workflows\build-now.yml
echo. >> .github\workflows\build-now.yml
echo     - name: 🏗️ Build APK >> .github\workflows\build-now.yml
echo       run: gradle assembleDebug >> .github\workflows\build-now.yml
echo. >> .github\workflows\build-now.yml
echo     - name: 📦 Upload APK >> .github\workflows\build-now.yml
echo       uses: actions/upload-artifact@v4 >> .github\workflows\build-now.yml
echo       if: success^(^) >> .github\workflows\build-now.yml
echo       with: >> .github\workflows\build-now.yml
echo         name: mathkids-apk >> .github\workflows\build-now.yml
echo         path: app/build/outputs/apk/debug/app-debug.apk >> .github\workflows\build-now.yml

echo [3/5] Add workflow file...
git add .github/workflows/build-now.yml

echo [4/5] Commit workflow...
git commit -m "🎮 Add simple build workflow for MathKids APK"

echo [5/5] Push to GitHub...
git push origin main

if %errorlevel% equ 0 (
    echo ✅ PUSH THÀNH CÔNG!
    echo.
    echo 🎉 WORKFLOW ĐÃ ĐƯỢC PUSH!
    echo.
    echo 📋 Bước tiếp theo:
    echo 1. Refresh GitHub Actions page (F5)
    echo 2. Tìm workflow "🎮 Build MathKids APK Now"
    echo 3. Nhấp "Run workflow"
    echo 4. Chờ build hoàn tất
    echo 5. Tải APK từ "mathkids-apk"
    echo.
    echo 🚀 Workflow đơn giản này sẽ hoạt động!
) else (
    echo ❌ PUSH THẤT BẠI
    echo Kiểm tra lỗi và thử lại
)

echo ========================================
pause
