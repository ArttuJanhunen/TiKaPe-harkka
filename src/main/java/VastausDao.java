
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author arttu
 */
public class VastausDao implements Dao<Vastaus, Integer> {

    private Database database;
    private Dao<Vastaus, Integer> vastausDao;

    public VastausDao(Database database) {
        this.database = database;
        this.vastausDao = vastausDao;
    }

    @Override
    public Vastaus findOne(Integer key) throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Vastaus uusi = new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastausteksti"), rs.getBoolean("oikein"));
        stmt.close();
        rs.close();
        connection.close();

        return uusi;
    }

    @Override
    public List<Vastaus> findAll() throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus");
        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList();
        while (rs.next()) {
            Vastaus uusi = new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastausteksti"), rs.getBoolean("oikein"));
            vastaukset.add(uusi);
        }
        stmt.close();
        rs.close();
        connection.close();

        return vastaukset;
    }

    @Override
    public Vastaus saveOrUpdate(Vastaus object) throws Exception {
        return save(object);
    }

    @Override
    public void delete(Integer key) throws Exception {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    private Vastaus save(Vastaus vastaus) throws Exception {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus"
                + " ( kysymys_id, vastausteksti, oikein)"
                + " VALUES (?, ?, ?)");
        stmt.setInt(1, vastaus.getKysymysId());
        stmt.setString(2, vastaus.getTeksti());
        stmt.setBoolean(3, vastaus.getArvo());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Vastaus"
                + " WHERE vastausteksti = ?");
        stmt.setString(1, vastaus.getTeksti());

        ResultSet rs = stmt.executeQuery();
        rs.next();

        Vastaus uusi = new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastausteksti"), rs.getBoolean("oikein"));

        stmt.close();
        rs.close();

        conn.close();

        return uusi;
    }

    public List<Vastaus> findAllwithKysymysId(int id) throws Exception {
        Connection connection = database.getConnection();
        List<Vastaus> kaikkivastaukset = findAll();
        List<Vastaus> halututvastaukset = new ArrayList();
        for (Vastaus vastaus : kaikkivastaukset) {
            if (vastaus.getKysymysId() == id) {
                halututvastaukset.add(vastaus);
            }
        }
        return halututvastaukset;
    }

}
