package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;
/*
import android.app.*;
import android.os.*;
import android.widget.*;
import android.net.*;
import android.media.*;
import android.widget.MediaController.*;
import android.view.View;
import android.content.pm.ActivityInfo;

// Credit Goes to Md Ahsan Kobir
// Subscribe to our Youtube Channel
// TT & TBD : Tech Tips & Tricks BD
// http://m.youtube.com/c/techtipstricksbd
// Contact me at fb.me/ak.ahsan.kobir
/*
import com.facebook.ads.AudienceNetworkAds;
//step 2 banner
import com.facebook.ads.*;


import com.facebook.ads.AbstractAdListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import android.widget.LinearLayout;
import android.util.Log;*/

/*
public class EbookTvPlayer extends Activity
{
	//private AdView adView;
    //private InterstitialAd interstitialAd;
	//private String TAG = "Home";


	VideoView vv;
	MediaController mc;
	String link;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//AudienceNetworkAds.initialize(this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ebook_tv_player);*/

		/*adView = new AdView(this, "423301482132187_423302455465423", AdSize.BANNER_HEIGHT_50);
		 // Find the Ad Container
		 LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
		 // Add the ad view to your activity layout
		 adContainer.addView(adView);
		 // Request an ad
		 adView.loadAd();*/


		//	interstitialAd = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#2811380842414575_2811476129071713");

		/*interstitialAd = new InterstitialAd(Player.this, "423301482132187_423301748798827");
		InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
			@Override
			public void onInterstitialDisplayed(Ad ad) {
				// Interstitial ad displayed callback
				Log.e(TAG, "Interstitial ad displayed.");
			}

			@Override
			public void onInterstitialDismissed(Ad ad) {
				// Interstitial dismissed callback
				Log.e(TAG, "Interstitial ad dismissed.");
			}

			@Override
			public void onError(Ad ad, AdError adError) {
				// Ad error callback
				Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
			}

			@Override
			public void onAdLoaded(Ad ad) {
				// Interstitial ad is loaded and ready to be displayed
				Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
				// Show the ad
				interstitialAd.show();
			}

			@Override
			public void onAdClicked(Ad ad) {
				// Ad clicked callback
				Log.d(TAG, "Interstitial ad clicked!");
			}

			@Override
			public void onLoggingImpression(Ad ad) {
				// Ad impression logged callback
				Log.d(TAG, "Interstitial ad impression logged!");
			}
		};

		// For auto play video ads, it's recommended to load the ad
		// at least 30 seconds before it is shown
		interstitialAd.loadAd(
			interstitialAd.buildLoadAdConfig()
			.withAdListener(interstitialAdListener)
			.build());

*/

/*

		ImageButton rotate = findViewById(R.id.rotate);
		rotate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int rotation = getWindowManager().getDefaultDisplay().getRotation();
					if (rotation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					}else{
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					}
				}
			});



		vv = (VideoView) findViewById(R.id.vv);
		mc = new MediaController(this);

		play();
	}

	private void play()
	{
		String link = getIntent().getStringExtra("link");
		//Uri   link = Uri.parse(link);
		vv.setVideoURI(Uri.parse(link));
		//vv.setVideoURI(link);
		vv.setMediaController(mc);
		mc.setAnchorView(mc);
		vv.start();

		//End of play
	}


}*/



import android.app.*;
import android.os.*;
import android.widget.*;
import android.net.*;
import android.view.View;
import android.content.pm.ActivityInfo;

public class EbookTvPlayer extends Activity
{
	MediaController mc;

	String link;
	VideoView vv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ebook_tv_player);
		ImageButton rotate = findViewById(R.id.rotate);
		rotate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int rotation = getWindowManager().getDefaultDisplay().getRotation();
					if (rotation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					}else{
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					}
				}
			});
		
		vv=findViewById(R.id.vv);
		link=getIntent().getStringExtra("link");
		vv.setVideoURI(Uri.parse(link));

		MediaController mc=new MediaController(this);
		vv.setMediaController(mc);

		vv.requestFocus();
		vv.start();

	}


}
