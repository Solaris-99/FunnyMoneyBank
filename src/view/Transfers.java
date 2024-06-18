package view;

import javax.swing.*;

public class Transfers implements Viewable {
    private JPanel content;
    private JLabel titleLabel;
    private JPanel title;
    private JButton toMenu;
    private JButton logout;
    private JTable transfers;
    private JLabel message;


    private void makeFunctional(){
        toMenu.addActionListener(new HyperLink<>(new UserMenu()));
    }

    @Override
    public JPanel getContent(){
        makeFunctional();
        return this.content;
    }




}

