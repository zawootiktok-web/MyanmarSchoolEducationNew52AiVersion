package com.mmschooledu;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bdtopcoder.quickadmob.Admob;
import com.bdtopcoder.quickadmob.onDismiss;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds; // Corrected Import
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.startapp.sdk.adsbase.StartAppAd;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class BookDetailActivity extends AppCompatActivity {
    // Ads Variables
    private InterstitialAd interstitialAd; // Facebook
    // private AudienceNetwork audienceNetwork; // Removed custom class reference if not available, assuming standard FB logic below

    private Button button;
    private TextView tv, tv2, tvProgressPercent;
    private ImageView iv;
    private ProgressBar progressBar;
    private LinearLayout layProgress;

    // Data Variables
    private String bname, type, link, link2, thumbnail, wname, fileId;

    private BlogspotAPIManagerStartAdsNew blogspotAPIManagerStardadsnew;

    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            boolean show = intent.getBooleanExtra("show", true);
            if (show) {
                Toast.makeText(BookDetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdetail_layout);

        // Initialize Ads (Existing Logic)
        // Note: Assuming AudienceNetworkAds.initialize is done elsewhere or here
        // AudienceNetworkAds.initialize(this);
        loadInterstitialAd(); // Load FB Ad

        blogspotAPIManagerStardadsnew = new BlogspotAPIManagerStartAdsNew(this);

        // Register Broadcast
        IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(messageStatusReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }

        // Ads Status Check
        blogspotAPIManagerStardadsnew.checkAdsStatus(new BlogspotAPIManagerStartAdsNew.OnAdsStatusListener() {
            @Override
            public void onAdsStatus(boolean isEnabled) {
                if (isEnabled) {
                    showAds();
                }
            }
            private void showAds() {
                StartAppAd.showAd(getBaseContext());
                if(interstitialAd != null && interstitialAd.isAdLoaded()) interstitialAd.show();
            }
        });

        Admob.loadInterstitialAds(BookDetailActivity.this);

        // UI Initialization
        button = findViewById(R.id.videolayoutButton1);
        tv2 = findViewById(R.id.tvViewerTitle);
        tv = findViewById(R.id.tvDetail);
        iv = findViewById(R.id.ivDetail);

        // Progress Bar Components
        progressBar = findViewById(R.id.progressBar);
        tvProgressPercent = findViewById(R.id.tvProgressPercent);
        layProgress = findViewById(R.id.layProgress);

        // Receive Data
        Intent intent = getIntent();
        bname = intent.getStringExtra("bname");
        type = intent.getStringExtra("type");
        link = intent.getStringExtra("link");
        link2 = intent.getStringExtra("link2"); // Download Link
        thumbnail = intent.getStringExtra("thumbnail");
        wname = intent.getStringExtra("wname");

        // Extra Data (if needed from Telegram Flow)
        fileId = intent.getStringExtra("FILE_ID");
        if(bname == null && intent.hasExtra("TITLE")) bname = intent.getStringExtra("TITLE");

        setTitle(bname);
        Glide.with(this).load(thumbnail).into(iv);
        tv.setText(bname + "\n\n" + (wname != null ? wname : ""));
        tv2.setText(bname);

        checkFileAndSetButton();
    }

    public void _Back_Click(View v){
        finish();
    }

    public void readClick(View v) {
        new Admob(new onDismiss() {
            @Override
            public void onDismiss() {
                Intent intent = new Intent(BookDetailActivity.this, BookViewerActivity.class);
                intent.putExtra("bname", bname);
                intent.putExtra("type", type);
                intent.putExtra("link", link);
                startActivity(intent);
            }
        }).ShowInterstitial(BookDetailActivity.this, true);
    }

    private void loadInterstitialAd() {
        interstitialAd = new InterstitialAd(this, "1072549720831975_1325325095554435");
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {}
                            @Override
                            public void onInterstitialDismissed(Ad ad) {}
                            @Override
                            public void onError(Ad ad, AdError adError) {}
                            @Override
                            public void onAdLoaded(Ad ad) {}
                            @Override
                            public void onAdClicked(Ad ad) {}
                            @Override
                            public void onLoggingImpression(Ad ad) {}
                        })
                        .build());
    }

    private void checkFileAndSetButton() {
        if(bname == null) return;
        String fileName = bname + ".pdf";
        String folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + File.separator + "MyanmarSchoolEducationBooks" + File.separator;
        File file = new File(folderPath, fileName);

        if (file.exists()) {
            button.setText("Open");
            button.setOnClickListener(v -> openPdf(file));
        } else {
            button.setText("Download");
            button.setOnClickListener(this::downloadClick);
        }
    }

    // *** New Download Logic with Progress Bar ***
    public void downloadClick(View v) {
        String url = link2;
        if (url == null || url.isEmpty()) {
            Toast.makeText(this, "Download link not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert Google Drive Link if needed
        if (url.contains("://drive.google.com")) {
            url = getGoogleDriveDownloadLinkFromUrl(url);
        }

        // Start Async Task
        new DownloadFileTask().execute(url);
    }

    // Background Task for Downloading
    private class DownloadFileTask extends AsyncTask<String, Integer, String> {
        File file;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            layProgress.setVisibility(View.VISIBLE);
            button.setEnabled(false);
            button.setText("Downloading...");
        }

        @Override
        protected String doInBackground(String... urls) {
            int count;
            try {
                URL url = new URL(urls[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                int lengthOfFile = connection.getContentLength();

                // Setup Folder
                String fileName = bname + ".pdf";
                String folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + File.separator + "MyanmarSchoolEducationBooks" + File.separator;
                File folder = new File(folderPath);
                if (!folder.exists()) folder.mkdirs();

                file = new File(folder, fileName);

                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish Progress
                    if (lengthOfFile > 0) {
                        publishProgress((int) ((total * 100) / lengthOfFile));
                    }
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
                return "Success";

            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            progressBar.setProgress(progress[0]);
            tvProgressPercent.setText(progress[0] + "%");
        }

        @Override
        protected void onPostExecute(String result) {
            layProgress.setVisibility(View.GONE);
            button.setEnabled(true);

            if (result.equals("Success")) {
                button.setText("Open");
                button.setOnClickListener(v -> openPdf(file));
                showDownloadCompleteDialog(file);
            } else {
                button.setText("Retry Download");
                Toast.makeText(BookDetailActivity.this, "Error: " + result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDownloadCompleteDialog(File file) {
        new AlertDialog.Builder(this)
                .setTitle("Download Complete")
                .setMessage("စာအုပ်ဒေါင်းလုဒ်ဆွဲပြီးပါပြီ။ ဖတ်ရှုလိုပါသလား?")
                .setPositiveButton("ဖတ်မည်", (dialog, which) -> openPdf(file))
                .setNegativeButton("မဖတ်ပါ", null)
                .show();
    }

    private void openPdf(File file) {
        try {
            Uri fileUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
            } else {
                fileUri = Uri.fromFile(file);
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No PDF Viewer found.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static String getGoogleDriveDownloadLinkFromUrl(String url) {
        // Original logic kept
        int index = url.indexOf("id=");
        int closingIndex = 0;
        if (index >= 0) {
            index += 3;
            closingIndex = url.indexOf("&", index);
            if (closingIndex < 0) closingIndex = url.length();
        } else {
            index = url.indexOf("file/d/");
            if (index < 0) return url;
            index += 7;
            closingIndex = url.indexOf("/", index);
            if (closingIndex < 0) {
                closingIndex = url.indexOf("?", index);
                if (closingIndex < 0) closingIndex = url.length();
            }
        }
        String id = url.substring(index, closingIndex);
        return "https://drive.google.com/uc?id=" + id + "&export=download";
    }
}