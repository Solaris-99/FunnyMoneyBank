package app.dao;
import app.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class Dao {
    // Is this necessary?
    protected final Connection con;
    protected Record dto;

    public Dao(){
        this.con = DBConnection.getConnection();
    }

    protected abstract List<? extends Record> hydrate(ResultSet res) throws SQLException;

}
