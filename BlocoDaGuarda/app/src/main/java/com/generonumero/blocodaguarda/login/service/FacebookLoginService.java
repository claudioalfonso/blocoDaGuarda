package com.generonumero.blocodaguarda.login.service;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

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

    public void trackUserData() {
        if(isLogged()) {
            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            Log.i("teste", "funcionou");
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }

    }
}
