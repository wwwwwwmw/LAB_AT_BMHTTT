Báo Cáo Thực Hành Lab 5: Thiết Lập Mô Hình Tường Lửa pfSense
1. Tổng Quan Bài Lab
Lab 5 tập trung vào việc triển khai và quản trị hệ thống tường lửa pfSense để bảo vệ an ninh mạng cho doanh nghiệp. Mục tiêu cốt lõi là thiết lập thành công mô hình mạng gồm ba vùng cách ly: WAN (Mạng bên ngoài/Internet), LAN (Mạng nội bộ), và DMZ (Vùng phi quân sự chứa các máy chủ public). Thông qua bài Lab, người học nắm vững cách thiết lập NAT, định tuyến và áp dụng các chính sách kiểm soát truy cập (Firewall Rules) dựa trên các kịch bản thực tế.

2. Mô Hình Mạng Cấu Hình
Hệ thống mạng được ảo hóa trên VMware/VirtualBox với các thành phần chính:

pfSense Firewall (Gateway): Đóng vai trò trung tâm kiểm soát lưu lượng, được cấp 3 card mạng (WAN, LAN, DMZ).

Mạng LAN (10.0.0.0/8): Vùng an toàn nội bộ. Cấu hình máy chủ Windows Server làm Domain Controller (IP tĩnh: 10.0.0.2).

Mạng DMZ (172.16.0.0/16): Vùng công cộng. Cấu hình máy chủ Windows Server chạy dịch vụ IIS làm Web Server (IP tĩnh: 172.16.0.2).

3. Các Hạng Mục Đã Hoàn Thành
Phần 1: Khởi tạo và Cấu hình Nền tảng

Cài đặt thành công pfSense CE 2.7.2 từ file ISO.

Thiết lập địa chỉ IP tĩnh cho cổng LAN và DMZ thông qua Console và WebGUI.

Cấu hình Domain Controller (Active Directory, DNS Forwarder).

Truy cập WebGUI pfSense từ máy thật và chuyển đổi Outbound NAT sang chế độ Hybrid.

Chuẩn hóa ruleset LAN, xóa các rule mặc định (Default allow to any) để áp dụng nguyên tắc bảo mật chặt chẽ (Default Deny).

Phần 2: Xử Lý Các Tình Huống Firewall Thực Tế

Tình huống 1 — Chặn ICMP, cho phép Web/DNS: Thiết lập luật chặn các gói tin Ping (ICMP) từ mạng LAN ra ngoài, nhưng vẫn mở cổng 53 (DNS) và 80/443 (HTTP/HTTPS) để người dùng lướt web bình thường.

Tình huống 2 — Cấp quyền truy cập Internet chỉ định: Viết rule cho phép duy nhất IP của Domain Controller (10.0.0.2) ra ngoài Internet, đồng thời chặn toàn bộ các máy trạm khác trong mạng LAN (10.0.0.3 trở đi).

Tình huống 3 — Cô lập vùng DMZ: Thiết lập luật ngăn chặn máy chủ trong vùng DMZ khởi tạo kết nối ngược vào mạng LAN nội bộ nhằm giảm thiểu rủi ro khi DMZ bị xâm nhập, nhưng vẫn đảm bảo máy DMZ ra được Internet.

Tình huống 4 — NAT Port Forwarding: Mở cổng (publish) dịch vụ Web IIS từ mạng DMZ (172.16.0.2:80) ra mạng WAN (Port 8080), cho phép người dùng từ Internet truy cập trực tiếp vào trang web nội bộ thông qua IP Public của pfSense.

Tình huống 5 — System Logging: Bật tính năng ghi nhật ký trên các rule Block quan trọng, thực hiện giả lập tấn công (gửi gói tin trái phép) và phân tích file Firewall Log để truy vết chính xác Source IP, Destination IP và luật đã chặn.

4. Kỹ Năng Kỹ Thuật (Skills Acquired)
Kiến trúc mạng: Tư duy thiết kế và phân hoạch các phân vùng mạng (LAN, WAN, DMZ).

Vận hành Firewall: Hiểu rõ cơ chế duyệt rule ưu tiên từ trên xuống dưới (Top-Down processing) và trạng thái kết nối (Stateful packet inspection).

Bảo mật & Khắc phục sự cố: Kỹ năng xử lý lỗi mất kết nối, xung đột IP ảo hóa, cấu hình dịch vụ DNS/NAT, và khả năng đọc hiểu log hệ thống để audit an ninh mạng.