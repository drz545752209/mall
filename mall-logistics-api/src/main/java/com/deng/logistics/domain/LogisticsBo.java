package com.deng.logistics.domain;

import java.io.Serializable;

public class LogisticsBo implements Serializable {
    private String location;
    private String userName;
    private String bizName;
    private String userContantWay;
    private String bizContantWay;
    private String address;
    private String status;

    public String getUserContantWay() {
        return userContantWay;
    }

    public void setUserContantWay(String userContantWay) {
        this.userContantWay = userContantWay;
    }

    public String getBizContantWay() {
        return bizContantWay;
    }

    public void setBizContantWay(String bizContantWay) {
        this.bizContantWay = bizContantWay;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
