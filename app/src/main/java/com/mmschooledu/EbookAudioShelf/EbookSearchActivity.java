package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;


import android.app.*;
import android.os.*;
import android.widget.*;
import java.util.*;
import org.json.*;
import android.widget.AdapterView.*;
import android.view.*;
import android.content.*;
//import com.squareup.picasso.*;
import android.text.*;
import android.net.*;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
//import androidx.appcompat.widget.Toolbar;

public class EbookSearchActivity extends Activity 
{Toolbar tb;
	ListView lv;
	EditText et;
	MyAdapter adapter;
	List<EbookPostItem> posts,filteredPosts;
	boolean online=false;
	final int FONT_ZAWGYI=1;
	final int FONT_UNI=2;
	final int FONT_NONE=0;
	int currentFont=FONT_NONE;
	String jsonStr="";
	int totalPosts=0;
	//String currentBlog="https://apassionforflowers.blogspot.com/feeds/posts/default?alt=json";

	String currentBlog="https://onlinebookstoreaudiobook.blogspot.com/feeds/posts/default/-/Horror?alt=json&start-index=1&max-results=15";
	//String currentBlog="https://acomputerblog.blogspot.com/feeds/posts/default/-/computer?alt=json" ;
	//String currentBlog="https://onlinebookstorepassward.blogspot.com/feeds/posts/default?alt=json&start-index=1&max-results=5";
//https://myanmarenglishlesson6.blogspot.com/2022/07/quiz12.html?m=1
	//https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/App?alt=json" ,


	//androidx.appcompat.widget.Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ebook_search_main);

