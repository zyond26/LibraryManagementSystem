package com.library.service;

import com.library.dao.ReaderDAO;
import com.library.model.Reader;

import java.util.List;

/**
 * Lớp nghiệp vụ xử lý dữ liệu cho Độc giả.
 */
public class ReaderService {
    private final ReaderDAO readerDAO;

    public ReaderService() {
        this.readerDAO = new ReaderDAO();
    }

    public List<Reader> getAllReaders() {
        return readerDAO.getAll();
    }

    public Reader getReaderById(int id) {
        return readerDAO.getById(id);
    }

    public boolean addReader(Reader reader) {
        if (reader.getFullName() == null || reader.getFullName().trim().isEmpty()) {
            return false;
        }
        return readerDAO.add(reader);
    }

    public boolean updateReader(Reader reader) {
        if (reader.getFullName() == null || reader.getFullName().trim().isEmpty()) {
            return false;
        }
        return readerDAO.update(reader);
    }

    public boolean deleteReader(int id) {
        return readerDAO.delete(id);
    }
}
