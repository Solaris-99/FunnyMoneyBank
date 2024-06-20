package app.dao;

import app.records.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class TransactionDao extends Dao{

    public TransactionDao (){
        super();
    }


    public List<Transaction> getTransactions(int walletId) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM transaction WHERE id_wallet = ?");
        stmt.setInt(1,walletId);
        return this.hydrate(stmt.executeQuery());
    }
    public List<Transaction> getTransactions() throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM transaction");
        return this.hydrate(stmt.executeQuery());
    }

    @Override
    protected List<Transaction> hydrate(ResultSet res) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        while(res.next()){
            Transaction transaction = new Transaction(
                    res.getInt("id"),
                    res.getDouble("amount"),
                    res.getDate("date"),
                    res.getInt("id_transaction_type"),
                    res.getInt("id_atm"),
                    res.getInt("id_wallet"),
                    res.getInt("id_wallet_target")
            );
            transactions.add(transaction);
        }
        return transactions;
    }

    public void create(double amount, Date date, int id_transaction_type,int id_atm, int id_wallet, int id_wallet_target ) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("INSERT INTO transaction(amount, date, id_transaction_type, id_atm, id_wallet, id_wallet_target) VALUES (?, ?, ?, ?, ?, ?)");
        stmt.setDouble(1,amount);
        stmt.setDate(2,date);
        stmt.setInt(3,id_transaction_type);
        stmt.setInt(4,id_atm);
        stmt.setInt(5,id_wallet);
        stmt.setInt(6,id_wallet_target);
        stmt.executeQuery();
    }

    public void create(double amount, Date date, int id_transaction_type,int id_atm, int id_wallet ) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("INSERT INTO transaction(amount, date, id_transaction_type, id_atm, id_wallet) VALUES (?, ?, ?, ?, ?)");
        stmt.setDouble(1,amount);
        stmt.setDate(2,date);
        stmt.setInt(3,id_transaction_type);
        stmt.setInt(4,id_atm);
        stmt.setInt(5,id_wallet);
        stmt.executeQuery();
    }
}
