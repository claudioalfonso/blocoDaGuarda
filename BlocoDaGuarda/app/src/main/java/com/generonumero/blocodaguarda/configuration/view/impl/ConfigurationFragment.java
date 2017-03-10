package com.generonumero.blocodaguarda.configuration.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.configuration.presenter.ConfigurationPresenter;
import com.generonumero.blocodaguarda.configuration.repository.TypeOfForm;
import com.generonumero.blocodaguarda.configuration.view.ConfigurationView;
import com.generonumero.blocodaguarda.menu.view.impl.MainActivity;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.generonumero.blocodaguarda.configuration.repository.TypeOfForm.*;


public class ConfigurationFragment extends Fragment implements ConfigurationView {


    @Bind(R.id.bdg_conf_range_seek_bar)
    RangeSeekBar<Integer> rangeSeekBar;
    @Bind(R.id.bdg_conf_radio_push)
    RadioButton radioPush;
    @Bind(R.id.bdg_conf_radio_email)
    RadioGroup radioGroup;

    @Bind(R.id.bdg_conf_radiogroup)
    AppCompatRadioButton radioEmail;

    private ConfigurationPresenter configurationPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configurationPresenter = BDGApplication.getInstance().getConfigurationPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.configuration_frag, null);
        ButterKnife.bind(this, view);

        configurationPresenter.loadViews();

        return view;
    }

    @Override
    public void onSaveData() {
        MainActivity activity = (MainActivity) getActivity();
        activity.goToHome();
    }

    @Override
    public void onDataLoaded(int time, int typeOfForm) {
        rangeSeekBar.setSelectedMaxValue(time);

        switch (typeOfForm) {
            case TypeOfForm.FORM_BY_PUSH:
                radioGroup.check(radioPush.getId());
                break;
            case FORM_BY_EMAIL:
                radioGroup.check(radioPush.getId());
                break;
        }


    }
}
