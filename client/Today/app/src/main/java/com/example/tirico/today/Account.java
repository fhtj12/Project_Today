package com.example.tirico.today;

import android.app.Activity;

/**
 * Created by Tirico on 2018-06-06.
 */

public class Account {
    private String id;
    private String pwd;
    private String email;
    private String address;
    private String birth;
    private String nickname;

    public String uid;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Account(String id, String pwd, String email, String address, String birth) {
        this.id = id;
        this.pwd = pwd;
        this.email = email;
        this.address = address;
        this.birth = birth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
