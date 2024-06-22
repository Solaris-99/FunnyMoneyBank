package test;
import static org.mockito.Mockito.*;

import app.controllers.UserController;
import app.dao.UserDao;
import app.records.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.sql.SQLException;


public class UserControllerTest {
    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() throws IllegalAccessException, NoSuchFieldException {
        MockitoAnnotations.openMocks(this);
        userController = new UserController();
        Field userDaoField = UserController.class.getDeclaredField("userDao");
        userDaoField.setAccessible(true);
        userDaoField.set(userController, userDao);
    }

    @Test
    public void getUserTest() throws SQLException {
        User mockUser = new User(1,"Pepe","Perez","pepe123@gmail.com","1234","abc123");
        when(userDao.getUser(1,"id")).thenReturn(mockUser);
        User user = userController.getUser(1);
        Assertions.assertAll(
                ()-> Assertions.assertEquals(mockUser.id(),user.id()),
                ()-> Assertions.assertEquals(mockUser.name(),user.name()),
                ()-> Assertions.assertEquals(mockUser.surname(),user.surname()),
                ()-> Assertions.assertEquals(mockUser.email(),user.email()),
                ()-> Assertions.assertEquals(mockUser.password(),user.password()),
                ()-> Assertions.assertEquals(mockUser.code(),user.code())
        );

    }



}
