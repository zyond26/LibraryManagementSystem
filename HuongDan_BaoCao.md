# BÁO CÁO ĐỒ ÁN MÔN HỌC: LẬP TRÌNH HƯỚNG ĐỐI TƯỢNG (JAVA)
## ĐỀ TÀI: HỆ THỐNG QUẢN LÝ THƯ VIỆN SÁCH (LIBRARY MANAGEMENT SYSTEM)

*(Hướng dẫn: Bạn có thể copy toàn bộ nội dung file Markdown này và paste trực tiếp vào Microsoft Word để tạo file báo cáo chính thức).*

---

## 1. GIỚI THIỆU ĐỀ TÀI

Quản lý thư viện là một bài toán thực tế phổ biến tại tất cả các trường học, viện nghiên cứu, và thư viện công cộng. Việc quản lý thủ công bằng sổ sách thường gặp rất nhiều khó khăn như dễ thất lạc thông tin, tra cứu chậm chạp, và khó thống kê chính xác số lượng sách mượn trả hay số lượng sách còn lại trong kho.

Nhằm giải quyết những hạn chế đó, đề tài **"Hệ thống quản lý thư viện sách"** được xây dựng trên ngôn ngữ lập trình Java với các công nghệ lõi như Java Swing cho giao diện người dùng và cơ sở dữ liệu nhúng SQLite kết hợp thư viện JDBC. Đây là ứng dụng dạng Desktop đơn giản, gọn nhẹ nhưng vẫn đáp ứng đầy đủ quy trình nghiệp vụ cơ bản của một thư viện thực tế.

---

## 2. MỤC TIÊU ĐỀ TÀI

* **Về mặt lý thuyết**: Áp dụng thành thạo các kiến thức nền tảng của môn Lập trình hướng đối tượng (OOP) như:
  * Cách xây dựng Lớp (Class), Đối tượng (Object) và hàm tạo (Constructor).
  * Thực thi tính Đóng gói (Encapsulation) thông qua các thuộc tính private và các phương thức getter/setter.
  * Phân chia mô hình ứng dụng theo các tầng rõ rệt: Model (mô hình dữ liệu), DAO (tương tác CSDL), Service (xử lý nghiệp vụ logic), và UI (giao diện Swing).
* **Về mặt thực tiễn**: Xây dựng thành công một phần mềm Desktop có giao diện thân thiện, dễ sử dụng, có tính thực tế cao, giúp nhân viên thư viện thực hiện nhanh chóng các tác vụ hàng ngày.

---

## 3. CẤU TRÚC CHƯƠNG TRÌNH

Dự án được quản lý bằng công cụ Maven để tự động tải các thư viện cần thiết (SQLite JDBC driver). Mã nguồn được chia thành các package chuyên biệt để dễ bảo trì và phát triển:

1. **`com.library.model`**: Chứa các lớp biểu diễn thực thể như `Book`, `Reader`, `Category`, `Borrow`.
2. **`com.library.database`**: Quản lý kết nối (`DBConnection`) và tự động khởi tạo cơ sở dữ liệu mẫu (`DatabaseInitializer`).
3. **`com.library.dao`**: Chứa các lớp Data Access Object (`BookDAO`, `ReaderDAO`, `CategoryDAO`, `BorrowDAO`) chịu trách nhiệm thực thi các truy vấn SQL trực tiếp lên cơ sở dữ liệu SQLite.
4. **`com.library.service`**: Chứa các lớp xử lý logic trung gian (`BookService`, `ReaderService`, `CategoryService`, `BorrowService`), kiểm tra tính hợp lệ của dữ liệu trước khi chuyển giao giữa UI và DAO.
5. **`com.library.ui`**: Chứa giao diện Swing (`MainFrame` và các panel con `BookPanel`, `ReaderPanel`, `CategoryPanel`, `BorrowPanel`, `SearchPanel`).
6. **`com.library.Main`**: Lớp chứa hàm `main` khởi chạy toàn bộ ứng dụng.

---

## 4. THIẾT KẾ CƠ SỞ DỮ LIỆU (DATABASE DESIGN)

Hệ thống sử dụng cơ sở dữ liệu SQLite nhẹ, nhúng trực tiếp vào dự án dưới dạng tệp tin `library.db`. Dưới đây là lược đồ thiết kế các bảng:

### 4.1. Bảng Thể loại (`category`)
* `id`: `INTEGER PRIMARY KEY AUTOINCREMENT` - Mã thể loại (tự động tăng).
* `name`: `TEXT NOT NULL UNIQUE` - Tên thể loại sách.

### 4.2. Bảng Sách (`book`)
* `id`: `INTEGER PRIMARY KEY AUTOINCREMENT` - Mã sách.
* `title`: `TEXT NOT NULL` - Tên cuốn sách.
* `author`: `TEXT NOT NULL` - Tác giả cuốn sách.
* `publisher`: `TEXT` - Nhà xuất bản.
* `price`: `REAL` - Đơn giá của sách.
* `quantity`: `INTEGER` - Số lượng sách hiện còn lại trong kho.
* `category_id`: `INTEGER` - Mã thể loại (Khóa ngoại tham chiếu đến bảng `category`).

### 4.3. Bảng Độc giả (`reader`)
* `id`: `INTEGER PRIMARY KEY AUTOINCREMENT` - Mã độc giả.
* `full_name`: `TEXT NOT NULL` - Họ và tên độc giả.
* `phone`: `TEXT` - Số điện thoại liên lạc.
* `email`: `TEXT` - Địa chỉ email.

