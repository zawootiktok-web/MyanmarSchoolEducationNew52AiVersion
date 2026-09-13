package com.mmschooledu;


import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.mmschooledu.Lesson.EngEssayActivity;
import com.mmschooledu.Lesson.Grade10Activity;
import com.mmschooledu.Lesson.Grade11Activity;
import com.mmschooledu.Lesson.Grade12Activity;
import com.mmschooledu.Lesson.Grade2Activity;
import com.mmschooledu.Lesson.Grade3Activity;
import com.mmschooledu.Lesson.Grade4Activity;
import com.mmschooledu.Lesson.Grade5Activity;
import com.mmschooledu.Lesson.Grade6Activity;
import com.mmschooledu.Lesson.Grade7Activity;
import com.mmschooledu.Lesson.Grade8Activity;
import com.mmschooledu.Lesson.Grade9Activity;
import com.mmschooledu.Lesson.KgActivity;
import com.mmschooledu.Lesson.KnowledgeActivity;
import com.mmschooledu.Lesson.LessonPlanActivity;
import com.mmschooledu.Lesson.MainActivityNew;
import com.mmschooledu.Lesson.MmEssayActivity;
import com.mmschooledu.Lesson.StoryActivity;
import com.mmschooledu.Lesson.TalentActivity;
import com.startapp.sdk.adsbase.StartAppAd;


public class LessonActivity extends AppCompatActivity {

    AudienceNetwork audienceNetwork;
    private InterstitialAd mInterstitialAd;
    private AdView adView;

    private InterstitialAd interstitial;

    CardView newlessonplan,tguide,knowledge,talent,story,mmessay,engessay,kg,grade1,grade2,grade3,grade4,grade5,grade6,grade7,grade8,grade9,grade10,grade11,grade12;

    private BlogspotAPIManagerFbAds blogspotAPIManagerFbads;
    private com.facebook.ads.InterstitialAd interstitialAd;
    private BlogspotAPIManagerStartAds blogspotAPIManagerStardads;

    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            boolean show = intent.getBooleanExtra("show", true);
            if (show) {
                // Display the message to the user
                Toast.makeText(LessonActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_new);
//        GeolocationUtils.checkCountryAndShowDialog(this);

        audienceNetwork = new AudienceNetwork(this)
                .setFullScreenStatus(true)
               // .setBannerStatus(true)
                .init()
                //.setInterstitialId("VID_HD_16_9_15S_APP_INSTALL#YOUR_PLACEMENT_ID")
                .setInterstitialId("1072549720831975_1325325095554435")

    //   .setBannerId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
               // .setBannerLayoutId(R.id.ad_container)
                .setClick(1)
                .build();


// Initialize BlogspotAPIManager
        blogspotAPIManagerStardads = new BlogspotAPIManagerStartAds(this);

        blogspotAPIManagerFbads = new BlogspotAPIManagerFbAds(this);





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
               // Toast.makeText(LessonActivity.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
            }
        });


        // Check ads status
        blogspotAPIManagerFbads.checkAdsStatus(new BlogspotAPIManagerFbAds.OnAdsStatusListener() {
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
                //  StartAppAd.showAd(getBaseContext());
//                startAppAd.loadAd(AdMode.REWARDED_VIDEO);
//                Intent i = new Intent(TextIndexActivity.this, AdsActivity.class);
//                startActivity(i);

                loadInterstitialAd();
            }

            private void hideAds() {
                // Hide ads or take appropriate action
                Toast.makeText(LessonActivity.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
            }
        });




        adView = (AdView)
		 findViewById(R.id.ad_view);
		 AdRequest adRequest = new AdRequest.Builder().build();
		 adView.loadAd(adRequest);


/*
		 // Prepare the Interstitial Ad
		 interstitial = new InterstitialAd(Main1.this);
		 // Insert the Ad Unit ID
		 interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

		 interstitial.loadAd(adRequest);
		 // Prepare an Interstitial Ad Listener
		 interstitial.setAdListener(new AdListener() {
		 public void onAdLoaded() {
		 // Call displayInterstitial() function
		 displayInterstitial();
		 }
		 });
		 */
        Admob.loadInterstitialAds(LessonActivity.this);
//        Admob.loadRewordedAds(LessonActivity.this);


        newlessonplan=(CardView)findViewById(R.id.newlessonplan);
        tguide=(CardView)findViewById(R.id.tguide);
        knowledge=(CardView)findViewById(R.id.knowledge);
        talent=(CardView)findViewById(R.id.talent);
        story=(CardView)findViewById(R.id.story);
        mmessay=(CardView)findViewById(R.id.mmessay);
        engessay=(CardView)findViewById(R.id.engessay);
        kg=(CardView)findViewById(R.id.kg);
        grade1=(CardView)findViewById(R.id.grade1);
        grade2=(CardView)findViewById(R.id.grade2);
        grade3=(CardView)findViewById(R.id.grade3);
        grade4=(CardView)findViewById(R.id.grade4);

        grade5=(CardView)findViewById(R.id.grade5);
        grade6=(CardView)findViewById(R.id.grade6);
        grade7=(CardView)findViewById(R.id.grade7);
        grade8=(CardView)findViewById(R.id.grade8);
        grade9=(CardView)findViewById(R.id.grade9);
        grade10=(CardView)findViewById(R.id.grade10);
        grade11=(CardView)findViewById(R.id.grade11);
        grade12=(CardView)findViewById(R.id.grade12);

//        newlessonplan.setOnClickListener(new OnClickListener(){
//
//            @Override
//            public void onClick(View p1) {
//                new Admob(new onDismiss() {
//                    @Override
//                    public void onDismiss() {


//                        Intent i=new Intent(LessonActivity.this, LessonPlanActivity.class);
//                        startActivity(i);
//                    }
////                }).ShowInterstitial(LessonActivity.this, true);
////            }
//        });

