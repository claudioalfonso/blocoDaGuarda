package com.generonumero.blocodaguarda.network.presenter;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.permission.presenter.PermissionPresenter;

import java.util.List;

public interface NetworkPresenter extends PermissionPresenter {

    void loadViews();

    void pickContact(Fragment fragment, String idContactList);

    void onReceiveDataFromContact(Fragment fragment, int requestCode, int resultCode, Intent data);

    void saveAllContacts(List<Contact> contacts);

    void showSMSPermissionIfNeeded(Fragment fragment);

}
