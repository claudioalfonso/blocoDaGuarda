package com.generonumero.blocodaguarda.login.view.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.login.view.LoginView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginEmailActivity extends AppCompatActivity implements LoginView {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public static void start(Activity activity) {
        Intent i = new Intent(activity.getApplicationContext(), LoginEmailActivity.class);
        activity.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cadastro por e-mail");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onLoginSuccessful() {

    }

    @Override
    public void onLoginError() {

    }
}
