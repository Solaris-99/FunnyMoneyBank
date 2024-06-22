package test;

import app.controllers.EmployeeController;
import app.controllers.TransactionController;
import app.dao.TransactionDao;
import app.helpers.Operation;
import app.helpers.Status;
import app.records.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class TransactionControllerTest {
    @Mock
    private TransactionDao transactionDao;
    private List<Transaction> transactionsMock;
    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException, SQLException {
        MockitoAnnotations.openMocks(this);
        transactionController = new TransactionController();
        Field transactionDaoField = TransactionController.class.getDeclaredField("transactionDao");
        transactionDaoField.setAccessible(true);
        transactionDaoField.set(transactionController, transactionDao);
        transactionsMock = new ArrayList<>();
        transactionsMock.add(new Transaction(1,100,new Date(System.currentTimeMillis()),1,1,1,0));
        when(transactionDao.getTransactions()).thenReturn(transactionsMock);


    }

    @Test
    public void getTransactionTest(){
        List<Transaction> transactions = transactionController.getTransactions();
        Transaction transactionMock = transactionsMock.getFirst();
        Transaction transaction = transactions.getFirst();
        Assertions.assertAll(
                () -> Assertions.assertEquals(transactionMock.id(),transaction.id()),
                () -> Assertions.assertEquals(transactionMock.amount(),transaction.amount()),
                () -> Assertions.assertEquals(transactionMock.date(),transaction.date()),
                () -> Assertions.assertEquals(transactionMock.id_transaction_type(),transaction.id_transaction_type()),
                () -> Assertions.assertEquals(transactionMock.id_atm(),transaction.id_atm()),
                () -> Assertions.assertEquals(transactionMock.id_wallet(),transaction.id_wallet()),
                () -> Assertions.assertEquals(transactionMock.id_wallet_target(),transaction.id_wallet_target())
        );
    }

    @Test
    public void getArrayTableTest(){
        Transaction transactionMock = transactionsMock.getFirst();
        Status.getInstance().setEmployee(true); // setteo que es un empleado así usa el metodo mockeado del transactiondao.
        String[][] tableMock = {{"$100.0",transactionMock.date().toString(), Operation.fromId(transactionMock.id_transaction_type()).toString()}};
        String[][] table = transactionController.getArrayTable();
        Assertions.assertAll(
                () -> Assertions.assertEquals(tableMock[0][0],table[0][0]),
                () -> Assertions.assertEquals(tableMock[0][1],table[0][1]),
                () -> Assertions.assertEquals(tableMock[0][2],table[0][2])
        );
    }

    @Test
    public void getStatisticsTest() throws SQLException {
        when(transactionDao.getTransactions(1,"id_transaction_type")).thenReturn(transactionsMock);
        when(transactionDao.getTransactions(2,"id_transaction_type")).thenReturn(transactionsMock);
        when(transactionDao.getTransactions(3,"id_transaction_type")).thenReturn(transactionsMock);
        String resMock = "<html>Estadisticas:\n" + "Retiros: " + 1 + " totales, " + Math.round((double)1/3*100) + "%<br>" +
                "Depositos: " + 1 + " totales, " + Math.round((double) 1/3*100) + "%<br>" + //<br> goes brrrrrrrr
                "Retiros: " + 1 + " totales, " + Math.round((double)1/3*100) + "%</html>";
        Assertions.assertEquals(resMock,transactionController.getStatistics());
    }
}
