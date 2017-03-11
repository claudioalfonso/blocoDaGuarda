package com.generonumero.blocodaguarda.alert.view.impl;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.alert.presenter.AlertPresenter;
import com.generonumero.blocodaguarda.alert.view.AlertView;
import com.generonumero.blocodaguarda.menu.view.impl.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlertFragment extends Fragment implements AlertView {

    private static final String PERMISSION = Manifest.permission.SEND_SMS;
    @Bind(R.id.alert_create_network)
    Button networkBt;

    private AlertPresenter alertPresenter;

    private Dialog alertDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.alertPresenter = BDGApplication.getInstance().getAlertPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        alertPresenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        alertPresenter.onStop();
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
    void onClickNetworkButton(View v) {
        alertPresenter.onClickNetwork();
    }

    @OnClick(R.id.alert_bt_save_me)
    void onClickSaveMeButton(View v) {
        alertPresenter.onClickSaveMe(this);
    }

    @Override
    public void showNetworkButton() {
        networkBt.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNetworkButton() {
        networkBt.setVisibility(View.GONE);
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
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        alertPresenter.onRequestPermissionsResult(getActivity(), requestCode, grantResults, PERMISSION);
    }

    @Override
    public void showSafeScreen(int time) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog_activate, null);

        View dialogButton = view.findViewById(R.id.bdg_alert_help_dialog_button);
        TextView textView = (TextView) view.findViewById(R.id.bdg_alert_save_me);


        textView.setText(getString(R.string.bdg_alert_help_dialog_time, Integer.toString(time)));

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Envio cancelado. Que bom que você está a salvo.", Toast.LENGTH_LONG).show();
                alertPresenter.onCancelClick();
            }
        });

        alertDialog = new Dialog(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        alertDialog.setContentView(view);

        alertDialog.show();

    }

    @Override
    public void dismissSafeScreen() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void disclaimerSMS() {

    }

    @Override
    public void showAlertPermissionDenied() {
        Toast.makeText(getContext(), "Precisamos da permissão de enviar sms, para poder contactar sua rede quando for avisar que você está em uma situação de risco.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAlertPermissionDisable() {
        Toast.makeText(getContext(), "Precisamos da permissão de enviar sms, para poder contactar sua rede quando for avisar que você está em uma situação de risco. " +
                "\nPor favor, vá em configurações e habilite para conseguir enviar o alerta. ", Toast.LENGTH_LONG).show();
    }
}
