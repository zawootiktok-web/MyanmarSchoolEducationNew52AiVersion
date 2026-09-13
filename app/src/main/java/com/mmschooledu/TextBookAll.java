package com.mmschooledu;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.material.snackbar.Snackbar;
import com.startapp.sdk.adsbase.StartAppAd;

//import com.google.android.gms.ads.*;
//import android.R;
public class TextBookAll extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener{
    String url;
    String link=url;
    AdRequest adRequest;
    private InterstitialAd interstitialAd;
    private SwipeRefreshLayout swipeRefreshLayout;
    private WebView mWebView;
    private CoordinatorLayout coordinatorLayout;
    @SuppressLint("SetJavaScriptEnabled")



    private BlogspotAPIManagerStartAds blogspotAPIManagerStardads;

    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            boolean show = intent.getBooleanExtra("show", true);
            if (show) {
                // Display the message to the user
                Toast.makeText(TextBookAll.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textbookall);



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
                Toast.makeText(TextBookAll.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
            }
        });


        //link=getIntent().getStringExtra("link");
        Bundle extras = getIntent().getExtras();
        String requestedUrl = extras != null ? extras.getString("link", "") : "";
        url = normalizeTextBookUrl(requestedUrl);
        if (url.isEmpty()) {
            Toast.makeText(this,
                    getString(R.string.textbooks_source_unavailable),
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!isNetworkAvailable()){
            Intent openRetry = new Intent(this.getApplicationContext(),Internetcheck.class);
            startActivity(openRetry);
            finish();

        }




        //adView = (AdView)
        //findViewById(R.id.ad_view);



        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(TextBookAll.this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.container);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                swipeRefreshLayout.setRefreshing(true);
            }

            public void onPageFinished(WebView view, String url) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        //  webSettings.setAppCacheEnabled(true);
        mWebView.setWebViewClient(new Callback());
        loadWebsite();
    }
    private String normalizeTextBookUrl(String requestedUrl) {
        if (requestedUrl == null || requestedUrl.trim().isEmpty()) {
            return "";
        }
        String normalized = requestedUrl.trim();
        if (normalized.startsWith("http://") || normalized.startsWith("http://128.199.240.200")) {
            return "";
        }
        return normalized.startsWith("https://") ? normalized : "";
    }

    private void loadWebsite() {
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            //mWebView.loadUrl("https://gulangguling.com/category/technology-development-design/");
            // The legacy HTTP IP is intentionally never loaded under strict HTTPS policy.
            //link=getIntent().getStringExtra("link");
            mWebView.loadUrl(url);

        } else {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Please check your internet connection.", Snackbar.LENGTH_LONG);
            snackbar.show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public class Callback extends WebViewClient {
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getApplicationContext(), "Failed loading app!", Toast.LENGTH_SHORT).show();
        }
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith(".pdf")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }else if (url.contains("mailto:")) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;

            }else if (url.contains("drive.google.com") || url.startsWith("https://drive.google.com")) {
                    openInExternalBrowser(url);
                    return true;


                // Handle MediaFire links
        }else if(url.contains("mediafire.com") || url.startsWith("https://www.mediafire.com")) {
                    openInExternalBrowser(url);
                    return true;

            }else if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(intent);
                return true;
            }else if (url != null && url.startsWith("https://")) {
                view.loadUrl(url);
                return true;
            } else {
                Toast.makeText(TextBookAll.this,
                        "ဒီ link ကို လုံခြုံစွာ ဖွင့်၍မရပါ။",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        private void openInExternalBrowser(String url) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
               // Toast.makeText(this, "Opening in external browser", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
               // Toast.makeText(this, "No application can handle this request", Toast.LENGTH_SHORT).show();
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            swipeRefreshLayout.setRefreshing(true);
        }


        public void onPageFinished(WebView view, String url) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        mWebView.reload();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}




