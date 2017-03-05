package com.generonumero.blocodaguarda.alert.service.impl;

import android.os.CountDownTimer;
import android.widget.Toast;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.alert.service.AlertService;
import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;

import java.util.List;

public class AlertServiceImpl implements AlertService {

    private NetworkRepository networkRepository;
    private CountDownTimer countDownTimer;
    private final int SECOND_IN_MILLIS = 1000;
    private final int TIME_TO_COUNT_DEFAULT = 15 * SECOND_IN_MILLIS;


    public AlertServiceImpl(NetworkRepository networkRepository) {
        this.networkRepository = networkRepository;
        countDownTimer = new CountDownTimer(TIME_TO_COUNT_DEFAULT, SECOND_IN_MILLIS) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                sendSMS();
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
    }

    @Override
    public void stopCountDown() {
        countDownTimer.cancel();
    }

    private void sendSMS() {
        Toast.makeText(BDGApplication.getInstance(), "teste", Toast.LENGTH_LONG).show();
    }
}
