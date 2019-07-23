/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gadai;

import Nasabah.Nasabah;
import Nasabah.NasabahDAOImp;
import barang.Barang;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import database.Koneksi;
import database.KoneksiReport;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author jneferboy
 */
public class GadaiDAOImp implements GadaiDAO {

    private Dao<Nasabah, Integer> daoNasabah;
    private Dao<Barang, Integer> daoBarang;
    private Dao<Gadai, Integer> daoGadai;

    private List<Gadai> items = new ArrayList<>();

    public GadaiDAOImp() {
        try {
            daoNasabah = DaoManager.createDao(Koneksi.cs(), Nasabah.class);
            daoBarang = DaoManager.createDao(Koneksi.cs(), Barang.class);
            daoGadai = DaoManager.createDao(Koneksi.cs(), Gadai.class);

        } catch (SQLException ex) {
            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Nasabah> loadNasabah() {
        //initial data
        List<Nasabah> nasabah = null;
        try {
            nasabah = daoNasabah.queryForAll();
        } catch (SQLException ex) {
            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nasabah;
    }

    public List<Gadai> loadGadai() {
        //initial data
        List<Gadai> gadai = null;
        try {
            gadai = daoGadai.queryBuilder().where().
                    like("Keterangan", "%" + "Belum Ditebus" + "%").query();
        } catch (SQLException ex) {
            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gadai;
    }

    @Override
    public List<Barang> loadBarang() {
        //initial data
        List<Barang> barangs = null;
        try {
            barangs = daoBarang.queryForAll();
        } catch (SQLException ex) {
            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return barangs;
    }

    public void addItem(Gadai item) {
        items.add(item);
    }

    @Override
    public void deleteGadai(int id) {
        try {
            daoGadai.deleteById(id);
        } catch (SQLException ex) {
            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public DefaultTableModel viewGadai() {
        DefaultTableModel dtm;
        String[] title = {"ID", "Nama Petugas", "Nama Nasabah", "Kode Barang", "Tanggal Gadai", "Jatuh Tempo", "Jumlah Pinjaman ", "Jumlah Tebusan", "Keterangan"};
        dtm = new DefaultTableModel(null, title);
        try {
            List<Gadai> gadai = daoGadai.queryBuilder().where().
                    like("Keterangan", "%" + "Belum Ditebus" + "%").query();
            for (Gadai item : gadai) {
                Object[] o = new Object[10];
                o[0] = item.getNo_gadai();
                o[1] = item.getPetugas().getNama();
                o[2] = item.getNasabah().getNama();

                o[3] = item.getBarang().getKode_barang();
                o[4] = item.getTgl_gadai();
                o[5] = item.getJatuh_tempo();
                o[6] = item.getJumlah_pinjaman();
                o[7] = item.getJumlah_tebusan();
                o[8] = item.getKeterangan();

                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        //kembalikan hasil dtm
        return dtm;
    }

    public void insertGadai(Gadai g) {
        try {
            daoGadai.create(g);
            JOptionPane.showMessageDialog(null, "Transaksi pegadainan telah tersimpan");
        } catch (SQLException ex) {
            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @Override
    public DefaultTableModel search(String key) {
        DefaultTableModel dtm;
        String[] title = {"ID", "Nama Petugas", "Nama Nasabah", "Kode Barang", "Tanggal Gadai", "Jatuh Tempo", "Jumlah Pinjaman ", "Jumlah Tebusan", "Keterangan"};
        dtm = new DefaultTableModel(null, title);

        try {
            List<Gadai> gadai = daoGadai.queryBuilder().where().
                    like("Jatuh_tempo", "%" + key + "%").and().like("Keterangan", "%" + "Belum Ditebus" + "%").query();
            for (Gadai item : gadai) {
                Object[] o = new Object[10];
                o[0] = item.getNo_gadai();
                o[1] = item.getPetugas().getNama();
                o[2] = item.getNasabah().getNama();

                o[3] = item.getBarang().getKode_barang();
                o[4] = item.getTgl_gadai();
                o[5] = item.getJatuh_tempo();
                o[6] = item.getJumlah_pinjaman();
                o[7] = item.getJumlah_tebusan();
                o[8] = item.getKeterangan();
                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        //kembalikan hasil dtm
        return dtm;
    }

    @Override
    public void update(Object o) {
        try {
            daoGadai.update((Gadai) o);
//            JOptionPane.showMessageDialog(null, "Update data berhasil !");
        } catch (SQLException ex) {
            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public DefaultTableModel viewtebusan() {
        DefaultTableModel dtm;
        String[] title = {"ID", "Nama Petugas", "Nama Nasabah", "Kode Barang", "Jatuh Tempo", "Tanggal Tebusan", "Jumlah Pinjaman ", "Jumlah Tebusan", "Denda", "Total Tebusan", "Keterangan"};
        dtm = new DefaultTableModel(null, title);
        try {
            List<Gadai> gadai = daoGadai.queryBuilder().where().
                    like("Keterangan", "%" + "Sudah Ditebus" + "%").query();
            for (Gadai item : gadai) {
                Object[] o = new Object[11];
                o[0] = item.getNo_gadai();
                o[1] = item.getPetugas().getNama();
                o[2] = item.getNasabah().getNama();

                o[3] = item.getBarang().getKode_barang();
                o[4] = item.getJatuh_tempo();
                o[5] = item.getTgl_tebusan();
                o[6] = item.getJumlah_pinjaman();
                o[7] = item.getJumlah_tebusan();
                o[8] = item.getDenda();
                o[9] = item.getTotal_tebusan();
                o[10] = item.getKeterangan();

                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //kembalikan hasil dtm
        return dtm;
    }

    @Override
    public DefaultTableModel detail(int id) {
        DefaultTableModel dtm;
        String judul[] = {"Kode Barang", "Nama Barang", "Type Barang", "Warna Barang"};
        dtm = new DefaultTableModel(null, judul);
        try {
            List<Gadai> gadai = daoGadai.queryForEq("No_gadai", id);
            for (Gadai g : gadai) {
                Object o[] = new Object[4];
                o[0] = g.getBarang().getKode_barang();
                o[1] = g.getBarang().getNama_barang();
                o[2] = g.getBarang().getType();
                o[3] = g.getBarang().getWarna();

                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dtm;
    }

    @Override
    public void Ditebus(Object o) {
        try {
            daoGadai.update((Gadai) o);
            JOptionPane.showMessageDialog(null, "Simpan data berhasil !");
        } catch (SQLException ex) {
            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public DefaultTableModel viewdataGadai() {
        DefaultTableModel dtm;
        String[] title = {"ID", "Nama Petugas", "Nama Nasabah", "Kode Barang", "Jatuh Tempo", "Tanggal Tebusan", "Jumlah Pinjaman ", "Jumlah Tebusan", "Denda", "Total Tebusan", "Keterangan"};
        dtm = new DefaultTableModel(null, title);
        try {
            List<Gadai> gadai = daoGadai.queryForAll();
            for (Gadai item : gadai) {
                Object[] o = new Object[11];
                o[0] = item.getNo_gadai();
                o[1] = item.getPetugas().getNama();
                o[2] = item.getNasabah().getNama();
                o[3] = item.getBarang().getKode_barang();
                o[4] = item.getJatuh_tempo();
                o[5] = item.getTgl_tebusan();
                o[6] = item.getJumlah_pinjaman();
                o[7] = item.getJumlah_tebusan();
                o[8] = item.getDenda();
                o[9] = item.getTotal_tebusan();
                o[10] = item.getKeterangan();

                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //kembalikan hasil dtm
        return dtm;
    }

}
