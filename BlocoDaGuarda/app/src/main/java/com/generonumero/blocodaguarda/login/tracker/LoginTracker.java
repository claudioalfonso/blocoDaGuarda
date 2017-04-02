package com.generonumero.blocodaguarda.login.tracker;

import android.os.Bundle;

import com.generonumero.blocodaguarda.login.event.UserProfile;

public class LoginTracker {


    public void sendLoginSucessful(UserProfile loginSuccessful) {
        Bundle bundle = new Bundle();
//        bundle.putString("name", loginSuccessful.getName());
//        bundle.putString("email", loginSuccessful.getEmail());
        bundle.putString("genero", loginSuccessful.getGender());
        bundle.putBoolean("facebook", loginSuccessful.isFacebook());

//        TrackerBDG.sendEvent("loginSucessful", bundle);
    }

    public void clickLoginFacebook() {
        Bundle bundle = new Bundle();
        bundle.putString("source", "facebook");

//        TrackerBDG.sendEvent("login", bundle);
    }

    public void clickLoginEmail() {
        Bundle bundle = new Bundle();
        bundle.putString("source", "email");

//        TrackerBDG.sendEvent("login", bundle);
    }

}
