

package com.mmschooledu;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mmschooledu.Item.PostItem;
import com.mmschooledu.Viewer.ViewerActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public abstract class JSONFeedActivity extends AppCompatActivity {

	private InterstitialAd mInterstitialAd;
	private AdView adView;




	public abstract void processJson(String jsonString);

	public abstract boolean useGridLayout();

	public abstract String getFeedAddress();

	public abstract void _Options_Menu_Click(MenuItem item);

	private SwipeRefreshLayout mSwipeLayout;
	RecyclerView rv;
	FeedAdapter adapter;
	List<PostItem> posts, filteredposts;
	boolean online = true;
	Toolbar tb;
	String currentLink = "";
	private DownloadTask downloadTask;
	final int FONT_ZAWGYI = 1;
	final int FONT_UNI = 2;
	final int FONT_NONE = 0;
	int currentFont = FONT_NONE;

	private BlogspotAPIManagerFbAds2 blogspotAPIManagerFbads;
	private InterstitialAd interstitialAd;


	private BlogspotAPIManagerStartAdsNew blogspotAPIManagerStardadsnew;

	private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String message = intent.getStringExtra("message");
			boolean show = intent.getBooleanExtra("show", true);
			if (show) {
				// Display the message to the user
				Toast.makeText(JSONFeedActivity.this, message, Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// NOTE always use test ads during development and testing
//		StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

		setContentView(R.layout.jsonfeed_layoutqa);
//		GeolocationUtils.checkCountryAndShowDialog(this);

		//StartAppAd.disableSplash();
		Admob.loadInterstitialAds(JSONFeedActivity.this);
//		Admob.loadRewordedAds(JSONFeedActivity.this);
		Admob.loadRewardedInterstitialAds(JSONFeedActivity.this);

		tb = (Toolbar) findViewById(R.id.nnl_toolbar);
		setSupportActionBar(tb);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		blogspotAPIManagerFbads = new BlogspotAPIManagerFbAds2(this);


		// Register the BroadcastReceiver to receive message status updates
		IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			registerReceiver(messageStatusReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
		}


		// Check ads status
		blogspotAPIManagerFbads.checkAdsStatus(new BlogspotAPIManagerFbAds2.OnAdsStatusListener() {
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
				Toast.makeText(JSONFeedActivity.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
			}
		});

		adView = (AdView)
				findViewById(R.id.ad_view);
				AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
//
//		InterstitialAd.load(this, getResources().getString(R.string.admob_interstitial_id), adRequest,
//				new InterstitialAdLoadCallback() {
//					@Override
//					public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//						// The mInterstitialAd reference will be null until
//						// an ad is loaded.
//						mInterstitialAd = interstitialAd;
//						Log.i(TAG, "onAdLoaded");
//						displayInterstitial();
//					}
//
//					@Override
//					public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//						// Handle the error
//						Log.d(TAG, loadAdError.toString());
//						mInterstitialAd = null;
//					}
//				});


		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (isOnline())
					refresh();
				else {
					mSwipeLayout.setRefreshing(false);
					Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
				}
			}
		});

		mSwipeLayout.setColorSchemeResources(
				R.color.refresh_progress_1,
				R.color.refresh_progress_2,
				R.color.refresh_progress_3);

		posts = new ArrayList<PostItem>();
		filteredposts = new ArrayList<PostItem>();
		rv = (RecyclerView) findViewById(R.id.recyclerview);
		if (useGridLayout()) {
			rv.setLayoutManager(new GridLayoutManager(this, 1));
		} else {
			rv.setLayoutManager(new LinearLayoutManager(this));
		}
		SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
		currentFont = sharedPreferences.getInt("font_main", 0);
		refresh();
	}


