package com.generonumero.blocodaguarda.configuration.di;


import android.content.Context;

import com.generonumero.blocodaguarda.configuration.view.ConfigurationView;
import com.generonumero.blocodaguarda.configuration.view.impl.ConfigurationFragment;
import com.generonumero.blocodaguarda.di.AppModule;
import com.generonumero.blocodaguarda.di.ConfigurationModule;


public class Injector {

    public static void inject(Context context, ConfigurationView view, ConfigurationFragment configurationFragment) {
        DaggerConfigurationComponent.builder().
                appModule(new AppModule(context))
                .configurationModule(new ConfigurationModule(view))
                .build()
                .inject(configurationFragment);
    }

}
