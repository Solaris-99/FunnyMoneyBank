package app.dao;
import app.records.Atm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtmDao extends Dao{

    public AtmDao(){
        super();
    }

    public Atm getAtm(int id) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM atm WHERE id = ?");
        stmt.setInt(1,id);
        return this.hydrate(stmt.executeQuery()).getFirst();
    }

    public void updateMoney(int id, double amount) throws SQLException{
        PreparedStatement stmt = this.con.prepareStatement("UPDATE ATM SET money = ? WHERE id = ?");
        stmt.setDouble(1,amount);
        stmt.setInt(2,id);
        stmt.executeUpdate();
    }

    @Override
    protected List<Atm> hydrate(ResultSet res) throws SQLException {
        List<Atm> atms = new ArrayList<>();
        while(res.next()){
            Atm atm = new Atm(res.getInt("id"),res.getDouble("money"));
            atms.add(atm);
        }
        return atms;
    }

}
