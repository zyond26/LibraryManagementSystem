package com.library.model;

/**
 * Lớp biểu diễn thông tin phiếu mượn/trả sách.
 */
public class Borrow {
    private int id;
    private int readerId;
    private int bookId;
    private String borrowDate; // Định dạng YYYY-MM-DD
    private String returnDate; // Định dạng YYYY-MM-DD (hoặc null nếu chưa trả)
    private String status;     // "BORROWED" (Đang mượn) hoặc "RETURNED" (Đã trả)

    // Các thuộc tính bổ sung để hiển thị trên UI
    private String readerName;
    private String bookTitle;

    // Constructor không tham số
    public Borrow() {
    }

    // Constructor đầy đủ tham số
    public Borrow(int id, int readerId, int bookId, String borrowDate, String returnDate, String status) {
        this.id = id;
        this.readerId = readerId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // Constructor không có id (dùng khi thêm mới lượt mượn)
    public Borrow(int readerId, int bookId, String borrowDate, String returnDate, String status) {
        this.readerId = readerId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
