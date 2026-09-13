package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;


import android.content.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.widget.*;
//import com.squareup.picasso.*;
import java.util.*;

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.startapp.sdk.adsbase.StartAppAd;

import android.os.Handler;
public abstract class EbookAudioJSONFeedActivity extends AppCompatActivity
{ 
	private InterstitialAd interstitial;
	private AdView adView;     
	public abstract void processJson(String jsonString);
	public abstract boolean useGridLayout();
	public abstract String getFeedAddress();
	public abstract void _Options_Menu_Click(MenuItem item);
	private SwipeRefreshLayout mSwipeLayout;
	RecyclerView rv;
	FeedAdapter adapter;
	List<EbookPostItem> posts,filteredposts;
	boolean online=true;
	Toolbar tb;
	String url;
	String link=url;
	String title;
	
	//String bname;
	
	//String link="";
	final int FONT_ZAWGYI=1;
	final int FONT_UNI=2;
	final int FONT_NONE=0;
	int currentFont=FONT_NONE;
	String category;
	//DrawerLayout mDrawerLayout;
	//private ActionBarDrawerToggle mDrawerToggle;
	//private NavigationView navigationView;

	
	
	private Handler mHandler;
    private Runnable mRunnable;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ebook_audiojsonfeed_layout);

		url=getIntent().getExtras()
			.get("link").toString();

		title=getIntent().getExtras()
			.get("title").toString();
		category=getIntent().getExtras()
			.get("category").toString();
		
		/*
		bname=getIntent().getExtras()
			.get("bname").toString();*/
			
		tb=(Toolbar)findViewById(R.id.nnl_toolbar);
		setSupportActionBar(tb);
		/*navigationView = (NavigationView) findViewById(R.id.nnl_navigation_view);
		 mDrawerLayout = (DrawerLayout) findViewById(R.id.nnl_drawer_layout);
		 mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
		 mDrawerLayout.setDrawerListener(mDrawerToggle);*/
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//setupNV();

		/*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
		 @Override
		 public boolean onNavigationItemSelected(MenuItem menuItem)
		 {
		 menuItem.setChecked(true);
		 mDrawerLayout.closeDrawers();
		 //_MenuItem_Click(menuItem.getItemId());
		 _Options_Menu_Click(menuItem);
		 return true;
		 }
		 });*/
		 
		
		 
		 
		 
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
		
		 


		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout); 
		mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { 
				@Override 
				public void onRefresh() { 
					if(isOnline())
						refresh();
					else{
						mSwipeLayout.setRefreshing(false);
						Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
					}
				} });

		mSwipeLayout.setColorSchemeResources(
			R.color.ebook_refresh_progress_1,
			R.color.ebook_refresh_progress_2,
			R.color.ebook_refresh_progress_3); 

		posts=new ArrayList<EbookPostItem>();
		filteredposts=new ArrayList<EbookPostItem>();
		rv=(RecyclerView)findViewById(R.id.recyclerview);
		if(useGridLayout()){
			rv.setLayoutManager(new GridLayoutManager(this,2));
		}else{
			rv.setLayoutManager(new LinearLayoutManager(this));
		}
		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		currentFont = sharedPreferences.getInt("font_main", 0);
		refresh();
    }

	/*private void setupNV()
	 {
	 navigationView.getMenu().clear();
	 navigationView.inflateMenu(R.menu.ebook_navigation_menu);
	 }

	 protected void onPostCreate(Bundle savedInstanceState)
	 {
	 super.onPostCreate(savedInstanceState);
	 mDrawerToggle.syncState();
	 }

	 @Override
	 public void onConfigurationChanged(Configuration newConfig)
	 {
	 super.onConfigurationChanged(newConfig);
	 mDrawerToggle.onConfigurationChanged(newConfig);
	 }*/


	/*
	 private void setupNV()
	 {
	 bottomNavigationView.getMenu().clear();
	 bottomNavigationView.inflateMenu(R.menu.ebook_btmain_menu);
	 }

	 protected void onPostCreate(Bundle savedInstanceState)
	 {
	 super.onPostCreate(savedInstanceState);
	 mDrawerToggle.syncState();
	 }

	 @Override
	 public void onConfigurationChanged(Configuration newConfig)
	 {
	 super.onConfigurationChanged(newConfig);
	 mDrawerToggle.onConfigurationChanged(newConfig);
	 }
	 */

	
	 
	public void refresh(){
		//adapter.reset();
		link=getFeedAddress();
		tb.collapseActionView();
		if(isOnline()){
			online=true;
			new DownloadTask().execute(link);
		}else{
			online=false;
			Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();

			try{
				processJson(getFromPrefs(link));
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
		String lastData= sharedPreferences.getString(link,"");
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
			String result=EbookJSONDownloader.download(p1[0]);
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
		getMenuInflater().inflate(R.menu.ebook_main_menuqa,menu);
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
			//mDrawerLayout.openDrawer(GravityCompat.START);
			//return true;
			//if(item.getItemId()==android.R.id.home){
			finish();

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

	public void addItem(EbookPostItem item){
		posts.add(item);
	}

	public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>
	{
		@Override
		public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
		{
			View v=getLayoutInflater().inflate(R.layout.ebook_audio_item_layout,p1,false);
			return new ViewHolder(v);
		}

		public void filter(String charText){
			charText =charText.toLowerCase();
			filteredposts.clear();
			if (charText.length()==0){
				filteredposts.addAll(posts);
			}else{
				for (EbookPostItem pi : posts){
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
				p1.iv.setImageResource(R.drawable.ebook_ic_launcher);
			}
			p1.tv.setText(filteredposts.get(p2).bname);
		}

		public EbookPostItem getItem(int pos){
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
			filteredposts=new ArrayList<EbookPostItem>();
			filteredposts.addAll(posts);
		}

		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
		{
			ImageView iv;
			TextView tv;

			@Override
			public void onClick(View view)
			{
				//Intent i=new Intent(EbookJSONFeedActivity.this,EbookDetailActivity.class);
				/*	EbookPostItem pi=filteredposts.get(getAdapterPosition());
				 i.putExtra("title",pi.title);
				 i.putExtra("type",pi.type);
				 i.putExtra("link",pi.link);
				 i.putExtra("desc",pi.desc);
				 i.putExtra("image",pi.thumbnailUrl);
				 startActivity(i);
				 }*/

				Intent i=new Intent(EbookAudioJSONFeedActivity.this,EbookAudioBookActivity2.class);


				EbookPostItem pi=filteredposts.get(getAdapterPosition());

				i.putExtra("title",pi.title);
				i.putExtra("thumbnail",pi.thumbnail);
				i.putExtra("bname",pi.bname);
				i.putExtra("wname",pi.wname);
				i.putExtra("link",pi.link);
				i.putExtra("category",pi.category);
				i.putExtra("mb1",pi.mb1);
				i.putExtra("mb2",pi.mb2);
				i.putExtra("time",pi.time);

				startActivity(i);
				StartAppAd.showAd(getBaseContext());

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
			processJson(getFromPrefs(link));
			adapter=new FeedAdapter();
			rv.setAdapter(adapter);
		}catch(Exception e){
			Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
		}
	}

	@Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }
}
