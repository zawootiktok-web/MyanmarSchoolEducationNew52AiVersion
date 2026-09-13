package com.mmschooledu;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class WelcomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout linearLayout;
    TextView[] dostTv;
    int[] layouts;
    Button nextButton, skipButton;
    MyAdapter myAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isFirstTimeAPpStart()) {
            setAppStartStatus(false);
            startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));
            finish();
        }

        setContentView(R.layout.activity_welcome);

//        TextView marqueeText = (TextView) findViewById(R.id.marqueeText);
//        marqueeText.setSelected(true);
        viewPager = findViewById(R.id.viewPagerId);
        linearLayout = findViewById(R.id.dotsLayoutId);
        nextButton = findViewById(R.id.btn_next);
        skipButton = findViewById(R.id.btn_skip);
//        adsBt = findViewById(R.id.adsBt);
//        TextView marqueeText = (TextView) findViewById(R.id.marqueeText);
//        marqueeText.setSelected(true);
        //status bar
        statusBarTransparent();

//        adsBt.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View p1) {
//
//
//                new Admob(new onDismiss() {
//                    @Override
//                    public void onDismiss() {
//                        openPlayStore();
//
//                    }
//
//                    private void openPlayStore() {
//
//
//                        // Replace "com.your.package.name" with your app's package name
//                        String appPackageName = "com.thaw.skm";
//
//                        try {
//                            // Open the app's page on the Play Store
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                        } catch (android.content.ActivityNotFoundException e) {
//                            // If the Play Store app is not installed, open the Play Store website
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                        }
//                    }
//                }).ShowInterstitial(WelcomeActivity.this, true);
//            };});


        skipButton.setOnClickListener(v -> {

            setAppStartStatus(false);
            startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));
            finish();

        });

        nextButton.setOnClickListener(v -> {
            int currentPage = viewPager.getCurrentItem()+1;
            if (currentPage < layouts.length) {
                viewPager.setCurrentItem(currentPage);
            }else {

                setAppStartStatus(false);
                startActivity(new Intent(WelcomeActivity.this, SplashActivity.class));
                finish();

            }
        });


        layouts = new int[] {R.layout.slide_1, R.layout.slide_2, R.layout.slide_3, R.layout.slide_4,R.layout.slide_5,R.layout.slide_6};
        myAdapter = new MyAdapter(layouts, getApplicationContext());
        viewPager.setAdapter(myAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == layouts.length - 1) {
                    nextButton.setText("Start");
                    skipButton.setVisibility(View.GONE);
                }else {
                    nextButton.setText("Next");
                    skipButton.setVisibility(View.VISIBLE);
                }

                setDots(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setDots(0);



    }

    private void statusBarTransparent() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
//    }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            getWindow().getInsetsController().hide(WindowInsets.Type.navigationBars());
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }
    }

        private void setDots(int page){
        linearLayout.removeAllViews();
        dostTv = new TextView[layouts.length];

        for (int i = 0; i<dostTv.length; i++){
            dostTv[i] = new TextView(this);
            dostTv[i].setText(Html.fromHtml("&#8226"));
            dostTv[i].setTextSize(30);
            dostTv[i].setTextColor(Color.parseColor("#a9b4bb"));
            linearLayout.addView(dostTv[i]);
        }

        if (dostTv.length > 0){
            dostTv[page].setTextColor(Color.parseColor("#ffffff"));
        }


    }

    private boolean isFirstTimeAPpStart(){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("SLIDER_APP", MODE_PRIVATE);
        return preferences.getBoolean("APP_START", true);
    }

    private void setAppStartStatus(boolean status){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("SLIDER_APP", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("APP_START", status);
        editor.apply();
    }



}