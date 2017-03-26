package com.generonumero.blocodaguarda.login.repository.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.generonumero.blocodaguarda.login.event.UserProfile;
import com.generonumero.blocodaguarda.login.repository.LoginRepository;
import com.google.gson.Gson;

public class LoginRepositoryImpl implements LoginRepository {

    private Context context;
    private static final String sharedName = "LoginRepositoryImpl";
    private SharedPreferences sharedPreferences;

    public LoginRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void saveUserData(UserProfile loginData) {

        Gson gson = new Gson();
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        String user = gson.toJson(loginData);

        edit.putString("user", user);
        edit.apply();
    }

    @Override
    public UserProfile getUser() {
        Gson gson = new Gson();
        String user = getSharedPreferences().getString("user", "");

        UserProfile profile = gson.fromJson(user, UserProfile.class);
        return profile;
    }

    @Override
    public boolean isLogged() {

        UserProfile user = getUser();

        return user != null && user.isValid();
    }

    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

}
