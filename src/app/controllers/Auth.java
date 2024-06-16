package app.controllers;

import app.dao.UserDao;
import app.dao.EmployeeDao;
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
            System.out.println(user);
            if(password.equals(user.password())){
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




}
