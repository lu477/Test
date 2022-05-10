package com.example.microbs;

public class Buyer {

    String name,pib,password;

    Buyer(){
    }

    public Buyer(String name,String pib,String password){
        this.name = name;
        this.password = password;
        this.pib = pib;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
