package view;

import app.controllers.AtmController;
import app.helpers.Status;

import javax.swing.*;

public class AtmMovements implements  Viewable{
    private JPanel title;
    private JLabel titleLabel;
    private JButton toMenu;
    private JButton logout;
    private JPanel tableParent;
    private JLabel message;
    private JTable transfers;
    private JPanel content;

    private void createUIComponents() {
        // TODO: place custom component creation code here

        AtmController atmController = new AtmController();
        String[] cols = {"Empleado", "Monto","Fecha"};
        String[][] tableContents = atmController.getAtmArrayTable(Status.ID_ATM);
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

    private void makeFunctional(){
        logout.addActionListener(new HyperLink<>(new Login()));
        toMenu.addActionListener(new HyperLink<>(new UserMenu()));

    }

    @Override
    public JPanel getContent(){
        makeFunctional();
        return this.content;
    }

}
