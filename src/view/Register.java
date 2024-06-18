package view;

import javax.swing.*;

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
    }

    @Override
    public JPanel getContent() {
        makeFunctional();
        return this.content;
    }

}
