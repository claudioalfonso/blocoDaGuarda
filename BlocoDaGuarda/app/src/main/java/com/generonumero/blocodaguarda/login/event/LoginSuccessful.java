package com.generonumero.blocodaguarda.login.event;


public class LoginSuccessful {


    private UserProfile userProfile;

    public LoginSuccessful() {
    }

    public LoginSuccessful(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
