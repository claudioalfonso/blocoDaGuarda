package com.generonumero.blocodaguarda.tracking.impl;

import android.os.Bundle;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.generonumero.blocodaguarda.tracking.Tracker;

import java.util.Set;

public class AnswersTracker implements Tracker {


    @Override
    public void sendEvent(String name, Bundle bundle) {

        CustomEvent customEvent = new CustomEvent(name);

        Set<String> keys = bundle.keySet();
        for (String key: keys) {
            customEvent.putCustomAttribute(key, bundle.getString(key));
        }
        Answers.getInstance().logCustom(customEvent);

    }

    @Override
    public void sendError(String name, String error) {

    }
}
