package com.generonumero.blocodaguarda.about.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.generonumero.blocodaguarda.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_frag, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.bdg_about_link)
    public void sendToSite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.generonumero.media"));
        startActivity(browserIntent);
    }
}
