package com.generonumero.blocodaguarda.login.service;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class FacebookLoginService {

    CallbackManager callbackManager;
    Collection<String> permissions;

    public FacebookLoginService(Context context) {
        initialize(context);

        if (callbackManager == null) {
            callbackManager = CallbackManager.Factory.create();
        }
    }

    private void initialize(Context context) {
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(context);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void login(Activity activity) {
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserGraphData();
                Log.i("teste","logou");
                Log.i("teste",loginResult.toString());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {

            }
        });
        loginManager.logInWithReadPermissions(activity, getPermissions());
    }

    public void logout() {
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logOut();
    }

    private Collection<String> getPermissions() {
        if (permissions == null) {
            permissions = new ArrayList<>();

            permissions.add("public_profile");
            permissions.add("email");

        }
        return permissions;
    }

    private void getUserGraphData() {
        if (isLogged()) {
            GraphRequest request = GraphRequest.newMeRequest(
                    getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {

                            //save User

                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, birthday, email, gender, name, age ");
            request.setParameters(parameters);
            request.executeAsync();
        }

    }

    private AccessToken getAccessToken() {
        return AccessToken.getCurrentAccessToken();
    }

    public Profile getUserProfile() {
        return Profile.getCurrentProfile();
    }

    public boolean isLogged() {
        return AccessToken.getCurrentAccessToken() != null;
    }


}
