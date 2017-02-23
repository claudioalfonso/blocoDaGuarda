package com.generonumero.blocodaguarda.network.view;

import com.generonumero.blocodaguarda.network.model.Contact;

import java.util.List;

public interface NetworkView {

    void OnLoadViews(List<Contact> contacts);


    void showAlertPermissionDenied();

    void showAlertPermissionDesable();
}
