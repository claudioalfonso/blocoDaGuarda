package com.generonumero.blocodaguarda.alert.service;

/**
 * Created by Pedro on 3/2/17.
 */

public interface AlertService {

    boolean isContactsRegistered();

    void startCountDown();

    void stopCountDown();
}
