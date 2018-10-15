
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
public class KysymysDao implements Dao<Kysymys, Integer> {

    private Database database;
    private Dao<Kysymys, Integer> kysymysDao;

    public KysymysDao(Database database) {
        this.database = database;
        this.kysymysDao = kysymysDao;
    }

    @Override
    public Kysymys findOne(Integer key) throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Kysymys uusi = new Kysymys(rs.getInt("id"), rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysymysteksti"));
        stmt.close();
        rs.close();
        connection.close();

        return uusi;
    }

    @Override
    public List<Kysymys> findAll() throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys");
        ResultSet rs = stmt.executeQuery();
        List<Kysymys> kysymykset = new ArrayList();
        while (rs.next()) {
            Kysymys uusi = new Kysymys(rs.getInt("id"), rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysymysteksti"));
            kysymykset.add(uusi);
        }
        stmt.close();
        rs.close();
        connection.close();

        return kysymykset;
    }

    @Override
    public Kysymys saveOrUpdate(Kysymys object) throws Exception {
        return save(object);
    }

    @Override
    public void delete(Integer key) throws Exception {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    private Kysymys save(Kysymys kysymys) throws Exception {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys"
                + " (kurssi, aihe, kysymysteksti)"
                + " VALUES (?, ?, ?)");
        stmt.setString(1, kysymys.getKurssi());
        stmt.setString(2, kysymys.getAihe());
        stmt.setString(3, kysymys.getKysymysteksti());

        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("SELECT * FROM Kysymys"
                + " WHERE kurssi = ?"
                + " AND aihe = ? AND kysymysteksti = ?");
        stmt.setString(1, kysymys.getKurssi());
        stmt.setString(2, kysymys.getAihe());
        stmt.setString(3, kysymys.getKysymysteksti());

        ResultSet rs = stmt.executeQuery();
        rs.next();

        Kysymys uusi = new Kysymys(rs.getInt("id"), rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysymysteksti"));

        stmt.close();
        rs.close();

        conn.close();

        return uusi;
    }

}
