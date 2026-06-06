package com.library.ui;

import com.library.model.Book;
import com.library.model.Category;
import com.library.service.BookService;
import com.library.service.CategoryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Giao diện Tìm kiếm và Lọc sách.
 */
public class SearchPanel extends JPanel {
    private final BookService bookService;
    private final CategoryService categoryService;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtSearch;
    private JComboBox<Object> cbCategoryFilter; // Chứa Category hoặc chuỗi "Tất cả thể loại"

    private JButton btnSearch;
    private JButton btnFilter;
    private JButton btnReset;

    public SearchPanel() {
        this.bookService = new BookService();
        this.categoryService = new CategoryService();
        initComponents();
        loadCategories();
        loadAllBooks();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 246, 250));

        // Tiêu đề Panel
        JLabel lblTitle = new JLabel("TÌM KIẾM VÀ LỌC SÁCH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(44, 62, 80));
        add(lblTitle, BorderLayout.NORTH);

        // Khu vực thanh công cụ Tìm kiếm / Lọc (Phía trên)
        JPanel pnlToolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlToolbar.setBackground(Color.WHITE);
        pnlToolbar.setBorder(BorderFactory.createEtchedBorder());

        // Tìm kiếm
        JLabel lblSearch = new JLabel("Từ khóa:");
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch = new JTextField(15);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnSearch = new JButton("Tìm kiếm");
        designButton(btnSearch, new Color(52, 152, 219));

        // Lọc thể loại
        JLabel lblCategory = new JLabel("Thể loại:");
        lblCategory.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbCategoryFilter = new JComboBox<>();
        cbCategoryFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnFilter = new JButton("Lọc");
        designButton(btnFilter, new Color(46, 204, 113));

        // Reset
        btnReset = new JButton("Tất cả sách");
        designButton(btnReset, new Color(149, 165, 166));

        pnlToolbar.add(lblSearch);
        pnlToolbar.add(txtSearch);
        pnlToolbar.add(btnSearch);
        pnlToolbar.add(new JSeparator(JSeparator.VERTICAL));
        pnlToolbar.add(lblCategory);
        pnlToolbar.add(cbCategoryFilter);
        pnlToolbar.add(btnFilter);
        pnlToolbar.add(btnReset);

        add(pnlToolbar, BorderLayout.NORTH);

        // Bảng kết quả tìm kiếm
        tableModel = new DefaultTableModel(
                new Object[]{"Mã sách", "Tên sách", "Tác giả", "Nhà xuất bản", "Đơn giá", "Số lượng", "Thể loại"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(44, 62, 80));
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Events
        btnSearch.addActionListener(e -> searchBooks());
        btnFilter.addActionListener(e -> filterBooks());
        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            cbCategoryFilter.setSelectedIndex(0);
            loadAllBooks();
        });
    }

    private void designButton(JButton button, Color background) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void loadCategories() {
        cbCategoryFilter.removeAllItems();
        cbCategoryFilter.addItem("Tất cả thể loại");
        List<Category> categories = categoryService.getAllCategories();
        for (Category c : categories) {
            cbCategoryFilter.addItem(c);
        }
    }

    private void loadAllBooks() {
        displayBooks(bookService.getAllBooks());
    }

    private void displayBooks(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                    b.getId(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.getPublisher(),
                    formatPrice(b.getPrice()),
                    b.getQuantity(),
                    b.getCategoryName()
            });
        }
    }

    private void searchBooks() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadAllBooks();
            return;
        }
        List<Book> result = bookService.searchBooks(keyword);
        displayBooks(result);
    }

    private void filterBooks() {
        Object selected = cbCategoryFilter.getSelectedItem();
        if (selected == null || selected.equals("Tất cả thể loại")) {
            loadAllBooks();
        } else if (selected instanceof Category) {
            int categoryId = ((Category) selected).getId();
            List<Book> result = bookService.filterBooksByCategory(categoryId);
            displayBooks(result);
        }
    }

    private String formatPrice(double price) {
        java.text.DecimalFormat formatter = new java.text.DecimalFormat("#,###");
        java.text.DecimalFormatSymbols symbols = new java.text.DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(price) + " (VNĐ)";
    }
}
