package com.generonumero.blocodaguarda.network.repository;

import com.generonumero.blocodaguarda.network.model.Contact;

import java.util.List;

/**
 * Created by Pedro on 2/19/17.
 */

public interface NetworkRepository {

    List<Contact> getAllContacts();
}
