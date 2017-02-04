package com.generonumero.blocodaguarda.login.presenter.impl;

import com.generonumero.blocodaguarda.login.presenter.LoginPresenter;
import com.generonumero.blocodaguarda.login.view.LoginView;


public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void loginSuccessful() {

    }

    @Override
    public void loginFailed() {

    }
}
