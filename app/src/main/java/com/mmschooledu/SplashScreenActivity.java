package com.mmschooledu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.google.android.ump.ConsentInformation;


public class SplashScreenActivity extends Activity {

private ConsentInformation consentInformation;


	SharedPreferences SharedPreferenceCheck;
	String banner, inter, admob_banner, admob_inter, Bundle;
	private String check;

	private SharedPreferences sharedPreferencesCheck;

	@Override
	public void onCreate(Bundle savedInstanceState) {
					super.onCreate(savedInstanceState);

			AppLocale.applySaved(this);
			requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.splash_activity);
		//requestConsform();

		sharedPreferencesCheck = SplashScreenActivity.this.getSharedPreferences("Check", Context.MODE_PRIVATE);
		check = sharedPreferencesCheck.getString("check", "");

		if (check.equals("")) {
			Intent i = new Intent(SplashScreenActivity.this, SearchActivity.class);
			Bundle b = new Bundle();
			b.putString("banner", banner);
			b.putString("inter", inter);
			b.putString("admob_banner", admob_banner);
			b.putString("admob_inter", admob_inter);
			i.putExtras(b);
			startActivity(i);
			finish();
		} else if (check.equals("၆၆၆၆၆")) {
			Intent i = new Intent(SplashScreenActivity.this, MainFragmentActivity .class);
			Bundle b = new Bundle();


			b.putString("link", "https://musicforhearts.blogspot.com");
			b.putString("banner", banner);
			b.putString("inter", inter);
			b.putString("admob_banner", admob_banner);
			b.putString("admob_inter", admob_inter);
			i.putExtras(b);
			startActivity(i);
			finish();
		} else if (check.equals("66666")) {
			Intent i = new Intent(SplashScreenActivity.this, MainFragmentActivity .class);
			Bundle b = new Bundle();


			b.putString("link", "https://musicforhearts.blogspot.com");
			b.putString("banner", banner);
			b.putString("inter", inter);
			b.putString("admob_banner", admob_banner);
			b.putString("admob_inter", admob_inter);
			i.putExtras(b);
			startActivity(i);
			finish();
		} else if (check.equals("User")) {
			Intent i = new Intent(SplashScreenActivity.this, MainFragmentActivity.class);
			Bundle b = new Bundle();

			b.putString("link", "https://musicforhearts.blogspot.com");
			b.putString("banner", banner);
			b.putString("inter", inter);
			b.putString("admob_banner", admob_banner);
			b.putString("admob_inter", admob_inter);
			i.putExtras(b);
			startActivity(i);
			finish();
		}

		//getconcentStatus();
	}

	@Override
	public void onBackPressed() {
		// SplashScreenActivity is a terminal entry screen: Back exits the task.
		finishAffinity();
	}
}
