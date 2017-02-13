package com.generonumero.blocodaguarda.login.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import com.generonumero.blocodaguarda.login.presenter.LoginPresenter;
import com.generonumero.blocodaguarda.login.service.FacebookLoginService;
import com.generonumero.blocodaguarda.login.view.LoginView;


public class LoginPresenterImpl implements LoginPresenter {

    private FacebookLoginService facebookLoginService;
    private LoginView loginView;


    public LoginPresenterImpl(LoginView loginView, FacebookLoginService facebookLoginService) {
        this.loginView = loginView;
        this.facebookLoginService = facebookLoginService;
    }


    @Override
    public void login(Activity activity) {
        facebookLoginService.login(activity);
    }

    @Override
    public void onLoginSuccesful() {
//        facebookLoginService.trackUserData();
        loginView.onLoginSuccessful();
    }

    @Override
    public void onLoginFailed() {
        loginView.onLoginError();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookLoginService.onActivityResult(requestCode, resultCode, data);
    }
}
