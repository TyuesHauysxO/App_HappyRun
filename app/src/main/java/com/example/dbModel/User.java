package com.example.dbModel;

public class User {
    private String name;
    private String pwd;

    public User(){
    }

    public User(String name, String pwd){
        this.pwd = pwd;
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
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
