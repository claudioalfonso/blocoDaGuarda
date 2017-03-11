package com.generonumero.blocodaguarda.tracking;

import android.content.Context;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.generonumero.blocodaguarda.tracking.impl.AnswersTracker;
import com.generonumero.blocodaguarda.tracking.impl.FirebaseTracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.InitializationException;

public class TrackerBDG {

    private List<Tracker> trackers;
    private static TrackerBDG instance;

    private TrackerBDG(Context context) {

        trackers = new ArrayList<>();
        trackers.add(new FirebaseTracker(context));
        trackers.add(new AnswersTracker());
    }

    public static void initialize(Context context) {
        FirebaseAnalytics.getInstance(context);

        Fabric.with(context, new Crashlytics());
        Fabric.with(context, new Answers());

        instance = new TrackerBDG(context);
    }

    public static void sendEvent(String name, Bundle bundle) {
        if(instance == null) {
            throw new InitializationException("Must initialize trackers");
        }

        for (Tracker tracker: instance.trackers) {
            tracker.sendEvent(name, bundle);
        }
    }

}
