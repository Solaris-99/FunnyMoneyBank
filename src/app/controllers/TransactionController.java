package app.controllers;

import app.dao.TransactionDao;
import app.records.Transaction;
import java.util.List;
import java.util.ArrayList;

public class TransactionController {

    private TransactionDao transactionDao;

    public TransactionController(){
        transactionDao = new TransactionDao();
    }

    public List<Transaction> getAll(){

    }


}