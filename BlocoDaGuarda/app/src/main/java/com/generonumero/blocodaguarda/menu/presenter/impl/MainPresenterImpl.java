package com.generonumero.blocodaguarda.menu.presenter.impl;


import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.login.repository.LoginRepository;
import com.generonumero.blocodaguarda.login.service.FacebookLoginService;
import com.generonumero.blocodaguarda.menu.presenter.MainPresenter;
import com.generonumero.blocodaguarda.menu.repository.MenuRepository;
import com.generonumero.blocodaguarda.menu.view.MainView;

public class MainPresenterImpl implements MainPresenter {


    private MainView mainView;
    private MenuRepository menuRepository;
    private LoginRepository loginRepository;

    public MainPresenterImpl(MainView mainView, MenuRepository menuRepository, LoginRepository loginRepository) {
        this.mainView = mainView;
        this.menuRepository = menuRepository;
        this.loginRepository = loginRepository;
    }

    @Override
    public void initView() {
        if (!loginRepository.isLogged()) {
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
