package com.generonumero.blocodaguarda.configuration.di;

import com.generonumero.blocodaguarda.configuration.view.impl.ConfigurationFragment;
import com.generonumero.blocodaguarda.di.AppModule;
import com.generonumero.blocodaguarda.di.ConfigurationModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class,  ConfigurationModule.class})
@Singleton
public interface ConfigurationComponent {

    void inject(ConfigurationFragment configurationFragment);
}
