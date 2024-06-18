package app.controllers;

import app.dao.UserDao;
import app.dao.WalletDao;
import app.helpers.Status;
import app.records.User;
import app.records.Wallet;

import java.sql.SQLException;

public class UserController {

    private WalletDao walletDao;
    private UserDao userDao;

    public UserController(){
        walletDao = new WalletDao();
        userDao = new UserDao();
    }

    public double getUserBalance(){
        return this.getUserWallet().balance();
    }

    public Wallet getUserWallet(){
        try {
            return walletDao.getWallet(Status.getInstance().getUserId(),true);
        } catch (SQLException e) {
            //TODO
            throw new RuntimeException(e);
        }
    }

    public User getUser(){
        try{
            return userDao.getUser(Status.getInstance().getUserId());
        }
        catch (SQLException e){
            //TODO
            throw new RuntimeException(e);
        }
    }




}
