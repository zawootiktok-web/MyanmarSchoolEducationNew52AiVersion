package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;

import android.view.*;
import android.widget.*;
//import com.squareup.picasso.*;
import java.io.*;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class EbookDetailActivity extends AppCompatActivity
{
	
	TextView tv;
	ImageView iv;
	String title,type,link,image,desc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ebook_detail_layout1);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		tv=(TextView)findViewById(R.id.tvDetail);
		iv=(ImageView)findViewById(R.id.ivDetail);
		Intent intent=getIntent();
		title=intent.getStringExtra("title");
		type=intent.getStringExtra("type");
		link=intent.getStringExtra("link");
		image=intent.getStringExtra("image");
		desc=intent.getStringExtra("desc");
		setTitle(title);
		Glide.with(this)
			.load(image)
			.into(iv);
		tv.setText("Apk Name :"+title+"\n\n"+"About :"+desc);
	}
	public void onClick(View v){
		return;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.getItemId()==android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

    public void downloadClick(View v) {
        downloadFromUrl();
    }

	
    

    private void downloadFromUrl(){
		String url=link;
		if(url.contains("://drive.google.com"))
			url=getGoogleDriveDownloadLinkFromUrl(link);
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
		String file=title+".pdf";
		String mBaseFolderPath = android.os.Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "MyanmarAudioBook" + File.separator;
		if (!new File(mBaseFolderPath).exists()) {
			new File(mBaseFolderPath).mkdir();
		}

		String mFilePath = "file://" + mBaseFolderPath + "/" + title + "."+type;
		request.setDestinationUri(Uri.parse(mFilePath));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
		dm.enqueue(request);
		Toast.makeText(this,"Download started.",1).show();
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

	

}
