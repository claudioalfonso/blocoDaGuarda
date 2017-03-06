package com.generonumero.blocodaguarda;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.generonumero.blocodaguarda.alert.presenter.AlertPresenter;
import com.generonumero.blocodaguarda.alert.presenter.impl.AlertPresenterImpl;
import com.generonumero.blocodaguarda.alert.service.AlertService;
import com.generonumero.blocodaguarda.alert.service.impl.AlertServiceImpl;
import com.generonumero.blocodaguarda.alert.view.AlertView;
import com.generonumero.blocodaguarda.analytics.BDGTracking;
import com.generonumero.blocodaguarda.login.presenter.LoginPresenter;
import com.generonumero.blocodaguarda.login.presenter.impl.LoginPresenterImpl;
import com.generonumero.blocodaguarda.login.service.FacebookLoginService;
import com.generonumero.blocodaguarda.login.view.LoginView;
import com.generonumero.blocodaguarda.menu.presenter.MainPresenter;
import com.generonumero.blocodaguarda.menu.presenter.impl.MainPresenterImpl;
import com.generonumero.blocodaguarda.menu.view.MainView;
import com.generonumero.blocodaguarda.network.presenter.NetworkPresenter;
import com.generonumero.blocodaguarda.network.presenter.impl.NetworkPresenterImpl;
import com.generonumero.blocodaguarda.network.repository.NetworkRepository;
import com.generonumero.blocodaguarda.network.repository.impl.NetworkRepositoryImpl;
import com.generonumero.blocodaguarda.network.view.NetworkView;
import com.generonumero.blocodaguarda.permission.service.PermissionService;
import com.generonumero.blocodaguarda.permission.service.impl.PermissionServiceImpl;
import com.squareup.otto.Bus;

public class BDGApplication extends Application {

    private static BDGApplication instance;

    private FacebookLoginService facebookLoginService;

    private Bus bus;

    private NetworkRepository networkRepository;

    private PermissionService permissionService;

    private AlertService alertService;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        instance = this;

        BDGTracking.initialize(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
    }

    public static BDGApplication getInstance() {
        return instance;
    }

    public MainPresenter getMainPresenter(MainView mainView) {
        return new MainPresenterImpl(mainView);
    }

    public FacebookLoginService getFacebookLoginService() {
        if (facebookLoginService == null) {
            facebookLoginService = new FacebookLoginService(getApplicationContext(), getBus());
        }
        return facebookLoginService;
    }

    public LoginPresenter getLoginPresenter(LoginView loginView) {
        return new LoginPresenterImpl(loginView, getFacebookLoginService(), getBus());
    }

    public NetworkPresenter getNetworkPresenter(NetworkView networkView) {
        return new NetworkPresenterImpl(networkView, getNetworkRepository(), getPermissionService());
    }

    public AlertPresenter getAlertPresenter(AlertView alertView) {
        return new AlertPresenterImpl(alertView, getAlertService(), getBus(), getPermissionService());
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

    public AlertService getAlertService() {
        if (alertService == null) {
            alertService = new AlertServiceImpl(getNetworkRepository());
        }
        return alertService;
    }
}
