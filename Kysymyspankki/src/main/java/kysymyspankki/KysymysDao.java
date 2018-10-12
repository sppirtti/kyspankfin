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
public class KysymysDao implements Dao<Kysymys, Integer> {

    private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }

    @Override
    public Kysymys findOne(Integer key) throws SQLException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id, kurssi, aihe, kysymysteksti FROM Kysymys WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();

        boolean hasOne = rs.next();

        if (!hasOne) {
            return null;
        }
        stmt.close();
        rs.close();
        conn.close();

        Kysymys k = new Kysymys(key, rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysmys"));

        return k;

    }

    @Override
    public List<Kysymys> findAll() throws SQLException {
        List<Kysymys> kysymykset = new ArrayList<>();

        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT id, kurssi, aihe, kysymysteksti FROM Kysymys");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Kysymys k = new Kysymys(rs.getInt("id"), rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysymysteksti"));
            kysymykset.add(k);
        }

        stmt.close();
        rs.close();
        conn.close();

        return kysymykset;
    }

    @Override
    public Kysymys saveOrUpdate(Kysymys kysymys) throws SQLException {
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys (kurssi,aihe,kysymysteksti) VALUES (?, ?, ?)");
        stmt.setString(1, kysymys.getKurssi());
        stmt.setString(2, kysymys.getAihe());
        stmt.setString(3, kysymys.getKysymysteksti());
        stmt.executeUpdate();

        PreparedStatement query = conn.prepareStatement("SELECT id, kurssi, aihe, kysymysteksti FROM Kysymys WHERE id = (SELECT MAX(id) FROM Kysymys)");
        ResultSet rs = query.executeQuery();
        rs.next();

        kysymys = new Kysymys(rs.getInt("id"), rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysymysteksti"));

        conn.close();

        return null;

    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");
        stmt.setInt(1, key);

        stmt.executeUpdate();

        conn.close();
    }
    
    public List<Vastaus> findVastaukset (Integer key) throws SQLException {
        Connection conn = database.getConnection();
        
        List <Vastaus> vastaukset = new ArrayList<>();
        
        PreparedStatement stmt = conn.prepareStatement("SELECT Vastaus.id, Vastaus.kysymys_id, Vastaus.vastausteksti,"
                    + " Vastaus.oikein FROM Kysymys, Vastaus WHERE Kysymys.id = ? AND Kysymys.id = Vastaus.kysymys_id");
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                vastaukset.add(new Vastaus(rs.getInt("id"),rs.getInt("kysymys_id"), rs.getString("vastaus"), rs.getBoolean("oikein")));
            }
            
            stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
            stmt.setInt(1, key);
            
            rs = stmt.executeQuery();
            return vastaukset;
    }

}
