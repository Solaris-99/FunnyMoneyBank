package app.controllers;

import app.dao.WalletDao;
import app.helpers.Operation;
import app.helpers.Status;
import app.records.Wallet;

import java.sql.SQLException;

public class WalletController {

    private WalletDao walletDao;

    public WalletController(){
        walletDao = new WalletDao();
    }

    public Wallet getWallet(int user_id, boolean isUserId){
        try {
            return walletDao.getWallet(user_id,isUserId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update the wallet's balance.
     * @param walletId The id of the wallet.
     * @param amount The amount of money of the operation.
     * @param operation The operation to execute: only DEPOSIT or WITHDRAW allowed <br>
     * This method does not create a new Transaction register - use UserController instead.
     * */
    public void updateBalance(int walletId,double amount, Operation operation){
        if(operation == Operation.TRANSFER || operation == Operation.REPLENISH){
            throw new IllegalArgumentException("Attempting to Transfer or Replenish on updateBalance");
        }

        UserController userController = new UserController();
        double balance = userController.getUserBalance();
        if(amount > balance){
            //todo: gui
            System.out.println("no tienes fondos suficientes");
            return;
        }

        double newBalance;
        if(operation == Operation.DEPOSIT){
            newBalance = balance + amount;
        }
        else{
            newBalance = balance - amount;

        }

        try {
            this.walletDao.updateBalance(newBalance, walletId);
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] - WalletController - makeDeposit:" + e);
        }
    }



}
