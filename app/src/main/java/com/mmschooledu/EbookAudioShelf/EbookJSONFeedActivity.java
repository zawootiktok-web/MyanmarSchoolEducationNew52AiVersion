package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.content.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import org.json.*;

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.*;
//import com.squareup.picasso.*;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.startapp.sdk.adsbase.StartAppAd;


public abstract class EbookJSONFeedActivity extends AppCompatActivity
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
	String currentLink="";
	final int FONT_ZAWGYI=1;
	final int FONT_UNI=2;
	final int FONT_NONE=0;
	int currentFont=FONT_NONE;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ebook_jsonfeed_layout);



		tb=(Toolbar)findViewById(R.id.nnl_toolbar);
		setSupportActionBar(tb);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);



		adView = (AdView) 
			findViewById(R.id.ad_view);           
		AdRequest adRequest = new AdRequest.Builder().build();           
		adView.loadAd(adRequest); 


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
			rv.setLayoutManager(new GridLayoutManager(this,1));
		}else{
			rv.setLayoutManager(new LinearLayoutManager(this));
		}
		SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
		currentFont = sharedPreferences.getInt("font_main", 0);
		refresh();
	}




	public void refresh(){
		//adapter.reset();
		currentLink=getFeedAddress();
		tb.collapseActionView();
		if(isOnline()){
			online=true;
			new DownloadTask().execute(currentLink);
		}else{
			online=false;
			Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();

			try{
				processJson(getFromPrefs(currentLink));
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
		editor.putString(currentLink,result);
		editor.commit();
	}

	public String getFromPrefs(String key){

		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		String lastData= sharedPreferences.getString(currentLink,"");
		return lastData;
	}



	private class DownloadTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... p1)
		{
			String result=EbookJSONDownloader.download(p1[0]);
			try{
				JSONObject jo=new JSONObject(result);
				jo=jo.getJSONObject("feed").getJSONObject("openSearch$totalResults");
				int totalPosts=Integer.parseInt(jo.getString("$t"));
				result = EbookJSONDownloader.download(p1[0] + "&max-results=" + totalPosts);

			}catch(Exception e){

			}
			return result;
		}




		@Override
		protected void onPreExecute() { 
			mSwipeLayout.setRefreshing(true); 
		} 



		@Override
		public void onPostExecute(String result) {
			processJson(result);
			SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("lastData",result);
			editor.commit();
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
		getMenuInflater().inflate(R.menu.ebook_btmain_menu,menu);
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
			finish();
		}
		else
		if(item.getItemId()==R.id.menu_search){
			refresh();
		}else
		{
			_Options_Menu_Click(item);
		}


		return super.onOptionsItemSelected(item);
	}

	public void addItem(EbookPostItem item){
		posts.add(item);
	}

	//public abstract void _FAB_Click(View v)

	public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>
	{
		@Override
		public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
		{
			View v=getLayoutInflater().inflate(R.layout.ebook_item_layout,p1,false);
			return new ViewHolder(v);
		}

		public void filter(String charText){
			charText =charText.toLowerCase();
			filteredposts.clear();
			if (charText.length()==0){
				filteredposts.addAll(posts);
			}else{
				for (EbookPostItem pi : posts){
					if(pi.title.toLowerCase().contains(charText))
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
			if((filteredposts.get(p2).thumbnailUrl.length()>0)&&(online)){
				Glide
					.with(getApplicationContext())
					.load(Html.fromHtml(filteredposts.get(p2).thumbnailUrl).toString())
					.into(p1.iv);
			}else{
				p1.iv.setImageResource(R.drawable.ebook_apk);
			}
			p1.tv.setText(filteredposts.get(p2).title);
			//p1.tv.setText(Html.fromHtml(posts.get(p2).content.substring(0,100)+" ..."));
			p1.tv2.setText(Html.fromHtml(posts.get(p2).content.substring(0,Math.min(posts.get(p2).content.length(),100))+" ...").toString());
			//published
			p1.tv3.setText(filteredposts.get(p2).published);

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
			TextView tv2;
			TextView tv3;

			@Override
			public void onClick(View view)
			{
				Intent i=new Intent(EbookJSONFeedActivity.this,EbookBtViewerActivity.class);
				EbookPostItem pi=filteredposts.get(getAdapterPosition());
				i.putExtra("title",pi.title);
				i.putExtra("content",pi.content);
				i.putExtra("published",pi.published);
				startActivity(i);

				StartAppAd.showAd(getBaseContext());
			}
			public ViewHolder(View view)
			{
				super(view);
				iv=view.findViewById(R.id.ivItemImage);
				tv=view.findViewById(R.id.tvItemText);
				tv2=(TextView) view.findViewById(R.id.tvItemText2);
				tv3=(TextView) view.findViewById(R.id.tvItemText3);
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

	}}

	
