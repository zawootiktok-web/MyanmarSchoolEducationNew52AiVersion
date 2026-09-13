package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;


import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View.OnClickListener;

import java.io.IOException;

import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.*;
//import com.google.android.gms.ads.*;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.startapp.sdk.adsbase.StartAppAd;

public abstract class EbookWriterfeedActivity extends AppCompatActivity
{
	private InterstitialAd interstitial;
	private AdView adView;    
	

    public abstract void processJson(String jsonString,String category);
    public abstract boolean useGridLayout();
    public abstract String getFeedAddress();
    public abstract void _Options_Menu_Click(MenuItem item);
    private SwipeRefreshLayout mSwipeLayout;
    RecyclerView rv;
    FeedAdapter adapter;
    List<EbookWriterItem> posts,filteredposts;
    boolean online=true;
    Toolbar tb;
    String currentLink="";
	final String lang="mm";
    String link="https://www.mmbookdownload.com/author.html";

	TextView position_tv;
	Button choose_bt;
	LinearLayout position_l;

	int current_p=0;
	int start_p=0;
	int final_p=34;
	String[] names={
		"က",
		"ခ",
		"ဂ",
		"င",
		"စ",
		"ဆ",
		"ဇ",
		"ည",
		"ဌ",
		"ဎ",
		"တ",
		"ထ",
		"ဒ",
		"ဓ",
		"န",
		"ပ",
		"ဖ",
		"​ဗ",
		"ဘ",
		"မ",
		"ယ",
		"ရ",
		"လ",
		"ဝ",
		"သ",
		"ဟ",
		"အ",
		"ဥ",
		"ဧ"
	};
	Document doc;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
		//StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

