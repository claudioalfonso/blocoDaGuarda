package com.generonumero.blocodaguarda.configuration.presenter;

public interface ConfigurationPresenter {

    void loadViews();

    void clickSaveConfigs(int time, int typeOfForm);

    void onConfigsSave();

}
