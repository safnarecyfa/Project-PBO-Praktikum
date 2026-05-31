/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connector.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;

/**
 *
 * @author putri
 */

public class PenyewaanDAO {

    Connection conn = Koneksi.getConnection();

    // Ambil semua data transaksi untuk tabel
    public ResultSet getDataPenyewaan() {
        try {
            String sql = "SELECT * FROM transaksi";
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error Get Data Penyewaan: " + e);
            return null;
        }
    }

    // Ambil daftar kendaraan yang berstatus Tersedia
    public ResultSet getKendaraanTersedia() {
        try {
            String sql = "SELECT id, nama FROM kendaraan WHERE status = 'Tersedia'";
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error Get Kendaraan Tersedia: " + e);
            return null;
        }
    }

    // Ambil detail harga kendaraan berdasarkan ID
    public ResultSet getDetailKendaraan(int id) {
        try {
            String sql = "SELECT * FROM kendaraan WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error Get Detail Kendaraan: " + e);
            return null;
        }
    }

    //TAMBAH
    public boolean tambahTransaksi(String nama, int kId, Date tglSewa, Date tglKembali, String durasi, double total) {
        try {
            String sql = "INSERT INTO transaksi (nama_pelanggan, kendaraan_id, tanggal_sewa, tanggal_kembali, durasi_sewa, total_biaya, denda, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setInt(2, kId);
            ps.setDate(3, tglSewa);
            ps.setDate(4, tglKembali);
            ps.setString(5, durasi);
            ps.setDouble(6, total);
            ps.setDouble(7, 0);
            ps.setString(8, "Berjalan");

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error DAO: " + e.getMessage());
            return false;
        }
    }

    // UPDATE Transaksi Saat Pengembalian 
    public boolean prosesPengembalian(int idTransaksi, double denda, double totalBiayaAkhir) {
        try {
            String sql = "UPDATE transaksi SET denda = ?, total_biaya = ?, status = 'Selesai' WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, denda);
            ps.setDouble(2, totalBiayaAkhir);
            ps.setInt(3, idTransaksi);

            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            System.out.println("Error Proses Pengembalian: " + e);
            return false;
        }
    }

    // Cari transaksi berdasarkan ID atau nama pelanggan
    public ResultSet cariTransaksi(String keyword) {
        try {
            String sql = "SELECT * FROM transaksi WHERE id = ? OR nama_pelanggan LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, keyword);
            ps.setString(2, "%" + keyword + "%");
            return ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error Cari Transaksi: " + e);
            return null;
        }
    }

    // Hapus Transaksi
    public boolean hapusTransaksi(int id) {
        try {
            String sql = "DELETE FROM transaksi WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error Hapus: " + e.getMessage());
            return false;
        }
    }

    public int getTotalTransaksi() {
        try {
            String sql = "SELECT COUNT(*) AS total FROM transaksi";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Error Count Transaksi: " + e);
        }
        return 0;
    }
    
    public boolean updateStatusTransaksi(int id, String status, double denda) {
        try {
            String sql = "UPDATE transaksi SET status = ?, denda = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setDouble(2, denda);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean simpanTransaksi(String pelanggan, int idKendaraan, Date s, Date k, String d, double t, String status) {
        try {
            String sql = "INSERT INTO transaksi (nama_pelanggan, kendaraan_id, tanggal_sewa, tanggal_kembali, durasi_sewa, total_biaya, status) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pelanggan);
            ps.setInt(2, idKendaraan);
            ps.setDate(3, s);
            ps.setDate(4, k);
            ps.setString(5, d);
            ps.setDouble(6, t);
            ps.setString(7, status);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error Simpan: " + e.getMessage());
            return false;
        }
    }
}
