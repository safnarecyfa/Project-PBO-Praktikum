/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.PenyewaanDAO;
import model.PelangganDAO;
import model.KendaraanDAO;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class PenyewaanController {
    PenyewaanDAO dao = new PenyewaanDAO();
    private KendaraanDAO kendaraanDao = new KendaraanDAO();
    private HashMap<String, Integer> kendaraanMap = new HashMap<>();
    public HashMap<String, Integer> getKendaraanMap() {
        return kendaraanMap;
    }

    // 1. Tampilkan Data ke Tabel (Diselaraskan dengan struktur tabel transaksi kamu)
    public void tampilkanData(DefaultTableModel model) {
        try {
            model.setRowCount(0); // Bersihkan tabel
            ResultSet rs = dao.getDataPenyewaan();
            int nomorUrut = 1; // 1. Inisialisasi nomor urut

            while (rs != null && rs.next()) {
                model.addRow(new Object[]{
                    nomorUrut++,                    // Kolom 0: Nomor urut (1, 2, 3...)
                    rs.getString("nama_pelanggan"), // Kolom 1
                    rs.getInt("kendaraan_id"),      // Kolom 2
                    rs.getDate("tanggal_sewa"),     // Kolom 3
                    rs.getDate("tanggal_kembali"),  // Kolom 4
                    rs.getString("durasi_sewa"),    // Kolom 5
                    rs.getDouble("total_biaya"),    // Kolom 6
                    rs.getDouble("denda"),          // Kolom 7
                    rs.getString("status"),         // Kolom 8
                    rs.getInt("id")                 // Kolom 9: ID Asli (Akan disembunyikan di View)
                });
            }
        } catch (Exception e) {
            System.out.println("Error Tampilkan Data: " + e);
            e.printStackTrace();
        }
    }

    // 2. Fungsi Simpan Transaksi Baru (Mulai Sewa)
    public boolean simpan(String namaPelanggan, int kId, Date tglSewa, Date tglKembali, String durasi, double total) {
        boolean isTransaksiSaved = dao.tambahTransaksi(namaPelanggan, kId, tglSewa, tglKembali, durasi, total);
        if (isTransaksiSaved) {
            kendaraanDao.ubahStatus(kId, "Disewa"); // Otomatis ubah status kendaraan jadi Disewa
        }
        return isTransaksiSaved;
    }

    // 3. Fungsi Pengembalian Kendaraan (Hitung Denda Otomatis Jika Terlambat)
    public boolean kembalikanKendaraan(int idTransaksi, int idKendaraan, Date tglKembaliSeharusnya, Date tglKembaliAktual) {
        try {
            double denda = 0;
            
            // Hitung selisih hari antara tanggal kembali aktual dengan tanggal seharusnya
            long diffInMillies = tglKembaliAktual.getTime() - tglKembaliSeharusnya.getTime();
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            
            // Jika diffInDays > 0 berarti ada keterlambatan pengembalian
            if (diffInDays > 0) {
                // Cari jenis kendaraan untuk menentukan tarif denda
                ResultSet rs = kendaraanDao.searchKendaraan(String.valueOf(idKendaraan));
                String jenis = "Mobil";
                if (rs != null && rs.next()) {
                    jenis = rs.getString("jenis"); 
                }
                
                // Aturan Denda: Mobil +50000/hari, Motor +25000/hari
                double tarifDendaPerHari = jenis.equalsIgnoreCase("Mobil") ? 50000 : 25000;
                denda = diffInDays * tarifDendaPerHari;
            }

            // Ambil data transaksi lama untuk tahu total biaya awal
            ResultSet rsTrans = dao.cariTransaksi(String.valueOf(idTransaksi));
            double biayaAwal = 0;
            if (rsTrans != null && rsTrans.next()) {
                biayaAwal = rsTrans.getDouble("total_biaya");
            }

            double totalBiayaAkhir = biayaAwal + denda;

            // Eksekusi pembaruan data transaksi ke database
            boolean isReturnSuccess = dao.prosesPengembalian(idTransaksi, denda, totalBiayaAkhir);
            if (isReturnSuccess) {
                kendaraanDao.ubahStatus(idKendaraan, "Tersedia"); // Kendaraan kembali Tersedia
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error pada proses pengembalian: " + e);
        }
        return false;
    }

    // 4. Hapus Transaksi (Jika transaksi batal / dihapus, kembalikan status kendaraan)
    public boolean hapusDanUpdateKendaraan(int idTransaksi, int idKendaraan) {
        // 1. Hapus transaksi dari database
        boolean success = dao.hapusTransaksi(idTransaksi);

        // 2. Jika berhasil, ubah status kendaraan menjadi "Tersedia"
        if (success) {
            kendaraanDao.ubahStatus(idKendaraan, "Tersedia");
        }
        return success;
    }
    
    // 5. Mencari harga dasar kendaraan
    public double getHargaDasar(int idKendaraan) {
        try {
            java.sql.ResultSet rs = dao.getDetailKendaraan(idKendaraan);
            if (rs != null && rs.next()) {
                return rs.getDouble("harga_24_jam");
            }
        } catch (Exception e) {
            System.out.println("Error ambil harga dasar: " + e);
        }
        return 0;
    }

    // 6. Hitung Estimasi Harga Awal di Form Sewa (Termasuk Kustom Hari)
    public double hitungHarga(int idKendaraan, String durasi, int hari) {
        if (idKendaraan == -1) return 0;
        try {
            java.sql.ResultSet rs = dao.getDetailKendaraan(idKendaraan);
            if (rs != null && rs.next()) {
                if (durasi.equals("12 Jam")) return rs.getDouble("harga_12_jam");
                if (durasi.equals("24 Jam")) return rs.getDouble("harga_24_jam");
                if (durasi.equals("Kustom (Hari)")) return rs.getDouble("harga_24_jam") * hari;
            }
        } catch (Exception e) {
            System.out.println("Error hitung: " + e);
        }
        return 0;
    }

    // 7. Load Dropdown Kendaraan & Pelanggan
    public void loadComboKendaraanYgTersedia(JComboBox<String> comboBox) {
        try {
            comboBox.removeAllItems();
            comboBox.addItem("-- Pilih Kendaraan --");
            kendaraanMap.clear();
            
            ResultSet rs = dao.getKendaraanTersedia();
            while (rs != null && rs.next()) {
                String itemDisplay = rs.getString("nama");
                int id = rs.getInt("id");
                comboBox.addItem(itemDisplay);
                kendaraanMap.put(itemDisplay, id);
            }
        } catch (Exception e) {
            System.out.println("Error Load Combo Kendaraan: " + e);
        }
    }

    public int getIdKendaraanSelected(String namaKendaraan) {
        if (namaKendaraan == null || namaKendaraan.equals("-- Pilih Kendaraan --")) return -1;
        return kendaraanMap.getOrDefault(namaKendaraan, -1);
    }
    
    public void isiDropdownPelanggan(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("-- Pilih Pelanggan --");
        try {
            ResultSet rs = new PelangganDAO().getSemuaPelanggan();
            while (rs != null && rs.next()) {
                comboBox.addItem(rs.getString("nama"));
            }
        } catch (Exception e) {
            System.out.println("Error Isi Dropdown Pelanggan: " + e);
        }
    }
    
    public boolean prosesPengembalian(int idTransaksi, int idKendaraan, Date tglKembaliSeharusnya, Date tglKembaliAktual) {
        long denda = 0;

        // Konversi ke LocalDate untuk menghitung selisih hari
        LocalDate seharusnya = tglKembaliSeharusnya.toLocalDate();
        LocalDate aktual = tglKembaliAktual.toLocalDate();

        if (aktual.isAfter(seharusnya)) {
            long telat = ChronoUnit.DAYS.between(seharusnya, aktual);
            denda = telat * 50000; // 50rb per hari
        }

        // Update status transaksi dan denda
        boolean success = dao.updateStatusTransaksi(idTransaksi, "Selesai", (double)denda);

        if (success) {
            kendaraanDao.ubahStatus(idKendaraan, "Tersedia");
        }
        return success;
    }
}