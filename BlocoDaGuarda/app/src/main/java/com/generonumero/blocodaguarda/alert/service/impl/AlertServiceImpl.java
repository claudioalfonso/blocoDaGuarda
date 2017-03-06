package com.generonumero.blocodaguarda.alert.service.impl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.alert.event.CountDownFinished;
import com.generonumero.blocodaguarda.alert.service.AlertService;
import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class AlertServiceImpl implements AlertService, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private NetworkRepository networkRepository;
    private CountDownTimer countDownTimer;
    private final int SECOND_IN_MILLIS = 1000;
    private final int TIME_TO_COUNT_DEFAULT = 15 * SECOND_IN_MILLIS;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location location;

    public AlertServiceImpl(NetworkRepository networkRepository) {
        this.networkRepository = networkRepository;
        countDownTimer = new CountDownTimer(TIME_TO_COUNT_DEFAULT, SECOND_IN_MILLIS) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                BDGApplication.getInstance().getBus().post(new CountDownFinished());
            }
        };
        locationRequest = new LocationRequest();
    }

    @Override
    public boolean isContactsRegistered() {
        List<Contact> allContacts = networkRepository.getAllContacts();
        for (Contact contact : allContacts) {
            if (!contact.isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void startCountDown() {
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
        Context applicationContext = BDGApplication.getInstance().getApplicationContext();
        StringBuffer buffer = new StringBuffer("");

//        buffer.append("Foi mal pertubar, mas to testando um app que fiz");
        if(location == null) {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }
        if (location != null) {
            String a = "https://maps.google.com?q=" + location.getLatitude() + "," + location.getLongitude();
            buffer.append(a);
        }

        SmsManager smsManager = SmsManager.getDefault();

        List<Contact> allContacts = networkRepository.getAllContacts();
        for (Contact contact : allContacts) {
            String phone = contact.getPhone().replaceAll("[^\\d.]", "");

            smsManager.sendTextMessage("4234234", null, buffer.toString(), null, null);
            break;
        }
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
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