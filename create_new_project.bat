@echo off
echo ========================================
echo    TẠO PROJECT ANDROID MỚI HOÀN TOÀN
echo ========================================

echo [1/8] Backup project hiện tại...
if exist backup rmdir /s /q backup
mkdir backup
xcopy /s /e /h /y app backup\app\ >nul 2>&1
xcopy /s /e /h /y .github backup\.github\ >nul 2>&1
echo ✅ Đã backup app và .github

echo [2/8] Xóa Gradle files cũ bị hỏng...
if exist gradle rmdir /s /q gradle
if exist gradlew del gradlew
if exist gradlew.bat del gradlew.bat
if exist gradle.properties del gradle.properties
if exist settings.gradle del settings.gradle
echo ✅ Đã xóa Gradle files cũ

echo [3/8] Tạo settings.gradle mới...
echo pluginManagement { > settings.gradle
echo     repositories { >> settings.gradle
echo         google() >> settings.gradle
echo         mavenCentral() >> settings.gradle
echo         gradlePluginPortal() >> settings.gradle
echo     } >> settings.gradle
echo } >> settings.gradle
echo dependencyResolutionManagement { >> settings.gradle
echo     repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) >> settings.gradle
echo     repositories { >> settings.gradle
echo         google() >> settings.gradle
echo         mavenCentral() >> settings.gradle
echo     } >> settings.gradle
echo } >> settings.gradle
echo rootProject.name = "MathKids" >> settings.gradle
echo include ':app' >> settings.gradle

echo [4/8] Tạo gradle.properties mới...
echo org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8 > gradle.properties
echo org.gradle.caching=true >> gradle.properties
echo org.gradle.parallel=true >> gradle.properties
echo android.useAndroidX=true >> gradle.properties
echo android.enableJetifier=true >> gradle.properties
echo android.nonTransitiveRClass=false >> gradle.properties

echo [5/8] Tạo thư mục Gradle wrapper...
mkdir gradle\wrapper

echo [6/8] Tạo gradle-wrapper.properties đúng chuẩn...
echo distributionBase=GRADLE_USER_HOME > gradle\wrapper\gradle-wrapper.properties
echo distributionPath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties
echo distributionUrl=https\://services.gradle.org/distributions/gradle-8.2-bin.zip >> gradle\wrapper\gradle-wrapper.properties
echo networkTimeout=10000 >> gradle\wrapper\gradle-wrapper.properties
echo validateDistributionUrl=true >> gradle\wrapper\gradle-wrapper.properties
echo zipStoreBase=GRADLE_USER_HOME >> gradle\wrapper\gradle-wrapper.properties
echo zipStorePath=wrapper/dists >> gradle\wrapper\gradle-wrapper.properties

echo [7/8] Tải Gradle wrapper JAR đúng chuẩn...
powershell -Command "try { $ProgressPreference = 'SilentlyContinue'; Invoke-WebRequest -Uri 'https://github.com/gradle/gradle/raw/v8.2.0/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle/wrapper/gradle-wrapper.jar' -ErrorAction Stop; Write-Host '✅ Tải gradle-wrapper.jar thành công' } catch { Write-Host '❌ Lỗi tải gradle-wrapper.jar' }"

echo [8/8] Tạo gradlew scripts mới...
powershell -Command "try { Invoke-WebRequest -Uri 'https://github.com/gradle/gradle/raw/v8.2.0/gradlew' -OutFile 'gradlew' -ErrorAction Stop; Write-Host '✅ Tải gradlew thành công' } catch { Write-Host '❌ Lỗi tải gradlew' }"
powershell -Command "try { Invoke-WebRequest -Uri 'https://github.com/gradle/gradle/raw/v8.2.0/gradlew.bat' -OutFile 'gradlew.bat' -ErrorAction Stop; Write-Host '✅ Tải gradlew.bat thành công' } catch { Write-Host '❌ Lỗi tải gradlew.bat' }"

echo ========================================
echo    KIỂM TRA FILES ĐÃ TẠO
echo ========================================

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

if exist settings.gradle (
    echo ✅ settings.gradle: OK
) else (
    echo ❌ settings.gradle: MISSING
)

echo ========================================
echo    TEST BUILD LOCAL
echo ========================================

echo Thử build local để kiểm tra...
gradlew.bat clean --stacktrace
if %errorlevel% equ 0 (
    echo ✅ LOCAL BUILD: THÀNH CÔNG!
    echo Bây giờ có thể push lên GitHub
) else (
    echo ❌ LOCAL BUILD: VẪN LỖI
    echo Cần kiểm tra thêm
)

echo ========================================
pause
