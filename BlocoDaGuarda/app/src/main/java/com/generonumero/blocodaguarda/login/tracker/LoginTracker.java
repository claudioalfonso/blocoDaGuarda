package com.generonumero.blocodaguarda.login.tracker;

import android.os.Bundle;

import com.generonumero.blocodaguarda.login.event.UserProfile;
import com.generonumero.blocodaguarda.tracking.TrackerBDG;

public class LoginTracker {


    public void sendLoginSucessful(UserProfile loginSuccessful) {

        Bundle bundle = new Bundle();
        bundle.putString("name", loginSuccessful.getName());
        bundle.putString("email", loginSuccessful.getEmail());
        bundle.putString("genero", loginSuccessful.getGender());
        bundle.putBoolean("facebook", loginSuccessful.isFacebook());

        TrackerBDG.sendEvent("loginSucessful", bundle);
    }

    public void sendLoginSucessfulFailed() {

    }
}
