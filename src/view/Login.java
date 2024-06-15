package view;
import javax.swing.*;
import java.awt.*;

public class Login extends JPanel{

    public Login(){
//        BoxLayout layout = new BoxLayout(this,BoxLayout.Y_AXIS);
//        this.setLayout(layout);
        JPanel emailPanel = new JPanel();
        JLabel emailLabel = new JLabel("Ingrese su email: ");
        JTextField emailInput = new JTextField(50);
        emailPanel.add(emailLabel);
        emailPanel.add(emailInput);

        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("Ingrese su contraseña: ");
        JTextField passwordInput = new JTextField(50);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordInput);
        add(emailPanel);
        add(passwordPanel);

        JButton login = new JButton("Ingresar");

        add(login);
    }

}
