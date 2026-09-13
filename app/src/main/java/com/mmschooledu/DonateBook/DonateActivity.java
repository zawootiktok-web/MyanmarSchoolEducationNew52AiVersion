package com.mmschooledu.DonateBook;

import android.app.Activity;

public class DonateActivity extends Activity
{}
	/*Button btn;
	TextView txt;
	private InterstitialAd interstitial;
	private AdView adView;     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donate);
		
		//ca-app-pub-2481530783933136/5610125809

		adView = (AdView) 
			findViewById(R.id.ad_view);           
		AdRequest adRequest = new AdRequest.Builder().build();           
		adView.loadAd(adRequest); 

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(DonateActivity.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

        interstitial.loadAd(adRequest);
        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
				public void onAdLoaded() {
					// Call displayInterstitial() function
					displayInterstitial();
				}

				public void displayInterstitial() {
					// If Ads are loaded, show Interstitial else show nothing.
					if (interstitial.isLoaded()) {
						interstitial.show();
					}

				}
				
			});
		
		//ca-app-pub-2481530783933136/5610125809
		
		btn = (Button) findViewById(R.id.btn);
		txt = (TextView) findViewById(R.id.tv);
	//Real Ads	MobileAds.initialize(this,
							// "ca-app-pub-2481530783933136~5610125809");
			
	MobileAds.initialize(this,
							 "ca-app-pub-2481530783933136~5610125809");
		rvd = MobileAds.getRewardedVideoAdInstance(this);
		rvd.setRewardedVideoAdListener(this);
		loadad();
		btn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(rvd.isLoaded()){
						rvd.show();
					} else{
						txt.setText("Connecting...");
					}
				}
			});
	}

	private void loadad(){
		//  Real ads rvd.loadAd("ca-app-pub-2481530783933136/5610125809",
		//   new AdRequest.Builder().build());

		rvd.loadAd("ca-app-pub-2481530783933136/5610125809",
				   new AdRequest.Builder().build());
	
}
	
	//@Override
	public void onRewardedVideoAdLoaded() {
		txt.setText("Donation For Books.");
	}

	@Override
	public void onRewardedVideoAdOpened() {
	}

	@Override
	public void onRewardedVideoStarted() {
	}

	@Override
	public void onRewardedVideoAdClosed() {
	}

	@Override
	public void onRewarded(RewardItem rewardItem) {
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {
	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {
	}

	//@Override
	public void onRewardedVideoCompleted() {
	}

	@Override
	public void onResume() {
		rvd.resume();
		super.onResume();
	}

	@Override
	public void onPause() {
		rvd.pause();
		super.onPause();
	}
}*/

