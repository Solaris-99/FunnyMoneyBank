package view;

import javax.swing.*;

import app.controllers.UserController;
import app.helpers.MoneyOperations;

public class MoneyOperation implements Viewable {
    private JPanel content;
    private JPanel title;
    private JPanel form;
    private JButton confirmarButton;
    private JTextField moneyInput;
    private JLabel money;
    private JButton toMenu;
    private JButton logout;
    private JLabel titleLabel;
    private JPanel target;
    private JTextField targetInput;
    private MoneyOperations operation;

    public MoneyOperation(MoneyOperations operation){
        this.operation = operation;

    }

    @Override
    public JPanel getContent(){
        makeFunctional();
        return this.content;
    }

    private void makeFunctional(){
        toMenu.addActionListener(new HyperLink<>(new UserMenu()));
        UserController userController = new UserController();
        double userMoney = userController.getUserBalance();
        money.setText("$"+userMoney);
        if(operation == MoneyOperations.DEPOSIT){
            form.remove(target);
            titleLabel.setText("Depósitar dinero");
        }
        else if (operation == MoneyOperations.WITHDRAW){
            form.remove(target);
            titleLabel.setText("Retirar dinero");
        }
        else if (operation == MoneyOperations.TRANSFER){
            titleLabel.setText("Nueva Transferencia");
        }

        content.repaint();
        content.revalidate();
    }


}