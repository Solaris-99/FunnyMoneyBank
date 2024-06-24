package view;

import javax.swing.*;

import app.controllers.AtmController;
import app.controllers.EmployeeController;
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
        if(!Status.getInstance().isEmployee()){
            UserController userController = new UserController();
            double userMoney = userController.getUserBalance();
            money.setText("$"+userMoney);
        }
        else{
            AtmController atmController = new AtmController();
            double atmMoney = atmController.getAtm(Status.ID_ATM).money();
            money.setText("$" + atmMoney);
        }
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
                    try{
                        double amount = Double.parseDouble(moneyInput.getText());
                        userController.makeTransference(targetCode,amount);
                        updateMoneyLabel();
                    }
                    catch (NumberFormatException ex){
                        System.out.println(ex.getMessage());
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese solo números", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            });
            titleLabel.setText("Nueva Transferencia");
        }
        else{
            System.out.println("employee view");
            form.remove(target);
            confirmarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double amount = Double.parseDouble(moneyInput.getText());
                    EmployeeController employeeController = new EmployeeController();
                    employeeController.replenishAtmMoney(amount);
                    updateMoneyLabel();
                }
            });
        }
        content.repaint();
        content.revalidate();
    }

}