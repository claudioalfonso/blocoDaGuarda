package com.generonumero.blocodaguarda.about.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.generonumero.blocodaguarda.R;
import com.generonumero.blocodaguarda.about.view.tracking.AboutTracking;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutFragment extends Fragment {


    private int image;
    private int about;
    private int link;
    private String linkUrl;
    private String contentType;
    private AboutTracking aboutTracking;

    public static AboutFragment getInstanceFromAbout() {

        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.image = R.drawable.logo_big;
        aboutFragment.about = R.string.bdg_bd_about;
        aboutFragment.link = R.string.bdg_bd_about_link;
        aboutFragment.contentType = "app";
        aboutFragment.linkUrl = "http://bracosdados.generonumero.media/termosdeuso/Termosdeuso-BracosDados.pdf";
        return aboutFragment;
    }

    public static AboutFragment getInstanceFromGN() {
        AboutFragment aboutFragment = new AboutFragment();
        aboutFragment.image = R.drawable.logo_gn;
        aboutFragment.about = R.string.bdg_about;
        aboutFragment.link = R.string.bdg_about_link;
        aboutFragment.contentType = "generoNumero";
        aboutFragment.linkUrl = "http://www.generonumero.media";
        return aboutFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aboutTracking = new AboutTracking();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_frag, null);
        ButterKnife.bind(this, view);

        ImageView imageView = (ImageView) view.findViewById(R.id.bdg_about_image);
        TextView about = (TextView) view.findViewById(R.id.bdg_about);
        TextView link = (TextView) view.findViewById(R.id.bdg_about_link);

        imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), image));
        about.setText(getString(this.about));
        link.setText(Html.fromHtml(getString(this.link)));

        aboutTracking.onContentView(contentType);

        return view;
    }


    @OnClick(R.id.bdg_about_link)
    public void sendToSite() {

        aboutTracking.clickLink(contentType);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl));
        startActivity(browserIntent);
    }
}
