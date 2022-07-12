package com.labs.lab5;

public class User {
    private Integer id;
    private String login;
    private String password;


    User(){}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    User(Integer id, String login, String password) {
        new User(login, password);
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
