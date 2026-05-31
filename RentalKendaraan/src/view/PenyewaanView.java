/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Asus
 */
import controller.PenyewaanController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;

public class PenyewaanView extends JPanel {

    // Komponen Form
    JComboBox<String> cbPelanggan = new JComboBox<>();
    JComboBox<String> cbKendaraan = new JComboBox<>();
    JComboBox<String> cbDurasiSewa = new JComboBox<>(new String[]{"-- Pilih Durasi --", "12 Jam", "24 Jam", "Kustom (Hari)"});
    JTextField tfJumlahHari = new JTextField();
    JTextField tfTglSewa = new JTextField();
    JTextField tfTglKembali = new JTextField();
    JTextField tfTotalHarga = new JTextField();

    JButton btnTambah = new JButton("Tambah");
    JButton btnKembali = new JButton("Pengembalian");
    JButton btnHapus = new JButton("Hapus");
    JButton btnRefresh = new JButton("Refresh");

    JTable table = new JTable();
    DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Pelanggan", "ID Kend", "Tgl Sewa", "Tgl Kembali", "Durasi", "Total", "Denda", "Status", "HiddenID"}, 0
    );

    PenyewaanController controller = new PenyewaanController();

    public PenyewaanView() {
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
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(), "Form Penyewaan", 0, 0, new Font("Dialog", Font.BOLD, 14)
        ));

        Dimension fieldSize = new Dimension(260, 30);
        Font labelFont = new Font("Dialog", Font.BOLD, 12);

        // Helper untuk rapiin field
        addFormField(formPanel, "Pilih Pelanggan", cbPelanggan, fieldSize, labelFont);
        addFormField(formPanel, "Pilih Kendaraan", cbKendaraan, fieldSize, labelFont);
        addFormField(formPanel, "Durasi Sewa", cbDurasiSewa, fieldSize, labelFont);
        addFormField(formPanel, "Jumlah Hari", tfJumlahHari, fieldSize, labelFont);
        addFormField(formPanel, "Tgl Sewa (YYYY-MM-DD)", tfTglSewa, fieldSize, labelFont);
        addFormField(formPanel, "Tgl Kembali (YYYY-MM-DD)", tfTglKembali, fieldSize, labelFont);
        addFormField(formPanel, "Total Harga (Rp)", tfTotalHarga, fieldSize, labelFont);
        tfTotalHarga.setEditable(false);

        // Panel Tombol
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        btnPanel.setBackground(new Color(240, 242, 245));
        btnPanel.setMaximumSize(new Dimension(260, 80));
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnPanel.add(btnTambah);
        btnPanel.add(btnKembali);
        btnPanel.add(btnHapus);
        btnPanel.add(btnRefresh);

        // Warna tombol
        styleButton(btnTambah, new Color(40, 167, 69));
        styleButton(btnKembali, new Color(224, 168, 0));
        styleButton(btnHapus, new Color(220, 53, 69));
        styleButton(btnRefresh, new Color(23, 162, 184));

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(btnPanel);
        add(formPanel, BorderLayout.WEST);

        // ==========================================
        // 2. PANEL KANAN: TABEL
        // ==========================================
        table.setModel(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(43, 61, 81));
        table.getTableHeader().setForeground(Color.WHITE);
        
        table.getColumnModel().getColumn(9).setMinWidth(0);
        table.getColumnModel().getColumn(9).setMaxWidth(0);
        table.getColumnModel().getColumn(9).setWidth(0);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(tableScroll, BorderLayout.CENTER);

        // Tambahkan di bawah inisialisasi table
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                cbPelanggan.setSelectedItem(table.getValueAt(row, 1).toString());
                cbKendaraan.setSelectedItem(table.getValueAt(row, 2).toString());
                
                String durasi = table.getValueAt(row, 5).toString();
                if(durasi.contains("Hari")) {
                    cbDurasiSewa.setSelectedItem("Kustom (Hari)");
                    tfJumlahHari.setText(durasi.replace(" Hari", ""));
                } else {
                    cbDurasiSewa.setSelectedItem(durasi);
                }
                
                tfTglSewa.setText(table.getValueAt(row, 3).toString());
                tfTglKembali.setText(table.getValueAt(row, 4).toString());
                tfTotalHarga.setText(table.getValueAt(row, 6).toString());
            }
        });

        // Listener
        btnTambah.addActionListener(e -> aksiTambah());
        btnRefresh.addActionListener(e -> initData());
        btnHapus.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
                return;
            }

            int idTransaksi = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
            int idKendaraan = Integer.parseInt(table.getValueAt(selectedRow, 2).toString()); 

            int confirm = JOptionPane.showConfirmDialog(this, "Hapus data dan ubah status kendaraan jadi Tersedia?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Panggil method hapus baru yang menyertakan idKendaraan
                if (controller.hapusDanUpdateKendaraan(idTransaksi, idKendaraan)) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus dan kendaraan tersedia kembali!");
                    initData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menghapus data.");
                }
            }
        });
        btnKembali.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data transaksi di tabel!");
                return;
            }

            // Ambil ID dan data dari tabel
            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            int idK = Integer.parseInt(table.getValueAt(row, 2).toString());
            double totalAwal = Double.parseDouble(table.getValueAt(row, 6).toString());
            Date tglSeharusnya = Date.valueOf(table.getValueAt(row, 4).toString());

            String inputTgl = JOptionPane.showInputDialog(this, "Masukkan Tanggal Kembali (YYYY-MM-DD):");
            if (inputTgl != null) {
                try {
                    Date tglAktual = Date.valueOf(inputTgl);

                    // Hitung selisih hari
                    long telat = java.time.temporal.ChronoUnit.DAYS.between(tglSeharusnya.toLocalDate(), tglAktual.toLocalDate());
                    double denda = (telat > 0) ? telat * 50000 : 0;
                    double totalBayar = totalAwal + denda;

                    // Tampilkan rincian sebelum proses
                    String pesan = "Pengembalian terlambat: " + (telat > 0 ? telat : 0) + " hari\n" +
                                   "Terkena Denda: Rp " + String.format("%.0f", denda) + "\n" +
                                   "Total biaya yang harus dibayarkan: Rp " + String.format("%.0f", totalBayar);

                    int confirm = JOptionPane.showConfirmDialog(this, pesan + "\n\nLanjutkan proses pengembalian?", "Konfirmasi Pembayaran", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        if (controller.prosesPengembalian(id, idK, tglSeharusnya, tglAktual)) {
                            JOptionPane.showMessageDialog(this, "Data pengembalian berhasil disimpan!");
                            initData();
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Format tanggal salah atau data tidak valid!");
                }
            }
        });
        
        tfJumlahHari.addKeyListener(new java.awt.event.KeyAdapter() {
        @Override 
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume(); // Tolak input selain angka
                }
            }
        });

        initData();
    }

    private void addFormField(JPanel p, String labelText, JComponent comp, Dimension size, Font font) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        comp.setMaximumSize(size);
        comp.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(label);
        p.add(Box.createVerticalStrut(5));
        p.add(comp);
        p.add(Box.createVerticalStrut(10));
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
    }

    private void updateHarga() {
        try {
            // Ambil ID kendaraan
            int idK = controller.getIdKendaraanSelected(cbKendaraan.getSelectedItem().toString());
            String durasi = cbDurasiSewa.getSelectedItem().toString();

            // Ambil data harga dasar dari controller
            double hargaDasar = controller.getHargaDasar(idK); 

            if (idK == -1 || durasi.equals("-- Pilih Durasi --")) return;

            double total = 0;
            if (durasi.equals("12 Jam")) {
                tfJumlahHari.setText("1");
                tfJumlahHari.setEditable(false);
                total = controller.hitungHarga(idK, "12 Jam", 1);
            } else if (durasi.equals("24 Jam")) {
                tfJumlahHari.setText("1");
                tfJumlahHari.setEditable(false);
                total = controller.hitungHarga(idK, "24 Jam", 1);
            } else if (durasi.equals("Kustom (Hari)")) {
                tfJumlahHari.setEditable(true);
                int hari = Integer.parseInt(tfJumlahHari.getText().isEmpty() ? "0" : tfJumlahHari.getText());
                // Logika: Harga Dasar x Jumlah Hari
                total = hargaDasar * hari; 
            }

            tfTotalHarga.setText(String.format("%.0f", total));
        } catch (Exception e) {
            tfTotalHarga.setText("0");
        }
    }

    private void aksiTambah() {
        try {
            // 1. Deklarasikan dan ambil nilai dari komponen
            String p = cbPelanggan.getSelectedItem().toString();
            int kId = controller.getIdKendaraanSelected(cbKendaraan.getSelectedItem().toString());
            Date s = Date.valueOf(tfTglSewa.getText());
            Date k = Date.valueOf(tfTglKembali.getText());

            // 2. Logika format durasi (seperti yang kita bahas tadi)
            String durasiPilihan = cbDurasiSewa.getSelectedItem().toString();
            String d = durasiPilihan.equals("Kustom (Hari)") ? tfJumlahHari.getText() + " Hari" : durasiPilihan;

            double t = Double.parseDouble(tfTotalHarga.getText());

            // 3. SEKARANG panggil controller.simpan, garis merah akan hilang
            if (controller.simpan(p, kId, s, k, d, t)) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
                initData();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void initData() {
        model.setRowCount(0);
        controller.tampilkanData(model);
        controller.isiDropdownPelanggan(cbPelanggan);
        controller.loadComboKendaraanYgTersedia(cbKendaraan);
        tfJumlahHari.setText("0");
        tfTotalHarga.setText("0");
        tfTglSewa.setText("");
        tfTglKembali.setText("");
        
        cbDurasiSewa.addActionListener(e -> updateHarga());

        cbKendaraan.addActionListener(e -> updateHarga());

        tfJumlahHari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateHarga(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateHarga(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateHarga(); }
        });
    }
}
