package com.mmschooledu.Player;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.mmschooledu.R;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.interfaces.ILibVLC;
import org.videolan.libvlc.util.VLCVideoLayout;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class VlcPlayer extends Activity {

    VLCVideoLayout videoLayout;
    LibVLC mLibVlc;
    MediaPlayer mMediaPlayer;

    ILibVLC libvlc;

//    String link;
//String finalUrl="link";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_vlc_layout_new);
        videoLayout = findViewById(R.id.view_vlc_layout);

        String link= getIntent().getStringExtra("finalUrl");
        try {
            initVideoPlayer(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    private void initVideoPlayer(String link) throws MalformedURLException {
        // Configure LibVLC arguments
        ArrayList<String> args = new ArrayList<>();
        args.add("--file-caching=2000");
        args.add("-vvv");
        args.add("--fullscreen");
        // Add more arguments as needed

        // Initialize LibVLC
        mLibVlc = new LibVLC(VlcPlayer.this, args);
        mMediaPlayer = new MediaPlayer(mLibVlc);

        // Attach video views
        mMediaPlayer.attachViews(videoLayout, null, true, true);

        // Create media from the provided link
        Uri movie = Uri.parse(link);
        Media media = new Media(mLibVlc, movie);

        // Configure media options
        media.setHWDecoderEnabled(true,false);
        media.addOption(":network-caching=150");
        media.addOption(":fullscreen");
        // Add more options as needed

        // Set the media to the media player
        mMediaPlayer.setMedia(media);

        // Set aspect ratio and scale of the video
        mMediaPlayer.setAspectRatio("16:9");
        mMediaPlayer.setScale(1.8f);

        // Release media
        media.release();

        // Start playing the video
        mMediaPlayer.play();
    }

    @Override
    public void onBackPressed() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mLibVlc.release();
        }
        finish();
        super.onBackPressed();
    }

}
