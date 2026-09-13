package com.mmschooledu.Viewer;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;
import com.mmschooledu.BlogspotAPIManagerStartAdsNew;
import com.mmschooledu.FontConverter;
import com.mmschooledu.R;
import com.startapp.sdk.adsbase.StartAppAd;

import java.io.File;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewerActivity extends Activity//AppCompatActivity
{
    private ConsentInformation consentInformation;

    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);
    TextView tvViewerTitle;
        private InterstitialAd mInterstitialAd;
    private AdView adView;
    WebView wv;
    final int FONT_ZAWGYI = 1;
    final int FONT_UNI = 2;
    final int FONT_NONE = 0;
    int currentFont = FONT_NONE;
    String output = "";


    private BlogspotAPIManagerStartAdsNew blogspotAPIManagerStardadsnew;

    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            boolean show = intent.getBooleanExtra("show", true);
            if (show) {
                // Display the message to the user
                Toast.makeText(ViewerActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewer_layout);

        requestConsform();


        adView = (AdView)
                findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);



// Initialize BlogspotAPIManager
        blogspotAPIManagerStardadsnew = new BlogspotAPIManagerStartAdsNew(this);



        // Register the BroadcastReceiver to receive message status updates
        IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(messageStatusReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }


        // Check ads status
        blogspotAPIManagerStardadsnew.checkAdsStatus(new BlogspotAPIManagerStartAdsNew.OnAdsStatusListener() {
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
                Toast.makeText(ViewerActivity.this, " Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
            }
        });


        String title1 = getIntent().getStringExtra("title");

//String published = getIntent().getStringExtra("published");

        requestAppPermissions();
        tvViewerTitle = (TextView) findViewById(R.id.tvViewerTitle);
        tvViewerTitle.setText(title1);


        wv = (WebView) findViewById(R.id.wv);

        //   wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient() {
        });

        wv.getSettings().setDomStorageEnabled(true);
        wv.getSettings().setDatabaseEnabled(true);
        //wv.getSettings().setAppCacheMaxSize(1024*1024*8);
        //wv.getSettings().setAppCachePath(getCacheDir().getAbsolutePath());
        wv.getSettings().setAllowFileAccess(true);
        //wv.getSettings().setAppCacheEnabled(true);
        wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        WebSettings webSettings = wv.getSettings();
        //  webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        //    webSettings.setAppCacheEnabled(true);


        wv.setDownloadListener(new DownloadListener() {


            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimeType);

                String cookies = android.webkit.CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);

                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_SHORT).show();
            }
        });

        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && (url.startsWith("https://drive.") || url.startsWith("https://www.doc")
                        //	|| url.startsWith("https://")
                        //	|| url.startsWith("http://")
                        || url.startsWith("https://openload") || url.startsWith("https://mega")
                        || url.startsWith("https://my") || url.startsWith("https://download")
                        || url.startsWith("http://www.mediafire.com")
                        || url.startsWith("http://j.gs ")
                        //	|| url.startsWith("http:// ")
                        //		|| url.startsWith("https:// ")


                        || url.startsWith(" http://evassmat.com/")
                        || url.startsWith("http://q.gs")
                        || url.startsWith("http://gloyah.net")
                        || url.startsWith("https://link.tl ")
                        || url.startsWith("http://link.tl")
                        || url.startsWith("http://adf.ly  ")
                        || url.startsWith("https://www.mediafire.com")
                        || url.startsWith("http://www.pcloud"))) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }
        });


        registerForContextMenu(wv);


        String title = getIntent().getStringExtra("title");
        String published = getIntent().getStringExtra("published");

        String content = getIntent().getStringExtra("content");

        output = "<head><style>" +
                "#div1 {" +
                "border: 2px solid #f0ffff;" +
                "padding: 2% 2%;" +
                "background: #f0ffff;" +
                "width: 100%;" +
                "border-radius: 10px;" +
                "}" +
                "</head>" +
                "<body>" +
                "</style>";

        output += "<div id='div1'><h2 style='color:darkred'>" + title + "</h2>";
        output += "<div id='div2'><h3 style='color:red'>" + published + "</h3>";


        output += "<p style='color:green'>" + content + "<br></div><br>";

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        currentFont = sharedPreferences.getInt("font_viewer", 0);
        showPost();
    }

    private void requestAppPermissions() {

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 101); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void showPost() {
        String temp = "";
        switch (currentFont) {
            case FONT_ZAWGYI:
                temp = FontConverter.uni2zg(output);
                break;
            case FONT_UNI:
                temp = FontConverter.zg2uni(output);
                break;
            default:
                temp = output;
                break;
        }
        wv.loadDataWithBaseURL("file:///", temp, "text/html", "UTF-8", null);
    }



    private void requestConsform() {
//		ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(this)
////			.addTestDeviceHashedId("36AC515B03D289296D467572BED30BE8")
//				.addTestDeviceHashedId("3AF928E9BC876E08D36CEED8015F8777")
//				.setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
//				.build();


        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                //.setConsentDebugSettings(debugSettings)
                .setTagForUnderAgeOfConsent(false)
                .build();

        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                (ConsentInformation.OnConsentInfoUpdateSuccessListener) () -> {
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                            this,
                            (ConsentForm.OnConsentFormDismissedListener) loadAndShowError -> {
                                if (loadAndShowError != null) {
                                    // Consent gathering failed.
                                    Log.w(TAG, String.format("%s: %s",
                                            loadAndShowError.getErrorCode(),
                                            loadAndShowError.getMessage()));
                                }

                                // Consent has been gathered.
                                if (consentInformation.canRequestAds()) {
//								initializeMobileAdsSdk();
                                    loadbannerads(true);
                                }
                            }
                    );
                },
                (ConsentInformation.OnConsentInfoUpdateFailureListener) requestConsentError -> {
                    // Consent gathering failed.
                    Log.w(TAG, String.format("%s: %s",
                            requestConsentError.getErrorCode(),
                            requestConsentError.getMessage()));
                });

        // Check if you can initialize the Google Mobile Ads SDK in parallel
        // while checking for new consent information. Consent obtained in
        // the previous session can be used to request ads.
        if (consentInformation.canRequestAds()) {
            //initializeMobileAdsSdk();
            loadbannerads(true);
        }
    }
