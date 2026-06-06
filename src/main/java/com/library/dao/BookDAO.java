package com.library.dao;

import com.library.database.DBConnection;
import com.library.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp truy cập dữ liệu cho thực thể Book (Sách).
 */
public class BookDAO {

    /**
     * Lấy danh sách sách và kết nối với bảng Category để lấy tên thể loại.
     */
    public List<Book> getAll() {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT b.*, c.name AS category_name FROM book b "
                + "JOIN category c ON b.category_id = c.id ORDER BY b.id DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Book book = mapRow(rs);
                list.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy sách theo ID.
     */
    public Book getById(int id) {
        String sql = "SELECT b.*, c.name AS category_name FROM book b "
                + "JOIN category c ON b.category_id = c.id WHERE b.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Thêm sách mới.
     */
    public boolean add(Book book) {
        String sql = "INSERT INTO book (title, author, publisher, price, quantity, category_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPublisher());
            pstmt.setDouble(4, book.getPrice());
            pstmt.setInt(5, book.getQuantity());
            pstmt.setInt(6, book.getCategoryId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật thông tin sách.
     */
    public boolean update(Book book) {
        String sql = "UPDATE book SET title = ?, author = ?, publisher = ?, price = ?, quantity = ?, category_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPublisher());
            pstmt.setDouble(4, book.getPrice());
            pstmt.setInt(5, book.getQuantity());
            pstmt.setInt(6, book.getCategoryId());
            pstmt.setInt(7, book.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa sách theo ID.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM book WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tìm kiếm sách theo tiêu đề hoặc tác giả.
     */
    public List<Book> search(String query) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT b.*, c.name AS category_name FROM book b "
                + "JOIN category c ON b.category_id = c.id "
                + "WHERE b.title LIKE ? OR b.author LIKE ? ORDER BY b.id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String likeQuery = "%" + query + "%";
            pstmt.setString(1, likeQuery);
            pstmt.setString(2, likeQuery);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lọc sách theo thể loại.
     */
    public List<Book> filterByCategory(int categoryId) {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT b.*, c.name AS category_name FROM book b "
                + "JOIN category c ON b.category_id = c.id "
                + "WHERE b.category_id = ? ORDER BY b.id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Helper map từ ResultSet sang Book model.
     */
    private Book mapRow(ResultSet rs) throws SQLException {
        Book book = new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("publisher"),
                rs.getDouble("price"),
                rs.getInt("quantity"),
                rs.getInt("category_id")
        );
        book.setCategoryName(rs.getString("category_name"));
        return book;
    }
}
