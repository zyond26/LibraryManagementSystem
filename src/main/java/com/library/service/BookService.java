package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;

import java.util.List;

/**
 * Lớp nghiệp vụ xử lý dữ liệu cho Sách.
 */
public class BookService {
    private final BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAll();
    }

    public Book getBookById(int id) {
        return bookDAO.getById(id);
    }

    public boolean addBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()
                || book.getAuthor() == null || book.getAuthor().trim().isEmpty()
                || book.getQuantity() < 0 || book.getPrice() < 0) {
            return false;
        }
        return bookDAO.add(book);
    }

    public boolean updateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()
                || book.getAuthor() == null || book.getAuthor().trim().isEmpty()
                || book.getQuantity() < 0 || book.getPrice() < 0) {
            return false;
        }
        return bookDAO.update(book);
    }

    public boolean deleteBook(int id) {
        return bookDAO.delete(id);
    }

    public List<Book> searchBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return bookDAO.getAll();
        }
        return bookDAO.search(query.trim());
    }

    public List<Book> filterBooksByCategory(int categoryId) {
        if (categoryId <= 0) {
            return bookDAO.getAll();
        }
        return bookDAO.filterByCategory(categoryId);
    }
}
