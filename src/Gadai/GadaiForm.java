/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gadai;

import Nasabah.Nasabah;
import Nasabah.NasabahDAOImp;
import auth.Auth;
import barang.Barang;
import com.sun.istack.internal.logging.Logger;
import database.Koneksi;
import database.KoneksiReport;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import petugas.Petugas;
import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.swing.JTable;
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
 * @author Th3-TW1N5
 */
public class GadaiForm extends javax.swing.JInternalFrame {

    /**
     * Creates new form GadaiItemForm1
     */
    GadaiDAO dao = new GadaiDAOImp();
    List<Integer> nasabahId = new ArrayList<>();
    List<Integer> barangId = new ArrayList<>();
    List<Integer> gadaiId = new ArrayList<>();

    Barang barang;
    Nasabah nasabah;
    Petugas petugas;
    Gadai gadaiItem;

    int ID = 0;
    int TAG = 0;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();

    /**
     * Creates new form PembelianItemForm
     */
    public GadaiForm() {
        initComponents();

        load();
        DateFormat dateFormat1 = new SimpleDateFormat("dd MM,yyyy");
        jTgl_sekarang.setText(dateFormat1.format(date));

        //get user logged in
    }

    public void load() {
        tfbayar.setVisible(false);
        tfkembalian.setVisible(false);
        jhtransaksi.setVisible(false);
        jLabel16.setVisible(false);
        jLabel17.setVisible(false);
        jhitungpanjang.setVisible(false);
        loadNasabah();
        loadBarang();
        viewGadai();
        loadgadai();
        viewtebusan();

        aktif();
    }

    public void viewGadai() {
        tabelGadai.setModel(dao.viewGadai());
    }

    public void viewtebusan() {
        tabeltebusan.setModel(dao.viewtebusan());
    }

    public void loadNasabah() {
        cbNasabah.removeAllItems();
        cbNasabah.addItem("-- Pilih --");
        nasabahId.add(0);
        for (Nasabah s : dao.loadNasabah()) {
            cbNasabah.addItem(String.valueOf(s.getKtp() + "     " + s.getNama()));
            nasabahId.add(s.getKtp());
        }
    }

    public void loadgadai() {
        jdatagadai.removeAllItems();
        jdatagadai.addItem("-- Pilih --");
        gadaiId.add(0);
        for (Gadai g : dao.loadGadai()) {
            jdatagadai.addItem(String.valueOf(g.getNo_gadai() + " : " + g.getNasabah().getNama() + " : " + g.getJatuh_tempo()));
            gadaiId.add(g.getNo_gadai());
        }
    }

    public void loadBarang() {
        cbBarang.removeAllItems();
        cbBarang.addItem("-- Pilih --");
        barangId.add(0);
        for (Barang b : dao.loadBarang()) {
            cbBarang.addItem(String.valueOf(b.getKode_barang() + "     " + b.getNama_barang()));
            barangId.add(b.getKode_barang());
//          
        }

    }

    public void getgadai() {
        if (jdatagadai.getSelectedIndex() > 0) {
            int index = jdatagadai.getSelectedIndex() - 1;
            Gadai g = dao.loadGadai().get(index);
            tfJ_tebusan.setText(String.valueOf(g.getJumlah_tebusan()));
            tfJ_pinjaman.setText(String.valueOf(g.getJumlah_pinjaman()));
        } else {
            tfJ_tebusan.setText("0");
        }
    }

    public void search() {
        if (tfcarigadai.getText().isEmpty()) {
            viewGadai();
        } else {
            tabelGadai.setModel(dao.search(tfcarigadai.getText()));
        }
    }

    public void hitung() {
        try {
            int row = tabelGadai.getSelectedRow();
//            Date date1 = dateFormat.parse(tabelGadai.getValueAt(row, 5).toString());
            Date date2 = dateFormat.parse(String.valueOf(dateFormat.format(jdt_taggaltebusan.getDate())));
            Date date1 = dateFormat.parse(String.valueOf(dateFormat.format(jDatejatuh.getDate())));
//            Date date1 = dateFormat.parse(tabelGadai.getValueAt(row, 5).toString());
            long selisih = date2.getTime() - date1.getTime();
            if (date2.getTime() < date1.getTime()) {
                JOptionPane.showMessageDialog(null, "tanggal kembali kurang dari batas kembali");
            } else {
                int telat = (int) TimeUnit.DAYS.convert(selisih, TimeUnit.MILLISECONDS);
                jThari.setText(String.valueOf(telat));
                double denda = telat * 5000;
                jtTotaltebusan.setText(String.valueOf(denda));

//                if (telat > 0) {
//                    tfbayar.setEnabled(true);
//
//                } else {
//                    tfbayar.setEnabled(false);
//                }
            }
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
            JOptionPane.showMessageDialog(null, "tanggal kembali belum dipilih");

        }

    }

