package com.generonumero.blocodaguarda.login.tracker;

import android.os.Bundle;

import com.generonumero.blocodaguarda.login.event.LoginData;
import com.generonumero.blocodaguarda.tracking.TrackerBDG;

public class LoginTracker {


    public void sendLoginSucessful(LoginData loginSuccessful) {

        Bundle bundle = new Bundle();
        bundle.putString("name", loginSuccessful.getName());
        bundle.putString("email", loginSuccessful.getEmail());
        bundle.putString("genero", loginSuccessful.getGender());

        TrackerBDG.sendEvent("loginSucessful", bundle);
    }

    public void sendLoginSucessfulFailed() {

    }
}
