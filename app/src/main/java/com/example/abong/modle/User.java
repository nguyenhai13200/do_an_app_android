package com.example.abong.modle;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String fullname;
    private String password;
    private String avatar;
    private String address;
    private String phone;

    public User(int id, String username, String fullname, String password, String avatar, String address, String phone) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.avatar = avatar;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
