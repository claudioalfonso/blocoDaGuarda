package com.generonumero.blocodaguarda.analytics;


import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

public class BDGTracking {


    public static void initialize(Context context) {
        FirebaseAnalytics.getInstance(context);
    }

}
