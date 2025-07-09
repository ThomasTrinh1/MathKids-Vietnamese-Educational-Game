# Hướng Dẫn Cài Đặt MathKids

## 📋 Mục Lục
1. [Cài Đặt APK Cho Người Dùng](#cài-đặt-apk-cho-người-dùng)
2. [Build Từ Source Code](#build-từ-source-code)
3. [Cài Đặt Môi Trường Phát Triển](#cài-đặt-môi-trường-phát-triển)
4. [Khắc Phục Sự Cố](#khắc-phục-sự-cố)

## 📱 Cài Đặt APK Cho Người Dùng

### Bước 1: Tải APK
1. Truy cập thư mục `output/` trong project
2. Tải file `MathKids-release.apk` (phiên bản chính thức)
3. Hoặc tải `MathKids-debug.apk` (phiên bản test)

### Bước 2: Chuẩn Bị Thiết Bị Android
1. **Bật Nguồn Không Xác Định**:
   ```
   Android 8.0+:
   Cài đặt > Ứng dụng > Menu (⋮) > Truy cập đặc biệt > 
   Cài đặt ứng dụng không xác định > Chọn trình duyệt/file manager > Cho phép
   
   Android 7.0 và cũ hơn:
   Cài đặt > Bảo mật > Nguồn không xác định > Bật
   ```

2. **Kiểm Tra Dung Lượng**:
   - Cần ít nhất 100MB dung lượng trống
   - Khuyến nghị 200MB để hoạt động mượt mà

### Bước 3: Cài Đặt
1. Mở file APK đã tải (từ Downloads hoặc file manager)
2. Nhấn "Cài đặt" khi được hỏi
3. Chờ quá trình cài đặt hoàn tất (30-60 giây)
4. Nhấn "Mở" để khởi chạy ứng dụng

### Bước 4: Kiểm Tra Cài Đặt
1. Tìm biểu tượng MathKids trên màn hình chính
2. Nhấn để mở ứng dụng
3. Kiểm tra âm thanh và các tính năng cơ bản

## 🛠️ Build Từ Source Code

### Yêu Cầu Hệ Thống
- **Hệ điều hành**: Windows 10/11, macOS 10.14+, Ubuntu 18.04+
- **RAM**: Tối thiểu 8GB (khuyến nghị 16GB)
- **Dung lượng**: 10GB trống cho Android Studio và SDK
- **Internet**: Cần kết nối để tải dependencies

### Bước 1: Cài Đặt Java JDK
```bash
# Windows (sử dụng Chocolatey)
choco install openjdk11

# macOS (sử dụng Homebrew)
brew install openjdk@11

# Ubuntu
sudo apt update
sudo apt install openjdk-11-jdk
```

### Bước 2: Cài Đặt Android Studio
1. Tải Android Studio từ: https://developer.android.com/studio
2. Cài đặt theo hướng dẫn của hệ điều hành
3. Khởi chạy và cài đặt Android SDK (API 21-33)

### Bước 3: Cấu Hình Biến Môi Trường
```bash
# Windows (thêm vào System Environment Variables)
ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\Sdk
JAVA_HOME=C:\Program Files\Java\jdk-11.0.16

# macOS/Linux (thêm vào ~/.bashrc hoặc ~/.zshrc)
export ANDROID_HOME=$HOME/Android/Sdk
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

### Bước 4: Build Project

#### Sử Dụng Script Tự Động (Windows)
```bash
cd D:\Augment\Mobileappthanhpphong
build-android-app.bat
```

#### Build Thủ Công
```bash
# Mở terminal trong thư mục project
cd D:\Augment\Mobileappthanhpphong

# Cấp quyền thực thi (macOS/Linux)
chmod +x gradlew

# Clean project
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Chạy tests
./gradlew test
```

### Bước 5: Tìm APK Đã Build
```
Vị trí APK sau khi build:
- Debug: app/build/outputs/apk/debug/app-debug.apk
- Release: app/build/outputs/apk/release/app-release.apk
```

## 🔧 Cài Đặt Môi Trường Phát Triển

### Android Studio Setup
1. **SDK Manager**:
   - Mở Android Studio > Tools > SDK Manager
   - Cài đặt Android SDK Platform 21-33
   - Cài đặt Android SDK Build-Tools 33.0.0
   - Cài đặt Android Emulator

2. **AVD Manager** (Tạo máy ảo test):
   - Tools > AVD Manager > Create Virtual Device
   - Chọn Pixel 4 hoặc tablet
   - Chọn API 30 (Android 11)
   - Finish và Start emulator

3. **Gradle Configuration**:
   - File > Settings > Build > Gradle
   - Gradle JVM: Chọn Java 11
   - Use Gradle from: 'gradle-wrapper.properties'

### IDE Plugins (Khuyến nghị)
```
Cài đặt plugins hữu ích:
- Kotlin (đã có sẵn)
- Android APK Support
- Rainbow Brackets
- GitToolBox
- Material Theme UI
```

## 🚨 Khắc Phục Sự Cố

### Lỗi Cài Đặt APK

#### "App not installed" hoặc "Parse error"
```
Nguyên nhân: APK bị hỏng hoặc không tương thích
Giải pháp:
1. Tải lại APK từ nguồn chính thức
2. Kiểm tra phiên bản Android (cần 5.0+)
3. Xóa phiên bản cũ trước khi cài mới
4. Khởi động lại thiết bị
```

#### "Installation blocked"
```
Nguyên nhân: Bảo mật thiết bị chặn
Giải pháp:
1. Bật "Nguồn không xác định" trong Cài đặt
2. Tạm thời tắt antivirus
3. Sử dụng file manager khác để mở APK
```

### Lỗi Build

#### "SDK not found"
```bash
# Kiểm tra ANDROID_HOME
echo $ANDROID_HOME  # macOS/Linux
echo %ANDROID_HOME% # Windows

# Cài đặt lại nếu cần
# Mở Android Studio > Tools > SDK Manager
```

#### "Java version incompatible"
```bash
# Kiểm tra Java version
java -version
javac -version

# Phải là Java 11 hoặc 17
# Cài đặt lại Java nếu cần
```

#### "Gradle build failed"
```bash
# Clean và rebuild
./gradlew clean
./gradlew build --refresh-dependencies

# Xóa cache Gradle
rm -rf ~/.gradle/caches  # macOS/Linux
rmdir /s %USERPROFILE%\.gradle\caches  # Windows
```

#### "Out of memory"
```bash
# Tăng memory cho Gradle (gradle.properties)
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=512m
```

### Lỗi Runtime

#### Ứng dụng crash khi khởi động
```
Kiểm tra:
1. Phiên bản Android tối thiểu (API 21)
2. RAM thiết bị (cần ít nhất 2GB)
3. Dung lượng trống (cần ít nhất 100MB)
4. Xem logs: adb logcat | grep MathKids
```

#### Không có âm thanh
```
Kiểm tra:
1. Âm lượng thiết bị
2. Cài đặt âm thanh trong app
3. Text-to-Speech engine (Cài đặt > Ngôn ngữ > Text-to-speech)
```

### Lỗi Performance

#### Ứng dụng chạy chậm
```
Tối ưu:
1. Đóng các app khác
2. Khởi động lại thiết bị
3. Xóa cache: Cài đặt > Apps > MathKids > Storage > Clear Cache
4. Giảm độ phân giải màn hình nếu có thể
```

## 📞 Hỗ Trợ Kỹ Thuật

### Thông Tin Debug
Khi báo lỗi, vui lòng cung cấp:
```
- Phiên bản Android: 
- Model thiết bị: 
- RAM: 
- Dung lượng trống: 
- Phiên bản MathKids: 
- Mô tả lỗi chi tiết: 
- Bước tái hiện lỗi: 
```

### Liên Hệ
- **Email**: support@mathkids.com
- **GitHub Issues**: [Link repository]
- **Telegram**: @mathkids_support

---

**Cập nhật lần cuối**: 09/01/2025  
**Phiên bản hướng dẫn**: 1.0.0
