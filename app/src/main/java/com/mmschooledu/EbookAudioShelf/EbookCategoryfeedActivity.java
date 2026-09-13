package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;



import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View.OnClickListener;

import java.io.IOException;

import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.jsoup.*;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.startapp.sdk.adsbase.StartAppAd;


public abstract class EbookCategoryfeedActivity extends AppCompatActivity
{private InterstitialAd interstitial;
	private AdView adView;   
	
	//private InterstitialAd interstitialAd;
	//private String TAG = "Home";
    public abstract void processJson(String jsonString,String category);
    public abstract boolean useGridLayout();
    public abstract String getFeedAddress();
    public abstract void _Options_Menu_Click(MenuItem item);
    private SwipeRefreshLayout mSwipeLayout;
    RecyclerView rv;
    FeedAdapter adapter;
    List<EbookCategoryItem> posts,filteredposts;
    boolean online=true;
    Toolbar tb;
	String link="https://www.mmbookdownload.com/category.html";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		//AudienceNetworkAds.initialize(this);
        super.onCreate(savedInstanceState);
		//    StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

        setContentView(R.layout.ebook_activity_more_setion);
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

        posts=new ArrayList<EbookCategoryItem>();
        filteredposts=new ArrayList<EbookCategoryItem>();
        rv=(RecyclerView)findViewById(R.id.recyclerview);
        if(useGridLayout()){
            rv.setLayoutManager(new GridLayoutManager(this,1));
        }else{
            rv.setLayoutManager(new LinearLayoutManager(this));
        }

        getSupportActionBar().setTitle("စာအုပ်အမျိုးအစား");
		refresh();

    }

	
	
    public void refresh(){

        tb.collapseActionView();
        if(isOnline()){
            online=true;
            try{
				mSwipeLayout.setRefreshing(true); 
                new WebScrapeTask().execute(link);

            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Can't be used Data!",Toast.LENGTH_SHORT).show();
            }   


        }else{
            online=false;


            try{
                processJson(getFromPrefs(""),"");
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
        editor.putString("",result);
        editor.commit();
    }

    public String getFromPrefs(String key){

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String lastData= sharedPreferences.getString("","");
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
        getMenuInflater().inflate(R.menu.ebook_category_menu,menu);
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
            finish();
            return true;
        }
        if(item.getItemId()==R.id.menu_share){
            shareText("https://play.google.com/store/apps/details?id="+getPackageName(),"Share App");
		}
        else{
            _Options_Menu_Click(item);
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
    public void addItem(EbookCategoryItem item){
        posts.add(item);
    }

    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>
    {
        @Override
        public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
        {
            View v=getLayoutInflater().inflate(R.layout.ebook_category_item,p1,false);
            return new ViewHolder(v);
        }

        public void filter(String charText){
            charText =charText.toLowerCase();
            filteredposts.clear();
            if (charText.length()==0){
                filteredposts.addAll(posts);
            }else{
                for (EbookCategoryItem pi : posts){
                    if(translate(pi.title).toLowerCase().contains(charText))
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

            p1.bname.setText(translate(filteredposts.get(p2).title));
			p1.count.setText(filteredposts.get(p2).count);

			p1.card.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1) {
                        if(isOnline()){
                            online=true;

                            Intent i=new Intent(EbookCategoryfeedActivity.this,EbookBookActivity.class);         

							i.putExtra("title",translate(filteredposts.get(p2).title));
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


            CardView card;
            TextView bname,count;
			//AdView adview;
            @Override
            public void onClick(View view)
            {


            }
            public ViewHolder(View view)
            {
                super(view);

                bname=view.findViewById(R.id.bcat_tv);
				count=view.findViewById(R.id.bcount_tv);
				card=view.findViewById(R.id.cat_card);
				//adview=view.findViewById(R.id.adView);

                view.setOnClickListener(this);
            }
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
			if(result.contains("title")){
				result=result.replace("},\n]","}\n]");
				processJson(result,"");
				adapter=new FeedAdapter();
				rv.setAdapter(adapter);

			}


		}

		public String process(Document doc){

			String res="[";
			try {
				try{
					doc.selectFirst("div.panel-group").remove();
				}catch( Exception e)	
				{

				}

				Element e=doc.selectFirst("table");
				Elements catE=e.select("td");
				for (Element element : catE){
					String title = element.selectFirst("a h5").text();
					String count = element.selectFirst("span").text();
					title=title.replace(count,"");
					String link = element.selectFirst("a").attr("href");

					res+="{\n";
					res+="\"title\":\""+title+"\",\n";
					res+="\"count\":\""+count+"\",\n";
					res+="\"link\":\""+"https://www.mmbookdownload.com/"+link+"\"},\n";


				}
			} catch (Exception e) {

				Toast.makeText(getApplicationContext(),"စာပ​ေအမျိုးအစားများ မရယူနိုင်ခ​ဲ့ပါ",Toast.LENGTH_SHORT).show();

			}

			mSwipeLayout.setRefreshing(false); 
			res=EbookFontConverter.zg2uni(res);
			return res+"]";
		}
	}
	String translate(String text){
		String ted="";
		ted=text
			.replace("Agricultural","စိုက်ပျိုးရေး")
			.replace("Astrology","​​ဗေဒင်")
			.replace("Business","စီးပွားရေး")
			.replace("Comic","ရုပ်ပြ")
			.replace("Detective","စုံ​ထောက်")
			.replace("Dictionary","အဘိဓာန်")
			.replace("English","အင်္ဂလိပ်")
			.replace("Fun","ဟာသ")
			.replace("Health","ကျန်းမာ​ရေး")
			.replace("History","သမိုင်း")
			.replace("Knowledge","သုတ/ရသ")
			.replace("EbookLanguage","ဘာသာစကား")
			.replace("Magazine","မဂ္ဂဇင်း")
			.replace("Martial-Art","သိုင်းပညာ")
			.replace("Musical","ဂီတ")
			.replace("Mystery","လျှို့ဝက်ဆန်းကြယ်")
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
