package com.mmschooledu;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.mmschooledu.Item.PostItem;
import com.mmschooledu.Viewer.ViewerActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public abstract class TGuideFeedActivity extends AppCompatActivity
{private AudienceNetwork audienceNetwork;
    private InterstitialAd mInterstitialAd;
    private AdView adView;


    public abstract void processJson(String jsonString);
    public abstract boolean useGridLayout();
    public abstract String getFeedAddress();
    public abstract void _Options_Menu_Click(MenuItem item);
    private SwipeRefreshLayout mSwipeLayout;
    RecyclerView rv;
    FeedAdapter adapter;
    List<PostItem> posts,filteredposts;
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
        AudienceNetwork audienceNetwork;

        super.onCreate(savedInstanceState);

        // NOTE always use test ads during development and testing
//        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
        setContentView(R.layout.jsonfeed_layoutqa);



        tb=(Toolbar)findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        adView = (AdView)
//                findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);


        Admob.loadInterstitialAds(TGuideFeedActivity.this);
//        Admob.loadRewordedAds(TGuideFeedActivity.this);


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
                    Toast.makeText(getApplicationContext(),"No internet connection!",Toast.LENGTH_SHORT).show();
                }
            } });

        mSwipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        posts=new ArrayList<PostItem>();
        filteredposts=new ArrayList<PostItem>();
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
        protected String doInBackground(String... p1)
        {
            String result=JSONDownloader.download(p1[0]);
            try{
                JSONObject jo=new JSONObject(result);
                jo=jo.getJSONObject("feed").getJSONObject("openSearch$totalResults");
                int totalPosts=Integer.parseInt(jo.getString("$t"));
                result = JSONDownloader.download(p1[0] + "&max-results=" + totalPosts);

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
        getMenuInflater().inflate(R.menu.main_menutguide,menu);
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

    public void addItem(PostItem item){
        posts.add(item);
    }

    //public abstract void _FAB_Click(View v)

    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>
    {
        @Override
        public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
        {
            View v=getLayoutInflater().inflate(R.layout.item_layout1,p1,false);
            return new ViewHolder(v);
        }

        public void filter(String charText){
            charText =charText.toLowerCase();
            filteredposts.clear();
            if (charText.length()==0){
                filteredposts.addAll(posts);
            }else{
                for (PostItem pi : posts){
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
                p1.iv.setImageResource(R.drawable.edu);
            }
            p1.tv.setText(filteredposts.get(p2).title);
            //p1.tv.setText(Html.fromHtml(posts.get(p2).content.substring(0,100)+" ..."));
            p1.tv2.setText(Html.fromHtml(posts.get(p2).content.substring(0,Math.min(posts.get(p2).content.length(),100))+" ...").toString());
            //published
            p1.tv3.setText(filteredposts.get(p2).published);

        }

        public PostItem getItem(int pos){
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
            filteredposts=new ArrayList<PostItem>();
            filteredposts.addAll(posts);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView iv;
            TextView tv;
            TextView tv2;
            TextView tv3;

            @Override
            public void onClick(View view) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {


                        Intent i = new Intent(TGuideFeedActivity.this, ViewerActivity.class);
                        PostItem pi = filteredposts.get(getAdapterPosition());
                        i.putExtra("title", pi.title);
                        i.putExtra("content", pi.content);
                        i.putExtra("published", pi.published);
                        startActivity(i);
//                        StartAppAd.showAd(getBaseContext());

                    }
                }).ShowInterstitial(TGuideFeedActivity.this, true);
            }

            public ViewHolder(View view) {
                super(view);
                iv = view.findViewById(R.id.ivItemImage);
                tv = view.findViewById(R.id.tvItemText);
                tv2 = (TextView) view.findViewById(R.id.tvItemText2);
                tv3 = (TextView) view.findViewById(R.id.tvItemText3);
                view.setOnClickListener(this);
            }
        }
    }

    public void MsgBox(String title, String msg) {

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

    private void showNoInternet () {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View parent_view = LayoutInflater.from(this).inflate(R.layout.nointernet_dia, null);
        dialog.setView(parent_view);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        // Finding Views inside dialog
        TextView tv_title = dialog.findViewById(R.id.dialogTextView1);
        Button button = dialog.findViewById(R.id.dialogButton1);


        tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ခြင်းမရှိပါ။ ​​​ကျေးဇူးပြု၍ အင်တာနက် ဆက်သွယ်​​ပေးပါ။");

        button.setText("..ဟုတ်ကကဲ့..");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {

                //dialog.dismiss();
                Intent i = new Intent(TGuideFeedActivity.this, SplashActivity.class);
                startActivity(i);
            }
        });
    }
}



