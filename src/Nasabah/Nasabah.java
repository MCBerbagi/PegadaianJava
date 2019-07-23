/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nasabah;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import Gadai.Gadai;

/**
 *
 * @author jneferboy
 */
//deklarasai nama tabel
@DatabaseTable(tableName = "nasabah")
public class Nasabah {
    //deklarasi kolom tabel
    @DatabaseField(generatedId = true,allowGeneratedIdInsert = true)
    protected int Ktp;
    
    @DatabaseField
    private String Nama;
    
     @DatabaseField
    private String Hp;
    
    @DatabaseField
    private String Alamat;
    
   
    
    //user memiliki banyak pembelian
    @ForeignCollectionField
    ForeignCollection<Gadai> gadai;

    public int getKtp() {
        return Ktp;
    }

    public void setKtp(int Ktp) {
        this.Ktp = Ktp;
    }

   

    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String Alamat) {
        this.Alamat = Alamat;
    }

    public String getHp() {
        return Hp;
    }

    public void setHp(String Hp) {
        this.Hp = Hp;
    }

    public ForeignCollection<Gadai> getGadai() {
        return gadai;
    }

    public void setGadai(ForeignCollection<Gadai> gadai) {
        this.gadai = gadai;
    }

   


    
    
    

}
