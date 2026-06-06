package com.library.dao;

import com.library.database.DBConnection;
import com.library.model.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp truy cập dữ liệu cho thực thể Reader (Độc giả).
 */
public class ReaderDAO {

    /**
     * Lấy danh sách toàn bộ độc giả.
     */
    public List<Reader> getAll() {
        List<Reader> list = new ArrayList<>();
        String sql = "SELECT * FROM reader ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reader r = new Reader(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy độc giả theo ID.
     */
    public Reader getById(int id) {
        String sql = "SELECT * FROM reader WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Reader(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("phone"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Thêm độc giả mới.
     */
    public boolean add(Reader reader) {
        String sql = "INSERT INTO reader (full_name, phone, email) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reader.getFullName());
            pstmt.setString(2, reader.getPhone());
            pstmt.setString(3, reader.getEmail());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật thông tin độc giả.
     */
    public boolean update(Reader reader) {
        String sql = "UPDATE reader SET full_name = ?, phone = ?, email = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, reader.getFullName());
            pstmt.setString(2, reader.getPhone());
            pstmt.setString(3, reader.getEmail());
            pstmt.setInt(4, reader.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa độc giả theo ID.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM reader WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
