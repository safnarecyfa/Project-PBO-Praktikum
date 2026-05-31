/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.PelangganDAO;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

/**
 *
 * @author putri
 */
public class PelangganController {
    PelangganDAO dao = new PelangganDAO();

    public void tampilkanData(DefaultTableModel model) {
        model.setRowCount(0); // Bersihkan tabel
        try {
            ResultSet rs = dao.getSemuaPelanggan(); // Asumsi method di DAO
            int nomorUrut = 1; // Inisialisasi nomor urut

            while (rs.next()) {
                Object[] row = {
                    nomorUrut++,               // Kolom 0: Nomor urut (1, 2, 3...)
                    rs.getString("nama"),      // Kolom 1
                    rs.getString("no_hp"),     // Kolom 2
                    rs.getString("alamat"),    // Kolom 3
                    rs.getInt("id")            // Kolom 4: ID Asli (Sembunyi)
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean simpan(String nama, String nohp, String alamat) {
        return dao.simpanPelanggan(nama, nohp, alamat);
    }

    public boolean ubah(int id, String nama, String nohp, String alamat) {
        return dao.updatePelanggan(id, nama, nohp, alamat);
    }

    public boolean hapus(int id) {
        return dao.hapusPelanggan(id);
    }
    
    public void cari(DefaultTableModel tableModel, String keyword) {
        try {
            tableModel.setRowCount(0);
            ResultSet rs = dao.cariPelanggan(keyword);
            while (rs != null && rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("no_hp"),
                    rs.getString("alamat")
                });
            }
        } catch (Exception e) {
            System.out.println("Error Controller Cari: " + e);
        }
    }
}
