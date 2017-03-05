package com.generonumero.blocodaguarda.permission.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;

public interface PermissionPresenter {

    void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull int[] grantResults, String permission);
}
