package com.generonumero.blocodaguarda.login.view.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.login.presenter.LoginPresenter;
import com.generonumero.blocodaguarda.login.view.LoginView;
import com.generonumero.blocodaguarda.menu.view.impl.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements LoginView {


    private LoginPresenter loginPresenter;


    public static void start(Activity activity) {
        Intent i = new Intent(activity.getApplicationContext(), LoginActivity.class);
        activity.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginPresenter = BDGApplication.getInstance().getLoginPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginPresenter.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginPresenter.onActivityResult(requestCode, resultCode,  data);
    }


    @OnClick(R.id.bdg_facebook_login_bt)
    public void login(View v) {
        loginPresenter.login(this);
    }


    @OnClick(R.id.bdg_email_login_bt)
    public void loginEmail(View v) {
        LoginEmailActivity.start(this);
    }


    @Override
    public void onLoginSuccessful() {
        finish();
        MainActivity.start(this);
    }

    @Override
    public void onLoginError() {
        Toast.makeText(this, "Ocorreu um erro ao logar, tente novamente.", Toast.LENGTH_SHORT).show();
    }
}

