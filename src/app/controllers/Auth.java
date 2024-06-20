package app.controllers;

import app.dao.UserDao;
import app.dao.EmployeeDao;
import app.helpers.Status;
import app.records.User;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Auth {

    private UserDao userDao;
    private EmployeeDao employeeDao;


    public Auth(){
        this.userDao = new UserDao();
        this.employeeDao = new EmployeeDao();
    }

    public boolean login(String userEmail, String password){
        try {
            User user = userDao.getUser(userEmail);
            boolean isEmployee = employeeDao.isEmployee(user.id());
            System.out.println(user);
            if(password.equals(user.password())){
                Status status = Status.getInstance();
                status.setLogged(true);
                status.setUserId(user.id());
                status.setEmployee(isEmployee);
                status.setUserCode(user.code());
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        catch (NoSuchElementException e){
            //todo
            System.out.println("No se encuentra el usuario");
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
