HƯỚNG DẪN CÀI ĐẶT VÀ SỬ DỤNG CHƯƠNG TRÌNH
ĐỀ TÀI: HỆ THỐNG QUẢN LÝ THƯ VIỆN SÁCH
Sinh viên thực hiện
	1.
	2.
	3.
	4.
	5.
1. GIỚI THIỆU CHƯƠNG TRÌNH
Chương trình Hệ thống Quản lý Thư viện Sách được xây dựng bằng ngôn ngữ Java nhằm hỗ trợ quản lý các hoạt động cơ bản của thư viện như quản lý thể loại sách, quản lý sách, quản lý độc giả và thực hiện các giao dịch mượn – trả sách.
Phần mềm được phát triển dưới dạng ứng dụng Desktop sử dụng Java Swing để xây dựng giao diện người dùng và SQLite để lưu trữ dữ liệu. Nhờ sử dụng SQLite, chương trình có thể hoạt động độc lập mà không cần cài đặt các hệ quản trị cơ sở dữ liệu phức tạp như MySQL hay SQL Server.
Đề tài được thực hiện nhằm vận dụng các kiến thức của môn Lập trình Hướng đối tượng, đồng thời giúp sinh viên làm quen với quy trình xây dựng một ứng dụng quản lý hoàn chỉnh từ khâu thiết kế dữ liệu, xử lý nghiệp vụ đến xây dựng giao diện người dùng.

2. CÔNG NGHỆ SỬ DỤNG
Công nghệ	Mục đích sử dụng
Java 21	Ngôn ngữ lập trình
Java Swing	Xây dựng giao diện Desktop
SQLite	Lưu trữ dữ liệu
JDBC	Kết nối java với SQLite
Maven	Quản lý thư viện và build project
VsCode	Môi trường phát triển

3. CHỨC NĂNG CHÍNH CỦA HỆ THỐNG
3.1. Quản lý thể loại sách
Cho phép người dùng:
•	Thêm thể loại mới. 
•	Cập nhật tên thể loại. 
•	Xóa thể loại. 
•	Hiển thị danh sách thể loại. 
Hệ thống không cho phép xóa thể loại nếu vẫn còn sách thuộc thể loại đó nhằm đảm bảo tính toàn vẹn dữ liệu.
3.2. Quản lý sách
Cho phép:
•	Thêm sách mới. 
•	Cập nhật thông tin sách. 
•	Xóa sách. 
•	Hiển thị danh sách sách. 
Thông tin sách bao gồm:
•	Tên sách. 
•	Tác giả. 
•	Nhà xuất bản. 
•	Giá sách. 
•	Số lượng tồn kho. 
•	Thể loại sách. 

3.3. Quản lý độc giả
Cho phép:
•	Thêm độc giả. 
•	Chỉnh sửa thông tin độc giả. 
•	Xóa độc giả. 
•	Hiển thị danh sách độc giả. 
Thông tin độc giả bao gồm:
•	Họ và tên. 
•	Số điện thoại. 
•	Email. 
3.4. Mượn sách
Người dùng chọn độc giả và cuốn sách cần mượn.
Hệ thống kiểm tra số lượng tồn kho:
•	Nếu số lượng lớn hơn 0 thì cho phép mượn. 
•	Nếu số lượng bằng 0 thì từ chối giao dịch. 
Khi mượn thành công:
•	Tạo phiếu mượn. 
•	Ghi nhận ngày mượn. 
•	Cập nhật trạng thái BORROWED. 
•	Giảm số lượng sách trong kho. 
3.5. Trả sách
Người dùng chọn phiếu mượn đang ở trạng thái BORROWED.
Khi trả sách:
•	Cập nhật trạng thái RETURNED. 
•	Ghi nhận ngày trả. 
•	Tăng lại số lượng sách trong kho. 
3.6. Tìm kiếm sách
Hỗ trợ tìm kiếm theo:
•	Tên sách. 
•	Tác giả. 
Người dùng có thể nhập từ khóa để tra cứu nhanh dữ liệu.

4. CẤU TRÚC MÃ NGUỒN
Dự án được tổ chức theo mô hình phân lớp nhằm giúp mã nguồn dễ đọc, dễ bảo trì và dễ mở rộng.
Package model
Chứa các lớp dữ liệu:
•	Category 
•	Book 
•	Reader 
•	Borrow 
Các lớp này dùng để biểu diễn dữ liệu của hệ thống.

Package dao
Chứa các lớp thao tác với cơ sở dữ liệu:
•	CategoryDAO 
•	BookDAO 
•	ReaderDAO 
•	BorrowDAO 
Nhiệm vụ chính là thực hiện các câu lệnh SQL như:
•	SELECT 
•	INSERT 
•	UPDATE 
•	DELETE 

