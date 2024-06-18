package view;

import app.controllers.Auth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login implements Viewable {
    private JPanel content;
    private JLabel titleLabel;
    private JPanel form;
    private JLabel emailLabel;
    private JTextField email;
    private JPasswordField password;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel error;
    private JPanel title;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public String getEmail() {
        return email.getText();
    }

    public void setError(String error) {
        this.error.setText(error);
    }


    public String getPassword() {
        //TODO: return char[] and handle this on Auth
        // Implement password hashing.
        return new String(password.getPassword());
    }

    private void makeFunctional() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Auth auth = new Auth();
                if (auth.login(getEmail(), getPassword())) {
                    Window.goTo(new UserMenu());
                } else {
                    setError("Email y/o contraseña incorrecto(s)");
                }
            }
        });
        registerButton.addActionListener(new HyperLink<Register>(new Register()));
    }

    @Override
    public JPanel getContent() {
        makeFunctional();
        return content;
    }

}
