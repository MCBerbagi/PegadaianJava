/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gadai;

import static Gadai.GadaiForm.tabelGadai;
import database.KoneksiReport;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import sun.misc.VM;

/**
 *
 * @author Th3-TW1N5
 */
public class Dtransaksi extends javax.swing.JInternalFrame {

    /**
     * Creates new form Dtransaksi
     */
    GadaiDAO dao = new GadaiDAOImp();
    int ID = 0;

    public Dtransaksi() {
        initComponents();
        viewdatagadai();
        berdasarkan.setVisible(false);
        tglawal.setVisible(false);
        tglakhir.setVisible(false);
        cetak1.setVisible(false);
        cetak.setVisible(false);

    }

    public void view() {

        GadaiForm.tabelGadai.setModel(dao.viewGadai());
        GadaiForm.tabeltebusan.setModel(dao.viewtebusan());

    }

    public void cetak() {
        try {
            Gadai g = new Gadai();
            String status = "";
            if (berdasarkan.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(rootPane, "Status laporan belum dipilih");
            } else {
                if (berdasarkan.getSelectedIndex() == 1) {
                    status = "Sudah Ditebus";
                } else if (berdasarkan.getSelectedIndex() == 2) {
                    status = "Belum Ditebus";
                } else {
                    status = "Milik Pegadaian";
                }
                JasperReport report;
                JasperPrint print;
                JasperDesign desaign;
                HashMap parameters = new HashMap();
                parameters.put("status", status);
//            parameters=null;

                File sumber = new File("src/report/StatusReport.jrxml");
                desaign = JRXmlLoader.load(sumber);
                report = JasperCompileManager.compileReport(desaign);
                print = JasperFillManager.fillReport(report, parameters, new KoneksiReport().getkon());
                JasperViewer.viewReport(print, false);
                viewdatagadai();
                berdasarkan.setSelectedIndex(0);
                pilih.setSelectedIndex(0);
            }

        } catch (JRException ex) {
//            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cetak1() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Gadai g = new Gadai();
            String status = "";
            if (berdasarkan.getSelectedIndex() == 0 || tglawal.getDate() == null || tglakhir.getDate() == null) {
                JOptionPane.showMessageDialog(rootPane, "Data Belum Dipilih Semua");
            } else {
                if (berdasarkan.getSelectedIndex() == 1) {
                    status = "Sudah Ditebus";
                } else if (berdasarkan.getSelectedIndex() == 2) {
                    status = "Belum Ditebus";
                } else {
                    status = "Milik Pegadaian";
                }
                JasperReport report;
                JasperPrint print;
                JasperDesign desaign;
                HashMap parameters = new HashMap(3);
                parameters.put("status", status);
                parameters.put("tglawal", String.valueOf(date.format(tglawal.getDate())));
                parameters.put("tglakhir", String.valueOf(date.format(tglakhir.getDate())));
//                parameters.put("tglakhir", status);
//            parameters=null;

                File sumber = new File("src/report/StatusReport1.jrxml");
                desaign = JRXmlLoader.load(sumber);
                report = JasperCompileManager.compileReport(desaign);
                print = JasperFillManager.fillReport(report, parameters, new KoneksiReport().getkon());
                JasperViewer.viewReport(print, false);
                viewdatagadai();
                berdasarkan.setSelectedIndex(0);
                tglawal.setDate(null);
                tglakhir.setDate(null);
                pilih.setSelectedIndex(0);

            }

        } catch (JRException ex) {
//            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void viewdatagadai() {
        tabeltransaksi.setModel(dao.viewdataGadai());
    }

    public void deleteGadai() {
        int record = tabeltransaksi.getRowCount();
        //jika ada data yang dipilih
        if (record > 0) {
            //validasi jumlah data yang dipilih
            int selected = tabeltransaksi.getSelectedRowCount();
            //jika ada data yang diplih
            if (selected > 0) {
                //konfirmasi hapus
                int index = tabeltransaksi.getSelectedRow();
                ID = Integer.valueOf(tabeltransaksi.getValueAt(index, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(rootPane, "yakin untuk dihapus?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
//                    if (rootPaneCheckingEnabled) {
//                        
//                    } else {
//                    }
                    dao.deleteGadai(ID);
                    viewdatagadai();
                    view();

//                    load();
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, " Tidak ada data yg dipilih !");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "data tidak ada");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabeltransaksi = new javax.swing.JTable();
        hapus = new javax.swing.JButton();
        berdasarkan = new javax.swing.JComboBox();
        cetak = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        tglawal = new com.toedter.calendar.JDateChooser();
        tglakhir = new com.toedter.calendar.JDateChooser();
        cetak1 = new javax.swing.JButton();
        pilih = new javax.swing.JComboBox();

        tabeltransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabeltransaksi.getTableHeader().setReorderingAllowed(false);
        tabeltransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeltransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabeltransaksi);

        hapus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-property-32.png"))); // NOI18N
        hapus.setText("Hapus");
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });

        berdasarkan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---Berdasarkan ---", "Sudah Ditebus", "Belum Ditebus", "Milik Pegadaian" }));
        berdasarkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                berdasarkanActionPerformed(evt);
            }
        });

        cetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/print-icon.png"))); // NOI18N
        cetak.setText("Cetak");
        cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sinchronize-32.png"))); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tglawal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "tgl awal", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 8))); // NOI18N

        tglakhir.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tgl Akhir", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 8))); // NOI18N

        cetak1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/print-icon.png"))); // NOI18N
        cetak1.setText("Cetak");
        cetak1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetak1ActionPerformed(evt);
            }
        });

        pilih.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--Cetak Berdasarkan--", "Tanggal Jatuh Tempo", "Status" }));
        pilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pilihActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(hapus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pilih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(berdasarkan, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cetak)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tglawal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tglakhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cetak1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1)
                                .addComponent(hapus)
                                .addComponent(pilih, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(berdasarkan, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cetak))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tglawal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tglakhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cetak1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        // TODO add your handling code here:
        deleteGadai();
    }//GEN-LAST:event_hapusActionPerformed

    private void tabeltransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeltransaksiMouseClicked
        // TODO add your handling code here:
