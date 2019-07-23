/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author twins
 */
public class KoneksiReport {
    private String Database="pegadaian";
    private String Server="jdbc:mysql://localhost/"+Database;
    private String Username="root";
    private String Password="catur123";
    private Connection kon=null;
    private Statement statmennt=null;
    public KoneksiReport() throws ClassNotFoundException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            kon=(Connection) DriverManager.getConnection(Server,Username,Password);
            statmennt=kon.createStatement();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Gagal koneksi ke database"+e.getMessage());
        }
    }
    public Connection getkon(){
        return kon;
    }
    
}
