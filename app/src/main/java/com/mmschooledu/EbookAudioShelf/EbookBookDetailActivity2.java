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
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.*;
//import com.bumptech.glide.Glide;
import android.view.View.OnClickListener;
import java.io.File;

import java.io.IOException;
import java.util.regex.*;
import org.jsoup.select.*;
import org.jsoup.nodes.*;
import org.jsoup.*;
import android.webkit.*;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.interstitial.InterstitialAd;


public abstract class EbookBookDetailActivity2 extends AppCompatActivity
{
	//private InterstitialAd interstitialAd;
	//private String TAG = "BookDetailActivity";
	
	private InterstitialAd interstitial;
	private AdView adView;     
	//AdView adView;
	//InterstitialAd interstitialAd;
    public abstract void processJson(String jsonString,String category);
    public abstract boolean useGridLayout();
    public abstract String getFeedAddress();
    public abstract void _Options_Menu_Click(MenuItem item);
    private SwipeRefreshLayout mSwipeLayout;
    RecyclerView recent_rv;
	RecentAdapter recent_adapter;
	List<EbookBItem> r_posts,r_filteredposts;   
    boolean online=true;
    Toolbar tb;
    String currentLink="";
   final String lang="mm";
 String s_bname,s_bimg,s_wname,s_wlink,s_link;
    
    TextView bname,wname,bview,binfo,bcat,support_tv;
    ImageView title_bg_img,title_img;
	String download_link = "";
	String ug,cd,mt;
	CardView author_card,cat_card,download_card,downloadother_card,view_card;
      Button author_bt;
	  
	//AdView adview0;
	//AdRequest req0;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		//AudienceNetworkAds.initialize(this);

        super.onCreate(savedInstanceState);
	
        setContentView(R.layout.ebook_ebactivity_book_detail);
		  
        tb=(Toolbar)findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
	
		//adView = (AdView) 
			//findViewById(R.id.ad_view);           
		AdRequest adRequest = new AdRequest.Builder().build();           
		//adView.loadAd(adRequest); 


		
		//adview0=(AdView) findViewById(R.id.adView); 
		view_card=(CardView) findViewById(R.id.view_card);
		
		author_card=(CardView) findViewById(R.id.author_card);
		 download_card=(CardView) findViewById(R.id.download_card);
		  downloadother_card=(CardView) findViewById(R.id.open_card);
		cat_card=(CardView) findViewById(R.id.cat_card);
		
		author_bt=(Button) findViewById(R.id.author_bt);
		
         support_tv=(TextView) findViewById(R.id.support_tv);
        bname=(TextView)findViewById(R.id.bname);
        wname=(TextView)findViewById(R.id.wname);
	  bview=(TextView)findViewById(R.id.bview);
        binfo=(TextView)findViewById(R.id.binfo);
	bcat=(TextView)findViewById(R.id.bcat);
		
		title_bg_img=(ImageView)findViewById(R.id.title_bg_img);
        title_img=(ImageView)findViewById(R.id.title_img);
     
		//1382006192270567_1382010002270186
		
		
		/*
		adView = (AdView)findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);


		MobileAds.initialize(this,"2481530783933136~4448320787");
		final InterstitialAd interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId("ca-app-pub-2481530783933136/4448320787");
		interstitialAd.loadAd(new AdRequest.Builder().build());
		interstitialAd.setAdListener(new AdListener()
			{

				@Override
				public void onAdLoaded(){

					interstitialAd.show();

				}});
		
		*/
		
		
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

	
			
        r_posts=new ArrayList<EbookBItem>();
		r_filteredposts=new ArrayList<EbookBItem>();

