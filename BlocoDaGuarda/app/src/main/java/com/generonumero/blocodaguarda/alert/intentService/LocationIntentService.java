package com.generonumero.blocodaguarda.alert.intentService;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.alert.tracking.AlertTracking;
import com.generonumero.blocodaguarda.login.event.UserProfile;
import com.generonumero.blocodaguarda.login.repository.LoginRepository;

import java.util.List;
import java.util.Locale;

public class LocationIntentService extends IntentService {

    private static final String LATITUDE = "com.generonumero.blocodaguarda.alert.intentService.extra.PARAM1";
    private static final String LONGITUDE = "com.generonumero.blocodaguarda.alert.intentService.extra.PARAM2";


    private AlertTracking alertTracking;
    private LoginRepository loginRepository;

    public LocationIntentService() {
        super("LocationIntentService");
        alertTracking = new AlertTracking();
        loginRepository = BDGApplication.getInstance().getLoginRepository();

    }

    public static void start(Context context, Double latitude, Double longitude) {
        Intent intent = new Intent(context, LocationIntentService.class);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            if (intent != null) {
                final Double latitude = intent.getDoubleExtra(LATITUDE, 0);
                final Double longitude = intent.getDoubleExtra(LONGITUDE, 0);

                if (latitude != 0 && longitude != 0) {
                    Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses;

                    addresses = gcd.getFromLocation(latitude, longitude, 1);

                    if (addresses.size() > 0) {
                        String locality = addresses.get(0).getLocality();
                        sendTrack(locality);
                    }
                }
            }
        } catch (Exception e) {
            alertTracking.errorAlertLocation("error to get location");
        }
    }


    private void sendTrack(String location) {
        UserProfile user = loginRepository.getUser();
        alertTracking.sendAlertLocation(location, user.getNeighborhood(), user.getGender());
    }
}
