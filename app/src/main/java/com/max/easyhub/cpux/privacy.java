package com.max.easyhub.cpux;


import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


public class privacy extends Activity {
    ActionBar actionBar;
    private AdView mAdView;
    private InterstitialAd interstitialAds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        MobileAds.initialize(this, getResources().getString(R.string.App_Id));
        WebView webview = findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("file:///android_asset/index.html");
        initializead();
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    private void initializead() {
        interstitialAds = new InterstitialAd(this);
        interstitialAds.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        interstitialAds.loadAd(new AdRequest.Builder().build());
        interstitialAds.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // TODO Auto-generated method stub
                super.onAdLoaded();
                if (interstitialAds.isLoaded()) {
                    interstitialAds.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // TODO Auto-generated method stub

                super.onAdFailedToLoad(errorCode);
            }
        });

    }
    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if ( mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if ( mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if ( mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}


