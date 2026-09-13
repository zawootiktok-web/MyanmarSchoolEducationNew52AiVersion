package com.mmschooledu.Mp4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bdtopcoder.quickadmob.onDismiss;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.mmschooledu.AudienceNetwork;
import com.mmschooledu.Item.BookItem;
import com.mmschooledu.Item.CategoryItem;
import com.mmschooledu.JSONDownloader;
import com.mmschooledu.R;
//import com.startapp.sdk.adsbase.StartAppAd;

import java.util.ArrayList;
import java.util.List;
public abstract class VideoLessonfeedActivity extends AppCompatActivity
{
    private AudienceNetwork audienceNetwork;
    private InterstitialAd mInterstitialAd;
    private AdView adView;


    public abstract void processJson(String jsonString,String category);
    public abstract boolean useGridLayout();
    public abstract String getFeedAddress();
    public abstract void _Options_Menu_Click(MenuItem item);
    private SwipeRefreshLayout mSwipeLayout;
    RecyclerView rv;
    FeedAdapter adapter;
    List<BookItem> posts,filteredposts;
    boolean online=true;
    Toolbar tb;
//    String link="";

	String url;
	String link=url;
	//String title;


    final int FONT_ZAWGYI=1;
    final int FONT_UNI=2;
    final int FONT_NONE=0;
    int currentFont=FONT_NONE;
    String title,bname,category;

    int itemClick=0;

	private Handler mHandler;
    private Runnable mRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        audienceNetwork = new AudienceNetwork(this)
                .setFullScreenStatus(true)
                // .setBannerStatus(true)
                .init()
                // .setInterstitialId("VID_HD_16_9_15S_APP_INSTALL#YOUR_PLACEMENT_ID")
                .setInterstitialId("1072549720831975_1325325095554435")
                //.setBannerId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
                .setBannerId("1072549720831975_1072552904164990")
                .setBannerStatus(true)
                .setBannerLayoutId(R.id.ad_container) // LinearLayout ID for the banner
                .build();

        // Load banner ad into the specified container
        LinearLayout bannerContainer = findViewById(R.id.ad_container);


        //title = getIntent().getStringExtra("title");
		//getSupportActionBar().setTitle(title+"");



		url=getIntent().getExtras()
			.get("link").toString();

		title=getIntent().getExtras()
			.get("title").toString();
		category=getIntent().getExtras()
			.get("category").toString();


        tb=(Toolbar)findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//adview=(AdView) findViewById(R.id.adView); 


		mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                String title = tb.getTitle().toString();
                tb.setTitle(title.substring(1) + title.substring(0, 1));
                mHandler.postDelayed(this, 700);
            }//500,500
        };
        mHandler.postDelayed(mRunnable, 1000);



