# LAB 4: KHẢO SÁT VÀ ĐÁNH GIÁ BỀ MẶT MẠNG BẰNG NMAP

## 1. Thông tin sinh viên
- **Họ và tên:** Lê Trí Anh
- **MSSV:** 1150080125
- **Lớp:** 11CNPM2

## 2. Môi trường thực hành
- **Ảo hóa:** VMware Workstation Pro (Cấu hình mạng Host-Only dải `192.168.74.0/24`).
- **Máy quét chính (Attacker):** Kali Linux (IP: `192.168.74.130`).
- **Máy mục tiêu (Target):** Metasploitable 2 (IP: `192.168.74.129`).
- **Công cụ sử dụng:** `nmap`, lệnh `ping`, `ip`, `ifconfig`.

## 3. Các tình huống thực hiện & Kết quả
- **Phát hiện host (Host Discovery):** Dùng `nmap -sn` để lập bản đồ các thiết bị đang hoạt động trong mạng Host-Only -> **PASS**
- **Khảo sát cổng TCP:** Thực hiện và so sánh kỹ thuật quét TCP Connect (`-sT`) và TCP SYN (`-sS`) -> **PASS**
- **Khảo sát cổng UDP:** Quét 20 cổng UDP phổ biến nhất (`-sU`) -> **PASS**
- **Nhận diện hệ thống:** Dùng `-sV` để xác định phiên bản dịch vụ (phát hiện Web, SSH, FTP cũ) và `-O` để đoán hệ điều hành (Linux 2.6.x) -> **PASS**
- **Kiểm tra lỗ hổng bằng NSE Script:** Dùng script `smb-vuln-ms17-010` rà quét cổng 445 -> **PASS**
- **Bảo vệ hệ thống (Hardening):** Thực hành ngắt dịch vụ Apache (cổng 80) trên máy đích và dùng Kali quét lại để chứng minh cổng đã chuyển sang trạng thái `closed` -> **PASS**
- **Báo cáo:** Xuất kết quả rà quét ra file text (`-oN`) để lưu trữ bằng chứng -> **PASS**

## 4. Lỗi gặp phải & Cách khắc phục
- **Lỗi `invalid argument` khi dùng lệnh ping trên Kali:** Do gõ thiếu tham số số lượng gói tin (gõ `ping -c 192...`). *Khắc phục:* Thêm số lượng gói tin cụ thể sau tham số `-c`, ví dụ: `ping -c 4 192.168.74.129`.
- **Nmap báo lỗi không đủ quyền khi chạy lệnh SYN Scan (`-sS`) hoặc OS Detection (`-O`):** Do các kỹ thuật này can thiệp trực tiếp vào raw packet ở tầng thấp. *Khắc phục:* Bổ sung `sudo` vào đầu câu lệnh (ví dụ: `sudo nmap -sS...`) và nhập mật khẩu quản trị.