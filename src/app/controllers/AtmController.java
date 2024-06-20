package app.controllers;

import app.dao.AtmDao;
import app.dao.AtmTransactionDao;
import app.helpers.Status;
import app.records.Atm;
import app.records.Employee;

import java.sql.Date;
import java.sql.SQLException;

public class AtmController {

    private final AtmDao atmDao;
    private final AtmTransactionDao atmTransactionDao;

    public AtmController(){
        this.atmDao = new AtmDao();
        this.atmTransactionDao = new AtmTransactionDao();
    }

    public void updateMoney(int id, double amount){
        try{
            this.atmDao.updateMoney(id,amount);
        }
        catch (SQLException e){
            //todo
            throw new RuntimeException(e);
        }
    }

    public Atm getAtm(int id){
        try{
            return this.atmDao.getAtm(id);
        }
        catch (SQLException e){
            //todo
            throw new RuntimeException(e);
        }
    }

    public void newAtmTransaction(int employee_id, double amount){
        try{
            int atmId = Status.ID_ATM;
            Date date = new Date(System.currentTimeMillis());
            atmTransactionDao.create(employee_id,atmId,amount,date);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
