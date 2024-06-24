package test;
import app.controllers.WalletController;
import app.dao.WalletDao;
import app.records.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public class WalletControllerTest {
    @Mock
    private WalletDao walletDao;

    @InjectMocks
    private WalletController walletController;

    @BeforeEach
    public void setUp() throws IllegalAccessException, NoSuchFieldException, SQLException {
        MockitoAnnotations.openMocks(this);
        walletController = new WalletController();
        Field walletDaoField = WalletController.class.getDeclaredField("walletDao");
        walletDaoField.setAccessible(true);
        walletDaoField.set(walletController, walletDao);
        when(walletDao.getWallet(1,true)).thenReturn(new Wallet(2,100,1));
        when(walletDao.getWallet(1,false)).thenReturn(new Wallet(1,30,2));
    }

    @Test
    public void getWalletTest(){
        Wallet walletByUserId = walletController.getWallet(1,true);
        Wallet walletById = walletController.getWallet(1, false);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2,walletByUserId.id()),
                () -> Assertions.assertEquals(100,walletByUserId.balance()),
                () -> Assertions.assertEquals(1,walletByUserId.id_user()),
                () -> Assertions.assertEquals(1,walletById.id()),
                () -> Assertions.assertEquals(30,walletById.balance()),
                () -> Assertions.assertEquals(2,walletById.id_user())



        );


    }

}
