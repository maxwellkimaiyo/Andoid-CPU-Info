package com.max.easyhub.cpux;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.max.easyhub.cpux.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * A fragment that launches other parts of the demo application.
 */
public class PageAbout extends Fragment {
    private AdView mAdView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);


        mAdView = rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        
        // Demonstration of a collection-browsing activity.
        rootView.findViewById(R.id.bt_read).setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View view) {
        		Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
            	Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            	try {
            	  startActivity(goToMarket);
            	} catch (ActivityNotFoundException e) {
            		getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
            	}
            }
        });


        return rootView;
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
