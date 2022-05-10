package com.example.microbs;

public class Employe {

    String name,surename,city,password,magacin1, magacin2,magacin3;

    Employe(){

    }

    public Employe(String name, String surename, String city, String password, String magacin1,String magacin2, String magacin3){
        this.name = name;
        this.surename = surename;
        this.city = city;
        this.password = password;
        this.magacin1 = magacin1;
        this.magacin2 = magacin2;
        this.magacin3 = magacin3;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMagacin1() {
        return magacin1;
    }

    public void setMagacin1(String magacin1) {
        this.magacin1 = magacin1;
    }

    public String getMagacin2() {
        return magacin2;
    }

    public void setMagacin2(String magacin2) {
        this.magacin2 = magacin2;
    }

    public String getMagacin3() {
        return magacin3;
    }

    public void setMagacin3(String magacin3) {
        this.magacin3 = magacin3;
    }
}
