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
        try {
            Wallet wallet = walletDao.getWallet(Status.getInstance().getUserId(),true);
            return wallet.balance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }




}
