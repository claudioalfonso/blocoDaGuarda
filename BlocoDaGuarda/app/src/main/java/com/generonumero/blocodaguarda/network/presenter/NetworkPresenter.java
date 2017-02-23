package com.generonumero.blocodaguarda.network.presenter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public interface NetworkPresenter {

    void loadViews();

    void pickContact(Fragment fragment, String idContactList);

    void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull int[] grantResults, String permission);

    void onReceiveDataFromContact(Fragment fragment, int requestCode, int resultCode, Intent data);

}
