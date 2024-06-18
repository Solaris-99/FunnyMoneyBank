package view;

import app.controllers.UserController;
import app.helpers.Operation;
import app.helpers.Status;

import javax.swing.*;

public class UserMenu implements Viewable {
    private JPanel content;
    private JLabel titleLabel;
    private JLabel money;
    private JButton viewTransferencesButton;
    private JButton newTransferButton;
    private JButton withdrawButton;
    private JButton replenishMoneyButton;
    private JButton depositButton;
    private JButton logout;
    private JPanel buttonsPanel;
    private JPanel title;

    public void setMoney(String money) {
        this.money.setText(money);
    }

    public void setEmployeeView(boolean v) {
        if (v) {
            //mostrar el dinero del cajero si es el empleado.
            buttonsPanel.remove(newTransferButton);
            buttonsPanel.remove(withdrawButton);
            buttonsPanel.remove(depositButton);
        } else {
            buttonsPanel.remove(replenishMoneyButton);
        }
        buttonsPanel.revalidate();
        buttonsPanel.repaint();
    }

    private void makeFunctional() {
        //TODO
        UserController userController = new UserController();
        setEmployeeView(Status.getInstance().isEmployee());
        setMoney("$" + userController.getUserBalance());
        depositButton.addActionListener(new HyperLink<>(new MoneyOperation(Operation.DEPOSIT)));
        withdrawButton.addActionListener(new HyperLink<>(new MoneyOperation(Operation.WITHDRAW)));
        newTransferButton.addActionListener(new HyperLink<>(new MoneyOperation(Operation.TRANSFER)));
        viewTransferencesButton.addActionListener(new HyperLink<>(new Movements()));
    }

    @Override
    public JPanel getContent() {
        makeFunctional();
        return this.content;
    }


}
