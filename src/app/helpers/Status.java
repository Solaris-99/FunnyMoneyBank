package app.helpers;

public class Status {

    public static final int ID_ATM = 1; //el id de "este cajero"
    private boolean isLogged;
    private boolean isEmployee;
    private int userId;
    private String userCode;
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

    public void setUserCode(String code){
        this.userCode = code;
    }

    public String getUserCode(){
        return userCode;
    }


}
