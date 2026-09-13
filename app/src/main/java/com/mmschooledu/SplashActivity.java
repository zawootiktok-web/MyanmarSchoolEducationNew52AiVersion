package com.mmschooledu;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

import java.util.concurrent.atomic.AtomicBoolean;

public class SplashActivity extends Activity {
	//private AudienceNetwork audienceNetwork;
	AdView adView;

	private Button en,mm;
	ConsentForm form;

	private ConsentInformation consentInformation;
	Button view;
	private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		//requestConsform();

					// Do not preload ads from the splash screen. The explicit rewarded action
			// below loads/shows its ad only while this Activity is active.

		en = findViewById(R.id.en);
		mm = findViewById(R.id.mm);
		view =(Button) findViewById(R.id.view);

//		adView = (AdView)
//				findViewById(R.id.adView);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		adView.loadAd(adRequest);


//		audienceNetwork = new AudienceNetwork(this)
//				.setFullScreenStatus(true)
//				// .setBannerStatus(true)
//				.init()
//				// .setInterstitialId("VID_HD_16_9_15S_APP_INSTALL#YOUR_PLACEMENT_ID")
//				.setInterstitialId("1072549720831975_1325325095554435")
//				//.setBannerId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
//				.setBannerId("1072549720831975_1072552904164990")
//				.setBannerStatus(true)
//				.setBannerLayoutId(R.id.ad_container) // LinearLayout ID for the banner
//				.build();
//
//		// Load banner ad into the specified container
//		LinearLayout bannerContainer = findViewById(R.id.ad_container);



		view.setOnClickListener(new OnClickListener(){


			@Override
			public void onClick(View p1) {
				new Admob(new onDismiss() {
					@Override
					public void onDismiss() {


						Intent i = new Intent(SplashActivity.this,WelcomeActivity2.class);
						startActivity(i);
					}
				}).ShowRewardedInterstitial(SplashActivity.this, true);
			}

			;
		});

		mm.setOnClickListener(new OnClickListener(){


							@Override
				public void onClick(View p1) {
					AppLocale.saveLanguage(SplashActivity.this, AppLocale.MYANMAR_ID);
					Intent i = new Intent(SplashActivity.this, SplashScreenActivity.class);
					startActivity(i);
				}

		});
		en.setOnClickListener(new OnClickListener(){

							@Override
				public void onClick(View p1) {
					AppLocale.saveLanguage(SplashActivity.this, AppLocale.ENGLISH_ID);
					Intent i = new Intent(SplashActivity.this, SplashScreenActivity.class);
					startActivity(i);
				}

//			}).ShowRewardedInterstitial(SplashActivity.this, true);
//		}

//		;
	});
		//getconcentStatus();
	}


	private void requestConsform() {
//		ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(this)
////			.addTestDeviceHashedId("36AC515B03D289296D467572BED30BE8")
//				.addTestDeviceHashedId("3AF928E9BC876E08D36CEED8015F8777")
//				.setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
//				.build();


		ConsentRequestParameters params = new ConsentRequestParameters
				.Builder()
				//.setConsentDebugSettings(debugSettings)
				.setTagForUnderAgeOfConsent(false)
				.build();

		consentInformation = UserMessagingPlatform.getConsentInformation(this);
		consentInformation.requestConsentInfoUpdate(
				this,
				params,
				(ConsentInformation.OnConsentInfoUpdateSuccessListener) () -> {
					UserMessagingPlatform.loadAndShowConsentFormIfRequired(
							this,
							(ConsentForm.OnConsentFormDismissedListener) loadAndShowError -> {
								if (loadAndShowError != null) {
									// Consent gathering failed.
									Log.w(TAG, String.format("%s: %s",
											loadAndShowError.getErrorCode(),
											loadAndShowError.getMessage()));
								}

								// Consent has been gathered.
								if (consentInformation.canRequestAds()) {
									initializeMobileAdsSdk();
									loadbannerads(true);
								}
							}
					);
				},
				(ConsentInformation.OnConsentInfoUpdateFailureListener) requestConsentError -> {
					// Consent gathering failed.
					Log.w(TAG, String.format("%s: %s",
							requestConsentError.getErrorCode(),
							requestConsentError.getMessage()));
				});

		// Check if you can initialize the Google Mobile Ads SDK in parallel
		// while checking for new consent information. Consent obtained in
		// the previous session can be used to request ads.
		if (consentInformation.canRequestAds()) {
			initializeMobileAdsSdk();
			loadbannerads(true);
		}
	}
	private void initializeMobileAdsSdk () {

		new Admob(new onDismiss() {
			@Override
			public void onDismiss() {
			}
		}).ShowInterstitial(SplashActivity.this, true);
	}

//
	private void loadbannerads(boolean isPersonlized) {
		//AdView adView = findViewById(R.id.adView);
		AdRequest adRequest;
		if (isPersonlized) {
			adRequest = new AdRequest.Builder().build();
		} else {
			Bundle bundle = new Bundle();
			bundle.putString("npa", "1");
			adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, bundle).build();
		}

		adView.loadAd(adRequest);

	}



	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		finishAffinity();
		super.onBackPressed();
	}





}
