import app.db.DBConnection;
import view.*;
import app.controllers.*;

public class Main {

    public static void main(String[] args) {
        Window window = Window.getInstance();
        Login login = new Login();
        Window.goTo(login);
    }

}
