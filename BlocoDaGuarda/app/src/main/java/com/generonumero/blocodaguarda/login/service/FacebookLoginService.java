package com.generonumero.blocodaguarda.login.service;


import android.content.Context;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

public class FacebookLoginService {

    private CallbackManager callbackManager;

    public FacebookLoginService(Context context) {
        initialize(context);

        if (callbackManager == null) {
            callbackManager = CallbackManager.Factory.create();
        }
    }

    public void initialize(Context context) {
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(context);
        }
    }

    public boolean isLogged() {
        return AccessToken.getCurrentAccessToken() != null;
    }
}