Package service
Chứa các lớp xử lý nghiệp vụ:
•	CategoryService 
•	BookService 
•	ReaderService 
•	BorrowService 
Đây là tầng trung gian giữa giao diện và cơ sở dữ liệu.

Package database
Bao gồm:
•	DBConnection 
•	DatabaseInitializer 
Có nhiệm vụ:
•	Tạo kết nối SQLite. 
•	Tạo bảng dữ liệu. 
•	Khởi tạo dữ liệu mẫu. 

Package ui
Chứa giao diện chương trình:
•	MainFrame 
•	CategoryPanel 
•	BookPanel 
•	ReaderPanel 
•	BorrowPanel 
•	SearchPanel 

Main.java
Là điểm bắt đầu của chương trình.
Khi chạy Main.java, toàn bộ hệ thống sẽ được khởi tạo.

5. HƯỚNG DẪN CHẠY CHƯƠNG TRÌNH
Cách 1: Chạy bằng Vscode hoặc bất kì IDE nào
Bước 1: Mở IntelliJ IDEA.
Bước 2: Chọn Open Project.
Bước 3: Mở thư mục source code.
Bước 4: Đợi Maven tải thư viện.
Bước 5: Mở lớp Main.java.
Bước 6: Nhấn Run.
Cách 2: Chạy bằng Terminal
Mở Terminal tại thư mục chứa file pom.xml.

Biên dịch chương trình: ‘bash’
mvn clean compile
Khởi chạy chương trình: ‘bash’
mvn exec:java


6. CƠ CHẾ KHỞI TẠO DỮ LIỆU
Khi chương trình được chạy lần đầu:
•	Tự động tạo file library.db. 
•	Tự động tạo các bảng dữ liệu. 
•	Tự động sinh dữ liệu mẫu. 
Người dùng không cần thao tác SQL thủ công.
7. MỘT SỐ LƯU Ý KHI CHẤM BÀI
1.	Không được xóa file pom.xml. 
2.	Máy tính cần cài đặt Java 21 trở lên. 
3.	Maven cần kết nối Internet ở lần chạy đầu tiên để tải thư viện. 
4.	Không cần cài đặt MySQL hoặc SQL Server. 
5.	Dữ liệu được lưu trong file: 
library.db
6.	Nếu muốn tạo dữ liệu mới: 
Xóa file:
library.db
và chạy lại chương trình.
7.	Nếu Maven báo lỗi thư viện:  mvn clean install
hoặc Reload Maven Project trong IntelliJ IDEA.
8.	Chương trình đã được kiểm thử trên: 
•	Windows 11 
•	JDK 21 
•	Maven 3.9.16


8. KẾT LUẬN
Chương trình Hệ thống Quản lý Thư viện Sách đã hoàn thành các chức năng cơ bản của một hệ thống quản lý thư viện gồm quản lý thể loại, quản lý sách, quản lý độc giả, mượn sách, trả sách và tìm kiếm dữ liệu.
Thông qua quá trình thực hiện đề tài, sinh viên đã vận dụng được các kiến thức về lập trình hướng đối tượng, Java Swing, JDBC, SQLite và Maven để xây dựng một ứng dụng quản lý hoàn chỉnh. Chương trình có thể triển khai trên các máy tính khác nhau với yêu cầu cài đặt đơn giản và không cần sử dụng hệ quản trị cơ sở dữ liệu bên ngoài.

*Nếu chưa có java và maven thì xem hướng dẫn sau : 
1. HƯỚNG DẪN CÀI ĐẶT JAVA
Bước 1: Tải Java
Tải JDK 21 tại:
•	Oracle JDK: https://www.oracle.com/java/technologies/downloads/ 
 
Khuyến nghị sử dụng JDK 21.
Bước 2: Cài đặt
Chạy file cài đặt và thực hiện theo các bước mặc định.
Ví dụ đường dẫn:
C:\Program Files\Java\jdk-21

Bước 3: Kiểm tra Java
Mở Command Prompt:    java --version
Nếu xuất hiện:
 thì Java đã được cài đặt thành công.
Kiểm tra trình biên dịch:
javac -version
Kết quả:   javac 21
2. HƯỚNG DẪN CÀI ĐẶT MAVEN
Bước 1: Tải Maven
Truy cập:
https://maven.apache.org/download.cgi
 
Tải bản Binary Zip Archive.
Bước 2: Giải nén
Ví dụ:
C:\Apache\Maven\apache-maven-3.9.16

Bước 3: Cấu hình biến môi trường
Tạo biến:
MAVEN_HOME
Giá trị:
C:\Apache\Maven\apache-maven-3.9.11
Thêm vào Path:
%MAVEN_HOME%\bin


Bước 4: Kiểm tra Maven
Mở Command Prompt:     mvn --version
Nếu xuất hiện:
 thì Maven đã được cài đặt thành công.

