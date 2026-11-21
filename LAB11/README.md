# LAB 2: MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI

* **Học phần:** An toàn và Bảo mật thông tin
* **Sinh viên thực hiện:** Lê Trí Anh
* **MSSV:** 1150080125
* **Công nghệ sử dụng:** Java (JDK 17, Swing, Maven, BouncyCastle), Python 3 (Tkinter, `cryptography`), Microsoft SQL Server

---

## 1. Nội dung thực hành

Dự án xây dựng ứng dụng mã hóa, giải mã đối xứng hiện đại, quản lý xác thực người dùng và lưu vết lịch sử thao tác vào cơ sở dữ liệu:

* **2.1. DES:** Mã hóa khối 64-bit, chế độ CBC kèm IV ngẫu nhiên 8 byte và đệm PKCS5.
* **2.2. TripleDES (3DES):** Mã hóa DES 3 lần với khóa 24 byte, chế độ CBC.
* **2.3. AES-256 (GCM) + Register / Login:** Kết hợp đăng ký/đăng nhập lưu mật khẩu băm PBKDF2 + Salt vào SQL Server, cấp phiên làm việc (Ticket) và mã hóa xác thực chống sửa đổi bản mã (GCM).
* **2.4. RC4:** Mật mã dòng sinh keystream (KSA + PRGA), xuất bản mã dạng chuỗi HEX.
* **2.5. Twofish:** Mã hóa khối 128-bit chế độ CBC sử dụng thư viện BouncyCastle, hỗ trợ sinh IV ngẫu nhiên và lưu/khôi phục file kèm IV.
* **2.6. Bài tập nâng cao:**
  * **AES-256-CTR (Java):** Mã hóa chế độ Counter biến AES thành mã dòng không cần đệm khối.
  * **AES-256-CBC (Python):** Ứng dụng giao diện Tkinter thực hiện mã hóa/giải mã AES-CBC kèm kiểm thử tự động (`--self-test`).
* **Quản trị CSDL & Lịch sử:** Kết nối SQL Server (`LAB2_MaHoaHienDai`) và xem lại lịch sử thao tác trên giao diện `frm_LichSu`[cite: 15, 22, 30, 34, 38, 44, 48, 52, 57].

---

## 2. Cấu trúc thư mục

```text
LAB2_1150080125_LeTriAnh/
├── docs/              # Báo cáo thực hành và hình ảnh minh chứng kết quả
├── LAB2_Python/       # Chương trình AES-256-CBC viết bằng Python (aes_cbc_gui.py)
├── sql/               # Script tạo CSDL (01_Tao_CSDL.sql) và truy vấn kiểm tra (03_Kiem_Tra_Du_Lieu.sql)
├── src/               # Mã nguồn Java (.java) và giao diện (.form) trong package week_02
├── pom.xml            # Cấu hình Maven (mssql-jdbc, bcprov-jdk18on)
└── README.md          # Tài liệu hướng dẫn dự án