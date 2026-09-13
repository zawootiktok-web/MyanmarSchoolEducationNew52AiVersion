package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import java.io.*;

import android.content.pm.*;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.interstitial.InterstitialAd;

public class EbookPdfDetailActivity extends AppCompatActivity
{static String[] titles,patch;
	Button button;


    private InterstitialAd interstitial;
	private AdView adView;     

	TextView tv,tv2;
	ImageView iv;
	String bname,type,link,link2,thumbnail,wname;
	//androidx.appcompat.widget.Toolbar tb;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ebook_pdfdetail_layout);
		/*tb = (androidx.appcompat.widget.Toolbar)findViewById(R.id.nnl_toolbar);
		 setSupportActionBar(tb);
		 getSupportActionBar().setHomeButtonEnabled(true);
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 */



		adView = (AdView) 
			findViewById(R.id.ad_view);           
		AdRequest adRequest = new AdRequest.Builder().build();           
		adView.loadAd(adRequest); 



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

	public void downloadClick(View v)
	{
//		int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//		int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//		if (permissionCheck1 == PackageManager.PERMISSION_GRANTED && permissionCheck2 == PackageManager.PERMISSION_GRANTED)
//		{
			downloadFromUrl();
//		}
//		else
//		{
//			requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
	}

	public void _Back_Click(View v){
		finish();


	}



	public void readClick(View v)
	{
		Intent intent=new Intent(this, EbookBookViewerActivity.class);
		intent.putExtra("bname", bname);
		intent.putExtra("type", type);
		intent.putExtra("link", link);
		startActivity(intent);
	}




	private void downloadFromUrl()
	{
		String url=link2;
		if (url.contains("://drive.google.com"))
			url = getGoogleDriveDownloadLinkFromUrl(link2);
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		request.setTitle(bname);

		String file=bname+".pdf";

		String mBaseFolderPath = android.os.Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "MyanmarSchoolEducation" + File.separator;
		if (!new File(mBaseFolderPath+file).exists())
		{
			//new File(mBaseFolderPath+file).mkdir();

			//Toast.makeText(this, "No No No Download started.", 1).show();
			//	button.setText("Downရန်");
			button.setText("Open");

			String mFilePath = "file://" + mBaseFolderPath + "/" + bname + ".pdf" ;
			request.setDestinationUri(Uri.parse(mFilePath));
			request.allowScanningByMediaScanner();
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
			dm.enqueue(request);

			Toast.makeText(this, "Download started.", 1).show();

		}else{

			//	Toast.makeText(this, "Download လုပ်ပြီးပာပြီ", 1).show();

			//startActivity(new Intent(BookDetailActivity.this,EbookGalleryActivity.class));


			/*
			 File f = new File("file://" + mBaseFolderPath + "/" + bname + ".pdf");
			 //if (f.exists()) {
			 //System.out.println("File exists");
			 //} else {
			 //System.out.println("File does not exist");
			 //}



			 //int p3 = 0;
			 //File f = new File(patch["file://" + mBaseFolderPath + "/" + bname + ".pdf"]);
			 Uri fileUri = Uri.fromFile(f);
			 if (Build.VERSION.SDK_INT >= 24) {
			 fileUri = EbookFileProvider.getUriForFile(BookDetailActivity.this, BookDetailActivity.this. getPackageName() + ".provider",
			 f);
			 }
			 Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
			 intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
			 intent.setDataAndType(fileUri, "application/pdf");
			 //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			 //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			 startActivity(intent);
			 //}catch(Exception e) {

			 //} 
			 //	checkPermission();
			 //requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);

			 //});*/


			//Good Code

			button.setText("Open");

			Uri fileUri = Uri.parse("file://" + mBaseFolderPath + "/" + bname + ".pdf" );
			Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
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
