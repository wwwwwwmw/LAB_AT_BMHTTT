\# LAB 3: MÃ HÓA KHÓA CÔNG KHAI



\* \*\*Học phần:\*\* An toàn và Bảo mật thông tin

\* \*\*Sinh viên thực hiện:\*\* Lê Trí Anh

\* \*\*MSSV:\*\* 1150080125

\* \*\*Công nghệ sử dụng:\*\* Java (JDK 17, Swing, Maven), Microsoft SQL Server (JDBC)



\---



\## 1. Nội dung thực hành



Dự án triển khai các thuật toán mật mã khóa công khai, hệ mã lai, chữ ký số và lưu vết lịch sử thao tác vào cơ sở dữ liệu:



\* \*\*3.1. RSA Console (`RSADemo`):\*\* Tạo cặp khóa 2048/3072 bit, mã hóa và giải mã chuẩn OAEP (SHA-256) trên cửa sổ Output.

\* \*\*3.2. RSA Swing (`frm\_RSA`):\*\* Giao diện đồ họa mã hóa/giải mã thông điệp ngắn bằng RSA-OAEP.

\* \*\*3.3. ECC (`frm\_ECC`):\*\* Mã hóa theo sơ đồ ECIES (ECDH đường cong `secp256r1` + HKDF-SHA256 + AES-256-GCM), hỗ trợ lưu và nạp cặp khóa.

\* \*\*3.4. Hệ mã lai RSA + AES (`frm\_RSA\_AES`):\*\* Kết hợp AES-256-GCM mã hóa văn bản dài và RSA-OAEP bọc khóa phiên AES, kèm nút kiểm thử tính toàn vẹn dữ liệu.

\* \*\*3.5. Chữ ký số ECDSA (`frm\_ECC\_Signature`):\*\* Ký thông điệp bằng khóa riêng (`SHA256withECDSA`) và xác minh chữ ký bằng khóa công khai (`VALID` / `INVALID`).

\* \*\*Quản trị CSDL \& Lịch sử:\*\* Kết nối SQL Server (`LAB3\_MaHoaKhoaCongKhai`) và theo dõi nhật ký thao tác trên giao diện `frm\_LichSu`\[cite: 67, 77, 82, 86, 87, 90, 96, 103].



\---



\## 2. Cấu trúc thư mục



LAB3\_1150080125\_LeTriAnh/

├── docs/              # Báo cáo thực hành và hình ảnh minh chứng kết quả

├── sql/               # Script tạo CSDL (01\_Tao\_CSDL.sql) và truy vấn kiểm tra (03\_KiemTra\_LichSu.sql)

├── src/               # Mã nguồn Java (.java) và giao diện (.form) trong package week\_03

├── pom.xml            # Cấu hình Maven và thư viện mssql-jdbc

└── README.md          # Tài liệu hướng dẫn dự án