    public void cetak(int id) {
        try {
            Gadai g = new Gadai();

            JasperReport report;
            JasperPrint print;
            JasperDesign desaign;
            HashMap parameters = new HashMap();
            parameters.put("nogadai", id);
//            parameters=null;

            File sumber = new File("src/report/GadaiReport.jrxml");
            desaign = JRXmlLoader.load(sumber);
            report = JasperCompileManager.compileReport(desaign);
            print = JasperFillManager.fillReport(report, parameters, new KoneksiReport().getkon());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
//            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cetakpanjang(int id) {
        try {
            Gadai g = new Gadai();

            JasperReport report;
            JasperPrint print;
            JasperDesign desaign;
            HashMap parameters = new HashMap();
            parameters.put("nogadai", id);
//            parameters=null;

            File sumber = new File("src/report/PerpanjangReport.jrxml");
            desaign = JRXmlLoader.load(sumber);
            report = JasperCompileManager.compileReport(desaign);
            print = JasperFillManager.fillReport(report, parameters, new KoneksiReport().getkon());
            JasperViewer.viewReport(print, false);
        } catch (JRException ex) {
//            Logger.getLogger(GadaiDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(NasabahDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addGadai() {
        try {

            if (cbNasabah.getSelectedIndex() != 0 || cbBarang.getSelectedIndex() != 0 || tfJ_pinjaman.getText().isEmpty() || tfJ_tebusan.getText().isEmpty()) {

                System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
                Gadai g = new Gadai();
                g.setTgl_gadai(dateFormat.format(date));

//            g.setTgl_tebusan(dateFormat.format(jdt_taggaltebusan.getDate()));
                g.setJatuh_tempo(dateFormat.format(jDatejatuh.getDate()));

                g.setJumlah_pinjaman(Double.valueOf(tfJ_pinjaman.getText()));
                g.setJumlah_tebusan(Double.valueOf(tfJ_tebusan.getText()));
                g.setTotal_tebusan(Double.valueOf(tfJ_tebusan.getText()));
                g.setTgl_tebusan(String.valueOf("0001-01-01"));
                g.setDenda(0.0);
                g.setTerbilang("");
//      
                g.setKeterangan("Belum Ditebus");
                Nasabah n = new Nasabah();
                Barang b = new Barang();

                if (cbNasabah.getSelectedIndex() != 0) {
                    int ID = nasabahId.get(cbNasabah.getSelectedIndex());
                    n.setKtp(ID);
                    g.setNasabah(n);
                } else {
                    JOptionPane.showMessageDialog(null, "nama customer belum dipilih");
                }
                if (cbBarang.getSelectedIndex() != 0) {
                    int ID = barangId.get(cbBarang.getSelectedIndex());
                    b.setKode_barang(ID);
                    g.setBarang(b);
                } else {
                    JOptionPane.showMessageDialog(null, "nama Barang belum dipilih");
                }
//        g.setBarang(b);
                //user
                Petugas petugas1 = new Petugas();
                petugas1.setNip(Auth.ID);
                //setuser
                g.setPetugas(petugas1);

                if (TAG == 0) {
                    //insert
                    dao.insertGadai(g);
                    viewGadai();
                    cetak(g.getNo_gadai());
                } else {
                    //update
                    g.setNo_gadai(ID);
                    dao.update(g);
                    viewGadai();
//                    new Dtransaksi().viewdataGadai();
                }
                reset();
                viewGadai();
                load();
//                Dtransaksi.tabeltransaksi.setModel(dao.viewdataGadai());

//       
            } else {
                JOptionPane.showMessageDialog(rootPane, "Data Belum Diisi Semuanya");
                viewGadai();
                loadgadai();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Data Belum Diisi Semuanya");
            viewGadai();
            loadgadai();
        }
    }

    public void Simpantebusan() {

        if (jdatagadai.getSelectedIndex() > 0) {
            try {
                int index = jdatagadai.getSelectedIndex() - 1;
                Gadai g = dao.loadGadai().get(index);

                Gadai gn = new Gadai();
                gn.setNo_gadai(g.getNo_gadai());

                gn.setBarang(g.getBarang());
//            gn.setDenda(g.getDenda());
                gn.setJatuh_tempo(g.getJatuh_tempo());
                gn.setJumlah_pinjaman(g.getJumlah_pinjaman());
                gn.setJumlah_tebusan(g.getJumlah_tebusan());
                gn.setTotal_tebusan(Double.valueOf(jtTotaltebusan.getText()));
                gn.setNasabah(g.getNasabah());
                gn.setPetugas(g.getPetugas());
                gn.setTerbilang(g.getTerbilang());
                gn.setTgl_gadai(g.getTgl_gadai());
                gn.setJatuh_tempo(g.getJatuh_tempo());
                gn.setDenda(Double.valueOf(jtdenda.getText()));

                gn.setKeterangan("Sudah Ditebus");
                gn.setTgl_tebusan(dateFormat.format(jdt_taggaltebusan.getDate()));

                dao.update(gn);
                JOptionPane.showMessageDialog(rootPane, "Barang Sudah Ditebus");
                load();
                reset();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, e);
            }

        }

    }

    public void deleteGadai() {
        int record = tabelGadai.getRowCount();
        //jika ada data yang dipilih
        if (record > 0) {
            //validasi jumlah data yang dipilih
            int selected = tabelGadai.getSelectedRowCount();
            //jika ada data yang diplih
            if (selected > 0) {
                //konfirmasi hapus
                int index = tabelGadai.getSelectedRow();
                ID = Integer.valueOf(tabelGadai.getValueAt(index, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(rootPane, "yakin untuk dihapus?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {

                    dao.deleteGadai(ID);
                    viewGadai();
                    load();

                }
            } else {
                JOptionPane.showMessageDialog(rootPane, " Tidak ada data yg dipilih !");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "data tidak ada");
        }
    }

    public void deleteTebus() {
        int record = tabeltebusan.getRowCount();
        //jika ada data yang dipilih
        if (record > 0) {
            //validasi jumlah data yang dipilih
            int selected = tabeltebusan.getSelectedRowCount();
            //jika ada data yang diplih
            if (selected > 0) {
                //konfirmasi hapus
                int index = tabeltebusan.getSelectedRow();
                ID = Integer.valueOf(tabeltebusan.getValueAt(index, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(rootPane, "yakin untuk dihapus?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {

                    dao.deleteGadai(ID);
                    load();

                }
            } else {
                JOptionPane.showMessageDialog(rootPane, " Tidak ada data yg dipilih !");
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "data tidak ada");
        }
    }

    public void reset() {
        //reset item
        bunga1.setText("Rp.0");
//        jtransaksi.setSelectedIndex(0);
        cbNasabah.setSelectedIndex(0);
        cbBarang.setSelectedIndex(0);
        jdatagadai.setSelectedIndex(0);
        jDatejatuh.setDate(null);
        tfJ_pinjaman.setText("");
        tfJ_tebusan.setText("");
        jtdenda.setText("0");
        tfbayar.setText("");
        tfkembalian.setText("");
        jdt_taggaltebusan.setDate(null);
        jtTotaltebusan.setText("");
        jThari.setText("0");

//       
        viewGadai();
    }

    public void viewItem() {

        if (tabelGadai.getSelectedRowCount() != 0) {
            int selected = tabelGadai.getSelectedRow();
//            String baris=tabelGadai.getValueAt(selected, 0).toString();
            DetailForm d = new DetailForm();
            d.setVisible(true);

        } else {
            ID = 0;
            DetailForm.tabeldetail.setModel(dao.detail(ID));
        }
    }

    public void pinjaman() {
        double pinjam = Double.valueOf(tfJ_pinjaman.getText());
        double pinjam1 = (pinjam * 10) / 100;
    }

    public void perpanjang() {
        try {

//       
            if (jdatagadai.getSelectedIndex() > 0) {
                int index = jdatagadai.getSelectedIndex() - 1;
                Gadai g = dao.loadGadai().get(index);

                Date date2 = dateFormat.parse(String.valueOf(dateFormat.format(jDatejatuh.getDate())));
                Date date1 = dateFormat.parse(String.valueOf(g.getJatuh_tempo()));

                long selisih = date2.getTime() - date1.getTime();
                int telat = (int) TimeUnit.DAYS.convert(selisih, TimeUnit.MILLISECONDS);
                if (telat > 14) {
                    JOptionPane.showMessageDialog(rootPane, "Tanggal Perpanjang Tidak Lebih 14 Hari");
                } else {
                    tfbayar.setVisible(true);
                    tfkembalian.setVisible(true);
                    jhitungpanjang.setVisible(true);
                    jLabel17.setVisible(true);
                    jLabel16.setVisible(true); 
                    double pinjaman = Double.valueOf(tfJ_pinjaman.getText());
                    double bunga = (pinjaman * 10) / 100;
                    bunga1.setText(String.valueOf("Rp. " + bunga));
//             
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }

    public void aktif() {
        reset();
        jtransaksi.setSelectedIndex(0);
        jButton1.setEnabled(true);
        jdatagadai.setSelectedIndex(0);
        jdatagadai.setEnabled(false);
        jdt_taggaltebusan.setDate(null);
//        jhtransaksi.setEnabled(false);
        cbNasabah.setEnabled(false);
        cbBarang.setEnabled(false);
        jTgl_sekarang.setEditable(false);
        jBhitunghari.setEnabled(false);
        jBhitung.setEnabled(false);
        jDatejatuh.setEnabled(false);
        tfJ_pinjaman.setEditable(false);
        tfJ_tebusan.setEnabled(false);
        jtdenda.setEnabled(false);
        jdt_taggaltebusan.setEnabled(false);
        simpantebusan.setEnabled(false);
//        hapustebusan.setEnabled(false);
        btnTambahItem.setEnabled(false);
        jtdenda.setEnabled(true);
        btnupdateItem.setEnabled(false);
//        jhitungpanjang.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cbNasabah = new javax.swing.JComboBox<String>();
        jLabel2 = new javax.swing.JLabel();
        cbBarang = new javax.swing.JComboBox<String>();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtransaksi = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        tfJ_pinjaman = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfJ_tebusan = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jtdenda = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jThari = new javax.swing.JTextField();
        jBhitung = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTgl_sekarang = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jBhitunghari = new javax.swing.JButton();
        jdatagadai = new javax.swing.JComboBox();
        jDatejatuh = new com.toedter.calendar.JDateChooser();
        jdt_taggaltebusan = new com.toedter.calendar.JDateChooser();
        btnTambahItem = new javax.swing.JButton();
        btnHapusItem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelGadai = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabeltebusan = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        btnupdateItem = new javax.swing.JButton();
        simpantebusan = new javax.swing.JButton();
        hapustebusan = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jtTotaltebusan = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tfbayar = new javax.swing.JTextField();
        tfkembalian = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jhtransaksi = new javax.swing.JButton();
        bunga = new javax.swing.JLabel();
        bunga1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jhitungpanjang = new javax.swing.JButton();
        tfcarigadai = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setTitle("FORM TRANSAKSI");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaksi"));

        cbNasabah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbNasabah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNasabahActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nasabah ");

        cbBarang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Barang ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Jatuh Tempo");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Transaksi          ");

        jtransaksi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---Pilih---", "Gadai", "Tebusan", "Perpanjang" }));
        jtransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtransaksiActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Jumlah Pinjaman ");

        tfJ_pinjaman.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfJ_pinjaman.setToolTipText(" masukkan Jumlah Pinjaman ");
        tfJ_pinjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfJ_pinjamanActionPerformed(evt);
            }
        });
        tfJ_pinjaman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfJ_pinjamanKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Jumlah Tebusan ");

        tfJ_tebusan.setEditable(false);
        tfJ_tebusan.setBackground(new java.awt.Color(255, 255, 255));
        tfJ_tebusan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Tanggal Tebusan ");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Denda");

        jtdenda.setEditable(false);
        jtdenda.setBackground(new java.awt.Color(255, 255, 255));
        jtdenda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Hari");

        jThari.setEditable(false);
        jThari.setBackground(new java.awt.Color(255, 255, 255));
        jThari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jThari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jThariActionPerformed(evt);
            }
        });

        jBhitung.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBhitung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bill-32.png"))); // NOI18N
        jBhitung.setText("Hitung");
        jBhitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBhitungActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Tanggal Sekarang");

        jTgl_sekarang.setEditable(false);
        jTgl_sekarang.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("DATA GADAI");

        jBhitunghari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBhitunghari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bill-32.png"))); // NOI18N
        jBhitunghari.setText("Hitung Hari");
        jBhitunghari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBhitunghariActionPerformed(evt);
            }
        });

        jdatagadai.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jdatagadai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdatagadaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel14)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel13)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jdt_taggaltebusan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtransaksi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbNasabah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbBarang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTgl_sekarang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfJ_pinjaman, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfJ_tebusan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdatagadai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jThari, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtdenda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDatejatuh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBhitung, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBhitunghari, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbBarang, cbNasabah, jBhitung, jBhitunghari, jTgl_sekarang, jThari, jdatagadai, jtransaksi, tfJ_pinjaman, tfJ_tebusan});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbNasabah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTgl_sekarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfJ_pinjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDatejatuh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBhitunghari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tfJ_tebusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jdatagadai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jdt_taggaltebusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jThari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBhitung)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtdenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(40, 40, 40))
        );

        btnTambahItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTambahItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/save-32.png"))); // NOI18N
        btnTambahItem.setText("Simpan");
        btnTambahItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahItemActionPerformed(evt);
            }
        });

        btnHapusItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHapusItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-property-32.png"))); // NOI18N
        btnHapusItem.setText("Hapus ");
        btnHapusItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusItemActionPerformed(evt);
            }
        });

        tabelGadai.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelGadai.getTableHeader().setReorderingAllowed(false);
        tabelGadai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelGadaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelGadai);

        tabeltebusan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabeltebusan.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tabeltebusan);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Transaksi Tebusan");

        btnupdateItem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnupdateItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit-property-32.png"))); // NOI18N
        btnupdateItem.setText("Perpanjang");
        btnupdateItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateItemActionPerformed(evt);
            }
        });

        simpantebusan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        simpantebusan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/save-32.png"))); // NOI18N
        simpantebusan.setText("Simpan");
        simpantebusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpantebusanActionPerformed(evt);
            }
        });

        hapustebusan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        hapustebusan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete-property-32.png"))); // NOI18N
        hapustebusan.setText("Hapus");
        hapustebusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapustebusanActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/view-details-32.png"))); // NOI18N
        jButton1.setText("Detail");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setToolTipText("");

        jtTotaltebusan.setBackground(new java.awt.Color(0, 0, 0));
        jtTotaltebusan.setFont(new java.awt.Font("DejaVu Sans", 1, 20)); // NOI18N
        jtTotaltebusan.setText(" 0");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Total Tebusan");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("Rp. ");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Dibayar");

        tfbayar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tfbayar.setText("0");

        tfkembalian.setEditable(false);
        tfkembalian.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tfkembalian.setText("0");
        tfkembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfkembalianActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Kembalian");

        jhtransaksi.setText("Hitung Transaksi");
        jhtransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jhtransaksiActionPerformed(evt);
            }
        });

        bunga.setFont(new java.awt.Font("Sitka Small", 1, 14)); // NOI18N

        bunga1.setFont(new java.awt.Font("Sitka Small", 1, 14)); // NOI18N
        bunga1.setText("Rp.0");

        jLabel18.setFont(new java.awt.Font("Sitka Small", 1, 14)); // NOI18N
        jLabel18.setText("Bunga");

        jhitungpanjang.setText("Hitung");
        jhitungpanjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jhitungpanjangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(259, 259, 259)
                        .addComponent(bunga, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel18))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bunga1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtTotaltebusan, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(tfkembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jhtransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jhitungpanjang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jLabel17))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtTotaltebusan)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15)
                            .addComponent(tfbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfkembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jhitungpanjang))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jhtransaksi)
                        .addComponent(bunga1)
                        .addComponent(jLabel18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bunga, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)))
                .addGap(5, 5, 5))
        );

        tfcarigadai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfcarigadaiKeyTyped(evt);
            }
        });

        jLabel1.setText("Cari dengan Tanggal Jatuh Tempo ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addGap(28, 28, 28)
                        .addComponent(simpantebusan)
                        .addGap(18, 18, 18)
                        .addComponent(hapustebusan)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTambahItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapusItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnupdateItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfcarigadai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfcarigadai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnHapusItem)
                        .addComponent(btnTambahItem)
                        .addComponent(btnupdateItem)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(simpantebusan)
                    .addComponent(hapustebusan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfJ_pinjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfJ_pinjamanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfJ_pinjamanActionPerformed

    private void tfJ_pinjamanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfJ_pinjamanKeyTyped

    }//GEN-LAST:event_tfJ_pinjamanKeyTyped

    private void btnTambahItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahItemActionPerformed
        // TODO add your handling code here:
