package com.generonumero.blocodaguarda.login.presenter;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Pedro on 3/19/17.
 */

public interface LoginFacebookPresenter extends LoginPresenter {

    void login(Activity activity);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
