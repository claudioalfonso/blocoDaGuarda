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

    private Context context;
    private static TrackerBDG instance;

    private TrackerBDG() {}

    public static TrackerBDG getInstance() {
        if(instance == null) {
            instance = new TrackerBDG();
        }
        return instance;
    }

    public void initialize(Context context) {
        instance.context = context;

        FirebaseAnalytics.getInstance(context);

        Fabric.with(context, new Crashlytics());
        Fabric.with(context, new Answers());
    }

    public FirebaseTracker provideFirebase() {
        return new FirebaseTracker(context);
    }

    public AnswersTracker provideFabric() {
        return new AnswersTracker();
    }


}
