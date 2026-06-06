package com.library;

import com.library.database.DatabaseInitializer;
import com.library.ui.MainFrame;

import javax.swing.*;

/**
 * Điểm khởi chạy chính của chương trình.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Khởi tạo cơ sở dữ liệu SQLite (tự động tạo bảng & chèn dữ liệu mẫu nếu chưa có)
        DatabaseInitializer.initialize();

        // 2. Thiết lập Look and Feel cho giao diện Swing đẹp và hiện đại hơn
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // 3. Khởi chạy giao diện chính trên luồng Swing Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
