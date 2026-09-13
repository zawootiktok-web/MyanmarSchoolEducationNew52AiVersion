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
import org.jsoup.*;
import org.jsoup.select.*;
import org.w3c.dom.Document;

import java.util.regex.*;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public abstract class EbookEBookfeedActivity extends AppCompatActivity
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
    List<EbookBItem> posts,filteredposts;
    boolean online=true;
    Toolbar tb;
    String title,link;

    FloatingActionButton fab;
	Button pre_bt,next_bt;
	TextView position_tv;
	LinearLayout position_l;
	int count=0;
	int org_pno=1;
	int first_pno=1;
	int last_pno=1;
	int current_pno=1;
	//link=url+current_pno;
	String url;
	Document doc;

	Boolean load=false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
		//    StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

        setContentView(R.layout.ebook_ebactivity_book);

        tb=(Toolbar)findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		//	adView = (AdView)findViewById(R.id.adView);
		//AdRequest adRequest = new AdRequest.Builder().build();
		//adView.loadAd(adRequest);

		/*
		 MobileAds.initialize(this,"3940256099942544~1033173712");
		 final InterstitialAd interstitialAd = new InterstitialAd(this);
		 interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
		 interstitialAd.loadAd(new AdRequest.Builder().build());
		 interstitialAd.setAdListener(new AdListener()
		 {

		 @Override
		 public void onAdLoaded(){

		 interstitialAd.show();

		 }});
		 */


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

		 }});
		 */
		adView = (AdView)
			findViewById(R.id.ad_view);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);




        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		fab=(FloatingActionButton)findViewById(R.id.nnl_fab);
		pre_bt=(Button) findViewById(R.id.pre_bt);
		next_bt=(Button) findViewById(R.id.next_bt);
		position_tv=(TextView) findViewById(R.id.position_tv);
		position_l=(LinearLayout) findViewById(R.id.position_l);

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

		posts=new ArrayList<EbookBItem>();
		filteredposts=new ArrayList<EbookBItem>();
		rv=(RecyclerView)findViewById(R.id.recyclerview);
		if(useGridLayout()){
			rv.setLayoutManager(new GridLayoutManager(this,1));
		}else{
			rv.setLayoutManager(new LinearLayoutManager(this));
		}
        final FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.nnl_fab);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 ||dy<0 && fab.isShown()) {
                        fab.hide();
                    }
                }
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        fab.show();
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
		title=getIntent().getExtras()
			.get("title").toString();
		link=getIntent().getExtras()
			.get("link").toString();

		getSupportActionBar().setTitle("Online Ebook Libary");
		getSupportActionBar().setSubtitle(title);

		refresh();

        fab.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
					startActivity(new Intent(EbookEBookfeedActivity.this,EbookGalleryActivity.class));


                }
            });



    }



	@Override
    protected void onStart() {
        super.onStart();




    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }





    public void refresh(){
		tb.collapseActionView();
        if(isOnline()){
            online=true;
            try{
				mSwipeLayout.setRefreshing(true);
                new WebScrapeTask().execute(link);
				position_tv.setText("စာအုပ်စင်= "+current_pno);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Can't be used Data!",Toast.LENGTH_SHORT).show();
            }


        }else{
            online=false;

        }
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
            shareText(link.replace("www.mmbookdownload.com","www.ayetharyar.com"),title);
        }
		if(id==R.id.menu_report){
			showToReport();
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
	public void addItem(EbookBItem item){
		posts.add(item);
	}

	public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>
	{
		@Override
		public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
		{
			View v=getLayoutInflater().inflate(R.layout.ebook_b_item,p1,false);
			return new ViewHolder(v);
		}

		public void filter(String charText){
			charText =charText.toLowerCase();
			filteredposts.clear();
			if (charText.length()==0){
				filteredposts.addAll(posts);
			}else{
				for (EbookBItem pi : posts){
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
			int  bookCount=filteredposts.size();
			return bookCount;
		}

		@Override
		public void onBindViewHolder(final FeedAdapter.ViewHolder p1,final int p2)
		{
			if((filteredposts.get(p2).thumbnail.length()>5)&&(online)){
				Glide
                    .with(getApplicationContext())
                    .load(filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
                    .into(p1.iv);

			}else{
				p1.iv.setImageResource(R.drawable.ebook_loading_book);
			}
			if(filteredposts.get(p2).bname.equals("")){
				p1.bname_tv.setText(filteredposts.get(p2).bname);
			}else{
				p1.bname_tv.setText(filteredposts.get(p2).bimg);
			}
			p1.wname_tv.setText(filteredposts.get(p2).wname.replace("Author","").replace(":",""));
			// : 17-May-2021 View: 25830 : 129.36 MB",
			String info=filteredposts.get(p2).info;
			String date=info.split("View")[0];
			String view=info.split(":")[2];
			String size=info.split(":")[3];
			p1.info_tv.setText((date+"/"+size).replace(":",""));
			p1.view_tv.setText("👁"+view);
			final String bimg=p1.bname_tv.getText().toString();
            p1.card.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1) {
						//    gotoBDActivity(filteredposts.get(p2).thumbnail,filteredposts.get(p2).bname,filteredposts.get(p2).wname,filteredposts.get(p2).res,filteredposts.get(p2).category);
                        Intent i=new Intent(EbookEBookfeedActivity.this,EbookBDActivity.class);
						i.putExtra("bname",filteredposts.get(p2).bname);
						i.putExtra("bimg",bimg);
						i.putExtra("wname",filteredposts.get(p2).wname);
						i.putExtra("wlink",filteredposts.get(p2).wlink);
						i.putExtra("link",filteredposts.get(p2).link);

						startActivity(i);
                    }
                });

			/*	if (p2%5==1 || p2 ==0){
			 p1.adview.setVisibility(View.VISIBLE);
			 AdRequest req= new AdRequest.Builder().build();
			 p1.adview.loadAd(req);
			 }*/
		}


		public EbookBItem getItem(int pos){
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
			filteredposts=new ArrayList<EbookBItem>();
			filteredposts.addAll(posts);
		}

		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
		{
			ImageView iv;
			CardView card;
			TextView bname_tv,wname_tv,info_tv,view_tv;
			//	AdView adview;
			@Override
			public void onClick(View view)
			{


			}
			public ViewHolder(View view)
			{
				super(view);
				iv=view.findViewById(R.id.thumbnail_iv);
				card=view.findViewById(R.id.card);
				bname_tv=view.findViewById(R.id.bname_tv);
				wname_tv=view.findViewById(R.id.wname_tv);
				info_tv=view.findViewById(R.id.info_tv);
				view_tv=view.findViewById(R.id.view_tv);
				//	adview=view.findViewById(R.id.adView);
				view.setOnClickListener(this);
			}
		}
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
                    call.setData(Uri.parse("tel:"+"096898765"));
                    startActivity(call);
                    dialog.dismiss();
                }});

	}

	public class WebScrapeTask extends AsyncTask<String,Void,String>
	{
		@Override
		protected String doInBackground(String[] p1)
		{

			try
			{
				doc = (Document) Jsoup.connect(p1[0]).get();

				return process((org.jsoup.nodes.Document) doc);
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
			if(result.contains("thumbnail")){
				result=result.replace("},\n]","}\n]");
				processJson(result);
				adapter=new FeedAdapter();
				rv.setAdapter(adapter);
				saveToPrefs("recent",result);
			}
			processLink(doc);


		}
		public void processLink(Document doc){
			try{
				Element getlinkE= (Element) doc.createElement("ul.pagination");
				String  first_link="https://www.mmbookdownload.com/"+(getlinkE.selectFirst("li a").attr("href"));
				String last_link="";
				try{
					last_link="https://www.mmbookdownload.com/"+(getlinkE.select("li a").get(2).attr("href"));
				}catch(Exception e){
					last_link="https://www.mmbookdownload.com/"+(getlinkE.select("li a").get(1).attr("href"));

				}

				String pno=first_link.split("pno=")[1];
				url=first_link.replace(pno,"");

				String l_pno=last_link.split("pno=")[1];
				try{
					last_pno=Integer.parseInt(l_pno);
				}catch(Exception e){}



				position_l.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						String pnos="";

						for (int i = 0; i < last_pno; i++) {
							pnos+="စာအုပ်စင်= "+(i+1)+"\n";
						}

						pnos=pnos.replace(last_pno+"\n",last_pno+"");
						String[] pages=pnos.split("\n");
						ArrayAdapter<String> arrayAdapter = new ArrayAdapter(EbookEBookfeedActivity.this, android.R.layout.simple_list_item_1, pages);
						arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


						new AlertDialog.Builder(EbookEBookfeedActivity.this)
								.setTitle("စာအုပ်စင်များ")
								. setAdapter(arrayAdapter, new DialogInterface.OnClickListener(){
									@Override
									public void onClick(DialogInterface p1, int position) {
										handleListClick (position);
									}})
								. show();
						// TODO: Implement this method
					}
				});

				pre_bt.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						pre();
						// TODO: Implement this method
					}

					private void pre() {
					}
				});
				next_bt.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						next();
						// TODO: Implement this method
					}

					private void next() {
					}
				});
			}
			catch(Exception e){
				Toast.makeText(getApplicationContext(),"စာအုပ်များ ဤမျှသာ ရှိပါသည်။",Toast.LENGTH_LONG).show();

			}
		}
		private void handleListClick (int position){
			current_pno=position+1;
			link=url+current_pno;
			refresh();
		}

		public String process(org.jsoup.nodes.Document doc){

			String res="[";
			try {
				try{
					doc.selectFirst("div.panel-group").remove();
				}catch( Exception e)
				{

				}

				Elements be=doc.getElementsByClass("panel panel-default");

				for (Element element : be){
					String bname = element.selectFirst("div a").text();

					Element body=element.selectFirst("div.panel-body").selectFirst("div.row");
					String thumbnail=body.selectFirst("a img").attr("src");
					String link=body.selectFirst("a").attr("href");
					String bimg="";
					try{
						bimg=(body.select("div img").get(1).attr("src").split("title=\""))[1].split("\"")[0];
					}catch (Exception e){

					}
					String author=body.selectFirst("span a").text();
					String author_link=body.selectFirst("span a").attr("href");

					String info=body.selectFirst("ul").text();


					res+="{\n";
					res+="\"thumbnail\":\""+thumbnail+"\",\n";
					res+="\"bname\":\""+bname.replace("\"","")+"\",\n";
					res+="\"bimg\":\""+unescapeUnicode(bimg).replace("\"","")+"\",\n";
					res+="\"wname\":\""+author.replace("\"","")+"\",\n";
					res+="\"wlink\":\""+"https://www.mmbookdownload.com/"+author_link+"\",\n";
					res+="\"info\":\""+info.replace("\"","")+"\",\n";
					res+="\"link\":\""+"https://www.mmbookdownload.com/"+link+"\"},\n";

				}
			} catch (Exception e) {

				Toast.makeText(getApplicationContext(),"စာအုပ်များ မရယူနိုင်ခဲ့ပါ",Toast.LENGTH_SHORT).show();

			}

			mSwipeLayout.setRefreshing(false);
			res=EbookFontConverter.zg2uni(res);
			return res+"]";
		}
	}
	public static String unescapeUnicode(String str) {
		StringBuffer b = new StringBuffer();
		Matcher m = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(str);
		while (m.find())
			b.append((char) Integer.parseInt(m.group(1), 16));
		return b.toString();
	}

}

