package com.generonumero.blocodaguarda.tracking.impl;

import android.os.Bundle;

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

        ContentViewEvent contentViewEvent = new ContentViewEvent();
        contentViewEvent.putContentName(contentName);
        contentViewEvent.putContentType(contentType);

        Answers.getInstance().logContentView(contentViewEvent);
    }

    public void sendLogin(String name, Bundle bundle) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.putMethod(name);
        putCustomContent(loginEvent, bundle);

        Answers.getInstance().logLogin(loginEvent);
    }


    private void putCustomContent(AnswersEvent answersEvent, Bundle bundle) {
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            for (String key : keys) {
                answersEvent.putCustomAttribute(key, bundle.getString(key));
            }
        }
    }

}
