# 🎮 MathKids - Ứng Dụng Học Toán Cho Trẻ Em

## 📱 Giới Thiệu
**MathKids** là ứng dụng giáo dục Android được thiết kế đặc biệt cho trẻ em Việt Nam, giúp các em học toán một cách vui vẻ và hiệu quả.

## ✨ Tính Năng Chính
- 🔢 **Học Đếm**: Dạy trẻ đếm từ 0-100
- ➕ **Phép Cộng**: Luyện tập phép cộng cơ bản
- ➖ **Phép Trừ**: Thực hành phép trừ đơn giản
- 🎯 **Nhận Biết Số**: Học nhận biết và viết số
- 🏆 **Hệ Thống Thưởng**: Động viên trẻ học tập
- 🎨 **Giao Diện Thân Thiện**: Màu sắc tươi sáng, phù hợp trẻ em

## 🛠️ Công Nghệ Sử Dụng
- **Platform**: Android (API 21+)
- **Language**: Java
- **Build Tool**: Gradle
- **UI Framework**: Material Design 3
- **Target Age**: 3-8 tuổi

## 📋 Yêu Cầu Hệ Thống
- Android 5.0 (API level 21) trở lên
- RAM: Tối thiểu 2GB
- Dung lượng: 50MB trống

## 🚀 Cài Đặt và Build

### Yêu Cầu Phát Triển:
- Android Studio Arctic Fox trở lên
- JDK 11 trở lên
- Android SDK 33

### Các Bước Build:
1. Clone repository này
2. Mở project trong Android Studio
3. Sync project với Gradle files
4. Build APK: `Build → Build Bundle(s) / APK(s) → Build APK(s)`

```bash
# Clone repository
git clone https://github.com/[username]/MathKids-Vietnamese-Educational-Game.git

# Mở trong Android Studio và build
./gradlew assembleDebug
```

## 📁 Cấu Trúc Project
```
app/
├── src/main/
│   ├── java/com/mathkids/education/
│   │   ├── activities/          # Các Activity chính
│   │   ├── fragments/           # Fragments cho từng module
│   │   ├── adapters/           # RecyclerView Adapters
│   │   ├── models/             # Data models
│   │   ├── utils/              # Utility classes
│   │   └── views/              # Custom views
│   ├── res/
│   │   ├── layout/             # XML layouts
│   │   ├── drawable/           # Hình ảnh và icons
│   │   ├── values/             # Strings, colors, styles
│   │   ├── anim/               # Animations
│   │   └── font/               # Custom fonts
│   └── AndroidManifest.xml
```

## 🎯 Modules Giáo Dục

### 1. 🔢 Module Đếm Số
- Học đếm từ 1-100
- Nhận biết số qua hình ảnh
- Phát âm số bằng tiếng Việt

### 2. ➕ Module Phép Cộng
- Phép cộng 1 chữ số
- Phép cộng có nhớ
- Bài tập tương tác

### 3. ➖ Module Phép Trừ
- Phép trừ cơ bản
- Phép trừ có mượn
- Game thực hành

### 4. 🎯 Module Nhận Biết
- Nhận biết số trong đời sống
- Viết số theo mẫu
- Trò chơi ghép số

## 🏆 Hệ Thống Thành Tích
- ⭐ Sao vàng cho bài làm đúng
- 🏅 Huy chương cho hoàn thành module
- 🎖️ Danh hiệu đặc biệt
- 📊 Theo dõi tiến độ học tập

## 🎨 Thiết Kế UI/UX
- **Màu sắc**: Tươi sáng, phù hợp trẻ em
- **Font chữ**: Dễ đọc, cỡ chữ lớn
- **Animation**: Mượt mà, thu hút
- **Sound**: Âm thanh vui nhộn
- **Touch**: Tối ưu cho tay nhỏ

## 🌐 Ngôn Ngữ
- **Chính**: Tiếng Việt
- **Phụ**: Tiếng Anh (tùy chọn)

## 📊 Tính Năng Theo Dõi
- Thời gian học tập
- Số bài đã hoàn thành
- Tỷ lệ đúng/sai
- Báo cáo tiến độ cho phụ huynh

## 🔧 Troubleshooting

### Lỗi Build Thường Gặp:
1. **Gradle Sync Failed**: Kiểm tra kết nối internet
2. **SDK Not Found**: Cài đặt Android SDK đúng version
3. **Resource Error**: Kiểm tra file XML resources

### Fix Lỗi Phổ Biến:
```bash
# Clear Gradle cache
./gradlew clean

# Invalidate caches trong Android Studio
File → Invalidate Caches and Restart
```

## 🤝 Đóng Góp
Chúng tôi hoan nghênh mọi đóng góp! Vui lòng:
1. Fork repository
2. Tạo feature branch
3. Commit changes
4. Push to branch
5. Tạo Pull Request

## 📄 License
Dự án này được phát hành dưới [MIT License](LICENSE).

## 👥 Tác Giả
- **Developer**: [Tên của bạn]
- **Email**: [email@example.com]
- **GitHub**: [github.com/username]

## 🙏 Cảm Ơn
- Material Design team cho design guidelines
- Android Developer community
- Các phụ huynh và giáo viên đã góp ý

## 📞 Liên Hệ
- **Issues**: [GitHub Issues](https://github.com/[username]/MathKids-Vietnamese-Educational-Game/issues)
- **Email**: support@mathkids.com
- **Website**: [mathkids.com](https://mathkids.com)

---
**Made with ❤️ for Vietnamese children**
