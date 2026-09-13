package com.mmschooledu.Mp3;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import androidx.core.view.ViewCompat;
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
import com.mmschooledu.BlogspotAPIManagerFbAds;
import com.mmschooledu.BlogspotAPIManagerStartAds;
import com.mmschooledu.BookShopActivity;
import com.mmschooledu.Dictionary.NewProverbsActivity;
import com.mmschooledu.Item.CategoryItem;
import com.mmschooledu.JSONDownloader;
import com.mmschooledu.Mp4.VideoLessonActivity;
import com.mmschooledu.R;
import com.startapp.sdk.adsbase.StartAppAd;

import java.util.ArrayList;
import java.util.List;

public abstract class Mp3Mp4IndexActivity extends AppCompatActivity

{
    private AdView adView;

    public abstract void processJson(String jsonString);
    public abstract boolean useGridLayout();
    public abstract String getFeedAddress();
    private SwipeRefreshLayout mSwipeLayout;
    RecyclerView rv;
   FeedAdapter adapter;
    List<CategoryItem> posts,filteredposts;
    boolean online=true;
    String currentLink="";
    final int FONT_ZAWGYI=1;
    final int FONT_UNI=2;
    final int FONT_NONE=0;
    int currentFont=FONT_NONE;
    String FACEBOOK_URL = "https://www.facebook.com/Kipon-Cinema-ကလေးရုပ်ရှင်ရုံ-101321321725798/";
    String FACEBOOK_PROFILE_ID = "101321321725798";

    ImageView p_img;
    TextView p_title,p_subtitle;

    String title,category;
    Button button1;
    private ImageView imageview;


    private BlogspotAPIManagerFbAds blogspotAPIManagerFbads;
    private InterstitialAd interstitialAd;
    private BlogspotAPIManagerStartAds blogspot;
    private BlogspotAPIManagerStartAds blogspotAPIManagerStardads;

    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            boolean show = intent.getBooleanExtra("show", true);
            if (show) {
                // Display the message to the user
                Toast.makeText(Mp3Mp4IndexActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_mainmp3);
        // GeolocationUtils.checkCountryAndShowDialog(this);


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
                Toast.makeText(Mp3Mp4IndexActivity.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Mp3Mp4IndexActivity.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
            }
        });

        imageview = (ImageView) findViewById(R.id.p_img2);

        //button1=(Button) findViewById(R.id.button1);


        adView = (AdView)
                findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);



        Admob.loadInterstitialAds(Mp3Mp4IndexActivity.this);
