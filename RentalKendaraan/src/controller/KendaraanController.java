/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Asus
 */
import model.KendaraanDAO;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.text.DecimalFormat; // Import untuk format angka
import java.text.DecimalFormatSymbols; // Import untuk custom simbol Rp

public class KendaraanController {
    KendaraanDAO dao = new KendaraanDAO();

    public void tambahKendaraan(String nama, String jenis, double harga12, double harga24){
        dao.tambahKendaraan(nama, jenis, harga12, harga24);
    }

    public void hapusKendaraan(int id){
        dao.hapusKendaraan(id);
    }

    public void updateKendaraan(int id, String nama, String jenis, double harga12, double harga24){
        dao.updateKendaraan(id, nama, jenis, harga12, harga24);
    }

    public void loadTable(DefaultTableModel model){
        try {
            model.setRowCount(0);
            ResultSet rs = dao.getDataKendaraan();

            // Format Rupiah
            DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setCurrencySymbol("Rp ");
            dfs.setMonetaryDecimalSeparator(',');
            dfs.setGroupingSeparator('.');
            df.setDecimalFormatSymbols(dfs);
            df.setMaximumFractionDigits(0);

            int no = 1; 

            while(rs != null && rs.next()){
                double harga12Raw = rs.getDouble("harga_12_jam"); 
                double harga24Raw = rs.getDouble("harga_24_jam"); 

                String harga12Formatted = df.format(harga12Raw);
                String harga24Formatted = df.format(harga24Raw);

                Object[] row = {
                        no++,                          // Indeks 0 (No Urut)
                        rs.getString("nama"),          // Indeks 1
                        rs.getString("jenis"),         // Indeks 2
                        harga12Formatted,              // Indeks 3
                        harga24Formatted,              // Indeks 4
                        rs.getString("status"),        // Indeks 5
                        rs.getInt("id")                // Indeks 6 (ID ASLI DATABASE)
                };
                model.addRow(row);
            }
        } catch(Exception e) {
            System.out.println("Error Load Table: " + e);
            e.printStackTrace();
        }
    }
}