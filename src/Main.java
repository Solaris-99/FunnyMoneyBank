import app.db.DBConnection;
import view.*;
import app.controllers.*;

public class Main {

    public static void main(String[] args) {
//        Window window = new Window("Banco");
//        Login login = new Login();
//        window.setContent(login);
        Auth auth = new Auth();
        boolean logged = auth.login("pepe@gmail.com","1234");
        System.out.println(logged);
    }
}
