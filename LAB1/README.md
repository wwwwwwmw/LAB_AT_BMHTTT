Lab 1: Examining SSH & Telnet in Wireshark
Môn học: Thực hành An toàn Hệ thống thông tin

Tổng quan
Bài thực hành này tập trung vào việc triển khai, phân tích và so sánh mức độ bảo mật của hai giao thức quản trị từ xa phổ biến: Telnet (giao thức cũ, không mã hóa) và SSH (giao thức hiện đại, mã hóa an toàn). Quá trình phân tích lưu lượng mạng được thực hiện thông qua công cụ Wireshark để có cái nhìn thực tế nhất về cách dữ liệu truyền tải.

Những nội dung đã thực hiện
Thiết lập môi trường: Xây dựng thành công mô hình mạng cục bộ gồm 1 máy chủ (Ubuntu Server 26.04) và 1 máy khách kiêm giám sát mạng (Windows 11).

Xử lý sự cố kỹ thuật: Triển khai thành công dịch vụ Telnet trên hệ điều hành Ubuntu mới bằng cách sử dụng socat để giả lập terminal (PTY), vượt qua các hạn chế khắt khe của hệ thống bảo mật hiện đại.

Bắt và phân tích gói tin Telnet:

Sử dụng Wireshark bắt luồng dữ liệu (TCP Stream) qua cổng 23.

Chứng minh thực nghiệm: Telnet truyền dữ liệu dưới dạng bản rõ (Plaintext). Mọi thông tin nhạy cảm bao gồm tài khoản, mật khẩu (dù phức tạp đến đâu) và các câu lệnh đều bị lộ hoàn toàn.

Bắt và phân tích gói tin SSH:

Bắt luồng dữ liệu SSH qua cổng 22.

Chứng minh thực nghiệm: Kênh truyền SSH được mã hóa toàn bộ (Ciphertext). Kẻ tấn công không thể đọc hay khôi phục được thông tin đăng nhập và các lệnh đã thao tác.

Phân tích bảo mật & Hardening: Phân tích sâu sự khác biệt giữa hai giao thức dựa trên 3 tiêu chí cốt lõi (Tính bí mật, Tính toàn vẹn, Xác thực) và đề xuất các biện pháp cấu hình gia cố (hardening) cho máy chủ SSH trong môi trường thực tế.

Công cụ sử dụng
Hệ điều hành: Ubuntu Server, Windows 11.

Phần mềm: VMware Workstation, Wireshark (kèm Npcap), PuTTY.

Công cụ command-line: socat, telnetd, openssh-server.

Tác giả: Lê Trí Anh - 1150080125