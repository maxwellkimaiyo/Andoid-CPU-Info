package com.max.easyhub.cpux;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.max.easyhub.cpux.tools.LoaderData;

public class ActivitySplash extends Activity {

	LoaderData cpu = null;
	LinearLayout lyt_progress;
	TextView tv_message;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title bar
     	requestWindowFeature(Window.FEATURE_NO_TITLE);
     		
     	//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
     	setContentView(R.layout.activity_splash);
     	tv_message	 =	(TextView) findViewById(R.id.tv_message);
     	lyt_progress = 	(LinearLayout) findViewById(R.id.lyt_progress);
     	cpu = new LoaderData(this);
     	
     	new LoaderInfo().execute("");
    }

    public class LoaderInfo extends AsyncTask<String, String, String>{

    	public LoaderInfo(){
    		tv_message.setText("Starting...");
    	}

		@Override
		protected String doInBackground(String... params) {
			try {
				
				publishProgress("load cpu info");
				Thread.sleep(300);
				cpu.loadCpuInfo();
				
				publishProgress("load batery info");
				Thread.sleep(300);
		     	cpu.loadBateryInfo();
		     	
		     	publishProgress("load device info");
		     	Thread.sleep(300);
		     	cpu.loadDeviceInfo();
		     	
		     	publishProgress("load system info");
		     	Thread.sleep(300);
		     	cpu.loadSystemInfo();
		     	
		     	publishProgress("load sensor info");
		     	Thread.sleep(300);
		     	cpu.loadSupportInfo();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			tv_message.setText(values[0]);
			super.onProgressUpdate(values);
		}
				
		@Override
		protected void onPostExecute(String result) {
			lyt_progress.setVisibility(View.GONE);
			Intent i = new Intent(getApplicationContext(), ActivityMain.class);
			startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			finish();
			super.onPostExecute(result);
		}

    	
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
    
}