//        Admob.loadRewordedAds(Mp3Mp4IndexActivity.this);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        Intent i=new Intent(Mp3Mp4IndexActivity.this, BookShopActivity.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Mp3Mp4IndexActivity.this, true);
            };});



        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isOnline())
                    refresh();
                else{
                    mSwipeLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(),"No internet connection!",Toast.LENGTH_SHORT).show();
                    showNoInternet();
                }
            } });

        mSwipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        posts=new ArrayList<CategoryItem>();
        filteredposts=new ArrayList<CategoryItem>();
        rv=(RecyclerView)findViewById(R.id.recyclerview);
        ViewCompat.setNestedScrollingEnabled(rv, false);
        p_img=(ImageView)findViewById(R.id.p_img);
        p_title=(TextView)findViewById(R.id.p_title);
        p_subtitle=(TextView)findViewById(R.id.p_subtitle);

        if(useGridLayout()){
            rv.setLayoutManager(new GridLayoutManager(this,2));
        }else{
            rv.setLayoutManager(new LinearLayoutManager(this));
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        currentFont = sharedPreferences.getInt("font_main", 0);

		/*	title=getIntent().getExtras()
		 .get("title").toString();
		 category=getIntent().getExtras()
		 .get("category").toString();
		 getSupportActionBar().setTitle(title+"");*/
        refresh();
    }



	/*try{



	 new CheckUpdateAsyncTask(this,"https://topaone-computer-basic.blogspot.com/feeds/posts/default?alt=json");
	 }catch(Exception e){
	 Toast.makeText(getApplicationContext(),"Can't be used update!",Toast.LENGTH_SHORT).show();
	 }
	 }*/

	/*public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }

	}*/

    public void refresh(){

        currentLink=getFeedAddress();

        if(isOnline()){
            online=true;
            try{
                new Mp3Mp4IndexActivity.DownloadTask().execute(currentLink);

                //	showAbout();
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Can't be used Data!",Toast.LENGTH_SHORT).show();
            }
        }else{
            online=false;

            showNoInternet();
            try{
                processJson(getFromPrefs(currentLink));
               // adapter= new FeedAdapter();
                adapter = new Mp3Mp4IndexActivity.FeedAdapter();
                rv.setAdapter(adapter);
            }catch(Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveToPrefs(String result){
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(currentLink,result);
        editor.commit();
    }

    public String getFromPrefs(String key){

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String lastData= sharedPreferences.getString(currentLink,"");
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


    private class DownloadTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... p1)
        {
            String result= JSONDownloader.download(p1[0]);
            return result;
        }

        @Override
        public void onPostExecute(String result) {
            processJson(result);
            saveToPrefs(result);
            mSwipeLayout.setRefreshing(false);
            adapter=new FeedAdapter();
            rv.setAdapter(adapter);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.menu_search);
        SearchView sv = (SearchView) myActionMenuItem.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String p1)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String p1)
            {
                adapter.filter(p1.toString());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id==R.id.menu_font){
            changeFont();
            //refresh();
        }

        return super.onOptionsItemSelected(item);
    }

    public void addItem(CategoryItem item){
        posts.add(item);
    }

    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>
    {
        @Override
        public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
        {
            View v=getLayoutInflater().inflate(R.layout.mp3mp4_item,p1,false);
            return new FeedAdapter.ViewHolder(v);
        }

        public void filter(String charText){
            charText =charText.toLowerCase();
            filteredposts.clear();
            if (charText.length()==0){
                filteredposts.addAll(posts);
            }else{
                for (CategoryItem pi : posts){
                    if(pi.bname.toLowerCase().contains(charText))
                    {
                        filteredposts.add(pi);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount()
        {
            return filteredposts.size();
        }

        @Override
        public void onBindViewHolder(FeedAdapter.ViewHolder p1, int p2)
        {
            if((filteredposts.get(p2).thumbnail.length()>0)&&(online)){
                Glide
                        .with(getApplicationContext())
                        .load(Html.fromHtml(filteredposts.get(p2).thumbnail).toString())
                        .into(p1.iv);
            }else{
                p1.iv.setImageResource(R.drawable.loading_book);
            }
            p1.tv.setText(filteredposts.get(p2).bname);

        }

        public CategoryItem getItem(int pos){
            return filteredposts.get(pos);
        }

        public void reset()
        {
            posts.clear();
            filteredposts.clear();
            notifyDataSetChanged();
        }

        public FeedAdapter()
        {
            super();
            filteredposts=new ArrayList<CategoryItem>();
            filteredposts.addAll(posts);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            ImageView iv;
            TextView tv;

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                try {

                } catch (Exception e) {

					/*
					 public void goBookActivity(String title,String category){
					 Intent i=new Intent(IndexActivity.this,EbookBookActivity.class);
					 i.putExtra("title",title);
					 i.putExtra("res",res);
					 startActivity(i);
					 }*/
                }


                if (position == 0) {
                    //	Intent i=new Intent(IndexActivity.this,VideoActivity.class);
                    new Admob(new onDismiss() {
                        @Override
                        public void onDismiss() {
                    Intent i = new Intent(Mp3Mp4IndexActivity.this, AudioLessonActivity.class);

                    CategoryItem pi = filteredposts.get(getAdapterPosition());

                    i.putExtra("title", pi.title);
                    i.putExtra("thumbnail", pi.thumbnail);
                    i.putExtra("bname", pi.bname);
                    i.putExtra("wname", pi.wname);
                    i.putExtra("link", pi.link);
                    i.putExtra("category", pi.category);
//                    i.putExtra("mb1", pi.mb1);
//                    i.putExtra("mb2", pi.mb2);
//                    i.putExtra("time", pi.time);

                    startActivity(i);
                    //StartAppAd.showAd(getBaseContext());
                      }
                    }).ShowInterstitial(Mp3Mp4IndexActivity.this, true);

                } else if (position == 1) {

                    new Admob(new onDismiss() {
                        @Override
                        public void onDismiss() {
                    Intent i = new Intent(Mp3Mp4IndexActivity.this, AudioLessonActivity.class);

                    CategoryItem pi = filteredposts.get(getAdapterPosition());

                    i.putExtra("title", pi.title);
                    i.putExtra("thumbnail", pi.thumbnail);
                    i.putExtra("bname", pi.bname);
                    i.putExtra("wname", pi.wname);
                    i.putExtra("link", pi.link);
                    i.putExtra("category", pi.category);
                    i.putExtra("mb1", pi.mb1);
                    i.putExtra("mb2", pi.mb2);
                    i.putExtra("time", pi.time);

                    startActivity(i);
                    //  StartAppAd.showAd(getBaseContext());

                }
            }).ShowRewardedInterstitial(Mp3Mp4IndexActivity.this, true);

                } else if (position == 2) {

                    new Admob(new onDismiss() {
                        @Override
                        public void onDismiss() {
                    Intent i = new Intent(Mp3Mp4IndexActivity.this, VideoLessonActivity.class);

                    CategoryItem pi = filteredposts.get(getAdapterPosition());

                    i.putExtra("title", pi.title);
                    i.putExtra("thumbnail", pi.thumbnail);
                    i.putExtra("bname", pi.bname);
                    i.putExtra("wname", pi.wname);
                    i.putExtra("link", pi.link);
                    i.putExtra("category", pi.category);
                    i.putExtra("mb1", pi.mb1);
                    i.putExtra("mb2", pi.mb2);
                    i.putExtra("time", pi.time);

                    startActivity(i);
                    // StartAppAd.showAd(getBaseContext());
                        }
                    }).ShowInterstitial(Mp3Mp4IndexActivity.this, true);

                } else if (position == 3) {

                    new Admob(new onDismiss() {
                        @Override
                        public void onDismiss() {
                            Intent i = new Intent(Mp3Mp4IndexActivity.this, VideoLessonActivity.class);

                            CategoryItem pi = filteredposts.get(getAdapterPosition());

                    i.putExtra("title", pi.title);
                    i.putExtra("thumbnail", pi.thumbnail);
                    i.putExtra("bname", pi.bname);
                    i.putExtra("wname", pi.wname);
                    i.putExtra("link", pi.link);
                    i.putExtra("category", pi.category);
                    i.putExtra("mb1", pi.mb1);
                    i.putExtra("mb2", pi.mb2);
                    i.putExtra("time", pi.time);

                    startActivity(i);
                      //StartAppAd.showAd(getBaseContext());
                        }
                    }).ShowInterstitial(Mp3Mp4IndexActivity.this, true);


                } else if (position == 4) {

                 new Admob(new onDismiss() {
                        @Override
                        public void onDismiss() {
                    Intent i = new Intent(Mp3Mp4IndexActivity.this, AudioLessonActivity.class);

                    CategoryItem pi = filteredposts.get(getAdapterPosition());

                    i.putExtra("title", pi.title);
                    i.putExtra("thumbnail", pi.thumbnail);
                    i.putExtra("bname", pi.bname);
                    i.putExtra("wname", pi.wname);
                    i.putExtra("link", pi.link);
                    i.putExtra("category", pi.category);
//                    i.putExtra("mb1", pi.mb1);
//                    i.putExtra("mb2", pi.mb2);
//                    i.putExtra("time", pi.time);

                    startActivity(i);
                    //StartAppAd.showAd(getBaseContext());
                      }
                    }).ShowInterstitial(Mp3Mp4IndexActivity.this, true);

                } else if (position == 5) {

                    new Admob(new onDismiss() {
                        @Override
                        public void onDismiss() {
                    Intent i = new Intent(Mp3Mp4IndexActivity.this, NewProverbsActivity.class);

                    CategoryItem pi = filteredposts.get(getAdapterPosition());

                    i.putExtra("title", pi.title);
                    i.putExtra("thumbnail", pi.thumbnail);
                    i.putExtra("bname", pi.bname);
                    i.putExtra("wname", pi.wname);
                    i.putExtra("link", pi.link);
                    i.putExtra("category", pi.category);
                    i.putExtra("mb1", pi.mb1);
                    i.putExtra("mb2", pi.mb2);
                    i.putExtra("time", pi.time);

                    startActivity(i);
                    // StartAppAd.showAd(getBaseContext());
                        }
                    }).ShowInterstitial(Mp3Mp4IndexActivity.this, true);

                } else if (position == 6) {

                    new Admob(new onDismiss() {
                        @Override
                        public void onDismiss() {
                            Intent i = new Intent(Mp3Mp4IndexActivity.this, VideoLessonActivity.class);

                            CategoryItem pi = filteredposts.get(getAdapterPosition());

                            i.putExtra("title", pi.title);
                            i.putExtra("thumbnail", pi.thumbnail);
                            i.putExtra("bname", pi.bname);
                            i.putExtra("wname", pi.wname);
                            i.putExtra("link", pi.link);
                            i.putExtra("category", pi.category);
                            i.putExtra("mb1", pi.mb1);
                            i.putExtra("mb2", pi.mb2);
                            i.putExtra("time", pi.time);

                            startActivity(i);
                            // StartAppAd.showAd(getBaseContext());
                        }
                    }).ShowInterstitial(Mp3Mp4IndexActivity.this, true);

                } else if (position == 7) {

//                    new Admob(new onDismiss() {
//                        @Override
//                        public void onDismiss() {
                    Intent i = new Intent(Mp3Mp4IndexActivity.this, NewProverbsActivity.class);

                    CategoryItem pi = filteredposts.get(getAdapterPosition());

                    i.putExtra("title", pi.title);
                    i.putExtra("thumbnail", pi.thumbnail);
                    i.putExtra("bname", pi.bname);
                    i.putExtra("wname", pi.wname);
                    i.putExtra("link", pi.link);
                    i.putExtra("category", pi.category);
                    i.putExtra("mb1", pi.mb1);
                    i.putExtra("mb2", pi.mb2);
                    i.putExtra("time", pi.time);

                    startActivity(i);
                    //  StartAppAd.showAd(getBaseContext());
//                        }
//                    }).ShowInterstitial(DictionaryIndexActivity.this, true);

                } else if (position == 8) {

//                    new Admob(new onDismiss() {
//                        @Override
//                        public void onDismiss() {
                    Intent i = new Intent(Mp3Mp4IndexActivity.this, NewProverbsActivity.class);

                    CategoryItem pi = filteredposts.get(getAdapterPosition());

                    i.putExtra("title", pi.title);
                    i.putExtra("thumbnail", pi.thumbnail);
                    i.putExtra("bname", pi.bname);
                    i.putExtra("wname", pi.wname);
                    i.putExtra("link", pi.link);
                    i.putExtra("category", pi.category);
                    i.putExtra("mb1", pi.mb1);
                    i.putExtra("mb2", pi.mb2);
                    i.putExtra("time", pi.time);

                    startActivity(i);
                    //  StartAppAd.showAd(getBaseContext());
//                        }
//                    }).ShowInterstitial(DictionaryIndexActivity.this, true);

                } else if (position == 9) {

//                    new Admob(new onDismiss() {
//                        @Override
//                        public void onDismiss() {
                    Intent i = new Intent(Mp3Mp4IndexActivity.this, NewProverbsActivity.class);

                    CategoryItem pi = filteredposts.get(getAdapterPosition());

                    i.putExtra("title", pi.title);
                    i.putExtra("thumbnail", pi.thumbnail);
                    i.putExtra("bname", pi.bname);
                    i.putExtra("wname", pi.wname);
                    i.putExtra("link", pi.link);
                    i.putExtra("category", pi.category);
                    i.putExtra("mb1", pi.mb1);
                    i.putExtra("mb2", pi.mb2);
                    i.putExtra("time", pi.time);

                    startActivity(i);
                    //   StartAppAd.showAd(getBaseContext());
//                        }
//                    }).ShowInterstitial(DictionaryIndexActivity.this, true);


                } else if (position == 10) {

//                    new Admob(new onDismiss() {
//                        @Override
//                        public void onDismiss() {
                    Intent i = new Intent(Mp3Mp4IndexActivity.this, NewProverbsActivity.class);

                    CategoryItem pi = filteredposts.get(getAdapterPosition());

                    i.putExtra("title", pi.title);
                    i.putExtra("thumbnail", pi.thumbnail);
                    i.putExtra("bname", pi.bname);
                    i.putExtra("wname", pi.wname);
                    i.putExtra("link", pi.link);
                    i.putExtra("category", pi.category);
                    i.putExtra("mb1", pi.mb1);
                    i.putExtra("mb2", pi.mb2);
                    i.putExtra("time", pi.time);

                    startActivity(i);
                    // StartAppAd.showAd(getBaseContext());
//                        }
//                    }).ShowInterstitial(DictionaryIndexActivity.this, true);


                }

            }

            public ViewHolder(View view)
            {
                super(view);
                iv=view.findViewById(R.id.ivItemImage);
                tv=view.findViewById(R.id.tvItemText);
                view.setOnClickListener(this);
            }
        }
    }

    public void MsgBox(String title, String msg){

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

    private void changeFont()
    {
        currentFont++;
        if (currentFont == 3)
        {
            currentFont = 0;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("font_main", currentFont);
        editor.commit();
        try{
            processJson(getFromPrefs(currentLink));
            adapter=new FeedAdapter();
            rv.setAdapter(adapter);
        }catch(Exception e){
           // Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();

            Toast.makeText(Mp3Mp4IndexActivity.this,
                    "\"သတိ လိုင်း\u200Bနှေး\u200Bနေပါသည်\"\\n\"Screen  ကို လက်နဲ့ပွတ်ဆွဲပြီး refresh လုပ်\u200Bပေးပါ စာ\u200Bတွေ\u200Bပေါ်လာပါမည်။\"\\n \"မ\u200Bပေါ်လာပါက ဗွီ ပီ အန် ကို အဖွင့် အပိတ်လုပ်ပြီးစမ်းကြည့်ပါ\"", Toast.LENGTH_LONG).show();

        }
    }
    public void  loadP(String thumbNail,String title,String subtitle){
        if((thumbNail.length()>0)&&(online)){
            Glide
                    .with(getApplicationContext())
                    .load(thumbNail)
                    .into(p_img);
        }else{
            Glide
                    .with(getApplicationContext())
                    .load(R.drawable.apk)
                    .into(p_img);
        }
        p_title.setText(title);
        p_subtitle.setText(subtitle);
    }






    public void gotoFacebook()
    {
        final String urlFb = "fb://page/"+FACEBOOK_PROFILE_ID;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser

        final PackageManager packageManager = getPackageManager();

        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() == 0) {
            final String urlBrowser = FACEBOOK_URL;
            intent.setData(Uri.parse(urlBrowser));
        }

        startActivity(intent);
    }
    private void showNoInternet (){
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

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {
                dialog.dismiss();
            }});
    }}
