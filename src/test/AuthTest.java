package test;

import app.controllers.Auth;
import app.dao.EmployeeDao;
import app.dao.UserDao;
import app.helpers.Status;
import app.records.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class AuthTest {
    @Mock
    private UserDao userDao;
    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private Auth auth;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        auth = new Auth();
        Field userDaoField = Auth.class.getDeclaredField("userDao");
        Field employeeDaoField = Auth.class.getDeclaredField("employeeDao");
        userDaoField.setAccessible(true);
        employeeDaoField.setAccessible(true);
        userDaoField.set(auth, userDao);
        employeeDaoField.set(auth, employeeDao);

    }

    @Test
    public void successfulLoginTest() throws SQLException {
        boolean isEmployee = false;
        Status status = Status.getInstance();
        String hash = BCrypt.hashpw("1234", BCrypt.gensalt());
        User mockUser = new User(1,"Pepe","Perez","pepe123@gmail.com",hash,"abc123");
        when(userDao.getUser("pepe123@gmail.com","email")).thenReturn(mockUser);
        when(employeeDao.isEmployee(1)).thenReturn(isEmployee);
        boolean login = auth.login("pepe123@gmail.com","1234");
        Assertions.assertAll(
                () -> Assertions.assertTrue(login),
                () -> Assertions.assertTrue(status.isLogged()),
                () -> Assertions.assertFalse(status.isEmployee()),
                () -> Assertions.assertEquals(1,status.getUserId()),
                () -> Assertions.assertEquals("abc123",status.getUserCode())
        );
    }

    @Test
    public void failedLoginTest() throws SQLException {
        boolean isEmployee = false;
        Status status = Status.getInstance();
        User mockUser = new User(1,"Pepe","Perez","pepe123@gmail.com","1234","abc123");
        when(userDao.getUser("pepe123@gmail.com","email")).thenReturn(mockUser);
        when(employeeDao.isEmployee(1)).thenReturn(isEmployee);
        boolean login = auth.login("pepe123@gmail.com","12345");
        Assertions.assertAll(
                () -> Assertions.assertFalse(login),
                () -> Assertions.assertFalse(status.isLogged()),
                () -> Assertions.assertFalse(status.isEmployee()),
                () -> Assertions.assertNull(status.getUserCode())
        );



    }


}
