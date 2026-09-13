package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;


import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.graphics.*;
import android.content.pm.*;
import android.view.View.*;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.AdRequest;

import androidx.cardview.widget.CardView;
/*import com.google.android.gms.ads.MobileAds;
 import com.google.android.gms.ads.AdLoader;
 import com.google.android.gms.ads.reward.RewardedVideoAdListener;
 import com.google.android.gms.ads.reward.RewardedVideoAd;
 import com.google.android.gms.ads.InterstitialAd;
 import com.google.android.gms.ads.AdListener;*/
//import com.htetznaing.xgetter.XGetter;
/*
 import com.afollestad.materialdialogs.DialogAction;
 import com.afollestad.materialdialogs.MaterialDialog;
 import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
 import com.github.javiersantos.materialstyleddialogs.enums.Style;
 import com.htetznaing.xgetter.Model.XModel;*/
//import com.htetznaing.xgetter.XGetter;
//import com.cnimea.city.Player.XPlayer;

import java.io.File;

import android.Manifest;
import android.content.pm.PackageManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
//import com.myanmarsurveyor.manual.Player.XPlayer;


import com.google.android.gms.ads.*;


public abstract class EbookEBookIndexFeedActivity extends AppCompatActivity
{
	private InterstitialAd interstitial;
	private AdView adView;     
	
	//XGetter xGetter;
	String org;
	TextView tv,tv1,tv2,tv3,tv4;
	ImageView iv;
	//String id="link";
	String link,sdlink,hdlink,bname,thumbnail,category,wname,mb1,mb2,time;
	//ProgressDialog pd,progressDialog;
	//private DownloadManager.Request mRequest;
	//private String mBaseFolderPath;
	//private DownloadManager mDownloadManager;
	//private long mDownloadedFileID;
	private Intent intent;




	//public abstract void processRecent(String jsonString, String cateogry);

	//public abstract void processTitleImg(String jsonString);
    public abstract String getFeedAddress();
	public abstract void _Options_Menu_Click(MenuItem item);
	private SwipeRefreshLayout mSwipeLayout;
	RecyclerView recent_rv,suggested_rv,child_rv,young_rv;
	//RecentAdapter recent_adapter;

	//List<EbookRecentItem> r_posts,r_filteredposts;

	boolean online=true;
	Toolbar tb;
	String currentLink="";
	final int FONT_ZAWGYI=1;
	final int FONT_UNI=2;
	final int FONT_NONE=0;
	int currentFont=FONT_NONE;

	//DrawerLayout mDrawerLayout;
	//private ActionBarDrawerToggle mDrawerToggle;
	//private NavigationView navigationView;
	String FACEBOOK_URL = "https://www.facebook.com/%E1%80%A1%E1%80%B1%E1%80%B8%E1%80%9E%E1%80%AC%E1%80%9A%E1%80%AC-%E1%80%85%E1%80%AC%E1%80%80%E1%80%BC%E1%80%8A%E1%80%BA%E1%80%B7%E1%80%90%E1%80%AD%E1%80%AF%E1%80%80%E1%80%BA-102387168438840/"; 
	String FACEBOOK_PROFILE_ID = "102387168438840";
	Handler handler = new Handler();
	Runnable runnable;
	//   int currentImg=0;
	// int maxImg=4;
	//   ImageView title_img;
    LinearLayout pre,next;
    TextView position;
	Button recent_bt,suggested_bt,child_bt,young_bt,imageview2,imageview3,textview2;
	int bookCount=0;
	int ItemClick=0;
	private FloatingActionButton play_fab;

	
    public abstract void processYoung(String jsonString, String cateogry);
    YoungAdapter young_adapter;
    List<EbookVideoItem> y_posts,y_filteredposts;

    CardView writer_card,category_card,ads_card,book_card;
	String about,developer;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		//AudienceNetworkAds.initialize(this);

        super.onCreate(savedInstanceState);

		setContentView(R.layout.ebook_detailactivity);

		


		tb=(Toolbar)findViewById(R.id.nnl_toolbar);
		setSupportActionBar(tb);
		tb.setLogo(R.drawable.ebook_ic_launcher);
        tb.setTitle("ATy");
		//navigationView = (NavigationView) findViewById(R.id.nnl_navigation_view);

