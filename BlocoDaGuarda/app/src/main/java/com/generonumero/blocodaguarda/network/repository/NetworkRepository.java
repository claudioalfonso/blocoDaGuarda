package com.generonumero.blocodaguarda.network.repository;

import com.generonumero.blocodaguarda.network.model.Contact;

import java.util.List;

public interface NetworkRepository {

    List<Contact> getAllContacts();

    void update(String id, Contact contact);

    void saveAll(List<Contact> contacts);
}
