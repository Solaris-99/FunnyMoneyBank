package app.dao;

import app.records.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends Dao{

    public UserDao (){
        super();
    }

    /**
     * Get the user based on a single column filter.
     * @param id The value of the filter.
     * @param column The column to filter on.
     * */
    public User getUser(int id, String column) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM user WHERE "+ column + " = ?");
        stmt.setInt(1,id);
        return this.hydrate(stmt.executeQuery()).getFirst();
    }

    /**
     * Get the user based on a single column filter.
     * @param value The value of the filter.
     * @param column The column to filter on.
     * */
    public User getUser(String value, String column) throws SQLException{
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM user WHERE "+ column + " = ?");
        stmt.setString(1,value);
        return this.hydrate(stmt.executeQuery()).getFirst();
    }

    public User createUser(String name, String surname, String email, String password, String code) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("INSERT INTO USER (NAME, SURNAME, EMAIL, PASSWORD, CODE) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, name);
        stmt.setString(2, surname);
        stmt.setString(3, email);
        stmt.setString(4, password);
        stmt.setString(5, code);
        stmt.executeUpdate();
        System.out.println(stmt.getGeneratedKeys());
        ResultSet res = stmt.getGeneratedKeys();
        res.next();
        int id = res.getInt(1);
        return new User(id, name,surname,email,password,code);
    }

    @Override
    protected List<User> hydrate(ResultSet res) throws SQLException {
        List<User> users = new ArrayList<>();
        while(res.next()){
            User user = new User(
                    res.getInt("id"),
                    res.getString("name"),
                    res.getString("surname"),
                    res.getString("email"),
                    res.getString("password"),
                    res.getString("code")
            );
            users.add(user);
        }
        return users;
    }



}
