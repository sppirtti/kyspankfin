/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kysymyspankki;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuli
 */
public class VastausDao implements Dao<Vastaus, Integer> {

    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {
        List<Vastaus> vastaukset = new ArrayList<>();

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id, vastaus, oikein FROM Vastaus ORDER BY vastaus");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Vastaus v = new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastaus"), rs.getBoolean("oikein"));
            vastaukset.add(v);
        }
        stmt.close();
        rs.close();
        conn.close();

        return vastaukset;

    }

    @Override
    public Vastaus saveOrUpdate(Vastaus vastaus) throws SQLException {
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus (kysymys_id, vastaus,oikein) VALUES (?, ?)");
        stmt.setInt(1, vastaus.getKysymys_id());
        stmt.setString(2, vastaus.getVastaus());
        stmt.setBoolean(3, vastaus.getOikein());
        stmt.executeUpdate();

        PreparedStatement query = conn.prepareStatement("SELECT id, kysymys_id, vastaus, oikein FROM Vastaus WHERE id = (SELECT MAX(id) FROM Vastaus)");
        ResultSet rs = query.executeQuery();
        rs.next();

        vastaus = new Vastaus (rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastaus"), rs.getBoolean("oikein"));

        conn.close();

        return null;
       
    }

    @Override
    public void delete(Integer key) throws SQLException {
       
        Connection conn = database.getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus where kysymys_id = ?");
        stmt.setInt(1, key);
        
        stmt.executeUpdate();
        
        conn.close();
        
    }

}
