package com.mmschooledu;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

public class AboutUsActivity extends AppCompatActivity {
  //  private AdView adView;
  private AudienceNetwork audienceNetwork;

    final int storageRequestCode=123;
    private static final int resultCode = 0;
    CardView kg,kg2,kg3,kg4,kg5,kg6,kg7,kg8;


    // private static final int REQUEST_READ_MEDIA_AUDIO = 1;


    //   Button showInterstitial, showReword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        audienceNetwork = new AudienceNetwork(this)
                .setFullScreenStatus(true)
                // .setBannerStatus(true)
                .init()
                // .setInterstitialId("VID_HD_16_9_15S_APP_INSTALL#YOUR_PLACEMENT_ID")
                .setInterstitialId("1072549720831975_1325325095554435")
                //.setBannerId("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
                .setBannerId("1072549720831975_1072552904164990")
                .setBannerStatus(true)
                .setBannerLayoutId(R.id.ad_container) // LinearLayout ID for the banner
                .build();

        // Load banner ad into the specified container
        LinearLayout bannerContainer = findViewById(R.id.ad_container);

//        Admob.loadInterstitialAds(AboutUsActivity.this);
//        Admob.loadRewordedAds(AboutUsActivity.this);

        //        ActivityCompat.requestPermissions(this,
//                new String[]{READ_MEDIA_IMAGES, WRITE_EXTERNAL_STORAGE},
//                PackageManager.PERMISSION_GRANTED);


        kg = (CardView) findViewById(R.id.kg);
        kg2 = (CardView) findViewById(R.id.kg2);
        kg3 = (CardView) findViewById(R.id.kg3);
        kg4 = (CardView) findViewById(R.id.kg4);
        kg5 = (CardView) findViewById(R.id.kg5);
        kg6 = (CardView) findViewById(R.id.kg6);
        kg7 = (CardView) findViewById(R.id.kg7);
        kg8 = (CardView) findViewById(R.id.kg8);


//        adView = (AdView)
//                findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

//requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);


