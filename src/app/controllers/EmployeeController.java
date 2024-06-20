package app.controllers;

import app.dao.EmployeeDao;
import app.helpers.Status;
import app.records.Employee;

import java.sql.SQLException;

public class EmployeeController {

    private final EmployeeDao employeeDao;

    public EmployeeController(){
        this.employeeDao = new EmployeeDao();
    }

    public Employee getEmployee(int userId){
        try{
           return this.employeeDao.getEmployee(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void replenishAtmMoney(double amount){
        try {
            Employee employee = getEmployee(Status.getInstance().getUserId());
            AtmController atmController = new AtmController();

            employeeDao.beginTransaction();

            double newAmount = atmController.getAtm(Status.ID_ATM).money() + amount;
            atmController.updateMoney(Status.ID_ATM,newAmount);
            atmController.newAtmTransaction(employee.id(),amount);

            employeeDao.commit();

        } catch (SQLException e) {

            try{
                employeeDao.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}