		//  title_img=(ImageView) findViewById(R.id.title_img);
        //pre=(LinearLayout) findViewById(R.id.pre_l);
		//  next=(LinearLayout) findViewById(R.id.next_l);            
		//     position=(TextView) findViewById(R.id.position);            
		//    recent_bt=(Button) findViewById(R.id.recent_bt);            
		//	suggested_bt=(Button) findViewById(R.id.suggested_bt);            
		//	child_bt=(Button) findViewById(R.id.child_bt);            
        young_bt=(Button) findViewById(R.id.young_bt);            
		//  mDrawerLayout = (DrawerLayout) findViewById(R.id.nnl_drawer_layout);
		//  adview0=(AdView) findViewById(R.id.adView_0); 
		//  adview1=(AdView) findViewById(R.id.adView_1); 
        //adview2=(AdView) findViewById(R.id.adView_2); 
		/*   mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
		 mDrawerLayout.setDrawerListener(mDrawerToggle);
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 setupNV();
		 navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
		 @Override
		 public boolean onNavigationItemSelected(MenuItem menuItem)
		 {
		 menuItem.setChecked(true);
		 mDrawerLayout.closeDrawers();
		 _Options_Menu_Click(menuItem);
		 return true;
		 }
		 });
		 */



		link = getIntent().getStringExtra("link");
		requestAppPermissions();
		//link=intent.getStringExtra("link");
		iv = (ImageView) findViewById(R.id.ivDetail);
		//id = getIntent().getStringExtra("link");
		thumbnail = getIntent().getStringExtra("thumbnail");
		bname = getIntent().getStringExtra("bname");
		wname = getIntent().getStringExtra("wname");
		mb1 = getIntent().getStringExtra("mb1");
		mb2 = getIntent().getStringExtra("mb2");
		time = getIntent().getStringExtra("time");

		//category = getIntent().getStringExtra("category");
		tv = (TextView) findViewById(R.id.tvTitle);
		tv.setText(bname);
		tv1 = (TextView) findViewById(R.id.detail_movie_desc);
		tv1.setText(wname);

		tv2 = (TextView) findViewById(R.id.mb1);
		tv2.setText(mb1);

		tv3 = (TextView) findViewById(R.id.mb2);
		tv3.setText(mb2);

		tv4 = (TextView) findViewById(R.id.time);
		tv4.setText(time);
		Glide.with(this)
			.load(thumbnail)
			.into(iv);
		//tv.setText(bname + "\n" + category);
		tv.setText(bname );
		tv1.setText(wname );
		tv2.setText(mb1 );
		tv3.setText(mb2 );
		tv4.setText(time );

		//tv.setTextColor(Color.rgb(30, 9, 203));
		tv.setTextColor(Color.rgb(225, 248, 250));
		//android:textColor="#E0E0E0"

		//Button imageview3 =  (Button) findViewById(R.id.imageview3);

		//imageview2=(Button) findViewById(R.id.imageview2); 
		//imageview3= (Button) findViewById(R.id.imageview3); 

		//ImageView imageview2=(ImageView) findViewById(R.id.imageview2);
		//ImageView imageview3 =(ImageView) findViewById(R.id.imageview3);
		//TextView textview2 = (TextView) findViewById(R.id.textview2);


		//pd = new ProgressDialog(this);
		//pd.setTitle("Please wailt...\n"+"Link ယူနေပါသဖြင် ခဏစောင့်ပါ ");
		//progressDialog = new ProgressDialog(this);
		//progressDialog.setCancelable(false);
		//mDownloadManager = (DownloadManager) getApplicationContext().getSystemService(DOWNLOAD_SERVICE);
		//mBaseFolderPath = android.os.Environment.getExternalStorageDirectory() + File.separator + "Cinema" + File.separator;}
		//FloatingActionButton play_fab = (FloatingActionButton) findViewById(R.id.floating_play_btn);









