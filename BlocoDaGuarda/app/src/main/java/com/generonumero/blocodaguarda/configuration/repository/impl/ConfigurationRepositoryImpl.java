package com.generonumero.blocodaguarda.configuration.repository.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.generonumero.blocodaguarda.configuration.repository.ConfigurationRepository;

public class ConfigurationRepositoryImpl implements ConfigurationRepository {


    private SharedPreferences sharedPreferences;
    private final int TIME_TO_COUNT_DEFAULT = 15;

    public ConfigurationRepositoryImpl(Context context) {
        this.sharedPreferences = context.getSharedPreferences("ConfigurationRepositoryImpl", Context.MODE_PRIVATE);
        ;
    }

    @Override
    public void saveTime(int time) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("time", time);
        edit.apply();

    }

    @Override
    public int getTime() {
        return sharedPreferences.getInt("time", TIME_TO_COUNT_DEFAULT);
    }

    @Override
    public int getTypeOfForm() {
        return sharedPreferences.getInt("form", -1);
    }

    @Override
    public void saveFormSender(int form) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("form", form);
        edit.apply();
    }
}
