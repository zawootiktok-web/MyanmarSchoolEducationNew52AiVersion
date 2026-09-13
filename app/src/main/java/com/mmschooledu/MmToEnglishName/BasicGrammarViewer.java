package com.mmschooledu.MmToEnglishName;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mmschooledu.R;


public class BasicGrammarViewer extends Activity {
	/*@Override
	 protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.webview_layout);
	 WebView wv = findViewById(R.id.wv); */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);


//adView = (AdView)
//			findViewById(R.id.ad_view);
//		AdRequest adRequest = new AdRequest.Builder().build();
//		adView.loadAd(adRequest);
//
//        // Prepare the Interstitial Ad
//        interstitial = new InterstitialAd(BasicGrammerViewer.this);
//        // Insert the Ad Unit ID
//        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));
//
//        interstitial.loadAd(adRequest);
//        // Prepare an Interstitial Ad Listener
//        interstitial.setAdListener(new AdListener() {
//				public void onAdLoaded() {
//					// Call displayInterstitial() function
//					displayInterstitial();
//				}
//
//
//			});

        WebView wv = findViewById(R.id.wv);
        //getActionBar().hide();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient() {
        });

        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setDatabaseEnabled(true);
        //wv.getSettings().setAppCacheMaxSize(1024*1024*8);
        //wv.getSettings().setAppCachePath(getCacheDir().getAbsolutePath());
        wv.getSettings().setAllowFileAccess(true);
        //wv.getSettings().setAppCacheEnabled(true);
        wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);


        wv.setWebViewClient(new myWebClient());
        wv.getSettings().setJavaScriptEnabled(true);
        int id = getIntent().getIntExtra("key", 0);
        if(id==0){ wv.loadUrl("file:///android_asset/personname.html");
//        int pos = getIntent().getIntExtra("key", 0);
//        if (pos == 0) {
//            wv.loadUrl("file:///android_asset/personname.htm");
//        } else if (pos == 1) {
//            wv.loadUrl("file:///android_asset/tense/Pronouns.htm");
//        } else if (pos == 2) {
//            wv.loadUrl("file:///android_asset/tense/types of adjectives.htm");
//        } else if (pos == 3) {
//            wv.loadUrl("file:///android_asset/tense/verb_definition.htm");
//        } else if (pos == 4) {
//            wv.loadUrl("file:///android_asset/tense/Adverb.htm");
//        } else if (pos == 5) {
//            wv.loadUrl("file:///android_asset/tense/Preposition.htm");
//        } else if (pos == 6) {
//            wv.loadUrl("file:///android_asset/tense/conjunction.htm");
//        } else if (pos == 7) {
//            wv.loadUrl("file:///android_asset/tense/interjection.htm");
        }
    }

//	public void displayInterstitial() {
//		// If Ads are loaded, show Interstitial else show nothing.
//		if (interstitial.isLoaded()) {
//			interstitial.show();
//		}}
//
//
//

	/*

	 int id = getIntent().getIntExtra("userClicked",0);
	 //int id = getIntent().getExtras().get("userClicked",0);
	 if(id==0){ wv.loadUrl("file:///android_asset/inter/level1/1-nice-to-meet-you.html");
	 } else if(id==2){ wv.loadUrl("file:///android_asset/inter/level1/5-are-you-from-seoul.html");
	 } else if(id==3){ wv.loadUrl("file:///android_asset/inter/level1/5-are-you-from-seoul.html");


	 } else if(id==26){ wv.loadUrl("file:///android_asset/inter/level2/1-please-call-me-beth.html");

	 }}} */

    public class myWebClient extends WebViewClient {
        @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url); }
    }}