package com.generonumero.blocodaguarda.tracking.impl;

import android.content.Context;
import android.os.Bundle;

import com.generonumero.blocodaguarda.tracking.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseTracker implements Tracker {

    private Context mContext;

    public FirebaseTracker(Context context) {
        mContext = context;
    }

    @Override
    public void sendEvent(String name, Bundle bundle) {
        FirebaseAnalytics instance = FirebaseAnalytics.getInstance(mContext);
        instance.logEvent(name, bundle);

    }

    @Override
    public void sendError(String name, String error) {
        FirebaseAnalytics instance = FirebaseAnalytics.getInstance(mContext);

        Bundle bundle = new Bundle();
        bundle.putString(name, error);
        instance.logEvent("error", bundle);
    }
}
