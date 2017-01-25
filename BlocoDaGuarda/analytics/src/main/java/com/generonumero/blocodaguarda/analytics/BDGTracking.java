package com.generonumero.blocodaguarda.analytics;


import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;

public class BDGTracking {


    public static void initialize(Context context) {
        FirebaseAnalytics.getInstance(context);
        Fabric.with(context, new Crashlytics());
    }

}