		adView = (AdView) 
			findViewById(R.id.ad_view);           
		AdRequest adRequest = new AdRequest.Builder().build();           
		adView.loadAd(adRequest); 


		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout); 
		mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { 
				@Override 
				public void onRefresh() { 
					if(isOnline())
					{
						refresh();
					}
					else{
						mSwipeLayout.setRefreshing(false);
						Toast.makeText(getApplicationContext(),"အင်တာနက်ဆက်သွယ်မှု မရှိပါ",Toast.LENGTH_SHORT).show();
						showNoInternet();
					}
				} });

		mSwipeLayout.setColorSchemeResources(
			R.color.ebook_refresh_progress_1,
			R.color.ebook_refresh_progress_2,
			R.color.ebook_refresh_progress_3); 

		//r_posts=new ArrayList<EbookRecentItem>();
		//r_filteredposts=new ArrayList<EbookRecentItem>();
		//s_posts=new ArrayList<SuggestedItem>();
		//    s_filteredposts=new ArrayList<SuggestedItem>();

		//   c_posts=new ArrayList<ChildItem>();
		//    c_filteredposts=new ArrayList<ChildItem>();
		y_posts=new ArrayList<EbookVideoItem>();
		y_filteredposts=new ArrayList<EbookVideoItem>();

		//	recent_rv=(RecyclerView)findViewById(R.id.recent_rv);
		//   suggested_rv=(RecyclerView)findViewById(R.id.suggested_rv);
		//child_rv=(RecyclerView)findViewById(R.id.child_rv);
		young_rv=(RecyclerView)findViewById(R.id.young_rv);

		//writer_card=(CardView)findViewById(R.id.person_card);
		// category_card=(CardView)findViewById(R.id.category_card);
		//book_card=(CardView)findViewById(R.id.all_card); 
		//ads_card=(CardView)findViewById(R.id.ads_card);

		//    horizontalRV(recent_rv);
		//     horizontalRV(suggested_rv);
		//     horizontalRV(child_rv);
		horizontalRV(young_rv);

		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		currentFont = sharedPreferences.getInt("font_main", 0);
		refresh();


	}

	
	
	

	private void downloadFromUrl()
	{
		String url=link;
		if (url.contains("://drive.google.com"))
			url = getGoogleDriveDownloadLinkFromUrl(link);
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		request.setTitle(bname);
		String mBaseFolderPath = android.os.Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "MyanmarSchoolEducation" + File.separator;
		if (!new File(mBaseFolderPath).exists())
		{
			new File(mBaseFolderPath).mkdir();
		}

		String mFilePath = "file://" + mBaseFolderPath + "/" + bname + ".pdf";
		request.setDestinationUri(Uri.parse(mFilePath));
		request.allowScanningByMediaScanner();
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
		dm.enqueue(request);
		Toast.makeText(this, "Download started.", 1).show();
	}

	public static String getGoogleDriveDownloadLinkFromUrl(String url)
	{
		int index = url.indexOf("id=");
		int closingIndex=0;
		if (index >= 0)
		{
			index += 3;
			closingIndex = url.indexOf("&", index);
			if (closingIndex < 0)
				closingIndex = url.length();
		}
		else
		{
			index = url.indexOf("file/d/");
			if (index < 0) // url is not in any of the supported forms
				return url;

			index += 7;

			closingIndex = url.indexOf("/", index);
			if (closingIndex < 0)
			{
				closingIndex = url.indexOf("?", index);
				if (closingIndex < 0)
					closingIndex = url.length();
			}
		}
		String id=url.substring(index, closingIndex);
		return ("https://drive.google.com/uc?id=" + id + "&export=download");
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		if (requestCode == 101)
		{
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
				&& grantResults[1] == PackageManager.PERMISSION_GRANTED)
			{
				downloadFromUrl();
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

	}


	private void requestAppPermissions() {
		if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			return;
		}

		if (hasReadPermissions() && hasWritePermissions()) {
			return;
		}

		ActivityCompat.requestPermissions(this, new String[] {
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





	void horizontalRV(RecyclerView rv){
		androidx.recyclerview.widget.LinearLayoutManager layoutManager
				= new androidx.recyclerview.widget.LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
		rv.setLayoutManager(layoutManager);
		ViewCompat.setNestedScrollingEnabled(rv, false);
	}

	public void refresh(){
		currentLink=getFeedAddress();
		tb.collapseActionView();
		if(isOnline()){
			online=true;
			try{
				new DownloadTask().execute(currentLink);
			}catch(Exception e){
				Toast.makeText(getApplicationContext(),"Can't be used Data!",Toast.LENGTH_SHORT).show();
			}	
		}else{
			online=false;

			showNoInternet();
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
			try{


				//	processTitleImg(result);
				//    processRecent(result,"recent");
				saveToPrefs(result);
				mSwipeLayout.setRefreshing(false);
				//   recent_adapter=new RecentAdapter();
				// recent_rv.setAdapter(recent_adapter);
				//     processSuggested(result,"suggested");
				//       suggested_adapter=new SuggestedAdapter();
				//	suggested_rv.setAdapter(suggested_adapter);
				//	processChild(result,"child");
				//		child_adapter=new ChildAdapter();
				//	child_rv.setAdapter(child_adapter);

				processYoung(result,"books");
				young_adapter=new YoungAdapter();
				young_rv.setAdapter(young_adapter);

				/*
				 recent_bt.setOnClickListener(new OnClickListener(){

				 @Override
				 public void onClick(View p1) {
				 goBookActivity("မက​ြာ​သ​ေးမီက(အသစ်)","recent");
				 }
				 });
				 suggested_bt.setOnClickListener(new OnClickListener(){

				 @Override
				 public void onClick(View p1) {
				 goBookActivity("သင့်အတွက်","suggested");
				 }
				 });

				 child_bt.setOnClickListener(new OnClickListener(){

				 @Override
				 public void onClick(View p1) {
				 goBookActivity("ကလ​ေးများအတွက်","child");
				 }
				 });*/
				young_bt.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View p1) {
							//goBookActivity("လူငယ်များအတွက်","young");

							goBookActivity("Survey Books ", "survey");


						}
					});


			}catch(Exception e){

			}



		}
	}



	public void downloadClick(View v)
	{
		int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
		int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permissionCheck1 == PackageManager.PERMISSION_GRANTED && permissionCheck2 == PackageManager.PERMISSION_GRANTED)
		{
			downloadFromUrl();
		}
		else
		{
			requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
		}
	}

	public void playClick(View v)
	{
		//Intent intent=new Intent(this, IndexFeedActivity2.class);

		Intent intent=new Intent(this, EbookBookViewerActivity.class);
		intent.putExtra("bname", bname);
		intent.putExtra("wname", wname);
		intent.putExtra("link", link);
		startActivity(intent);
	}





	public void goBookActivity(String title,String category){
		Intent i=new Intent(EbookEBookIndexFeedActivity.this,EbookBookActivity.class);         
		//i.putExtra("title",title);
		//i.putExtra("category",category);

		i.putExtra("thumbnail",thumbnail);
		i.putExtra("bname",bname);
		i.putExtra("title",title);
		i.putExtra("wname",wname);
		i.putExtra("link",link);
		i.putExtra("category",category);
		i.putExtra("mb1",mb1);
		i.putExtra("mb2",mb2);
		i.putExtra("time",time);

		startActivity(i);
	}



	/*
	 void showGlide(ImageView iv,String src){
	 Glide
	 .with(getApplicationContext())
	 .load(src).placeholder(R.drawable.ebook_ic_launcher)
	 .into(iv);
	 }*/

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
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if(id==android.R.id.home){
			//mDrawerLayout.openDrawer(GravityCompat.START);
			return true;
		}
		if(item.getItemId()==R.id.menu_font){
			changeFont();
			refresh();
		}
		else{
			_Options_Menu_Click(item);
		}
		return super.onOptionsItemSelected(item);
	}


	//for young
	public void addItem(EbookVideoItem item){
		y_posts.add(item);
	}
	public class YoungAdapter extends RecyclerView.Adapter<YoungAdapter.ViewHolder>
	{
		@Override
		public YoungAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
		{
			View v=getLayoutInflater().inflate(R.layout.ebook_image_item,p1,false);
			return new ViewHolder(v);
		}

		public void filter(String charText){
			charText =charText.toLowerCase();
			y_filteredposts.clear();
			if (charText.length()==0){
				y_filteredposts.addAll(y_posts);
			}else{
				for (EbookVideoItem pi : y_posts){
					if(pi.bname.toLowerCase().contains(charText))
					{ 
						y_filteredposts.add(pi);
					}
				}
			}
			notifyDataSetChanged();
		}

		@Override
		public int getItemCount()
		{

			bookCount=y_filteredposts.size();
			if(bookCount>10){
				bookCount=10;
			}else{
				bookCount=y_filteredposts.size();
			}
			return bookCount;
		}

		@Override
		public void onBindViewHolder(YoungAdapter.ViewHolder p1, final int p2)
		{
			if((y_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
				Glide
					.with(getApplicationContext())
					.load(y_filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
					.into(p1.iv);

			}else{

				p1.iv.setImageResource(R.drawable.ebook_loading_book);
			}
			p1.bname.setText(y_filteredposts.get(p2).bname);


			p1.card.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1) {

						gotoBDActivity(y_filteredposts.get(p2).thumbnail,y_filteredposts.get(p2).bname,y_filteredposts.get(p2).wname,y_filteredposts.get(p2).link,y_filteredposts.get(p2).category);
					}
				});
		}

		public EbookVideoItem getItem(int pos){
			return y_filteredposts.get(pos);
		}

		public void reset()
		{
			y_posts.clear();
			y_filteredposts.clear();
			notifyDataSetChanged();
		}

		public YoungAdapter()
		{
			super();
			y_filteredposts=new ArrayList<EbookVideoItem>();
			y_filteredposts.addAll(y_posts);
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
				card=view.findViewById(R.id.card);
				bname=view.findViewById(R.id.bname);

				view.setOnClickListener(this);
			}
		}
	}
	void gotoBDActivity(String thumbnail,String bname,String wname,String link,String category ){
		if(isOnline()){
			online=true;

			Intent i=new Intent(EbookEBookIndexFeedActivity.this,EbookEBookDetailActivity.class);         
			i.putExtra("thumbnail",thumbnail);
            i.putExtra("bname",bname);
			i.putExtra("wname",wname);
            i.putExtra("link",link);
			//   i.putExtra("category",category);
			i.putExtra("mb1",mb1);
			i.putExtra("mb2",mb2);
			i.putExtra("time",time);
			startActivity(i);
		}else{
			online=false;

			showNoInternet();

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
			// processTitleImg(getFromPrefs(currentLink));
			//processRecent(getFromPrefs(currentLink),"recent");
			//	recent_adapter=new RecentAdapter();
			//recent_rv.setAdapter(recent_adapter);
			//    processSuggested(getFromPrefs(currentLink),"suggested");
			//   suggested_adapter=new SuggestedAdapter();
			//     suggested_rv.setAdapter(suggested_adapter);
			//   processChild(getFromPrefs(currentLink),"child");
			//     child_adapter=new ChildAdapter();
			//    child_rv.setAdapter(child_adapter);

			processYoung(getFromPrefs(currentLink),"books");
			young_adapter=new YoungAdapter();
			young_rv.setAdapter(young_adapter);

		}catch(Exception e){
			Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
		}

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
	public void gotoFbAccount()
	{
		final String urlFb = "fb://profile/"+"100029607351728";

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(urlFb));

		// If a Facebook app is installed, use it. Otherwise, launch
		// a browser

		final PackageManager packageManager = getPackageManager();

		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
																	  PackageManager.MATCH_DEFAULT_ONLY);

		if (list.size() == 0) {
			final String urlBrowser = "https://www.facebook.com/techkipon";
			intent.setData(Uri.parse(urlBrowser));
		}

		startActivity(intent);
	}
	private void showNoInternet (){
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		View parent_view = LayoutInflater.from(this).inflate(R.layout.ebook_one_button_dia, null);
		dialog.setView(parent_view);
		dialog.setCancelable(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.show();
		ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
		TextView tv_title = (TextView) dialog.findViewById(R.id.title);
		TextView tv_message = (TextView) dialog.findViewById(R.id.message);
		final Button cancel= (Button) dialog.findViewById(R.id.bt1);
		iv.setImageResource(R.drawable.ebook_no_internet_icon);
		tv_title.setText("အင်တာနက်မရှိခ​ြင်း");
		tv_message.setText("မင်္ဂလာပါ....စာဖတ်ပရိတ်သတ်​ရ​ေ။ စာအုပ်များ ဖတ်ရှုနိုင်ရန် အင်တာနက်ဖွင့်ထားရန် လိုအပ်ပါတယ်။");
		cancel.setText("ဟုတ်​ပ​ြီ");
		cancel.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{

					dialog.dismiss();
				}});


	}
}
	/*void loadInter(){
		MobileAds.initialize(IndexFeedActivity2.this); 
		AdRequest adIRequest = new AdRequest.Builder().build(); 

		// Prepare the Interstitial Ad Activity 
		interstitial = new InterstitialAd(IndexFeedActivity2.this); 

		// Insert the Ad Unit ID 
		interstitial.setAdUnitId(getString(R.string.ebook_admob_interstitial_id)); 

		// Interstitial Ad load Request 
		interstitial.loadAd(adIRequest); 

		// Prepare an Interstitial Ad Listener 
		interstitial.setAdListener(new AdListener()  
			{ 
				public void onAdLoaded() 
				{ 
					if (interstitial.isLoaded()) { 
						interstitial.show(); 
					} 

				} 
			});
	}
}
	
*/
