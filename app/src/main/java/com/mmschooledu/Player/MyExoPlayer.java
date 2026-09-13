package com.mmschooledu.Player;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.mmschooledu.R;

//import com.mmschooledu.R;

public class MyExoPlayer extends AppCompatActivity {
    private ExoPlayer player;
    //    String videoURL="https://video.blender.org/download/videos/3d95fb3d-c866-42c8-9db1-fe82f48ccb95-804.mp4";
    //String link;
    String url;
    //String videoURL="link";
    String videoURL="url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myplayer_view_new);
//        videoURL=getIntent().getStringExtra("link");
        videoURL=getIntent().getStringExtra("url");
        StyledPlayerView playerView = findViewById(R.id.player_view);

        ImageButton rotate = findViewById(R.id.rotatenew);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rotation = getWindowManager().getDefaultDisplay().getRotation();
                if (rotation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });



        player = new ExoPlayer.Builder(MyExoPlayer.this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(videoURL);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.setPlayWhenReady(false);
        player.release();
        player = null;
    }
}