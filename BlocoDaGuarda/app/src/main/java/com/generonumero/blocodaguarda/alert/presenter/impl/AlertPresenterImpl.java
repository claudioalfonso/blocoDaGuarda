package com.generonumero.blocodaguarda.alert.presenter.impl;


import com.generonumero.blocodaguarda.alert.presenter.AlertPresenter;
import com.generonumero.blocodaguarda.alert.service.AlertService;
import com.generonumero.blocodaguarda.alert.view.AlertView;

public class AlertPresenterImpl implements AlertPresenter {


    private AlertView alertView;
    private AlertService alertService;

    public AlertPresenterImpl(AlertView alertView, AlertService alertService) {
        this.alertView = alertView;
        this.alertService = alertService;
    }

    @Override
    public void loadViews() {
        if (!alertService.isContactsRegistered()) {
            alertView.showNetworkButton();
        }

    }

    @Override
    public void onClickNetwork() {
        alertView.goToNetworkScreen();
    }

    @Override
    public void onClickSaveMe() {
        if (alertService.isContactsRegistered()) {
            alertService.startCountDown();
            alertView.showSafeScreen();
        } else {
            alertView.showNetworkPopup();
        }
    }

    @Override
    public void onClickHelpMe() {
        if (alertService.isContactsRegistered()) {
            alertView.showSafeScreen();
        } else {
            alertView.showNetworkPopup();
        }
    }
}
