package com.javalab.marketing.models;

public class Order {
    //id
    private int id;
    //user who create order
    private int userid;
    //Name app order
    private String appname;
    //URL to app in AppStore
    private String hrefappstore;
    //URL to app in PlayMarket
    private String hrefplaymarket;
    //Name service which choice user(click or install)
    private String service;
    //Count clicks or installs
    private int count;
    //Count earned clicks or installs
    private int earn;
    //Price for service
    private double price;
    //It status order
    private String state;

    public Order(int userid, String appname, String hrefappstore, String hrefplaymarket, String service,
                 int count, double price, String state){
        this.userid=userid;
        this.appname=appname;
        this.hrefappstore=hrefappstore;
        this.hrefplaymarket=hrefplaymarket;
        this.service=service;
        this.count=count;
        this.price=price;
        this.state=state;
    }

    public int getEarn() {
        return earn;
    }

    public void setEarn(int earn) {
        this.earn = earn;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getHrefappstore() {
        return hrefappstore;
    }

    public void setHrefappstore(String hrefappstore) {
        this.hrefappstore = hrefappstore;
    }

    public String getHrefplaymarket() {
        return hrefplaymarket;
    }

    public void setHrefplaymarket(String hrefplaymarket) {
        this.hrefplaymarket = hrefplaymarket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
