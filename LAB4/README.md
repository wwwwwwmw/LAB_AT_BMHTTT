# LAB 3: NHẬN DIỆN VÀ ỨNG PHÓ CÁC MỐI ĐE DỌA ĐẾN AN TOÀN THÔNG TIN

## 1. Thông tin sinh viên
- **Họ và tên:** Lê Trí Anh
- **MSSV:** 1150080125
- **Lớp:** 11CNPM2

## 2. Môi trường thực hành
- **Ảo hóa:** VMware Workstation Pro 26H1 (Mạng Host-only).
- **Máy ảo:** Windows 11 25H2 x64, OS build 26200.9445.
- **Công cụ:** Sysmon, Autoruns, Process Explorer, Wireshark, Python.
- **Cách dựng môi trường:** Cài đặt máy ảo trên VMware, cấu hình card mạng Host-only để cô lập hệ thống, cập nhật Defender. Thiết lập thư mục `C:\LAB3`, cài đặt các công cụ bằng lệnh `winget` và tạo snapshot `LAB3_CLEAN_20260914` trước khi làm bài.

## 3. Các tình huống thực hiện & Kết quả
- **TH1:** Xác định tài sản, lỗ hổng, mối đe dọa và rủi ro -> **PASS**
- **TH2:** Nhận diện mã độc bằng mẫu EICAR và Defender -> **PASS**
- **TH3:** Phân tích tấn công mật khẩu và nguy cơ keylogging qua Event Log -> **PASS**
- **TH4:** Nhận diện Backdoor (persistence và dịch vụ lắng nghe) qua Sysmon/Autoruns -> **PASS**
- **TH5:** Phân tích Sniffing, MITM và Spoofing (HTTP vs HTTPS) bằng Wireshark -> **PASS**
- **TH6:** Phân tích DoS, DDoS và Mail Bombing qua dataset -> **PASS**
- **TH7:** Phân tích Social Engineering và Phishing offline -> **PASS**
- **Cleanup:** Cô lập, dọn dẹp hệ thống và xuất mã băm SHA-256 -> **PASS**

## 4. Lỗi gặp phải & Cách khắc phục
- **Lỗi Access Denied khi tạo tài khoản/dọn dẹp tác vụ:** Do chạy PowerShell ở chế độ User thường. *Khắc phục:* Tắt và mở lại PowerShell bằng tùy chọn `Run as Administrator`.
- **Lỗi không tìm thấy đường dẫn Registry (Cannot find path):** Do gõ thiếu cú pháp. *Khắc phục:* Thêm dấu hai chấm vào biến đường dẫn (đổi `HKCU` thành `HKCU:\`).
- **Lỗi xung đột file khi tính mã băm SHA-256:** Do tiến trình Export-Csv khóa file dữ liệu. *Khắc phục:* Bổ sung tham số `-Exclude evidence_sha256.csv` để loại trừ file đích khỏi quá trình quét mã băm.