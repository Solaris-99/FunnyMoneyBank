package view;

import app.controllers.UserController;
import app.records.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register implements Viewable {
    private JPanel content;
    private JLabel titleLabel;
    private JPanel form;
    private JLabel emailLabel;
    private JTextField email;
    private JLabel passwordLabel;
    private JPasswordField password;
    private JButton registerButton;
    private JTextField name;
    private JTextField surname;
    private JButton toLogin;
    private JPanel title;



    private void makeFunctional() {
        toLogin.addActionListener(new HyperLink<>(new Login()));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserController userController = new UserController();
                String emailText = email.getText();
                String passwordText = new String(password.getPassword()); //pasar como argumento y luego hashear.
                String nameText = name.getText();
                String surnameText = surname.getText();
                if( userController.register(nameText,surnameText,emailText,passwordText) != null){
                    Window.goTo(new UserMenu());
                }
            }
        });


    }

    @Override
    public JPanel getContent() {
        makeFunctional();
        return this.content;
    }

}
