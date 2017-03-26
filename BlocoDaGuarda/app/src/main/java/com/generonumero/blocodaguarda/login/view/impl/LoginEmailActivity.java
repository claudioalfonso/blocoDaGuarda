package com.generonumero.blocodaguarda.login.view.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.configuration.repository.TypeOfForm;
import com.generonumero.blocodaguarda.login.event.UserProfile;
import com.generonumero.blocodaguarda.login.presenter.LoginEmailPresenter;
import com.generonumero.blocodaguarda.login.view.LoginView;
import com.generonumero.blocodaguarda.menu.view.impl.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginEmailActivity extends AppCompatActivity implements LoginView {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bdg_loginemail_name)
    EditText name;
    @Bind(R.id.bdg_loginemail_email)
    EditText email;
    @Bind(R.id.bdg_loginemail_city)
    EditText city;
    @Bind(R.id.bdg_loginemail_neighborhood)
    EditText neighborhood;
    @Bind(R.id.bdg_login_radiogroup)
    RadioGroup radioGroup;
    @Bind(R.id.bdg_loginemail_gender_male)
    RadioButton radioMale;
    @Bind(R.id.bdg_loginemail_gender_female)
    RadioButton radioFemale;


    private LoginEmailPresenter loginEmailPresenter;

    public static void start(Activity activity) {
        Intent i = new Intent(activity.getApplicationContext(), LoginEmailActivity.class);
        activity.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        ButterKnife.bind(this);

        loginEmailPresenter = BDGApplication.getInstance().getLoginEmailPresenter(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cadastro por e-mail");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        loginEmailPresenter = null;
    }

    @OnClick(R.id.bdg_email_login_bt)
    public void login(View v) {

        int type = UserProfile.FEMALE;
        if (radioGroup.getCheckedRadioButtonId() == radioMale.getId()) {
            type = UserProfile.MALE;
        }

        String nameText = name.getText().toString();
        String emailText = email.getText().toString();
        String cityText = city.getText().toString();
        String neighborhoodText = neighborhood.getText().toString();

        loginEmailPresenter.clickLogin(nameText, emailText,cityText, neighborhoodText, type);
    }


    @Override
    public void onLoginSuccessful() {
        finish();
        MainActivity.start(this);
    }

    @Override
    public void onLoginError() {
        Toast.makeText(this, "Por favor, preencha os campos corretamente.", Toast.LENGTH_SHORT).show();
    }
}
