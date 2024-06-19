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
            Transaction transaction = new Transaction(res.getInt(1),res.getDouble(2),res.getDate(3),res.getInt(4),res.getInt(5));
            transactions.add(transaction);
        }
        return transactions;
    }

    public void create(double amount, Date date, int id_wallet, int id_transaction_type) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("INSERT INTO transaction(amount, date, id_wallet, id_transaction_type) VALUES (?, ?, ?, ?)");
        stmt.setDouble(1,amount);
        stmt.setDate(2, date);
        stmt.setInt(3,id_wallet);
        stmt.setInt(4, id_transaction_type);
        stmt.executeQuery();
    }

}
