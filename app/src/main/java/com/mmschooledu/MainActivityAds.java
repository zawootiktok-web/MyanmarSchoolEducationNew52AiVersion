package com.mmschooledu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;

public class MainActivityAds extends AppCompatActivity {

    Button showInterstitial, showReword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainads);

//        AdsUnit.BANNER = "ca-app-pub-3940256099942544/6300978111";
//        AdsUnit.INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712";
//        AdsUnit.REWARDED = "ca-app-pub-3940256099942544/5224354917";

        // SDK Initialize

        showInterstitial = findViewById(R.id.showInterstitial);
        showReword = findViewById(R.id.showReword);

        // Show Banner
        com.bdtopcoder.quickadmob.Admob.setBanner(findViewById(R.id.showBanner), MainActivityAds.this);

        // Show Interstitial Ads
        showInterstitial.setOnClickListener(view -> {
            new com.bdtopcoder.quickadmob.Admob(new onDismiss() {
                @Override
                public void onDismiss() {
                    // When Ads Close Take Action
                    Intent i = new Intent(MainActivityAds.this, Main1.class);
                    startActivity(i);
                }
            }).ShowInterstitial(MainActivityAds.this, true);
        });

        // Show Reworded Ads
        showReword.setOnClickListener(view -> {
            new Admob(new onDismiss() {
                @Override
                public void onDismiss() {
                    // when ads watch give some reworded
                }
            }).ShowInterstitial(MainActivityAds.this, true);

        });

    } // OnCreate Method End Here ============
}

