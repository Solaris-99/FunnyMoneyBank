package app.dao;

import app.records.Wallet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected List<Wallet> hydrate(ResultSet res) throws SQLException {
        List<Wallet> wallets = new ArrayList<>();
        while(res.next()){
            Wallet wallet = new Wallet(res.getInt(1),res.getDouble(2),res.getInt(3));
            wallets.add(wallet);
        }
        return wallets;
    }

}
