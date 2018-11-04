package com.example.syj0918.myapplication;

import java.util.Date;

public class account {
    String id;
    String pwd;
    String uid;
    String address;
    String email;
    String date;
    String user_like;
    Boolean gps_permission;
    Boolean search_permission;
    Date birth;
    String last_login;

    public account(String id, String uid, String last_login) {
        this.id = id;
        this.uid = uid;
        this.last_login = last_login;
    }
}
