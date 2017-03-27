package com.generonumero.blocodaguarda.alert.presenter.impl;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import com.generonumero.blocodaguarda.alert.event.CountDownFinished;
import com.generonumero.blocodaguarda.alert.presenter.AlertPresenter;
import com.generonumero.blocodaguarda.alert.service.AlertService;
import com.generonumero.blocodaguarda.alert.view.AlertView;
import com.generonumero.blocodaguarda.configuration.repository.ConfigurationRepository;
import com.generonumero.blocodaguarda.permission.service.PermissionService;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class AlertPresenterImpl implements AlertPresenter {


    private static final int RESULT_CODE_PERMISSION_FROM_SAVEME = 41;
    private static final String PERMISSION_SMS = Manifest.permission.SEND_SMS;
    private static final String PERMISSION_LOCATION_FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String PERMISSION_LOCATION_COARSE = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final int SECOND_IN_MILLIS = 1000;

    private AlertView alertView;
    private AlertService alertService;
    private Bus bus;
    private PermissionService permissionService;
    private ConfigurationRepository configurationRepository;
    private CountDownTimer countDownTimer;

    public AlertPresenterImpl(AlertView alertView, AlertService alertService, Bus bus, PermissionService permissionService, ConfigurationRepository configurationRepository) {
        this.alertView = alertView;
        this.alertService = alertService;
        this.bus = bus;
        this.permissionService = permissionService;
        this.configurationRepository = configurationRepository;
    }

    @Override
    public void loadViews() {
        if (!alertService.isContactsRegistered()) {
            alertView.showNetworkButton();
        } else {
            alertView.hideNetworkButton();
        }

    }

    @Override
    public void onClickNetwork() {
        alertView.goToNetworkScreen();
    }

    @Override
    public void onClickSaveMe(Fragment fragment) {
        if (alertService.isContactsRegistered()) {

            if (permissionService.getPermissionStatus(fragment.getActivity(), PERMISSION_SMS) == PermissionService.GRANTED
                    && permissionService.getPermissionStatus(fragment.getActivity(), PERMISSION_LOCATION_FINE) == PermissionService.GRANTED
                    && permissionService.getPermissionStatus(fragment.getActivity(), PERMISSION_LOCATION_COARSE) == PermissionService.GRANTED) {
                saveMe();
            } else {
                permissionService.askPermissionFromFragment(fragment, new String[]{PERMISSION_SMS, PERMISSION_LOCATION_FINE, PERMISSION_LOCATION_COARSE}, RESULT_CODE_PERMISSION_FROM_SAVEME);
            }
        } else {
            alertView.showNetworkPopup();
        }
    }

    @Subscribe
    @Override
    public void onCountDownFinished(CountDownFinished countDownFinished) {
        alertView.dismissSafeScreen();
        alertService.sendSMS();
        alertView.disclaimerSMS();
    }

    @Override
    public void onCancelClick() {
        alertService.stopCountDown(countDownTimer);
        alertView.dismissSafeScreen();
    }


    private void saveMe() {
        int time = configurationRepository.getTime();
        alertView.showSafeScreen(time);

        countDownTimer = new CountDownTimer(time * SECOND_IN_MILLIS, SECOND_IN_MILLIS) {
            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                onCountDownFinished(new CountDownFinished());
            }
        };
        alertService.startCountDown(countDownTimer);
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
                alertView.showAlertPermissionDisable();
            } else {
                alertView.showAlertPermissionDenied();
            }
        } else if(grantResults.length >2 && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            if (permissionService.getPermissionStatus(activity, PERMISSION_LOCATION_FINE) == PermissionService.BLOCKED_OR_NEVER_ASKED) {
                alertView.showAlertLocationPermissionDisable();
            } else {
                alertView.showAlertLocationPermissionDenied();
            }
        } else {
            switch (requestCode) {
                case RESULT_CODE_PERMISSION_FROM_SAVEME:
                    saveMe();
                    break;
            }
        }
    }
}
