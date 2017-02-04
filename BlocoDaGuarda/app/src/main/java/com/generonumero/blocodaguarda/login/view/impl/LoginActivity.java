package com.generonumero.blocodaguarda.login.view.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.login.presenter.LoginPresenter;
import com.generonumero.blocodaguarda.login.view.LoginView;


public class LoginActivity extends AppCompatActivity implements LoginView {


    private LoginButton loginButton;

    private LoginPresenter loginPresenter;

    private CallbackManager callbackManager;


    public static void start(Activity activity) {
        Intent i = new Intent(activity.getApplicationContext(), LoginActivity.class);
        activity.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = BDGApplication.getInstance().getLoginPresenter(this);

        loginButton = (LoginButton) findViewById(R.id.facebook_login_bt);

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginPresenter.loginSuccessful();
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {
                loginPresenter.loginFailed();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

