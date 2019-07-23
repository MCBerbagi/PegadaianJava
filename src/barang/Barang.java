/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barang;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import Gadai.Gadai;

/**
 *
 * @author jneferboy
 */
@DatabaseTable(tableName = "barang")
public class Barang {

    @DatabaseField(generatedId = true)
    private int Kode_barang;
    @DatabaseField
    private String Nama_barang;
    @DatabaseField
    private String Type;
    @DatabaseField
    private String Warna;

    @ForeignCollectionField
    private ForeignCollection<Gadai> gadai;

    public int getKode_barang() {
        return Kode_barang;
    }

    public void setKode_barang(int Kode_barang) {
        this.Kode_barang = Kode_barang;
    }

    public String getNama_barang() {
        return Nama_barang;
    }

    public void setNama_barang(String Nama_barang) {
        this.Nama_barang = Nama_barang;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getWarna() {
        return Warna;
    }

    public void setWarna(String Warna) {
        this.Warna = Warna;
    }

    public ForeignCollection<Gadai> getGadai() {
        return gadai;
    }

    public void setGadai(ForeignCollection<Gadai> gadai) {
        this.gadai = gadai;
    }

}
