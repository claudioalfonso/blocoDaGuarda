package com.generonumero.blocodaguarda.push;

import com.google.firebase.iid.FirebaseInstanceIdService;

public class PushInstanceIdService extends FirebaseInstanceIdService {

    public PushInstanceIdService() {
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    }


}
