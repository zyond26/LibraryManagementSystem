package com.library.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Lớp khởi tạo cơ sở dữ liệu và dữ liệu mẫu nếu chưa tồn tại.
 */
public class DatabaseInitializer {

    /**
     * Khởi tạo cơ sở dữ liệu: Tạo bảng và chèn dữ liệu mẫu.
     */
    public static void initialize() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // 1. Kích hoạt Foreign Key hỗ trợ trong SQLite
            stmt.execute("PRAGMA foreign_keys = ON;");

            // 2. Tạo bảng category
            String createCategoryTable = "CREATE TABLE IF NOT EXISTS category ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL UNIQUE"
                    + ");";
            stmt.execute(createCategoryTable);

            // 3. Tạo bảng book
            String createBookTable = "CREATE TABLE IF NOT EXISTS book ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "title TEXT NOT NULL,"
                    + "author TEXT NOT NULL,"
                    + "publisher TEXT,"
                    + "price REAL,"
                    + "quantity INTEGER,"
                    + "category_id INTEGER,"
                    + "FOREIGN KEY(category_id) REFERENCES category(id)"
                    + ");";
            stmt.execute(createBookTable);

            // 4. Tạo bảng reader
            String createReaderTable = "CREATE TABLE IF NOT EXISTS reader ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "full_name TEXT NOT NULL,"
                    + "phone TEXT,"
                    + "email TEXT"
                    + ");";
            stmt.execute(createReaderTable);

            // 5. Tạo bảng borrow
            String createBorrowTable = "CREATE TABLE IF NOT EXISTS borrow ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "reader_id INTEGER,"
                    + "book_id INTEGER,"
                    + "borrow_date TEXT NOT NULL,"
                    + "return_date TEXT,"
                    + "status TEXT NOT NULL,"
                    + "FOREIGN KEY(reader_id) REFERENCES reader(id),"
                    + "FOREIGN KEY(book_id) REFERENCES book(id)"
                    + ");";
            stmt.execute(createBorrowTable);

            // 6. Chèn dữ liệu mẫu nếu các bảng đang rỗng
            insertMockData(conn);

            System.out.println("Khởi tạo cơ sở dữ liệu thành công!");

        } catch (SQLException e) {
            System.err.println("Lỗi khi khởi tạo cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertMockData(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Kiểm tra bảng category có dữ liệu chưa
            ResultSet rsCategory = stmt.executeQuery("SELECT COUNT(*) FROM category");
            if (rsCategory.next() && rsCategory.getInt(1) == 0) {
                stmt.execute("INSERT INTO category (name) VALUES ('Công nghệ thông tin')");
                stmt.execute("INSERT INTO category (name) VALUES ('Văn học nước ngoài')");
                stmt.execute("INSERT INTO category (name) VALUES ('Kỹ năng sống')");
                stmt.execute("INSERT INTO category (name) VALUES ('Khoa học viễn tưởng')");
                System.out.println("Đã thêm thể loại mẫu.");
            }

            // Kiểm tra bảng book
            ResultSet rsBook = stmt.executeQuery("SELECT COUNT(*) FROM book");
            if (rsBook.next() && rsBook.getInt(1) == 0) {
                // Giả định category_id tương ứng là 1, 2, 3, 4
                stmt.execute("INSERT INTO book (title, author, publisher, price, quantity, category_id) "
                        + "VALUES ('Lập trình hướng đối tượng Java', 'Nguyễn Văn A', 'NXB Giáo Dục', 85000.0, 10, 1)");
                stmt.execute("INSERT INTO book (title, author, publisher, price, quantity, category_id) "
                        + "VALUES ('Cấu trúc dữ liệu và giải thuật', 'Trần Văn B', 'NXB Khoa Học', 95000.0, 5, 1)");
                stmt.execute("INSERT INTO book (title, author, publisher, price, quantity, category_id) "
                        + "VALUES ('Đắc Nhân Tâm', 'Dale Carnegie', 'NXB Trẻ', 76000.0, 15, 3)");
                stmt.execute("INSERT INTO book (title, author, publisher, price, quantity, category_id) "
                        + "VALUES ('Nhà giả kim', 'Paulo Coelho', 'NXB Hội Nhà Văn', 69000.0, 8, 2)");
                stmt.execute("INSERT INTO book (title, author, publisher, price, quantity, category_id) "
                        + "VALUES ('Hai vạn dặm dưới đáy biển', 'Jules Verne', 'NXB Kim Đồng', 120000.0, 3, 4)");
                System.out.println("Đã thêm sách mẫu.");
            }

            // Kiểm tra bảng reader
            ResultSet rsReader = stmt.executeQuery("SELECT COUNT(*) FROM reader");
            if (rsReader.next() && rsReader.getInt(1) == 0) {
                stmt.execute("INSERT INTO reader (full_name, phone, email) "
                        + "VALUES ('Nguyễn Hoàng Nam', '0912345678', 'nam.nh@gmail.com')");
                stmt.execute("INSERT INTO reader (full_name, phone, email) "
                        + "VALUES ('Trần Thị Mai', '0987654321', 'mai.tt@gmail.com')");
                stmt.execute("INSERT INTO reader (full_name, phone, email) "
                        + "VALUES ('Lê Minh Quân', '0905111222', 'quan.lm@gmail.com')");
                System.out.println("Đã thêm độc giả mẫu.");
            }

            // Kiểm tra bảng borrow
            ResultSet rsBorrow = stmt.executeQuery("SELECT COUNT(*) FROM borrow");
            if (rsBorrow.next() && rsBorrow.getInt(1) == 0) {
                stmt.execute("INSERT INTO borrow (reader_id, book_id, borrow_date, return_date, status) "
                        + "VALUES (1, 1, '2026-06-01', NULL, 'BORROWED')");
                stmt.execute("INSERT INTO borrow (reader_id, book_id, borrow_date, return_date, status) "
                        + "VALUES (2, 3, '2026-05-20', '2026-05-28', 'RETURNED')");
                System.out.println("Đã thêm lượt mượn trả mẫu.");
            }
        }
    }
}
