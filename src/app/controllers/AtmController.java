package app.controllers;

import app.dao.AtmDao;
import app.dao.AtmTransactionDao;
import app.helpers.Operation;
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

    /**
     * Set the money of the atm to amount.
     * @param id The id of the atm to update.
     * @param amount The new amount of money to set to.
     *
    * */
    public void updateMoney(int id, double amount){
        try{
            this.atmDao.updateMoney(id,amount);
        }
        catch (SQLException e){
            //todo
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the money of the atm, depending on the operation.
     * @param id The id of the atm to update.
     * @param amount The amount of money to update into the atm.
     * @param operation The operation being carried out. It can be either DEPOSIT OR WITHDRAW,
     *                  DEPOSIT will sum money into the atm, while WITHDRAW will decrease it.
     * */
    public void updateMoney(int id, double amount, Operation operation){
        if(operation == Operation.TRANSFER || operation == Operation.REPLENISH){
            throw new RuntimeException("Attempting to transfer/replenish on atm updateMoney");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("Amount cannot be less or equal than 0");
        }
        double currentMoney = this.getAtm(id).money();
        if(operation == Operation.WITHDRAW){
            amount *= -1;
        }
        double newMoney = currentMoney + amount;
        updateMoney(id,newMoney);
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
