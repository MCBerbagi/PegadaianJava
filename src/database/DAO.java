/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jneferboy
 */
public interface DAO {

    public void insert(Object o);

    public void update(Object o);

    public void delete(int id);

    public DefaultTableModel selectAll();

    public DefaultTableModel search(String key);

    public void getreport(String file);

}
