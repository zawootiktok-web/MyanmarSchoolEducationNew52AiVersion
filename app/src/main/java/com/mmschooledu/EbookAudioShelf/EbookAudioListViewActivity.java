package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;
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


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EbookAudioListViewActivity extends AppCompatActivity {
    private AdView adView;
    EbookCustomDialog customDialog;
    ListView lv;
    List<EbookSongData> posts,list;
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
        setContentView(R.layout.ebook_audiolistview_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String bname=getIntent().getExtras()
                .get("bname").toString();
        getSupportActionBar().setTitle(bname+"");
        setTitle(title);

        lv= findViewById(R.id.lv);
        customDialog=new EbookCustomDialog(this);

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
            return EbookJSONDownloader.download(p1[0]);
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
        getMenuInflater().inflate(R.menu.ebook_audio_list_view_menu, menu);
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

                        EbookSongData songData = new EbookSongData();
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
                p2=getLayoutInflater().inflate(R.layout.ebook_audiolistitem,null);
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

                // When Ads Close Take Action
                Intent intent=new Intent(EbookAudioListViewActivity.this,EbookPlayListActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("thumbnail",thumbnail);
                intent.putExtra("title",title);
                intent.putExtra("category",category);
                startActivity(intent);
            }

    public void showDownload(String tName,String dLink){
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC+"/MyanmarSchoolEducationMp3");
        if (!dir.exists()) {
            boolean created = dir.mkdir();
            if (!created) {
                Toast.makeText(getApplicationContext(), "Can't create folder ", Toast.LENGTH_LONG).show();
            }
        }
        String mFilePath = "file://" + dir + "/" + tName + ".mp3";
        Uri downloadUri = Uri.parse(dLink);
        req = new DownloadManager.Request(downloadUri);
        req.setDestinationUri(Uri.parse(mFilePath));
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(EbookAudioListViewActivity.this,new String[]{
                    android.Manifest.permission.READ_MEDIA_AUDIO,
            },resultCode);
            request13Permission();
        }else{
            ActivityCompat.requestPermissions(EbookAudioListViewActivity.this,new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            },resultCode);
            requestPermission();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==storageRequestCode){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (grantResults.length == 1 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveSong();
                }
            } else {
                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    saveSong();
                }
            }
        }
    }
    public void saveSong(){

        try {
            dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(req);
            Toast.makeText(EbookAudioListViewActivity.this,"Download Started",Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(EbookAudioListViewActivity.this,"Download Failed: "+ e,Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void request13Permission(){
        if( ContextCompat.checkSelfPermission(EbookAudioListViewActivity.this, android.Manifest.permission.READ_MEDIA_AUDIO)== PackageManager.PERMISSION_GRANTED
        ){
            saveSong();
        }else{
            ActivityCompat.requestPermissions(EbookAudioListViewActivity.this,new String[]{android.Manifest.permission.READ_MEDIA_AUDIO},storageRequestCode);
        }
    }
    public void requestPermission(){
        if(ContextCompat.checkSelfPermission(EbookAudioListViewActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(EbookAudioListViewActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
        ){
            saveSong();
        }else{
            ActivityCompat.requestPermissions(EbookAudioListViewActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},storageRequestCode);
        }
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