        setContentView(R.layout.ebook_activity_writer);
        tb=(Toolbar)findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    
		/*adView = (AdView)findViewById(R.id.adView);
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

				}});*/
		
		
				adView = (AdView) 
			findViewById(R.id.ad_view);           
		AdRequest adRequest = new AdRequest.Builder().build();           
		adView.loadAd(adRequest); 

				
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout); 
		choose_bt=(Button) findViewById(R.id.choose_bt);
		position_tv=(TextView) findViewById(R.id.position_tv);
		position_l=(LinearLayout) findViewById(R.id.position_l);

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

        posts=new ArrayList<EbookWriterItem>();
        filteredposts=new ArrayList<EbookWriterItem>();
        rv=(RecyclerView)findViewById(R.id.recyclerview);
        if (useGridLayout()) {
            rv.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(this, 1));
        } else {
            rv.setLayoutManager(new LinearLayoutManager(this));
        }

        getSupportActionBar().setTitle("စာရေးဆရာများ");
		currentLink=getFeedAddress();   
		refresh();

    }




	

    public void refresh(){

        currentLink=getFeedAddress();
        tb.collapseActionView();
        if(isOnline()){
            online=true;
            try{
				mSwipeLayout.setRefreshing(true); 
				position_tv.setText("`"+names[current_p]+"´ အက္ခရာ စာရေးဆရာများ");		
				new WebScrapeTask().execute(link);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Can't be used Data!",Toast.LENGTH_SHORT).show();
            }   


        }else{
            online=false;
            try{
                processJson(getFromPrefs(currentLink),"");
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
            processJson(result,"");
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
        getMenuInflater().inflate(R.menu.ebook_writer_menu,menu);
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
		if(id==R.id.menu_share){
			shareText("https://play.google.com/store/apps/details?id="+getPackageName(),"Share App");
		}

        else{
            _Options_Menu_Click(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void addItem(EbookWriterItem item){
        posts.add(item);
    }

    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>
    {
        @Override
        public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
        {
            View v=getLayoutInflater().inflate(R.layout.ebook_writer_item,p1,false);
            return new ViewHolder(v);
        }

        public void filter(String charText){
            charText =charText.toLowerCase();
            filteredposts.clear();
            if (charText.length()==0){
                filteredposts.addAll(posts);
            }else{
                for (EbookWriterItem pi : posts){
                    if(pi.link.toLowerCase().contains(charText))
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
        public void onBindViewHolder(FeedAdapter.ViewHolder p1,final int p2) 
        {
			if((filteredposts.get(p2).img.length()>5)&&(online)){
				if(filteredposts.get(p2).img.contains("doublecolum")){
					p1.iv.getLayoutParams().height = 53;
				}else{
					p1.iv.getLayoutParams().height = 35;
				}
				Glide
                    .with(getApplicationContext())
                    .load((filteredposts.get(p2).img).replace("doublecolum","\""))
                    .into(p1.iv);
			}else{
			}
            p1.tv.setText((p2+1)+"");
            p1.card.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1) {
                        if(isOnline()){
                            online=true;

                            Intent i=new Intent(EbookWriterfeedActivity.this,EbookEBookActivity.class);         
							i.putExtra("title","စာရေးဆရာ-"+(p2+1));
                            i.putExtra("link",filteredposts.get(p2).link);
                            startActivity(i);
                            StartAppAd.showAd(getBaseContext());

                        }else{
                            online=false;



                        }
                    }
                });

			/*if (p2%5==1 || p2 ==1){
				p1.adview.setVisibility(View.VISIBLE);
				AdRequest req= new AdRequest.Builder().build();	
				p1.adview.loadAd(req); 
			}*/
        }

        public EbookWriterItem getItem(int pos){
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
            filteredposts=new ArrayList<EbookWriterItem>();
            filteredposts.addAll(posts);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {


            CardView card;
            TextView tv;
			ImageView iv;
			//AdView adview;
            @Override
            public void onClick(View view)
            {


            }
            public ViewHolder(View view)
            {
                super(view);

                tv=view.findViewById(R.id.wp_tv);
				iv=view.findViewById(R.id.w_iv);
                card=view.findViewById(R.id.w_card);
				//adview=view.findViewById(R.id.adView);

                view.setOnClickListener(this);
            }
        }
    }
	public void shareText(String text,String message){
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, message);
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
		startActivity(Intent.createChooser(sharingIntent, message));
	}
	public class WebScrapeTask extends AsyncTask<String,Void,String>
	{
		@Override
		protected String doInBackground(String[] p1)
		{

			try
			{
				doc = (Document) Jsoup.connect(p1[0]).get();
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
			result=result.replace("},\n]","}\n]");
			processJson(result,"");
			adapter=new FeedAdapter();
			rv.setAdapter(adapter);
			position_l.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						showNames();
					}
				});
			choose_bt.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						showNames();
					}
				});
		}
		void showNames(){
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter(EbookWriterfeedActivity.this, android.R.layout.simple_list_item_1, names);	
			arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			// TODO: Implement this method

			new AlertDialog.Builder(EbookWriterfeedActivity.this)
				.setTitle("အက္ခရာ ရွေးချယ်ပါ")
				. setAdapter(arrayAdapter, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface p1, int position) {
						//handleListClick (position);
						if(!(current_p==position)){
							current_p=position;
							position_tv.setText("`"+names[current_p]+"´ အက္ခရာ စာရေးဆရာများ");

							String result=process(doc);
							result=result.replace("},\n]","}\n]");
							processJson(result,"");
							adapter=new FeedAdapter();
							rv.setAdapter(adapter);

						}

					}})
				. show(); 


		}

		public String process(Document doc){

			String res="[";
			try {

				Element e=doc.select("div.row").get(4);
				Element a1=e.select("table").get(current_p);
				Elements catE=a1.select("tr");
				for (Element element : catE){
					Element info=element.select("td").get(1);
					String img="";
					try{
						img = info.selectFirst("a img").attr("src");
					}catch (Exception ex){}
					String link = info.selectFirst("a").attr("href");

					if(img.contains("title.php")){
						img="https://www.mmbookdownload.com/"+img;
					}
					res+="{\n";
					res+="\"img\":\""+(img).replace("\"","doublecolum")+"\",\n";
					res+="\"link\":\""+"https://www.mmbookdownload.com/"+link+"\"},\n";


				}


			} catch (Exception e) {

				Toast.makeText(getApplicationContext(),"စာရေးဆရာ​ဒေတာများ မရယူနိုင်ခဲ့ပါ",Toast.LENGTH_SHORT).show();

			}

			mSwipeLayout.setRefreshing(false); 
			res=EbookFontConverter.zg2uni(res);
			return res+"]";
		}
	}

}
