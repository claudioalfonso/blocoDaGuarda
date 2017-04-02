package com.generonumero.blocodaguarda;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.generonumero.blocodaguarda.alert.presenter.AlertPresenter;
import com.generonumero.blocodaguarda.alert.presenter.impl.AlertPresenterImpl;
import com.generonumero.blocodaguarda.alert.service.AlertService;
import com.generonumero.blocodaguarda.alert.service.impl.AlertServiceImpl;
import com.generonumero.blocodaguarda.alert.tracking.AlertTracking;
import com.generonumero.blocodaguarda.alert.view.AlertView;
import com.generonumero.blocodaguarda.configuration.presenter.ConfigurationPresenter;
import com.generonumero.blocodaguarda.configuration.presenter.impl.ConfigurationPresenterImpl;
import com.generonumero.blocodaguarda.configuration.repository.ConfigurationRepository;
import com.generonumero.blocodaguarda.configuration.repository.impl.ConfigurationRepositoryImpl;
import com.generonumero.blocodaguarda.configuration.view.ConfigurationView;
import com.generonumero.blocodaguarda.login.presenter.LoginEmailPresenter;
import com.generonumero.blocodaguarda.login.presenter.LoginFacebookPresenter;
import com.generonumero.blocodaguarda.login.presenter.impl.LoginEmailPresenterImpl;
import com.generonumero.blocodaguarda.login.presenter.impl.LoginFacebookPresenterImpl;
import com.generonumero.blocodaguarda.login.repository.LoginRepository;
import com.generonumero.blocodaguarda.login.repository.impl.LoginRepositoryImpl;
import com.generonumero.blocodaguarda.login.service.FacebookLoginService;
import com.generonumero.blocodaguarda.login.tracker.LoginTracker;
import com.generonumero.blocodaguarda.login.view.LoginView;
import com.generonumero.blocodaguarda.menu.presenter.MainPresenter;
import com.generonumero.blocodaguarda.menu.presenter.impl.MainPresenterImpl;
import com.generonumero.blocodaguarda.menu.repository.MenuRepository;
import com.generonumero.blocodaguarda.menu.repository.MenuRepositoryImpl;
import com.generonumero.blocodaguarda.menu.view.MainView;
import com.generonumero.blocodaguarda.network.presenter.NetworkPresenter;
import com.generonumero.blocodaguarda.network.presenter.impl.NetworkPresenterImpl;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;
import com.generonumero.blocodaguarda.network.repository.impl.NetworkRepositoryImpl;
import com.generonumero.blocodaguarda.network.view.NetworkView;
import com.generonumero.blocodaguarda.permission.service.PermissionService;
import com.generonumero.blocodaguarda.permission.service.impl.PermissionServiceImpl;
import com.generonumero.blocodaguarda.tracking.TrackerBDG;
import com.squareup.otto.Bus;

public class BDGApplication extends Application {

    private static BDGApplication instance;
    private FacebookLoginService facebookLoginService;
    private LoginRepository loginRepository;
    private Bus bus;
    private NetworkRepository networkRepository;
    private PermissionService permissionService;
    private AlertService alertService;
    private ConfigurationRepository configurationRepository;
    private MenuRepository menuRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        instance = this;

        TrackerBDG.getInstance().initialize(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public static BDGApplication getInstance() {
        return instance;
    }

    public MainPresenter getMainPresenter(MainView mainView) {
        return new MainPresenterImpl(mainView, getMenuRepository(), getLoginRepository());
    }


    public MenuRepository getMenuRepository() {
        if (menuRepository == null) {
            menuRepository = new MenuRepositoryImpl(getApplicationContext());
        }
        return menuRepository;
    }

    public FacebookLoginService getFacebookLoginService() {
        if (facebookLoginService == null) {
            facebookLoginService = new FacebookLoginService(getApplicationContext(), getBus());
        }
        return facebookLoginService;
    }

    public LoginRepository getLoginRepository() {
        if (loginRepository == null) {
            loginRepository = new LoginRepositoryImpl(getApplicationContext());
        }
        return loginRepository;
    }

    public LoginFacebookPresenter getLoginFacebookPresenter(LoginView loginView) {
        return new LoginFacebookPresenterImpl(loginView, getFacebookLoginService(), getBus(), getLoginRepository());
    }

    public LoginEmailPresenter getLoginEmailPresenter(LoginView loginView) {
        return new LoginEmailPresenterImpl(getBus(), loginView, new LoginTracker(), getLoginRepository());
    }

    public NetworkPresenter getNetworkPresenter(NetworkView networkView) {
        return new NetworkPresenterImpl(networkView, getNetworkRepository(), getPermissionService(), getLoginRepository());
    }

    public AlertPresenter getAlertPresenter(AlertView alertView) {
        return new AlertPresenterImpl(alertView, getAlertService(), getBus(), getPermissionService(), getConfigurationRepository(), new AlertTracking());
    }

    private NetworkRepository getNetworkRepository() {
        if (networkRepository == null) {
            networkRepository = new NetworkRepositoryImpl(getApplicationContext());
        }
        return networkRepository;
    }

    public Bus getBus() {
        if (bus == null) {
            bus = new Bus();
        }
        return bus;
    }

    private PermissionService getPermissionService() {
        if (permissionService == null) {
            permissionService = new PermissionServiceImpl();
        }
        return permissionService;
    }

    private AlertService getAlertService() {
        if (alertService == null) {
            alertService = new AlertServiceImpl(getNetworkRepository(), getLoginRepository());
        }
        return alertService;
    }

    private ConfigurationRepository getConfigurationRepository() {
        if (configurationRepository == null) {
            configurationRepository = new ConfigurationRepositoryImpl(getApplicationContext());
        }
        return configurationRepository;
    }

    public ConfigurationPresenter getConfigurationPresenter(ConfigurationView configurationView) {
        return new ConfigurationPresenterImpl(configurationView, getConfigurationRepository());
    }
}
