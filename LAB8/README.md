BÁO CÁO THỰC HÀNH LAB 8: LOCAL DNS ATTACKDNS SPOOFING VÀ CACHE POISONING TRONG MẠNG CÔ LẬPThông tin sinh viên:Họ và tên: Lê Trí AnhMSSV: 1150080125Lớp học phần: 11CNPM2Môi trường: VMware Workstation, pfSense 2.7.x, Ubuntu Server 24.04, Kali Linux 2024.x1. Mục tiêu thực hànhThiết lập dịch vụ phân giải tên miền BIND9 trên Ubuntu Server làm máy chủ đệ quy và lưu bộ nhớ đệm (Recursive \& Caching Resolver).Cấu hình phân đoạn mạng và định tuyến trên Firewall pfSense giữa mạng DNS-LAB (10.9.0.0/24) và DMZ (172.16.6.0/24).Thực hiện và phân tích 3 kịch bản tấn công:Điều hướng tên miền thông qua file hosts cục bộ (HOSTS-file Redirection).Tấn công giả mạo phản hồi DNS trực tiếp tới máy nạn nhân (Direct DNS Spoofing).Tấn công đầu độc bộ nhớ đệm máy chủ DNS (DNS Cache Poisoning).Phân tích cơ chế bắt gói tin với Wireshark và đề xuất giải pháp phòng thủ (DNSSEC, DNS COOKIE, Egress Filtering).2. Thông số mạng và Phân bổ IPHệ thống gồm 5 máy ảo kết nối qua tường lửa pfSense trên các LAN Segment ảo:Máy ảoHệ điều hành / Vai tròĐịa chỉ IPGiao tiếp mạngpfSenseRouter / Firewall GatewayWAN: 192.168.226.131LAN: 10.9.0.1/24OPT1: 172.16.6.1/24 | NAT, LAN Segment (dns-lab), LAN Segment (dmz) |

| DNS-SERVER | Ubuntu Server / BIND9 Resolver | 10.9.0.10/24 (GW: 10.9.0.1) | LAN Segment: dns-lab |

| VICTIM | Ubuntu Client | 10.9.0.100/24 (GW: 10.9.0.1) | LAN Segment: dns-lab |

| ATTACKER | Kali Linux / Scapy, Wireshark | 10.9.0.200/24 (GW: 10.9.0.1) | LAN Segment: dns-lab (Promiscuous Mode) |

| DMZ-WEB | Ubuntu Web Server | 172.16.6.10/24 | LAN Segment: dmz |

| AUTH-DNS | Delayed DNS Simulator | 172.16.6.20/24 | LAN Segment: dmz |3. Các bước thực hiện và Kết quả3.1. Kiểm tra trạng thái ban đầu (Baseline)Trên máy DMZ (172.16.6.20), chạy dịch vụ DNS có độ trễ:sudo python3 auth\_dns\_delayed.py --bind 172.16.6.20 --delay 1.5

Trên máy DNS-SERVER (10.9.0.10), dọn sạch cache:sudo rndc flush

Trên máy VICTIM (10.9.0.100), kiểm tra phân giải:dig @10.9.0.10 www.external.lab9.test A +noall +answer

curl http://www.external.lab9.test

Kết quả: Tên miền trỏ về đúng IP máy chủ web DMZ (172.16.6.10) và tải về trang web chính thức.3.2. Task 1: Điều hướng tên miền bằng file /etc/hostsThêm bản ghi giả mạo trên máy Victim:sudo cp /etc/hosts /etc/hosts.bak

echo "10.9.0.200    www.lab9.test" | sudo tee -a /etc/hosts

Kiểm tra phân giải:curl http://www.lab9.test: Bị điều hướng sang web giả mạo (10.9.0.200) do hệ điều hành ưu tiên file hosts qua nsswitch.conf.dig @10.9.0.10 www.lab9.test: Vẫn trả về IP gốc do dig truy vấn trực tiếp DNS Server qua UDP port 53.Khôi phục lại file hosts:sudo cp /etc/hosts.bak /etc/hosts

sudo resolvectl flush-caches

3.3. Task 2: Tấn công giả mạo DNS trực tiếp (Direct DNS Spoofing)Trên máy Kali (10.9.0.200), chạy máy chủ web giả lập và script tấn công ở chế độ victim:# Terminal 1:

cd \~/LAB9/fake\_site \&\& sudo python3 -m http.server 80



\# Terminal 2:

sudo python3 dns\_spoof\_lab9.py --mode victim --iface eth0

Trên máy VICTIM, gửi truy vấn:dig @10.9.0.10 www.external.lab9.test A +noall +answer

curl http://www.external.lab9.test

Kết quả: Gói tin phản hồi giả từ Kali đến trước phản hồi từ DMZ (vốn bị trễ 1.5s). Máy Victim nhận IP 10.9.0.200 và lệnh curl hiển thị trang web giả mạo. Cuộc tấn công chỉ ảnh hưởng cục bộ trên máy Victim.3.4. Task 3: Đầu độc bộ nhớ đệm DNS (DNS Cache Poisoning)Trên máy DNS-SERVER, xóa cache BIND9:sudo rndc flush

Trên máy Kali, chạy script chế độ resolver:sudo python3 dns\_spoof\_lab9.py --mode resolver --iface eth0

Trên máy VICTIM, kích hoạt truy vấn đệ quy:dig @10.9.0.10 www.external.lab9.test A +noall +answer

Kiểm tra cache trên máy DNS-SERVER:dig @127.0.0.1 www.external.lab9.test A +short

Kết quả: BIND9 lưu bản ghi giả mạo vào bộ nhớ đệm (trả về 10.9.0.200). Lúc này, toàn bộ các máy trạm khác trong mạng trỏ về DNS-SERVER đều bị điều hướng sai cho đến khi cache hết hạn TTL (120 giây).4. Khôi phục hệ thốngSau khi hoàn thành bài lab, tiến hành hoàn trả môi trường sạch:Dừng các script Python và web server giả lập trên Kali (Ctrl + C).Dừng script mô phỏng trên máy DMZ (172.16.6.20).Xóa cache trên DNS-SERVER:sudo rndc flush

Xóa cache trên Victim:sudo resolvectl flush-caches

Chuyển thiết lập card mạng Kali trên máy ảo từ Promiscuous Mode về trạng thái Deny mặc định.5. Kết luận và Biện pháp phòng chốngPhân đoạn mạng Layer 2: Vô hiệu hóa Promiscuous Mode trên switch mạng, cấu hình Dynamic ARP Inspection để chặn việc nghe lén gói tin trong cùng mạng LAN.Triển khai DNSSEC: Ký số các bản ghi bằng khóa mật mã (RRSIG, DNSKEY) để máy chủ phân giải kiểm tra tính toàn vẹn và nguồn gốc dữ liệu.Kích hoạt DNS COOKIE (RFC 7873): Ngăn chặn việc giả mạo gói tin DNS từ bên ngoài hoặc trên đường truyền.DNS Egress Filtering: Cấu hình tường lửa pfSense chỉ cho phép máy chủ DNS-SERVER truy vấn cổng 53 ra ngoài, chặn toàn bộ truy vấn trực tiếp từ các máy client.

