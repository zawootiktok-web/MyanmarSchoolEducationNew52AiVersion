package com.mmschooledu;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.ump.ConsentInformation;
import com.mmschooledu.Cinema.CinemaLessonActivity;
import com.mmschooledu.EbookAudioShelf.EbookMainActivity;
import com.mmschooledu.Mp3.Mp3Mp4MainActivity;
import com.mmschooledu.QandAnswer.QandAActivity;
//
//import java.util.Objects;
//import java.util.concurrent.atomic.AtomicBoolean;

import com.mmschooledu.R;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)


public class Main extends AppCompatActivity {
    private static final int STORAGE_REQUEST_CODE = 100; // Any unique integer

    //private static final int STORAGE_REQUEST_CODE =101 ;
    Constant constant;
    SharedPreferences.Editor editor;
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    private Handler handler;
    private Runnable geolocationRunnable;
    private Runnable dialogRunnable;


    private Runnable showDialogRunnable;

    private ConsentInformation consentInformation;

    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);
    final int storageRequestCode = 123;
    private static final int resultCode = 0;
    CardView kg, kg2, kg3, kg4, kg5, kg6, kg7, kg8, kg9, kg10;

    TextView text01, text02, text, text2, text3, text4, text5, text6, text7, text8, text9, text10;

    private BlogspotAPIManager blogspotAPIManager;

    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            boolean show = intent.getBooleanExtra("show", true);
            if (show) {
                // Display the message to the user
                Toast.makeText(Main.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLocale.applySaved(this);
        setContentView(R.layout.main_main);

        // VPN/country verification must complete before normal app use.
        GeolocationUtils.checkCountryAndShowDialog(Main.this);


// Initialize BlogspotAPIManager
        blogspotAPIManager = new BlogspotAPIManager(this);



        // requestConsform();

        //  new CountryCheckAsyncTask().execute();

        String check_update_json = "https://2020goodblog.blogspot.com/2022/07/myanmarschooleducationapk.html";
        AppUpdater appUpdater = new AppUpdater(this, check_update_json);
        boolean shouldShowDialog = false;
        appUpdater.check(shouldShowDialog);

      //  requestAppPermissions();


        // Register the BroadcastReceiver to receive message status updates
        IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
        registerReceiver(messageStatusReceiver, intentFilter, android.content.Context.RECEIVER_NOT_EXPORTED);



        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;
        Constant.color = appColor;

        getWindow().getDecorView().setBackgroundColor(Constant.color);

        if (themeColor == 0) {
            setTheme(Constant.theme);
        } else if (appTheme == 0) {
            setTheme(Constant.theme);
        } else {
            setTheme(appTheme);
        }

        // SDK Initialize
        Admob.loadInterstitialAds(Main.this);
//        Admob.loadRewordedAds(Main.this);
        Admob.loadRewardedInterstitialAds(Main.this);
        Admob.loadOpenAd(Main.this);

        //requestAppPermissions();
        text01 = (TextView) findViewById(R.id.text01);
        text02 = (TextView) findViewById(R.id.text02);
        text = (TextView) findViewById(R.id.text);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        text5 = (TextView) findViewById(R.id.text5);
        text6 = (TextView) findViewById(R.id.text6);
        text7 = (TextView) findViewById(R.id.text7);
        text8 = (TextView) findViewById(R.id.text8);

        text9 = (TextView) findViewById(R.id.text9);
        text10 = (TextView) findViewById(R.id.text10);



        // Check ads status
        blogspotAPIManager.checkAdsStatus(new BlogspotAPIManager.OnAdsStatusListener() {
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
//				Toast.makeText(Main1.this, "Start.io ads are Enabled", Toast.LENGTH_SHORT).show();
                //StartAppAd.showAd(getBaseContext());
                Intent i = new Intent(Main.this, AdsActivity.class);
                startActivity(i);
            }

            private void hideAds() {
                // Hide ads or take appropriate action
              //  Toast.makeText(Main.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
            }
        });




        applyDashboardLocalization();

        kg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
//
//                Intent i=new Intent(Main.this,TextBook.class);
//                startActivity(i);
//            }
//        });

                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        // When Ads Close Take Action
                        Intent i = new Intent(Main.this, TextBook.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Main.this, true);
            }

            ;
        });
        kg2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        // When Ads Close Take Action
                        Intent i = new Intent(Main.this, TeacherGuide.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Main.this, true);
            }

            ;
        });


        kg3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {

                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        // When Ads Close Take Action
                        Intent i = new Intent(Main.this, LessonActivity.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Main.this, true);
            }

            ;
        });


        kg4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {




                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        // When Ads Close Take Action
                        Intent i = new Intent(Main.this, STeachingBook.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Main.this, true);
            }

            ;
        });

        kg5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {

//                Intent i=new Intent(Main.this,QandAActivity.class);
//                startActivity(i);
//            }
//        });


//                new com.bdtopcoder.quickadmob.Admob(new onDismiss() {
//                    @Override
//                    public void onDismiss() {

                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() { // When Ads Close Take Action
                        Intent i = new Intent(Main.this, QandAActivity.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Main.this, true);
            }

            ;
        });

        kg6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {

//                Intent i=new Intent(Main.this,Main2.class);
//                startActivity(i);
//
//            }
//        });

                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        // When Ads Close Take Action
                        Intent i = new Intent(Main.this, Mp3Mp4MainActivity.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Main.this, true);
            }

            ;
        });


        kg7.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {

//                Intent i=new Intent(Main.this,EbookMainActivity.class);
//                startActivity(i);
//            }
//        });
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        // When Ads Close Take Action
                        Intent i = new Intent(Main.this, EbookMainActivity.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Main.this, true);
            }

            ;
        });


        kg8.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {

//                Intent i=new Intent(Main.this,CinemaLessonActivity.class);
//                startActivity(i);
//
//            }
//        });
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        // When Ads Close Take Action
                        Intent i = new Intent(Main.this, CinemaLessonActivity.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Main.this, true);
            }

            ;
        });


        kg9.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {

//                Intent i=new Intent(Main.this,Main1.class);
//                startActivity(i);
//
//            }
//        });
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        // When Ads Close Take Action
                       // Intent i = new Intent(Main.this, Main1.class);
                        Intent i = new Intent(Main.this, Main1.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Main.this, true);
            }

            ;
        });


        kg10.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {

                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {

                        Intent i = new Intent(Main.this, Main3.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(Main.this, true);
            }

            ;
        });


    }


    private void applyDashboardLocalization() {
        text01.setText(getString(R.string.text01new));
        text02.setText(getString(R.string.text02new));
        text.setText(getString(R.string.textnew));
        text2.setText(getString(R.string.text2new));
        text3.setText(getString(R.string.text3new));
        text4.setText(getString(R.string.text4new));
        text5.setText(getString(R.string.text5new));
        text6.setText(getString(R.string.text6new));
        text7.setText(getString(R.string.text7new));
        text8.setText(getString(R.string.text8new));
        text9.setText(getString(R.string.text9new));
        text10.setText(getString(R.string.text10new));
    }

    private void showNoInternet() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View parent_view = LayoutInflater.from(this).inflate(R.layout.nointernet_dia, null);
        dialog.setView(parent_view);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        // Finding Views inside dialog
        TextView tv_title = (TextView) dialog.findViewById(R.id.dialogTextView1);
        Button button = (Button) dialog.findViewById(R.id.dialogButton1);

        tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ခြင်းမရှိပါ။ ​\u200Bကျေးဇူး​ပြု၍ အင်တာနက် ဆက်သွယ်​\u200Bပေးပါ။");

        button.setText("...ဟုတ်ကဲ့...");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                //dialog.dismiss();
                finish();
            }
        });
    }


}

