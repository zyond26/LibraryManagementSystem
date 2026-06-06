package com.library.ui;

import com.library.model.Reader;
import com.library.service.ReaderService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Giao diện Quản lý Độc giả.
 */
public class ReaderPanel extends JPanel {
    private final ReaderService readerService;

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtId;
    private JTextField txtFullName;
    private JTextField txtPhone;
    private JTextField txtEmail;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnClear;

    public ReaderPanel() {
        this.readerService = new ReaderService();
        initComponents();
        loadReaders();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 246, 250));

        // Tiêu đề Panel
        JLabel lblTitle = new JLabel("QUẢN LÝ ĐỘC GIẢ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(44, 62, 80));
        add(lblTitle, BorderLayout.NORTH);

        // Bảng dữ liệu (Bên trái)
        tableModel = new DefaultTableModel(
                new Object[]{"Mã độc giả", "Họ và tên", "Số điện thoại", "Email"}, 0
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
                BorderFactory.createEtchedBorder(), "Thông tin độc giả",
                0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80)
        ));
        pnlForm.setPreferredSize(new Dimension(300, 0));
        pnlForm.setBackground(Color.WHITE);

        JPanel pnlFields = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlFields.setBackground(Color.WHITE);
        pnlFields.setBorder(new EmptyBorder(15, 10, 15, 10));

        JLabel lblId = new JLabel("Mã độc giả:");
        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(new Color(236, 240, 241));

        JLabel lblFullName = new JLabel("Họ và tên:");
        txtFullName = new JTextField();

        JLabel lblPhone = new JLabel("Số điện thoại:");
        txtPhone = new JTextField();

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();

        // Set font
        JLabel[] labels = {lblId, lblFullName, lblPhone, lblEmail};
        JTextField[] fields = {txtId, txtFullName, txtPhone, txtEmail};
        for (JLabel lbl : labels) lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        for (JTextField tf : fields) tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pnlFields.add(lblId); pnlFields.add(txtId);
        pnlFields.add(lblFullName); pnlFields.add(txtFullName);
        pnlFields.add(lblPhone); pnlFields.add(txtPhone);
        pnlFields.add(lblEmail); pnlFields.add(txtEmail);

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
        btnAdd.addActionListener(e -> addReader());
        btnEdit.addActionListener(e -> editReader());
        btnDelete.addActionListener(e -> deleteReader());
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

    public void loadReaders() {
        tableModel.setRowCount(0);
        List<Reader> readers = readerService.getAllReaders();
        for (Reader r : readers) {
            tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getFullName(),
                    r.getPhone(),
                    r.getEmail()
            });
        }
    }

    private void fillForm(int selectedRow) {
        txtId.setText(table.getValueAt(selectedRow, 0).toString());
        txtFullName.setText(table.getValueAt(selectedRow, 1).toString());
        txtPhone.setText(table.getValueAt(selectedRow, 2).toString());
        txtEmail.setText(table.getValueAt(selectedRow, 3).toString());
    }

    private void addReader() {
        String fullName = txtFullName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ và tên độc giả không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Reader reader = new Reader(fullName, phone, email);
        if (readerService.addReader(reader)) {
            JOptionPane.showMessageDialog(this, "Thêm độc giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadReaders();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm độc giả thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editReader() {
        if (txtId.getText().isEmpty()) return;

        int id = Integer.parseInt(txtId.getText());
        String fullName = txtFullName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();

        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ và tên độc giả không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Reader reader = new Reader(id, fullName, phone, email);
        if (readerService.updateReader(reader)) {
            JOptionPane.showMessageDialog(this, "Cập nhật độc giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadReaders();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật độc giả thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteReader() {
        if (txtId.getText().isEmpty()) return;

        int id = Integer.parseInt(txtId.getText());
        int option = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa độc giả này?\nLưu ý: Nếu độc giả này đang có sách chưa trả, thao tác xóa có thể bị từ chối.",
                "Xác nhận xóa độc giả",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            if (readerService.deleteReader(id)) {
                JOptionPane.showMessageDialog(this, "Xóa độc giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadReaders();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể xóa độc giả này! Độc giả này có thể đang liên kết với phiếu mượn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtFullName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        table.clearSelection();
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }
}