//         if (tabeltransaksi.getSelectedRowCount() != 0) {
//            int selected = tabeltransaksi.getSelectedRow();
////                }
//    String baris=tabelGadai.getValueAt(selected, 0).toString();
//            DetailForm d = new DetailForm();
//            d.setVisible(true);
//
//        } else {
//            ID = 0;
//            DetailForm.tabeldetail.setModel(dao.detail(ID));
//        }
    }//GEN-LAST:event_tabeltransaksiMouseClicked

    private void cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakActionPerformed
        // TODO add your handling code here:
        cetak();

    }//GEN-LAST:event_cetakActionPerformed

    private void berdasarkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_berdasarkanActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_berdasarkanActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        viewdatagadai();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cetak1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetak1ActionPerformed
        // TODO add your handling code here:
        cetak1();
    }//GEN-LAST:event_cetak1ActionPerformed

    private void pilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pilihActionPerformed
        // TODO add your handling code here:
        if (pilih.getSelectedIndex() == 1) {
            berdasarkan.setVisible(true);
            tglawal.setVisible(true);
            tglakhir.setVisible(true);
            cetak1.setVisible(true);
            cetak.setVisible(false);

        } else if (pilih.getSelectedIndex() == 2) {
            berdasarkan.setVisible(true);
            tglawal.setVisible(false);
            tglakhir.setVisible(false);
            cetak1.setVisible(false);
            cetak.setVisible(true);

        } else {
            berdasarkan.setVisible(false);
            tglawal.setVisible(false);
            tglakhir.setVisible(false);
            cetak1.setVisible(false);
            cetak.setVisible(false);

        }
    }//GEN-LAST:event_pilihActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JComboBox berdasarkan;
    public static javax.swing.JButton cetak;
    public static javax.swing.JButton cetak1;
    public static javax.swing.JButton hapus;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JComboBox pilih;
    public static javax.swing.JTable tabeltransaksi;
    public static com.toedter.calendar.JDateChooser tglakhir;
    public static com.toedter.calendar.JDateChooser tglawal;
    // End of variables declaration//GEN-END:variables
}
