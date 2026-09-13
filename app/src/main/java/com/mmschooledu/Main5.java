package com.mmschooledu;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.mmschooledu.Grammar.SpecialGrammarActivity;
import com.mmschooledu.Grammar.SpecialGrammarActivity2;
import com.mmschooledu.Grammar.SpecialGrammarActivity3;
import com.mmschooledu.Grammar.SpecialGrammarActivity4;
import com.startapp.sdk.adsbase.StartAppAd;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class Main5 extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private AdView adView;


    CardView kg, kg2, kg3, kg4, kg5, kg6, kg7, kg8;

//
//    String[] required_permissions = new String[]{
//            android.Manifest.permission.READ_MEDIA_IMAGES,
//            android.Manifest.permission.READ_MEDIA_VIDEO,
//            android.Manifest.permission.READ_MEDIA_AUDIO
//    };

    boolean is_storage_image_permitted = false;
    boolean is_storage_video_permitted = false;
    boolean is_storage_audio_permitted = false;

    String TAG = "Permission";




    private BlogspotAPIManagerStartAds blogspotAPIManagerStardads;

    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            boolean show = intent.getBooleanExtra("show", true);
            if (show) {
                // Display the message to the user
                Toast.makeText(Main5.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_grammar);
//        GeolocationUtils.checkCountryAndShowDialog(this);


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
                Toast.makeText(Main5.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
            }
        });





        kg = (CardView) findViewById(R.id.kg);
        kg2 = (CardView) findViewById(R.id.kg2);
        kg3 = (CardView) findViewById(R.id.kg3);
        kg4 = (CardView) findViewById(R.id.kg4);
        kg5 = (CardView) findViewById(R.id.kg5);
        kg6 = (CardView) findViewById(R.id.kg6);

//        if (!allPermissionsResultCheck()) {
//            requestPermissionsStorageImages();
//
//        } else {
//            Toast.makeText(Main5.this, "All Storage Permissions Granted...", Toast.LENGTH_SHORT).show();
//        }

//
//        AdsUnit.BANNER = "R.string.banner_ad_unit_id";
//        AdsUnit.INTERSTITIAL = "R.string.admob_interstitial_id";
//        AdsUnit.REWARDED = "A.string.reward_ad_unit_id";

        Admob.loadInterstitialAds(Main5.this);
//        Admob.loadRewordedAds(Main5.this);

        adView = (AdView)
                findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        kg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        String newtitle = "Special Grammar Books(1)";
                        //String link = "https://sania.worksheetpoint.com/all-pdf-books/";
                        Intent intent = new Intent(Main5.this, SpecialGrammarActivity3.class);
                       //intent.putExtra("link", link);
                        intent.putExtra("newtitle", newtitle);
                        startActivity(intent);

                    }
                }).ShowInterstitial(Main5.this, true);
            }

            ;
        });


        //adView = (AdView)
        //   findViewById(R.id.ad_view);
     /*   AdRequest adRequest = new AdRequest.Builder().build();
        //adView.loadAd(adRequest);
        InterstitialAd.load(this, getResources().getString(R.string.admob_interstitial_id), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        displayInterstitial();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });*/


        kg2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View p1) {
//                String newtitle = "Special Grammar Books(2)";
//                String link = "https://english.pdfdrive.com.pk/all-books";
//                Intent intent = new Intent(Main5.this, SpecialGrammarActivity.class);
//                intent.putExtra("link", link);
//                intent.putExtra("newtitle", newtitle);
//                startActivity(intent);
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        String newtitle = "Special Grammar Books(2)";
                        // String link = "https://uk.fims.org.pk/all-pdf-books";
                        Intent intent = new Intent(Main5.this, SpecialGrammarActivity4.class);
                        // intent.putExtra("link", link);
                        intent.putExtra("newtitle", newtitle);
                        startActivity(intent);

                    }
                }).ShowInterstitial(Main5.this, true);
            }

            ;
        });


        kg3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        String newtitle = "Special Grammar Books(3)";
                        String link = "https://uk.fims.org.pk/all-pdf-books";
                        Intent intent = new Intent(Main5.this, SpecialGrammarActivity2.class);
                        intent.putExtra("link", link);
                        intent.putExtra("newtitle", newtitle);
                        startActivity(intent);

                    }
                }).ShowInterstitial(Main5.this, true);
            }

            ;
        });

        kg4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {

                        String newtitle = "Special Grammar Books(4)";
                        String link = "https://uk.fims.org.pk/all-pdf-books";
                        Intent intent = new Intent(Main5.this, SpecialGrammarActivity.class);
                        intent.putExtra("link", link);
                        intent.putExtra("newtitle", newtitle);
                        startActivity(intent);

                    }
                }).ShowInterstitial(Main5.this, true);
            }

            ;
        });

        kg5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        String newtitle = "Special Grammar Books(3)";
                        String link = "https://pk.fimsschools.com/all-pdf-books";
                        Intent intent = new Intent(Main5.this, SpecialGrammarActivity.class);
                        intent.putExtra("link", link);
                        intent.putExtra("newtitle", newtitle);
                        startActivity(intent);

                    }
                }).ShowInterstitial(Main5.this, true);
            }

            ;
        });

        kg6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {

                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        String newtitle = "Special Grammar Books(6)";
                        String link = "https://english.pdfdrive.com.pk/all-books";
                        Intent intent = new Intent(Main5.this, SpecialGrammarActivity.class);
                        intent.putExtra("link", link);
                        intent.putExtra("newtitle", newtitle);
                        startActivity(intent);

                    }
                }).ShowInterstitial(Main5.this, true);
            }

            ;
        });
    }}

    //    private boolean allPermissionsResultCheck() {
