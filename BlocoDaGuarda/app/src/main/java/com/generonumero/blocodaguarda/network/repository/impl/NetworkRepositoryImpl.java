package com.generonumero.blocodaguarda.network.repository.impl;

import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;

import java.util.ArrayList;
import java.util.List;

public class NetworkRepositoryImpl implements NetworkRepository {
    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();

        contacts.add(new Contact("Pedro", "21984417744"));
        contacts.add(new Contact());
        contacts.add(new Contact("Joao", "21984417744"));
        contacts.add(new Contact());
        return contacts;
    }
}
