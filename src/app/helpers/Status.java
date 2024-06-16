package app.helpers;

public class Status {

    private boolean isLogged;
    private boolean isEmployee;
    private static Status instance;

    private Status(){
        this.isLogged = false;
        this.isEmployee = false;
    }

    public static Status getInstance(){
        if(instance == null){
            instance = new Status();
        }
        return instance;
    }



}
