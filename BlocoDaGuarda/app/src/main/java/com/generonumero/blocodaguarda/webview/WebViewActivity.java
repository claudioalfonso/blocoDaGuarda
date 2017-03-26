package com.generonumero.blocodaguarda.webview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.generonumero.blocodaguarda.R;

public class WebViewActivity extends AppCompatActivity {


    public static void start(Activity activity, String url) {
        Intent intent = new Intent(activity, WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        webView = (WebView) findViewById(R.id.bdg_webview);

        String url = null;
        if(getIntent() != null && getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            if(extras.getString("url") != null) {
                url = extras.getString("url");
            }
        }

        if(url != null && !url.trim().equals("")){
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        } else {
            finish();
        }


    }
}
