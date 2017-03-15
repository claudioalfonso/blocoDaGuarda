package com.generonumero.blocodaguarda.menu.repository;

import android.content.Context;
import android.content.SharedPreferences;

public class MenuRepositoryImpl implements MenuRepository {

    private Context context;
    private static final String sharedName = "MenuRepositoryImpl";
    private final String first_open = "first_open";
    private SharedPreferences sharedPreferences;

    public MenuRepositoryImpl(Context applicationContext) {
        this.context = applicationContext;
    }


    @Override
    public void saveFirstOpen() {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putBoolean("first_open", false);
        edit.commit();
    }

    @Override
    public boolean isFirstOpen() {
        return getSharedPreferences().getBoolean("first_open", true);
    }

    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

}
