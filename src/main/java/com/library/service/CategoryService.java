package com.library.service;

import com.library.dao.CategoryDAO;
import com.library.model.Category;

import java.util.List;

/**
 * Lớp nghiệp vụ xử lý dữ liệu cho Thể loại.
 */
public class CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    public List<Category> getAllCategories() {
        return categoryDAO.getAll();
    }

    public Category getCategoryById(int id) {
        return categoryDAO.getById(id);
    }

    public boolean addCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            return false;
        }
        return categoryDAO.add(category);
    }

    public boolean updateCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            return false;
        }
        return categoryDAO.update(category);
    }

    public boolean deleteCategory(int id) {
        // Có thể bổ sung kiểm tra xem thể loại có chứa sách hay không
        return categoryDAO.delete(id);
    }
}
