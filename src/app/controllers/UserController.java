package app.controllers;

import app.dao.UserDao;
import app.helpers.Operation;
import app.helpers.Status;
import app.records.User;
import app.records.Wallet;
import org.mindrot.jbcrypt.BCrypt;
import view.Register;
import view.Window;

import javax.swing.*;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.UUID;

public class UserController {

    private final WalletController walletController;
    private final UserDao userDao; //No es final porque se rompe con mockito.
    private final TransactionController transactionController;

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

    /**
     * Returns the current user, set in Status.userId
     * */
    public User getUser(){
        try{
            return userDao.getUser(Status.getInstance().getUserId(), "id");
        }
        catch (SQLException | NoSuchElementException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public User getUser(int id){
        try{
            return userDao.getUser(id, "id");
        }
        catch (SQLException | NoSuchElementException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public User getUser(String code){
        try{
            return userDao.getUser(code,"code");
        }
        catch (SQLException | NoSuchElementException e){
            System.out.println(e.getMessage());
        }
        return null;

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
            throw new RuntimeException("Hay que laburar, che");
        }
        if(status.getUserCode().equals(userCode)){
            return false;
        }

        int targetId = this.getUser(userCode).id();
        int userId = Status.getInstance().getUserId();
        int currentUserWalletId = this.getUserWallet(userId).id();
        int targetWalletId = this.getUserWallet(targetId).id();

        try {
            userDao.beginTransaction();
            walletController.updateBalance(currentUserWalletId,amount, Operation.WITHDRAW);
            walletController.updateBalance(targetWalletId,amount, Operation.DEPOSIT);
            transactionController.create(amount,currentUserWalletId, targetWalletId);
            userDao.commit();
        } catch (Exception e) {
            try{
                System.out.println("Ocurrio un error en la operación, por favor, intentelo más tarde");
                System.out.println(e.getMessage());
                userDao.rollback();
                return false;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }

        return true;
    }

    public void atm(double amount, Operation operation){
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
                System.out.println(ex.getMessage());
            }
        }
    }

    public User register(String name, String surname, String email, String password){
        if(name.isBlank() || surname.isBlank() || email.isBlank() || password.isBlank()){
            JOptionPane.showMessageDialog(null,"Debe ingresar todos los campos", "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        try{
//            try {
//                userDao.getUser(email,"email");
//                return null;
//            }
//            catch (NoSuchElementException ne){
//                System.out.println(ne.getMessage());
//            }

            this.userDao.beginTransaction();
            String code = UUID.randomUUID().toString();

            String hashPass = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = this.userDao.createUser(name,surname,email,hashPass,code);
            walletController.createWallet(0,user.id());
            userDao.commit();
            Auth auth = new Auth();
            auth.login(user.email(),password);
            return user;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            String message = "No se pudo crear el usuario, por favor vuelva a intentarlo";
            if(e.getMessage().contains("Duplicate entry")){
                message = "Este email ya está registrado";
            }
            JOptionPane.showMessageDialog(null, message, "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


}
