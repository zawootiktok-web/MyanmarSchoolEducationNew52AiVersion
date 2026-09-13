package com.mmschooledu;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mmschooledu.Dictionary.OnlineDictionary;
import com.mmschooledu.EbookAudioShelf.EbookGalleryActivity;
import com.mmschooledu.MmToEnglishName.MmToEngActivity;
import com.mmschooledu.MyanmarSpelling.activity.MmSpellingMainActivity;
import com.mmschooledu.Story.activity.StoryMainActivity;
import com.mmschooledu.UnitConverter.UnitConverterActivity;
import com.startapp.sdk.adsbase.StartAppAd;

import java.util.ArrayList;
import java.util.List;
//import com.startapp.sdk.adsbase.StartAppAd;


public class Main1 extends AppCompatActivity {

	private NoInternetDialog noInternetDialog;

	private AdView adView;
	private String TAG = "Home";

	final int storageRequestCode=123;
	private static final int resultCode = 0;
	CardView kg,kg2,kg3,kg4,kg5,kg6,kg7,kg8;


	private BlogspotAPIManagerFbAds blogspotAPIManagerFbads;
	private InterstitialAd interstitialAd;

	private BlogspotAPIManagerStartAds blogspotAPIManagerStardads;

	private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String message = intent.getStringExtra("message");
			boolean show = intent.getBooleanExtra("show", true);
			if (show) {
				// Display the message to the user
				Toast.makeText(Main1.this, message, Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// NOTE always use test ads during development and testing
		//StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
		setContentView(R.layout.main1);

//		GeolocationUtils.checkCountryAndShowDialog(this);

		requestAppPermissions();
		//getWindow().getDecorView().setBackgroundColor(Constant.color);


		myPermissions();


		noInternetDialog = new NoInternetDialog(this);
		checkInternetConnection();


// Initialize BlogspotAPIManager
		blogspotAPIManagerStardads = new BlogspotAPIManagerStartAds(this);
		blogspotAPIManagerFbads = new BlogspotAPIManagerFbAds(this);


		// Register the BroadcastReceiver to receive message status updates
		IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			registerReceiver(messageStatusReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
		}


		// Check ads status
		blogspotAPIManagerStardads.checkAdsStatus(new BlogspotAPIManagerStartAds.OnAdsStatusListener() {
			@Override
			public void onAdsStatus(boolean isEnabled) {
				if (isEnabled) {
					// Show ads
					showAds();
				} else {
					// Hide ads
					hideAds();
				}
			}

			private void showAds() {
				// Load and show ads here
				//Toast.makeText(TextIndexActivity.this, "Start.io ads are Enabled", Toast.LENGTH_SHORT).show();
				StartAppAd.showAd(getBaseContext());
//                startAppAd.loadAd(AdMode.REWARDED_VIDEO);
//                Intent i = new Intent(TextIndexActivity.this, AdsActivity.class);
//                startActivity(i);
			}

			private void hideAds() {
				// Hide ads or take appropriate action
				//Toast.makeText(Main1.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
			}
		});

		// Check ads status
		blogspotAPIManagerFbads.checkAdsStatus(new BlogspotAPIManagerFbAds.OnAdsStatusListener() {
			@Override
			public void onAdsStatus(boolean isEnabled) {
				if (isEnabled) {
					// Show ads
					showAds();
				} else {
					// Hide ads
					hideAds();
				}
			}

			private void showAds() {
				// Load and show ads here
				//Toast.makeText(TextIndexActivity.this, "Start.io ads are Enabled", Toast.LENGTH_SHORT).show();
				//  StartAppAd.showAd(getBaseContext());
//                startAppAd.loadAd(AdMode.REWARDED_VIDEO);
//                Intent i = new Intent(TextIndexActivity.this, AdsActivity.class);
//                startActivity(i);

				loadInterstitialAd();
			}

			private void hideAds() {
				// Hide ads or take appropriate action
				Toast.makeText(Main1.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
			}
		});



		adView = (AdView)
				findViewById(R.id.ad_view);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		/*
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(Main1.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

        interstitial.loadAd(adRequest);
        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
				public void onAdLoaded() {
					// Call displayInterstitial() function
					displayInterstitial();
				}
			});
*/


		kg = (CardView) findViewById(R.id.kg);
		kg2 = (CardView) findViewById(R.id.kg2);
		kg3 = (CardView) findViewById(R.id.kg3);
		kg4 = (CardView) findViewById(R.id.kg4);
		kg5 = (CardView) findViewById(R.id.kg5);
		kg6 = (CardView) findViewById(R.id.kg6);
		kg7 = (CardView) findViewById(R.id.kg7);
		kg8 = (CardView) findViewById(R.id.kg8);
//requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);


		// SDK Initialize
		Admob.loadInterstitialAds(Main1.this);
//		Admob.loadRewordedAds(Main1.this);
		Admob.loadRewardedInterstitialAds(Main1.this);

//IMG_16_9_APP_INSTALL#2811380842414575_2811476129071713


		//Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		//setSupportActionBar(toolbar);






		kg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View p1) {
				new Admob(new onDismiss() {
					@Override
					public void onDismiss() {

//						String link = "http://128.199.240.200";
//						Intent intent = new Intent(Main1.this, TextBookAll.class);
//						intent.putExtra("link", link);
//						startActivity(intent);


						Intent i = new Intent(Main1.this, OnlineDictionary.class);
//						Intent i = new Intent(Main1.this, MmSpellingMainActivity.class);
						startActivity(i);
						StartAppAd.showAd(getBaseContext());

					}
				}).ShowInterstitial(Main1.this, true);
			};});