### 4.4. Bảng Mượn trả (`borrow`)
* `id`: `INTEGER PRIMARY KEY AUTOINCREMENT` - Mã lượt mượn.
* `reader_id`: `INTEGER` - Mã độc giả mượn sách (Khóa ngoại tham chiếu đến bảng `reader`).
* `book_id`: `INTEGER` - Mã sách được mượn (Khóa ngoại tham chiếu đến bảng `book`).
* `borrow_date`: `TEXT NOT NULL` - Ngày mượn sách (Định dạng YYYY-MM-DD).
* `return_date`: `TEXT` - Ngày trả sách (Định dạng YYYY-MM-DD, giá trị `NULL` nếu chưa trả).
* `status`: `TEXT NOT NULL` - Trạng thái mượn sách (`BORROWED` - Đang mượn, `RETURNED` - Đã trả).

---

## 5. CÁC CHỨC NĂNG CHÍNH CỦA CHƯƠNG TRÌNH

### 5.1. Quản lý Thể loại sách
* Xem danh sách các thể loại sách hiện có trên bảng.
* Thêm mới thể loại.
* Chỉnh sửa tên thể loại khi chọn một dòng trên bảng.
* Xóa thể loại (Không cho phép xóa nếu có sách thuộc thể loại này để đảm bảo tính toàn vẹn dữ liệu).

### 5.2. Quản lý Sách
* Hiển thị danh sách toàn bộ sách cùng thông tin thể loại.
* Thêm cuốn sách mới (Chọn thể loại thông qua ComboBox).
* Cập nhật thông tin chi tiết của sách (tên, tác giả, nhà xuất bản, giá bán, số lượng).
* Xóa sách khỏi hệ thống với hộp thoại xác nhận.

### 5.3. Quản lý Độc giả
* Quản lý danh sách độc giả của thư viện.
* Thêm độc giả mới, cập nhật số điện thoại và email.
* Xóa độc giả có xác nhận của quản trị viên.

### 5.4. Nghiệp vụ Mượn - Trả sách
* **Mượn sách**: Cho phép chọn Độc giả từ danh sách và chọn Sách muốn mượn.
  * *Quy tắc nghiệp vụ*: Hệ thống kiểm tra số lượng sách còn lại trong kho (`quantity`). Nếu số lượng > 0, hệ thống chấp nhận cho mượn, ghi nhận ngày mượn, đặt trạng thái là `BORROWED`, và tự động giảm số lượng sách trong kho đi 1. Nếu số lượng = 0, ứng dụng báo lỗi và từ chối mượn.
* **Trả sách**: Chọn một lượt mượn đang có trạng thái "Đang mượn" trên bảng và nhấn nút "Trả sách". Hệ thống cập nhật trạng thái phiếu mượn thành `RETURNED`, ghi nhận ngày trả là ngày hiện tại, đồng thời cộng trả lại 1 đơn vị vào số lượng cuốn sách đó trong kho.

### 5.5. Tìm kiếm & Lọc sách
* Tìm kiếm sách nhanh chóng bằng cách nhập từ khóa (hệ thống sẽ so khớp theo tên sách hoặc tên tác giả).
* Lọc danh sách sách theo từng thể loại cụ thể từ trình thả xuống (ComboBox).

---

## 6. HƯỚNG DẪN CHẠY CHƯƠNG TRÌNH

### 6.1. Chuẩn bị môi trường
* Đảm bảo máy tính đã cài đặt **JDK 21** và **Maven**.
* Tải về và giải nén thư mục dự án `Java_OOP`.

### 6.2. Các lệnh thực thi
Mở terminal hoặc PowerShell tại thư mục gốc của dự án và chạy các lệnh:
1. **Biên dịch mã nguồn**:
   ```bash
   mvn clean compile
   ```
2. **Khởi chạy ứng dụng**:
   ```bash
   mvn exec:java
   ```

### 6.3. Hoạt động của ứng dụng trong lần đầu chạy
* Hệ thống sẽ tự động phát hiện nếu file cơ sở dữ liệu `library.db` chưa tồn tại để tự tạo lập.
* Đồng thời các bảng sẽ được khởi tạo qua các câu lệnh `CREATE TABLE IF NOT EXISTS`.
* Một bộ dữ liệu mẫu (các cuốn sách, thể loại, độc giả, lịch sử mượn mẫu) được nạp trực tiếp để người dùng kiểm thử giao diện ngay lập tức mà không gặp bất kỳ lỗi kết nối nào.

---

## 7. KẾT LUẬN

Đồ án **"Hệ thống quản lý thư viện sách"** đã hoàn thành đầy đủ tất cả các mục tiêu đề ra:
* Đáp ứng đầy đủ các yêu cầu nghiệp vụ quản lý sách, độc giả, thể loại và vòng đời mượn trả sách.
* Cấu trúc mã nguồn chuẩn hướng đối tượng Java OOP, sạch sẽ, tách bạch vai trò giữa các lớp.
* Giao diện Swing được thiết kế theo phong cách hiện đại với các gam màu sắc phối hợp nhã nhặn, mang lại trải nghiệm sử dụng tốt.
* Hệ thống cơ sở dữ liệu thông minh tự khởi tạo giúp tối giản hóa quá trình triển khai ứng dụng trên máy của giáo viên hoặc hội đồng chấm điểm đồ án.
