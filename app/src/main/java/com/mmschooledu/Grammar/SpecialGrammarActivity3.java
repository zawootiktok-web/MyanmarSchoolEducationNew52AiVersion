package com.mmschooledu.Grammar;


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

public class SpecialGrammarActivity3 extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private AdView adView;
    Toolbar tb;

    CustomAdapter customAdapter;
    GridView gridView;
    List<EbookBookItem> list,post;
    //String mainUrl="https://english.pdfdrive.com.pk/all-books";
    String mainUrl="https://sania.worksheetpoint.com/all-pdf-books/";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Button btnNext,btnPrev;
    int i=1;
    String e="";

    String a="";
    String newtitle;

    private Handler mHandler;
    private Runnable mRunnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_grammar_activity2);

        tb = (Toolbar) findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setTitle(title);
        //String title = tb.getTitle().toString();

//
//        adView = (AdView)
//                findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);


        newtitle=getIntent().getExtras()
                .get("newtitle").toString();
        setTitle (newtitle);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                String newtitle = tb.getTitle().toString();
                tb.setTitle(newtitle.substring(1) + newtitle.substring(0, 1));
                mHandler.postDelayed(this, 700);
            }//500,500
        };
        mHandler.postDelayed(mRunnable, 1000);

        gridView=findViewById(R.id.gridView);
        mSwipeRefreshLayout=findViewById(R.id.swipeRefresh);
        btnNext=findViewById(R.id.btnNext);
        btnPrev=findViewById(R.id.btnPrev);
        btnNext.setVisibility(View.VISIBLE);
        btnPrev.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            e="div.elementor-post__card";
            a="a";
            loadWeblink(mainUrl);

        }else {
            // DataAlert("No Internet!","Please turn on mobile data or wifi.");
            Toast.makeText(getApplicationContext(),"No internet connection!",Toast.LENGTH_SHORT).show();
            showNoInternet();
            mSwipeRefreshLayout.setRefreshing(false);
        }
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                if(i<2){
                    i=1;
                    newToast("Home Page");
                    loadWeblink(mainUrl);
                }else{
                    nextPage(i);
                    newToast("page/"+i);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if(i>1){
                    nextPage(i);
                    newToast("page/"+i);
                }else{
                    i=1;

                }

            }
        });
    }
    @Override
    public void onRefresh() {
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            loadWeblink(mainUrl);
        }else {
            // DataAlert("No Internet!","Please turn on mobile data or wifi.");
            Toast.makeText(getApplicationContext(),"No internet connection!",Toast.LENGTH_SHORT).show();
            showNoInternet();
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }
    public void newToast(String message){
        Toast.makeText(SpecialGrammarActivity3.this,message,Toast.LENGTH_LONG).show();
    }
    public void nextPage(int j){

        String newLink=mainUrl+"page/"+j;
        loadWeblink(newLink);


    }
    public  void loadWeblink(String link){
        mSwipeRefreshLayout.setRefreshing(true);
        new WebScrapeTask().execute(link);
    }
    public class WebScrapeTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String[] p1)
        {
            try
            {
                Document doc = Jsoup.connect(p1[0]).get();
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
            mSwipeRefreshLayout.setRefreshing(false);
            customAdapter = new CustomAdapter(getApplicationContext(),post);
            gridView.setAdapter(customAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
                {

                    Intent i=new Intent(SpecialGrammarActivity3.this,SpecialBookDetailActivity.class);
                    i.putExtra("link",post.get(p3).link);
                    i.putExtra("title",post.get(p3).title);
                    i.putExtra("img",post.get(p3).img);
                    startActivity(i);




                }


            });
        }

    }


    public String process(Document doc){

        post=new ArrayList<>();
        String result="";

        Elements elements=doc.select(e);
        for(Element element:elements){
            String title=element.select(a).text();
            String link=element.select("a").attr("href");
            String img=element.select("img").attr("src");
            EbookBookItem td=new EbookBookItem();
            td.title=title;
            td.link=link;
            td.img=img;
            post.add(td);

        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_menu_grammer3,menu);
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) myActionMenuItem.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String p1)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String p1)
            {
                customAdapter.filter(p1.toString());

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    { int id = item.getItemId();
        if (id == android.R.id.home) {
            //	mDrawerLayout.openDrawer(GravityCompat.START);
            //if(item.getItemId()==android.R.id.home){
            finish();
            return true;

            //_Options_Menu_Click(item);
        }

        i=1;
        if(item.getItemId()==R.id.home2){
            e="div.elementor-post__card";
            a="a";
            mainUrl="https://sania.worksheetpoint.com/all-pdf-books/";
            loadWeblink(mainUrl);
            btnNext.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
        }else if(item.getItemId()==R.id.allpdf){
            e="div.elementor-post__card";
            a="h2";
            mainUrl="https://sania.worksheetpoint.com/all-pdf-books";
            loadWeblink(mainUrl);
            btnNext.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
        }else if(item.getItemId()==R.id.english){
            e="div.elementor-post__card";
            a="h2";
            mainUrl="https://sania.worksheetpoint.com/category/english-books";
            loadWeblink(mainUrl);
            //  loadWeblink(mainUrl+"category/english-books/");
            btnNext.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
        }else if(item.getItemId()==R.id.englishgrammar){
            e="div.elementor-post__card";
            a="h2";
            mainUrl="https://sania.worksheetpoint.com/category/english-grammmar";
            loadWeblink(mainUrl);
            btnNext.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
        }else if(item.getItemId()==R.id.englishvocabulary){
            e="div.elementor-post__card";
            a="h2";
            mainUrl="https://sania.worksheetpoint.com/category/english-vocabulary";
            loadWeblink(mainUrl);
            btnNext.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);

        }else if(item.getItemId()==R.id.phonics){
            e="div.elementor-post__card";
            a="h2";
            mainUrl="https://sania.worksheetpoint.com/category/jolly-phonics";
            loadWeblink(mainUrl);
            btnNext.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }
    public void DataAlert(String title, String message){
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
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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

