package view;

import app.controllers.TransactionController;
import app.controllers.UserController;
import app.helpers.Status;
import app.records.Transaction;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class Movements implements Viewable {
    private JPanel content;
    private JLabel titleLabel;
    private JPanel title;
    private JButton toMenu;
    private JButton logout;
    private JTable transfers;
    private JLabel message;
    private JPanel tableParent;
    private JLabel statistics;


    private void makeFunctional(){
        TransactionController transactionController = new TransactionController();
        logout.addActionListener(new HyperLink<>(new Login()));
        toMenu.addActionListener(new HyperLink<>(new UserMenu()));
        Status status = Status.getInstance();
        if(status.isEmployee()){
            statistics.setText(transactionController.getStatistics());
        }
    }

    @Override
    public JPanel getContent(){
        makeFunctional();
        return this.content;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        TransactionController transactionController = new TransactionController();
        String[] cols = {"Monto", "Fecha","Tipo"};
        String[][] tableContents = transactionController.getArrayTable();
        if(tableContents.length == 0){
            message = new JLabel();
            message.setText("No hay transacciones para mostrar");
            message.setVisible(true);
        }
        transfers = new JTable(tableContents, cols);
        transfers.setVisible(true);
        transfers.setBounds(30,40,200,300);
        transfers.revalidate();
        transfers.repaint();

    }
}

