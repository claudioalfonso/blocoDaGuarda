package com.generonumero.blocodaguarda.login.presenter;

import android.app.Activity;
import android.content.Intent;

import com.generonumero.blocodaguarda.login.event.UserProfile;
import com.generonumero.blocodaguarda.login.event.LoginFailed;
import com.generonumero.blocodaguarda.login.event.LoginSuccessful;
import com.generonumero.blocodaguarda.presenter.BasePresenter;

public interface LoginPresenter extends BasePresenter {

    void onLoginSuccesful(LoginSuccessful loginSuccessful);

    void onLoginFailed(LoginFailed loginFailed);

    void onLoginDataReceived(UserProfile loginData);

}
