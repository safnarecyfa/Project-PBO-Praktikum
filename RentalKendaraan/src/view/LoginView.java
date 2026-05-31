/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.LoginController;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Asus
 */
public class LoginView extends JFrame {

    // =========================
    // OBJECT CONTROLLER
    // =========================
    LoginController controller = new LoginController();

    // =========================
    // COMPONENT
    // =========================
    JLabel lUser = new JLabel("Username");
    JLabel lPass = new JLabel("Password");

    JTextField tfUser = new JTextField();
    JPasswordField tfPass = new JPasswordField();

    JButton btnLogin = new JButton("LOGIN");

    // =========================
    // CONSTRUCTOR
    // =========================
    public LoginView() {

        setTitle("LOGIN ADMIN");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // =========================
        // PANEL UTAMA
        // =========================
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Style.light);
        mainPanel.setLayout(new GridLayout(1,2));
        
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Style.dark);
        leftPanel.setLayout(new GridBagLayout());

        JPanel branding = new JPanel();
        branding.setOpaque(false);
        branding.setLayout(new GridLayout(2,1));

        JLabel namaRental = new JLabel("RS RENTAL");

        namaRental.setHorizontalAlignment(SwingConstants.CENTER);
        namaRental.setForeground(Color.WHITE);
        namaRental.setFont(new Font("Segoe UI",Font.BOLD,58));

        JLabel slogan = new JLabel("Vehicle Management System");
        slogan.setHorizontalAlignment(SwingConstants.CENTER);
        slogan.setForeground(new Color(220,220,220));
        slogan.setFont(new Font("Segoe UI",Font.PLAIN,24));

        branding.add(namaRental);
        branding.add(slogan);

        leftPanel.add(branding);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Style.background);
        rightPanel.setLayout(new GridBagLayout());

        // =========================
        // CARD LOGIN
        // =========================
        RoundedPanel card = new RoundedPanel(35);
        card.setLayout(new GridLayout(7, 1, 10, 10));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(500, 420));
        card.setBorder(BorderFactory.createEmptyBorder(
                        30,
                        30,
                        30,
                        30));

        // =========================
        // TITLE
        // =========================
        JLabel title = new JLabel("ADMIN LOGIN");
        title.setFont(Style.fontTitle);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // =========================
        // STYLE COMPONENT
        // =========================
        Style.label(lUser);
        Style.label(lPass);
        Style.textField(tfUser);
        Style.textField(tfPass);
        Style.button(btnLogin,Style.primary);

        // =========================
        // ADD COMPONENT
        // =========================
        card.add(title);

        card.add(lUser);
        card.add(tfUser);

        card.add(lPass);
        card.add(tfPass);

        card.add(Box.createVerticalStrut(20));
        
        card.add(btnLogin);

        rightPanel.add(card);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);

        // =========================
        // ACTION LOGIN
        // =========================
        btnLogin.addActionListener(e -> {
            String user = tfUser.getText().trim();
            String pass = new String(tfPass.getPassword()).trim();

            // =========================
            // 1. VALIDASI KOSONG
            // =========================
            if (user.isEmpty() || pass.isEmpty()) {
                showError("Field tidak boleh kosong!");
                return;
            }

            // =========================
            // 2. CEK LOGIN
            // =========================
            boolean login = controller.login(user, pass);

            if (login) {
                JOptionPane.showMessageDialog(
                        this,
                        "Login Berhasil!",
                        "Selamat Datang di RS Rental!",
                        JOptionPane.INFORMATION_MESSAGE
                );

                new DashboardView();
                dispose();
            } else {
                showError("Username / Password salah!");
            }
        });

        setVisible(true);
    }
    
    private void showError(String message) {
        JDialog dialog = new JDialog(this);
        dialog.setSize(420, 260);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);
        dialog.setModal(true);

        // =========================
        // MAIN PANEL
        // =========================
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // =========================
        // TOP BAR (X BUTTON)
        // =========================
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);

        JLabel close = new JLabel("X");
        close.setFont(new Font("Segoe UI", Font.BOLD, 18));
        close.setForeground(Color.GRAY);
        close.setHorizontalAlignment(SwingConstants.RIGHT);
        close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                dialog.dispose();
            }

            public void mouseEntered(java.awt.event.MouseEvent e) {
                close.setForeground(Color.RED);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                close.setForeground(Color.GRAY);
            }
        });

        topBar.add(close, BorderLayout.EAST);

        // =========================
        // ICON
        // =========================
        JLabel icon = new JLabel(UIManager.getIcon("OptionPane.errorIcon"));
        icon.setHorizontalAlignment(SwingConstants.CENTER);

        // =========================
        // TEXT
        // =========================
        JLabel text = new JLabel(message, SwingConstants.CENTER);
        text.setFont(new Font("Segoe UI", Font.BOLD, 17));
        text.setForeground(new Color(60, 60, 60));

        JPanel center = new JPanel(new GridLayout(2, 1, 10, 10));
        center.setBackground(Color.WHITE);
        center.add(icon);
        center.add(text);

        // =========================
        // BUTTON OK
        // =========================
        JButton ok = new JButton("OK");
        ok.setFocusPainted(false);
        ok.setBackground(new Color(220, 53, 69));
        ok.setForeground(Color.WHITE);
        ok.setFont(new Font("Segoe UI", Font.BOLD, 14));

        ok.addActionListener(e -> dialog.dispose());

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);
        bottom.add(ok);

        // =========================
        // ASSEMBLY
        // =========================
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
//        panel.add(bottom, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}
