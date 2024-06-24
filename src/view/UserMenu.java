package view;

import app.controllers.AtmController;
import app.controllers.UserController;
import app.helpers.Operation;
import app.helpers.Status;
import app.records.User;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JPanel moneyLabelPanel;
    private JLabel message;
    private JButton atmTransferences;
    private JButton copyCode;

    public void setMoney(String money) {
        this.money.setText(money);
    }

    public void setEmployeeView(boolean isEmployee) {
        if (isEmployee) {
            //mostrar el dinero del cajero si es el empleado.
            message.setText("Dinero del cajero:");
            buttonsPanel.remove(newTransferButton);
            buttonsPanel.remove(withdrawButton);
            buttonsPanel.remove(depositButton);
            AtmController atmController = new AtmController();
            setMoney("$" + atmController.getAtm(Status.ID_ATM).money());
            replenishMoneyButton.addActionListener(new HyperLink<>(new MoneyOperation(Operation.REPLENISH)));
            atmTransferences.addActionListener(new HyperLink<>(new AtmMovements()));
            copyCode.setVisible(false);
        } else {
            buttonsPanel.remove(replenishMoneyButton);
            buttonsPanel.remove(atmTransferences);
            UserController userController = new UserController();
            setMoney("$" + userController.getUserBalance());
            message.setText(String.format("Código de cliente:\n%s",userController.getUser().code()));
            depositButton.addActionListener(new HyperLink<>(new MoneyOperation(Operation.DEPOSIT)));
            withdrawButton.addActionListener(new HyperLink<>(new MoneyOperation(Operation.WITHDRAW)));
            newTransferButton.addActionListener(new HyperLink<>(new MoneyOperation(Operation.TRANSFER)));
            copyCode.setVisible(true);
            copyCode.setEnabled(true);
            copyCode.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StringSelection stringSelection = new StringSelection(userController.getUser().code());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                }
            });
        }
        buttonsPanel.revalidate();
        buttonsPanel.repaint();

    }

    private void makeFunctional() {
        //TODO
        UserController userController = new UserController();
        User user = userController.getUser();
        titleLabel.setText(String.format("Bienvenido, %s %s",user.name(), user.surname()));
        setEmployeeView(Status.getInstance().isEmployee());
        logout.addActionListener(new HyperLink<>(new Login()));
        viewTransferencesButton.addActionListener(new HyperLink<>(new Movements()));
        message.setVisible(true);

    }

    @Override
    public JPanel getContent() {
        makeFunctional();
        return this.content;
    }


    private void createUIComponents() {
            // TODO: place custom component creation code here
    }
}