//        newlessonplan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (audienceNetwork.isLoaded()){
//                    audienceNetwork.show();
//                }else {
//                    Toast.makeText(LessonActivity.this, "Ad is'nt ready yet", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        newlessonplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audienceNetwork.isLoaded()){
                    audienceNetwork.show(new AudienceNetwork.Dismissed() {
                        @Override
                        public void onclick() {
                            startActivity(new Intent(LessonActivity.this, LessonPlanActivity.class));
                        }
                    });
                }else {
                    Toast.makeText(LessonActivity.this, "Ad is'nt ready yet", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LessonActivity.this, LessonPlanActivity.class));
                }
            }
        });



        tguide.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                //Intent mm=	new Intent(Intent.ACTION_VIEW, Uri.parse("http://128.199.240.200/index.html"));
                //startActivity(mm);
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this,TGuideActivity.class);
                startActivity(i);
                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });

        knowledge.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, KnowledgeActivity.class);
                startActivity(i);
                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });

        talent.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {

                Intent i=new Intent(LessonActivity.this, TalentActivity.class);
                startActivity(i);
            }
        }).ShowInterstitial(LessonActivity.this, true);
    }
});
        story.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        Intent i=new Intent(LessonActivity.this, StoryActivity.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });
        mmessay.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        Intent i=new Intent(LessonActivity.this, MmEssayActivity.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });
        engessay.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        Intent i=new Intent(LessonActivity.this, EngEssayActivity.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });


        kg.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {

                Intent i=new Intent(LessonActivity.this, KgActivity.class);
                startActivity(i);

                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });


        grade1.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, MainActivityNew.class);
                startActivity(i);
            }
        }).ShowInterstitial(LessonActivity.this, true);
    }
});

        grade2.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, Grade2Activity.class);
                startActivity(i);
                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });


        grade3.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, Grade3Activity.class);
                startActivity(i);
            }
        }).ShowInterstitial(LessonActivity.this, true);
    }
});



        grade4.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, Grade4Activity.class);
                startActivity(i);
                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });


        grade5.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, Grade5Activity.class);
                startActivity(i);
            }
        }).ShowInterstitial(LessonActivity.this, true);
    }
});

        grade6.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, Grade6Activity.class);
                startActivity(i);
                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });


        grade7.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, Grade7Activity.class);
                startActivity(i);
            }
        }).ShowInterstitial(LessonActivity.this, true);
    }
});



        grade8.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, Grade8Activity.class);
                startActivity(i);

                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });


        grade9.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, Grade9Activity.class);
                startActivity(i);
            }
        }).ShowInterstitial(LessonActivity.this, true);
    }
});
        grade10.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, Grade10Activity.class);
                startActivity(i);
                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });


        grade11.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                Intent i=new Intent(LessonActivity.this, Grade11Activity.class);
                startActivity(i);
            }
        }).ShowInterstitial(LessonActivity.this, true);
    }
});


        grade12.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1) {

                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {

                Intent i=new Intent(LessonActivity.this, Grade12Activity.class);
                startActivity(i);

                    }
                }).ShowInterstitial(LessonActivity.this, true);
            }
        });}

    /*public void displayInterstitial() {
     // If Ads are loaded, show Interstitial else show nothing.
     if (interstitial.isLoaded()) {
     interstitial.show();
     }

     }*/

    private void loadInterstitialAd() {
        interstitialAd = new com.facebook.ads.InterstitialAd(this, "1072549720831975_1325325095554435"); // Replace with your real ID
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {
                                Log.d(TAG, "Interstitial ad displayed.");
                            }

                            @Override
                            public void onInterstitialDismissed(Ad ad) {
                                Log.d(TAG, "Interstitial ad dismissed.");
                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {
                                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {
                                Log.d(TAG, "Interstitial ad loaded.");
                                interstitialAd.show(); // Show ad when loaded
                            }

                            @Override
                            public void onAdClicked(Ad ad) {
                                Log.d(TAG, "Interstitial ad clicked.");
                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {
                                Log.d(TAG, "Interstitial ad impression logged.");
                            }
                        })
                        .build());
    }


    private void showContact(){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View parent_view = LayoutInflater.from(this).inflate(R.layout.two_button_dia, null);
        dialog.setView(parent_view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
        TextView tv_title = (TextView) dialog.findViewById(R.id.title);
        TextView tv_message = (TextView) dialog.findViewById(R.id.message);
        final Button call = (Button) dialog.findViewById(R.id.bt2);
        final Button cancel= (Button) dialog.findViewById(R.id.bt1);
        iv.setImageResource(R.drawable.phone_icon);
        tv_title.setText("App Creater");
        tv_message.setText(getString(R.string.contact_content));
        cancel.setText("No");
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {
                dialog.dismiss();
            }});
        call.setText("No Called");
        call.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {

                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+"000000"));
                startActivity(call);
                dialog.dismiss();
            }});

    }
    public void showAbout(){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View parent_view = LayoutInflater.from(this).inflate(R.layout.two_button_dia, null);
        dialog.setView(parent_view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
        TextView tv_title = (TextView) dialog.findViewById(R.id.title);
        TextView tv_message = (TextView) dialog.findViewById(R.id.message);
        final Button call = (Button) dialog.findViewById(R.id.bt2);
        final Button cancel= (Button) dialog.findViewById(R.id.bt1);
        iv.setImageResource(R.drawable.about_icon);
        tv_title.setText("About App  ​");
        tv_message.setText(getString(R.string.about_content));
        cancel.setText("Yes");
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {

                dialog.dismiss();
            }});
        call.setText("App Creater");
        call.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {
                showContact();
                dialog.dismiss();
            }});

    }

}

