package com.generonumero.blocodaguarda.alert.view;

import com.generonumero.blocodaguarda.permission.view.PermissionView;

public interface AlertView extends PermissionView {

    void showNetworkButton();

    void goToNetworkScreen();

    void showNetworkPopup();

    void showSafeScreen();

    void dismissSafeScreen();

    void disclaimerSMS();
}
