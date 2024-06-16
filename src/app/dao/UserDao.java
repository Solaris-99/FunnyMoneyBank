package app.dao;

import app.records.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends Dao{

    public UserDao (){
        super();
    }

    public User getUser(int id) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM user WHERE id = ?");
        stmt.setInt(1,id);
        return this.hydrate(stmt.executeQuery()).getFirst();
    }

    public User getUser(String email) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM user WHERE email = ?");
        stmt.setString(1,email);
        return this.hydrate(stmt.executeQuery()).getFirst();
    }

    @Override
    protected List<User> hydrate(ResultSet res) throws SQLException {
        List<User> users = new ArrayList<>();
        while(res.next()){
            User user = new User(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5));
            users.add(user);
        }
        return users;
    }


}
