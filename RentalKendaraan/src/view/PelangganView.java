/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.PelangganController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author putri
 */
public class PelangganView extends JPanel {
    // Komponen Form Input (Kiri)
    JTextField tfNama = new JTextField();
    JTextField tfNoHP = new JTextField();
    JTextArea taAlamat = new JTextArea(4, 20);
    
    JButton btnTambah = new JButton("Tambah");
    JButton btnSave = new JButton("Save");
    JButton btnHapus = new JButton("Hapus");
    JButton btnRefresh = new JButton("Refresh");
    
    // Komponen Pencarian & Tabel (Kanan)
    JTextField tfCari = new JTextField();
    JTable table = new JTable();
    DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"No", "Nama", "No. HP", "Alamat", "ID"}, 0);
    
    PelangganController controller = new PelangganController();
    int selectedId = -1;

    public PelangganView() {
        // Menggunakan BorderLayout utama dengan gap agar tidak saling menempel
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(240, 242, 245));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ==========================================
        // 1. PANEL KIRI: FORM INPUT
        // ==========================================
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(240, 242, 245));
        formPanel.setPreferredSize(new Dimension(280, 600));
        
        // Menggunakan TitledBorder tanpa garis border luar biar bersih mepet kiri sempurna
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(), "Form Pelanggan", 0, 0, new Font("Dialog", Font.BOLD, 14)
        ));

        // 1. ATUR UKURAN MAKSIMAL KOMPONEN
        Dimension fieldSize = new Dimension(260, 30);
        tfNama.setMaximumSize(fieldSize);
        tfNoHP.setMaximumSize(fieldSize);
        
        JScrollPane scrollAlamat = new JScrollPane(taAlamat);
        scrollAlamat.setMaximumSize(new Dimension(260, 80));
        taAlamat.setLineWrap(true);
        taAlamat.setWrapStyleWord(true);

        // 2. FIXED: ATUR FONT DIALOG BOLD 12 & PAKSA LABEL MEPRET KIRI
        Font labelFont = new Font("Dialog", Font.BOLD, 12);
        
        JLabel lblNama = new JLabel("Nama Pelanggan"); 
        lblNama.setFont(labelFont);
        lblNama.setAlignmentX(Component.LEFT_ALIGNMENT); // Mepet kiri
        
        JLabel lblNoHP = new JLabel("No. HP / WhatsApp"); 
        lblNoHP.setFont(labelFont);
        lblNoHP.setAlignmentX(Component.LEFT_ALIGNMENT); // Mepet kiri
        
        JLabel lblAlamat = new JLabel("Alamat"); 
        lblAlamat.setFont(labelFont);
        lblAlamat.setAlignmentX(Component.LEFT_ALIGNMENT); // Mepet kiri

        // 3. FIXED: PAKSA TEXTFIELD & SCROLLPANE IKUT RATAKAN KIRI
        tfNama.setAlignmentX(Component.LEFT_ALIGNMENT);
        tfNoHP.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollAlamat.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel Grid Tombol
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        btnPanel.setBackground(new Color(240, 242, 245));
        btnPanel.setMaximumSize(new Dimension(260, 80));
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Tombol ikut mepet kiri
        
        btnPanel.add(btnTambah); btnPanel.add(btnSave);
        btnPanel.add(btnHapus);  btnPanel.add(btnRefresh);

        // 4. SUSUN SECOBA-COBA KOMPONEN KE DALAM PANEL
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(lblNama);       formPanel.add(Box.createVerticalStrut(5)); formPanel.add(tfNama);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(lblNoHP);       formPanel.add(Box.createVerticalStrut(5)); formPanel.add(tfNoHP);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(lblAlamat);     formPanel.add(Box.createVerticalStrut(5)); formPanel.add(scrollAlamat);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(btnPanel); 

        // Warna Tombol Sesuai Gambar Referensi Kamu
        btnTambah.setBackground(new Color(40, 167, 69));  btnTambah.setForeground(Color.WHITE);  
        btnSave.setBackground(new Color(224, 168, 0));    btnSave.setForeground(Color.WHITE);   
        btnHapus.setBackground(new Color(220, 53, 69));   btnHapus.setForeground(Color.WHITE);  
        btnRefresh.setBackground(new Color(23, 162, 184)); btnRefresh.setForeground(Color.WHITE);

        btnTambah.setFocusPainted(false); btnSave.setFocusPainted(false);
        btnHapus.setFocusPainted(false);  btnRefresh.setFocusPainted(false);

        // ==========================================
        // 2. PANEL KANAN: CARI & TABEL
        // ==========================================
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(new Color(240, 242, 245));

        // Bar Pencarian di Atas Tabel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        searchPanel.setBackground(new Color(240, 242, 245));
        
        JLabel lblCari = new JLabel("Cari Pelanggan: ");
        lblCari.setFont(labelFont);
        tfCari.setPreferredSize(new Dimension(250, 28));
        
        searchPanel.add(lblCari);
        searchPanel.add(tfCari);
        rightPanel.add(searchPanel, BorderLayout.NORTH);

        // Tabel Data Pelanggan
        table.setModel(tableModel);
        table.setRowHeight(25); // Baris lebih renggang & rapi
        table.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(43, 61, 81));
        table.getTableHeader().setForeground(Color.WHITE);
        
        table.getColumnModel().getColumn(4).setMinWidth(0);
        table.getColumnModel().getColumn(4).setMaxWidth(0);
        table.getColumnModel().getColumn(4).setWidth(0);
        
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        rightPanel.add(tableScroll, BorderLayout.CENTER);

        // Gabungkan Panel Kiri dan Kanan ke Layar Utama
        add(formPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // ==========================================
        // 3. LOGIKA AKSI & EVENT LISTENER
        // ==========================================
        // Ambil data dari database saat menu pertama dibuka
        controller.tampilkanData(tableModel);

        // Event Ketik Langsung Cari (Real-time Search)
        tfCari.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                controller.cari(tableModel, tfCari.getText());
            }
        });

        // FIXED: Tombol Tambah khusus untuk memasukkan DATA BARU (INSERT)
        btnTambah.addActionListener(e -> {
            String nama = tfNama.getText().trim();
            String nohp = tfNoHP.getText().trim();
            String alamat = taAlamat.getText().trim();

            if (nama.isEmpty() || nohp.isEmpty() || alamat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua kolom input wajib diisi untuk menambah data baru!", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Langsung eksekusi simpan data baru
            if (controller.simpan(nama, nohp, alamat)) {
                JOptionPane.showMessageDialog(this, "Data Pelanggan baru berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            }
        });

        // FIXED: Tombol Save KHUSUS untuk UPDATE data yang dipilih dari tabel
        btnSave.addActionListener(e -> {
            // Validasi
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Silakan pilih data pelanggan dari tabel terlebih dahulu untuk diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nama = tfNama.getText().trim();
            String nohp = tfNoHP.getText().trim();
            String alamat = taAlamat.getText().trim();

            if (nama.isEmpty() || nohp.isEmpty() || alamat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kolom input tidak boleh kosong saat mengedit data!", "Validasi Gagal", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Eksekusi update data lama berdasarkan selectedId
            if (controller.ubah(selectedId, nama, nohp, alamat)) {
                JOptionPane.showMessageDialog(this, "Perubahan data pelanggan berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            }
        });

        // Klik Baris Tabel untuk ambil data ke Form
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                // Ambil dari kolom indeks 4 (kolom ID yang disembunyikan)
                selectedId = (int) table.getValueAt(row, 4); 

                tfNama.setText(table.getValueAt(row, 1).toString());
                tfNoHP.setText(table.getValueAt(row, 2).toString());
                taAlamat.setText(table.getValueAt(row, 3).toString());
            }
        });

        // Tombol Hapus
        btnHapus.addActionListener(e -> {
            // Validasi
            if (selectedId == -1) {
                JOptionPane.showMessageDialog(this, "Silakan pilih data pelanggan dari tabel terlebih dahulu untuk dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Jika sudah milih, munculkan konfirmasi biar tidak sengaja kehapus
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus pelanggan ini?", "Hapus Data", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                // Eksekusi hapus ke controller berdasarkan selectedId
                if (controller.hapus(selectedId)) {
                    JOptionPane.showMessageDialog(this, "Data pelanggan berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    resetForm(); // Bersihkan form dan refresh tabel otomatis
                }
            }
        });

        // Tombol Refresh
        btnRefresh.addActionListener(e -> resetForm());
    }

    private void clearInput() {
        tfNama.setText("");
        tfNoHP.setText("");
        taAlamat.setText("");
        selectedId = -1;
        table.clearSelection();
    }

    private void resetForm() {
        clearInput();
        tfCari.setText("");
        controller.tampilkanData(tableModel);
    }
}