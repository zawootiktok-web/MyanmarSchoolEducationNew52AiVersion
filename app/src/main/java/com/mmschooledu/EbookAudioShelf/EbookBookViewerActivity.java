package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.app.*;
import android.net.*;
import android.os.*;

import android.view.*;
import android.webkit.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.io.*;

public class EbookBookViewerActivity extends AppCompatActivity
{
	WebView wv;
	String link="",bname,wname;
	androidx.appcompat.widget.Toolbar tb;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ebook_book_viewer_layout);
		tb=(androidx.appcompat.widget.Toolbar)findViewById(R.id.nnl_toolbar3);
		setSupportActionBar(tb);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		link=getIntent().getStringExtra("link");
		bname=getIntent().getStringExtra("bname");
		wname=getIntent().getStringExtra("wname");

		setTitle(bname);
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
				public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
					if(link.contains("://drive.google.com"))
						url=getGoogleDriveDownloadLinkFromUrl(link);
					DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
					request.setTitle(bname);
					String mBaseFolderPath = android.os.Environment
						.getExternalStorageDirectory()
						+ File.separator
						+ "MyanmarAudioBook" + File.separator;
					if (!new File(mBaseFolderPath).exists()) {
						new File(mBaseFolderPath).mkdir();
					}
					String mFilePath = "file://" + mBaseFolderPath + "/" + bname + "."+ wname;
					request.setDestinationUri(Uri.parse(mFilePath));
					request.allowScanningByMediaScanner();
					request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
					DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
					dm.enqueue(request);
					Toast.makeText(getApplicationContext(),"Download started.",1).show();
				}
			});


		if(link.contains("://drive.google.com"))
			wv.loadUrl(link);
		else
			wv.loadUrl("https://docs.google.com/viewer?url="+link);

	}

	public static String getGoogleDriveDownloadLinkFromUrl( String url )
    {
        int index = url.indexOf( "id=" );
        int closingIndex=0;
        if( index >= 0 )
        {
            index += 3;
            closingIndex = url.indexOf( "&", index );
            if( closingIndex < 0 )
                closingIndex = url.length();
        }
        else
        {
            index = url.indexOf( "file/d/" );
            if( index < 0 ) // url is not in any of the supported forms
                return url;

            index += 7;

            closingIndex = url.indexOf( "/", index );
            if( closingIndex < 0 )
            {
                closingIndex = url.indexOf( "?", index );
                if( closingIndex < 0 )
                    closingIndex = url.length();
            }
        }
		String id=url.substring( index, closingIndex);
        return ("https://drive.google.com/uc?id="+id+"&export=download");
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.getItemId()==android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}


}
