# 🔧 Hướng Dẫn Sửa Lỗi Gradle Sync

## 📋 Tổng Quan
Project này chứa các script tự động để sửa lỗi Gradle sync trong Android Studio.

## 🚀 Cách Sử Dụng

### Bước 1: Sửa Lỗi Gradle Nhanh
```bash
fix_gradle_simple.bat
```
- Dọn dẹp cache Gradle
- Tạo lại Gradle wrapper
- Chuẩn bị project cho sync

### Bước 2: Sửa Lỗi Gradle Hoàn Chỉnh
```bash
fix_gradle_complete.bat
```
- Thực hiện tất cả bước sửa lỗi
- Tự động mở Android Studio
- Hướng dẫn sync project

### Bước 3: Mở Android Studio
```bash
open_android_studio.bat
```
- Tự động tìm và mở Android Studio
- Mở project hiện tại
- Hiển thị hướng dẫn sync

## 📁 Cấu Trúc Files

### Scripts Chính
- `fix_gradle_simple.bat` - Sửa lỗi cơ bản
- `fix_gradle_complete.bat` - Sửa lỗi hoàn chỉnh
- `open_android_studio.bat` - Mở Android Studio

### Files Gradle
- `build.gradle` - Cấu hình project
- `settings.gradle` - Cài đặt project
- `gradle/wrapper/gradle-wrapper.properties` - Cấu hình Gradle wrapper

## 🔍 Xử Lý Lỗi Thường Gặp

### Lỗi: "Gradle sync failed"
1. Chạy `fix_gradle_simple.bat`
2. Mở Android Studio
3. Chọn "Sync Now"

### Lỗi: "Could not find gradle wrapper"
1. Chạy `fix_gradle_complete.bat`
2. Đợi script hoàn thành
3. Gradle wrapper sẽ được tạo tự động

### Lỗi: "Build failed"
1. Trong Android Studio: Build → Clean Project
2. Sau đó: Build → Rebuild Project
3. Nếu vẫn lỗi, chạy lại `fix_gradle_complete.bat`

## ⚙️ Cấu Hình

### Android Studio Path
Script tự động tìm Android Studio tại:
- `D:\Androidstudio\bin\studio64.exe`
- `D:\Androidstudio\bin\studio.exe`
- `C:\Program Files\Android\Android Studio\bin\studio64.exe`

### Gradle Version
- Gradle: 8.2
- Android Gradle Plugin: 8.2.0
- Kotlin: 1.9.22

## 📞 Hỗ Trợ

Nếu gặp vấn đề:
1. Kiểm tra kết nối internet
2. Đảm bảo Android Studio đã cài đặt
3. Chạy lại script với quyền Administrator
4. Kiểm tra Windows Defender/Antivirus

## 🎯 Lưu Ý Quan Trọng

- Luôn đóng Android Studio trước khi chạy script
- Đảm bảo có kết nối internet để tải Gradle
- Chờ đợi quá trình sync hoàn thành
- Không gián đoạn quá trình sync đầu tiên

## ✅ Kiểm Tra Thành Công

Gradle sync thành công khi:
- Không có lỗi đỏ trong Android Studio
- Project structure hiển thị đầy đủ
- Có thể build project thành công
- Gradle tasks hiển thị trong panel bên phải
