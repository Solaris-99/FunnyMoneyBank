package app.controllers;

import app.dao.UserDao;
import app.dao.EmployeeDao;
import app.helpers.Status;
import app.records.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Auth {

    private final UserDao userDao;
    private final EmployeeDao employeeDao;


    public Auth(){
        this.userDao = new UserDao();
        this.employeeDao = new EmployeeDao();
    }

    public boolean login(String userEmail, String password){
        try {
            User user = userDao.getUser(userEmail, "email");
            boolean isEmployee = employeeDao.isEmployee(user.id());
            if(BCrypt.checkpw(password,user.password())){
                Status status = Status.getInstance();
                status.setLogged(true);
                status.setUserId(user.id());
                status.setEmployee(isEmployee);
                status.setUserCode(user.code());
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (NoSuchElementException e){
            System.out.println("Email/password incorrectos");
        }
        return false;
    }

    public static void logout(){
        Status status = Status.getInstance();
        status.setUserId(-1);
        status.setEmployee(false);
        status.setLogged(false);
        status.setUserCode(null);
    }


}
