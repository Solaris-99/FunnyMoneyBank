package app.controllers;

import app.dao.TransactionDao;
import app.helpers.Operation;
import app.helpers.Status;
import app.records.Transaction;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class TransactionController {

    private final TransactionDao transactionDao;

    public TransactionController(){
        transactionDao = new TransactionDao();
    }

    public List<Transaction> getTransactions(int wallet_id){
        try {
            return transactionDao.getTransactions(wallet_id);
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

}