		kg2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View p1) {

				new Admob(new onDismiss() {
					@Override
					public void onDismiss() {

//						Intent i = new Intent(Main1.this, MainActivity.class);
//						startActivity(i);

						Intent i = new Intent(Main1.this, MmSpellingMainActivity.class);
						startActivity(i);
					}
				}).ShowInterstitial(Main1.this, true);
			}

			;
		});

		kg3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View p1) {

				new Admob(new onDismiss() {
					@Override
					public void onDismiss() {

						Intent i = new Intent(Main1.this, StoryMainActivity.class);
						startActivity(i);
					}
				}).ShowInterstitial(Main1.this, true);
			}

			;
		});


		kg4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View p1) {

				new Admob(new onDismiss() {
					@Override
					public void onDismiss() {

						Intent i = new Intent(Main1.this, AgeCalculatorActivity.class);
						startActivity(i);
					}
				}).ShowInterstitial(Main1.this, true);
			}

			;
		});






		kg5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View p1) {
				new Admob(new onDismiss() {
					@Override
					public void onDismiss() {

						Intent i = new Intent(Main1.this, UnitConverterActivity.class);
						startActivity(i);
					}
				}).ShowInterstitial(Main1.this, true);
			}

			;
		});
		kg6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View p1) {
				new Admob(new onDismiss() {
					@Override
					public void onDismiss() {
						Intent i = new Intent(Main1.this, EbookGalleryActivity.class);
						startActivity(i);

					}
				}).ShowRewardedInterstitial(Main1.this, true);
			}

			;
		});

		kg7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View p1) {
				new Admob(new onDismiss() {
					@Override
					public void onDismiss() {
						Intent i = new Intent(Main1.this, ChatActivity.class);
						startActivity(i);

					}
				}).ShowInterstitial(Main1.this, true);
			}

			;
		});

		kg8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View p1) {
				new Admob(new onDismiss() {
					@Override
					public void onDismiss() {
					/*Intent i=new Intent(Intent.ACTION_SEND);
					i.setType("text/plain");
					i.putExtra(Intent.EXTRA_TEXT,"This is a Myanmar School Education apk.Download hear... https://play.google.com/store/apps/details?id=com.mmschooledu ");
					startActivity(i);
					 */
						Intent i = new Intent(Main1.this, MmToEngActivity.class);
						startActivity(i);

					}
				}).ShowInterstitial(Main1.this, true);
			}

			;
		});

