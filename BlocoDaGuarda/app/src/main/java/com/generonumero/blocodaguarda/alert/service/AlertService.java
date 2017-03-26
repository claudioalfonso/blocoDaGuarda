package com.generonumero.blocodaguarda.alert.service;

import android.os.CountDownTimer;

/**
 * Created by Pedro on 3/2/17.
 */

public interface AlertService {

    boolean isContactsRegistered();

    void startCountDown(CountDownTimer countDownTimer);

    void stopCountDown(CountDownTimer countDownTimer);

    void sendSMS();
}
