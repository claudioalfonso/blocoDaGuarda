package com.generonumero.blocodaguarda.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.generonumero.blocodaguarda.configuration.presenter.ConfigurationPresenter;
import com.generonumero.blocodaguarda.configuration.presenter.impl.ConfigurationPresenterImpl;
import com.generonumero.blocodaguarda.configuration.repository.ConfigurationRepository;
import com.generonumero.blocodaguarda.configuration.repository.impl.ConfigurationRepositoryImpl;
import com.generonumero.blocodaguarda.configuration.view.ConfigurationView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ConfigurationModule {

    private ConfigurationView configurationView;

    public ConfigurationModule() {}

    public ConfigurationModule(ConfigurationView configurationView) {
        this.configurationView = configurationView;
    }

    @Provides
    @Singleton
    @NonNull
    public ConfigurationRepository provideConfigurationRepository(Context context) {
        return new ConfigurationRepositoryImpl(context);
    }

    @Provides
    @Singleton
    @NonNull
    public ConfigurationPresenter provideConfigurationPresenter(ConfigurationRepository configurationRepository) {
        return new ConfigurationPresenterImpl(configurationView, configurationRepository);
    }


}
