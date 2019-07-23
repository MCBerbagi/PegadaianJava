/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jneferboy
 */
public class Koneksi {

    ConnectionSource csInit = null;

    //konfigurasi koneksi ke mysql server
    public static ConnectionSource cs() {
        String dbName = "pegadaian";
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        String user = "root";
        String pass = "catur123";

        //inisiasi sumber koneksi
        ConnectionSource csInit = null;
        try {
            csInit = new JdbcConnectionSource(dbUrl, user, pass);
        } catch (SQLException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }

        //kembalikan hasil koneksi
        return csInit;

    }

    public Koneksi() {

        String dbName = "pegadaian";
        String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
        String user = "root";
        String pass = "catur123";

        //inisiasi sumber koneksi
        try {
            csInit =  new JdbcConnectionSource(dbUrl, user, pass);
        } catch (SQLException ex) {
            Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
        }

        //kembalikan hasil koneksi
    }

    public Connection getConnection() {
        return (Connection) csInit;
    }

}
