package com.generonumero.blocodaguarda.alert.presenter.impl;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.generonumero.blocodaguarda.alert.event.CountDownFinished;
import com.generonumero.blocodaguarda.alert.presenter.AlertPresenter;
import com.generonumero.blocodaguarda.alert.service.AlertService;
import com.generonumero.blocodaguarda.alert.view.AlertView;
import com.generonumero.blocodaguarda.permission.service.PermissionService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class AlertPresenterImpl implements AlertPresenter {


    private static final int RESULT_CODE_PERMISSION_FROM_HELPME = 42;
    private static final int RESULT_CODE_PERMISSION_FROM_SAVEME = 41;
    private static final String PERMISSION = Manifest.permission.SEND_SMS;

    private AlertView alertView;
    private AlertService alertService;
    private Bus bus;
    private PermissionService permissionService;

    public AlertPresenterImpl(AlertView alertView, AlertService alertService, Bus bus, PermissionService permissionService) {
        this.alertView = alertView;
        this.alertService = alertService;
        this.bus = bus;
        this.permissionService = permissionService;
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
    public void onClickSaveMe(Fragment fragment) {
        if (alertService.isContactsRegistered()) {

            if (permissionService.getPermissionStatus(fragment.getActivity(), PERMISSION) == PermissionService.GRANTED) {
                saveme();
            } else {
                permissionService.askPermissionFromFragment(fragment, new String[]{PERMISSION}, RESULT_CODE_PERMISSION_FROM_SAVEME);
            }

        } else {
            alertView.showNetworkPopup();
        }
    }

    @Override
    public void onClickHelpMe(Fragment fragment) {
        if (alertService.isContactsRegistered()) {

            if (permissionService.getPermissionStatus(fragment.getActivity(), PERMISSION) == PermissionService.GRANTED) {
                helpme();
            } else {
                permissionService.askPermissionFromFragment(fragment, new String[]{PERMISSION}, RESULT_CODE_PERMISSION_FROM_HELPME);
            }

        } else {
            alertView.showNetworkPopup();
        }
    }

    private void helpme() {
        alertView.showSafeScreen();
        alertService.startCountDown();
    }

    private void saveme() {
        alertView.showSafeScreen();
        alertService.startCountDown();
    }

    @Subscribe
    @Override
    public void onCountDownFinished(CountDownFinished countDownFinished) {
        alertView.dismissSafeScreen();
        alertService.sendSMS();
    }

    @Override
    public void onStart() {
        bus.register(this);
    }

    @Override
    public void onStop() {
        bus.unregister(this);
    }

    @Override
    public void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull int[] grantResults, String permission) {
        if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            if (permissionService.getPermissionStatus(activity, permission) == PermissionService.BLOCKED_OR_NEVER_ASKED) {

            } else {

            }
        } else {
            switch (requestCode) {
                case RESULT_CODE_PERMISSION_FROM_HELPME:
                    helpme();
                    break;
                case RESULT_CODE_PERMISSION_FROM_SAVEME:
                    saveme();
                    break;
            }
        }
    }
}
