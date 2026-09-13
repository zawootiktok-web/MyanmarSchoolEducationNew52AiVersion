package com.mmschooledu.Mp3;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mmschooledu.JSONDownloader;
import com.mmschooledu.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AudioListViewActivity extends AppCompatActivity {
    private AdView adView;
    CustomDialog customDialog;
    ListView lv;
    List<SongData> posts,list;
    ULGAdapter adapter;
    final int storageRequestCode=123;
    DownloadManager dm;
    DownloadManager.Request req;

//    String link="https://script.google.com/macros/s/AKfycbyWv1imnZH4ta5M0nNUuavXasQgFFLHEbX4lxG5jKSJJlFqS0qD7JzeEulO5rhWMbvi2A/exec";
    private static final int resultCode = 0;


    String link ="link";
    String title;
    String category;



    private Handler mHandler;
    private Runnable mRunnable;

    ImageView iv;
    String bname,type,thumbnail,wname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audiolistview_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Admob.loadInterstitialAds(AudioListViewActivity.this);
//        Admob.loadRewordedAds(AudioListViewActivity.this);
        Admob.loadRewardedInterstitialAds(AudioListViewActivity.this);

        String bname=getIntent().getExtras()
                .get("bname").toString();
        getSupportActionBar().setTitle(bname+"");
        setTitle(title);

        lv= findViewById(R.id.lv);
        customDialog=new CustomDialog(this);

        Intent intent = getIntent();
        link = intent.getStringExtra("link");
        Intent intent2 = getIntent();
        title = intent2.getStringExtra("title");
        category = intent.getStringExtra("category");
        Intent intent4 = getIntent();
        thumbnail = intent4.getStringExtra("thumbnail");





        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                String title = toolbar.getTitle().toString();
                toolbar.setTitle(title.substring(1) + title.substring(0, 1));
                mHandler.postDelayed(this, 700);
            }//500,500
        };
        mHandler.postDelayed(mRunnable, 1000);



        adView = (AdView)
                findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            new DownloadTask().execute(link);
            customDialog.show();
        }else {
            DataAlert("No Internet!","Please turn on mobile data or wifi.");
        }
    }

    public void setTitleText(String title){
        setTitle(title);
    }
    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... p1) {
            return JSONDownloader.download(p1[0]);
        }

        @Override
        public void onPostExecute(String result) {
            if (result.length() > 0) {
//                processJSON(result);
//                saveArray(result);
                processJSON(result, category);
                saveArray(result, title);

            } else {
                DataAlert("No Song!", "No song at this time. Try again.");
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.audio_list_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            //mDrawerLayout.openDrawer(GravityCompat.START);
            //return true;
            finish();
//        } else if (item.getItemId() == R.id.lessonaudio) {
//
//            Toast.makeText(getApplicationContext(), "Can't create folder ", Toast.LENGTH_LONG).show();

        }


        return super.onOptionsItemSelected(item);
    }
    private void processJSON(String input, String title){
        posts = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(input);
            JSONArray ja = jo.getJSONObject("feed").getJSONArray("entry");
            JSONObject jo2;
            category = getIntent().getStringExtra("category");
            for (int i = 0; i < ja.length(); i++) {
                jo2 = ja.getJSONObject(i);
                if (jo2.getJSONObject("title").getString("$t").equals(category)) {
                    String content = jo2.getJSONObject("content").getString("$t");
                    String orgjson = Html.fromHtml(content).toString();

                    JSONObject obj = new JSONObject(orgjson);
                    JSONArray jarr = obj.getJSONArray("books");
                    for (int j = 0; j < jarr.length(); j++) {
                        JSONObject bookObj = jarr.getJSONObject(j);
                        String link = bookObj.getString("link");
                        String bname = bookObj.getString("bname");
                        String thumbnail = bookObj.getString("thumbnail");

                        SongData songData = new SongData();
                        songData.bname = bname;
                        songData.link = link;
                        songData.thumbnail = thumbnail;
                        songData.title = title;
                        posts.add(songData);
                    }
                    break; // Exit loop after finding the matching title
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter=new ULGAdapter();
        lv.setAdapter(adapter);
        customDialog.dismiss();

    }
    public void saveArray(String myList, String title)
    {
        SharedPreferences sp = getSharedPreferences("MyList", Context.MODE_PRIVATE);
        SharedPreferences.Editor mEd = sp.edit();
        mEd.putString("songList",myList);
        mEd.apply();
    }
    public class ULGAdapter extends BaseAdapter
    {
        public ULGAdapter(){
            list= new ArrayList<>();
            list.addAll(posts);
        }
        @Override
        public int getCount()
        {
            return list.size();
        }
        @Override
        public Object getItem(int p1)
        {
            return list.get(p1);
        }
        @Override
        public long getItemId(int p1)
        {
            return 0;
        }
        @SuppressLint("InflateParams")
        @Override
        public View getView(final int p1, View p2, ViewGroup p3)
        {
            if(p2 == null){
                p2=getLayoutInflater().inflate(R.layout.audiolistitem,null);
            }
            final TextView tv1= p2.findViewById(R.id.tv1);
            tv1.setText(list.get(p1).bname);
            final ImageButton btnPlay= p2.findViewById(R.id.btnPlay);
            final String title=list.get(p1).bname;
            btnPlay.setOnClickListener(v -> playActivity(p1));
            ImageButton btn= p2.findViewById(R.id.btnDownload);
            btn.setOnClickListener(v -> {
                showDownload(title,list.get(p1).link);
            });
            return p2;
        }
    }
    public void playActivity(int position){
        new Admob(new onDismiss() {
            @Override
            public void onDismiss() {
                // When Ads Close Take Action
        Intent intent=new Intent(AudioListViewActivity.this,PlayListActivity.class);
        intent.putExtra("position",position);
        intent.putExtra("thumbnail",thumbnail);
        intent.putExtra("title",title);
        intent.putExtra("category",category);
        startActivity(intent);
    }
        }).ShowInterstitial(AudioListViewActivity.this, true);
    }

    public void showDownload(String tName, String dLink) {
        String folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
                + File.separator + "MyanmarSchoolEducationMp3" + File.separator;
        File folder = new File(folderPath);

        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) {
                Toast.makeText(getApplicationContext(), "Can't create folder", Toast.LENGTH_LONG).show();
                return;
            }
        }

        File file = new File(folder, tName + ".mp3");
        if (file.exists()) {
            Toast.makeText(this, "File already downloaded!", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = dLink;
        if (url.contains("://drive.google.com")) {
            url = getGoogleDriveDownloadLinkFromUrl(url);
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(tName);
        request.setDescription("Downloading " + tName);
        request.setDestinationUri(Uri.fromFile(file));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(request);

        Toast.makeText(this, "Downloading started...", Toast.LENGTH_SHORT).show();

    }

    public static String getGoogleDriveDownloadLinkFromUrl(String url) {
        int index = url.indexOf("id=");
        int closingIndex = 0;
        if (index >= 0) {
            index += 3;
            closingIndex = url.indexOf("&", index);
            if (closingIndex < 0) {
                closingIndex = url.length();
            }
        } else {
            index = url.indexOf("file/d/");
            if (index < 0) {
                return url;
            }
            index += 7;
            closingIndex = url.indexOf("/", index);
            if (closingIndex < 0) {
                closingIndex = url.indexOf("?", index);
                if (closingIndex < 0) {
                    closingIndex = url.length();
                }
            }
        }
        String id = url.substring(index, closingIndex);
        return "https://drive.google.com/uc?id=" + id + "&export=download";
    }


    public void DataAlert(String title, String message){
        MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder(this);
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


    @Override
    public void onBackPressed()
    {
        customDialog.dismiss();
        finish();
        super.onBackPressed();
    }
}