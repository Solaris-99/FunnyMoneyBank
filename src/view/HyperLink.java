package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HyperLink <T extends Viewable> implements ActionListener {
    private final T view;

    public HyperLink(T view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Window.goTo(this.view);
    }

}
