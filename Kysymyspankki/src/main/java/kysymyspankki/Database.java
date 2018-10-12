/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kysymyspankki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Samuli
 */
public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        if(databaseAddress != null && databaseAddress.length() > 0) {
            return DriverManager.getConnection(databaseAddress);
        }
        return DriverManager.getConnection("jdbc:sqlite:kysymyspankki.db");
    }
    
}
