package com.generonumero.blocodaguarda.analytics;


import android.content.Context;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.generonumero.blocodaguarda.analytics.tracking.Tracker;
import com.generonumero.blocodaguarda.analytics.tracking.impl.FirebaseTracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class BDGTracking {

    private List<Tracker> trackers;
    private static BDGTracking instance;
    private Context context;

    public BDGTracking(Context context) {
        this.context = context;

        trackers = new ArrayList<>();
        trackers.add(new FirebaseTracker(context));
    }

    public static void initialize(Context context) {
        FirebaseAnalytics.getInstance(context);

        Fabric.with(context, new Crashlytics());
        Fabric.with(context, new Answers());

        instance = new BDGTracking(context);

        Crashlytics.log("teste");

    }

    public void sendEvent(String name, Bundle bundle) {
        for (Tracker tracker:trackers) {
            tracker.sendEvent(name, bundle);
        }
    }

}
