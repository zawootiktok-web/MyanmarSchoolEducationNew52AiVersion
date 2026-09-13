package com.mmschooledu.examresults;

import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mmschooledu.R;

import java.io.File;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.interstitial.InterstitialAd;

public class BookViewerActivity extends AppCompatActivity
{

//    private InterstitialAd mInterstitialAd;
//    private AdView adView;

    WebView wv;
    String pdfurl="",title,year;

    String tempUrl;

    TextView tv,tv2;


    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookviewer_layout);
        pdfurl = getIntent().getStringExtra("url");
        year = getIntent().getStringExtra("year");
        title = getIntent().getStringExtra("title");

        progressbar = (ProgressBar) findViewById(R.id.progressbar);


        // tv2 = (TextView)findViewById(R.id.tvViewerTitle);

        tv = (TextView) findViewById(R.id.tvViewerTitle);
        // iv = (ImageView)findViewById(R.id.ivDetail);
        // Intent intent=getIntent();
        // bname = intent.getStringExtra("bname");
        tv.setText(title);
        // setTitle(bname);

//
//        adView = (AdView)
//                findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//        InterstitialAd.load(this, getResources().getString(R.string.admob_interstitial_id), adRequest,
//                new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        // The mInterstitialAd reference will be null until
//                        // an ad is loaded.
//                        mInterstitialAd = interstitialAd;
//                        Log.i(TAG, "onAdLoaded");
//                        displayInterstitial();
//                    }
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error
//                        Log.d(TAG, loadAdError.toString());
//                        mInterstitialAd = null;
//                    }
//                });


        wv = (WebView)findViewById(R.id.wv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(false);
        wv.getSettings().setAllowContentAccess(false);
        wv.getSettings().setAllowFileAccessFromFileURLs(false);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wv.getSettings().setSafeBrowsingEnabled(true);
        }
        wv.setWebViewClient(new WebViewClient(){});

        wv.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength)
            {
//                int permissionCheck1 = ContextCompat.checkSelfPermission(BookViewerActivity.this, Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION);
//                int permissionCheck2 = ContextCompat.checkSelfPermission(BookViewerActivity.this, Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION);
                int permissionCheck1 = 0;
                byte permissionCheck2;
                downloadFromUrl(url);
            }
        });

        if (pdfurl.contains("://drive.google.com"))
            wv.loadUrl(pdfurl);
        else
            wv.loadUrl("https://docs.google.com/viewer?url=" + pdfurl);
        progressbar.setVisibility(View.GONE);
    }

    private void downloadFromUrl(String url){
        if (url.contains("://drive.google.com"))
            url = getGoogleDriveDownloadLinkFromUrl(url);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        String mBaseFolderPath = Environment
                .getExternalStorageDirectory()
                + File.separator
                + "MyanmarSchoolEducation" + File.separator;
        if (!new File(mBaseFolderPath).exists())
        {
            new File(mBaseFolderPath).mkdir();
        }
        String mFilePath = "file://" + mBaseFolderPath + "/" + title + "." ;
        request.setDestinationUri(Uri.parse(mFilePath));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(request);
        Toast.makeText(getApplicationContext(), "Download started.", 1).show();
    }

    public void _Back_Click(View v){
        finish();


    }



    public static String getGoogleDriveDownloadLinkFromUrl(String url)
    {
        int index = url.indexOf("id=");
        int closingIndex=0;
        if (index >= 0)
        {
            index += 3;
            closingIndex = url.indexOf("&", index);
            if (closingIndex < 0)
                closingIndex = url.length();
        }
        else
        {
            index = url.indexOf("file/d/");
            if (index < 0) // url is not in any of the supported forms
                return url;

            index += 7;

            closingIndex = url.indexOf("/", index);
            if (closingIndex < 0)
            {
                closingIndex = url.indexOf("?", index);
                if (closingIndex < 0)
                    closingIndex = url.length();
            }
        }
        String id=url.substring(index, closingIndex);
        return ("https://drive.google.com/uc?id=" + id + "&export=download");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == 101)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                downloadFromUrl(tempUrl);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    public void displayInterstitial() {
//        // If Ads are loaded, show Interstitial else show nothing.
//        if (mInterstitialAd!=null) {
//            mInterstitialAd.show(this);
//        }
    }


