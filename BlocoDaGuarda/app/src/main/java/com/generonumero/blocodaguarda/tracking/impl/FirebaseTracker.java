package com.generonumero.blocodaguarda.tracking.impl;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseTracker {

    private Context mContext;

    public FirebaseTracker(Context context) {
        mContext = context;
    }

    public void sendEvent(String name, Bundle bundle) {
        FirebaseAnalytics instance = FirebaseAnalytics.getInstance(mContext);
        instance.logEvent(name, bundle);

    }

    public void sendError(String name, String error) {
        FirebaseAnalytics instance = FirebaseAnalytics.getInstance(mContext);

        Bundle bundle = new Bundle();
        bundle.putString(name, error);
        instance.logEvent("error", bundle);
    }
}
