package com.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Lớp quản lý kết nối cơ sở dữ liệu SQLite.
 */
public class DBConnection {
    private static final String DB_URL = "jdbc:sqlite:library.db";

    /**
     * Lấy kết nối tới cơ sở dữ liệu SQLite.
     * @return Connection đối tượng kết nối
     * @throws SQLException nếu kết nối thất bại
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Tải driver SQLite (tùy chọn nhưng tốt để đảm bảo driver được load)
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Không tìm thấy driver SQLite JDBC: " + e.getMessage());
        }
    }
}
