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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View.OnClickListener;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;

public abstract class EbookTvIndexCategories extends AppCompatActivity
{

	public abstract void processJson(String jsonString,String category);
    public abstract boolean useGridLayout();
    public abstract String getFeedAddress();
    public abstract void _Options_Menu_Click(MenuItem item);
    private SwipeRefreshLayout mSwipeLayout;
    RecyclerView rv;
    FeedAdapter adapter;
    List<EbookBookItem> posts,filteredposts;
    boolean online=true;
    Toolbar tb;
    String currentLink="";
    final int FONT_ZAWGYI=1;
    final int FONT_UNI=2;
    final int FONT_NONE=0;
    int currentFont=FONT_NONE;
    String title,bname,category;
    AdView adview;
	AdRequest req;

    int itemClick=0;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.ebook_activity_book);

		//title = getIntent().getStringExtra("title");
		//getSupportActionBar().setTitle(title+"");


        tb=(Toolbar)findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	//	adview=(AdView) findViewById(R.id.adView); 

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
            R.color.ebook_refresh_progress_1,
            R.color.ebook_refresh_progress_2,
            R.color.ebook_refresh_progress_3); 

        posts=new ArrayList<EbookBookItem>();
        filteredposts=new ArrayList<EbookBookItem>();
        rv=(RecyclerView)findViewById(R.id.recyclerview);
        if (useGridLayout()) {
            rv.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(this, 3));
        } else {
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


   /* @Override
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
    } */

    public void refresh(){
        //adapter.reset();
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
        String lastData= sharedPreferences.getString(currentLink,category);
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
		getMenuInflater().inflate(R.menu.ebook_main_menu1,menu);
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



    public void addItem(EbookBookItem item){
        posts.add(item);
    }

    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>
    {
        @Override
        public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
        {
            View v=getLayoutInflater().inflate(R.layout.ebook_book_item,p1,false);
            return new ViewHolder(v);
        }

        public void filter(String charText){
            charText =charText.toLowerCase();
            filteredposts.clear();
            if (charText.length()==0){
                filteredposts.addAll(posts);
            }else{
                for (EbookBookItem pi : posts){
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
                    .load(filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
                    .into(p1.iv);

            }else{
                p1.iv.setImageResource(R.drawable.ebook_loading_book);
            }
            p1.bname.setText(filteredposts.get(p2).bname);
            p1.card.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1) {
                        if(isOnline()){
                            online=true;

                            itemClick++;
                            if(itemClick==3){
                                itemClick=0;
                               // loadInter();
                            }

                            Intent i=new Intent(EbookTvIndexCategories.this,EbookTvPlayer.class);         
							/*  i.putExtra("thumbnailUrl",filteredposts.get(p2).thumbnail);
							 i.putExtra("bname",filteredposts.get(p2).bname);
							 i.putExtra("wname",filteredposts.get(p2).wname);
							 i.putExtra("link",filteredposts.get(p2).link);
							 i.putExtra("category",filteredposts.get(p2).category);*/

							i.putExtra("thumbnail",filteredposts.get(p2).thumbnail);
							i.putExtra("bname",filteredposts.get(p2).bname);
							i.putExtra("wname",filteredposts.get(p2).wname);
							i.putExtra("link",filteredposts.get(p2).link);
							i.putExtra("category",filteredposts.get(p2).category);
							i.putExtra("mb1",filteredposts.get(p2).mb1);
							i.putExtra("mb2",filteredposts.get(p2).mb2);
							i.putExtra("time",filteredposts.get(p2).time);


                            startActivity(i);
                        }else{
                            online=false;



                        }
                    }
                });
        }

        public EbookBookItem getItem(int pos){
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
            filteredposts=new ArrayList<EbookBookItem>();
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
            processJson(getFromPrefs(currentLink),category);
            adapter=new FeedAdapter();
            rv.setAdapter(adapter);

        }catch(Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
/*
    void loadInter(){
        MobileAds.initialize(EbookTvIndexCategories.this); 
        AdRequest adIRequest = new AdRequest.Builder().build(); 

        // Prepare the Interstitial Ad Activity 
        final  InterstitialAd    interstitial = new InterstitialAd(EbookTvIndexCategories.this); 

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
                    // Call displayInterstitial() function when the Ad loads 

                } 
            });
    }
*/


}
