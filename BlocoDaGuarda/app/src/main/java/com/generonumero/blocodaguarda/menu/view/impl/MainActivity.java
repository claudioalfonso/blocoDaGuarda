package com.generonumero.blocodaguarda.menu.view.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.about.view.AboutFragment;
import com.generonumero.blocodaguarda.alert.view.impl.AlertFragment;
import com.generonumero.blocodaguarda.configuration.view.impl.ConfigurationFragment;
import com.generonumero.blocodaguarda.login.view.impl.LoginActivity;
import com.generonumero.blocodaguarda.menu.presenter.MainPresenter;
import com.generonumero.blocodaguarda.menu.view.MainView;
import com.generonumero.blocodaguarda.network.view.impl.NetworkFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {


    public final int MENU_MAIN = R.id.action_menu_main;
    public final int MENU_ABOUT = R.id.action_menu_about;
    public final int MENU_CONFIGURATION = R.id.action_menu_configuration;
    public final int MENU_NETWORK = R.id.action_menu_network;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigation)
    NavigationView navigationView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MainPresenter mainPresenter;

    private Map<Integer, Fragment> fragments;


    public static void start(Activity activity) {
        Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        verifyDeepLink();

        mainPresenter = BDGApplication.getInstance().getMainPresenter(this);
        mainPresenter.initView();
    }

    private void verifyDeepLink() {
        Log.i("teste", "verifyDeepLink");
        Intent intent = getIntent();
        if (intent != null) {
            Log.i("teste", "intent != null");
            if (intent.getExtras() != null) {
                Log.i("teste", intent.getExtras().toString());
                Bundle extras = intent.getExtras();
                for (String key : extras.keySet()) {
                    Log.i("teste", "key: " + key + " - value:  " + extras.get(key));
                }
            } else {
                Log.i("teste", "no extras");

            }

        }
    }

    @Override
    public void goToLoginView() {
        LoginActivity.start(this);
        finish();
    }

    @Override
    public void loadViews() {
        setupDrawerContent(navigationView);
        setFirstItemNavigationView();
    }

    public void goToNetworkView() {
        changeFragment(getFragment(MENU_NETWORK));
    }

    public void goToHome() {
        changeFragmentWithoutBackStack(getFragment(MENU_MAIN));
    }


    private void setFirstItemNavigationView() {
        navigationView.setCheckedItem(MENU_MAIN);
        navigationView.getMenu().performIdentifierAction(MENU_MAIN, 0);
    }

    private void setupDrawerContent(NavigationView navigationView) {

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setIcon(R.drawable.logo_small);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                onNavDrawerItemSelected(item);
                return false;
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = getActionBarDrawerToggle(toolbar);
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private ActionBarDrawerToggle getActionBarDrawerToggle(Toolbar toolbar) {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
    }

    private void onNavDrawerItemSelected(MenuItem menuItem) {
        Fragment fragment = getFragment(menuItem.getItemId());
        changeFragmentWithoutBackStack(fragment);
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }

    }

    private Fragment getFragment(int id) {
        switch (id) {
            case MENU_MAIN:
                if (getFragments().get(MENU_MAIN) == null) {
                    getFragments().put(MENU_MAIN, new AlertFragment());
                }
                toolbar.setTitle("  Braços dados");
                break;
            case MENU_ABOUT:
                if (getFragments().get(MENU_ABOUT) == null) {
                    getFragments().put(MENU_ABOUT, new AboutFragment());
                }
                toolbar.setTitle("  Gênero e número");
                break;
            case MENU_CONFIGURATION:
                if (getFragments().get(MENU_CONFIGURATION) == null) {
                    getFragments().put(MENU_CONFIGURATION, new ConfigurationFragment());
                }
                toolbar.setTitle("  Configurações");
            case MENU_NETWORK:
                if (getFragments().get(MENU_NETWORK) == null) {
                    getFragments().put(MENU_NETWORK, new NetworkFragment());
                }
                toolbar.setTitle("  Rede de confiança");
                break;
        }
        return getFragments().get(id);
    }


    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void changeFragmentWithoutBackStack(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private Map<Integer, Fragment> getFragments() {
        if (fragments == null) {
            fragments = new HashMap<>();
        }
        return fragments;
    }
}
