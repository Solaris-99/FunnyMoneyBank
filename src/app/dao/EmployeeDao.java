package app.dao;

import app.records.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao extends Dao {

    public EmployeeDao (){
        super();
    }

    public boolean isEmployee(int userId) throws SQLException {
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM employee WHERE user_id = ?");
        stmt.setInt(1,userId);
        ResultSet res = stmt.executeQuery();
        return res.isBeforeFirst();
    }

    protected List<Employee> hydrate(ResultSet res) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while(res.next()){
            Employee employee = new Employee(res.getInt(1),res.getInt(2));
            employees.add(employee);
        }
        return employees;
    }
}
