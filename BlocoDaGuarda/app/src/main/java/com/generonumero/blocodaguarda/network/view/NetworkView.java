package com.generonumero.blocodaguarda.network.view;

import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.permission.view.PermissionView;

import java.util.List;

public interface NetworkView extends PermissionView {

    void OnLoadViews(List<Contact> contacts);

    void showContactWithoutNumber();

    void updateList(int position, Contact contact);

    void showPopupExplaningNetwork();

    void goHome();

    void showMsgInvalidContacts();
}
