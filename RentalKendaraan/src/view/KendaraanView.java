/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Asus
 */
import view.Style;
import controller.KendaraanController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class KendaraanView extends JPanel {
    // ==========================================
    // FORM COMPONENT
    // ==========================================
    JLabel lNama = new JLabel("Nama Kendaraan");
    JLabel lJenis = new JLabel("Jenis Kendaraan");
    JLabel lHarga12 = new JLabel("Harga 12 Jam");
    JLabel lHarga24 = new JLabel("Harga 24 Jam");
    
    JTextField tfNama = new JTextField();
    JComboBox<String> cbJenis = new JComboBox<>(new String[]{ "Mobil", "Motor"});
    JTextField tfHarga12 = new JTextField();
    JTextField tfHarga24 = new JTextField();

    // ==========================================
    // BUTTONS & SEARCH
    // ==========================================
    JButton btnTambah = new JButton("Tambah");
    JButton btnSave = new JButton("Save");
    JButton btnHapus = new JButton("Hapus");
    JButton btnRefresh = new JButton("Refresh");

    // Komponen Baru untuk Pencarian
    JLabel lCari = new JLabel("Cari Kendaraan: ");
    JTextField tfCari = new JTextField();

    // ==========================================
    // TABLE COMPONENT & SORTER
    // ==========================================
    JTable table;
    DefaultTableModel model;
    TableRowSorter<DefaultTableModel> rowSorter; // Untuk menyaring baris tabel
    KendaraanController controller = new KendaraanController();

    public KendaraanView() {
        // ==========================================
        // PANEL FORM (KIRI)
        // ==========================================
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(320, 600));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Kendaraan"));

        // Mengatur Ukuran Field Agar Tidak Terlalu Besar
        tfNama.setMaximumSize(new Dimension(260, 35));
        cbJenis.setMaximumSize(new Dimension(260, 35));
        tfHarga12.setMaximumSize(new Dimension(260, 35));
        tfHarga24.setMaximumSize(new Dimension(260, 35));

        // Set semua komponen agar mepet ke kiri (LEFT_ALIGNMENT)
        lNama.setAlignmentX(Component.LEFT_ALIGNMENT);
        tfNama.setAlignmentX(Component.LEFT_ALIGNMENT);
        lJenis.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbJenis.setAlignmentX(Component.LEFT_ALIGNMENT);
        lHarga12.setAlignmentX(Component.LEFT_ALIGNMENT);
        tfHarga12.setAlignmentX(Component.LEFT_ALIGNMENT);
        lHarga24.setAlignmentX(Component.LEFT_ALIGNMENT);
        tfHarga24.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Penataan Komponen di Form
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(lNama);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(tfNama);
        
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(lJenis);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(cbJenis);
        
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(lHarga12);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(tfHarga12);
        
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(lHarga24);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(tfHarga24);
        
        formPanel.add(Box.createVerticalStrut(25));

        // Panel Tombol Aksi
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(260, 80));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnRefresh);
        
        formPanel.add(buttonPanel);

        // ==========================================
        // PANEL TABEL & PENCARIAN (KANAN)
        // ==========================================
        JPanel kananPanel = new JPanel(new BorderLayout(10, 10));
        kananPanel.setBackground(Style.light);

        // Membuat Top Panel khusus tempat Field Cari
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Style.light);
        tfCari.setPreferredSize(new Dimension(250, 30));
        Style.label(lCari);
        searchPanel.add(lCari);
        searchPanel.add(tfCari);

        // Struktur Tabel
        model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Nama");
        model.addColumn("Jenis");
        model.addColumn("Harga 12 Jam");
        model.addColumn("Harga 24 Jam");
        model.addColumn("Status");
        model.addColumn("ID_Asli"); // Kolom rahasia untuk menampung ID database

        table = new JTable(model);
        Style.table(table);
        
        // Memasang RowSorter ke Tabel untuk fitur pencarian
        rowSorter = new TableRowSorter<>(model);
        table.setRowSorter(rowSorter);
        
        // Menyembunyikan kolom ID_Asli (indeks 6) dari pandangan user
        table.getColumnModel().getColumn(6).setMinWidth(0);
        table.getColumnModel().getColumn(6).setMaxWidth(0);
        table.getColumnModel().getColumn(6).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);

        // Menyatukan search bar dan tabel di panel kanan
        kananPanel.add(searchPanel, BorderLayout.NORTH);
        kananPanel.add(scrollPane, BorderLayout.CENTER);

        // Styling Komponen
        Style.button(btnTambah, Style.success);
        Style.button(btnSave, Style.warning);
        Style.button(btnHapus, Style.danger);
        Style.button(btnRefresh, Style.primary);
        
        Style.label(lNama);
        Style.label(lJenis);
        Style.label(lHarga12);
        Style.label(lHarga24);

        // Pasang Layout Utama
        setLayout(new BorderLayout(20, 20));
        add(formPanel, BorderLayout.WEST);
        add(kananPanel, BorderLayout.CENTER); // Menggunakan kananPanel yang baru

        // Load awal data ke JTable
        controller.loadTable(model);

        // ==========================================
        // PENCARIAN
        // ==========================================
        tfCari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                cariData();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                cariData();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                cariData();
            }
            
            // Method pembantu untuk menyaring data di tabel
            private void cariData() {
                String text = tfCari.getText().trim();
                if (text.length() == 0) {
                    rowSorter.setRowFilter(null); // Tampilkan semua jika kolom pencarian kosong
                } else {
                    // bisa cari dari Nama (1), Jenis (2), dan Status (5)
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1, 2, 5));
                }
            }
        });

        // ==========================================
        // ACTION EVENT HANDLING (CRUD)
        // ==========================================
        
        // 1. TAMBAH DATA
        btnTambah.addActionListener(e -> {
            try {
                // Mengambil teks dari form inputan
                String nama = tfNama.getText().trim();
                String jenis = cbJenis.getSelectedItem().toString();
                String harga12Raw = tfHarga12.getText().trim();
                String harga24Raw = tfHarga24.getText().trim();

                // Validasi
                if (nama.isEmpty() || harga12Raw.isEmpty() || harga24Raw.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Mengubah string harga menjadi angka pecahan (double)
                double harga12 = Double.parseDouble(harga12Raw);
                double harga24 = Double.parseDouble(harga24Raw);

                // Kirim data ke controller untuk diproses ke DB
                controller.tambahKendaraan(nama, jenis, harga12, harga24);
                
                // Refresh tabel agar data yang baru ditambahkan langsung muncul
                controller.loadTable(model);
                
                // Kosongkan kembali form inputan
                clearForm();
                
                JOptionPane.showMessageDialog(this, "Data kendaraan berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                // Menangkap error jika admin menginputkan huruf/simbol di field harga
                JOptionPane.showMessageDialog(this, "Harga 12 Jam dan 24 Jam harus berupa angka numerik!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 2. HAPUS DATA
        btnHapus.addActionListener(e -> {
            // Ambil baris yang sedang dipilih
            int row = table.getSelectedRow();
            
            // Validasi
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data di tabel terlebih dahulu yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Konfirmasi ulang ke user agar tidak sengaja terhapus
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus kendaraan ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    // Konversi indeks baris agar aman dari filter pencarian
                    int modelRow = table.convertRowIndexToModel(row);
                    int id = Integer.parseInt(model.getValueAt(modelRow, 6).toString());
                    
                    // Panggil controller untuk mengeksekusi hapus data di database
                    controller.hapusKendaraan(id);
                    
                    // Refresh tampilan tabel agar data yang dihapus langsung hilang
                    controller.loadTable(model);
                    
                    clearForm();
                    
                    JOptionPane.showMessageDialog(this, "Data kendaraan berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 3. EDIT DATA
        btnSave.addActionListener(e -> {
            // Ambil baris tabel yang sedang dipilih/diklik
            int row = table.getSelectedRow();
            
            // Validasi
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data di tabel terlebih dahulu yang ingin diubah!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                // Konversi indeks baris agar aman dari filter pencarian
                int modelRow = table.convertRowIndexToModel(row);
                
                // Ambil ID
                int id = Integer.parseInt(model.getValueAt(modelRow, 6).toString());
                
                // Ambil data baru dari field form yang sudah diubah
                String nama = tfNama.getText().trim();
                String jenis = cbJenis.getSelectedItem().toString();
                
                if (nama.isEmpty() || tfHarga12.getText().trim().isEmpty() || tfHarga24.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double harga12 = Double.parseDouble(tfHarga12.getText().trim());
                double harga24 = Double.parseDouble(tfHarga24.getText().trim());

                // Kirim ke controller untuk update ke DB (Menggunakan ID asli)
                controller.updateKendaraan(id, nama, jenis, harga12, harga24);
                
                // Refresh tabel agar data di layar langsung terupdate
                controller.loadTable(model);
                
                clearForm();
                
                JOptionPane.showMessageDialog(this, "Data kendaraan berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga harus berupa angka numerik!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //4. REFRESH / RESET FORM
        btnRefresh.addActionListener(e -> {
            controller.loadTable(model);
            clearForm();
        });

        // 5. KLIK BARIS TABEL (Pindah ke Form)
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int modelRow = table.convertRowIndexToModel(row);
                tfNama.setText(model.getValueAt(modelRow, 1).toString());
                cbJenis.setSelectedItem(model.getValueAt(modelRow, 2).toString());
                
                // Ambil string berformat Rp dari tabel
                String harga12Str = model.getValueAt(modelRow, 3).toString();
                String harga24Str = model.getValueAt(modelRow, 4).toString();
                
                // Bersihkan
                String harga12Clean = harga12Str.replaceAll("[^0-9]", "");
                String harga24Clean = harga24Str.replaceAll("[^0-9]", "");
                
                tfHarga12.setText(harga12Clean);
                tfHarga24.setText(harga24Clean);
            }
        });
    }

    void clearForm() {
        tfNama.setText("");
        tfHarga12.setText("");
        tfHarga24.setText("");
        tfCari.setText("");
        cbJenis.setSelectedIndex(0);
        table.clearSelection();
    }
}