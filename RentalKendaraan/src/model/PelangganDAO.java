/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connector.Koneksi;
import java.sql.*;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author putri
 */
public class PelangganDAO {

    Connection conn = Koneksi.getConnection();

    public boolean simpanPelanggan(String nama, String nohp, String alamat) {
        try {
            String sql = "INSERT INTO pelanggan (nama, no_hp, alamat) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, nohp);
            ps.setString(3, alamat);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error Simpan Pelanggan: " + e);
            return false;
        }
    }

    public ResultSet getSemuaPelanggan() {
        try {
            return conn.createStatement().executeQuery("SELECT * FROM pelanggan ORDER BY id DESC");
        } catch (Exception e) {
            System.out.println("Error Get Pelanggan: " + e);
            return null;
        }
    }

    public boolean updatePelanggan(int id, String nama, String nohp, String alamat) {
        try {
            String sql = "UPDATE pelanggan SET nama=?, no_hp=?, alamat=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, nohp);
            ps.setString(3, alamat);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Update!\nPesan Error: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error Update Pelanggan: " + e);
            return false;
        }
    }

    public boolean hapusPelanggan(int id) {
        try {
            String sql = "DELETE FROM pelanggan WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Hapus!\nPesan Error: " + e.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error Hapus Pelanggan: " + e);
            return false;
        }
    }

    public ResultSet cariPelanggan(String keyword) {
        try {
            String sql = "SELECT * FROM pelanggan WHERE nama LIKE ? OR alamat LIKE ? ORDER BY id DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            return ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error Cari Pelanggan: " + e);
            return null;
        }
    }

    public int getTotalPelanggan() {
        try {
            String sql = "SELECT COUNT(*) AS total FROM pelanggan";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Error Count Pelanggan: " + e);
        }
        return 0;
    }
}
