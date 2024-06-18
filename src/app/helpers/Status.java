package app.helpers;

public class Status {

    private boolean isLogged;
    private boolean isEmployee;
    private int userId;

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

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



}
