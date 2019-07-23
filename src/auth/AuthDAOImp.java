/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import database.Koneksi;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import petugas.Petugas;

/**
 *
 * @author jneferboy
 */
public class AuthDAOImp implements AuthDAO {

    private Dao<Petugas, Integer> dao;

    public AuthDAOImp() {
        try {
            //hubungkan cs dengan dao
            dao = DaoManager.createDao(Koneksi.cs(), Petugas.class);
        } catch (SQLException ex) {
            Logger.getLogger(AuthDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void login(String username, String password,String HAK) {
        try {
            Petugas petugas = dao.queryBuilder().where()
                    .eq("username", username)
                    .and()
                    .eq("Password", password)
                    .and()
                    .eq("Hakakses", HAK)
                    .queryForFirst();
            if(petugas != null){
                Auth.ID = petugas.getNip();
                Auth.NAMA = petugas.getNama();
                Auth.HAK = petugas.getHakakses();
                Auth.AUTH = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    @Override
    public void logout() {
        try {
              Auth.ID = 0;
        Auth.NAMA = null;
        Auth.HAK = null;
        Auth.AUTH = false;
        } catch (Exception e) {
        }
      
    }

}
