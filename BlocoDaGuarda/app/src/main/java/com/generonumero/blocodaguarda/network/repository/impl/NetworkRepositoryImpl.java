package com.generonumero.blocodaguarda.network.repository.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NetworkRepositoryImpl implements NetworkRepository {

    private Context context;
    private static final String sharedName = "NetworkRepositoryImpl2";
    private SharedPreferences sharedPreferences;

    public NetworkRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<Contact> getAllContacts() {

        Map<String, String> all = (Map<String, String>) getSharedPreferences().getAll();
        List<Contact> contacts = new ArrayList<>();
        Gson gson = new Gson();

        List<String> keys = new ArrayList<>(all.keySet());
        Collections.sort(keys);

        for (String key : keys) {
            try {
                contacts.add(gson.fromJson(all.get(key), Contact.class));
            } catch (Exception e) {
                Crashlytics.logException(e);
            }
        }
        if (contacts.size() < 5) {
            int size = 5 - contacts.size();
            for (int i = 0; i < size; i++) {
                contacts.add(new Contact());
            }
        }
        Collections.sort(contacts);
        return contacts;
    }

    public void update(String id, Contact contact) {
        getSharedPreferences().edit().putString(id, new Gson().toJson(contact)).apply();
    }

    @Override
    public void saveAll(List<Contact> contacts) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();

        for (int i = 0; i < contacts.size(); i++) {
            edit.putString(Integer.toString(i), new Gson().toJson(contacts.get(i)));
        }

        edit.apply();
    }

    @Override
    public void saveFirstOpen() {
        SharedPreferences.Editor edit = context.getSharedPreferences("NetworkRepositoryImpl2_new", Context.MODE_PRIVATE).edit();
        edit.putBoolean("first_open", false);
        edit.commit();
    }

    @Override
    public boolean isFirstOpen() {
        return context.getSharedPreferences("NetworkRepositoryImpl2_new", Context.MODE_PRIVATE).getBoolean("first_open", true);
    }


    private SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

}
