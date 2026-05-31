/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */
import connector.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KendaraanDAO {

    Connection conn = Koneksi.getConnection();

    // CREATE
    public void tambahKendaraan(String nama, String jenis, double harga12, double harga24) {
        try {
            String sql = "INSERT INTO kendaraan (nama, jenis, harga_12_jam, harga_24_jam, status) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, jenis);
            ps.setDouble(3, harga12);
            ps.setDouble(4, harga24);
            ps.setString(5, "Tersedia");

            ps.executeUpdate();
            System.out.println("Data berhasil ditambah");
        } catch (Exception e) {
            System.out.println("Error Tambah: " + e);
        }
    }

    // READ
    public ResultSet getDataKendaraan() {
        try {
            String sql = "SELECT * FROM kendaraan";
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error Get Data: " + e);
        }
        return null;
    }

    // UPDATE
    public void updateKendaraan(int id, String nama, String jenis, double harga12, double harga24) {
        try {
            // SINKRONISASI: Pastikan harga_12 dan harga_24 sesuai dengan nama kolom di phpMyAdmin kamu
            String sql = "UPDATE kendaraan SET nama=?, jenis=?, harga_12_jam=?, harga_24_jam=? WHERE id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, jenis);
            ps.setDouble(3, harga12);
            ps.setDouble(4, harga24);
            ps.setInt(5, id); // id digunakan sebagai patokan WHERE

            ps.executeUpdate();
            System.out.println("Data berhasil diubah di database!");
        } catch (Exception e) {
            System.out.println("Error Update di DAO: " + e);
        }
    }

    // DELETE
    public void hapusKendaraan(int id) {
        try {
            String sql = "DELETE FROM kendaraan WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Data berhasil dihapus dari database");
        } catch (Exception e) {
            System.out.println("Error Hapus di DAO: " + e);
        }
    }

    public ResultSet searchKendaraan(String keyword) {
        try {
            String sql = "SELECT * FROM kendaraan WHERE nama LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            return ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error Search: " + e);
        }
        return null;
    }

    public void ubahStatus(int id, String status) {
        try {
            String sql = "UPDATE kendaraan SET status=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Status kendaraan ID " + id + " berhasil diubah menjadi: " + status);
        } catch (Exception e) {
            System.out.println("Error Ubah Status di DAO: " + e);
        }
    }

    public int getTotalKendaraan() {
        try {
            String sql = "SELECT COUNT(*) AS total FROM kendaraan";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Error Count Kendaraan: " + e);
        }
        return 0;
    }
}