//	private void initializeMobileAdsSdk () {
//
//
//	}


    private void loadbannerads(boolean isPersonlized) {
        AdView adView = findViewById(R.id.ad_view);
        AdRequest adRequest;
        if (isPersonlized) {
            adRequest = new AdRequest.Builder().build();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("npa", "1");
            adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, bundle).build();
        }

        adView.loadAd(adRequest);

    }





    private void changeFont() {
        currentFont++;
        if (currentFont == 3) {
            currentFont = 0;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("font_viewer", currentFont);
        editor.commit();
        showPost();
    }

    @Override
    public void onBackPressed() {
        if (wv.canGoBack()) {
            wv.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void _Back_Click(View v) {
        finish();
    }

    public void _Font_Click(View v) {
        changeFont();
    }


    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (mInterstitialAd!=null) {
            mInterstitialAd.show(this);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        final WebView.HitTestResult result = wv.getHitTestResult();
        MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                new ImageDownloadTask().execute(result.getExtra());
                return true;
            }
        };

        if (result.getType() == WebView.HitTestResult.IMAGE_TYPE || result.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            menu.setHeaderTitle(result.getExtra());
            menu.add(0, 666, 0, "Save Image").setOnMenuItemClickListener(handler);
        }
    }

    public class ImageDownloadTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String[] p1) {
            try {
                //String filename="img_"+System.currentTimeMillis()+".png";
                URL url = new URL(p1[0]);

                //InputStream input = url.openStream();
                //File storagePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),
                //filename);

                String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

                        + File.separator
                        + "MyanmarSchoolEduImage" + File.separator;
                if (!new File(mBaseFolderPath).exists()) {
                    new File(mBaseFolderPath).mkdir();
                }

                Uri downloadUri = Uri.parse(p1[0]);

                DownloadManager.Request request = new DownloadManager.Request(downloadUri);   //(Uri.parse(url));
                //request.setTitle(bname);
				/*String mBaseFolderPath = android.os.Environment
				 .getExternalStorageDirectory()
				 + File.separator
				 + "BookStore" + File.separator;
				 if (!new File(mBaseFolderPath).exists())
				 {
				 new File(mBaseFolderPath).mkdir();
				 }*/

                String mFilePath = "file://" + mBaseFolderPath + "/" + System.currentTimeMillis() + ".png";

                request.setDestinationUri(Uri.parse(mFilePath));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                //Toast.makeText(this, "Download started.", 1).show();



				/*//OutputStream output = new FileOutputStream (storagePath);
				byte[] buffer = new byte[1024];
				int bytesRead = 0;

				while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
					output.write(buffer, 0, bytesRead);
				}

				input.close();
				output.close();*/

                return true;

            } catch (Exception e) {
                return false;

            }
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(), "File download complete.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error downloading file.", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

}



