package com.generonumero.blocodaguarda.alert.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.alert.presenter.AlertPresenter;
import com.generonumero.blocodaguarda.alert.view.AlertView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlertFragment extends Fragment implements AlertView {


    @Bind(R.id.alert_create_network)
    Button networkBt;

    AlertPresenter alertPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.alertPresenter = BDGApplication.getInstance().getAlertPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alert_frag, null);
        ButterKnife.bind(this, view);
        alertPresenter.loadViews();
        return view;
    }

    @Override
    public void showNetworkButton() {
        networkBt.setVisibility(View.VISIBLE);
    }
}
