/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Asus
 */
import java.awt.*;
import javax.swing.*;

import javax.swing.table.JTableHeader;

public class Style {

    // =========================
    // COLOR
    // =========================
    public static Color primary = new Color(41, 128, 185);
    public static Color success = new Color(39, 174, 96);
    public static Color warning = new Color(241, 196, 15);
    public static Color danger = new Color(231, 76, 60);
    public static Color dark = new Color(44, 62, 80);
    public static Color light = new Color(245, 246, 250);

    // =========================
    // FONT
    // =========================
    public static Font fontNormal = new Font("Segoe UI", Font.PLAIN, 14);
    public static Font fontBold = new Font("Segoe UI", Font.BOLD, 14);
    public static Font fontTitle = new Font("Segoe UI", Font.BOLD, 28);

    // =========================
    // BUTTON STYLE
    // =========================
    public static void button(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(fontBold);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        );
    }

    // =========================
    // TEXTFIELD STYLE
    // =========================
    public static void textField(JTextField tf) {
        tf.setFont(fontNormal);
        tf.setPreferredSize(new Dimension(200, 35));
    }

    // =========================
    // LABEL STYLE
    // =========================
    public static void label(JLabel label) {
        label.setFont(fontBold);
    }

    // =========================
    // TABLE STYLE
    // =========================
    public static void table(JTable table) {
        table.setRowHeight(30);
        table.setFont(fontNormal);

        JTableHeader header = table.getTableHeader();

        header.setBackground(dark);
        header.setForeground(Color.WHITE);
        header.setFont(fontBold);
    }
    
    public static Color background = new Color( 240, 242, 245);
    
    public static void roundedTextField(JTextField tf) {
        tf.setFont(fontNormal);
        tf.setPreferredSize(new Dimension(250, 45));
        tf.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        tf.setBackground(Color.WHITE);
    }
}
