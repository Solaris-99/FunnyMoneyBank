package test;

import app.dao.EmployeeDao;
import app.controllers.EmployeeController;
import app.records.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class EmployeeControllerTest {
    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        employeeController = new EmployeeController();
        Field employeeDaoField = EmployeeController.class.getDeclaredField("employeeDao");
        employeeDaoField.setAccessible(true);
        employeeDaoField.set(employeeController, employeeDao);
    }

    @Test
    public void testGetEmployee() throws SQLException {
        Employee mockedEmployee = new Employee(1,2,100);
        when(employeeDao.getEmployee(2,"user_id")).thenReturn(mockedEmployee);
        Employee employee = employeeController.getEmployee(2);

        Assertions.assertAll(
                () -> Assertions.assertEquals(mockedEmployee.id(),employee.id()),
                () -> Assertions.assertEquals(mockedEmployee.id_user(), employee.id_user()),
                () -> Assertions.assertEquals(mockedEmployee.salary(), employee.salary())
        );


    }

}
