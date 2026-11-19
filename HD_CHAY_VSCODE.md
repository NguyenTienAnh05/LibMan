# Hướng dẫn chạy LibMan bằng VS Code

Dự án LibMan là ứng dụng Java Web đóng gói dạng WAR. Cấu hình hiện tại dùng JDK 25, Maven Wrapper, Tomcat 9 và MySQL.

## 1. Chuẩn bị môi trường

Cài đặt hoặc chuẩn bị sẵn:

- JDK 25.
- MySQL Server và cơ sở dữ liệu `libman` đã được tạo, nạp dữ liệu.
- Apache Tomcat 9.
- VS Code cùng các tiện ích **Extension Pack for Java** và **Community Server Connectors** (hoặc **Tomcat for Java**).

Các tác vụ VS Code hiện được cấu hình cho JDK tại `C:\Program Files\Microsoft\jdk-25.0.5.21-hotspot` và Tomcat tại `C:\Tools\apache-tomcat-9.0.124`. Nếu cài ở vị trí khác, cập nhật `.vscode/settings.json`, `.vscode/tasks.json` và `tomcat-debug.bat`.

## 2. Cấu hình kết nối MySQL

Mở `src/main/java/dao/DAO7.java` và chỉnh các hằng số `DB_URL`, `DB_USER`, `DB_PASSWORD` theo cấu hình MySQL trên máy. Ứng dụng kết nối đến cơ sở dữ liệu `libman` trên máy cục bộ theo mặc định.

## 3. Biên dịch và chạy ứng dụng

1. Mở thư mục gốc của dự án trong VS Code.
2. Nhấn **Ctrl + Shift + B** và chọn tác vụ **Build + Deploy + Start**.
3. Tác vụ sẽ đóng gói WAR bằng Maven Wrapper, sao chép WAR vào thư mục `webapps` của Tomcat rồi khởi động Tomcat.
4. Mở [http://localhost:8080/LibMan_final/](http://localhost:8080/LibMan_final/). Trang đăng nhập là `login.jsp`.

Để chỉ biên dịch và đóng gói, chạy tác vụ **1. Build WAR (mvnw)**. Có thể chạy lệnh tương đương trong Terminal:

```powershell
.\mvnw.cmd clean package -DskipTests
```

## 4. Gỡ lỗi trong VS Code

Mở **Run and Debug** bằng **Ctrl + Shift + D**, chọn cấu hình **Debug on Tomcat 9**, sau đó nhấn **F5**. Cấu hình này tự đóng gói, triển khai và khởi động Tomcat rồi kết nối trình gỡ lỗi vào cổng `8000`.

## 5. Dừng Tomcat

Mở **Terminal > Run Task...** và chọn **4. Stop Tomcat**. Nếu muốn khởi động Tomcat mà không chạy tác vụ gỡ lỗi, chọn tác vụ **3. Start Tomcat**.
