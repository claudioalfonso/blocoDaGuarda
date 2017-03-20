package com.generonumero.blocodaguarda.login.repository;

import com.generonumero.blocodaguarda.login.event.UserProfile;

public interface LoginRepository {

    void saveUserData(UserProfile loginData);

    UserProfile getUser();

    boolean isLogged();
}
