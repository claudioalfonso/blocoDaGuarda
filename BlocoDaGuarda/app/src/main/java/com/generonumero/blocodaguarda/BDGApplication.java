package com.generonumero.blocodaguarda;

import android.app.Application;

import com.generonumero.blocodaguarda.analytics.BDGTracking;

public class BDGApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        BDGTracking.initialize(getApplicationContext());
    }
}
