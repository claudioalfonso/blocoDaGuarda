package com.generonumero.blocodaguarda.login.presenter.impl;

import com.generonumero.blocodaguarda.login.event.LoginFailed;
import com.generonumero.blocodaguarda.login.event.LoginSuccessful;
import com.generonumero.blocodaguarda.login.event.UserProfile;
import com.generonumero.blocodaguarda.login.presenter.LoginEmailPresenter;
import com.generonumero.blocodaguarda.login.repository.LoginRepository;
import com.generonumero.blocodaguarda.login.tracker.LoginTracker;
import com.generonumero.blocodaguarda.login.view.LoginView;
import com.squareup.otto.Bus;

/**
 * Created by Pedro on 3/19/17.
 */

public class LoginEmailPresenterImpl implements LoginEmailPresenter {

    private Bus bus;
    private LoginView loginView;
    private LoginTracker loginTracker;
    private LoginRepository loginRepository;

    public LoginEmailPresenterImpl(Bus bus, LoginView loginView, LoginTracker loginTracker, LoginRepository loginRepository) {
        this.bus = bus;
        this.loginView = loginView;
        this.loginTracker = loginTracker;
        this.loginRepository = loginRepository;
    }


    @Override
    public void onLoginSuccesful(LoginSuccessful loginSuccessful) {
        loginRepository.saveUserData(loginSuccessful.getUserProfile());
        loginView.onLoginSuccessful();
    }

    @Override
    public void onLoginFailed(LoginFailed loginFailed) {
        loginView.onLoginError();
    }

    @Override
    public void onLoginDataReceived(UserProfile loginData) {
        this.loginTracker.sendLoginSucessful(loginData);
    }

    @Override
    public void onStart() {
        bus.register(this);
    }

    @Override
    public void onStop() {
        bus.unregister(this);
    }

    @Override
    public void clickLogin(String name, String email, int gender) {
        String strGender = "";
        if (UserProfile.MALE == gender) {
            strGender = "male";
        }
        if (UserProfile.FEMALE == gender) {
            strGender = "female";
        }
        UserProfile user = new UserProfile(name, email, strGender, false);

        if(user.isValid()) {
            onLoginSuccesful(new LoginSuccessful(user));
        } else {
            onLoginFailed(new LoginFailed(false));
        }

    }
}