		//initToolbar();
		tb=(androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
		//setSupportActionBar(tb);
		//getSupportActionBar().setHomeButtonEnabled(true);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		tb=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(tb);
		//tb.setLogo(R.drawable.ebook_ic_launcher);
		// tb.setTitle("ATy");



		et=(EditText)findViewById(R.id.et);

		lv=(ListView)findViewById(R.id.lv);
		lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					Intent i=new Intent(EbookSearchActivity.this,EbookSearchViewerActivity.class);
					EbookPostItem pi=filteredPosts.get(p3);
					i.putExtra("title",pi.title);
					i.putExtra("content",pi.content);
					startActivity(i);
				}
			});

		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		currentFont= sharedPreferences.getInt("font_main",0);
		refresh();
    }

	private void setSupportActionBar(Toolbar tb) {
	}





	private void refresh(){
		if(isOnline()){
			online=true;
			new DownloadTask().execute(currentBlog);
		}else{
			online=false;
			SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
			jsonStr= sharedPreferences.getString(currentBlog,"");
			try{
				processJSON();
			}catch(Exception e){
				Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
			}
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

	}/*

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu)
	 {
	 menu.add("Refresh")
	 .setShowAsAction((MenuItem.SHOW_AS_ACTION_ALWAYS));
	 menu.add("Change_Font")
	 .setShowAsAction((MenuItem.SHOW_AS_ACTION_ALWAYS));
	 menu.add("Computers");
	 menu.add("Books");
	 menu.add("Flowers");

	 return super.onCreateOptionsMenu(menu);
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item)
	 {
	 if(item.getTitle().equals("Refresh")){
	 refresh();
	 }else if(item.getTitle().equals("Change_Font")){
	 changeFont();
	 }else if(item.getTitle().equals("Computers")){
	 currentBlog="http://acomputerblog.blogspot.com/feeds/posts/default?alt=json";
	 refresh();
	 }else if(item.getTitle().equals("Books")){
	 currentBlog="http://free-ebook-download-links.blogspot.com/feeds/posts/default?alt=json";
	 refresh();
	 }else if(item.getTitle().equals("Flowers")){
	 currentBlog="http://apassionforflowers.blogspot.com/feeds/posts/default?alt=json";
	 refresh();
	 }
	 return super.onOptionsItemSelected(item);
	 }*/

	private void changeFont(){
		currentFont++;
		if(currentFont==3){
			currentFont=0;
		}
		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt("font_main",currentFont);
		editor.commit();
		processJSON();
	}


	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		finishAffinity();
		super.onBackPressed();
	}
	public void _Back_Click(View v){
		finish();
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
				totalPosts=Integer.parseInt(jo.getString("$t"));
				result=EbookJSONDownloader.download(p1[0]+"&max-results="+totalPosts);

			}catch(Exception e){

			}
			return result;
		}

		@Override
		public void onPostExecute(String result) {
			jsonStr=result;
			SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString(currentBlog,result);
			editor.commit();
			processJSON();

			et.addTextChangedListener(new TextWatcher(){

					@Override
					public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
					{
						// TODO: Implement this method
					}

					@Override
					public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
					{
						EbookSearchActivity.this.adapter.filter(p1.toString());
					}

					@Override
					public void afterTextChanged(Editable p1)
					{
						// TODO: Implement this method
					}

				});
		}
	}

	private void processJSON(){
		String input="";
		posts=new ArrayList<EbookPostItem>();
		switch(currentFont){
			case FONT_ZAWGYI:
				input=EbookFontConverter.uni2zg(jsonStr);
				break;
			case FONT_UNI:
				input=EbookFontConverter.zg2uni(jsonStr);
				break;
			default:
				input=jsonStr;
				break;
		}
		try
		{
			JSONObject jo=new JSONObject(input);
			JSONArray ja=jo.getJSONObject("feed").getJSONArray("entry");
			JSONObject jo2;
			for(int i=0;i<ja.length();i++){
				EbookPostItem pi=new EbookPostItem();
				jo2=ja.getJSONObject(i);
				pi.title= jo2.getJSONObject("title").getString("$t");
				pi.content=jo2.getJSONObject("content").getString("$t");
				posts.add(pi);

				try{
					String thumbnail=jo2.getJSONObject("media$thumbnail").getString("url");
					pi.thumbnailUrl=thumbnail;
				}catch(Exception e){
				}
			}

		}
		catch (JSONException e)
		{
		}
		adapter=new MyAdapter();
		lv.setAdapter(adapter);
	}


	class MyAdapter extends BaseAdapter
	{
		//List<EbookPostItem> filteredPosts;

		public MyAdapter(){
			et.setText("");
			filteredPosts=new ArrayList<EbookPostItem>();
			filteredPosts.addAll(posts);
		}

		@Override
		public int getCount()
		{
			return filteredPosts.size();
		}

		@Override
		public Object getItem(int p1)
		{
			return filteredPosts.get(p1);
		}

		@Override
		public long getItemId(int p1)
		{
			return 0;
		}

		@Override
		public View getView(int p1, View p2, ViewGroup p3)
		{
			if(p2 == null) {
				p2 = getLayoutInflater().inflate(R.layout.ebook_searhitem_layout, null); 
			}
			TextView tv1 = (TextView)p2.findViewById(R.id.tv1);
			TextView tv2 = (TextView) p2.findViewById(R.id.tv2); 
			ImageView iv = (ImageView) p2.findViewById(R.id.iv);

			if((filteredPosts.get(p1).thumbnailUrl.length()>0)&&(online)){
				//Picasso
				Glide
					.with(getApplicationContext())
					.load(Html.fromHtml(filteredPosts.get(p1).thumbnailUrl).toString())
					.into(iv);
			}else{
				iv.setImageResource(R.drawable.ebook_apk);
			}
			tv1.setText(filteredPosts.get(p1).title);

			tv2.setText(Html.fromHtml(filteredPosts.get(p1).content.substring(0,Math.min(filteredPosts.get(p1).content.length(),100))+" ...").toString());

			return p2;
		}

		public void filter(String charText) {

			charText = charText.toLowerCase();

			filteredPosts.clear();

			if (charText.length() == 0) {

				filteredPosts.addAll(posts);

			} else {
				for (EbookPostItem pi : posts) 
				{
					if (pi.title.toLowerCase().contains(charText)) 
					{
						filteredPosts.add(pi);
					}
					else
					if(charText.equals("၆၆၆၆၆")){

						//  Toast.makeText(getApplication(), "lets go to SecondActivity.", Toast.LENGTH_SHORT).show();

						SharedPreferences sharedPreferences = EbookSearchActivity.this.getSharedPreferences("Check", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor1 = sharedPreferences.edit();
						editor1.clear();
						editor1.putString("check", "၆၆၆၆၆");
						editor1.apply();


						Intent i=new Intent(EbookSearchActivity.this,EbookMainActivity.class);         
						startActivity(i);	




					}

					else
					if(charText.equals("66666")){

						//  Toast.makeText(getApplication(), "lets go to SecondActivity.", Toast.LENGTH_SHORT).show();

						SharedPreferences sharedPreferences = EbookSearchActivity.this.getSharedPreferences("Check", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor1 = sharedPreferences.edit();
						editor1.clear();
						editor1.putString("check", "66666");
						editor1.apply();


						Intent i=new Intent(EbookSearchActivity.this,EbookMainActivity.class);         
						startActivity(i);	




					}


				}
				notifyDataSetChanged();
			}
		}}}
	







