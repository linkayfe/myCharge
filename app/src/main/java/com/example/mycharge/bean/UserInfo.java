package com.example.mycharge.bean;

public class UserInfo {

    private long userId;
    private String username;
    private String password;

    public UserInfo() {
    }

    public UserInfo(long id, String username, String password) {
        this.userId = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + userId +
                ", username=" + username +
                ", password='" + password + '\'' +
                '}';
    }

    public long getId() {
        return userId;
    }

    public void setId(long id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
