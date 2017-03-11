package com.generonumero.blocodaguarda.login.presenter.impl;

import android.app.Activity;
import android.content.Intent;

import com.generonumero.blocodaguarda.login.event.LoginData;
import com.generonumero.blocodaguarda.login.event.LoginFailed;
import com.generonumero.blocodaguarda.login.event.LoginSuccessful;
import com.generonumero.blocodaguarda.login.presenter.LoginPresenter;
import com.generonumero.blocodaguarda.login.service.FacebookLoginService;
import com.generonumero.blocodaguarda.login.tracker.LoginTracker;
import com.generonumero.blocodaguarda.login.view.LoginView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;


public class LoginPresenterImpl implements LoginPresenter {

    private FacebookLoginService facebookLoginService;
    private Bus bus;
    private LoginView loginView;
    private LoginTracker loginTracker;


    public LoginPresenterImpl(LoginView loginView, FacebookLoginService facebookLoginService, Bus bus) {
        this.loginView = loginView;
        this.facebookLoginService = facebookLoginService;
        this.bus = bus;
        this.loginTracker = new LoginTracker();
    }


    @Override
    public void login(Activity activity) {
        facebookLoginService.login(activity);
    }


    @Subscribe
    @Override
    public void onLoginSuccesful(LoginSuccessful loginSuccessful) {
        loginView.onLoginSuccessful();

    }

    @Subscribe
    @Override
    public void onLoginFailed(LoginFailed loginFailed) {
        if (!loginFailed.isCanceled()) {
            loginView.onLoginError();
        }
    }


    @Subscribe
    public void onLoginDataReceived(LoginData loginData) {
        loginTracker.sendLoginSucessful(loginData);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookLoginService.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        bus.register(this);
    }

    @Override
    public void onStop() {
        bus.unregister(this);

    }
}
