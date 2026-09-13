package com.mmschooledu;


import android.app.*;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.webkit.*;
import android.widget.Toast;

public class ChatActivity extends Activity
{
    private static final String CHAT_URL = "https://my.cbox.ws/MyanmarSchoolEduApp";
    private static final String ALLOWED_CHAT_HOST = "my.cbox.ws";
    private WebView wv;
    private boolean loadErrorShown;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatactivity_main);

        wv = findViewById(R.id.wv);
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            settings.setSafeBrowsingEnabled(true);
        }

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView web, WebResourceRequest request) {
                return handleUrl(web, request.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView web, String url) {
                return handleUrl(web, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                loadErrorShown = false;
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) {
                    showLoadError();
                }
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                showLoadError();
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, android.webkit.SslErrorHandler handler,
                                           android.net.http.SslError error) {
                handler.cancel();
                showLoadError();
            }
        });

        wv.loadUrl(CHAT_URL);
    }

    private boolean handleUrl(WebView web, String url) {
        Uri uri = Uri.parse(url);
        String host = uri.getHost();
        if ("https".equalsIgnoreCase(uri.getScheme())
                && host != null
                && (ALLOWED_CHAT_HOST.equalsIgnoreCase(host)
                || host.toLowerCase(java.util.Locale.US).endsWith(".cbox.ws"))) {
            return false;
        }

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (Exception e) {
            Toast.makeText(this, "Cannot open link", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (wv != null) {
            wv.stopLoading();
            wv.setWebViewClient(null);
            wv.destroy();
            wv = null;
        }
        super.onDestroy();
    }

    private void showLoadError() {
        if (loadErrorShown || isFinishing()) return;
        loadErrorShown = true;
        new AlertDialog.Builder(this)
                .setTitle("Live Chat unavailable")
                .setMessage("The live chat server could not be reached. Please retry or open it in a browser.")
                .setCancelable(true)
                .setPositiveButton("Retry", (dialog, which) -> {
                    loadErrorShown = false;
                    wv.reload();
                })
                .setNegativeButton("Open browser", (dialog, which) -> {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(CHAT_URL)));
                    } catch (Exception e) {
                        Toast.makeText(this, "No browser available", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }
}
