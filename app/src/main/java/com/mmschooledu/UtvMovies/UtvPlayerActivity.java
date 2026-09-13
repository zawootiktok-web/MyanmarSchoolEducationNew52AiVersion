package com.mmschooledu.UtvMovies;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.mmschooledu.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class UtvPlayerActivity extends Activity {

    SimpleExoPlayer exoPlayer;
    PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umvplayer);


        String link=getIntent().getStringExtra("link");

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
                return "NO Result";
            }
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            playVideo(result);

        }

    }

    public String process(Document doc){

        Elements elements=doc.select("li.col-sm-4.col-xs-3");
        String res="";
        for(Element e:elements){
            String link=e.select("div").first().attr("onclick");
            String playLink=link.substring(44);
            String name=playLink.substring(0,playLink.indexOf("/"));
            //res+=playLink+"\n";
            res="http://www.zegomovie.com/Videos/"+name+"/index.m3u8";


        }
        return res;

    }
    public void playVideo(String link){
        exoPlayer=new SimpleExoPlayer.Builder(this).build();
        try {
            Uri videouri = Uri.parse(link);
            HlsMediaSource.Factory factory = new HlsMediaSource.Factory(
                    new DefaultDataSourceFactory(this, "ExoPlayer"));
            HlsMediaSource mediaSource = factory.createMediaSource(MediaItem.fromUri(videouri));
            playerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
            uiHide();
            playerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uiHide();
                }
            });


        } catch (Exception e) {

            Log.e("TAG", "Error : " + e.toString());
        }
    }
    public void uiHide(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


    @Override
    public void onBackPressed() {
        if(exoPlayer!=null){
            exoPlayer.release();
        }
        super.onBackPressed();
    }

}
