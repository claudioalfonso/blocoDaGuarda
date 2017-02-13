package com.generonumero.blocodaguarda.login.presenter;

import android.app.Activity;
import android.content.Intent;

public interface LoginPresenter {

    void login(Activity activity);

    void onLoginSuccesful();

    void onLoginFailed();

    void onActivityResult(int requestCode, int resultCode, Intent data);


}
