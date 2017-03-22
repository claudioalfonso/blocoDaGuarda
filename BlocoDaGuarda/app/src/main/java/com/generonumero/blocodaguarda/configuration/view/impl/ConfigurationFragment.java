package com.generonumero.blocodaguarda.configuration.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.generonumero.blocodaguarda.BDGApplication;
import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.configuration.presenter.ConfigurationPresenter;
import com.generonumero.blocodaguarda.configuration.repository.TypeOfForm;
import com.generonumero.blocodaguarda.configuration.view.ConfigurationView;
import com.generonumero.blocodaguarda.menu.view.impl.MainActivity;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ConfigurationFragment extends Fragment implements ConfigurationView {


    @Bind(R.id.bdg_conf_range_seek_bar)
    RangeSeekBar<Integer> rangeSeekBar;
//    @Bind(R.id.bdg_conf_radio_push)
//    RadioButton radioPush;
//    @Bind(R.id.bdg_conf_radio_email)
//    RadioButton radioEmail;
//    @Bind(R.id.bdg_conf_radiogroup)
//    RadioGroup radioGroup;


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

    @OnClick(R.id.bdg_config_save)
    public void onClickSave() {
        int type = TypeOfForm.FORM_BY_PUSH;
//        if (radioGroup.getCheckedRadioButtonId() == radioPush.getId()) {
//            type = TypeOfForm.FORM_BY_PUSH;
//        }

        configurationPresenter.clickSaveConfigs(rangeSeekBar.getSelectedMaxValue(), type);
    }


    @Override
    public void onSaveData() {
        MainActivity activity = (MainActivity) getActivity();
        activity.goToHome();
    }

    @Override
    public void onDataLoaded(int time, int typeOfForm) {
        rangeSeekBar.setSelectedMaxValue(time);
//        switch (typeOfForm) {
//            case TypeOfForm.FORM_BY_PUSH:
//                radioGroup.check(radioPush.getId());
//                break;
//            case TypeOfForm.FORM_BY_EMAIL:
//                radioGroup.check(radioEmail.getId());
//                break;
//        }
    }
}
