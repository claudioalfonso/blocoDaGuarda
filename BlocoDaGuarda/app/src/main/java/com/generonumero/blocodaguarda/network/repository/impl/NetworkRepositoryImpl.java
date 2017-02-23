package com.generonumero.blocodaguarda.network.repository.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NetworkRepositoryImpl implements NetworkRepository {

    private Context context;
    private static final String sharedName = "NetworkRepositoryImpl";
    private SharedPreferences sharedPreferences;

    public NetworkRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<Contact> getAllContacts() {

        Map<String, String> all = (Map<String, String>) getSharedPreferences().getAll();
        List<Contact> contacts = new ArrayList<>();
        Gson gson = new Gson();

        for (String key : all.keySet()) {
            contacts.add(Integer.parseInt(key), gson.fromJson(all.get(key), Contact.class));
        }

        if (contacts.size() < 3) {
            int size = 3 - contacts.size();
            for (int i = 0; i < size; i++) {
                contacts.add(new Contact());
            }
        }
        return contacts;
    }

    public void update(String id, Contact contact) {
        getSharedPreferences().edit().putString(id, new Gson().toJson(contact)).apply();
    }


    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

}
