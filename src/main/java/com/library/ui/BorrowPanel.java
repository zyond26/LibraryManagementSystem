package com.library.ui;

import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Reader;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.ReaderService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Giao diện Quản lý Mượn/Trả sách.
 */
public class BorrowPanel extends JPanel {
    private final BorrowService borrowService;
    private final ReaderService readerService;
    private final BookService bookService;

    private JTable table;
    private DefaultTableModel tableModel;

    private JComboBox<Reader> cbReader;
    private JComboBox<Book> cbBook;

    private JButton btnBorrow;
    private JButton btnReturn;
    private JButton btnRefresh;

    private int selectedBorrowId = -1;

    public BorrowPanel() {
        this.borrowService = new BorrowService();
        this.readerService = new ReaderService();
        this.bookService = new BookService();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 246, 250));

        // Tiêu đề Panel
        JLabel lblTitle = new JLabel("QUẢN LÝ MƯỢN TRẢ SÁCH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(44, 62, 80));
        add(lblTitle, BorderLayout.NORTH);

        // Bảng danh sách phiếu mượn (Phía dưới)
        tableModel = new DefaultTableModel(
                new Object[]{"Mã phiếu", "Độc giả", "Tên sách", "Ngày mượn", "Ngày trả", "Trạng thái"}, 0
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
                selectedBorrowId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                String status = table.getValueAt(selectedRow, 5).toString();
                if ("Đang mượn".equals(status) || "BORROWED".equals(status)) {
                    btnReturn.setEnabled(true);
                } else {
                    btnReturn.setEnabled(false);
                }
            } else {
                btnReturn.setEnabled(false);
                selectedBorrowId = -1;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Khu vực điều khiển (Phía trên/Bên phải)
        JPanel pnlControl = new JPanel(new GridBagLayout());
        pnlControl.setBackground(Color.WHITE);
        pnlControl.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Thực hiện mượn sách",
                0, 0, new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80)
        ));
        pnlControl.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Label Reader
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblReader = new JLabel("Chọn Độc giả:");
        lblReader.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlControl.add(lblReader, gbc);

        // Combobox Reader
        gbc.gridx = 0; gbc.gridy = 1;
        cbReader = new JComboBox<>();
        cbReader.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlControl.add(cbReader, gbc);

        // Label Book
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblBook = new JLabel("Chọn Sách:");
        lblBook.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlControl.add(lblBook, gbc);

        // Combobox Book
        gbc.gridx = 0; gbc.gridy = 3;
        cbBook = new JComboBox<>();
        cbBook.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pnlControl.add(cbBook, gbc);

        // Button Mượn
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.insets = new Insets(20, 10, 5, 10);
        btnBorrow = new JButton("Đăng ký Mượn Sách");
        designButton(btnBorrow, new Color(46, 204, 113));
        pnlControl.add(btnBorrow, gbc);

        // Button Trả
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.insets = new Insets(5, 10, 5, 10);
        btnReturn = new JButton("Trả Sách Đang Chọn");
        designButton(btnReturn, new Color(231, 76, 60));
        btnReturn.setEnabled(false);
        pnlControl.add(btnReturn, gbc);

        // Button Refresh
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.insets = new Insets(5, 10, 10, 10);
        btnRefresh = new JButton("Làm mới danh sách");
        designButton(btnRefresh, new Color(52, 152, 219));
        pnlControl.add(btnRefresh, gbc);

        add(pnlControl, BorderLayout.EAST);

        // Sự kiện
        btnBorrow.addActionListener(e -> borrowBook());
        btnReturn.addActionListener(e -> returnBook());
        btnRefresh.addActionListener(e -> loadData());
    }

    private void designButton(JButton button, Color background) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void loadData() {
        loadReaders();
        loadBooks();
        loadBorrows();
        btnReturn.setEnabled(false);
        selectedBorrowId = -1;
    }

    private void loadReaders() {
        cbReader.removeAllItems();
        List<Reader> readers = readerService.getAllReaders();
        for (Reader r : readers) {
            cbReader.addItem(r);
        }
    }

    private void loadBooks() {
        cbBook.removeAllItems();
        List<Book> books = bookService.getAllBooks();
        for (Book b : books) {
            // Chỉ hiển thị sách còn trong kho để tránh chọn nhầm sách hết
            cbBook.addItem(b);
        }
    }

    private void loadBorrows() {
        tableModel.setRowCount(0);
        List<Borrow> borrows = borrowService.getAllBorrows();
        for (Borrow b : borrows) {
            String statusText = "BORROWED".equals(b.getStatus()) ? "Đang mượn" : "Đã trả";
            String returnDateText = b.getReturnDate() == null ? "Chưa trả" : b.getReturnDate();
            tableModel.addRow(new Object[]{
                    b.getId(),
                    b.getReaderName(),
                    b.getBookTitle(),
                    b.getBorrowDate(),
                    returnDateText,
                    statusText
            });
        }
    }

    private void borrowBook() {
        Reader reader = (Reader) cbReader.getSelectedItem();
        Book book = (Book) cbBook.getSelectedItem();

        if (reader == null || book == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả và sách hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String result = borrowService.borrowBook(reader.getId(), book.getId());
        if ("MƯỢN_THÀNH_CÔNG".equals(result)) {
            JOptionPane.showMessageDialog(this, "Đã đăng ký mượn sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, result, "Lỗi mượn sách", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnBook() {
        if (selectedBorrowId == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu mượn từ danh sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(
                this,
                "Xác nhận hoàn thành việc trả sách cho phiếu mượn này?",
                "Xác nhận trả sách",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            if (borrowService.returnBook(selectedBorrowId)) {
                JOptionPane.showMessageDialog(this, "Đã hoàn thành việc trả sách!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xử lý trả sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
