package com.generonumero.blocodaguarda.alert.service.impl;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.alert.service.AlertService;
import com.generonumero.blocodaguarda.login.event.UserProfile;
import com.generonumero.blocodaguarda.login.repository.LoginRepository;
import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;
import com.generonumero.blocodaguarda.permission.service.PermissionService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class AlertServiceImpl implements AlertService, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private NetworkRepository networkRepository;
    private PermissionService permissionService;
    private LoginRepository loginRepository;

    private static final String PERMISSION_LOCATION_FINE = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String PERMISSION_LOCATION_COARSE = Manifest.permission.ACCESS_COARSE_LOCATION;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location location;

    public AlertServiceImpl(NetworkRepository networkRepository, PermissionService permissionService, LoginRepository loginRepository) {
        this.networkRepository = networkRepository;
        this.permissionService = permissionService;
        this.loginRepository = loginRepository;
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
    public void startCountDown(CountDownTimer countDownTimer) {
        googleApiClient = new GoogleApiClient.Builder(BDGApplication.getInstance())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
        countDownTimer.start();
    }

    @Override
    public void stopCountDown(CountDownTimer countDownTimer) {
        countDownTimer.cancel();
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void sendSMS() {
        try {
            if (location == null) {
                location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            }
        } catch (Exception e) {
        }

        String link = "";
        if (location != null) {
            link = "https://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();
        }

        SmsManager smsManager = SmsManager.getDefault();

        UserProfile user = loginRepository.getUser();
        String pronomn;
        if (user.getGender().equals("male")) {
            pronomn = "o";
        } else {
            pronomn = "a";
        }

        List<Contact> allContacts = networkRepository.getAllContacts();
        for (Contact contact : allContacts) {
            if (contact.isValid()) {
                String msg = BDGApplication.getInstance().getString(R.string.alert_sms_message, contact.getName(), pronomn, link);

                String phone = contact.getPhoneFormated();
                ArrayList<String> parts = smsManager.divideMessage(msg);
                smsManager.sendMultipartTextMessage(phone, null, parts, null, null);
            }
        }
        FirebaseMessaging.getInstance().subscribeToTopic("push");
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
