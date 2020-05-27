package com.example.multicast;

public class User {
    private String idUser, passwordUser, emailUser;
    private int imageUser;

    public User(){
        //Mac dinh cua firebase, khi nhan data
    }

    public User(String idUser, String passwordUser, String emailUser, int imageUser) {
        this.idUser = idUser;
        this.passwordUser = passwordUser;
        this.emailUser = emailUser;
        this.imageUser = imageUser;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public int getImageUser() {
        return imageUser;
    }

    public void setImageUser(int imageUser) {
        this.imageUser = imageUser;
    }
}
