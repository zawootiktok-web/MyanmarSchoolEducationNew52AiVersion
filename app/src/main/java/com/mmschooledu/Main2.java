package com.mmschooledu;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.mmschooledu.Mp3.AudioLessonActivity;
import com.mmschooledu.Mp4.VideoLessonActivity;

public class Main2 extends AppCompatActivity {
    private NoInternetDialog noInternetDialog;

    private RewardedInterstitialAd rewardedInterstitialAd;
    private AdView adView;
    final int storageRequestCode=123;
    private static final int resultCode = 0;
    CardView kg,kg2,kg3,kg4,kg5,kg6,kg7,kg8;


    // private static final int REQUEST_READ_MEDIA_AUDIO = 1;


    //   Button showInterstitial, showReword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
//        GeolocationUtils.checkCountryAndShowDialog(this);
        requestAppPermissions();

//        MobileAds.initialize(this);
//
//        loadRewardedInterstitialAd();

        noInternetDialog = new NoInternetDialog(this);
        checkInternetConnection();


        Admob.loadInterstitialAds(Main2.this);
//        Admob.loadRewordedAds(Main2.this);

        //        ActivityCompat.requestPermissions(this,
//                new String[]{READ_MEDIA_IMAGES, WRITE_EXTERNAL_STORAGE},
//                PackageManager.PERMISSION_GRANTED);


        kg = (CardView) findViewById(R.id.kg);
        kg2 = (CardView) findViewById(R.id.kg2);


        adView = (AdView)
                findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

//requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);


        kg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {


                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i = new Intent(Main2.this, AudioLessonActivity.class);
                startActivity(i);
               // AdHelper.showRewardedAd(Main2.this);
                 }
                }).ShowInterstitial(Main2.this, true);
            }

            ;
        });
       // AdHelper.loadRewardedAd(this);



//                if (rewardedInterstitialAd != null) {
//                    rewardedInterstitialAd.show(Main2.this, new OnUserEarnedRewardListener() {
//                        @Override
//                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//                            //
//                            //loadRewardedInterstitialAd();
//                            RewardedAdManager.showRewardedInterstitialAd(this);
//                        }
//                    });
//                } else {
//                    Intent i = new Intent(Main2.this, AudioLessonActivity.class);
//                    startActivity(i);
//                }
//            }
//        });


        kg2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        Intent i = new Intent(Main2.this, VideoLessonActivity.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Main2.this, true);
            }

            ;
        });
    }

//    private void loadRewardedInterstitialAd() {
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        //tesd id ca-app-pub-3940256099942544/5354046379
//
//        RewardedInterstitialAd.load(Main2.this, "ca-app-pub-3940256099942544/5354046379", adRequest, new RewardedInterstitialAdLoadCallback() {
//            @Override
//            public void onAdLoaded(@NonNull RewardedInterstitialAd ad) {
//                rewardedInterstitialAd = ad;
//                rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                    @Override
//                    public void onAdShowedFullScreenContent() {
//                        // Called when ad is shown.
//
//                    }
//
//                    @Override
//                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                        // Called when ad fails to show.
//                    }
//
//                    @Override
//                    public void onAdDismissedFullScreenContent() {
//                        // Called when ad is dismissed.
//                    }
//                });
//            }
//
//            @Override
//            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                rewardedInterstitialAd = null;
//            }
//        });
//    }


    private void requestAppPermissions() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(Main2.this,new String[]{
                    android.Manifest.permission.READ_MEDIA_AUDIO,
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_VIDEO
            },resultCode);
            //  request13Permission();

            // Toast.makeText(this, "You Need To Accept Storage Permission.", Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "Storage Permission ​ပေးပြီး​သော​ကြောင့် ကြိုက်ရာကို Download ရယူနိုင်ပါပြီ", Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(Main2.this,new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            },resultCode);
            //requestPermission();

            // Toast.makeText(this, "You Need To Accept Storage Permission.", Toast.LENGTH_SHORT).show();
            //  Toast.makeText(this, "Storage Permission ​ပေးပြီး​သော​ကြောင့် ကြိုက်ရာကို Download ရယူနိုင်ပါပြီ", Toast.LENGTH_SHORT).show();
        }


    }




    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void request13Permission(){

        if( ContextCompat.checkSelfPermission(Main2.this, android.Manifest.permission.READ_MEDIA_AUDIO)== PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(Main2.this, android.Manifest.permission.READ_MEDIA_IMAGES)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(Main2.this, android.Manifest.permission.READ_MEDIA_VIDEO)== PackageManager.PERMISSION_GRANTED
        ){
            showAlert("Great!!","You did made permission request.");
        }else{
            ActivityCompat.requestPermissions(Main2.this,new String[]{android.Manifest.permission.READ_MEDIA_AUDIO, android.Manifest.permission.READ_MEDIA_IMAGES, android.Manifest.permission.READ_MEDIA_VIDEO},storageRequestCode);
        }

    }
    public void requestPermission(){
        if(ContextCompat.checkSelfPermission(Main2.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(Main2.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
        ){
            showAlert("Great!!","You did made permission request.");
        }else{
            ActivityCompat.requestPermissions(Main2.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},storageRequestCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==storageRequestCode) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (grantResults.length == 3 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED &&  grantResults[1] == PackageManager.PERMISSION_GRANTED &&  grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You can press Button now.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "You Need To Accept 13 Permission.", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You can press Button now.", Toast.LENGTH_LONG).show();
                } else {
                    //  Toast.makeText(this, "You Need To Accept Storage Permission.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void showAlert(String title, String message){
        new android.app.AlertDialog.Builder(Main2.this)
                .setTitle("Alert for Permission")
                .setMessage("Go to Settings for Permissions")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///  code to go to settings of application
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // Toast.makeText(Main2.this, "Setting မှ Storage Permission ဖွင့်\u200Bပေးမှ စာအုပ်များ Down ယူရရှိနိုင်ပါမည်...", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).show();


    }
    private void checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {
                // Show the no internet dialog if there's no internet connection
                noInternetDialog.showNoInternet();
            }
        }}}



