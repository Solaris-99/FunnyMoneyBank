package app.controllers;

import app.dao.UserDao;
import app.helpers.Operation;
import app.helpers.Status;
import app.records.User;
import app.records.Wallet;

import javax.swing.*;
import java.sql.SQLException;
import java.util.UUID;

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
        //TODO: ATM ADD/DEC MONEY
        AtmController atmController = new AtmController();
        double atmMoney = atmController.getAtm(Status.ID_ATM).money();
        if(operation == Operation.TRANSFER){
            throw new IllegalArgumentException("Attempting to Transfer on ATM");
        }
        if (amount > atmMoney && operation == Operation.WITHDRAW){
            JOptionPane.showMessageDialog(null, "El monto máximo que este cajero puede facilitar es: $"+atmMoney,"Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(amount < 10){
            JOptionPane.showMessageDialog(null, "El monto mínimo para operar el cajero automático es de $10","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(amount > getUserBalance() && operation == Operation.WITHDRAW){
            JOptionPane.showMessageDialog(null, "Fondos insuficentes.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            userDao.beginTransaction();
            int userId = Status.getInstance().getUserId();
            int walletId = this.getUserWallet(userId).id();
            this.walletController.updateBalance(walletId,amount,operation);
            transactionController.create(amount,walletId,operation);
            atmController.updateMoney(Status.ID_ATM,amount,operation);
            userDao.commit();
        } catch (SQLException e) {
            try{
                userDao.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public User register(String name, String surname, String email, String password){
        if(name.isBlank() || surname.isBlank() || email.isBlank() || password.isBlank()){
            JOptionPane.showMessageDialog(null,"Debe ingresar todos los campos", "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        try{

            this.userDao.beginTransaction();
            String code = UUID.randomUUID().toString();
            User user = this.userDao.createUser(name,surname,email,password,code);
            walletController.createWallet(0,user.id());
            Auth auth = new Auth();
            auth.login(email,password);
            return user;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"No se pudo crear el usuario, por favor vuelva a intentarlo", "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


}
