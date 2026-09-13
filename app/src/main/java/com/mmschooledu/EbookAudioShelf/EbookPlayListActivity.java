package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EbookPlayListActivity extends AppCompatActivity {
    private ImageButton btnClose;
    private ImageButton btnplay;
    private ImageButton btnRepeatOne;
    public static MediaPlayer mPlayer;
    private TextView tt, startTime, songTime;
    //    ImageView logo;
    boolean playing=false;
    int currentSong=0;
    boolean repeatOne=false;
    SeekBar seekBar;
    Handler handler;
    Runnable runnable;
    int duration;
    List<EbookSongData> posts,list;
    EbookCustomDialog customDialog;
    RelativeLayout relativeLayout;

    ImageView iv;
    String bname,type,thumbnail,wname,title,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ebook_playlistview);



        Intent intent2 = getIntent();
        title = intent2.getStringExtra("title");

        Intent intent4 = getIntent();
        thumbnail = intent4.getStringExtra("thumbnail");
        Intent intent3 = getIntent();
        category = intent3.getStringExtra("category");

        iv = (ImageView)findViewById(R.id.imgLogo);

        Glide.with(this)
                .load(thumbnail)
                .into(iv);

        this.setFinishOnTouchOutside(false);
//        logo = findViewById(R.id.imgLogo);
        tt = findViewById(R.id.txtTitle);
        startTime = findViewById(R.id.txtStartTime);
        songTime = findViewById(R.id.txtSongTime);
        ImageButton btnNext = findViewById(R.id.btnNext);
        ImageButton btnPrev = findViewById(R.id.btnPrev);
        ImageButton btnClose = findViewById(R.id.btnClose);
        btnRepeatOne=findViewById(R.id.btnRepeatOne);
        relativeLayout=findViewById(R.id.playerLayout);
        seekBar=findViewById(R.id.sb);
        mPlayer=new MediaPlayer();
        handler=new Handler();
        runnable = this::updateSeekBar;

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if(mPlayer!=null && mPlayer.isPlaying()){
                    mPlayer.stop();
                    mPlayer.release();
                    handler.removeCallbacks(runnable);
                }
                customDialog.dismiss();
                finish();
            }
        });


        try{
            SharedPreferences sp = getSharedPreferences("MyList", Context.MODE_PRIVATE);
            String songList=sp.getString("songList","");
            processJSON(songList);
        }catch (Exception ignored){
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar p1, int p2, boolean user)
            {
                if(user){
                    mPlayer.seekTo(p2);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar p1)
            {
                if(playing){
                    mPlayer.pause();
                    handler.removeCallbacks(runnable);
                }
            }
            @Override
            public void onStopTrackingTouch(SeekBar p1)
            {
                if(playing){
                    try{
                        mPlayer.start();
                        handler.postDelayed(runnable,200);}catch(Exception ignored){}
                }
            }
        });
        btnplay=findViewById(R.id.btnPlay);
        btnplay.setOnClickListener(v -> {
            if(playing){
                playing=false;
                mPlayer.pause();
                btnplay.setImageResource(R.drawable.ebook_ic_baseline_play_arrow_24);
                handler.removeCallbacks(runnable);
            }else{
                playing=true;
                btnplay.setImageResource(R.drawable.ebook_ic_baseline_pause_24);
                playSong();
            }
        });
        btnPrev.setOnClickListener(view -> prevSong());
        btnNext.setOnClickListener(view -> nextSong());
        btnRepeatOne.setOnClickListener(view -> repeatSong());
        customDialog=new EbookCustomDialog(this);
        customDialog.show();

    }
