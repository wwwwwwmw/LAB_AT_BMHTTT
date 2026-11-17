# LAB 1: MÃ HÓA ĐỐI XỨNG CỔ ĐIỂN

Học phần: An toàn và Bảo mật thông tin
Sinh viên thực hiện: Lê Trí Anh — MSSV: 1150080125
Công nghệ sử dụng: Java (JDK 17), Java Swing (Apache NetBeans GUI Builder), Maven, Microsoft SQL Server (JDBC)



## 1. Nội dung bài Lab

Dự án triển khai các thuật toán mã hóa cổ điển, lưu trữ lịch sử thao tác vào CSDL và phân tích thám mã cơ bản:

6 Thuật toán mã hóa cơ bản:** Caesar, Vigenère, Rail Fence, Playfair, Transposition (Hoán vị cột), Autokey (hỗ trợ nhập/xuất file `.txt`).
Chương trình tổng hợp & Lưu lịch sử (Nâng cao 01):** Tích hợp 6 thuật toán trên một giao diện duy nhất, tự động lưu và truy xuất lịch sử mã hóa/giải mã từ SQL Server (`MahoacodienDB`).
Phân tích tấn công mật mã (Nâng cao 02):** Cài đặt 3 kỹ thuật thám mã gồm Vét cạn (Brute Force Caesar), Phân tích tần suất chữ cái (Frequency Analysis) và Tìm khóa từ bản rõ đã biết (Known Plaintext cho Vigenère).
Menu điều hướng chính (`frm_Menu`):** Quản lý và mở toàn bộ các chức năng của chương trình.



## 2. Cấu trúc thư mục


LAB1_TuLam/
├── docs/          # Báo cáo chi tiết (.docx, .pdf) và ảnh minh chứng kiểm thử
├── sql/           # Script tạo CSDL (01_Tao_CSDL.sql) và truy vấn kiểm tra (03_Kiem_Tra_Du_Lieu.sql)
├── src/           # Mã nguồn Java (.java) và thiết kế giao diện (.form) trong package week_01
├── pom.xml        # Cấu hình Maven và thư viện mssql-jdbc
└── README.md      # Tài liệu mô tả dự án