/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package barang;

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
public class BarangDAOImp implements DAO {

    Dao<Barang, Integer> dao;

    public BarangDAOImp() {
        try {
            dao = DaoManager.createDao(Koneksi.cs(), Barang.class);
        } catch (SQLException ex) {
            Logger.getLogger(BarangDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(Object o) {
        try {
            dao.create((Barang) o);
            JOptionPane.showMessageDialog(null, "Simpan data berhasil !");
        } catch (SQLException ex) {
            Logger.getLogger(BarangDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Object o) {
        try {
            dao.update((Barang) o);
            JOptionPane.showMessageDialog(null, "Simpan data berhasil !");
        } catch (SQLException ex) {
            Logger.getLogger(BarangDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int id) {
        try {
            dao.deleteById(id);
            JOptionPane.showMessageDialog(null, "Hapus data berhasil !");
        } catch (SQLException ex) {
            Logger.getLogger(BarangDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public DefaultTableModel selectAll() {
        DefaultTableModel dtm;
        String[] title = {"Kode", "Nama Barang", "Type", "warna"};
        dtm = new DefaultTableModel(null, title);
        try {
            List<Barang> barang = dao.queryForAll();
            for (Barang b : barang) {
                Object[] o = new Object[4];
                o[0] = b.getKode_barang();
                o[1] = b.getNama_barang();
                o[2] = b.getType();
                o[3] = b.getWarna();

                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BarangDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //kembalikan hasil dtm
        return dtm;
    }

    @Override
    public DefaultTableModel search(String key) {
        DefaultTableModel dtm;
        String[] title = {"Kode", "Nama Barang", "Type", "warna"};
        dtm = new DefaultTableModel(null, title);
        try {
            List<Barang> barang = dao.queryBuilder().where().
                    like("Nama_barang", "%" + key + "%").query();
            for (Barang b : barang) {

                Object[] o = new Object[4];
                o[0] = b.getKode_barang();
                o[1] = b.getNama_barang();
                o[2] = b.getType();
                o[3] = b.getWarna();
                dtm.addRow(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BarangDAOImp.class.getName()).log(Level.SEVERE, null, ex);
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
//            parameters.put("id", id);
          
            File sumber = new File("src/report/"+file+".jrxml");
            desaign = JRXmlLoader.load(sumber);
            report = JasperCompileManager.compileReport(desaign);
            try {
                print = JasperFillManager.fillReport(report, parameters, new KoneksiReport().getkon());
                JasperViewer.viewReport(print,false);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BarangDAOImp.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JRException ex) {
            Logger.getLogger(BarangDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
