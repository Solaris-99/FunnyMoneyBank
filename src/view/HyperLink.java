package view;

import app.controllers.Auth;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HyperLink <T extends Viewable> implements ActionListener {
    private final T view;

    public HyperLink(T view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(this.view instanceof Login) {
            Auth.logout();//todos los botones que van a login son de salida.
        }
        Window.goTo(this.view);
    }

}