//		kg8.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View p1) {
//				new Admob(new onDismiss() {
//					@Override
//					public void onDismiss() {
//						//showAbout();
//						Intent i = new Intent(Main1.this, Main3.class);
//						startActivity(i);
//					}
//				}).ShowRewardedInterstitial(Main1.this, true);
//			}
//
//			;
//		});
	}

	private void loadInterstitialAd() {
		interstitialAd = new InterstitialAd(this, "1072549720831975_1325325095554435"); // Replace with your real ID
		interstitialAd.loadAd(
				interstitialAd.buildLoadAdConfig()
						.withAdListener(new InterstitialAdListener() {
							@Override
							public void onInterstitialDisplayed(Ad ad) {
								Log.d(TAG, "Interstitial ad displayed.");
							}

							@Override
							public void onInterstitialDismissed(Ad ad) {
								Log.d(TAG, "Interstitial ad dismissed.");
							}

							@Override
							public void onError(Ad ad, AdError adError) {
								Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
							}

							@Override
							public void onAdLoaded(Ad ad) {
								Log.d(TAG, "Interstitial ad loaded.");
								interstitialAd.show(); // Show ad when loaded
							}

							@Override
							public void onAdClicked(Ad ad) {
								Log.d(TAG, "Interstitial ad clicked.");
							}

							@Override
							public void onLoggingImpression(Ad ad) {
								Log.d(TAG, "Interstitial ad impression logged.");
							}
						})
						.build());
	}


	private final ActivityResultLauncher<String[]> requestPermissionLauncher =
			registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permission ->{
				boolean allGranted = true;

				for (Boolean isGranted : permission.values()){
					if (!isGranted){
						allGranted = false;
						break;
					}
				}

				if (allGranted){
					// All is granted
				} else {
					// All is not granted
				}

			});

	private void myPermissions(){

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

			String[] permissions = new String[]{
					android.Manifest.permission.READ_MEDIA_IMAGES,
					android.Manifest.permission.READ_MEDIA_AUDIO,
					android.Manifest.permission.READ_MEDIA_VIDEO,
					android.Manifest.permission.CAMERA,
			};


			List<String> permissionsTORequest = new ArrayList<>();
			for (String permission : permissions){
				if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
					permissionsTORequest.add(permission);
				}
			}

			if (permissionsTORequest.isEmpty()){
				// All permissions are already granted
				Toast.makeText(this, "All permissions are already granted", Toast.LENGTH_SHORT).show();


			} else {
				String[] permissionsArray = permissionsTORequest.toArray(new String[0]);
				boolean shouldShowRationale = false;

				for (String permission : permissionsArray){
					if (shouldShowRequestPermissionRationale(permission)){
						shouldShowRationale = true;
						break;
					}
				}

				if (shouldShowRationale){
					new AlertDialog.Builder(this)
							.setMessage("Please allow all permissions")
							.setCancelable(false)
							.setPositiveButton("YES", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {
									requestPermissionLauncher.launch(permissionsArray);
								}
							})

							.setNegativeButton("NO", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {
									dialogInterface.dismiss();
								}
							})
							.show();

				} else {
					requestPermissionLauncher.launch(permissionsArray);
				}


			}


		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			String[] permissions = new String[]{
					android.Manifest.permission.READ_EXTERNAL_STORAGE,
			};


			List<String> permissionsTORequest = new ArrayList<>();
			for (String permission : permissions){
				if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
					permissionsTORequest.add(permission);
				}
			}

			if (permissionsTORequest.isEmpty()){
				// All permissions are already granted
				Toast.makeText(this, "All permissions are already granted", Toast.LENGTH_SHORT).show();


			} else {
				String[] permissionsArray = permissionsTORequest.toArray(new String[0]);
				boolean shouldShowRationale = false;

				for (String permission : permissionsArray){
					if (shouldShowRequestPermissionRationale(permission)){
						shouldShowRationale = true;
						break;
					}
				}

				if (shouldShowRationale){
					new AlertDialog.Builder(this)
							.setMessage("Please allow all permissions")
							.setCancelable(false)
							.setPositiveButton("YES", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {
									requestPermissionLauncher.launch(permissionsArray);
								}
							})

							.setNegativeButton("NO", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {
									dialogInterface.dismiss();
								}
							})
							.show();

				} else {
					requestPermissionLauncher.launch(permissionsArray);
				}


			}


		}


	} // myPermissions end here ================


