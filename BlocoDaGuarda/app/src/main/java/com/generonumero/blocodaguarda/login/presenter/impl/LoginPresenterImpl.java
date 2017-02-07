package com.generonumero.blocodaguarda.login.presenter.impl;

import com.generonumero.blocodaguarda.login.presenter.LoginPresenter;
import com.generonumero.blocodaguarda.login.service.FacebookLoginService;
import com.generonumero.blocodaguarda.login.view.LoginView;


public class LoginPresenterImpl implements LoginPresenter {

    private FacebookLoginService facebookLoginService;
    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    public LoginPresenterImpl(LoginView loginView, FacebookLoginService facebookLoginService) {
        this.loginView = loginView;
        this.facebookLoginService = facebookLoginService;
    }

    @Override
    public void loginSuccessful() {
        facebookLoginService.trackUserData();
        loginView.onLoginSuccessful();
    }

    @Override
    public void loginFailed() {
        loginView.onLoginError();
    }
}
