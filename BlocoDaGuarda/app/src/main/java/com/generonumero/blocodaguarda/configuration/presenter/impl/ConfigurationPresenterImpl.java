package com.generonumero.blocodaguarda.configuration.presenter.impl;

import com.generonumero.blocodaguarda.configuration.presenter.ConfigurationPresenter;
import com.generonumero.blocodaguarda.configuration.repository.ConfigurationRepository;
import com.generonumero.blocodaguarda.configuration.view.ConfigurationView;

public class ConfigurationPresenterImpl implements ConfigurationPresenter{

    private ConfigurationView configurationView;
    private ConfigurationRepository configurationRepository;

    public ConfigurationPresenterImpl(ConfigurationView configurationView, ConfigurationRepository configurationRepository) {
        this.configurationView = configurationView;
        this.configurationRepository = configurationRepository;
    }

    @Override
    public void loadViews() {
        int time = configurationRepository.getTime();
        int type = configurationRepository.getTypeOfForm();

        configurationView.onDataLoaded(time, type);
    }

    @Override
    public void clickSaveConfigs(int time, int typeOfForm) {

    }

    @Override
    public void onConfigsSave() {

    }
}
