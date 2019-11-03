/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.max.easyhub.cpux;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.max.easyhub.cpux.adapter.PagerAdapter;
import com.max.easyhub.cpux.tools.LoaderData;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class ActivityMain extends FragmentActivity implements ActionBar.TabListener {

    
    PagerAdapter mAppSectionsPagerAdapter;

    private String[] tabTitle;
    //for ads
    private InterstitialAd interstitialAds;
    ViewPager mViewPager;
    ActionBar actionBar;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, getResources().getString(R.string.App_Id));
        tabTitle = getResources().getStringArray(R.array.tab_title);
        
        mAppSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabTitle.length);

        // Set up the action bar.
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        defineTabAnpageView();



    }

    
    public void defineTabAnpageView(){
    	mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(tabTitle[i]).setTabListener(this));
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    	//mAppSectionsPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
    	
    	showInterstitial();
    	
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    	//mAppSectionsPagerAdapter.notifyDataSetChanged();
    }

    

    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu=menu;
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
    private Menu menu;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
            	new LoaderInfo(this).execute("");
            return true;
            case R.id.privacy:
                Intent intent = new Intent(this, privacy.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
    
    
    public class LoaderInfo extends AsyncTask<String, String, String>{
    	LoaderData cpu = null;
    	String status = "failed";
    	Context context;
    	public LoaderInfo(Activity act){
    		context=act;
    		cpu = new LoaderData(act);
    		menu.getItem(0).setVisible(false);
    		setProgressBarIndeterminateVisibility(true);
    	}

		@Override
		protected String doInBackground(String... params) {
			try {
				cpu.loadCpuInfo();
		     	cpu.loadBateryInfo();
		     	cpu.loadDeviceInfo();
		     	cpu.loadSystemInfo();
		     	cpu.loadSupportInfo();
		     	status="succced";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		
				
		@Override
		protected void onPostExecute(String result) {
			setProgressBarIndeterminateVisibility(false);
			menu.getItem(0).setVisible(true);
			if(!status.equals("failed")){
				Toast.makeText(context, "Info updated", Toast.LENGTH_SHORT).show();
				//refresh view
				mAppSectionsPagerAdapter.notifyDataSetChanged();
			}
			super.onPostExecute(result);
		}

    }

    /**
	  * show ads
	  */
	public void showInterstitial() {
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
    
}
