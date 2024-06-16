package app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends Dao{

    public UserDao (){
        super();
    }

    public ResultSet getUser(int id) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM user WHERE id = ?");
        stmt.setInt(1,id);
        return stmt.executeQuery();
    }

    public ResultSet getUser(String email) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM user WHERE email = ?");
        stmt.setString(1,email);
        return stmt.executeQuery();
    }


}
