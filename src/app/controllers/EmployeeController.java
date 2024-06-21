package app.controllers;

import app.dao.EmployeeDao;
import app.helpers.Status;
import app.records.Employee;
import app.records.User;

import java.sql.SQLException;

public class EmployeeController {

    private final EmployeeDao employeeDao;

    public EmployeeController(){
        this.employeeDao = new EmployeeDao();
    }

    public Employee getEmployee(int userId){
        try{
           return this.employeeDao.getEmployee(userId, "user_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(int employeeId){
        try {
            UserController userController = new UserController();
            Employee employee = this.employeeDao.getEmployee(employeeId, "id");
            return userController.getUser(employee.id_user());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void replenishAtmMoney(double amount){
        try {
            Employee employee = getEmployee(Status.getInstance().getUserId());
            AtmController atmController = new AtmController();

            employeeDao.beginTransaction();

            double newAmount = atmController.getAtm(Status.ID_ATM).money() + amount;
            System.out.println(newAmount);
            atmController.updateMoney(Status.ID_ATM,newAmount);
            atmController.newAtmTransaction(employee.id(),amount);

            employeeDao.commit();

        } catch (SQLException e) {

            try{
                System.out.println("error, rolling back on replenish");
                employeeDao.rollback();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


}
