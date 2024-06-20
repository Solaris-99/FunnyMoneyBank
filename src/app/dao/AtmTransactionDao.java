package app.dao;
import app.records.AtmTransaction;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtmTransactionDao extends Dao{


    public AtmTransactionDao (){
        super();
    }


    public List<AtmTransaction> getTransactions() throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM atm_transaction");
        return this.hydrate(stmt.executeQuery());
    }

    @Override
    protected List<AtmTransaction> hydrate(ResultSet res) throws SQLException {
        List<AtmTransaction> transactions = new ArrayList<>();
        while(res.next()){
            AtmTransaction transaction = new AtmTransaction(
                    res.getInt("id"),
                    res.getInt("id_employee"),
                    res.getInt("id_atm"),
                    res.getDouble("amount"),
                    res.getDate("date")
            );
            transactions.add(transaction);
        }
        return transactions;
    }

    public void create(int id_employee, int id_atm, double amount, Date date ) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("INSERT INTO atm_transaction(id_employee, id_atm, amount, date) VALUES (?, ?, ?, ?)");
        stmt.setInt(1,id_employee);
        stmt.setInt(2,id_atm);
        stmt.setDouble(3,amount);
        stmt.setDate(4,date);
        stmt.executeUpdate();
    }

}
