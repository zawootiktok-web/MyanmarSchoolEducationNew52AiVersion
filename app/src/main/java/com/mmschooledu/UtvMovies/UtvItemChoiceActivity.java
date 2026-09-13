package com.mmschooledu.UtvMovies;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mmschooledu.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class UtvItemChoiceActivity extends Activity {
    private AdView adView;
    TextView tv;
    ImageView imageView;
    ImageButton btn;
    Button btnDownload;

    String imgLink,playLink;
    String excLink="";
    String movieName="";
    String img="";

    final int storageRequestCode=123;

    private static final int resultCode = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umvitem_choice);




        adView = (AdView)
                findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        tv=findViewById(R.id.tvName);
        btn=findViewById(R.id.btnPlay);
        imageView=findViewById(R.id.ivItem);
        btnDownload=findViewById(R.id.btnDownload);
        movieName=getIntent().getStringExtra("name");
        img=getIntent().getStringExtra("img");
        Glide.with(UtvItemChoiceActivity.this).load(img).into(imageView);
        tv.setText(movieName);
        excLink=getIntent().getStringExtra("link");
        new WebScrapeTask().execute(excLink);

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
                return "NO Result";
            }
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            btn.setVisibility(View.VISIBLE);
            String link="http://www.zegomovie.com"+imgLink;
            String play="http://www.zegomovie.com"+playLink;

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    new com.bdtopcoder.quickadmob.Admob(new onDismiss() {
//                        @Override
//                        public void onDismiss() {

                    Intent intent=new Intent(UtvItemChoiceActivity.this,UtvPlayerActivity.class);
                    intent.putExtra("link",play);
                    startActivity(intent);
                    //StartAppAd.showAd(getBaseContext());

                }
//            }).ShowInterstitial(UtvItemChoiceActivity.this, true);
//        }

            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    new com.bdtopcoder.quickadmob.Admob(new onDismiss() {
//                        @Override
//                        public void onDismiss() {
                    Intent intent=new Intent(UtvItemChoiceActivity.this,UtvPlayerActivity.class);
                    intent.putExtra("link",play);
                    startActivity(intent);
                    //StartAppAd.showAd(getBaseContext());

                        }
//                    }).ShowInterstitial(UtvItemChoiceActivity.this, true);
//                }
});

            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(UtvItemChoiceActivity.this,new String[]{

                            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },resultCode);
                    if(ContextCompat.checkSelfPermission(UtvItemChoiceActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(UtvItemChoiceActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
                    ){
                        File dir = new File(Environment.getExternalStorageDirectory() + "/UMV");
                        if (!dir.exists()) {

                            dir.mkdirs();
                            UtvVideoDownloader vd=new UtvVideoDownloader(UtvItemChoiceActivity.this,play);
                            vd.show();

                        }else{
                            Toast.makeText(UtvItemChoiceActivity.this,"Folder Not Found",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        ActivityCompat.requestPermissions(UtvItemChoiceActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},storageRequestCode);
                    }


                   // http://www.zegomovie.com/Download/AlexCross12/AlexCross12.mp4
                }
            });

        }

    }

    public String process(Document doc){

        Elements elements=doc.select("div.media");
        String res="";
        for(Element e:elements){
            imgLink=e.select("img.media-object.img-thumbnail.ff-img").first().attr("data-original");
            playLink=e.select("a.btn.btn-default.btn-block.btn-sm.text-ellipsis").first().attr("href");
            res+=imgLink+"\n";
            res+=playLink+"\n\n";

        }

        return res;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==storageRequestCode){

            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"You can press Download Button now.",Toast.LENGTH_LONG).show();
                File dir = new File(Environment.getExternalStorageDirectory() + "/UMV");
                if (!dir.exists()) {

                    dir.mkdirs();

                }
            }else{
                Toast.makeText(this, "Write storage permission denied.", Toast.LENGTH_SHORT).show();


            }
        }
    }




    public void DataAlert(String title, String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                (dialog, id) -> {
                    if(title.equals("No Song!")){
                        finish();
                    }
                    dialog.cancel();
                });


        builder1.show();
    }

}