//    }
////
//    public boolean allPermissionsResultCheck() {
//        return is_storage_image_permitted && is_storage_video_permitted && is_storage_audio_permitted;
//    }
//
//
//    public void requestPermissionsStorageImages() {
//        if (ContextCompat.checkSelfPermission(Main5.this, required_permissions[0]) == PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, required_permissions[0] + " Granted");
////
////                    is_storage_image_permitted = true;
////                    img_deny_image.setVisibility(View.GONE);
////                    img_given_image.setVisibility(View.VISIBLE);
//
//            if (!allPermissionsResultCheck()) {
//                requestPermissionsStorageVideo();
//            }
//        } else { //new android 13 code after onActivityResult is deprecated, now ActivityResultLauncher...
//            request_permission_launcher_storage_images.launch(required_permissions[0]);
//
//        }
//    }
//
//    private ActivityResultLauncher<String> request_permission_launcher_storage_images =
//            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
//                    isGranted -> {
//                        if (isGranted)  //java8
//                        {
//                            Log.d(TAG, required_permissions[0] + " Granted");
////                                    is_storage_image_permitted = true;
////                                    img_deny_image.setVisibility(View.GONE);
////                                    img_given_image.setVisibility(View.VISIBLE);
//                        } else {
//                            Log.d(TAG, required_permissions[0] + " Not Granted");
//                            is_storage_image_permitted = false;
//                        }
//                        if (!allPermissionsResultCheck()) {
//                            requestPermissionsStorageVideo();
//                        }
//                    });
//
//
//    public void requestPermissionsStorageVideo() {
//
//        if (ContextCompat.checkSelfPermission(Main5.this, required_permissions[1]) == PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, required_permissions[1] + " Granted");
//
////                    is_storage_video_permitted = true;
////                    img_deny_video.setVisibility(View.GONE);
////                    img_given_video.setVisibility(View.VISIBLE);
//
//            if (!allPermissionsResultCheck()) {
//                requestPermissionsStorageAudio();
//            }
//        } else { //new android 13 code after onActivityResult is deprecated, now ActivityResultLauncher...
//            request_permission_launcher_storage_video.launch(required_permissions[1]);
//        }
//    }
//
//    private ActivityResultLauncher<String> request_permission_launcher_storage_video =
//            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
//                    isGranted -> {
//                        if (isGranted)  //java8
//                        {
//                            Log.d(TAG, required_permissions[1] + " Granted");
////
////                                    is_storage_video_permitted = true;
////                                    img_deny_video.setVisibility(View.GONE);
////                                    img_given_video.setVisibility(View.VISIBLE);
//                        } else {
//                            Log.d(TAG, required_permissions[1] + " Not Granted");
//                            is_storage_video_permitted = false;
//                        }
//                        if (!allPermissionsResultCheck()) {
//                            requestPermissionsStorageAudio();
//                        }
//                    });
//
//    ///   code for read media videos ends
//
//
//    public void requestPermissionsStorageAudio() {
//        if (ContextCompat.checkSelfPermission(Main5.this, required_permissions[2]) == PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, required_permissions[2] + " Granted");
////                    is_storage_audio_permitted = true;
////                    img_deny_audio.setVisibility(View.GONE);
////                    img_given_audio.setVisibility(View.VISIBLE);
//
//        } else { //new android 13 code after onActivityResult is deprecated, now ActivityResultLauncher...
//            request_permission_launcher_storage_audio.launch(required_permissions[2]);
//        }
//    }
//
//    private ActivityResultLauncher<String> request_permission_launcher_storage_audio =
//            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
//                    isGranted -> {
//                        if (isGranted)  //java8
//                        {
//                            Log.d(TAG, required_permissions[2] + " Granted");
//
////                                    is_storage_audio_permitted = true;
////                                    img_deny_audio.setVisibility(View.GONE);
////                                    img_given_audio.setVisibility(View.VISIBLE);
//                        } else {
//                            Log.d(TAG, required_permissions[2] + " Not Granted");
//                            is_storage_audio_permitted = false;
//                            sendToSettingDialog();
//                        }
//
//                    });
//
//    public void sendToSettingDialog() {
//        new AlertDialog.Builder(Main5.this)
//                .setTitle("Alert for Permission")
//                .setMessage("Go to Settings for Permissions")
//                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ///  code to go to settings of application
//                        Intent intent = new Intent();
//                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                        intent.setData(uri);
//                        startActivity(intent);
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        finish();
//                    }
//                }).show();
//
//    }
//}
   /* public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (mInterstitialAd!=null) {
            mInterstitialAd.show(this);
        }
}
}*/
//            public boolean allPermissionsResultCheck() {
//                return is_storage_image_permitted && is_storage_video_permitted && is_storage_audio_permitted;
//            }
//
//
//        }}




