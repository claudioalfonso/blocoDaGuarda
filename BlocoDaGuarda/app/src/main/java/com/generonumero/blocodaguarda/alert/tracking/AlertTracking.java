package com.generonumero.blocodaguarda.alert.tracking;


import android.os.Bundle;

import com.generonumero.blocodaguarda.tracking.EventType;
import com.generonumero.blocodaguarda.tracking.ObjectName;
import com.generonumero.blocodaguarda.tracking.TrackerBDG;

public class AlertTracking {

    String objectName = ObjectName.ALERT_SCREEN;

    public void onContentView() {

        String eventType = EventType.VIEW;

        Bundle bundle = new Bundle();
        bundle.putString("screenView", objectName);

        TrackerBDG.getInstance().provideFabric().sendView(objectName, eventType);

        TrackerBDG.getInstance().provideFirebase().sendEvent(eventType, bundle);
    }

    public void sendAlert(boolean hasAllPermissions, boolean isContactsRegistered) {
        String eventType = EventType.CLICK;

        Bundle bundle = new Bundle();
        bundle.putString("content_type", "sendSaveAlert");
        bundle.putBoolean("hasAllContacts", isContactsRegistered);
        bundle.putBoolean("hasAllPermissions", hasAllPermissions);


        TrackerBDG.getInstance().provideFabric().sendEvent(eventType, bundle);

        TrackerBDG.getInstance().provideFirebase().sendEvent(eventType, bundle);

    }

    public void alertCanceled() {
        String eventType = EventType.CLICK;

        Bundle bundle = new Bundle();
        bundle.putString("content_type", "cancelAlert");


        TrackerBDG.getInstance().provideFabric().sendEvent(eventType, bundle);

        TrackerBDG.getInstance().provideFirebase().sendEvent(eventType, bundle);
    }

    public void alertOpen(int timeOfAlert) {
        String eventType = EventType.VIEW;
        String contentType = "popupSaveCountDown";

        Bundle bundle = new Bundle();
        bundle.putString("screenView", objectName);
        bundle.putString("content_type", contentType);
        bundle.putInt("timeCountdown", timeOfAlert);

        TrackerBDG.getInstance().provideFabric().sendView(objectName, contentType, bundle);

        TrackerBDG.getInstance().provideFirebase().sendEvent(eventType, bundle);
    }


    public void clickNetwork() {
        String eventType = EventType.CLICK;
        String contentType = "clickNetwork";

        Bundle bundle = new Bundle();
        bundle.putString("screenView", objectName);
        bundle.putString("content_type", contentType);

        TrackerBDG.getInstance().provideFabric().sendView(objectName, contentType);

        TrackerBDG.getInstance().provideFirebase().sendEvent(eventType, bundle);
    }


    public void alertFinishCountdown(int timeOfAlert) {
        String eventType = EventType.FINISH;
        String contentType = "popupSaveCountDown";

        Bundle bundle = new Bundle();
        bundle.putString("screenView", objectName);
        bundle.putString("content_type", contentType);
        bundle.putInt("timeCountdown", timeOfAlert);

        TrackerBDG.getInstance().provideFabric().sendEvent(objectName, bundle);

        TrackerBDG.getInstance().provideFirebase().sendEvent(eventType, bundle);
    }

    public void sendAlertLocation(String local, String neighborhood, String gender) {
        String eventType = EventType.FINISH;
        String contentType = "alertLocation";

        Bundle bundle = new Bundle();
        bundle.putString("screenView", objectName);
        bundle.putString("content_type", contentType);
        bundle.putString("location", neighborhood);
        bundle.putString("neighborhood", neighborhood);
        bundle.putString("gender", gender);

        TrackerBDG.getInstance().provideFabric().sendEvent(objectName, bundle);

        TrackerBDG.getInstance().provideFirebase().sendEvent(eventType, bundle);
    }

    public void errorAlertLocation(String error) {
        String contentType = "errorAlertLocation";

        TrackerBDG.getInstance().provideFabric().sendError(error);

        TrackerBDG.getInstance().provideFirebase().sendError(contentType, error);
    }

}