		recent_rv=(RecyclerView)findViewById(R.id.recent_rv);
		horizontalRV(recent_rv);
	try{
      s_bname=getIntent().getExtras()
            .get("bname").toString();
        s_wname=getIntent().getExtras()
            .get("wname").toString();
		  s_wlink=getIntent().getExtras()
			  .get("wlink").toString();
		
        wname.setText(s_wname.replace("Author","").replace(":",""));

		author_card.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					goBookActivity(s_wname,s_wlink);
					// TODO: Implement this method
				}
			});
		author_bt.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					goBookActivity(s_wname,s_wlink);
					// TODO: Implement this method
				}
			});
}catch(Exception e){
	
}
	s_bimg=getIntent().getExtras()
			.get("bimg").toString();
        s_link=getIntent().getExtras()
            .get("link").toString();
		bname.setText(s_bimg);
		cat_card.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Intent i=new Intent(EbookBookDetailActivity2.this,EbookCategoryActivity.class);         
					startActivity(i);
					// TODO: Implement this method
				}
			});
			
			
   
		  
		  support_tv.setText(getString(R.string.ebook_support_content));
		support_tv.setSelected(true);
		getSupportActionBar().setTitle("MyanmarSchoolEducation" );
        getSupportActionBar().setSubtitle(s_bimg);
        
	
        
		    currentLink=getFeedAddress();
		//    loadBanner();
		    refresh();
		  
		 
    }

	/*void loadBanner()  {
        req0= new AdRequest.Builder().build();
        adview0.loadAd(req0); 

	
	}
	void showInter(){
		final InterstitialAd interstitial;
		MobileAds.initialize(BookDetailActivity.this); 
		AdRequest adIRequest = new AdRequest.Builder().build(); 
		/*final ProgressDialog progressDialog  = new ProgressDialog(EbookIndexFeedActivity.this);

		 progressDialog.setMessage("Loading...");
		 progressDialog.setCancelable(false);
		 progressDialog.show();
		 */

		// Prepare the Interstitial Ad Activity 
	/*	interstitial = new InterstitialAd(BookDetailActivity.this); 

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
						//progressDialog.dismiss();
					} 

				} 
				public void onAdFailedToLoad(int it) 
				{ 
					super.onAdFailedToLoad(it);
					//progressDialog.dismiss();
					//Toast.makeText(getApplicationContext(),"Ad Failled",Toast.LENGTH_SHORT).show();

				} 
			});

	}*/
	void horizontalRV(RecyclerView rv){
        LinearLayoutManager layoutManager
            = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(layoutManager);
        ViewCompat.setNestedScrollingEnabled(rv, false);
    }
    public void refresh(){
	currentLink=getFeedAddress();     
        if(isOnline()){
            online=true;
            try{
              //  new DownloadTask().execute(currentLink);
	  mSwipeLayout.setRefreshing(true); 
	  new WebScrapeTask().execute(s_link);
				
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Can't be used Data!",Toast.LENGTH_SHORT).show();
            }   


        }else{
            online=false;
            
        }
    }
	
	public void goBookActivity(String title,String category){

        Intent i=new Intent(EbookBookDetailActivity2.this,EbookBookActivity.class);         
        i.putExtra("title",title);
        i.putExtra("link",category);
        startActivity(i);

    }
    public void saveToPrefs(String key,String result){
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,result);
        editor.commit();
    }

    public String getFromPrefs(String key){

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String lastData= sharedPreferences.getString(key,"");
        return lastData;
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
        getMenuInflater().inflate(R.menu.ebook_book_menu,menu);
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
                    recent_adapter.filter(p1.toString());
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
            finish();
            return true;
        }
        if(item.getItemId()==R.id.menu_share){
            shareText(s_link.replace("www.mmbookdownload.com","www.ayetharyar.com"),s_bimg);
		}
			if(id==R.id.menu_report){
				//showToReport();
			}
        
        return super.onOptionsItemSelected(item);
    }
	public void shareText(String text,String message){
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, message);
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		startActivity(Intent.createChooser(sharingIntent, message));
	}
    private void showToReport(){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View parent_view = LayoutInflater.from(this).inflate(R.layout.ebook_two_button_dia, null);
        dialog.setView(parent_view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
        TextView tv_title = (TextView) dialog.findViewById(R.id.title);
        TextView tv_message = (TextView) dialog.findViewById(R.id.message);
        final Button call = (Button) dialog.findViewById(R.id.bt2);
        final Button cancel= (Button) dialog.findViewById(R.id.bt1);
        iv.setImageResource(R.drawable.ebook_report_icon);
        tv_title.setText("ရီပို့တင်မည်");
        tv_message.setText(getString(R.string.ebook_report_message));
        cancel.setText("မလုပ်တော့ပါ");
        cancel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View p1)
                {
					dialog.dismiss();
                }});
        call.setText("ဖုန်းဆက်သွယ်ရန်");
        call.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View p1)
                {

                    Intent call = new Intent(Intent.ACTION_DIAL);
                    call.setData(Uri.parse("tel:"+"09895411099996"));
                    startActivity(call); 
                    dialog.dismiss();
                }});

	}
	
    public void addItem(EbookBItem item){
		r_posts.add(item);
	}

	public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder>
	{
		@Override
		public RecentAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
		{
			View v=getLayoutInflater().inflate(R.layout.ebook_image_item,p1,false);
			return new ViewHolder(v);
		}

		public void filter(String charText){
			charText =charText.toLowerCase();
			r_filteredposts.clear();
			if (charText.length()==0){
				r_filteredposts.addAll(r_posts);
			}else{
				for (EbookBItem pi : r_posts){
					if(pi.bname.toLowerCase().contains(charText))
					{ 
						r_filteredposts.add(pi);
					}
				}
			}
			notifyDataSetChanged();
		}

		@Override
		public int getItemCount()
		{
            int bookCount=r_filteredposts.size();
			return bookCount;
		}

		@Override
		public void onBindViewHolder(RecentAdapter.ViewHolder p1,final int p2)
		{
			if((r_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
				Glide
                    .with(getApplicationContext())
                    .load(r_filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
                    .into(p1.iv);

			}else{
				p1.iv.setImageResource(R.drawable.ebook_loading_book);
			}
			if(r_filteredposts.get(p2).bname.equals("")){
				p1.tv.setText(r_filteredposts.get(p2).bname);
			}else{
				p1.tv.setText(r_filteredposts.get(p2).bimg);		
			}
			final String bimg=p1.tv.getText().toString();

            p1.card.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1) {
						//    gotoBDActivity(r_filteredposts.get(p2).thumbnail,r_filteredposts.get(p2).bname,r_filteredposts.get(p2).wname,r_filteredposts.get(p2).res,r_filteredposts.get(p2).category);
                        Intent i=new Intent(EbookBookDetailActivity2.this,EbookBDActivity.class);         
						i.putExtra("bname",r_filteredposts.get(p2).bname);
						i.putExtra("bimg",bimg);
						i.putExtra("wname",r_filteredposts.get(p2).wname);
						i.putExtra("wlink",r_filteredposts.get(p2).wlink);
						i.putExtra("link",r_filteredposts.get(p2).link);
						startActivity(i);
						finish();
                    }
                });
		}


		public EbookBItem getItem(int pos){
			return r_filteredposts.get(pos);
		}

		public void reset()
		{
			r_posts.clear();
			r_filteredposts.clear();
			notifyDataSetChanged();
		}

		public RecentAdapter()
		{
			super();
			r_filteredposts=new ArrayList<EbookBItem>();
			r_filteredposts.addAll(r_posts);
		}

		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
		{
			ImageView iv;
			CardView card;
			TextView tv;
			@Override
			public void onClick(View view)
			{


			}
			public ViewHolder(View view)
			{
				super(view);
				iv=view.findViewById(R.id.image);
				card=view.findViewById(R.id.card);
				tv=view.findViewById(R.id.bname);
				view.setOnClickListener(this);
			}
		}
	}
	
    
    private void downloadFile(String userAgent,String contentDisposition,String mimeType){
   
        try {
            
            String mBaseFolderPath = android.os.Environment
				.getExternalStorageDirectory()
				+ File.separator
				+ "MyanmarAudioBook" + File.separator;
			if (!new File(mBaseFolderPath).exists())
			{
				new File(mBaseFolderPath).mkdir();
			}

		String mFilePath = "file://" + mBaseFolderPath + "/" +s_bimg+ ".pdf";
		
		Uri downloadUri = Uri.parse(download_link);
            DownloadManager.Request req = new DownloadManager.Request(downloadUri);
			req.setMimeType(mimeType);
			//------------------------COOKIE!!------------------------
			String cookies = CookieManager.getInstance().getCookie(download_link);
			req.addRequestHeader("cookie", cookies);
			//------------------------COOKIE!!------------------------
			req.addRequestHeader("User-Agent", userAgent);
			req.setDescription("Downloading file...");
			req.setTitle(URLUtil.guessFileName(download_link, contentDisposition, mimeType));
			req.allowScanningByMediaScanner();
			req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			
		req.setDestinationUri(Uri.parse(mFilePath));
            req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            DownloadManager dm = (DownloadManager) getSystemService(getApplicationContext().DOWNLOAD_SERVICE);
			
            dm.enqueue(req);
            Toast.makeText(EbookBookDetailActivity2.this,"စတင်​​ဒေါင်းယူ​နေပါပြီ",0).show();
        } catch (Exception e) {
            Toast.makeText(EbookBookDetailActivity2.this,"ဒေါင်းယူမှု့မပြုလုပ်နိုင်ပါ"+e.toString(),0).show();
        }

    }
	Boolean checkFile(){
		Boolean exist=false;
		String mBaseFolderPath = android.os.Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "MyanmarAudioBook" + File.separator;
		if (!new File(mBaseFolderPath).exists())
		{
			new File(mBaseFolderPath).mkdir();
		}
		
		String mFilePath = "file://" + mBaseFolderPath + "/" +s_bimg+ ".pdf";
		if (!new File(mFilePath).exists())
		{
			exist=true;
			}
			
		return exist;
	}
	
    void alertToDownload(){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View parent_view = LayoutInflater.from(this).inflate(R.layout.ebook_download_dia2, null);
        dialog.setView(parent_view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
        TextView tv_title = (TextView) dialog.findViewById(R.id.title);
        TextView tv_message = (TextView) dialog.findViewById(R.id.message);
		final Button d = (Button) dialog.findViewById(R.id.d);
        final Button od= (Button) dialog.findViewById(R.id.od);
		
        final Button cp = (Button) dialog.findViewById(R.id.cp);
        final Button cc= (Button) dialog.findViewById(R.id.cc);
		
		iv.setImageResource(R.drawable.ebook_ic_download);
		
        tv_title.setText("စာအုပ်​ဒေါင်းမည်");
		tv_message.setText( "စာ​ရေးဆရာ :"+s_wname+"\nစာအုပ်အမည် :"+s_bimg+"\nစာအုပ်ကို ​ဒေါင်းယူမှာ​သေချာပြီလား");
        

        
        d.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View p1)
                {
				
			downloadFile(ug,cd,mt);              
                    dialog.dismiss();
                }});
		od.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View p1)
                {
					try{
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(download_link));
						startActivity(intent);
					}catch(Exception e){
						Toast.makeText(EbookBookDetailActivity2.this,e.getMessage(),0).show();

					}
					
											 dialog.dismiss();
                }});
				
		cp.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View p1)
                {
					setClipboard(getApplicationContext(),s_link.replace("www.mmbookdownload.com","www.ayetharyar.com"));
					dialog.dismiss();
                }});
				
		cc.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View p1)
                {
					dialog.dismiss();
                }});
    }
	private void setClipboard(Context context, String text) {
		if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(text);
			Toast.makeText(EbookBookDetailActivity2.this,"လင့် ကူးယူပြီးပါပြီ",0).show();



		} else {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
			clipboard.setPrimaryClip(clip);
			Toast.makeText(EbookBookDetailActivity2.this,"လင့် ကူးယူပြီးပါပြီ",0).show();

		}
	}
	public class WebScrapeTask extends AsyncTask<String,Void,String>
	{
		@Override
		protected String doInBackground(String[] p1)
		{

			try
			{
				Document doc = (Document) Jsoup.connect(p1[0]).get();
				return process(doc);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return "";
			}
		}

		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);
			String thumbnail="";
			String b_cat="";
			String b_detail="";
			
			try
			{
				JSONObject jo=new JSONObject(result);
			thumbnail=jo.getString("thumbnail");
			b_cat=jo.getString("bc");
			b_detail=jo.getString("bd");
			final String b_link=jo.getString("blink");
			Glide
					.with(getApplicationContext())
					.load(thumbnail).placeholder(R.drawable.ebook_loading_book)
					.into(title_bg_img);
				Glide
					.with(getApplicationContext())
					.load(thumbnail).placeholder(R.drawable.ebook_loading_book)
					.into(title_img);
			
				String info=b_detail;
				String date=info.split("View")[0];
				String view=info.split(":")[2];
				String size=info.split(":")[3];
				binfo.setText((date+"/"+size).replace(":",""));
				bview.setText(view);
				bcat.setText(translate(b_cat));
				binfo.setSelected(true);
				bcat.setSelected(true);
				
				WebView wv = (WebView) findViewById(R.id.wv);
				wv.getSettings().setJavaScriptEnabled(true);
				wv.setWebViewClient(new WebViewClient());
				wv.setDownloadListener(new DownloadListener(){

						@Override
						public void onDownloadStart(String url, String userAgent, String 
													contentDisposition, String mimeType, long contentLength) {
							
							download_link=url;
							ug=userAgent;
							cd=contentDisposition;
						         mt=mimeType;
							download_card.setOnClickListener(new OnClickListener(){

									@Override
									public void onClick(View p1) {
										/*final InterstitialAd interstitial;
										MobileAds.initialize(BookDetailActivity.this); 
										AdRequest adIRequest = new AdRequest.Builder().build(); 
										final ProgressDialog progressDialog  = new ProgressDialog(BookDetailActivity.this);

										progressDialog.setMessage("Loading...");
										progressDialog.setCancelable(false);
										progressDialog.show();


										// Prepare the Interstitial Ad Activity 
										interstitial = new InterstitialAd(BookDetailActivity.this); 

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
														progressDialog.dismiss();
													}*/ 
													alertToDownload();
												} 
												public void onAdFailedToLoad(int it) 
												{ 
												//	super.onAdFailedToLoad(it);
											//		progressDialog.dismiss();
													//Toast.makeText(getApplicationContext(),"Ad Failled",Toast.LENGTH_SHORT).show();
													alertToDownload();
												} 
											});
										
										
										
							// TODO: Implement this method
						}
							
					});
					
					
					
					
				
					
					//}});
				wv.loadUrl(b_link);
				String resultJson=getFromPrefs("recent");
				processJson(resultJson,"");	
                recent_adapter=new RecentAdapter();
                recent_rv.setAdapter(recent_adapter); 
				
				downloadother_card.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View p1) {
							
						//final InterstitialAd interstitial;
							//MobileAds.initialize(BookDetailActivity.this); 
						//	AdRequest adIRequest = new AdRequest.Builder().build(); 
							//final ProgressDialog progressDialog  = new ProgressDialog(BookDetailActivity.this);

							//progressDialog.setMessage("Loading...");
						//	progressDialog.setCancelable(false);
							//progressDialog.show();


							// Prepare the Interstitial Ad Activity 
						//	interstitial = new InterstitialAd(BookDetailActivity.this); 

							// Insert the Ad Unit ID 
							//interstitial.setAdUnitId(getString(R.string.ebook_admob_interstitial_id)); 

							// Interstitial Ad load Request 
							//interstitial.loadAd(adIRequest); 

							// Prepare an Interstitial Ad Listener 
							//interstitial.setAdListener(new AdListener()  
							//	{ 
									//public void onAdLoaded() 
									//{ 

										//if (interstitial.isLoaded()) { 
										//	interstitial.show(); 
											//progressDialog.dismiss();
										//} 
										try{
											Intent intent = new Intent(Intent.ACTION_VIEW);
											intent.setData(Uri.parse(download_link));
											startActivity(intent);
										}catch(Exception e){
											Toast.makeText(EbookBookDetailActivity2.this,e.getMessage(),0).show();

									//	}
									} 
								//	public void onAdFailedToLoad(int it) 
									//{ 
										//super.onAdFailedToLoad(it);
										//progressDialog.dismiss();
										//Toast.makeText(getApplicationContext(),"Ad Failled",Toast.LENGTH_SHORT).show();
										try{
											Intent intent = new Intent(Intent.ACTION_VIEW);
										intent.setData(Uri.parse(download_link));
											startActivity(intent);
										}catch(Exception e){
											Toast.makeText(EbookBookDetailActivity2.this,e.getMessage(),0).show();

										}
									} 
								});

							
				//}
					//});
					
				
					}
					
			catch (JSONException e)
			{
				Toast.makeText(EbookBookDetailActivity2.this,e.getMessage(),0).show();
				
			}

			
			
			//ug=userAgent;
					//	cd=contentDisposition;
						      //   mt=mimeType;
			view_card.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1) {
			
						Intent intent = new Intent(Intent.ACTION_VIEW);
						//String link = null;
						intent.setData(Uri.parse(download_link));
						startActivity(intent);
						/*Intent i=new Intent(BookDetailActivity.this,ViewerActivity2.class);         
						//i.putExtra("title",title);
						//i.putExtra("link",download_link);
						startActivity(i);*/
						
						/*Intent i=new Intent(BookDetailActivity.this,ViewerActivity2.class);         
						i.putExtra("link",download_link);
						startActivity(i);*/
						
						/*Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.addCategory(Intent.CATEGORY_BROWSABLE);
						intent.setData(Uri.parse("link"));
						startActivity(intent);**/

		
					//alertToDownload();
				} 
		//public void onAdFailedToLoad(int it) 
		//{ 
			//	super.onAdFailedToLoad(it);
			//		progressDialog.dismiss();
			//Toast.makeText(getApplicationContext(),"Ad Failled",Toast.LENGTH_SHORT).show();
			//alertToDownload();
		//} 
	});



	// TODO: Implement this method
	}

	
	
		
		
		
		
		public String process(Document doc){

			String res="";
			
			try {
				 
				Element bd=doc.select("div.row").get(2);
				String b_thumbnail=bd.select("div img").attr("src");
				Elements bge=bd.select("a.badge");
			       String b_cat=bge.text();
				
				Element ie=bd.selectFirst("div.post-details");
				String post_detail=ie.selectFirst("ul").text();
				
				Element ble=bd.selectFirst("div.footer-social2");
				String b_link=ble.selectFirst("li a").attr("href");
				
				
				res+="{\n";
				res+="\"thumbnail\":\""+b_thumbnail+"\",\n";
				res+="\"bc\":\""+b_cat+"\",\n";
				res+="\"bd\":\""+post_detail+"\",\n";
				res+="\"blink\":\""+"https://www.mmbookdownload.com/"+b_link+"\"}";
				
				try{
					doc.selectFirst("div.panel-group").remove();
				}catch( Exception e)	
				{

					
				}
			} catch (Exception e) {
				
			}

			mSwipeLayout.setRefreshing(false); 
			res=EbookFontConverter.zg2uni(res);
			return res;
		}
	}
	public static String unescapeUnicode(String str) {
        StringBuffer b = new StringBuffer();
        Matcher m = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(str);
        while (m.find())
            b.append((char) Integer.parseInt(m.group(1), 16));
        return b.toString();
    }
	String translate(String text){
		String ted="";
		ted=text
			.replace("Agricultural","စိုက်ပျိုးရေး")
			.replace("Astrology","ဗေဒင်")
			.replace("Business","စီးပွားရ​ေး")
			.replace("Comic","ရုပ်ပြ")
			.replace("Detective","စုံထ​ောက်")
			.replace("Dictionary","အဘိဓာန်")
			.replace("English","အင်္ဂလိပ်")
			.replace("Fun","ဟာသ")
			.replace("Health","ကျန်းမာ​ရ​ေး")
			.replace("History","သမိုင်း")
			.replace("Knowledge","သုတ/ရသ")
			.replace("EbookLanguage","ဘာသာစကား")
			.replace("Magazine","မဂ္ဂဇင်း")
			.replace("Martial-Art","သိုင်းပညာ")
			.replace("Musical","ဂီတ")
			.replace("Mystery","လှျို့ဝှက် ဆန်းကြယ်")
			.replace("Novels","ဝတ္ထုများ")
			.replace("Other","အ​ခြား")
			.replace("Poem","က​ဗျာ")
			.replace("Politics","နိုင်ငံရေး")
			.replace("Rare","ရှားပါးစာအုပ်များ")
			.replace("Religion","ဘာသာရေး")
			.replace("Technical","နည်းပညာ")
			.replace("Thriller","သည်းထိတ်ရင်ဖို")
			.replace("Translation","ဘာသာပြန်")
			;
		return ted;
	}
}