//        save();
        addGadai();
//        cetak();
    }//GEN-LAST:event_btnTambahItemActionPerformed

    private void btnHapusItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusItemActionPerformed
        // TODO add your handling code here:
        deleteGadai();
    }//GEN-LAST:event_btnHapusItemActionPerformed

    private void jtransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtransaksiActionPerformed
        // TODO add your handling code here:

        if (jtransaksi.getSelectedIndex() == 1) {

            cbNasabah.setEnabled(true);
            cbBarang.setEnabled(true);

            jDatejatuh.setEnabled(true);
            tfJ_pinjaman.setEditable(true);
            tfJ_tebusan.setEnabled(true);

            jdt_taggaltebusan.setEnabled(false);
            simpantebusan.setEnabled(false);
            hapustebusan.setEnabled(false);
            btnTambahItem.setEnabled(true);
            btnHapusItem.setEnabled(true);
            btnupdateItem.setEnabled(false);
            jtTotaltebusan.setEnabled(false);
            jBhitung.setEnabled(false);
            jBhitunghari.setEnabled(true);
            jdatagadai.setEnabled(false);
            btnupdateItem.setEnabled(false);
//            jhtransaksi.setEnabled(false);
            jButton1.setEnabled(true);
            loadNasabah();
            loadBarang();
            reset();

//          
//             
        } else if (jtransaksi.getSelectedIndex() == 2) {

            cbNasabah.setEnabled(false);
            cbBarang.setEnabled(false);
            jBhitung.setEnabled(true);
            jdatagadai.setEnabled(true);
            jDatejatuh.setEnabled(false);
            tfJ_pinjaman.setEditable(false);
            tfJ_tebusan.setEnabled(true);

            jdt_taggaltebusan.setEnabled(true);
            simpantebusan.setEnabled(false);
            hapustebusan.setEnabled(true);
            btnTambahItem.setEnabled(false);
            btnHapusItem.setEnabled(false);
            btnupdateItem.setEnabled(false);
            jtTotaltebusan.setEnabled(false);
            jBhitunghari.setEnabled(false);
            jButton1.setEnabled(true);
//            jhtransaksi.setEnabled(false);
            reset();
//            jhitungpanjang.setEnabled(false);
//            reset();
        } else if (jtransaksi.getSelectedIndex() == 3) {

            cbNasabah.setEnabled(false);
            cbBarang.setEnabled(false);
            jBhitung.setEnabled(false);
            jdatagadai.setEnabled(true);
            jDatejatuh.setEnabled(true);
            tfJ_pinjaman.setEditable(false);
            tfJ_tebusan.setEnabled(true);

            jdt_taggaltebusan.setEnabled(false);
            simpantebusan.setEnabled(false);
            hapustebusan.setEnabled(false);
            btnTambahItem.setEnabled(false);
            btnHapusItem.setEnabled(false);
            btnupdateItem.setEnabled(true);
            jtTotaltebusan.setEnabled(false);
            jBhitunghari.setEnabled(false);
            btnupdateItem.setEnabled(true);
//            jhtransaksi.setEnabled(false);
            jButton1.setEnabled(false);
            reset();
        } else {
            aktif();
        }
    }//GEN-LAST:event_jtransaksiActionPerformed

    private void cbNasabahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNasabahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbNasabahActionPerformed

    private void tabelGadaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelGadaiMouseClicked
