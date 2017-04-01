package com.generonumero.blocodaguarda.about.view.tracking;

import android.os.Bundle;

import com.generonumero.blocodaguarda.tracking.EventType;
import com.generonumero.blocodaguarda.tracking.ObjectName;
import com.generonumero.blocodaguarda.tracking.TrackerBDG;

public class AboutTracking {

    public void onContentView(String contentType) {
        String objectName = ObjectName.ABOUT;
        String eventType = EventType.VIEW;

        Bundle bundle = new Bundle();
        bundle.putString("screenView", objectName);
        bundle.putString("content_type", contentType);

        TrackerBDG.getInstance().provideFabric().sendView(objectName, contentType);

        TrackerBDG.getInstance().provideFirebase().sendEvent(eventType, bundle);
    }


    public void clickLink(String contentType) {
        String objectName = ObjectName.ABOUT;
        String eventType = EventType.CLICK;

        Bundle bundle = new Bundle();
        bundle.putString("screenView", objectName);
        bundle.putString("content_type", contentType);


        TrackerBDG.getInstance().provideFabric().sendEvent(eventType, bundle);
    }
}
