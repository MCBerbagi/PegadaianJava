/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petugas;

import barang.BarangDAOImp;
import petugas.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
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
public class PetugasDAOImp implements DAO {

    Dao<Petugas, Integer> dao;

    public PetugasDAOImp() {
        try {
            dao = DaoManager.createDao(Koneksi.cs(), Petugas.class);
        } catch (SQLException ex) {
            Logger.getLogger(PetugasDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(Object o) {
        try {
            dao.create((Petugas) o);
            JOptionPane.showMessageDialog(null, "Simpan data berhasil !");
        } catch (SQLException ex) {
            Logger.getLogger(PetugasDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Object o) {
        try {
            dao.update((Petugas) o);
            JOptionPane.showMessageDialog(null, "Simpan data berhasil !");
        } catch (SQLException ex) {
            Logger.getLogger(PetugasDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            dao.deleteById(id);
            JOptionPane.showMessageDialog(null, "Hapus data berhasil !");
            
        } catch (SQLException ex) {
            Logger.getLogger(PetugasDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public DefaultTableModel selectAll() {
        DefaultTableModel dtm;
        String[] title = {"Nip", "Nama Petugas", "Alamat", "Username", "Password","Hak Akses"};
        dtm = new DefaultTableModel(null, title);
        try {
            List<Petugas> petugas = dao.queryForAll();
            for (Petugas b : petugas) {
                Object[] o = new Object[6];
                o[0] = b.getNip();
                o[1] = b.getNama();
                o[2] = b.getAlamat();
                o[3] = b.getUsername();
                o[4] = b.getPassword();
                o[5] = b.getHakakses();

                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PetugasDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //kembalikan hasil dtm
        return dtm;
    }

    @Override
    public DefaultTableModel search(String key) {
        DefaultTableModel dtm;
        String[] title = {"Nip", "Nama Petugas", "Alamat", "Username", "Password","Hak Akses"};
        dtm = new DefaultTableModel(null, title);
        try {
             List<Petugas> petugas = dao.queryBuilder().where().
                    like("Nama", "%" + key + "%").query();
            for (Petugas b :petugas) {

                Object[] o = new Object[6];
                o[0] = b.getNip();
                o[1] = b.getNama();
                o[2] = b.getAlamat();
                o[3] = b.getUsername();
                o[4] = b.getPassword();
                o[5] = b.getHakakses();
                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PetugasDAOImp.class.getName()).log(Level.SEVERE, null, ex);
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
            HashMap parameters=new HashMap();
            parameters=null;
            Koneksi kon=new Koneksi();
            File sumber=new File("src/report/"+file+".jrxml");
            desaign=JRXmlLoader.load(sumber);
            report=JasperCompileManager.compileReport(desaign);
            print=JasperFillManager.fillReport(report, parameters,new KoneksiReport().getkon());
            JasperViewer.viewReport(print,false);
        } catch (JRException ex) {
            Logger.getLogger(PetugasDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PetugasDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }}
    


