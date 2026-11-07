Lab 9: Remote DNS Cache Poisoning



Giới thiệu



Bài thực hành mô phỏng kỹ thuật tấn công độc bộ đệm máy chủ phân giải tên miền từ xa (Remote DNS Cache Poisoning / Kaminsky Attack). Kẻ tấn công bị cô lập khỏi mạng nội bộ, buộc phải sử dụng kỹ thuật đoán mã Transaction ID và truy vấn tên miền ngẫu nhiên để vượt qua cache.



Môi trường (Topology)



Hệ thống mạng được phân vùng bởi tường lửa pfSense:



DNS-LAB (10.9.0.0/24): DNS Server (10.9.0.10) và Victim (10.9.0.100).



ATTACK-NET (10.10.0.0/24): Attacker (10.10.0.200).



DMZ (172.16.6.0/24): Web Server (172.16.6.10) và Auth DNS (172.16.6.20).



Các bước thực hiện



Kiểm tra Baseline: Xác nhận Victim truy cập đúng Web Server hợp lệ.



Xác nhận cô lập: Attacker không thể nghe lén (sniff) lưu lượng mạng DNS-LAB.



Kích hoạt lỗ hổng: Khởi chạy mô phỏng DNS Server với cổng nguồn (Source Port) cố định.



Tấn công: Chạy script tấn công Kaminsky từ máy Attacker để đầu độc bộ đệm.



Kiểm chứng: Victim truy cập tên miền và bị chuyển hướng sang trang web giả mạo.



Phòng vệ (Hardening): Khôi phục BIND9 tiêu chuẩn, bật tính năng Source Port Randomization (SPR) và DNSSEC.

