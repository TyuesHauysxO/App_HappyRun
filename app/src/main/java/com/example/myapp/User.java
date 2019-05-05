package com.example.myapp;

public class User {
    private String name;
    private String pwd;

    public User(){
    }

    public User(String user_name, String pwd){
        this.pwd = pwd;
        this.name = user_name;
    }

    public void setName(String user_name){
        this.name = user_name;
    }

    public void setPwd(String pwd){
        this.pwd = pwd;
    }


    public String getName(){
        return name;
    }

    public String getPwd(){
        return pwd;
    }

}
