package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import java.io.*;
import java.net.*;
import android.*;
//import com.google.android.gms.ads.*;
//import mobile.project.teknokitah.Menu.*;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;



import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
//import com.google.android.gms.ads.*;


public class EbookSearchViewerActivity extends Activity//AppCompatActivity
{

TextView tvViewerTitle;
	
	WebView wv;
	final int FONT_ZAWGYI=1;
	final int FONT_UNI=2;
	final int FONT_NONE=0;
	int currentFont=FONT_NONE;
	String output="";


	//  private InterstitialAd interstitial;
	//private AdView adView;     
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ebook_searchviewer_layout);



		String title1 = getIntent().getStringExtra("title");

//String published = getIntent().getStringExtra("published");

		requestAppPermissions();
		tvViewerTitle = (TextView) findViewById(R.id.tvViewerTitle);
		tvViewerTitle.setText(title1);





		wv=(WebView)findViewById(R.id.wv);

		wv.getSettings().setJavaScriptEnabled(true);
		wv.setWebViewClient(new WebViewClient(){});

		wv.getSettings().setDomStorageEnabled(true);
		wv.getSettings().setDatabaseEnabled(true);
		//wv.getSettings().setAppCacheMaxSize(1024*1024*8);
	//	wv.getSettings().setAppCachePath(getCacheDir().getAbsolutePath());
		wv.getSettings().setAllowFileAccess(true);
		//wv.getSettings().setAppCacheEnabled(true);
		wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

		WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
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

		wv.setWebViewClient(new WebViewClient(){
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					if (url != null && (url.startsWith("https://drive.") || url.startsWith("https://www.doc")
					//	|| url.startsWith("https://")
					//	|| url.startsWith("http://")
						|| url.startsWith("https://openload") || url.startsWith("https://mega")	
						|| url.startsWith("https://my")|| url.startsWith("https://download")
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
						view.getContext().startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
						return true;
					} else {
						return false;
					}
				}
			});
















		registerForContextMenu(wv);


		String title=getIntent().getStringExtra("title");
		String published=getIntent().getStringExtra("published");

		String content=getIntent().getStringExtra("content");

		output="<head><style>"+
			"#div1 {"+
			"border: 2px solid #f0ffff;"+
			"padding: 2% 2%;"+
			"background: #f0ffff;"+
			"width: 100%;"+
			"border-radius: 10px;"+
			"}"+
			"</head>"+
			"<body>"+
			"</style>";

		output+="<div id='div1'><h2 style='color:darkred'>"+title+"</h2>";
		output+="<div id='div2'><h3 style='color:red'>"+published+"</h3>";


		output+="<p style='color:green'>"+content+"<br></div><br>";

		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		currentFont= sharedPreferences.getInt("font_viewer",0);
		showPost();
	}

	private void requestAppPermissions() {
		if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			return;
		}

		if (hasReadPermissions() && hasWritePermissions()) {
			return;
		}

		ActivityCompat.requestPermissions(this,
										  new String[] {
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

	private void showPost(){
		String temp="";
		switch(currentFont){
			case FONT_ZAWGYI:
				temp=EbookFontConverter.uni2zg(output);
				break;
			case FONT_UNI:
				temp=EbookFontConverter.zg2uni(output);
				break;
			default:
				temp=output;
				break;
		}
		wv.loadDataWithBaseURL("file:///",temp,"text/html","UTF-8",null);
	}

	private void changeFont(){
		currentFont++;
		if(currentFont==3){
			currentFont=0;
		}
		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt("font_viewer",currentFont);
		editor.commit();
		showPost();
	}

	@Override
	public void onBackPressed()
	{
		if(wv.canGoBack()){
			wv.goBack();
		}else{
			super.onBackPressed();
		}
	}

	public void _Back_Click(View v){
		finish();
	}

	public void _Font_Click(View v){
		changeFont();
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

	class ImageDownloadTask extends AsyncTask<String,Void,Boolean>
	{

		@Override
		protected Boolean doInBackground(String[] p1)
		{
			try
			{
				//String filename="img_"+System.currentTimeMillis()+".png";
				URL url = new URL(p1[0]);

				//InputStream input = url.openStream();
				//File storagePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), 
				//filename);

				String mBaseFolderPath = android.os.Environment
					.getExternalStorageDirectory()
					+ File.separator
					+ "MyanmarAudioBook" + File.separator;
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

				String mFilePath = "file://" + mBaseFolderPath + "/"+  System.currentTimeMillis()  + ".png";

				request.setDestinationUri(Uri.parse(mFilePath));
				request.allowScanningByMediaScanner();
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
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

			} 





			catch (Exception e)
			{
				return false;

			}
		}






		@Override
		protected void onPostExecute(Boolean result)
		{
			if(result){
				Toast.makeText(getApplicationContext(),"File download complete.",Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(),"Error downloading file.",Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}
	}
}
	
