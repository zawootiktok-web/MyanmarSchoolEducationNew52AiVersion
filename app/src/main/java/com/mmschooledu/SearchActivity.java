package com.mmschooledu;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;
import com.mmschooledu.Item.PostItem;
import com.mmschooledu.Viewer.ViewerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class SearchActivity extends Activity
{
//	private AudienceNetwork audienceNetwork;
//	private com.facebook.ads.InterstitialAd interstitialAd;


	private String TAG = "Home";

	final int storageRequestCode=123;
	private static final int resultCode = 0;

	private ConsentInformation consentInformation;

	private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);


	Toolbar tb;
	ListView lv;
	EditText et;
	MyAdapter adapter;
	List<PostItem> posts,filteredPosts;
	boolean online=false;
	final int FONT_ZAWGYI=1;
	final int FONT_UNI=2;
	final int FONT_NONE=0;
	int currentFont=FONT_NONE;
	String jsonStr="";
	int totalPosts=0;
	String currentBlog="https://myanmarschoolsearchpage.blogspot.com/feeds/posts/default?alt=json&start-index=1&max-results=100";



	//androidx.appcompat.widget.Toolbar tb;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		 AdView adView;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_main);

		requestConsform();

		//initToolbar();
		tb=(androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
		//setSupportActionBar(tb);
		//getSupportActionBar().setHomeButtonEnabled(true);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		tb=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(tb);
		//tb.setLogo(R.drawable.ic_launcher);
		// tb.setTitle("ATy");



		et=(EditText)findViewById(R.id.et);

		lv=(ListView)findViewById(R.id.lv);

		Admob.loadInterstitialAds(SearchActivity.this);
//		Admob.loadRewordedAds(SearchActivity.this);

		//loadInterstitialAd();


//		adView = (AdView)
//				findViewById(R.id.adView);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		adView.loadAd(adRequest);





		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
			{
				new Admob(new onDismiss() {
					@Override
					public void onDismiss() {
						// When Ads Close Take Action

				Intent i=new Intent(SearchActivity.this, ViewerActivity.class);
				PostItem pi=filteredPosts.get(p3);
				i.putExtra("title",pi.title);
				i.putExtra("content",pi.content);
				i.putExtra("published", pi.published);
				startActivity(i);
					}
				}).ShowInterstitial(SearchActivity.this, true);
			};
		});

		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		currentFont= sharedPreferences.getInt("font_main",0);
		refresh();
	}

	private void setSupportActionBar(Toolbar tb) {
	}

