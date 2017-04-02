package com.generonumero.blocodaguarda.login.tracker;

import android.os.Bundle;

import com.generonumero.blocodaguarda.login.event.UserProfile;
import com.generonumero.blocodaguarda.tracking.ObjectName;
import com.generonumero.blocodaguarda.tracking.TrackerBDG;

public class LoginTracker {

    String objectName = ObjectName.ALERT_SCREEN;

    public void sendLoginSucessful(UserProfile loginSuccessful) {
        Bundle bundle = new Bundle();
        bundle.putString("gender", loginSuccessful.getGender());
        bundle.putBoolean("facebook", loginSuccessful.isFacebook());

        TrackerBDG.getInstance().provideFabric().sendEvent("loginSucessful", bundle);
        TrackerBDG.getInstance().provideFirebase().sendEvent("loginSucessful", bundle);
    }

    public void clickLoginFacebook() {
        Bundle bundle = new Bundle();
        bundle.putString("source", "facebook");

        TrackerBDG.getInstance().provideFabric().sendLogin("facebook", null);
        TrackerBDG.getInstance().provideFirebase().sendEvent(objectName, bundle);
    }

    public void clickLoginEmail() {
        Bundle bundle = new Bundle();
        bundle.putString("source", "email");

        TrackerBDG.getInstance().provideFabric().sendLogin("email", null);
        TrackerBDG.getInstance().provideFirebase().sendEvent(objectName, bundle);
    }
}
