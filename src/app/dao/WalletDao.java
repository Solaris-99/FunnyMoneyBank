package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletDao extends Dao{

    public WalletDao(){
        super();
    }

    public ResultSet getWallet(int id, boolean isUserId) throws SQLException {
        String sql = "SELECT * FROM wallet WHERE ";
        if(isUserId){
            sql += "id = ?";
        }
        else{
            sql += "user_id = ?";
        }
        PreparedStatement stmt = this.con.prepareStatement(sql);
        stmt.setInt(1,id);
        return stmt.executeQuery();
    }

    public void updateBalance(double balance) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("UPDATE wallet SET balance = ?");
        stmt.setDouble(1, balance);
        stmt.execute();
    }

}