//        jBhitunghari.setEnabled(false);
//        btnTambahItem.setEnabled(false);
//        ID = tabelGadai.getSelectedRow();
//        tfJ_pinjaman.setText(tabelGadai.getValueAt(ID, 6).toString());
//        try {
//            jDatejatuh.setDate(dateFormat.parse(tabelGadai.getValueAt(ID, 5).toString()));
//        } catch (ParseException ex) {
//            java.util.logging.Logger.getLogger(GadaiForm.class.getName()).log(Level.SEVERE, null, ex);
//        }


    }//GEN-LAST:event_tabelGadaiMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        viewItem();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnupdateItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateItemActionPerformed
        // TODO add your handling code here:
        perpanjang();

    }//GEN-LAST:event_btnupdateItemActionPerformed

    private void simpantebusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpantebusanActionPerformed
        Simpantebusan();
    }//GEN-LAST:event_simpantebusanActionPerformed

    private void jBhitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBhitungActionPerformed
        // TODO add your handling code here:

        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (jdatagadai.getSelectedIndex() > 0) {
                int index = jdatagadai.getSelectedIndex() - 1;
                Gadai gadai = dao.loadGadai().get(index);
                tfJ_tebusan.setText(String.valueOf(gadai.getJumlah_tebusan()));

                Date date2 = dateFormat.parse(String.valueOf(sdf.format(jdt_taggaltebusan.getDate())));
                Date date1 = dateFormat.parse(String.valueOf(gadai.getJatuh_tempo()));

                long selisih = date2.getTime() - date1.getTime();
                int telat = (int) TimeUnit.DAYS.convert(selisih, TimeUnit.MILLISECONDS);
                if (date2.getTime() < date1.getTime()) {
                    JOptionPane.showMessageDialog(null, " Tanggal Jatuh Tempo Tidak Valid");
                } else {
                    if (telat < 7) {
                        jThari.setText(String.valueOf(telat));
                        double denda = telat * 10000;
                        jtdenda.setText(String.valueOf(denda));
                        double Total = denda + (Double.valueOf(tfJ_tebusan.getText()));
                        jtTotaltebusan.setText(String.valueOf(Total));
                        tfbayar.setVisible(true);
////                 
                        tfkembalian.setVisible(true);
                        jhitungpanjang.setVisible(false);
                        jhtransaksi.setVisible(true);
                        jLabel17.setVisible(true);
                        jLabel16.setVisible(true);

                    } else {
                        int index1 = jdatagadai.getSelectedIndex() - 1;
                        Gadai g = dao.loadGadai().get(index1);
                        Gadai gn = new Gadai();
                        gn.setNo_gadai(g.getNo_gadai());
                        gn.setBarang(g.getBarang());
                        gn.setJatuh_tempo(g.getJatuh_tempo());
                        gn.setJumlah_pinjaman(g.getJumlah_pinjaman());
                        gn.setJumlah_tebusan(g.getJumlah_tebusan());
                        gn.setTotal_tebusan(Double.valueOf(jtTotaltebusan.getText()));
                        gn.setNasabah(g.getNasabah());
                        gn.setPetugas(g.getPetugas());
                        gn.setTerbilang(g.getTerbilang());
                        gn.setTgl_gadai(g.getTgl_gadai());
                        gn.setJatuh_tempo(g.getJatuh_tempo());
                        gn.setDenda(Double.valueOf(jtdenda.getText()));

                        gn.setKeterangan("Milik Pegadaian");

                        gn.setTgl_tebusan(dateFormat.format(jdt_taggaltebusan.getDate()));
                        jThari.setText(String.valueOf(telat));
                        double denda = 0;
                        jtdenda.setText(String.valueOf(denda));
                        double Total = 0;
                        jtTotaltebusan.setText(String.valueOf(Total));

                        JOptionPane.showMessageDialog(null, "Barang Sudah Jadi milik Pegadaian");
                        dao.update(gn);
                        viewtebusan();
                        load();
                        reset();

                    }
                }
            }
            return;
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "tanggal kembali belum dipilih");
            JOptionPane.showMessageDialog(null, e);

        }

    }//GEN-LAST:event_jBhitungActionPerformed

    private void jDatejatuhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDatejatuhMousePressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jDatejatuhMousePressed

    private void jDatejatuhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDatejatuhMouseClicked

    }//GEN-LAST:event_jDatejatuhMouseClicked

    private void jDatejatuhComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jDatejatuhComponentAdded

    }//GEN-LAST:event_jDatejatuhComponentAdded

    private void jBhitunghariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBhitunghariActionPerformed
        // TODO add your handling code here:

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {

            int pinjaman = Integer.valueOf(tfJ_pinjaman.getText());
//            Date date1 = dateFormat.parse(tabelGadai.getValueAt(row, 5).toString());
            Date date1 = sdf.parse(String.valueOf(sdf.format(date)));
            Date date2 = sdf.parse(String.valueOf(sdf.format(jDatejatuh.getDate())));

            long selisih = date2.getTime() - date1.getTime();
            int telat = (int) TimeUnit.DAYS.convert(selisih, TimeUnit.MILLISECONDS);;
            if (telat > 14) {
                JOptionPane.showMessageDialog(rootPane, "Batas waktu Gadai" + "\n Tidak Lebih Dari 14 Hari");
                btnTambahItem.setEnabled(false);

            } else if (telat < 0) {
                JOptionPane.showMessageDialog(rootPane, "Tanggal Jatuh Tempo " + "\n Tidak Sesuai");
                btnTambahItem.setEnabled(false);

            } else if (telat <= 14) {
                if (pinjaman < 800000) {
                    int tebusan = pinjaman + ((pinjaman * 10) / 100);
                    tfJ_tebusan.setText(String.valueOf(tebusan));
                    double bunga2 = (pinjaman * 10) / 100;
                    bunga1.setText(String.valueOf(bunga2));
                } else if (pinjaman >= 800000) {
                    int tebusan = pinjaman + ((pinjaman * 10) / 100) + 5000;
                    tfJ_tebusan.setText(String.valueOf(tebusan));
                    double bunga2 = (pinjaman * 10) / 100;
                    bunga1.setText("Rp. " + String.valueOf(bunga2));
                }
                btnTambahItem.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(rootPane, "Tanggal Belum Dipilih ");
                btnTambahItem.setEnabled(false);
            }

            return;
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(rootPane, e);
            JOptionPane.showMessageDialog(rootPane, "Data Belum Diisi Semuanya");

        }
    }//GEN-LAST:event_jBhitunghariActionPerformed

    private void hapustebusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapustebusanActionPerformed
        // TODO add your handling code here:
        deleteTebus();
    }//GEN-LAST:event_hapustebusanActionPerformed

    private void jdatagadaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdatagadaiActionPerformed
        // TODO add your handling code here:
        getgadai();
    }//GEN-LAST:event_jdatagadaiActionPerformed

    private void jThariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jThariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jThariActionPerformed

    private void tfcarigadaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfcarigadaiKeyTyped
        // TODO add your handling code here:
        search();
    }//GEN-LAST:event_tfcarigadaiKeyTyped

    private void jhtransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jhtransaksiActionPerformed
        double total = Double.valueOf(jtTotaltebusan.getText());
        double bayar = Double.valueOf(tfbayar.getText());
        double kembali = bayar - total;
        tfkembalian.setText(String.valueOf(kembali));
        if (kembali < 0) {
            JOptionPane.showMessageDialog(rootPane, "Uang Anda Kurang");
        } else {
            simpantebusan.setEnabled(true);
        }


    }//GEN-LAST:event_jhtransaksiActionPerformed

    private void tfkembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfkembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfkembalianActionPerformed

    private void jhitungpanjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jhitungpanjangActionPerformed
        // TODO add your handling code here:
        int index = jdatagadai.getSelectedIndex() - 1;
        Gadai g = dao.loadGadai().get(index);
        double bayar = Double.valueOf(tfbayar.getText());
        double bunga2 = (g.getJumlah_pinjaman() * 10) / 100;

        if (bunga2 > bayar) {
            JOptionPane.showMessageDialog(rootPane, "Uang Anda Kurang");
        } else {
            tfkembalian.setText(String.valueOf(bayar - bunga2));
            Gadai gn = new Gadai();
            gn.setNo_gadai(g.getNo_gadai());
            gn.setBarang(g.getBarang());
            gn.setJumlah_pinjaman(g.getJumlah_pinjaman());
            gn.setJumlah_tebusan(g.getJumlah_tebusan());
            gn.setTotal_tebusan(g.getTotal_tebusan());
            gn.setNasabah(g.getNasabah());
            gn.setPetugas(g.getPetugas());
            gn.setTerbilang(g.getTerbilang());
            gn.setTgl_gadai(g.getTgl_gadai());
            gn.setJatuh_tempo(dateFormat.format(jDatejatuh.getDate()));
            gn.setDenda(g.getDenda());
            gn.setTgl_tebusan(g.getTgl_tebusan());

            gn.setKeterangan(g.getKeterangan());

//                tfkembalian.setText(String.valueOf(kembali));
            dao.update(gn);

            viewtebusan();
            JOptionPane.showMessageDialog(rootPane, "Perpanjang Tanggal Jatuh Tempo Berhasil");
            load();
            reset();
            cetakpanjang(gn.getNo_gadai());
        }
    }//GEN-LAST:event_jhitungpanjangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnHapusItem;
    private javax.swing.JButton btnTambahItem;
    private javax.swing.JButton btnupdateItem;
    private javax.swing.JLabel bunga;
    public static javax.swing.JLabel bunga1;
    public static javax.swing.JComboBox<String> cbBarang;
    public static javax.swing.JComboBox<String> cbNasabah;
    public static javax.swing.JButton hapustebusan;
    private javax.swing.JButton jBhitung;
    private javax.swing.JButton jBhitunghari;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDatejatuh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTgl_sekarang;
    private javax.swing.JTextField jThari;
    public static javax.swing.JComboBox jdatagadai;
    private com.toedter.calendar.JDateChooser jdt_taggaltebusan;
    private javax.swing.JButton jhitungpanjang;
    private javax.swing.JButton jhtransaksi;
    private javax.swing.JLabel jtTotaltebusan;
    private javax.swing.JTextField jtdenda;
    private javax.swing.JComboBox jtransaksi;
    public static javax.swing.JButton simpantebusan;
    public static javax.swing.JTable tabelGadai;
    public static javax.swing.JTable tabeltebusan;
    public static javax.swing.JTextField tfJ_pinjaman;
    private javax.swing.JTextField tfJ_tebusan;
    private javax.swing.JTextField tfbayar;
    private javax.swing.JTextField tfcarigadai;
    private javax.swing.JTextField tfkembalian;
    // End of variables declaration//GEN-END:variables
}
