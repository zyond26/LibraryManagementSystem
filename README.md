# Hệ thống Quản lý Thư viện (Library Management System)

Đây là một dự án Java Desktop bằng Java Swing kết hợp cơ sở dữ liệu SQLite và JDBC, được xây dựng theo mô hình chuẩn Maven. Dự án phù hợp làm đồ án môn học Lập trình hướng đối tượng (OOP) cho sinh viên đại học với mã nguồn đơn giản, sạch sẽ, có cấu trúc rõ ràng và được chú thích chi tiết.

---

## Tính năng chính

1. **Quản lý Sách**: Thêm, sửa, xóa, xem danh sách sách trong thư viện.
2. **Quản lý Độc giả**: Thêm, sửa, xóa, xem danh sách độc giả.
3. **Quản lý Thể loại**: CRUD thể loại sách.
4. **Quản lý Mượn/Trả**: Cho độc giả mượn sách (có kiểm tra số lượng sách trong kho), thực hiện trả sách, cập nhật số lượng và trạng thái sách.
5. **Tìm kiếm & Lọc**: Tìm sách theo tên/tác giả, lọc sách theo thể loại.
6. **Tự động khởi tạo Database**: Tự động tạo cơ sở dữ liệu SQLite (`library.db`) và nạp dữ liệu mẫu khi ứng dụng chạy lần đầu tiên.

---

## Yêu cầu hệ thống

* **Hệ điều hành**: Windows, macOS hoặc Linux.
* **Java Development Kit (JDK)**: Phiên bản 21 trở lên.
* **Apache Maven**: Phiên bản 3.8 trở lên.

---

## Hướng dẫn cài đặt và chạy ứng dụng

### Bước 1: Sao chép dự án về máy
Mở thư mục chứa mã nguồn dự án trên máy của bạn.

### Bước 2: Biên dịch dự án bằng Maven
Chạy lệnh sau tại thư mục gốc của dự án (nơi chứa file `pom.xml`):
```bash
mvn clean compile
```

### Bước 3: Chạy chương trình
Sử dụng plugin Maven Exec để khởi chạy ứng dụng nhanh chóng:
```bash
mvn exec:java
```
Hoặc bạn có thể mở dự án trong các IDE như IntelliJ IDEA, Eclipse, NetBeans và chạy trực tiếp class `com.library.Main`.

> **Lưu ý:** Trong lần đầu tiên khởi chạy, chương trình sẽ tự động tạo một file cơ sở dữ liệu tên là `library.db` ở thư mục gốc của dự án và chèn dữ liệu mẫu vào đó. Bạn không cần bất kỳ cài đặt cơ sở dữ liệu thủ công nào.

---

## Cấu trúc thư mục dự án

```
Java_OOP/
├── pom.xml                               # File cấu hình Maven và các dependency (SQLite JDBC)
├── README.md                             # Hướng dẫn sử dụng dự án
├── library.db                            # Tệp cơ sở dữ liệu SQLite (sinh ra khi chạy app)
└── src/
    └── main/
        └── java/
            └── com/
                └── library/
                    ├── Main.java         # Lớp chạy chương trình chính
                    ├── model/            # Chứa các lớp thực thể (Entity Models)
                    │   ├── Category.java
                    │   ├── Book.java
                    │   ├── Reader.java
                    │   └── Borrow.java
                    ├── database/         # Kết nối & Khởi tạo CSDL SQLite
                    │   ├── DBConnection.java
                    │   └── DatabaseInitializer.java
                    ├── dao/              # Truy cập dữ liệu (Data Access Objects)
                    │   ├── CategoryDAO.java
                    │   ├── BookDAO.java
                    │   ├── ReaderDAO.java
                    │   └── BorrowDAO.java
                    ├── service/          # Logic nghiệp vụ (Service layer)
                    │   ├── CategoryService.java
                    │   ├── BookService.java
                    │   ├── ReaderService.java
                    │   └── BorrowService.java
                    └── ui/               # Giao diện người dùng Java Swing
                        ├── MainFrame.java
                        ├── CategoryPanel.java
                        ├── BookPanel.java
                        ├── ReaderPanel.java
                        ├── BorrowPanel.java
                        └── SearchPanel.java
```
