package com.generonumero.blocodaguarda.network.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public interface NetworkPresenter {

    void loadViews();

    boolean askPermissionIfNeed(Fragment fragment, String permission);

    void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull int[] grantResults, String permission);

}
