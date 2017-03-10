package com.generonumero.blocodaguarda.configuration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.generonumero.blocodaguarda.R;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ConfigurationFragment extends Fragment implements ConfigurationView {


    @Bind(R.id.teste)
    public RangeSeekBar rangeSeekBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.configuration_frag, null);
        ButterKnife.bind(this, view);

        rangeSeekBar.setSelected(true);
        rangeSeekBar.setSelectedMaxValue(30);
        return view;
    }

}
