package com.generonumero.blocodaguarda.menu.presenter.impl;


import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.login.service.FacebookLoginService;
import com.generonumero.blocodaguarda.menu.presenter.MainPresenter;
import com.generonumero.blocodaguarda.menu.repository.MenuRepository;
import com.generonumero.blocodaguarda.menu.view.MainView;

public class MainPresenterImpl implements MainPresenter {


    private MainView mainView;
    private FacebookLoginService facebookLoginService;
    private MenuRepository menuRepository;

    public MainPresenterImpl(MainView mainView, MenuRepository menuRepository) {
        this.mainView = mainView;
        this.menuRepository = menuRepository;
        this.facebookLoginService = BDGApplication.getInstance().getFacebookLoginService();
    }

    @Override
    public void initView() {
        if (!facebookLoginService.isLogged()) {
            mainView.goToLoginView();
        } else {
            mainView.loadViews();
            if (menuRepository.isFirstOpen()) {
                mainView.showFirstOpenAppDialog();
            }
        }
    }

    @Override
    public void clickDialogAddContacts() {
        mainView.goToNetworkView();
        menuRepository.saveFirstOpen();
    }

    @Override
    public void clickDialogNotAddContacts() {
        menuRepository.saveFirstOpen();
    }
}
