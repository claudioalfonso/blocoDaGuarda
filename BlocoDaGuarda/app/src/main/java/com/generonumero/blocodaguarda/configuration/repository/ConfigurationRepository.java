package com.generonumero.blocodaguarda.configuration.repository;



public interface ConfigurationRepository {



    void saveTime(int time);

    int getTime();

    int getTypeOfForm();

    void saveFormSender(int form);

}
