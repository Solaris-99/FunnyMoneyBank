package app.controllers;

import app.dao.UserDao;
import app.helpers.Operation;
import app.helpers.Status;
import app.records.User;
import app.records.Wallet;

import javax.swing.*;
import java.sql.SQLException;

public class UserController {

    private WalletController walletController;
    private UserDao userDao;
    private TransactionController transactionController;

    public UserController(){
        walletController = new WalletController();
        userDao = new UserDao();
        transactionController = new TransactionController();
    }

    public double getUserBalance(){
        return this.getUserWallet().balance();
    }

    public Wallet getUserWallet(){
        return walletController.getWallet(Status.getInstance().getUserId(),true);
    }

    public Wallet getUserWallet(int userId){
        return walletController.getWallet(userId, true);
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

    public User getUserByCode(String code){
        try{
            return userDao.getUserByCode(code);
        }
        catch (SQLException e){
            //TODO
            throw new RuntimeException(e);
        }

    }


    /**
     * Make a transference, from the status' user id to
     * the target user_id passed in.
     * @param userCode the id of the user to transfer to.
     * @param amount the amount of money to transfer.
     * */
    public boolean makeTransference(String userCode, double amount){
        Status status = Status.getInstance();
        if(status.isEmployee()){
            throw new RuntimeException("YOU SHOULD NOT BE HERE!");
        }
        if(status.getUserCode() == userCode){
            return false;
        }

        int targetId = this.getUserByCode(userCode).id();
        int userId = Status.getInstance().getUserId();
        int currentUserWalletId = this.getUserWallet(userId).id();
        int targetWalletId = this.getUserWallet(targetId).id();

        try {
            userDao.beginTransaction();
            walletController.updateBalance(currentUserWalletId,amount, Operation.WITHDRAW);
            walletController.updateBalance(targetWalletId,amount, Operation.DEPOSIT);
            transactionController.create(amount,currentUserWalletId, targetWalletId);
            userDao.commit();
        } catch (SQLException e) {
            try{
                userDao.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        return true;
    }

    public void atm(double amount, Operation operation){
        if(operation == Operation.TRANSFER){
            throw new IllegalArgumentException("Attempting to Transfer on ATM");
        }
        if(amount < 10){
            JOptionPane.showMessageDialog(null, "El monto minimo para operar es 10","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            userDao.beginTransaction();
            int userId = Status.getInstance().getUserId();
            int walletId = this.getUserWallet(userId).id();
            this.walletController.updateBalance(walletId,amount,operation);
            transactionController.create(amount,walletId,operation);
            userDao.commit();
        } catch (SQLException e) {
            try{
                userDao.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}
