package com.example.chattest.Models;

public class User {
    private String pseudo;
    private String mdp;
    private String id;
    private String status;



    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public User(){}

    public User(String pseudo, String mdp, String id,String status) {
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
