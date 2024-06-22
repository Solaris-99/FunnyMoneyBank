package test;

import app.controllers.AtmController;
import app.controllers.EmployeeController;
import app.dao.AtmDao;
import app.dao.AtmTransactionDao;
import app.records.Atm;
import app.records.AtmTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class AtmControllerTest {

    @Mock
    private AtmDao atmDao;
    @Mock
    private AtmTransactionDao atmTransactionDao;

    @InjectMocks
    private AtmController atmController;

    private Atm atmMock;
    private AtmTransaction atmTransactionMock;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException, SQLException {
        MockitoAnnotations.openMocks(this);
        atmController = new AtmController();
        Field atmDaoField = AtmController.class.getDeclaredField("atmDao");
        Field atmTransactionDaoField = AtmController.class.getDeclaredField("atmTransactionDao");
        atmDaoField.setAccessible(true);
        atmTransactionDaoField.setAccessible(true);
        atmDaoField.set(atmController, atmDao);
        atmTransactionDaoField.set(atmController,atmTransactionDao);
        atmMock = new Atm(1,100);
        atmTransactionMock = new AtmTransaction(1,1,1,100,new Date(System.currentTimeMillis()));
        when(atmDao.getAtm(1)).thenReturn(atmMock);
    }

    @Test
    public void getAtmTest(){
        Atm atm = atmController.getAtm(1);
        Assertions.assertAll(
                ()-> Assertions.assertEquals(atmMock.id(),atm.id()),
                ()-> Assertions.assertEquals(atmMock.money(),atm.money())
        );
    }

}
