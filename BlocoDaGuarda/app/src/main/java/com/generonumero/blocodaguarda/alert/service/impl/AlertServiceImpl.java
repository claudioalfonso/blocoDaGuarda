package com.generonumero.blocodaguarda.alert.service.impl;

import com.generonumero.blocodaguarda.alert.service.AlertService;
import com.generonumero.blocodaguarda.network.model.Contact;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;

import java.util.List;

public class AlertServiceImpl implements AlertService{

    private NetworkRepository networkRepository;

    public AlertServiceImpl(NetworkRepository networkRepository) {
        this.networkRepository = networkRepository;
    }

    @Override
    public boolean isContactsRegistered() {
        List<Contact> allContacts = networkRepository.getAllContacts();
        for (Contact contact: allContacts) {
            if(!contact.isValid()) {
                return false;
            }
        }
        return true;
    }
}
