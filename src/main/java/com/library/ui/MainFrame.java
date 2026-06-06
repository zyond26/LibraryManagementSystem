package com.library.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Giao diện chính của Hệ thống Quản lý Thư viện.
 */
public class MainFrame extends JFrame {
    private JPanel pnlContent;
    private CardLayout cardLayout;

    // Các Panels chức năng
    private BookPanel bookPanel;
    private ReaderPanel readerPanel;
    private CategoryPanel categoryPanel;
    private BorrowPanel borrowPanel;
    private SearchPanel searchPanel;

    // Các buttons menu
    private JButton btnBook;
    private JButton btnReader;
    private JButton btnCategory;
    private JButton btnBorrow;
    private JButton btnSearch;

    public MainFrame() {
        super("Hệ thống Quản lý Thư viện - Đồ án Java OOP");
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null); // Hiển thị giữa màn hình
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // 1. Sidebar bên trái
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));
        pnlSidebar.setBackground(new Color(44, 62, 80)); // Màu Navy sẫm
        pnlSidebar.setPreferredSize(new Dimension(250, 0));
        pnlSidebar.setBorder(new EmptyBorder(20, 15, 20, 15));

        // Tiêu đề Sidebar
        JLabel lblLogo = new JLabel("THƯ VIỆN OOP");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogo.setBorder(new EmptyBorder(0, 0, 30, 0));
        pnlSidebar.add(lblLogo);

        // Các nút chức năng
        btnBook = createMenuButton(" Quản lý Sách");
        btnReader = createMenuButton(" Quản lý Độc giả");
        btnCategory = createMenuButton(" Quản lý Thể loại");
        btnBorrow = createMenuButton(" Quản lý Mượn Trả");
        btnSearch = createMenuButton(" Tìm kiếm & Lọc");

        pnlSidebar.add(btnBook);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlSidebar.add(btnReader);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlSidebar.add(btnCategory);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlSidebar.add(btnBorrow);
        pnlSidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlSidebar.add(btnSearch);

        // Thêm thông tin bản quyền/sinh viên ở góc dưới sidebar
        pnlSidebar.add(Box.createVerticalGlue());
        JLabel lblFooter = new JLabel("<html><center>Đồ án môn học OOP<br>GVHD: Giảng viên Java</center></html>");
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblFooter.setForeground(new Color(189, 195, 199));
        lblFooter.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlSidebar.add(lblFooter);

        add(pnlSidebar, BorderLayout.WEST);

        // 2. Khu vực hiển thị nội dung bên phải (CardLayout)
        cardLayout = new CardLayout();
        pnlContent = new JPanel(cardLayout);
        pnlContent.setBackground(new Color(245, 246, 250));

        // Khởi tạo các panel
        bookPanel = new BookPanel();
        readerPanel = new ReaderPanel();
        categoryPanel = new CategoryPanel();
        borrowPanel = new BorrowPanel();
        searchPanel = new SearchPanel();

        // Thêm các panel vào CardLayout
        pnlContent.add(bookPanel, "BOOK");
        pnlContent.add(readerPanel, "READER");
        pnlContent.add(categoryPanel, "CATEGORY");
        pnlContent.add(borrowPanel, "BORROW");
        pnlContent.add(searchPanel, "SEARCH");

        add(pnlContent, BorderLayout.CENTER);

        // Gắn sự kiện cho các nút menu
        btnBook.addActionListener(e -> showPanel("BOOK"));
        btnReader.addActionListener(e -> showPanel("READER"));
        btnCategory.addActionListener(e -> showPanel("CATEGORY"));
        btnBorrow.addActionListener(e -> showPanel("BORROW"));
        btnSearch.addActionListener(e -> showPanel("SEARCH"));

        // Hiển thị Panel Sách đầu tiên và đánh dấu nút
        showPanel("BOOK");
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(new Color(236, 240, 241));
        btn.setBackground(new Color(52, 73, 94)); // Màu xám nhẹ hơn
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Hover Effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn.getBackground().equals(new Color(52, 73, 94))) {
                    btn.setBackground(new Color(41, 128, 185)); // Màu xanh sáng khi hover
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn.getBackground().equals(new Color(41, 128, 185))) {
                    btn.setBackground(new Color(52, 73, 94));
                }
            }
        });

        return btn;
    }

    private void showPanel(String panelName) {
        // Chuyển card hiển thị
        cardLayout.show(pnlContent, panelName);

        // Reset màu tất cả các nút
        Color defaultBtnColor = new Color(52, 73, 94);
        btnBook.setBackground(defaultBtnColor);
        btnReader.setBackground(defaultBtnColor);
        btnCategory.setBackground(defaultBtnColor);
        btnBorrow.setBackground(defaultBtnColor);
        btnSearch.setBackground(defaultBtnColor);

        // Đánh dấu nút đang được chọn bằng màu Active (Teal/Blue)
        Color activeColor = new Color(26, 188, 156); // Teal
        switch (panelName) {
            case "BOOK" -> {
                btnBook.setBackground(activeColor);
                bookPanel.loadCategories();
                bookPanel.loadBooks();
            }
            case "READER" -> {
                btnReader.setBackground(activeColor);
                readerPanel.loadReaders();
            }
            case "CATEGORY" -> {
                btnCategory.setBackground(activeColor);
                categoryPanel.loadData();
            }
            case "BORROW" -> {
                btnBorrow.setBackground(activeColor);
                borrowPanel.loadData();
            }
            case "SEARCH" -> {
                btnSearch.setBackground(activeColor);
                searchPanel.loadCategories();
            }
        }
    }
}
