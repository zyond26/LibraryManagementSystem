package com.library.model;

/**
 * Lớp biểu diễn thông tin một cuốn sách.
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private double price;
    private int quantity;
    private int categoryId;
    
    // Thuộc tính phụ trợ dùng để hiển thị tên thể loại trên bảng
    private String categoryName;

    // Constructor không tham số
    public Book() {
    }

    // Constructor đầy đủ tham số
    public Book(int id, String title, String author, String publisher, double price, int quantity, int categoryId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }

    // Constructor không có id (dùng khi thêm mới)
    public Book(String title, String author, String publisher, double price, int quantity, int categoryId) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return title + " - " + author;
    }
}
