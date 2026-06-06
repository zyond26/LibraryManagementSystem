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
 * Giao diện Quản lý Sách.
 */
public class BookPanel extends JPanel {
    private final BookService bookService;
    private final CategoryService categoryService;

    private JTable table;
    private DefaultTableModel tableModel;
    
    private JTextField txtId;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtPublisher;
    private JTextField txtPrice;
    private JTextField txtQuantity;
    private JComboBox<Category> cbCategory;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;

    public BookPanel() {
        this.bookService = new BookService();
        this.categoryService = new CategoryService();
        initComponents();
        loadCategories();
        loadBooks();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 246, 250));

        // Tiêu đề Panel
        JLabel lblTitle = new JLabel("QUẢN LÝ SÁCH TRONG THƯ VIỆN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(44, 62, 80));
        add(lblTitle, BorderLayout.NORTH);

        // Bảng dữ liệu (Bên trái)
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
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                fillForm(selectedRow);
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Form (Bên phải)
        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Thông tin cuốn sách",
                0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80)
        ));
        pnlForm.setPreferredSize(new Dimension(350, 0));
        pnlForm.setBackground(Color.WHITE);

        JPanel pnlFields = new JPanel(new GridLayout(7, 2, 10, 10));
        pnlFields.setBackground(Color.WHITE);
        pnlFields.setBorder(new EmptyBorder(15, 10, 15, 10));

        JLabel lblId = new JLabel("Mã sách:");
        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(new Color(236, 240, 241));

        JLabel lblBookTitle = new JLabel("Tên sách:");
        txtTitle = new JTextField();

        JLabel lblAuthor = new JLabel("Tác giả:");
        txtAuthor = new JTextField();

        JLabel lblPublisher = new JLabel("Nhà xuất bản:");
        txtPublisher = new JTextField();

        JLabel lblPrice = new JLabel("Đơn giá (VND):");
        txtPrice = new JTextField();

        JLabel lblQuantity = new JLabel("Số lượng:");
        txtQuantity = new JTextField();

        JLabel lblCategory = new JLabel("Thể loại:");
        cbCategory = new JComboBox<>();
        cbCategory.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Set font
        JLabel[] labels = {lblId, lblBookTitle, lblAuthor, lblPublisher, lblPrice, lblQuantity, lblCategory};
        JTextField[] fields = {txtId, txtTitle, txtAuthor, txtPublisher, txtPrice, txtQuantity};
        for (JLabel lbl : labels) lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        for (JTextField tf : fields) tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pnlFields.add(lblId); pnlFields.add(txtId);
        pnlFields.add(lblBookTitle); pnlFields.add(txtTitle);
        pnlFields.add(lblAuthor); pnlFields.add(txtAuthor);
        pnlFields.add(lblPublisher); pnlFields.add(txtPublisher);
        pnlFields.add(lblPrice); pnlFields.add(txtPrice);
        pnlFields.add(lblQuantity); pnlFields.add(txtQuantity);
        pnlFields.add(lblCategory); pnlFields.add(cbCategory);

        pnlForm.add(pnlFields);

        // Panel nút bấm
        JPanel pnlButtons = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlButtons.setBackground(Color.WHITE);
        pnlButtons.setBorder(new EmptyBorder(0, 10, 15, 10));

        btnAdd = new JButton("Thêm mới");
        btnEdit = new JButton("Cập nhật");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");

        designButton(btnAdd, new Color(46, 204, 113));
        designButton(btnEdit, new Color(52, 152, 219));
        designButton(btnDelete, new Color(231, 76, 60));
        designButton(btnClear, new Color(149, 165, 166));

        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);

        pnlButtons.add(btnAdd);
        pnlButtons.add(btnEdit);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnClear);

        pnlForm.add(pnlButtons);
        add(pnlForm, BorderLayout.EAST);

        // Events
        btnAdd.addActionListener(e -> addBook());
        btnEdit.addActionListener(e -> editBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnClear.addActionListener(e -> clearForm());
    }

    private void designButton(JButton button, Color background) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void loadCategories() {
        cbCategory.removeAllItems();
        List<Category> categories = categoryService.getAllCategories();
        for (Category c : categories) {
            cbCategory.addItem(c);
        }
    }

    public void loadBooks() {
        tableModel.setRowCount(0);
        List<Book> books = bookService.getAllBooks();
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                    b.getId(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.getPublisher(),
                    b.getPrice(),
                    b.getQuantity(),
                    b.getCategoryName()
            });
        }
    }

    private void fillForm(int selectedRow) {
        txtId.setText(table.getValueAt(selectedRow, 0).toString());
        txtTitle.setText(table.getValueAt(selectedRow, 1).toString());
        txtAuthor.setText(table.getValueAt(selectedRow, 2).toString());
        txtPublisher.setText(table.getValueAt(selectedRow, 3).toString());
        txtPrice.setText(table.getValueAt(selectedRow, 4).toString());
        txtQuantity.setText(table.getValueAt(selectedRow, 5).toString());

        String categoryName = table.getValueAt(selectedRow, 6).toString();
        for (int i = 0; i < cbCategory.getItemCount(); i++) {
            Category c = cbCategory.getItemAt(i);
            if (c.getName().equals(categoryName)) {
                cbCategory.setSelectedIndex(i);
                break;
            }
        }
    }

    private void addBook() {
        if (!validateForm()) return;

        String title = txtTitle.getText().trim();
        String author = txtAuthor.getText().trim();
        String publisher = txtPublisher.getText().trim();
        double price = Double.parseDouble(txtPrice.getText().trim());
        int quantity = Integer.parseInt(txtQuantity.getText().trim());
        Category category = (Category) cbCategory.getSelectedItem();

        if (category == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thể loại sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = new Book(title, author, publisher, price, quantity, category.getId());
        if (bookService.addBook(book)) {
            JOptionPane.showMessageDialog(this, "Thêm sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadBooks();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editBook() {
        if (txtId.getText().isEmpty()) return;
        if (!validateForm()) return;

        int id = Integer.parseInt(txtId.getText());
        String title = txtTitle.getText().trim();
        String author = txtAuthor.getText().trim();
        String publisher = txtPublisher.getText().trim();
        double price = Double.parseDouble(txtPrice.getText().trim());
        int quantity = Integer.parseInt(txtQuantity.getText().trim());
        Category category = (Category) cbCategory.getSelectedItem();

        if (category == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thể loại sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book book = new Book(id, title, author, publisher, price, quantity, category.getId());
        if (bookService.updateBook(book)) {
            JOptionPane.showMessageDialog(this, "Cập nhật sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadBooks();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBook() {
        if (txtId.getText().isEmpty()) return;

        int id = Integer.parseInt(txtId.getText());
        int option = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa cuốn sách này?\nLưu ý: Nếu sách này từng được mượn, thao tác xóa có thể bị lỗi liên kết dữ liệu.",
                "Xác nhận xóa sách",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            if (bookService.deleteBook(id)) {
                JOptionPane.showMessageDialog(this, "Xóa sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadBooks();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể xóa sách này. Có thể sách đang nằm trong phiếu mượn trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateForm() {
        if (txtTitle.getText().trim().isEmpty() || txtAuthor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên sách và Tác giả không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn hoặc bằng 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            if (quantity < 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn hoặc bằng 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void clearForm() {
        txtId.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        txtPublisher.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        if (cbCategory.getItemCount() > 0) cbCategory.setSelectedIndex(0);
        table.clearSelection();
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }
}
