/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */
abstract public class Kendaraan {
    protected int id;
    protected String nama;
    protected double hargaSewa;
    protected String status;

    public Kendaraan() {
    }

    public Kendaraan(int id, String nama, double hargaSewa, String status) {
        this.id = id;
        this.nama = nama;
        this.hargaSewa = hargaSewa;
        this.status = status;
    }

    // ABSTRACT
    public abstract double hitungBiaya(int hari);

    // ENCAPSULATION
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    public void setHargaSewa(double hargaSewa) {
        this.hargaSewa = hargaSewa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
