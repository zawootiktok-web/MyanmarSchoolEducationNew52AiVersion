package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;


import android.content.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.pm.*;
import android.*;
import com.bumptech.glide.Glide;
import com.startapp.sdk.adsbase.StartAppAd;

public abstract class EbookTvIndexActivity extends AppCompatActivity

{

	public abstract void processJson(String jsonString);
	public abstract boolean useGridLayout();
	public abstract String getFeedAddress();
	private SwipeRefreshLayout mSwipeLayout;
	RecyclerView rv;
	FeedAdapter adapter;
	List<EbookCategoryItem> posts,filteredposts;
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


	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ebook_activity_main1);

		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout); 
		mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { 
				@Override 
				public void onRefresh() { 
					if(isOnline())
						refresh();
					else{
						mSwipeLayout.setRefreshing(false);
						Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
						showNoInternet();
					}
				} });

		mSwipeLayout.setColorSchemeResources(
			R.color.ebook_refresh_progress_1,
			R.color.ebook_refresh_progress_2,
			R.color.ebook_refresh_progress_3); 

		posts=new ArrayList<EbookCategoryItem>();
		filteredposts=new ArrayList<EbookCategoryItem>();
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



	 new EbookCheckUpdateAsyncTask(this,"https://topaone-computer-basic.blogspot.com/feeds/posts/default?alt=json");
	 }catch(Exception e){
	 Toast.makeText(getApplicationContext(),"Can't be used update!",Toast.LENGTH_SHORT).show();
	 }
	 }*/



	public void refresh(){

		currentLink=getFeedAddress();

		if(isOnline()){
			online=true;
			try{
				new DownloadTask().execute(currentLink);

			//	showAbout();
			}catch(Exception e){
				Toast.makeText(getApplicationContext(),"Can't be used Data!",Toast.LENGTH_SHORT).show();
			}	
		}else{
			online=false;

			showNoInternet();
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
		getMenuInflater().inflate(R.menu.ebook_main_menu,menu);
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

	public void addItem(EbookCategoryItem item){
		posts.add(item);
	}

	public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>
	{
		@Override
		public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
		{
			View v=getLayoutInflater().inflate(R.layout.ebook_type_item,p1,false);
			return new ViewHolder(v);
		}

		public void filter(String charText){
			charText =charText.toLowerCase();
			filteredposts.clear();
			if (charText.length()==0){
				filteredposts.addAll(posts);
			}else{
				for (EbookCategoryItem pi : posts){
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

		public EbookCategoryItem getItem(int pos){
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
			filteredposts=new ArrayList<EbookCategoryItem>();
			filteredposts.addAll(posts);
		}

		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
		{
			ImageView iv;
			TextView tv;

			@Override
			public void onClick(View view)
			{
				try{

				}catch(Exception e){

					/*	
					 public void goBookActivity(String title,String category){
					 Intent i=new Intent(EbookIndexActivity.this,EbookBookActivity.class);         
					 i.putExtra("title",title);
					 i.putExtra("res",res);
					 startActivity(i);
					 }*/
				}
				//	Intent i=new Intent(EbookIndexActivity.this,VideoActivity.class);

				Intent i=new Intent(EbookTvIndexActivity.this,EbookTvActivityCategories.class);

				EbookCategoryItem pi=filteredposts.get(getAdapterPosition());

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
			processJson(getFromPrefs(currentLink));
			adapter=new FeedAdapter();
			rv.setAdapter(adapter);
		}catch(Exception e){
			Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
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
				.load(R.drawable.ebook_ic_launcher)
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
		View parent_view = LayoutInflater.from(this).inflate(R.layout.ebook_nointernet_dia, null);
		dialog.setView(parent_view);
		dialog.setCancelable(false);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.show();

		// Finding Views inside dialog
		TextView tv_title = dialog.findViewById(R.id.dialogTextView1);
		Button button = dialog.findViewById(R.id.dialogButton1);

		tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ြခင်းမရှိပါ။ ​ေကျးဇူး​ြပု၍ အင်တာနက် ဆက်သွယ်​ေပးပါ။");

		button.setText("..ဟုတ်က​ဲ့..");

		button.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					dialog.dismiss();
				}});
	}
}
/*
	private void showAbout(){
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		View parent_view = LayoutInflater.from(this).inflate(R.layout.ebook_about_dia, null);
		dialog.setView(parent_view);
		dialog.setCancelable(false);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.show();
// Finding Views inside dialog
		TextView tv_title = (TextView) dialog.findViewById(R.id.dialogTextView1);
		Button button = (Button) dialog.findViewById(R.id.dialogButton1);

		tv_title.setText("😘​​အခမ​ဲ့ ​ြကည့်ရှုနိုင်ပါ​ေ​ြကာင်း🤗");
		//tv_title.setTypeface(getPyiDaungSuTypeface());
		button.setText("ဟုတ်က​ဲ့");
		//button.setTypeface(getPyiDaungSuTypeface());
		button.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					dialog.dismiss();
				}});

	}

}
	
*/