//    private void processJSON(String input) {
//        posts = new ArrayList<>();
//        try {
//            JSONObject jo = new JSONObject(input);
//            JSONArray jarray = jo.getJSONArray("data");
//            for (int j = 0; j < jarray.length(); j++) {
//                EbookSongData songData = new EbookSongData();
//                songData.songname = (jarray.getJSONObject(j).getString("songname"));
//                songData.link = (jarray.getJSONObject(j).getString("downloadlink"));
//                posts.add(songData);
//            }
//        } catch (JSONException ignored) {
//        }

    private void processJSON(String input) {
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
                        String downloadLink = bookObj.getString("link");
                        String bname = bookObj.getString("bname");

                        EbookSongData songData = new EbookSongData();
                        songData.bname = bname;
                        songData.link = downloadLink;
                        posts.add(songData);
                    }
                    break; // Exit loop after finding the matching title
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        addList();
    }
    public void addList(){
        list= new ArrayList<>();
        list.addAll(posts);
        currentSong=getIntent().getIntExtra("position",0);
        loadSong();
    }
    @SuppressLint("DefaultLocale")
    public void  playSong(){
        customDialog.dismiss();
        relativeLayout.setVisibility(View.VISIBLE);
        handler.postDelayed(runnable,200);
        try {
            btnplay.setClickable(true);
            playing=true;
            btnplay.setImageResource(R.drawable.ebook_ic_baseline_pause_24);
            mPlayer.start();
            duration=mPlayer.getDuration();
            seekBar.setMax(duration);
            seekBar.setProgress(0);
            songTime.setText(getDuration(duration));
            updateSeekBar();
        } catch (IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("SetTextI18n")
    private void loadSong(){
        try {
            mPlayer.setAudioAttributes(
                    new AudioAttributes
                            .Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build());
            mPlayer.setDataSource(getApplicationContext(), Uri.parse(list.get(currentSong).link));
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.setOnPreparedListener(mPlayer -> {
            Toast.makeText(getApplicationContext(),"Play",Toast.LENGTH_LONG).show();
            tt.setText(list.get(currentSong).bname);
            playSong();

        });
        mPlayer.setOnCompletionListener(mediaPlayer -> {
            if(repeatOne){
                playSong();
            }else {
                nextSong();
            }
        });
    }
    private void updateSeekBar() {
        seekBar.setProgress(mPlayer.getCurrentPosition());
        startTime.setText(getDuration(mPlayer.getCurrentPosition()));
        handler.postDelayed(runnable, 200);
    }
    public static int getHour(int millisec){
        return (int) TimeUnit.MILLISECONDS.toHours(millisec);
    }
    public static int getMinute(int millisec){
        int minutes=(int)TimeUnit.MILLISECONDS.toMinutes(millisec);
        return minutes%60;
    }
    public static int getSecond(int millisec){
        int secs=(int) TimeUnit.MILLISECONDS.toSeconds(millisec);
        return secs%60;
    }
    public static String getDuration(int millisec){
        String hr=String.valueOf(getHour(millisec));
        String min=String.valueOf(getMinute(millisec));
        String sec=String.valueOf(getSecond(millisec));
        if(hr.length()==1){
            hr="0"+hr;
        }
        if(min.length()==1){
            min="0"+min;
        }
        if(sec.length()==1){
            sec="0"+sec;
        }
        return hr+":"+min+":"+sec;
    }
    public void nextSong(){
        customDialog.show();
        currentSong++;
        if (currentSong == list.size()) {
            currentSong = 0;
        }
        mPlayer.reset();
        loadSong();
    }
    public void prevSong(){
        customDialog.show();
        currentSong--;
        if (currentSong == - 1) {
            currentSong = list.size()-1;
        }
        mPlayer.reset();
        loadSong();
    }
    public void repeatSong(){
        if(repeatOne){
            repeatOne=false;
            btnRepeatOne.setImageResource(R.drawable.ebook_ic_baseline_repeat_one_black);

        }else {
            repeatOne=true;
            btnRepeatOne.setImageResource(R.drawable.ebook_ic_baseline_repeat_one_24);
        }
    }


    @Override
    public void onBackPressed()
    {
        if(mPlayer!=null && mPlayer.isPlaying()){
            mPlayer.stop();
            mPlayer.release();
            handler.removeCallbacks(runnable);
        }
        customDialog.dismiss();
        finish();
        super.onBackPressed();
    }
}
