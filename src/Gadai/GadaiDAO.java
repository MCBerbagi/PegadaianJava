/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gadai;

import Nasabah.Nasabah;
import barang.Barang;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jneferboy
 */
public interface GadaiDAO {

    public List<Nasabah> loadNasabah();

    public List<Barang> loadBarang();

    public List<Gadai> loadGadai();

    public void insertGadai(Gadai item);

    public void deleteGadai(int id);

    public void Ditebus(Object o);

    public DefaultTableModel viewGadai();

    public DefaultTableModel viewdataGadai();

    public DefaultTableModel viewtebusan();

    public DefaultTableModel search(String key);

    public DefaultTableModel detail(int id);

    public void update(Object o);
    
//    public void cetak(String file);

}
