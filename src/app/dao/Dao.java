package app.dao;
import app.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Dao {
    // Is this necessary?
    protected final Connection con;

    public Dao(){
        this.con = DBConnection.getConnection();
    }

}
