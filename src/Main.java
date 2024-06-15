import app.db.DBConnection;
import view.*;

public class Main {

    public static void main(String[] args) {
        Window window = new Window("Banco");
        Login login = new Login();
        window.setContent(login);
    }
}
