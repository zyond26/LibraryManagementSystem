package com.library.ui;

import com.library.model.Category;
import com.library.service.CategoryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Giao diện Quản lý Thể loại sách.
 */
public class CategoryPanel extends JPanel {
    private final CategoryService categoryService;

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId;
    private JTextField txtName;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;

    public CategoryPanel() {
        this.categoryService = new CategoryService();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 246, 250));

        // Tiêu đề Panel
        JLabel lblTitle = new JLabel("QUẢN LÝ THỂ LOẠI SÁCH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
        add(lblTitle, BorderLayout.NORTH);

        // Bảng danh sách thể loại (Bên trái)
        tableModel = new DefaultTableModel(new Object[]{"Mã thể loại", "Tên thể loại"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
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
                txtId.setText(table.getValueAt(selectedRow, 0).toString());
                txtName.setText(table.getValueAt(selectedRow, 1).toString());
                btnDelete.setEnabled(true);
                btnEdit.setEnabled(true);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Form nhập liệu (Bên phải)
        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Thông tin thể loại",
                0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80)
        ));
        pnlForm.setPreferredSize(new Dimension(300, 0));
        pnlForm.setBackground(Color.WHITE);

        JPanel pnlFields = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlFields.setBackground(Color.WHITE);
        pnlFields.setBorder(new EmptyBorder(15, 10, 15, 10));

        JLabel lblId = new JLabel("Mã thể loại:");
        lblId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtId = new JTextField();
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtId.setEditable(false);
        txtId.setBackground(new Color(236, 240, 241));

        JLabel lblName = new JLabel("Tên thể loại:");
        lblName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtName = new JTextField();
        txtName.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pnlFields.add(lblId);
        pnlFields.add(txtId);
        pnlFields.add(lblName);
        pnlFields.add(txtName);

        pnlForm.add(pnlFields);

        // Panel nút bấm
        JPanel pnlButtons = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlButtons.setBackground(Color.WHITE);
        pnlButtons.setBorder(new EmptyBorder(0, 10, 15, 10));

        btnAdd = new JButton("Thêm mới");
        btnEdit = new JButton("Cập nhật");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");

        // Thiết kế button
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

        // Gắn sự kiện cho các button
        btnAdd.addActionListener(e -> addCategory());
        btnEdit.addActionListener(e -> editCategory());
        btnDelete.addActionListener(e -> deleteCategory());
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

    public void loadData() {
        tableModel.setRowCount(0);
        List<Category> categories = categoryService.getAllCategories();
        for (Category c : categories) {
            tableModel.addRow(new Object[]{c.getId(), c.getName()});
        }
    }

    private void addCategory() {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên thể loại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Category category = new Category(name);
        if (categoryService.addCategory(category)) {
            JOptionPane.showMessageDialog(this, "Thêm thể loại thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại! Tên thể loại có thể đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editCategory() {
        int id = Integer.parseInt(txtId.getText());
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên thể loại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Category category = new Category(id, name);
        if (categoryService.updateCategory(category)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thể loại thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCategory() {
        int id = Integer.parseInt(txtId.getText());
        int option = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa thể loại này?\nLưu ý: Nếu có sách thuộc thể loại này, thao tác sẽ bị từ chối.",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            try {
                if (categoryService.deleteCategory(id)) {
                    JOptionPane.showMessageDialog(this, "Xóa thể loại thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa thể loại này. Thể loại có thể đang chứa sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        table.clearSelection();
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }
}
