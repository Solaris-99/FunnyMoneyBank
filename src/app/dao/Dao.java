package app.dao;
import app.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class Dao {

    protected final Connection con;

    public Dao(){
        this.con = DBConnection.getConnection();
    }

    protected abstract List<? extends Record> hydrate(ResultSet res) throws SQLException;

    public void beginTransaction() throws SQLException {
        this.con.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        this.con.commit();
        this.con.setAutoCommit(true);
    }

    public void rollback() throws SQLException {
        this.con.rollback();
    }

}
