package com.generonumero.blocodaguarda.menu.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.login.view.impl.LoginActivity;
import com.generonumero.blocodaguarda.menu.presenter.MainPresenter;
import com.generonumero.blocodaguarda.menu.view.MainView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {


    public final int MENU_MAIN = R.id.action_menu_main;
    public final int MENU_ABOUT = R.id.action_menu_about;
    public final int MENU_CONFIGURATION = R.id.action_menu_configuration;
    public final int MENU_NETWORK = R.id.action_menu_network;
    public final int MENU_SOS = R.id.action_menu_sos;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigation)
    NavigationView navigationView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    MainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainPresenter = BDGApplication.getInstance().getMainPresenter(this);

        mainPresenter.initView();

    }

    @Override
    public void goToLoginView() {
        LoginActivity.start(this);
    }

    @Override
    public void loadViews() {

        setupDrawerContent(navigationView);

    }


    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                onNavDrawerItemSelected(item);
                return false;
            }
        });
    }

    private void onNavDrawerItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case MENU_MAIN:
                Log.i("teste", "MENU_MAIN");
                break;
            case MENU_ABOUT:
                Log.i("teste", "MENU_ABOUT");
                break;
            case MENU_CONFIGURATION:
                Log.i("teste", "MENU_CONFIGURATION");
                break;
            case MENU_NETWORK:
                Log.i("teste", "MENU_NETWORK");
                break;
            case MENU_SOS:
                Log.i("teste", "MENU_SOS");
                break;


        }
    }

}
