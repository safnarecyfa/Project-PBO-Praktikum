/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */
public class Motor extends Kendaraan {
    public Motor( int id, String nama, double hargaSewa, String status) {
        super(id, nama, hargaSewa, status);
    }

    @Override
    public double hitungBiaya(int hari) {
        return hargaSewa * hari;
    }
}