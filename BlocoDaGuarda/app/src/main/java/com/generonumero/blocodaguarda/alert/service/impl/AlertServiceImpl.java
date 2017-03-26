package com.generonumero.blocodaguarda.alert.service.impl;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.alert.event.CountDownFinished;
import com.generonumero.blocodaguarda.alert.service.AlertService;
import com.generonumero.blocodaguarda.configuration.repository.ConfigurationRepository;
import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;
import com.generonumero.blocodaguarda.permission.service.PermissionService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class AlertServiceImpl implements AlertService, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private NetworkRepository networkRepository;
    private ConfigurationRepository configurationRepository;
    private CountDownTimer countDownTimer;
    private final int SECOND_IN_MILLIS = 1000;
    private PermissionService permissionService;

    private static final String PERMISSION_LOCATION_FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String PERMISSION_LOCATION_COARSE = Manifest.permission.ACCESS_COARSE_LOCATION;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location location;

    public AlertServiceImpl(NetworkRepository networkRepository, ConfigurationRepository configurationRepository, PermissionService permissionService) {
        this.networkRepository = networkRepository;
        this.configurationRepository = configurationRepository;
        this.permissionService = permissionService;
        locationRequest = new LocationRequest();
    }

    @Override
    public boolean isContactsRegistered() {
        List<Contact> allContacts = networkRepository.getAllContacts();
        int count = 0;
        for (Contact contact : allContacts) {
            if (contact.isValid()) {
                count++;
            }
        }
        return count > 2;
    }

    @Override
    public void startCountDown() {
        int time = configurationRepository.getTime() * SECOND_IN_MILLIS;

        countDownTimer = new CountDownTimer(time, SECOND_IN_MILLIS) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                BDGApplication.getInstance().getBus().post(new CountDownFinished());
            }
        };
        countDownTimer.start();
        googleApiClient = new GoogleApiClient.Builder(BDGApplication.getInstance())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void stopCountDown() {
        countDownTimer.cancel();
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void sendSMS() {
        StringBuffer buffer = new StringBuffer("");
        buffer.append("Estou em uma situação de risco, por favor me ajude. Estou te enviando a minha localização aproximada pelo link  ");
        if (location == null
                && !permissionService.hasNeedAskPermission(BDGApplication.getInstance(), PERMISSION_LOCATION_FINE)
                && !permissionService.hasNeedAskPermission(BDGApplication.getInstance(), PERMISSION_LOCATION_COARSE)) {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }
        if (location != null) {
            String a = "https://maps.google.com?q=" + location.getLatitude() + "," + location.getLongitude();
            buffer.append(a);
        }

        SmsManager smsManager = SmsManager.getDefault();

        List<Contact> allContacts = networkRepository.getAllContacts();
        for (Contact contact : allContacts) {
            if (contact.isValid()) {
                String phone = contact.getPhoneFormated();
                smsManager.sendTextMessage(phone, null, buffer.toString(), null, null);
            }
        }
        FirebaseMessaging.getInstance().subscribeToTopic("push");
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            if (!permissionService.hasNeedAskPermission(BDGApplication.getInstance(), PERMISSION_LOCATION_FINE)
                    && !permissionService.hasNeedAskPermission(BDGApplication.getInstance(), PERMISSION_LOCATION_COARSE)) {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }
}
