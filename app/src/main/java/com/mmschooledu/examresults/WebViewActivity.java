package com.mmschooledu.examresults;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.mmschooledu.R;


public class WebViewActivity extends AppCompatActivity {

    private WebView wv_pdf_view;
    LinearProgressIndicator pi_loading;
    String year;
    String stringExtra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);

        prepareView();
        actionData();
        actionView();
        actionClick();

    }

    void prepareView() {
        pi_loading = findViewById(R.id.pi_loading);
        wv_pdf_view = findViewById(R.id.wv_pdf_view);
    }

    void actionData() {
        year = getIntent().getStringExtra("year");
        stringExtra = getIntent().getStringExtra("url");
    }

    void actionView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(("Exam Result-" + year));
            // actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        if (isOnline()) {
            pi_loading.setVisibility(View.VISIBLE);
            wv_pdf_view.setWebViewClient(new MyBrowser());
            initWebView();
            loadWebPage(stringExtra);
        } else {
            pi_loading.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Error : " + getString(R.string.internet_message), Toast.LENGTH_SHORT).show();
        }
    }

    void actionClick() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed(); // or handle the back/home navigation as per your requirements
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private void initWebView() {
        android.webkit.CookieManager.getInstance().setAcceptCookie(true);
        WebSettings webSettings = wv_pdf_view.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + "checktool";
        webSettings.setDatabasePath(cacheDirPath);

        webSettings.setDefaultTextEncodingName("utf-8");
        String userAgentString = webSettings.getUserAgentString();
        webSettings.setUserAgentString(userAgentString);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
        wv_pdf_view.getSettings().setDomStorageEnabled(true);
        wv_pdf_view.getSettings().setDatabaseEnabled(true);

        wv_pdf_view.getSettings().setAllowFileAccess(true);

        wv_pdf_view.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        registerForContextMenu(wv_pdf_view);


    }


    void loadWebPage(String link) {
        pi_loading.setVisibility(View.VISIBLE);
        if (isOnline()) {
            wv_pdf_view.loadUrl(link);
        } else {
        }


    }

    private class MyBrowser extends WebViewClient {

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            pi_loading.setVisibility(View.GONE);

            super.onReceivedHttpError(view, request, errorResponse);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            pi_loading.setVisibility(View.VISIBLE);

            // TODO: Implement this method
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            pi_loading.setVisibility(View.GONE);

            // TODO: Implement this method
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            pi_loading.setVisibility(View.GONE);

            Toast.makeText(getApplicationContext(), "Error : " + description, Toast.LENGTH_SHORT).show();

            // TODO: Implement this method
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}

