package com.generonumero.blocodaguarda.alert.presenter;


import android.support.v4.app.Fragment;

import com.generonumero.blocodaguarda.alert.event.CountDownFinished;
import com.generonumero.blocodaguarda.permission.presenter.PermissionPresenter;
import com.generonumero.blocodaguarda.presenter.BasePresenter;

public interface AlertPresenter extends BasePresenter, PermissionPresenter {

    void loadViews();

    void onClickNetwork();

    void onClickSaveMe(Fragment fragment);

    void onClickHelpMe(Fragment fragment);

    void onCountDownFinished(CountDownFinished countDownFinished);

}
