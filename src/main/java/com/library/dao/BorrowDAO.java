package com.library.dao;

import com.library.database.DBConnection;
import com.library.model.Borrow;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp truy cập dữ liệu cho thực thể Borrow (Mượn/Trả sách).
 */
public class BorrowDAO {

    /**
     * Lấy danh sách toàn bộ lượt mượn trả, kết nối với Reader và Book để hiển thị tên.
     */
    public List<Borrow> getAll() {
        List<Borrow> list = new ArrayList<>();
        String sql = "SELECT bw.*, r.full_name AS reader_name, b.title AS book_title "
                + "FROM borrow bw "
                + "JOIN reader r ON bw.reader_id = r.id "
                + "JOIN book b ON bw.book_id = b.id "
                + "ORDER BY bw.id DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy phiếu mượn theo ID.
     */
    public Borrow getById(int id) {
        String sql = "SELECT bw.*, r.full_name AS reader_name, b.title AS book_title "
                + "FROM borrow bw "
                + "JOIN reader r ON bw.reader_id = r.id "
                + "JOIN book b ON bw.book_id = b.id "
                + "WHERE bw.id = ?";
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
     * Thêm mới một phiếu mượn.
     */
    public boolean add(Borrow borrow) {
        String sql = "INSERT INTO borrow (reader_id, book_id, borrow_date, return_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, borrow.getReaderId());
            pstmt.setInt(2, borrow.getBookId());
            pstmt.setString(3, borrow.getBorrowDate());
            pstmt.setString(4, borrow.getReturnDate());
            pstmt.setString(5, borrow.getStatus());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật trạng thái trả sách.
     */
    public boolean returnBook(int id, String returnDate) {
        String sql = "UPDATE borrow SET return_date = ?, status = 'RETURNED' WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, returnDate);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Helper map từ ResultSet sang Borrow model.
     */
    private Borrow mapRow(ResultSet rs) throws SQLException {
        Borrow borrow = new Borrow(
                rs.getInt("id"),
                rs.getInt("reader_id"),
                rs.getInt("book_id"),
                rs.getString("borrow_date"),
                rs.getString("return_date"),
                rs.getString("status")
        );
        borrow.setReaderName(rs.getString("reader_name"));
        borrow.setBookTitle(rs.getString("book_title"));
        return borrow;
    }
}
