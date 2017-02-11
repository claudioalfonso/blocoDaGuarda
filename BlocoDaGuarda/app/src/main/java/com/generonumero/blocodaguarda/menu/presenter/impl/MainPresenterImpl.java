package com.generonumero.blocodaguarda.menu.presenter.impl;


import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.login.service.FacebookLoginService;
import com.generonumero.blocodaguarda.menu.presenter.MainPresenter;
import com.generonumero.blocodaguarda.menu.view.MainView;

public class MainPresenterImpl implements MainPresenter {


    private MainView mainView;
    private FacebookLoginService facebookLoginService;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.facebookLoginService = BDGApplication.getInstance().getFacebookLoginService();
    }

    @Override
    public void initView() {
        if(!facebookLoginService.isLogged()) {
            mainView.goToLoginView();
        } else {
            mainView.loadViews();
        }
    }
}
