package com.library.model;

/**
 * Lớp biểu diễn Thể loại sách.
 */
public class Category {
    private int id;
    private String name;

    // Constructor không tham số
    public Category() {
    }

    // Constructor đầy đủ tham số
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor tiện lợi khi thêm mới (chưa có ID)
    public Category(String name) {
        this.name = name;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Ghi đè phương thức toString để hiển thị tên thể loại trên ComboBox dễ dàng hơn
    @Override
    public String toString() {
        return name;
    }
}
