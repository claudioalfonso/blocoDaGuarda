package com.generonumero.blocodaguarda.tracking;


import android.os.Bundle;

public interface Tracker {

    void sendEvent(String name, Bundle bundle);

    void sendError(String name, String error);

}
