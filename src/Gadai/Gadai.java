/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gadai;

import Nasabah.Nasabah;
import barang.Barang;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import petugas.Petugas;

/**
 *
 * @author jneferboy
 */
@DatabaseTable(tableName = "gadai")
public class Gadai {

    @DatabaseField(generatedId = true)
    private int No_gadai;
    @DatabaseField
    private String Tgl_gadai;
    @DatabaseField
    private String Jatuh_tempo;
    @DatabaseField
    private Double Jumlah_pinjaman;
    @DatabaseField
    private String Terbilang;
    @DatabaseField
    private Double Jumlah_tebusan;
    @DatabaseField
    private Double Denda;
    @DatabaseField
    private String Tgl_tebusan;
    @DatabaseField
    private String Keterangan;
    @DatabaseField
    private Double Total_tebusan;

    @DatabaseField(foreign = true, foreignAutoRefresh = true,foreignColumnName = "Kode_barang")
    private Barang barang;

    @DatabaseField(foreign = true, foreignAutoRefresh = true,foreignColumnName = "Nip")
    private Petugas petugas;

    //class ini (pembelian) dimiliki oleh suplier
    @DatabaseField(foreign = true, foreignAutoRefresh = true,foreignColumnName = "KTP")
    private Nasabah nasabah;

    public Petugas getPetugas() {
        return petugas;
    }

    public void setPetugas(Petugas petugas) {
        this.petugas = petugas;
    }

    public Nasabah getNasabah() {
        return nasabah;
    }

    public void setNasabah(Nasabah nasabah) {
        this.nasabah = nasabah;
    }

    public int getNo_gadai() {
        return No_gadai;
    }

    public void setNo_gadai(int No_gadai) {
        this.No_gadai = No_gadai;
    }

    public String getTgl_gadai() {
        return Tgl_gadai;
    }

    public void setTgl_gadai(String Tgl_gadai) {
        this.Tgl_gadai = Tgl_gadai;
    }

    public String getJatuh_tempo() {
        return Jatuh_tempo;
    }

    public void setJatuh_tempo(String Jatuh_tempo) {
        this.Jatuh_tempo = Jatuh_tempo;
    }

    public Double getJumlah_pinjaman() {
        return Jumlah_pinjaman;
    }

    public void setJumlah_pinjaman(Double Jumlah_pinjaman) {
        this.Jumlah_pinjaman = Jumlah_pinjaman;
    }

    public String getTerbilang() {
        return Terbilang;
    }

    public void setTerbilang(String Terbilang) {
        this.Terbilang = Terbilang;
    }

    public Double getJumlah_tebusan() {
        return Jumlah_tebusan;
    }

    public void setJumlah_tebusan(Double Jumlah_tebusan) {
        this.Jumlah_tebusan = Jumlah_tebusan;
    }

    public Double getDenda() {
        return Denda;
    }

    public void setDenda(Double Denda) {
        this.Denda = Denda;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public String getTgl_tebusan() {
        return Tgl_tebusan;
    }

    public void setTgl_tebusan(String Tgl_tebusan) {
        this.Tgl_tebusan = Tgl_tebusan;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String Keterangan) {
        this.Keterangan = Keterangan;
    }

    public Double getTotal_tebusan() {
        return Total_tebusan;
    }

    public void setTotal_tebusan(Double Total_tebusan) {
        this.Total_tebusan = Total_tebusan;
    }

}