//        adView = (AdView)
//                findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout); 
		mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { 
                @Override 
                public void onRefresh() { 
                    if(isOnline())
                        refresh();
                    else{
                        mSwipeLayout.setRefreshing(false);
                        Toast.makeText(getApplicationContext(),"အင်တာနက်မရှိပါ",Toast.LENGTH_SHORT).show();
                    }
                } });

        mSwipeLayout.setColorSchemeResources(
            R.color.refresh_progress_1,
            R.color.refresh_progress_2,
            R.color.refresh_progress_3); 

        posts=new ArrayList<BookItem>();
        filteredposts=new ArrayList<BookItem>();
        rv=(RecyclerView)findViewById(R.id.recyclerview);
        if(useGridLayout()){
            rv.setLayoutManager(new GridLayoutManager(this,3));
        }else{
            rv.setLayoutManager(new LinearLayoutManager(this));
        }

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        currentFont = sharedPreferences.getInt("font_main", 0);
		title=getIntent().getExtras()
			.get("title").toString();
		category=getIntent().getExtras()
			.get("category").toString();
        getSupportActionBar().setTitle(title+"");
		/*bname=getIntent().getExtras()
		 .get("bname").toString();
		 getSupportActionBar().setTitle(bname+"");*/
        refresh();

    }
	/*  @Override
	 protected void onStart() {
	 super.onStart();


	 req= new AdRequest.Builder().build();
	 adview.loadAd(req); 

	 }

	 @Override
	 public void onPause() {
	 if (adview != null) {
	 adview.pause();
	 }
	 super.onPause();
	 }

	 @Override
	 public void onResume() {
	 super.onResume();
	 if (adview!= null) {
	 adview.resume();
	 }
	 }

	 @Override
	 public void onDestroy() {
	 if (adview != null) {
	 adview.destroy();
	 }
	 super.onDestroy();
	 } 
	 */

    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (mInterstitialAd!=null) {
            mInterstitialAd.show(this);
        }
    }


    public void refresh(){
        //adapter.reset();
        link=getFeedAddress();
        tb.collapseActionView();
        if(isOnline()){
            online=true;
            try{
                new DownloadTask().execute(link);

            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Can't be used Data!",Toast.LENGTH_SHORT).show();
            }   


        }else{
            online=false;


            try{
                processJson(getFromPrefs(link),"");
                adapter=new FeedAdapter();
                rv.setAdapter(adapter);

            }catch(Exception e){
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void saveToPrefs(String result){
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(link,result);
        editor.commit();
    }

    public String getFromPrefs(String key){

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String lastData= sharedPreferences.getString(link,category);
        return lastData;
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
            processJson(result,category);
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
	/*
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu)
	 {


	 return super.onCreateOptionsMenu(menu);
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item)
	 {

	 return super.onOptionsItemSelected(item);
	 }*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main_menu1,menu);
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
		if(id==android.R.id.home){
			//	mDrawerLayout.openDrawer(GravityCompat.START);
			//if(item.getItemId()==android.R.id.home){
			finish();
			return true;
		}
		if(item.getItemId()==R.id.menu_font){
			changeFont();
			//refresh();
		}
		else{
			_Options_Menu_Click(item);
		}
		return super.onOptionsItemSelected(item);
	}



    public void addItem(BookItem item){
        posts.add(item);
    }

    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>
    {
        @Override
        public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
        {
            View v=getLayoutInflater().inflate(R.layout.book_item,p1,false);
            return new ViewHolder(v);
        }

        public void filter(String charText){
            charText =charText.toLowerCase();
            filteredposts.clear();
            if (charText.length()==0){
                filteredposts.addAll(posts);
            }else{
                for (BookItem pi : posts){
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
//@Overide
//ဒီ​ေနရာပြင်ပါ
		public void setTitleText(String title){
			setTitle(title);
		}

        @Override
        public void onBindViewHolder(FeedAdapter.ViewHolder p1,final int p2) 
        {
            if((filteredposts.get(p2).thumbnail.length()>5)&&(online)){
                Glide
                    .with(getApplicationContext())
                    .load(filteredposts.get(p2).thumbnail).placeholder(R.drawable.loading_book)
                    .into(p1.iv);

            }else{
                p1.iv.setImageResource(R.drawable.loading_book);
            }
            p1.bname.setText(filteredposts.get(p2).bname);
            p1.card.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1) {


//                        new com.bdtopcoder.quickadmob.Admob(new onDismiss() {
//                            @Override
//                            public void onDismiss() {




//                        if(isOnline()){
//                            online=true;
//
//
//
//                            Intent i=new Intent(VideoLessonfeedActivity.this,VideoDetailActivity.class);    //    DetailActivity2
//
//							i.putExtra("thumbnail",filteredposts.get(p2).thumbnail);
//							i.putExtra("bname",filteredposts.get(p2).bname);
//							i.putExtra("wname",filteredposts.get(p2).wname);
//							i.putExtra("link",filteredposts.get(p2).link);
//
//							i.putExtra("link2",filteredposts.get(p2).link2);
//							i.putExtra("title",filteredposts.get(p2).category);
//							i.putExtra("mb1",filteredposts.get(p2).mb1);
//							i.putExtra("mb2",filteredposts.get(p2).mb2);
//							i.putExtra("time",filteredposts.get(p2).time);
//
//
//                            startActivity(i);
//                          //  StartAppAd.showAd(getBaseContext());
//                        }else{
//                            online=false;
//
//
//
//                        }
//                    }
//                        }).ShowInterstitial(VideoLessonfeedActivity.this, true);
//                    }
//            });}


                        if (isOnline()) {
                            online = true;

                            try {
                                // Check if the interstitial ad is loaded
                                if (audienceNetwork.isLoaded()) {
                                    // Show the interstitial ad
                                    audienceNetwork.show(new AudienceNetwork.Dismissed() {
                                        @Override
                                        public void onclick() {
                                            // Action to perform after ad is dismissed
                                            launchViewerActivity();
                                        }
                                    });
                                } else {
                                    // If the ad is not loaded, directly proceed to the next action
                                    launchViewerActivity();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            online = false;
                        }
                    }

                private void launchViewerActivity() {
                 //   Intent intent = new Intent(VideoLessonfeedActivity.this, VideoDetailActivity.class);
                    Intent i=new Intent(VideoLessonfeedActivity.this,VideoDetailActivity.class);    //    DetailActivity2
//
                    i.putExtra("thumbnail",filteredposts.get(p2).thumbnail);
							i.putExtra("bname",filteredposts.get(p2).bname);
							i.putExtra("wname",filteredposts.get(p2).wname);
							i.putExtra("link",filteredposts.get(p2).link);

							i.putExtra("link2",filteredposts.get(p2).link2);
							i.putExtra("title",filteredposts.get(p2).category);
							i.putExtra("mb1",filteredposts.get(p2).mb1);
							i.putExtra("mb2",filteredposts.get(p2).mb2);
							i.putExtra("time",filteredposts.get(p2).time);

                    // Start the VideoDetailActivity
                    startActivity(i);

                    // Optionally show an ad (commented out here)
                    // StartAppAd.showAd(getBaseContext());
                }
            });}

        public BookItem getItem(int pos){
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
            filteredposts=new ArrayList<BookItem>();
            filteredposts.addAll(posts);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {

            ImageView iv;
            CardView card;
            TextView bname;
            @Override
            public void onClick(View view)
            {


            }
            public ViewHolder(View view)
            {
                super(view);
                iv=view.findViewById(R.id.image);
                bname=view.findViewById(R.id.bname);
                card=view.findViewById(R.id.card);
                view.setOnClickListener(this);
            }
        }
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
            processJson(getFromPrefs(link),category);
            adapter=new FeedAdapter();
            rv.setAdapter(adapter);

        }catch(Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }}

