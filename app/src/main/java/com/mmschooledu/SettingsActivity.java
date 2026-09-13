package com.mmschooledu;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.mmschooledu.EbookAudioShelf.EbookGalleryActivity;
import com.mmschooledu.Mp3.AudioOpenActivity;
import com.mmschooledu.Mp4.VideoOpenActivity;
import com.startapp.sdk.adsbase.StartAppAd;

import java.util.Objects;

//import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
//import com.turkialkhateeb.materialcolorpicker.ColorListener;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences, app_preferences;
    SharedPreferences.Editor editor;
    Button button;
    ImageView imageView,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView22,imageView33;
    Methods methods;

    int appTheme;
    int themeColor;
    int appColor;

    //Toolbar tb;

    private BlogspotAPIManagerStartAds blogspotAPIManagerStardads;

    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            boolean show = intent.getBooleanExtra("show", true);
            if (show) {
                // Display the message to the user
                Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;
        Constant.color = appColor;

        if (themeColor == 0){
            setTheme(Constant.theme);
        }else if (appTheme == 0){
            setTheme(Constant.theme);
        }else{
            setTheme(appTheme);
        }
        setContentView(R.layout.activity_settings);


// Initialize BlogspotAPIManager
        blogspotAPIManagerStardads = new BlogspotAPIManagerStartAds(this);



        // Register the BroadcastReceiver to receive message status updates
        IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(messageStatusReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }


        // Check ads status
        blogspotAPIManagerStardads.checkAdsStatus(new BlogspotAPIManagerStartAds.OnAdsStatusListener() {
            @Override
            public void onAdsStatus(boolean isEnabled) {
                if (isEnabled) {
                    // Show ads
                    showAds();
                } else {
                    // Hide ads
                    hideAds();
                }
            }

            private void showAds() {
                // Load and show ads here
                //Toast.makeText(TextIndexActivity.this, "Start.io ads are Enabled", Toast.LENGTH_SHORT).show();
                StartAppAd.showAd(getBaseContext());
//                startAppAd.loadAd(AdMode.REWARDED_VIDEO);
//                Intent i = new Intent(TextIndexActivity.this, AdsActivity.class);
//                startActivity(i);
            }

            private void hideAds() {
                // Hide ads or take appropriate action
               // Toast.makeText(SettingsActivity.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
            }
        });




        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
       toolbar.setTitle(R.string.settings_title);
        setSupportActionBar(toolbar);

//        tb = (Toolbar) findViewById(R.id.toolbar_setting);
//        setSupportActionBar(tb);
       Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        tb.setTitle("Settings");


        toolbar.setBackgroundColor(Constant.color);
        methods = new Methods();

        imageView = (ImageView) findViewById(R.id.image);
        imageView2 = (ImageView) findViewById(R.id.image2);
        imageView3 = (ImageView) findViewById(R.id.image3);
        imageView4 = (ImageView) findViewById(R.id.image4);
        imageView5 = (ImageView) findViewById(R.id.image5);
        imageView6 = (ImageView) findViewById(R.id.image6);
        imageView7 = (ImageView) findViewById(R.id.image7);
        imageView22 = (ImageView) findViewById(R.id.image22);
        imageView33 = (ImageView) findViewById(R.id.image33);

        button = (Button) findViewById(R.id.button_color);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        colorize();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ColorChooserDialog dialog = new ColorChooserDialog(SettingsActivity.this);
                dialog.setTitle(R.string.select_language);
                dialog.setColorListener(new ColorListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        colorize();
                        Constant.color = color;

                        methods.setColorTheme();
                        editor.putInt("color", color);
                        editor.putInt("theme",Constant.theme);
                        editor.commit();

                        Intent intent = new Intent(SettingsActivity.this, Main.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                dialog.show();
            }
        });


        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                Intent intent=new Intent(SettingsActivity.this, ChatActivity.class);
                startActivity(intent);

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                Intent intent=new Intent(SettingsActivity.this, EbookGalleryActivity.class);
                startActivity(intent);

            }
        });

        imageView22.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                Intent intent=new Intent(SettingsActivity.this, VideoOpenActivity.class);
                startActivity(intent);

            }
        });

        imageView3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                Intent intent=new Intent(SettingsActivity.this, ImageViewerActivity.class);
                startActivity(intent);

            }
        });

        imageView33.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                Intent intent=new Intent(SettingsActivity.this, AudioOpenActivity.class);
                startActivity(intent);

            }
        });

        imageView4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {

                        openTelegramLink(SettingsActivity.this);
                    }

                    private void openTelegramLink(Context context) {
                        String telegramLink = "https://t.me/myanmarschooleducation";
                        Intent openTelegram = new Intent(Intent.ACTION_VIEW, Uri.parse(telegramLink));
                        openTelegram.addCategory(Intent.CATEGORY_BROWSABLE);

                        try {
                            startActivity(openTelegram);
                        } catch (ActivityNotFoundException e) {
                            Uri websiteUri = Uri.parse(telegramLink);
                            Intent openBrowser = new Intent(Intent.ACTION_VIEW, websiteUri);
                            startActivity(openBrowser);
                        }
                    }
                }).ShowInterstitial(SettingsActivity.this, true);
            };});

                imageView5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {

                        openYouTubeLink(SettingsActivity.this);
                    }
                    private void openYouTubeLink(Context context) {
                        // Replace "YOUR_YOUTUBE_VIDEO_ID" with the actual video ID or the complete video URL
                        String youtubeLink = "https://youtube.com/@myanmarschooleducation9624?si=h9sOaO7Yl1n7aK6t";
                        Intent openYouTube = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink));

                        // Ensure it opens in the YouTube app if available, otherwise open in browser
                        openYouTube.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        try {
                            startActivity(openYouTube);
                        } catch (ActivityNotFoundException e) {
                            // If YouTube app is not available, open the YouTube website
                            Uri websiteUri = Uri.parse(youtubeLink);
                            Intent openBrowser = new Intent(Intent.ACTION_VIEW, websiteUri);
                            startActivity(openBrowser);
                        }
                    }
                }).ShowInterstitial(SettingsActivity.this, true);
            };});

        imageView6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        openFacebookLink(SettingsActivity.this);
                    }

                    private void openFacebookLink(Context context) {
                        // Replace "YOUR_FACEBOOK_PAGE_ID" with the actual page ID or the complete page URL
                        String facebookLink = "https://www.facebook.com/profile.php?id=100088521429730&mibextid=ZbWKwL";
                        Intent openFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookLink));

                        // Ensure it opens in the Facebook app if available, otherwise open in browser
                        openFacebook.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        try {
                            startActivity(openFacebook);
                        } catch (ActivityNotFoundException e) {
                            // If Facebook app is not available, open the Facebook website
                            Uri websiteUri = Uri.parse(facebookLink);
                            Intent openBrowser = new Intent(Intent.ACTION_VIEW, websiteUri);
                            startActivity(openBrowser);
                        }
                    }
                }).ShowInterstitial(SettingsActivity.this, true);
            };});



        imageView7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                Intent intent=new Intent(SettingsActivity.this, AboutUsActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void colorize(){
        ShapeDrawable d = new ShapeDrawable(new OvalShape());
        d.setBounds(58, 58, 58, 58);

        d.getPaint().setStyle(Paint.Style.FILL);
        d.getPaint().setColor(Constant.color);

        button.setBackground(d);
    }
}

