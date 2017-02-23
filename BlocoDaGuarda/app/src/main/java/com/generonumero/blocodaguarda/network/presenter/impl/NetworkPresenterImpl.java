package com.generonumero.blocodaguarda.network.presenter.impl;

import com.generonumero.blocodaguarda.network.presenter.NetworkPresenter;
import com.generonumero.blocodaguarda.network.view.NetworkView;

public class NetworkPresenterImpl implements NetworkPresenter {


    private NetworkView networkView;

    public NetworkPresenterImpl(NetworkView networkView) {
        this.networkView = networkView;
    }

    @Override
    public void loadViews() {
        networkView.OnLoadViews();
    }
}
