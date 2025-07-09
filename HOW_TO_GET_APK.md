# 📱 **CÁCH TẢI APK MATHKIDS - HƯỚNG DẪN HOÀN CHỈNH**

## 🎯 **TÓM TẮT:**
Repository GitHub chỉ chứa **source code**, không có APK sẵn. Bạn cần **build APK từ source code**.

---

## 🚀 **CÁCH 1: BUILD APK BẰNG ANDROID STUDIO (KHUYẾN NGHỊ)**

### **Bước 1: Tải Source Code**
```bash
# Clone repository về máy
git clone https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game.git
cd MathKids-Vietnamese-Educational-Game
```

### **Bước 2: Mở trong Android Studio**
1. **Khởi động Android Studio**
2. **File → Open** → Chọn thư mục `MathKids-Vietnamese-Educational-Game`
3. **Chờ indexing hoàn tất** (2-5 phút)

### **Bước 3: Fix Build Issues**
1. **File → Invalidate Caches and Restart** (QUAN TRỌNG)
2. **Chờ Android Studio restart và re-index**

### **Bước 4: Build APK**
1. **Build → Clean Project**
2. **Build → Rebuild Project**
3. **Build → Build Bundle(s) / APK(s) → Build APK(s)**
4. **Chờ 5-15 phút** để build hoàn tất

### **Bước 5: Tìm APK**
- **Vị trí**: `app/build/outputs/apk/debug/app-debug.apk`
- **Kích thước**: Khoảng 15-25 MB
- **Tên file**: `app-debug.apk`

---

## 🤖 **CÁCH 2: SỬ DỤNG GITHUB ACTIONS (TỰ ĐỘNG)**

### **Bước 1: Kích hoạt GitHub Actions**
1. Vào repository: https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game
2. Nhấp tab **"Actions"**
3. Nhấp **"I understand my workflows, go ahead and enable them"**

### **Bước 2: Chạy Build Workflow**
1. Nhấp **"🚀 Build MathKids APK"**
2. Nhấp **"Run workflow"** → **"Run workflow"**
3. **Chờ 10-20 phút** để GitHub build APK

### **Bước 3: Tải APK**
1. Sau khi build xong, vào tab **"Actions"**
2. Nhấp vào build job mới nhất (có dấu ✅)
3. Scroll xuống phần **"Artifacts"**
4. Nhấp **"mathkids-debug-apk"** để tải về

### **Bước 4: Giải nén và cài đặt**
1. Giải nén file zip đã tải
2. Lấy file `app-debug.apk`
3. Copy vào điện thoại Android và cài đặt

---

## 📦 **CÁCH 3: TẢI APK TỪ RELEASES (SAU KHI GITHUB ACTIONS CHẠY)**

### **Khi GitHub Actions hoàn tất:**
1. Vào: https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game/releases
2. Tìm release mới nhất (ví dụ: `v2024.01.09-build1430`)
3. Nhấp **"app-debug.apk"** để tải trực tiếp

---

## 🔧 **CÁCH 4: BUILD BẰNG COMMAND LINE**

### **Yêu cầu:**
- Java JDK 11+
- Android SDK
- Git

### **Các lệnh:**
```bash
# 1. Clone repository
git clone https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game.git
cd MathKids-Vietnamese-Educational-Game

# 2. Cấp quyền cho gradlew (Linux/Mac)
chmod +x gradlew

# 3. Build APK
./gradlew clean assembleDebug

# 4. Tìm APK
ls -la app/build/outputs/apk/debug/
```

---

## 📱 **CÁCH CÀI ĐẶT APK TRÊN ANDROID:**

### **Bước 1: Bật Unknown Sources**
1. **Settings → Security → Unknown Sources** (Android cũ)
2. **Settings → Apps → Special Access → Install Unknown Apps** (Android mới)

### **Bước 2: Cài đặt APK**
1. Copy file `app-debug.apk` vào điện thoại
2. Mở File Manager, tìm file APK
3. Nhấp vào file APK
4. Nhấp **"Install"**
5. Chờ cài đặt hoàn tất

### **Bước 3: Mở ứng dụng**
1. Tìm icon **"MathKids"** trên màn hình chính
2. Nhấp để mở ứng dụng
3. Bắt đầu học toán! 🎉

---

## ⚠️ **TROUBLESHOOTING:**

### **Lỗi Build trong Android Studio:**
```
Giải pháp:
1. File → Invalidate Caches and Restart
2. Build → Clean Project
3. Kiểm tra internet connection
4. Cập nhật Android Studio
```

### **Lỗi GitHub Actions:**
```
Nguyên nhân: Repository chưa enable Actions
Giải pháp: Vào tab Actions → Enable workflows
```

### **Lỗi cài đặt APK:**
```
Nguyên nhân: Chưa bật Unknown Sources
Giải pháp: Settings → Security → Allow Unknown Sources
```

---

## 📊 **THÔNG TIN APK:**

| Thông tin | Chi tiết |
|-----------|----------|
| **Tên file** | `app-debug.apk` |
| **Kích thước** | ~15-25 MB |
| **Android tối thiểu** | 5.0 (API 21) |
| **Ngôn ngữ** | Tiếng Việt |
| **Độ tuổi** | 3-8 tuổi |
| **Loại** | Educational Game |

---

## 🎮 **TÍNH NĂNG TRONG APK:**

✅ **Modules học tập:**
- 🔢 Học đếm (0-100)
- ➕ Phép cộng cơ bản
- ➖ Phép trừ đơn giản
- 🎯 Nhận biết số

✅ **Giao diện:**
- 🎨 Màu sắc tươi sáng
- 👶 Thân thiện với trẻ em
- 🔊 Âm thanh vui nhộn
- 📱 Tối ưu cho touch

✅ **Hệ thống:**
- 🏆 Thành tích và phần thưởng
- 📊 Theo dõi tiến độ
- 💾 Lưu kết quả học tập
- 🌐 Hỗ trợ tiếng Việt

---

## 🔗 **LINKS QUAN TRỌNG:**

- **Repository**: https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game
- **Actions**: https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game/actions
- **Releases**: https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game/releases
- **Issues**: https://github.com/ThomasTrinh1/MathKids-Vietnamese-Educational-Game/issues

---

**🎯 KHUYẾN NGHỊ: Sử dụng CÁCH 1 (Android Studio) để có APK nhanh nhất!**
