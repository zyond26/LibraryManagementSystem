package com.library.model;

/**
 * Lớp biểu diễn thông tin một độc giả.
 */
public class Reader {
    private int id;
    private String fullName;
    private String phone;
    private String email;

    // Constructor không tham số
    public Reader() {
    }

    // Constructor đầy đủ tham số
    public Reader(int id, String fullName, String phone, String email) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }

    // Constructor không có id (dùng khi thêm mới)
    public Reader(String fullName, String phone, String email) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // toString để hiển thị tên độc giả trong JComboBox
    @Override
    public String toString() {
        return fullName + " (" + phone + ")";
    }
}
