package app.controllers;

import app.dao.TransactionDao;
import app.helpers.Operation;
import app.helpers.Status;
import app.records.Transaction;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class TransactionController {

    private final TransactionDao transactionDao;

    public TransactionController(){
        transactionDao = new TransactionDao();
    }

    public List<Transaction> getTransactions(int wallet_id){
        try {
            return transactionDao.getTransactions(wallet_id,"id_wallet");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> getTransactions(){
        try {
            return transactionDao.getTransactions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getArrayTable(){
        UserController userController = new UserController();
        List<Transaction> transactions;
        Status status = Status.getInstance();
        if(status.isEmployee()){
            transactions = this.getTransactions();
        }
        else{
            transactions = this.getTransactions(userController.getUserWallet().id());
        }

        String[][] array = new String[transactions.size()][3];  //3 columnas
        for (int i = 0; i < transactions.size(); i++){
            Transaction t = transactions.get(i);
            String[] a = new String[]{"$" + t.amount(), t.date().toString(), Operation.fromId(t.id_transaction_type()).toString() };
            array[i] = a;
        }
        return array;
    }

    public void create(double amount, int id_wallet, Operation operation){
        try{
            Date date = new Date(System.currentTimeMillis());
            this.transactionDao.create(amount,date,operation.getId(),Status.ID_ATM,id_wallet);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void create(double amount, int id_wallet, int id_wallet_target){
        try{
            Date date = new Date(System.currentTimeMillis());
            this.transactionDao.create(amount,date,Operation.TRANSFER.getId(), Status.ID_ATM,id_wallet,id_wallet_target);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String getStatistics(){
        try{
            int withdrawAmount = transactionDao.getTransactions(1,"id_transaction_type").size();
            int depositAmount = transactionDao.getTransactions(2,"id_transaction_type").size();
            int transferAmount = transactionDao.getTransactions(3,"id_transaction_type").size();
            int totalAmount = withdrawAmount + depositAmount + transferAmount;
            double withdrawPercent = (double) withdrawAmount/totalAmount * 100;
            double depositPercent = (double) depositAmount/totalAmount * 100;
            double transferPercent = (double) transferAmount/totalAmount* 100;

            return "<html>Estadisticas:\n" + "Retiros: " + withdrawAmount + " totales, " + Math.round(withdrawPercent) + "%<br>" +
                    "Depositos: " + depositAmount + " totales, " + Math.round(depositPercent) + "%<br>" + //<br> goes brrrrrrrr
                    "Retiros: " + transferAmount + " totales, " + Math.round(transferPercent) + "%</html>";
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Algo salió mal generando las estadísticas","Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }



}


