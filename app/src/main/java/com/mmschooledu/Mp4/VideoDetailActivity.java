package com.mmschooledu.Mp4;


import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bdtopcoder.quickadmob.onDismiss;
import com.bumptech.glide.Glide;
import com.mmschooledu.Player.VideoExoPlayer;
import com.mmschooledu.R;

import java.io.File;


public class VideoDetailActivity extends AppCompatActivity
{
	Button button;


	TextView tv,tv2;
	ImageView iv;
	String bname,type,link,link2,thumbnail,wname;
	//androidx.appcompat.widget.Toolbar tb;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videodetail_layout);
		/*tb = (androidx.appcompat.widget.Toolbar)findViewById(R.id.nnl_toolbar);
		 setSupportActionBar(tb);
		 getSupportActionBar().setHomeButtonEnabled(true);
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 */




		button=(Button) findViewById(R.id.videolayoutButton1);
		
		tv2 = (TextView)findViewById(R.id.tvViewerTitle);

		tv = (TextView)findViewById(R.id.tvDetail);
		iv = (ImageView)findViewById(R.id.ivDetail);
		Intent intent=getIntent();
		bname = intent.getStringExtra("bname");
		type = intent.getStringExtra("type");
		link = intent.getStringExtra("link");
		link2 = intent.getStringExtra("link2");
		thumbnail = intent.getStringExtra("thumbnail");
		wname = intent.getStringExtra("wname");
		setTitle(bname);
		Glide.with(this)
			.load(thumbnail)
			.into(iv);
		tv.setText(bname + "\n\n" + wname);

		tv2.setText(bname);
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

	public void downloadClick(View v) {
		/*int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
		int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permissionCheck1 == PackageManager.PERMISSION_GRANTED && permissionCheck2 == PackageManager.PERMISSION_GRANTED)
		{
			downloadFromUrl();
		}
		else
		{
			requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
		}}
*/

		new com.bdtopcoder.quickadmob.Admob(new onDismiss() {
			@Override
			public void onDismiss() {

		downloadFromUrl();



		//StartAppAd.showAd(getBaseContext());

	}
}).ShowInterstitial(VideoDetailActivity.this, true);
		}

	public void _Back_Click(View v){
		finish();


	}



	public void readClick(View v)
	{
		new com.bdtopcoder.quickadmob.Admob(new onDismiss() {
			@Override
			public void onDismiss() {
		Intent intent=new Intent(VideoDetailActivity.this, VideoExoPlayer.class);
		intent.putExtra("bname", bname);
		//intent.putExtra("type", type);
		intent.putExtra("link", link);
		startActivity(intent);
//		StartAppAd.showAd(getBaseContext());

			}
		}).ShowInterstitial(VideoDetailActivity.this, true);
	}


	/*public void readClick2(View v)Z
	{
		Intent intent=new Intent(this, VideoViewer.class);
		//intent.putExtra("bname", bname);
		//intent.putExtra("type", type);
		intent.putExtra("link", link);
		startActivity(intent);
	}*/




	private void downloadFromUrl()
	{
		String file=bname+".mp4";

		String url=link2;
		if (url.contains("://drive.google.com"))
			url = getGoogleDriveDownloadLinkFromUrl(link2);
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		request.setTitle(bname);
		String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
			
			+ File.separator
			+ "MyanmarSchoolEducationVideo" + File.separator;
		//if (!new File(mBaseFolderPath).exists())
		//{
			
			//new File(mBaseFolderPath).mkdir();
		if (!new File(mBaseFolderPath+file).exists())
		{
			
			button.setText("Open");
		

		String mFilePath = "file://" + mBaseFolderPath + "/" + bname + ".mp4" ;
		request.setDestinationUri(Uri.parse(mFilePath));
		request.allowScanningByMediaScanner();
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
		dm.enqueue(request);
		Toast.makeText(this, "Download started.", 1).show();
	}

	else{
	button.setText("Open");

	
		String link="file://" + mBaseFolderPath + "/" + bname + ".mp4" ;
		Intent intent=new Intent(VideoDetailActivity.this, VideoExoPlayer.class);
		intent.putExtra("link", link);
		startActivity(intent);			
		
		
	
	}}
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
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		if (requestCode == 101)
		{
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
				&& grantResults[1] == PackageManager.PERMISSION_GRANTED)
			{
				downloadFromUrl();
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

}
