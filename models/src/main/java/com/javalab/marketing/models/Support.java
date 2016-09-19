package com.javalab.marketing.models;

public class Support {

    //id
    private int id;
    //id user who sent support
    private int iduser;
    //theme support
    private String theme;
    //message which write user
    private String messageuser;
    //answer from admin
    private String messageadmin;
    //read by user(yes or no)
    private int read;

    public Support(int iduser,String theme ,String messageuser, String messageadmin, int read){
        this.iduser=iduser;
        this.theme=theme;
        this.messageuser=messageuser;
        this.messageadmin=messageadmin;
        this.read=read;
    }

    public int getId() {
        return id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getMessageadmin() {
        return messageadmin;
    }

    public void setMessageadmin(String messageadmin) {
        this.messageadmin = messageadmin;
    }

    public String getMessageuser() {
        return messageuser;
    }

    public void setMessageuser(String messageuser) {
        this.messageuser = messageuser;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}
