package com.generonumero.blocodaguarda.tracking;

import android.content.Context;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Pedro on 3/11/17.
 */

public class TrackerInitializer {

    private List<Tracker> trackers;
    private static TrackerInitializer instance;
    private Context context;

    public TrackerInitializer(Context context) {
        this.context = context;

        trackers = new ArrayList<>();
//        trackers.add(new FirebaseTracker(context));
    }

    public static void initialize(Context context) {
//        FirebaseAnalytics.getInstance(context);

        Fabric.with(context, new Crashlytics());
        Fabric.with(context, new Answers());

        instance = new TrackerInitializer(context);

        Crashlytics.log("teste");

    }

    public void sendEvent(String name, Bundle bundle) {
        for (Tracker tracker:trackers) {
            tracker.sendEvent(name, bundle);
        }
    }
}
