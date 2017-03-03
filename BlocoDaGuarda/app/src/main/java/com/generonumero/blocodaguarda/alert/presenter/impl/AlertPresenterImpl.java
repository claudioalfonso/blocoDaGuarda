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
       if(!alertService.isContactsRegistered()) {
           alertView.showNetworkButton();
       }

    }
}
