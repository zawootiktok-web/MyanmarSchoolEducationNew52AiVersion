package com.mmschooledu.Grammar;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mmschooledu.EbookAudioShelf.EbookBookItem;
import com.mmschooledu.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpecialGrammarActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {
    private AdView adView;

    Toolbar tb;

    CustomAdapter customAdapter;
    GridView gridView;
    List<EbookBookItem> list, post;
      //  String mainUrl="https://english.pdfdrive.com.pk/all-books";
   // String mainUrl = "https://uk.fims.org.pk/all-pdf-books";
String newtitle;
    String mainUrl;
    String link=mainUrl;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_grammar_activity);
        gridView = findViewById(R.id.gridView);

        adView = (AdView)
                findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        newtitle= Objects.requireNonNull(getIntent().getExtras()
                .get("newtitle")).toString();
        setTitle (newtitle);


        tb = (Toolbar) findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setTitle(title);
        //String title = tb.getTitle().toString();

        mainUrl= Objects.requireNonNull(getIntent().getExtras()
                .get("link")).toString();

        mHandler = new Handler();
        //500,500
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                String newtitle = tb.getTitle().toString();
                tb.setTitle(newtitle.substring(1) + newtitle.substring(0, 1));
                mHandler.postDelayed(this, 700);
            }//500,500
        };
        mHandler.postDelayed(mRunnable, 1000);


        mSwipeRefreshLayout = findViewById(R.id.swipeRefresh);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            mSwipeRefreshLayout.setRefreshing(true);
            new WebScrapeTask().execute(mainUrl);
        } else {
           // DataAlert("No Internet!", "Please turn on mobile data or wifi.");
            Toast.makeText(getApplicationContext(),"No internet connection!",Toast.LENGTH_SHORT).show();
            showNoInternet();
        }
        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
    }

    @Override
    public void onRefresh() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            new WebScrapeTask().execute(mainUrl);
        } else {
           //DataAlert("No Internet!", "Please turn on mobile data or wifi.");
            Toast.makeText(getApplicationContext(),"No internet connection!",Toast.LENGTH_SHORT).show();
            showNoInternet();
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @SuppressLint("StaticFieldLeak")
    public class WebScrapeTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String[] p1) {
            try {
                Document doc = Jsoup.connect(p1[0]).get();
                return process(doc);
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mSwipeRefreshLayout.setRefreshing(false);
            customAdapter = new CustomAdapter(getApplicationContext(), post);
            gridView.setAdapter(customAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4) {

                    Intent i = new Intent(SpecialGrammarActivity.this,SpecialBookDetailActivity.class);
                    i.putExtra("link", post.get(p3).link);
                    i.putExtra("title", post.get(p3).title);
                    i.putExtra("img", post.get(p3).img);
                    startActivity(i);


                }


            });
        }

    }


    public String process(Document doc) {

        post = new ArrayList<>();
        String result = "";
        Elements elements = doc.select("div.elementor-post__card");
        for (Element element : elements) {
            String title = element.select("a").text();
            String link = element.select("a").attr("href");
            String img = element.select("img").attr("src");

            EbookBookItem td = new EbookBookItem();
            td.title = title;
            td.link = link;
            td.img = img;
            post.add(td);

        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_grammer, menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) myActionMenuItem.getActionView();
        assert sv != null;
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String p1) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String p1) {
                customAdapter.filter(p1.toString());

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //return super.onOptionsItemSelected(item);

        int id = item.getItemId();
        if (id == android.R.id.home) {
            //	mDrawerLayout.openDrawer(GravityCompat.START);
            //if(item.getItemId()==android.R.id.home){
            finish();
            return true;

            //_Options_Menu_Click(item);
        }
        return super.onOptionsItemSelected(item);
        }


        public void DataAlert (String title, String message){
            MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder(this);
            builder1.setTitle(title);
            builder1.setMessage(message);
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "OK",
                    (dialog, id) -> {
                        dialog.cancel();
                    });
            builder1.show();
        }

    private void showNoInternet (){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View parent_view = LayoutInflater.from(this).inflate(R.layout.nointernet_dia, null);
        dialog.setView(parent_view);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        // Finding Views inside dialog
        TextView tv_title = dialog.findViewById(R.id.dialogTextView1);
        Button button = dialog.findViewById(R.id.dialogButton1);

        tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ခြင်းမရှိပါ။ ​​​ကျေးဇူးပြု၍ အင်တာနက် ဆက်သွယ်​​ပေးပါ။");

        button.setText("..ဟုတ်ကကဲ့..");

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {
                dialog.dismiss();
            }});
    }}



