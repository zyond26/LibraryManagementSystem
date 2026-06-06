package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.model.Book;
import com.library.model.Borrow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Lớp nghiệp vụ xử lý Mượn/Trả sách.
 * Đảm nhận việc kiểm tra số lượng sách, cập nhật kho sách khi mượn và trả.
 */
public class BorrowService {
    private final BorrowDAO borrowDAO;
    private final BookDAO bookDAO;

    public BorrowService() {
        this.borrowDAO = new BorrowDAO();
        this.bookDAO = new BookDAO();
    }

    public List<Borrow> getAllBorrows() {
        return borrowDAO.getAll();
    }

    /**
     * Nghiệp vụ mượn sách.
     * Kiểm tra số lượng sách còn lại trong thư viện, nếu có thì cho mượn và giảm số lượng đi 1.
     */
    public synchronized String borrowBook(int readerId, int bookId) {
        Book book = bookDAO.getById(bookId);
        if (book == null) {
            return "Không tìm thấy cuốn sách này!";
        }

        if (book.getQuantity() <= 0) {
            return "Sách này hiện đã hết trong thư viện!";
        }

        // Tạo ngày mượn hiện tại (Định dạng YYYY-MM-DD)
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Borrow borrow = new Borrow(readerId, bookId, currentDate, null, "BORROWED");

        // Lưu phiếu mượn vào CSDL
        boolean isSuccess = borrowDAO.add(borrow);
        if (isSuccess) {
            // Giảm số lượng sách đi 1
            book.setQuantity(book.getQuantity() - 1);
            bookDAO.update(book);
            return "MƯỢN_THÀNH_CÔNG";
        }

        return "Lỗi hệ thống, không thể tạo phiếu mượn!";
    }

    /**
     * Nghiệp vụ trả sách.
     * Cập nhật trạng thái phiếu mượn thành "RETURNED", thêm ngày trả và tăng số lượng sách lên 1.
     */
    public synchronized boolean returnBook(int borrowId) {
        Borrow borrow = borrowDAO.getById(borrowId);
        if (borrow == null || "RETURNED".equals(borrow.getStatus())) {
            return false;
        }

        // Tạo ngày trả hiện tại
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Cập nhật trạng thái trả sách trong DB
        boolean isSuccess = borrowDAO.returnBook(borrowId, currentDate);
        if (isSuccess) {
            // Tăng số lượng sách trong kho lên 1
            Book book = bookDAO.getById(borrow.getBookId());
            if (book != null) {
                book.setQuantity(book.getQuantity() + 1);
                bookDAO.update(book);
            }
            return true;
        }

        return false;
    }
}