//
//	private void loadInterstitialAd() {
//		interstitialAd = new com.facebook.ads.InterstitialAd(this, "1072549720831975_1325325095554435"); // Replace with your real ID
//		interstitialAd.loadAd(
//				interstitialAd.buildLoadAdConfig()
//						.withAdListener(new InterstitialAdListener() {
//							@Override
//							public void onInterstitialDisplayed(Ad ad) {
//								Log.d(TAG, "Interstitial ad displayed.");
//							}
//
//							@Override
//							public void onInterstitialDismissed(Ad ad) {
//								Log.d(TAG, "Interstitial ad dismissed.");
//							}
//
//							@Override
//							public void onError(Ad ad, AdError adError) {
//								Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
//							}
//
//							@Override
//							public void onAdLoaded(Ad ad) {
//								Log.d(TAG, "Interstitial ad loaded.");
//								interstitialAd.show(); // Show ad when loaded
//							}
//
//							@Override
//							public void onAdClicked(Ad ad) {
//								Log.d(TAG, "Interstitial ad clicked.");
//							}
//
//							@Override
//							public void onLoggingImpression(Ad ad) {
//								Log.d(TAG, "Interstitial ad impression logged.");
//							}
//						})
//						.build());
//	}


	private void refresh(){
		if(isOnline()){
			online=true;
			new DownloadTask().execute(currentBlog);
		}else{
			online=false;
			SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
			jsonStr= sharedPreferences.getString(currentBlog,"");
			try{
				processJSON();
			}catch(Exception e){
				Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
			}
		}
	}

	protected boolean isOnline() {

		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}

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
//								initializeMobileAdsSdk();
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
			//initializeMobileAdsSdk();
			loadbannerads(true);
		}
	}


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

	//	adView.loadAd(adRequest);

	}






	/*

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu)
	 {
	 menu.add("Refresh")
	 .setShowAsAction((MenuItem.SHOW_AS_ACTION_ALWAYS));
	 menu.add("Change_Font")
	 .setShowAsAction((MenuItem.SHOW_AS_ACTION_ALWAYS));
	 menu.add("Computers");
	 menu.add("Books");
	 menu.add("Flowers");

	 return super.onCreateOptionsMenu(menu);
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item)
	 {
	 if(item.getTitle().equals("Refresh")){
	 refresh();
	 }else if(item.getTitle().equals("Change_Font")){
	 changeFont();
	 }else if(item.getTitle().equals("Computers")){
	 currentBlog="http://acomputerblog.blogspot.com/feeds/posts/default?alt=json";
	 refresh();
	 }else if(item.getTitle().equals("Books")){
	 currentBlog="http://free-ebook-download-links.blogspot.com/feeds/posts/default?alt=json";
	 refresh();
	 }else if(item.getTitle().equals("Flowers")){
	 currentBlog="http://apassionforflowers.blogspot.com/feeds/posts/default?alt=json";
	 refresh();
	 }
	 return super.onOptionsItemSelected(item);
	 }*/

	private void changeFont(){
		currentFont++;
		if(currentFont==3){
			currentFont=0;
		}
		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt("font_main",currentFont);
		editor.commit();
		processJSON();
	}


	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		finishAffinity();
		super.onBackPressed();
	}
	public void _Back_Click(View v){
		finish();
	}





	private class DownloadTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... p1)
		{
			String result=JSONDownloader.download(p1[0]);
			try{
				JSONObject jo=new JSONObject(result);
				jo=jo.getJSONObject("feed").getJSONObject("openSearch$totalResults");
				totalPosts=Integer.parseInt(jo.getString("$t"));
				result=JSONDownloader.download(p1[0]+"&max-results="+totalPosts);

			}catch(Exception e){

			}
			return result;
		}

		@Override
		public void onPostExecute(String result) {
			jsonStr=result;
			SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(currentBlog,result);
			editor.commit();
			processJSON();

			et.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					SearchActivity.this.adapter.filter(p1.toString());
				}

				@Override
				public void afterTextChanged(Editable p1)
				{
					// TODO: Implement this method
				}

			});
		}
	}

	private void processJSON(){
		String input="";
		posts=new ArrayList<PostItem>();
		switch(currentFont){
			case FONT_ZAWGYI:
				input=FontConverter.uni2zg(jsonStr);
				break;
			case FONT_UNI:
				input=FontConverter.zg2uni(jsonStr);
				break;
			default:
				input=jsonStr;
				break;
		}
		try
		{
			JSONObject jo=new JSONObject(input);
			JSONArray ja=jo.getJSONObject("feed").getJSONArray("entry");
			JSONObject jo2;
			for(int i=0;i<ja.length();i++){
				PostItem pi=new PostItem();
				jo2=ja.getJSONObject(i);
				pi.title= jo2.getJSONObject("title").getString("$t");
				pi.content=jo2.getJSONObject("content").getString("$t");
				posts.add(pi);

				try{
					String thumbnail=jo2.getJSONObject("media$thumbnail").getString("url");
					pi.thumbnailUrl=thumbnail;
				}catch(Exception e){
				}
			}

		}
		catch (JSONException e)
		{
		}
		adapter=new MyAdapter();
		lv.setAdapter(adapter);
	}


	class MyAdapter extends BaseAdapter
	{
		//List<PostItem> filteredPosts;

		public MyAdapter(){
			et.setText("");
			filteredPosts=new ArrayList<PostItem>();
			filteredPosts.addAll(posts);
		}

		@Override
		public int getCount()
		{
			return filteredPosts.size();
		}

		@Override
		public Object getItem(int p1)
		{
			return filteredPosts.get(p1);
		}

		@Override
		public long getItemId(int p1)
		{
			return 0;
		}

		@Override
		public View getView(int p1, View p2, ViewGroup p3)
		{
			if(p2 == null) {
				p2 = getLayoutInflater().inflate(R.layout.searhitem_layout, null);
			}
			TextView tv1 = (TextView)p2.findViewById(R.id.tv1);
			TextView tv2 = (TextView) p2.findViewById(R.id.tv2);
			ImageView iv = (ImageView) p2.findViewById(R.id.iv);

			if((filteredPosts.get(p1).thumbnailUrl.length()>0)&&(online)){
				//Picasso
				Glide
						.with(getApplicationContext())
						.load(Html.fromHtml(filteredPosts.get(p1).thumbnailUrl).toString())
						.into(iv);
			}else{
				iv.setImageResource(R.drawable.apk);
			}
			tv1.setText(filteredPosts.get(p1).title);

			tv2.setText(Html.fromHtml(filteredPosts.get(p1).content.substring(0,Math.min(filteredPosts.get(p1).content.length(),100))+" ...").toString());

			return p2;
		}

		public void filter(String charText) {

			charText = charText.toLowerCase();

			filteredPosts.clear();

			if (charText.length() == 0) {

				filteredPosts.addAll(posts);

			} else {
				for (PostItem pi : posts)
				{
					if (pi.title.toLowerCase().contains(charText))
					{
						filteredPosts.add(pi);
					}
					else
					if(charText.equals("၆၆၆၆၆")){

						//  Toast.makeText(getApplication(), "lets go to SecondActivity.", Toast.LENGTH_SHORT).show();

						SharedPreferences sharedPreferences = SearchActivity.this.getSharedPreferences("Check", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor1 = sharedPreferences.edit();
						editor1.clear();
						editor1.putString("check", "၆၆၆၆၆");
						editor1.apply();


						Intent i=new Intent(SearchActivity.this,MainFragmentActivity.class);
						startActivity(i);




					}

					else
					if(charText.equals("66666")){

						//  Toast.makeText(getApplication(), "lets go to SecondActivity.", Toast.LENGTH_SHORT).show();

						SharedPreferences sharedPreferences = SearchActivity.this.getSharedPreferences("Check", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor1 = sharedPreferences.edit();
						editor1.clear();
						editor1.putString("check", "66666");
						editor1.apply();


						Intent i=new Intent(SearchActivity.this,MainFragmentActivity.class);
						startActivity(i);




					}


				}
				notifyDataSetChanged();
			}
		}}}








