/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nasabah;

import barang.BarangDAOImp;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import database.DAO;
import database.Koneksi;
import database.KoneksiReport;
import java.io.File;
import java.sql.SQLException;
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
public class NasabahDAOImp implements DAO {

    private Dao<Nasabah, Integer> dao;

    public NasabahDAOImp() {
        try {
            //hubungkan cs dengan dao
            dao = DaoManager.createDao(Koneksi.cs(), Nasabah.class);
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(Object o) {
        try {
            dao.create((Nasabah) o);
            JOptionPane.showMessageDialog(null, "Simpan data berhasil !");
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @Override
    public void update(Object o) {
        try {
            dao.update((Nasabah) o);
            JOptionPane.showMessageDialog(null, "Simpan data berhasil !");
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            dao.deleteById(id);
            JOptionPane.showMessageDialog(null, "Hapus data berhasil !");
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public DefaultTableModel selectAll() {
        DefaultTableModel dtm;
        String[] title = {"No KTP", "Nama", "No HP", "Alamat"};
        dtm = new DefaultTableModel(null, title);
        try {
            List<Nasabah> nasabah = dao.queryForAll();
            for (Nasabah s : nasabah) {
                Object[] o = new Object[4];
                o[0] = s.getKtp();
                o[1] = s.getNama();
                o[2] = s.getHp();
                o[3] = s.getAlamat();
                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //kembalikan hasil dtm
        return dtm;
    }

    @Override
    public DefaultTableModel search(String key) {
        DefaultTableModel dtm;
        String[] title = {"No KTP", "Nama", "No HP", "Alamat"};
        dtm = new DefaultTableModel(null, title);
        try {
            List<Nasabah> nasabah = dao.queryBuilder().where().
                    like("Nama", "%" + key + "%").query();
            for (Nasabah s : nasabah) {
                Object[] o = new Object[4];
                o[0] = s.getKtp();
                o[1] = s.getNama();
                o[2] = s.getHp();
                o[3] = s.getAlamat();
                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //kembalikan hasil dtm
        return dtm;
    }

    @Override
    public void getreport(String file) {
        try {
            JasperReport report;
            JasperPrint print;
            JasperDesign desaign;
            HashMap parameters = new HashMap();
//            parameters.put("Ktp",id );
            Koneksi kon = new Koneksi();
            File sumber = new File("src/report/" + file + ".jrxml");
            desaign = JRXmlLoader.load(sumber);
            report = JasperCompileManager.compileReport(desaign);
            print = JasperFillManager.fillReport(report, parameters, new KoneksiReport().getkon());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
