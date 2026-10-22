Lab 07: Xây dựng chính sách Web Protection trên mô hình pfSense + Web Proxy
1. Mục tiêu bài lab
Phân vùng mạng (LAN, DMZ, WAN) trên pfSense.

Ép lưu lượng web nội bộ qua Forward Proxy.

Lọc nội dung bằng E2Guardian và Squid.

Quét virus bằng ClamAV.

Thực thi 10 chính sách (chặn, cảnh báo, whitelist, lịch biểu, giới hạn tải).

Mở rộng: Cấu hình HTTPS Inspection (SSL MITM).

2. Mô hình và Môi trường
pfSense: WAN (DHCP), LAN (192.168.10.1/24), DMZ (192.168.20.1/24)

WEB-PROXY (DMZ): 192.168.20.10/24 (Chạy E2Guardian, Squid, ClamAV)

DMZ-WEB (DMZ): 192.168.20.20/24 (Web Server Nginx thử nghiệm)

Windows DC (LAN): 192.168.10.10/24 (AD, DNS)

Windows Client (LAN): DHCP 192.168.10.0/24

3. Cài đặt và Cấu hình
3.1 Cấu hình WEB-PROXY (192.168.20.10)
Cài đặt:
sudo apt update
sudo apt install -y squid e2guardian clamav-daemon clamav-freshclam

Cấu hình ClamAV:
sudo systemctl stop clamav-freshclam
sudo freshclam
sudo systemctl start clamav-freshclam
sudo systemctl enable clamav-daemon
sudo systemctl start clamav-daemon

Cấu hình E2Guardian (/etc/e2guardian/e2guardian.conf):

filterports = 8080

proxyip = 127.0.0.1

proxyport = 3128

Bỏ chú thích: plugin contentscanner = '/etc/e2guardian/contentscanners/clamdscan.conf'

Cấu hình Clamdscan (/etc/e2guardian/contentscanners/clamdscan.conf):

clamdudsfile = '/var/run/clamav/clamd.ctl'

Cấu hình Squid (/etc/squid/squid.conf):

http_port 127.0.0.1:3128

acl localhost_src src 127.0.0.1

http_access allow localhost_src

http_access deny all

Khởi động lại:
sudo systemctl restart clamav-daemon squid e2guardian

3.2 Cấu hình DMZ-WEB (192.168.20.20)
Tạo Virtual Host Nginx cho: career.local, download.local, vietnam-portal.local, foreign-portal.local.

Tạo file 100MB:
sudo dd if=/dev/urandom of=/var/www/download/test100M.bin bs=1M count=100

Tạo file EICAR:
echo 'X5O!P%@AP[4\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*' | sudo tee /var/www/download/eicar.com

3.3 Cấu hình Tường lửa pfSense
LAN Rules:

Pass DNS (UDP/53) đến 192.168.10.10.

Pass TCP/8080 đến 192.168.20.10.

Block TCP/80, 443 ra mọi nơi (ngăn vượt proxy).
DMZ Rules:

Pass 192.168.20.10 ra Internet (Port 80, 443).

Block máy chủ DMZ khác truy cập LAN.

4. Kiểm thử 10 Chính sách
Chặn Việc làm: Chặn career.local qua bannedsitelist.

Cảnh báo: Đặt chế độ warn cho trang mạng xã hội.

Whitelist: Cho phép trang nghiệp vụ qua exceptionsitelist.

Chặn địa lý: Chặn dải IP bằng pfBlockerNG.

Lịch biểu: Dùng ACL thời gian chặn trang giải trí trong giờ hành chính.

Quét Virus: Tải eicar.com bị ClamAV chặn.

Giới hạn dung lượng: Đặt maxuploadsize 100MB, thử tải test100M.bin bị lỗi.

Chặn đuôi file: Chặn .exe qua bannedextensionlist.

Lọc từ khóa: Chặn theo bannedphraselist.

Chặn giao thức: Cấm truy cập cổng 8080 bằng telnet qua cấu hình Squid.

5. Mở rộng: HTTPS Inspection (SSL MITM)
Tạo chứng chỉ gốc (Root CA):
sudo openssl req -new -newkey rsa:2048 -days 3650 -nodes -x509 -keyout /etc/squid/bump_ca.key -out /etc/squid/bump_ca.crt -subj "/C=VN/ST=Hanoi/L=Hanoi/O=LabOrganization/CN=Lab Root CA"
sudo chown proxy:proxy /etc/squid/bump_ca.*
sudo chmod 400 /etc/squid/bump_ca.key

Tạo CSDL chứng chỉ (Squid):
sudo /usr/lib/squid/security_file_certgen -c -s /var/spool/squid/ssl_db -M 4MB
sudo chown -R proxy:proxy /var/spool/squid/ssl_db

Cấu hình Squid (/etc/squid/squid.conf):
http_port 3128 ssl-bump cert=/etc/squid/bump_ca.crt key=/etc/squid/bump_ca.key generate-host-certificates=on dynamic_cert_mem_cache_size=4MB
acl step1 at_step SslBump1
ssl_bump peek step1
ssl_bump bump all

Cài đặt CA: Nhập file bump_ca.crt vào Trusted Root Certification Authorities trên Windows (bằng tay hoặc qua GPO).
Kiểm thử: Truy cập trang HTTPS xem Issuer chứng chỉ có phải là "Lab Root CA" không, và thử tải EICAR qua HTTPS.