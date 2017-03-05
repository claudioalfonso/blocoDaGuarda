package com.generonumero.blocodaguarda.alert.view.impl;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.alert.presenter.AlertPresenter;
import com.generonumero.blocodaguarda.alert.view.AlertView;
import com.generonumero.blocodaguarda.menu.view.impl.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlertFragment extends Fragment implements AlertView {


    @Bind(R.id.alert_create_network)
    Button networkBt;

    private AlertPresenter alertPresenter;

    private AlertDialog alertDialog;

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

    @OnClick(R.id.alert_create_network)
    public void onClickNetworkButton(View v) {
        alertPresenter.onClickNetwork();
    }

    @OnClick(R.id.alert_bt_save_me)
    public void onClickSaveMeButton(View v) {
        alertPresenter.onClickSaveMe();
    }

    public void onClickHelpMeButton(View v) {
        alertPresenter.onClickHelpMe();
    }


    @Override
    public void showNetworkButton() {
        networkBt.setVisibility(View.VISIBLE);
    }

    @Override
    public void goToNetworkScreen() {
        MainActivity activity = (MainActivity) getActivity();
        activity.goToNetworkView();
    }

    @Override
    public void showNetworkPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.bdg_alert_network_dialog_title));
        builder.setMessage(getString(R.string.bdg_alert_network_dialog_message));
        builder.setPositiveButton(getString(R.string.bdg_alert_network_dialog_positive_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertPresenter.onClickNetwork();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.bdg_alert_network_dialog_negative_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        // Create the AlertDialog object and return it
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void showSafeScreen() {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View inflate = inflater.inflate(R.layout.alert_dialog_activate, null);

        Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(inflate);

        dialog.show();

    }
}
