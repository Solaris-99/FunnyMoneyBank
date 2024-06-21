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
        PreparedStatement stmt = this.con.prepareStatement("SELECT id FROM employee WHERE id_user = ?");
        stmt.setInt(1,userId);
        ResultSet res = stmt.executeQuery();
        return res.isBeforeFirst();
    }

    public Employee getEmployee(int id, String column) throws SQLException{
        PreparedStatement stmt = this.con.prepareStatement("SELECT * FROM employee WHERE "+ column +" = ?");
        stmt.setInt(1,id);
        ResultSet res = stmt.executeQuery();
        return this.hydrate(res).getFirst();
    }

    @Override
    protected List<Employee> hydrate(ResultSet res) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while(res.next()){
            Employee employee = new Employee(
                    res.getInt("id"),
                    res.getInt("id_user"),
                    res.getDouble("salary")
            );
            employees.add(employee);
        }
        return employees;
    }
}
