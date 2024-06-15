package app.db;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static DBConnection instance;
    private Connection con;
    private DBConnection(){
        //TODO
        try{
            String engine = "mysql";
            String address = "localhost";
            String port = "3306";
            String dbName = "TEST"; //TODO: CHANGE DB.
            String user = "root";
            String password = "";
            con = DriverManager.getConnection("jdbc:"+engine+"://"+address+":"+port+"/"+dbName,user,password);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error de conexión");
        }

    }

    public static Connection getConnection(){
        if(instance == null){
            instance = new DBConnection();
        }
        return instance.con;
    }

}
