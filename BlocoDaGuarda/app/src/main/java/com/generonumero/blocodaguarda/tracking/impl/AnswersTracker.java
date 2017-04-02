package com.generonumero.blocodaguarda.tracking.impl;

import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.AnswersEvent;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.LoginEvent;

import java.util.Set;

public class AnswersTracker {

    public void sendEvent(String name, Bundle bundle) {
        CustomEvent customEvent = new CustomEvent(name);
        putCustomContent(customEvent, bundle);

        Answers.getInstance().logCustom(customEvent);
    }

    public void sendView(String contentName, String contentType) {

       sendView(contentName, contentType, null);
    }

    public void sendView(String contentName, String contentType, Bundle bundle) {

        ContentViewEvent contentViewEvent = new ContentViewEvent();
        contentViewEvent.putContentName(contentName);
        contentViewEvent.putContentType(contentType);

        putCustomContent(contentViewEvent, bundle);

        Answers.getInstance().logContentView(contentViewEvent);
    }

    public void sendLogin(String name, Bundle bundle) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.putMethod(name);
        putCustomContent(loginEvent, bundle);

        Answers.getInstance().logLogin(loginEvent);
    }

    public void sendError(String error) {
        Crashlytics.log(error);
    }


    private void putCustomContent(AnswersEvent answersEvent, Bundle bundle) {
        if (bundle != null && bundle.size() > 0) {
            Set<String> keys = bundle.keySet();
            for (String key : keys) {
                answersEvent.putCustomAttribute(key, bundle.get(key).toString());
            }
        }
    }

}
