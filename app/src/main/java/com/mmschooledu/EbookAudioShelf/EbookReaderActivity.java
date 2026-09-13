package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.*;
import android.os.*;
import android.view.*;
import android.webkit.*;

import androidx.appcompat.app.AppCompatActivity;

public class EbookReaderActivity extends AppCompatActivity
{
	WebView wv;
	String link,title;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ebook_viewer_layout);


		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
		link = getIntent().getStringExtra("link");
		title = getIntent().getStringExtra("pdfname");
		setTitle(title);
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

		if(link.contains("://drive.google.com"))
			wv.loadUrl(link);
		else
			wv.loadUrl("https://docs.google.com/viewer?url="+link);



	}

}
