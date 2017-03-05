package com.generonumero.blocodaguarda.alert.service.impl;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.alert.event.CountDownFinished;
import com.generonumero.blocodaguarda.alert.service.AlertService;
import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class AlertServiceImpl implements AlertService, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private NetworkRepository networkRepository;
    private CountDownTimer countDownTimer;
    private final int SECOND_IN_MILLIS = 1000;
    private final int TIME_TO_COUNT_DEFAULT = 15 * SECOND_IN_MILLIS;

    private GoogleApiClient googleApiClient;


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

    public void sendSMS() {
        Context applicationContext = BDGApplication.getInstance().getApplicationContext();
        try {

            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            StringBuffer buffer = new StringBuffer("");
            buffer.append("Foi mal pertubar, mas to testando um app que fiz");
            buffer.append("\n");
            buffer.append("http://maps.google.com?q=");
            buffer.append(lastLocation.getLatitude());
            buffer.append(",");
            buffer.append(lastLocation.getLongitude());

            SmsManager smsManager = SmsManager.getDefault();

            String phoneAdress = "021984417774";

            smsManager.sendTextMessage(phoneAdress, null, buffer.toString(), null, null);
            Toast.makeText(applicationContext, "Message Sent", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(applicationContext, ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
