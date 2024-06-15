package view;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private JPanel contentPanel;

    public Window(String title){
        super(title);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);
        JPanel statusPanel = new JPanel();
        this.contentPanel = new JPanel();
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(statusPanel,BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void setContent(JPanel panel){
        remove(this.contentPanel);
        this.contentPanel = panel;

        add(this.contentPanel,BorderLayout.CENTER);
        revalidate();
        repaint();
    }


}
