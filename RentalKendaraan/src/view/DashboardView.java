/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Asus
 */
import javax.swing.*;
import java.awt.*;
import model.KendaraanDAO;
import model.PelangganDAO;
import model.PenyewaanDAO;

public class DashboardView extends JFrame {
    // Komponen utama layout
    private JPanel sidebarPanel;
    private JPanel mainContentPanel;
    
    // Tombol menu di sidebar
    private JButton btnDashboard;
    private JButton btnDataKendaraan;
    private JButton btnDataPelanggan;
    private JButton btnPenyewaan;
    private JButton btnLogout;

    // Instance DAO untuk mengambil statistik data database
    private KendaraanDAO kendaraanDAO = new KendaraanDAO();
    private PelangganDAO pelangganDAO = new PelangganDAO();
    private PenyewaanDAO penyewaanDAO = new PenyewaanDAO();

    public DashboardView() {
        setTitle("RS RENTAL - Dashboard Admin");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Langsung full screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        // ==========================================
        // 1. SIDEBAR (SEBELAH KIRI)
        // ==========================================
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Style.dark);
        sidebarPanel.setPreferredSize(new Dimension(250, 700));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(30, 15, 30, 15));

        // Judul Aplikasi di Sidebar
        JLabel logoLabel = new JLabel("RS RENTAL");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(logoLabel);
        
        sidebarPanel.add(Box.createVerticalStrut(40)); // Jarak pemisah

        // Inisialisasi tombol menu
        btnDashboard = new JButton("Dashboard");
        btnDataKendaraan = new JButton("Data Kendaraan");
        btnDataPelanggan = new JButton("Data Pelanggan");
        btnPenyewaan = new JButton("Penyewaan");
        btnLogout = new JButton("Logout");

        // Styling tombol menggunakan kelas Style bawaanmu
        Style.button(btnDashboard, Style.dark);
        Style.button(btnDataKendaraan, Style.dark);
        Style.button(btnDataPelanggan, Style.dark);
        Style.button(btnPenyewaan, Style.dark); 
        Style.button(btnLogout, Style.danger);

        // Atur alignment tombol agar rapi memenuhi sidebar
        btnDashboard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnDataKendaraan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnDataPelanggan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnPenyewaan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnLogout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        
        btnDashboard.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDataKendaraan.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDataPelanggan.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPenyewaan.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Masukkan tombol ke sidebar secara berurutan
        sidebarPanel.add(btnDashboard);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnDataKendaraan);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnDataPelanggan);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnPenyewaan);
        sidebarPanel.add(Box.createVerticalStrut(20));
        sidebarPanel.add(btnLogout);

        // ==========================================
        // 2. MAIN CONTENT PANEL (SEBELAH KANAN)
        // ==========================================
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.setBackground(Style.light);

        // Tampilan default saat pertama kali admin masuk
        showWelcomePanel();

        // Menyusun kerangka utama frame
        add(sidebarPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);

        // ==========================================
        // 3. LOGIKA EVENT HANDLING (PERPINDAHAN VIEW)
        // ==========================================
        // Klik Menu Dashboard
        btnDashboard.addActionListener(e -> {
            showWelcomePanel();
        });

        // Klik Menu Data Kendaraan
        btnDataKendaraan.addActionListener(e -> {
            System.out.println("Tombol Kendaraan diklik!");
            switchPanel(new KendaraanView()); 
        });
        
        // Klik Menu Data Pelanggan
        btnDataPelanggan.addActionListener(e -> {
            switchPanel(new PelangganView());
        });

        // Klik Menu Penyewaan
        btnPenyewaan.addActionListener(e -> {
            switchPanel(new PenyewaanView()); 
        });

        // Klik Menu Logout
        btnLogout.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda ingin keluar?", "Logout", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                new LoginView();
                dispose();
            }
        });

        setVisible(true);
    }

    // untuk menampilkan halaman depan dashboard 
    private void showWelcomePanel() {
        mainContentPanel.removeAll();
    
        JPanel dashboardContainer = new JPanel(new BorderLayout(20, 20));
        dashboardContainer.setBackground(Style.light);
        dashboardContainer.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Header Section
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        headerPanel.setBackground(Style.light);
        
        JLabel welcomeLabel = new JLabel("Selamat Datang Kembali, Admin!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setForeground(Style.dark);
        
        JLabel subLabel = new JLabel("Berikut adalah ringkasan operasional bisnis RS RENTAL hari ini.");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subLabel.setForeground(Color.GRAY);
        
        headerPanel.add(welcomeLabel);
        headerPanel.add(subLabel);
        dashboardContainer.add(headerPanel, BorderLayout.NORTH);

        // Cards Section (Tengah)
        JPanel cardsGridPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardsGridPanel.setBackground(Style.light);

        // Ambil data angka aktual dari database via fungsi DAO baru
        int countKendaraan = kendaraanDAO.getTotalKendaraan();
        int countPelanggan = pelangganDAO.getTotalPelanggan();
        int countTransaksi = penyewaanDAO.getTotalTransaksi();

        // Membuat 3 buah kotak info panel memanfaatkan komponen RoundedPanel buatanmu
        JPanel card1 = createStatCard("Total Kendaraan", String.valueOf(countKendaraan), "Unit Armada", new Color(40, 167, 69));
        JPanel card2 = createStatCard("Pelanggan Terdaftar", String.valueOf(countPelanggan), "Orang", new Color(23, 162, 184));
        JPanel card3 = createStatCard("Total Transaksi", String.valueOf(countTransaksi), "Sewa Berjalan", new Color(224, 168, 0));

        cardsGridPanel.add(card1);
        cardsGridPanel.add(card2);
        cardsGridPanel.add(card3);
        
        // Agar posisi card tidak terlalu melar ke bawah, kita bungkus di panel penahan khusus
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(Style.light);
        centerWrapper.add(cardsGridPanel, BorderLayout.NORTH);
        
        dashboardContainer.add(centerWrapper, BorderLayout.CENTER);
        
        mainContentPanel.add(dashboardContainer, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    // Helper Method khusus untuk mencetak komponen Box Stat Card rounded secara dinamis
    private JPanel createStatCard(String title, String value, String unit, Color accentColor) {
        view.RoundedPanel card = new view.RoundedPanel(20); 
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(220, 150));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 235, 240), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // Panel teks atas & angka tengah
        JPanel textPanel = new JPanel(new GridLayout(3, 1, 2, 2));
        textPanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(Color.GRAY);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValue.setForeground(Style.dark);

        JLabel lblUnit = new JLabel(unit);
        lblUnit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUnit.setForeground(Color.LIGHT_GRAY);

        textPanel.add(lblTitle);
        textPanel.add(lblValue);
        textPanel.add(lblUnit);
        card.add(textPanel, BorderLayout.CENTER);

        JPanel lineAccent = new JPanel();
        lineAccent.setBackground(accentColor);
        lineAccent.setPreferredSize(new Dimension(5, 0));
        card.add(lineAccent, BorderLayout.WEST);

        return card;
    }

    // Method dinamis untuk menukar isi panel sebelah kanan
    private void switchPanel(JPanel panelBaru) {
        mainContentPanel.removeAll(); // Hapus konten lama
        mainContentPanel.add(panelBaru, BorderLayout.CENTER); // Tambah yang baru

        //Refresh layout agar tidak stuck/kosong
        mainContentPanel.revalidate(); 
        mainContentPanel.repaint();
    }
}