package com.generonumero.blocodaguarda.login.event;

public class LoginData {

    private String name;
    private String email;
    private String gender;


    public LoginData(String name, String email, String gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