//
//	public void displayInterstitial() {
//		// If Ads are loaded, show Interstitial else show nothing.
//		if (mInterstitialAd != null) {
//			mInterstitialAd.show(this);
//		} else {
//			Log.d("TAG", "The interstitial ad wasn't ready yet.");
//		}
//
//	}


	public void refresh() {
		//adapter.reset();
		currentLink = getFeedAddress();
		tb.collapseActionView();
			if (isOnline()) {
				online = true;
				if (downloadTask != null) {
					downloadTask.cancel(true);
				}
				downloadTask = new DownloadTask();
				downloadTask.execute(currentLink);
		} else {
			online = false;
			Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
			showNoInternet();

			try {
				processJson(getFromPrefs(currentLink));
				adapter = new FeedAdapter();
				rv.setAdapter(adapter);
			} catch (Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
	}


	public void saveToPrefs(String result) {
		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(currentLink, result);
		editor.commit();
	}

	public String getFromPrefs(String key) {

		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		String lastData = sharedPreferences.getString(key, "");
		return lastData;
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

	class DownloadTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... p1) {
			String result = JSONDownloader.download(p1[0]);
			if (isCancelled() || result == null || result.trim().isEmpty()) {
				return "";
			}
			try {
				JSONObject jo = new JSONObject(result);
				jo = jo.getJSONObject("feed").getJSONObject("openSearch$totalResults");
				int totalPosts = Integer.parseInt(jo.getString("$t"));
				if (totalPosts > 0 && !isCancelled()) {
					String fullResult = JSONDownloader.download(p1[0] + "&max-results=" + totalPosts);
					if (fullResult != null && !fullResult.trim().isEmpty()) {
						result = fullResult;
					}
				}
			} catch (Exception e) {
				// Keep the valid first response when the total-count expansion fails.
			}
			return result;
		}


		@Override
		protected void onPreExecute() {
			mSwipeLayout.setRefreshing(true);
		}


		@Override
		protected void onPostExecute(String result) {
			if (isCancelled()) {
				return;
			}
			mSwipeLayout.setRefreshing(false);
			if (result == null || result.trim().isEmpty()) {
				String cached = getFromPrefs(currentLink);
				if (cached != null && !cached.trim().isEmpty()) {
					processJson(cached);
					online = false;
				} else {
					Toast.makeText(JSONFeedActivity.this, "Unable to load Blog data", Toast.LENGTH_SHORT).show();
				}
			} else {
				processJson(result);
				saveToPrefs(result);
			}
			adapter = new FeedAdapter();
			rv.setAdapter(adapter);
			downloadTask = null;
		}

		@Override
		protected void onCancelled() {
			mSwipeLayout.setRefreshing(false);
			downloadTask = null;
		}
	}

	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		MenuItem myActionMenuItem = menu.findItem(R.id.menu_search);
		SearchView sv = (SearchView) myActionMenuItem.getActionView();
		sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String p1) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String p1) {
				adapter.filter(p1.toString());
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			//mDrawerLayout.openDrawer(GravityCompat.START);
			//return true;
			finish();
		} else if (item.getItemId() == R.id.menu_search) {
			refresh();
		} else {
			_Options_Menu_Click(item);
		}


		return super.onOptionsItemSelected(item);
	}

	public void addItem(PostItem item) {
		posts.add(item);
	}

	//public abstract void _FAB_Click(View v)

	public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
		@Override
		public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2) {
			View v = getLayoutInflater().inflate(R.layout.item_layout1, p1, false);
			return new ViewHolder(v);
		}

		public void filter(String charText) {
			charText = charText.toLowerCase();
			filteredposts.clear();
			if (charText.length() == 0) {
				filteredposts.addAll(posts);
			} else {
				for (PostItem pi : posts) {
					if (pi.title.toLowerCase().contains(charText)) {
						filteredposts.add(pi);
					}
				}
			}
			notifyDataSetChanged();
		}

		@Override
		public int getItemCount() {
			return filteredposts.size();
		}

		@Override
		public void onBindViewHolder(FeedAdapter.ViewHolder p1, int p2) {
			if ((filteredposts.get(p2).thumbnailUrl.length() > 0) && (online)) {
				Glide
						.with(getApplicationContext())
						.load(Html.fromHtml(filteredposts.get(p2).thumbnailUrl).toString())
						.into(p1.iv);
			} else {
				p1.iv.setImageResource(R.drawable.edu);
			}
			p1.tv.setText(filteredposts.get(p2).title);
			//p1.tv.setText(Html.fromHtml(posts.get(p2).content.substring(0,100)+" ..."));
			p1.tv2.setText(Html.fromHtml(posts.get(p2).content.substring(0, Math.min(posts.get(p2).content.length(), 100)) + " ...").toString());
			//published
			p1.tv3.setText(filteredposts.get(p2).published);

		}

		public PostItem getItem(int pos) {
			return filteredposts.get(pos);
		}

		public void reset() {
			posts.clear();
			filteredposts.clear();
			notifyDataSetChanged();
		}

		public FeedAdapter() {
			super();
			filteredposts = new ArrayList<PostItem>();
			filteredposts.addAll(posts);
		}

		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			ImageView iv;
			TextView tv;
			TextView tv2;
			TextView tv3;

			@Override
			public void onClick(View view) {
				new com.bdtopcoder.quickadmob.Admob(new onDismiss() {
					@Override
					public void onDismiss() {

				Intent i = new Intent(JSONFeedActivity.this, ViewerActivity.class);
				PostItem pi = filteredposts.get(getAdapterPosition());
				i.putExtra("title", pi.title);
				i.putExtra("content", pi.content);
				i.putExtra("published", pi.published);
				startActivity(i);

	//			StartAppAd.showAd(getBaseContext());
				//StartAppAd.showAd(JSONFeedActivity.this);
			}
				}).ShowInterstitial(JSONFeedActivity.this, true);
			}


		public ViewHolder(View view) {
				super(view);
				iv = view.findViewById(R.id.ivItemImage);
				tv = view.findViewById(R.id.tvItemText);
				tv2 = (TextView) view.findViewById(R.id.tvItemText2);
				tv3 = (TextView) view.findViewById(R.id.tvItemText3);
				view.setOnClickListener(this);
			}
		}
	}

	public void MsgBox(String title, String msg) {

		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		alertDialog.setTitle(title);

		alertDialog.setMessage(msg);

		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog.show();

	}
	private void showNoInternet () {
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		View parent_view = LayoutInflater.from(this).inflate(R.layout.nointernet_dia, null);
		dialog.setView(parent_view);
		dialog.setCancelable(false);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.show();

		// Finding Views inside dialog
		TextView tv_title = dialog.findViewById(R.id.dialogTextView1);
		Button button = dialog.findViewById(R.id.dialogButton1);


		tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ခြင်းမရှိပါ။ ​​​ကျေးဇူးပြု၍ အင်တာနက် ဆက်သွယ်​​ပေးပါ။");

		button.setText("..ဟုတ်ကကဲ့..");

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View p1) {

				//dialog.dismiss();
				Intent i = new Intent(JSONFeedActivity.this, SplashActivity.class);
				startActivity(i);
			}
		});
	}
}