        kg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {


//                new Admob(new onDismiss() {
//                    @Override
//                    public void onDismiss() {
//                        Intent i = new Intent(AboutUsActivity.this, PrivacyPolicy.class);
//                        startActivity(i);
//
//                    }



                try {
                    // Check if the interstitial ad is loaded
                    if (audienceNetwork.isLoaded()) {
                        // Show the interstitial ad
                        audienceNetwork.show(new AudienceNetwork.Dismissed() {
                            @Override
                            public void onclick() {
                                // Action to perform after ad is dismissed
                                launchAboutUsActivity();
                            }
                        });
                    } else {
                        // If the ad is not loaded, directly proceed to the next action
                        launchAboutUsActivity();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void launchAboutUsActivity() {

                        MsgBox("About",
                                "Apk Name- Myanmar School Education  Apk\n\n"+
                                        " Version- 1.1.51\n\n"+"မူရင်းတင်​ပေး​သောဆရာများကိုလည်း အထူး​ကျေးဇူးတင်ပါတယ်။မကြိုက်တာများရှိပါက mail က​​နေပြန်ဖျက်ခိုင်းနိုင်ပါတယ်။ ပညာတတ်များလည်း များစွာ ပေါ်ထွန်းလာကြပါ​စေခင်ဗျာ😀\n\n"+
                                        "Developed by Myanmar Developer Group");
                    }

//                    private void MsgBox(String about, String s) {
//                    }


                    public void MsgBox(String title, String msg){

                        AlertDialog alertDialog = new AlertDialog.Builder(AboutUsActivity.this).create();

                        alertDialog.setTitle(title);

                        alertDialog.setMessage(msg);

                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();

//                    }


//                }).ShowInterstitial(AboutUsActivity.this, true);
            };});

        kg2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {


                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
//                        Intent i = new Intent(AboutUsActivity.this, PrivacyPolicy.class);
//                        startActivity(i);
//
//                    }
                        MsgBox("About",
                                "Apk Name- Myanmar School Education Apk\n\n"+
                                        " Version- 1.1.51\n\n"+"မူရင်းတင်​ပေး​သောဆရာများကိုလည်း အထူး​ကျေးဇူးတင်ပါတယ်။မကြိုက်တာများရှိပါက mail က​​နေပြန်ဖျက်ခိုင်းနိုင်ပါတယ်။ ပညာတတ်များလည်း များစွာ ပေါ်ထွန်းလာကြပါ​စေခင်ဗျာ😀\n\n"+
                                        "Developed by Myanmar Developer Group");
                    }

//                    private void MsgBox(String about, String s) {
//                    }


                    public void MsgBox(String title, String msg){

                        AlertDialog alertDialog = new AlertDialog.Builder(AboutUsActivity.this).create();

                        alertDialog.setTitle(title);

                        alertDialog.setMessage(msg);

                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();

                    }



                }).ShowInterstitial(AboutUsActivity.this, true);
            };});
        kg3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {


                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        openPlayStore();

                    }

                    private void openPlayStore() {


                            // Replace "com.your.package.name" with your app's package name
                            String appPackageName = "com.mmschooledu";

                            try {
                                // Open the app's page on the Play Store
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException e) {
                                // If the Play Store app is not installed, open the Play Store website
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                    }
                }).ShowInterstitial(AboutUsActivity.this, true);
            };});
        kg4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {


                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        Intent i=	new Intent(Intent.ACTION_SEND);
                        i.setData(Uri.parse("email"));
                        String[] s={"myanmarschooleducation2022@gmail.com"};
                        i.putExtra(Intent.EXTRA_EMAIL,s);
                        i.putExtra(Intent.EXTRA_SUBJECT,"This is Title");
                        i.putExtra(Intent.EXTRA_TEXT,"This is a Email Body");
                        i.setType("message/rfc822");
                        Intent chooser=Intent.createChooser(i,"Launch Email");
                        startActivity(chooser);

                    }
                }).ShowInterstitial(AboutUsActivity.this, true);
            };});


        kg5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {
                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        Intent i = new Intent(AboutUsActivity.this, ChatActivity.class);
                        startActivity(i);
                    }
                }).ShowInterstitial(AboutUsActivity.this, true);
            }

            ;
        });

        kg6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {


                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        openPlayStoreListing(AboutUsActivity.this);
                    }

                    private void openPlayStoreListing(Context context) {
                        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

                        // To ensure it opens in the Play Store app (if available), otherwise open in browser
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            // If Play Store app is not available, open the Play Store website
                            Uri websiteUri = Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName());
                            Intent openBrowser = new Intent(Intent.ACTION_VIEW, websiteUri);
                            startActivity(openBrowser);
                        }}
                }).ShowInterstitial(AboutUsActivity.this, true);
            };});
        kg7.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {


                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        //if(item.getTitle().equals("Share")){
                        Intent i=new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_TEXT,"This is a good apk.Download hear... https://play.google.com/store/apps/details?id=com.mmschooledu");
                        startActivity(i);
                    }
                }).ShowInterstitial(AboutUsActivity.this, true);
            };});
        kg8.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View p1) {


                new Admob(new onDismiss() {
                    @Override
                    public void onDismiss() {
                        Intent i = new Intent(AboutUsActivity.this, PrivacyPolicy.class);
                        startActivity(i);


                    }
                }).ShowInterstitial(AboutUsActivity.this, true);
            };});

    }



    private void requestAppPermissions() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(AboutUsActivity.this,new String[]{
                    android.Manifest.permission.READ_MEDIA_AUDIO,
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_VIDEO
            },resultCode);
            //  request13Permission();

            // Toast.makeText(this, "You Need To Accept Storage Permission.", Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "Storage Permission ​ပေးပြီး​သော​ကြောင့် ကြိုက်ရာကို Download ရယူနိုင်ပါပြီ", Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(AboutUsActivity.this,new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            },resultCode);
            //requestPermission();

            // Toast.makeText(this, "You Need To Accept Storage Permission.", Toast.LENGTH_SHORT).show();
            //  Toast.makeText(this, "Storage Permission ​ပေးပြီး​သော​ကြောင့် ကြိုက်ရာကို Download ရယူနိုင်ပါပြီ", Toast.LENGTH_SHORT).show();
        }


    }




    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void request13Permission(){

        if( ContextCompat.checkSelfPermission(AboutUsActivity.this, android.Manifest.permission.READ_MEDIA_AUDIO)== PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(AboutUsActivity.this, android.Manifest.permission.READ_MEDIA_IMAGES)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(AboutUsActivity.this, android.Manifest.permission.READ_MEDIA_VIDEO)== PackageManager.PERMISSION_GRANTED
        ){
            showAlert("Great!!","You did made permission request.");
        }else{
            ActivityCompat.requestPermissions(AboutUsActivity.this,new String[]{android.Manifest.permission.READ_MEDIA_AUDIO, android.Manifest.permission.READ_MEDIA_IMAGES, android.Manifest.permission.READ_MEDIA_VIDEO},storageRequestCode);
        }

    }
    public void requestPermission(){
        if(ContextCompat.checkSelfPermission(AboutUsActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(AboutUsActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
        ){
            showAlert("Great!!","You did made permission request.");
        }else{
            ActivityCompat.requestPermissions(AboutUsActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},storageRequestCode);
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
        new android.app.AlertDialog.Builder(AboutUsActivity.this)
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


}

//
//import android.content.Intent;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.mmschooledu.util.FeedbackUtils;
//
//import butterknife.ButterKnife;
//import butterknife.OnClick;
////import swati4star.createpdf.R;
////import swati4star.createpdf.util.FeedbackUtils;
//
//public class AboutUsActivity extends AppCompatActivity {
//
//    private FeedbackUtils mFeedbackUtils;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_about_us);
//
//        ButterKnife.bind(this);
//
//        try {
//            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//            TextView versionText = findViewById(R.id.version_value);
//            String version = versionText.getText().toString() + " " + packageInfo.versionName;
//            versionText.setText(version);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        mFeedbackUtils = new FeedbackUtils(this);
//    }
//
//    @OnClick(R.id.layout_email)
//    public void sendMail() {
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"swati4star@gmail.com"});
////        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback_subject));
////        intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.feedback_text));
//        mFeedbackUtils.openMailIntent(intent);
//    }
//
//    @OnClick(R.id.layout_website)
//    void openWeb() {
//        mFeedbackUtils.openWebPage("http://swati4star.github.io/Images-to-PDF/");
//    }
//
//    @OnClick(R.id.layout_slack)
//    void joinSlack() {
//        mFeedbackUtils.openWebPage("https://join.slack.com/t/imagestopdf/shared_invite/" +
//                "enQtNDA2ODk1NDE3Mzk3LTUwNjllYzY5YWZkZDliY2FmNDhkNmM1NjIwZTc1Y" +
//                "jU4NTgxNWI0ZDczMWQxMTEyZjA0M2Y5N2RlN2NiMWRjZGI");
//    }
//
//    @OnClick(R.id.layout_github)
//    void githubRepo() {
//        mFeedbackUtils.openWebPage("https://github.com/Swati4star/Images-to-PDF");
//    }
//
//    @OnClick(R.id.layout_contri)
//    void contributorsList() {
//        mFeedbackUtils.openWebPage("https://github.com/Swati4star/Images-to-PDF/graphs/contributors");
//    }
//
//    @OnClick(R.id.layout_playstore)
//    void openPlaystore() {
//        mFeedbackUtils.openWebPage("https://play.google.com/store/apps/details?id=swati4star.createpdf");
//    }
//
//    @OnClick(R.id.layout_privacy)
//    void privacyPolicy() {
//        mFeedbackUtils.openWebPage("https://sites.google.com/view/privacy-policy-image-to-pdf/home");
//    }
//
//    @OnClick(R.id.layout_license)
//    void license() {
//        mFeedbackUtils.openWebPage("https://github.com/Swati4star/Images-to-PDF/blob/master/LICENSE.md");
//    }
//}
