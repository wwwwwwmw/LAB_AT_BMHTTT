LAB 6: XÁC THỰC ACTIVE DIRECTORY VÀ CHÍNH SÁCH TRUY CẬP TRÊN pfSense

Sinh viên thực hiện: Lê Trí Anh

MSSV: 1150080125

Môn học: An toàn và Bảo mật Hệ thống Thông tin (Thực hành)

Tổng quan dự án

Mục tiêu của bài Lab này là xây dựng một hệ thống mạng an toàn sử dụng tường lửa pfSense, tích hợp xác thực người dùng tập trung thông qua Active Directory (LDAP), triển khai Captive Portal để kiểm soát truy cập và áp dụng các chính sách Firewall (Rules, Aliases, Schedules) theo các kịch bản thực tế của doanh nghiệp.

Mô hình mạng (Topology)

Hệ thống được chia thành 4 phân vùng mạng chuyên biệt trên VirtualBox:

WAN (Adapter 1): 192.168.250.0/24 - Kết nối Internet (NAT Network).

MGMT (Adapter 2): 192.168.56.254/24 - Vùng quản trị riêng biệt (Host-Only).

LAN (Adapter 3): 10.6.0.1/24 - Vùng mạng nội bộ chứa Domain Controller và các Client (Internal Network lab6-lan).

DMZ (Adapter 4): 172.16.6.1/24 - Vùng chứa các dịch vụ Web (Internal Network lab6-dmz).

Danh sách máy ảo:

pfSense (Firewall/Router): Định tuyến và kiểm soát luồng dữ liệu.

Windows Server 2025 (DC01): Domain Controller (10.6.0.10), quản lý Domain letrianh.local, DNS và LDAP.

Windows 10/11 (Client): Máy trạm (10.6.0.21, 10.6.0.22) dùng để kiểm thử truy cập.

Ubuntu Server (DMZ-WEB): Giả lập nhiều website trên các IP alias (172.16.6.10, .20, .30, .40).

Các bước triển khai chính

1. Chuẩn bị môi trường & Dọn dẹp Firewall

Cấu hình IP tĩnh cho các Interface thông qua pfSense Console.

Chuyển quyền truy cập WebGUI (Anti-Lockout) sang cổng MGMT (Port 443).

Làm sạch hoàn toàn các rules mặc định trên tab LAN để chuẩn bị cho Captive Portal.

2. Tích hợp Active Directory (LDAP)

Kết nối pfSense với Windows Server AD để xác thực người dùng:

Server: 10.6.0.10 (Port 389)

Base DN: DC=letrianh,DC=local

Bind Credentials: Administrator@letrianh.local (Tài khoản được sử dụng để khắc phục lỗi kết nối data 52e).

Kiểm thử: Sử dụng công cụ Diagnostics > Authentication trên pfSense để test các user sv_a, sv_b.

3. Cấu hình Captive Portal

Tạo Captive Portal Zone trên giao diện LAN.

Thiết lập Backend Authentication trỏ về máy chủ LDAP vừa tạo.

Người dùng (Client) bắt buộc phải đăng nhập qua cổng web trước khi được cấp quyền truy cập mạng.

4. Thiết lập Aliases và Schedules

Aliases: Định nghĩa các nhóm IP máy trạm (CLIENT_A, POLICY_CLIENTS) và nhóm Web IP (JOB_SITE, SOCIAL_SITE, RESTRICTED_SITE, GENERAL_SITE).

Schedules: Định nghĩa các khung giờ LUNCH_WINDOW (11h30-13h) và WORK_HOURS (8h-17h).

Kịch bản Kiểm thử Firewall (Rules)

Các chính sách được áp dụng nguyên tắc First-match (khớp từ trên xuống dưới):

Kịch bản 1: Client-A bị chặn truy cập vào JOB_SITE nhưng được phép truy cập GENERAL_SITE.

Kịch bản 2: Cấm toàn bộ POLICY_CLIENTS truy cập vào RESTRICTED_SITE.

Kịch bản 3 (Time-based): Client-B chỉ được phép truy cập SOCIAL_SITE vào giờ nghỉ trưa (LUNCH_WINDOW). Cần kết hợp Rule Pass (có schedule) nằm trên Rule Block (luôn bật).

Kịch bản 4 (Port/Time-based): Cho phép POLICY_CLIENTS lướt web (HTTP/HTTPS) trong giờ hành chính (WORK_HOURS), và chặn toàn bộ các traffic khác (như Ping/ICMP ra ngoài Internet).

Kịch bản 5 (DMZ Isolation): Cô lập vùng DMZ, không cho phép các máy chủ trong DMZ chủ động khởi tạo kết nối ngược (Ping/truy cập) vào vùng LAN, nhưng LAN vẫn được truy cập DMZ bình thường.

Lưu ý: Mọi thay đổi trong Firewall Rules cần thực hiện Reset States để các cấu hình mới lập tức có hiệu lực, xóa bỏ các session cũ còn lưu trong bộ nhớ (Stateful Firewall).