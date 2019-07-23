/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petugas;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import Gadai.Gadai;

/**
 *
 * @author jneferboy
 */
@DatabaseTable(tableName = "petugas")
public class Petugas {

    @DatabaseField(generatedId = true)
    private int Nip;
    @DatabaseField(columnName = "Nama")
    private String Nama;
    @DatabaseField
    private String Alamat;
    @DatabaseField
    private String username;
    @DatabaseField
    private String Password;
    @DatabaseField
    private String Hakakses;
  

    //petugas memiliki banyak gadai
    @ForeignCollectionField
    private ForeignCollection<Gadai> gadai;

    public int getNip() {
        return Nip;
    }

    public void setNip(int Nip) {
        this.Nip = Nip;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getHakakses() {
        return Hakakses;
    }

    public void setHakakses(String Hakakses) {
        this.Hakakses = Hakakses;
    }

    

    public ForeignCollection<Gadai> getGadai() {
        return gadai;
    }

    public void setGadai(ForeignCollection<Gadai> gadai) {
        this.gadai = gadai;
    }

    

   
    

}
