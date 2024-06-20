package view;

import javax.swing.*;

import app.controllers.UserController;
import app.helpers.Operation;
import app.helpers.Status;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private Operation operation;

    public MoneyOperation(Operation operation){
        this.operation = operation;

    }

    @Override
    public JPanel getContent(){
        makeFunctional();
        return this.content;
    }

    private void updateMoneyLabel() {
        UserController userController = new UserController();
        double userMoney = userController.getUserBalance();
        money.setText("$"+userMoney);
    }


    private void makeFunctional(){
        toMenu.addActionListener(new HyperLink<>(new UserMenu()));
        logout.addActionListener(new HyperLink<>(new Login()));
        UserController userController = new UserController();
        updateMoneyLabel();




        if(operation == Operation.DEPOSIT || operation == Operation.WITHDRAW){
            confirmarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double amount = Double.parseDouble(moneyInput.getText());
                    userController.atm(amount, operation);
                    updateMoneyLabel();
                }
            });
            form.remove(target);
            if(operation == Operation.DEPOSIT){
                titleLabel.setText("Depósitar dinero");
            }
            else {
                titleLabel.setText("Retirar dinero");
            }

        }
        else if (operation == Operation.TRANSFER){
            confirmarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String targetCode = targetInput.getText();
                    if(targetCode.equals(Status.getInstance().getUserCode())){
                        return;
                    }

                    double amount = Double.parseDouble(moneyInput.getText());
                    userController.makeTransference(targetCode,amount);
                    updateMoneyLabel();
                }
            });
            titleLabel.setText("Nueva Transferencia");
        }
        content.repaint();
        content.revalidate();
    }


}