//} // public class end here =========================

	/*private void requestAppPermissions() {
		if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			return;
		}

		if (hasReadPermissions() && hasWritePermissions()) {
			return;
		}

		ActivityCompat.requestPermissions(this,
				new String[] {
						Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE
				}, 101); // your request code
	}

	private boolean hasReadPermissions() {
		return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
	}

	private boolean hasWritePermissions() {
		return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
	}


}*/






	private void requestAppPermissions() {


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
			ActivityCompat.requestPermissions(Main1.this,new String[]{
					Manifest.permission.READ_MEDIA_AUDIO,
					Manifest.permission.READ_MEDIA_IMAGES,
					Manifest.permission.READ_MEDIA_VIDEO
			},resultCode);
			//request13Permission();
			//Toast.makeText(this, "You Need To Accept Storage Permission.", Toast.LENGTH_SHORT).show();
			//Toast.makeText(this, "Storage Permission ​ပေးပြီး​သော​ကြောင့် ကြိုက်ရာကို Download ရယူနိုင်ပါပြီ", Toast.LENGTH_SHORT).show();
		}else{
			ActivityCompat.requestPermissions(Main1.this,new String[]{
					Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
			},resultCode);
			//requestPermission();
			//Toast.makeText(this, "You Need To Accept Storage Permission.", Toast.LENGTH_SHORT).show();

			//Toast.makeText(this, "Storage Permission ​ပေးပြီး​သော​ကြောင့် ကြိုက်ရာကို Download ရယူနိုင်ပါပြီ", Toast.LENGTH_SHORT).show();
		}


	}




	@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
	public void request13Permission(){

		if( ContextCompat.checkSelfPermission(Main1.this, Manifest.permission.READ_MEDIA_AUDIO)== PackageManager.PERMISSION_GRANTED
				&& ContextCompat.checkSelfPermission(Main1.this,Manifest.permission.READ_MEDIA_IMAGES)== PackageManager.PERMISSION_GRANTED &&
				ContextCompat.checkSelfPermission(Main1.this,Manifest.permission.READ_MEDIA_VIDEO)== PackageManager.PERMISSION_GRANTED
		){
			showAlert("Great!!","You did made permission request.");
		}else{
			ActivityCompat.requestPermissions(Main1.this,new String[]{Manifest.permission.READ_MEDIA_AUDIO, Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO},storageRequestCode);
		}

	}
	public void requestPermission(){
		if(ContextCompat.checkSelfPermission(Main1.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
				&& ContextCompat.checkSelfPermission(Main1.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
		){
			showAlert("Great!!","You did made permission request.");
		}else{
			ActivityCompat.requestPermissions(Main1.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},storageRequestCode);
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if(requestCode==storageRequestCode) {

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
				if (grantResults.length == 3 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED &&  grantResults[1] == PackageManager.PERMISSION_GRANTED &&  grantResults[2] == PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(this, "You can press Button now.", Toast.LENGTH_LONG).show();
				} else {
					//Toast.makeText(this, "You Need To Accept 13 Permission.", Toast.LENGTH_SHORT).show();
				//	Toast.makeText(this, "Storage Permission ​ပေးပြီး​သော​ကြောင့် ကြိုက်ရာကို Download ရယူနိုင်ပါပြီ", Toast.LENGTH_SHORT).show();
				}
			} else {
				if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
					Toast.makeText(this, "You can press Button now.", Toast.LENGTH_LONG).show();
				} else {
					//Toast.makeText(this, "You Need To Accept Storage Permission.", Toast.LENGTH_SHORT).show();
					//Toast.makeText(this, "Storage Permission ​ပေးပြီး​သော​ကြောင့် ကြိုက်ရာကို Download ရယူနိုင်ပါပြီ", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	public void showAlert(String title, String message){
		new android.app.AlertDialog.Builder(Main1.this)
				.setTitle("Alert for Permission")
				.setMessage("Go to Settings for Permissions")
				.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						///  code to go to settings of application
						Intent intent = new Intent();
						intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
						Uri uri = Uri.fromParts("package", getPackageName(), null);
						intent.setData(uri);
						startActivity(intent);
						dialog.dismiss();
					}
				})
				.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					//	Toast.makeText(Main1.this, "Setting မှ Storage Permission ဖွင့်\u200Bပေးမှ စာအုပ်များ Down ယူရရှိနိုင်ပါမည်...", Toast.LENGTH_SHORT).show();
						finish();
					}
				}).show();


	}
	private void checkInternetConnection() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
			boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
			if (!isConnected) {
				// Show the no internet dialog if there's no internet connection
				noInternetDialog.showNoInternet();
		}
	}
}}


	/*public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }

	}*/
	/*private void showContact(){
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		View parent_view = LayoutInflater.from(this).inflate(R.layout.two_button_dia, null);
		dialog.setView(parent_view);
		dialog.setCancelable(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.show();
		ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
		TextView tv_title = (TextView) dialog.findViewById(R.id.title);
		TextView tv_message = (TextView) dialog.findViewById(R.id.message);
		final Button call = (Button) dialog.findViewById(R.id.bt2);
		final Button cancel= (Button) dialog.findViewById(R.id.bt1);
		iv.setImageResource(R.drawable.phone_icon);
		tv_title.setText("App Creater");
		tv_message.setText(getString(R.string.contact_content));
		cancel.setText("No");
		cancel.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					dialog.dismiss();
				}});
		call.setText("No Called");
		call.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{

					Intent call = new Intent(Intent.ACTION_DIAL);
					call.setData(Uri.parse("tel:"+"000000"));
					startActivity(call); 
					dialog.dismiss();
				}});

	}
	public void showAbout(){
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		View parent_view = LayoutInflater.from(this).inflate(R.layout.two_button_dia, null);
		dialog.setView(parent_view);
		dialog.setCancelable(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.show();
		ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
		TextView tv_title = (TextView) dialog.findViewById(R.id.title);
		TextView tv_message = (TextView) dialog.findViewById(R.id.message);
		final Button call = (Button) dialog.findViewById(R.id.bt2);
		final Button cancel= (Button) dialog.findViewById(R.id.bt1);
		iv.setImageResource(R.drawable.about_icon);
		tv_title.setText("About App  ​");
		tv_message.setText(getString(R.string.about_content));
		cancel.setText("Yes");
		cancel.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{

					dialog.dismiss();
				}});
		call.setText("App Creater");
		call.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{				
					showContact();
					dialog.dismiss();
				}});

	}

}*/
		
