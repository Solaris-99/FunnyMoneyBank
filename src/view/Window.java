package view;

import javax.swing.*;

public class Window extends JFrame {
    private static Window instance;
    private JPanel content;

    private Window(){
        setTitle("FunnyMoneyBank");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);
        content = new JPanel();
        add(content);
        setVisible(true);
    }

    public static Window getInstance(){
        if(instance == null){
            instance = new Window();
        }
        return instance;
    }

    private <T extends Viewable> void setContent(T view){
        remove(content);
        content = view.getContent();
        add(content);
        this.revalidate();
        this.repaint();
    }

    public static <T extends Viewable> void goTo(T view){
        Window window = Window.getInstance();
        window.setContent(view);
    }

}
