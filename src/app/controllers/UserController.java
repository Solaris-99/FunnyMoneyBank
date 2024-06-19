package app.controllers;

import app.dao.UserDao;
import app.helpers.Operation;
import app.helpers.Status;
import app.records.User;
import app.records.Wallet;

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

    /**
     * Make a transference, from the status' user id to
     * the target user_id passed in.
     * @param targetId the id of the user to transfer to.
     * @param amount the amount of money to transfer.
     * */
    public void makeTransference(int targetId, double amount){
        //TODO: GET WALLET ID FROM USER, TARGET_ID
        // GET STATUS USER WALLET ID.

        int userId = Status.getInstance().getUserId();
        int currentUserWalletId = this.getUserWallet(userId).id();
        int targetWalletId = this.getUserWallet(targetId).id();

        try {
            userDao.beginTransaction();
            walletController.updateBalance(currentUserWalletId,amount, Operation.WITHDRAW);
            walletController.updateBalance(targetWalletId,amount, Operation.DEPOSIT);
            transactionController.create(amount,currentUserWalletId, Operation.TRANSFER.getId());
            userDao.commit();
        } catch (SQLException e) {
            try{
                userDao.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void atm(double amount, Operation operation){
        if(operation == Operation.TRANSFER || operation == Operation.REPLENISH){
            throw new IllegalArgumentException("Attempting to Transfer or Replenish on ATM");
        }
        try {
            userDao.beginTransaction();
            int userId = Status.getInstance().getUserId();
            int walletId = this.getUserWallet(userId).id();
            this.walletController.updateBalance(walletId,amount,operation);
            transactionController.create(amount,walletId,operation.getId());
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
