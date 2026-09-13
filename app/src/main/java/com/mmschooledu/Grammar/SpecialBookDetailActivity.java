package com.mmschooledu.Grammar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bdtopcoder.quickadmob.onDismiss;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mmschooledu.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SpecialBookDetailActivity extends AppCompatActivity {


    TextView tvTitle;
    ImageView imgView;
    ImageButton download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        tvTitle=findViewById(R.id.tvCard);
        imgView=findViewById(R.id.ivCard);
        download=findViewById(R.id.btnDownload);


        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            String url=getIntent().getStringExtra("link");
            new WebScrapeTask().execute(url);
        }else {
            DataAlert("No Internet!","Please turn on mobile data or wifi.");

        }
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
        protected void onPostExecute(final String result)
        {
            super.onPostExecute(result);
            String title=getIntent().getStringExtra("title");
            assert title != null;
            if(title.contains("Continue")){
                int index=title.indexOf("Continue");
                tvTitle.setText(title.substring(0,index));
            }else{
                tvTitle.setText(title);
            }
            tvTitle.setVisibility(View.VISIBLE);
            download.setVisibility(View.VISIBLE);


            try{
                String img=getIntent().getStringExtra("img");
                Glide.with(getApplicationContext()).load(img).into(imgView);
            }catch(Exception e){

            }
            download.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    new com.bdtopcoder.quickadmob.Admob(new onDismiss() {
                        @Override
                        public void onDismiss() {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
                    startActivity(browserIntent);
//                    StartAppAd.showAd(getBaseContext());

                        }
                    }).ShowInterstitial(SpecialBookDetailActivity.this, true);
                }

            });


        }

    }


    public String process(Document doc){


        String result="";
        Elements elements=doc.select("div.elementor-button-wrapper");
        for(Element element:elements){
            String title=element.select("a").text();
            String link=element.select("a").attr("href");

           if(title.equals("Pdf Download")){
               result=link;
           }
else
            if(title.equals("Download Pdf")){
                result=link;
            }

        }

        return result;
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
}
