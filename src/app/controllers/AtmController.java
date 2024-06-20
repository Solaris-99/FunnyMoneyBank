package app.controllers;

import app.dao.AtmDao;
import app.records.Atm;

import java.sql.SQLException;

public class AtmController {

    private final AtmDao atmDao;

    public AtmController(){
        this.atmDao = new AtmDao();
    }

    public void UpdateMoney(int id, double amount){
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



}
