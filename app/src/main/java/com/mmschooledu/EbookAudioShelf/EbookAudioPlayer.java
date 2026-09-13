package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.concurrent.TimeUnit;
public class EbookAudioPlayer extends AppCompatActivity
{
	TextView tvViewerTitle;


	MediaPlayer mp;
	Button btPlay,btDownload;
	boolean playing=false;
	//String link="https://drive.google.com/uc?export=download&id=1N8SFe7r4y8Qzz6vA69wlu6Lro6-DXX56";
	String link="link";


	TextView tv;
	ImageView iv;
	String bname,type,thumbnail,wname;
	ProgressDialog pd,progressDialog;

	androidx.appcompat.widget.Toolbar tb;

	private TextView tt,bd,tv1,tv2;
	//MediaPlayer mp;
	//Button button,button1;
	int duration;
	//boolean playing = false;
	Handler handler;
	Runnable runnable;
	SeekBar sBar;

	private ImageView imageview2;
	private ImageView imageview4;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ebook_audioplayer);

		//	String bname = getIntent().getStringExtra("bname");

		//String title=getIntent().getStringExtra("title");


		TextView marqueeText = (TextView) findViewById(R.id.marqueeText);
		marqueeText.setSelected(true);

		//tb = (androidx.appcompat.widget.Toolbar)findViewById(R.id.nnl_toolbar2);
		//	setSupportActionBar(tb);
		//bname = intent.getStringExtra("bname");
		iv = (ImageView)findViewById(R.id.ivDetail);
		Intent intent=getIntent();
		bname = intent.getStringExtra("bname");
		type = intent.getStringExtra("type");
		//link = intent.getStringExtra("link");
		thumbnail = intent.getStringExtra("thumbnail");
		wname = intent.getStringExtra("wname");
		setTitle(bname);
		//setTitle(bname);

		//  pd = new ProgressDialog(this);
		// pd.setTitle("Please wailt...\n"+"Link ယူနေပါသဖြင် ခဏစောင့်ပါ ");
		// progressDialog = new ProgressDialog(this);
		//progressDialog.setCancelable(false);

		Glide.with(this)
				.load(thumbnail)
				.into(iv);
		//tv.setText(title + "\n\n" + desc);
		marqueeText.setText(bname +  wname);


		imageview2 = (ImageView) findViewById(R.id.imageview2);
		imageview4 = (ImageView) findViewById(R.id.imageview4);


		//button1=(Button) findViewById(R.id.videolayoutButton2);

		tv1=(TextView) findViewById(R.id.videolayoutTextView2);
		tv2=(TextView) findViewById(R.id.videolayoutTextView3);
		//button=(Button) findViewById(R.id.videolayoutButton1);


		//btPlay=(Button)findViewById(R.id.btPlay);
		//btPlay=(Button)findViewById(R.id.btPlay);
		link = getIntent().getStringExtra("link");


		tvViewerTitle = (TextView) findViewById(R.id.tvViewerTitle);
		tvViewerTitle.setText(bname);
		setTitle(bname);
		//setTitle(bname);




		mp=MediaPlayer.create(this,Uri.parse(link));

		//mp=MediaPlayer.create(this,Uri.parse(playLink));
		mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
			@Override
			public void onPrepared(MediaPlayer p1){
				imageview2.setEnabled(true);
				//visualizer.setPlayer(mp);
				duration=mp.getDuration();
				tv2.setText(getDuration(duration));
				sBar.setMax(duration);
				sBar.setProgress(0);


			}
		});

		sBar=(SeekBar) findViewById(R.id.seekbar);

		handler=new Handler();
		runnable = new Runnable(){

			@Override
			public void run()
			{
				handler.postDelayed(this,200);
				sBar.setProgress(mp.getCurrentPosition());
				tv1.setText(getDuration(mp.getCurrentPosition()));
			}
		};

		//===== Song SeekBar Position
		sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar p1, int p2, boolean user)
			{
				if(user){
					mp.seekTo(p2);
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar p1)
			{

				if(playing){
					mp.pause();
					handler.removeCallbacks(runnable);
				}
			}

			@Override
			public void onStopTrackingTouch(SeekBar p1)
			{

				if(playing){
					mp.start();
					handler.postDelayed(runnable,200);
				}
			}


		});
		//====
	}
	//public void onClick(View v){
	//  switch(v.getId()){
	// case R.id.imageview32:

	public void onClick(View v){
		//button.setText("play");
		//button.setText("pause");
		if (v.getId() == R.id.imageview2)


			if(playing){
				mp.pause();
				//button.setText("play");


				imageview2.setImageResource(R.drawable.ebook_ic_play);

				playing=false;
				handler.removeCallbacks(runnable);

			}else{
				mp.start();
				//button.setText("pause");
				imageview2.setImageResource(R.drawable.ebook_ic_pause);

				playing=true;
				handler.postDelayed(runnable,200);
			}
			//     break;
			//   case R.id.imageview4:
			//String[] arrayLink= );
			//String playLink="https://drive.google.com/uc?export=download&id="+arrayLink[5];
			//download(link);
			//break;
			//}
			//}
	/*	public void downloadVideo(final String Link) {
	 try {
	 String mBaseFolderPath = android.os.Environment.getExternalStorageDirectory()
	 + File.separator+ "ThingyanMusic" + File.separator;
	 if (!new File(mBaseFolderPath).exists()) {
	 new File(mBaseFolderPath).mkdir();
	 }
	 String mFilePath = "file://" + mBaseFolderPath + "/" +title+ ".mp3";
	 Uri downloadUri = Uri.parse(Link);
	 DownloadManager.Request req = new DownloadManager.Request(downloadUri);
	 req.setDestinationUri(Uri.parse(mFilePath));
	 req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
	 DownloadManager dm = (DownloadManager) getSystemService(getApplicationContext().DOWNLOAD_SERVICE);
	 dm.enqueue(req);
	 Toast.makeText(this,title+"is downloading.",0).show();
	 } catch (Exception e) {
	 Toast.makeText(this,"Download Failed: "+e.toString(),0).show();
	 }
	 }*/

		else if(v.getId() == R.id.imageview4)
//        {  int permissionCheck1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
//                int permissionCheck2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//                if (permissionCheck1 == PackageManager.PERMISSION_GRANTED && permissionCheck2 == PackageManager.PERMISSION_GRANTED)
//                {
//                    //downloadFromUrl();
			download(link);
//                }
//                else
//                {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
//                    }
	}


	//public void _Back_Click(View v){
	//finish();


	//}



//
//    public void downloadClick(View v){
//        download(link);
//    }

	public void download(final String dlLink) {
		try {
			String file=bname+".mp3";
			//request.setTitle(bname);
			//String mBaseFolderPath = android.os.Environment
			//.getExternalStorageDirectory()

			String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

					+ File.separator
					+ "MyanmarSchoolEducationMp3" + File.separator;

			if (!new File(mBaseFolderPath+file).exists())
			{
				//imageview4.setText("Open");
				Uri downloadUri = Uri.parse(dlLink);

				DownloadManager.Request request = new DownloadManager.Request(downloadUri);   //(Uri.parse(url));

				/*String mBaseFolderPath = android.os.Environment
				 .getExternalStorageDirectory()
				 + File.separator
				 + "BookStore" + File.separator;
				 if (!new File(mBaseFolderPath).exists())
				 {
				 new File(mBaseFolderPath).mkdir();
				 }*/
				request.setTitle(bname);

				String mFilePath = "file://" + mBaseFolderPath + "/" + bname +".mp3";
				request.setDestinationUri(Uri.parse(mFilePath));
				request.allowScanningByMediaScanner();
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
				dm.enqueue(request);
				Toast.makeText(this, "Download started.", Toast.LENGTH_SHORT).show();
			}
			else{
				//imageview4.setText("Open");


				String link="file://" + mBaseFolderPath + "/" + bname + ".mp3" ;
				Intent intent=new Intent(EbookAudioPlayer.this,EbookAudioPlayer.class);
				intent.putExtra("link", link);
				intent.putExtra("bname", bname);
				intent.putExtra("thumbnail", thumbnail);
				startActivity(intent);



			}}
		catch (Exception e) {
			Toast.makeText(this,"Download Failed: "+e.toString(),Toast.LENGTH_SHORT).show();
		}
	}




	public static int getHour(int millisec){
		int hour=(int)TimeUnit.MILLISECONDS.toHours(millisec);
		return hour;
	}
	public static int getMinute(int millisec){
		int minutes=(int)TimeUnit.MILLISECONDS.toMinutes(millisec);
		int minute=minutes%60;
		return minute;
	}
	public static int getSecond(int millisec){
		int secs=(int) TimeUnit.MILLISECONDS.toSeconds(millisec);
		int sec=secs%60;
		return sec;
	}
	public static String getDuration(int millisec){
		String hr=String.valueOf(getHour(millisec));
		String min=String.valueOf(getMinute(millisec));
		String sec=String.valueOf(getSecond(millisec));
		if(hr.length()==1){
			hr="0"+hr;
		}
		if(min.length()==1){
			min="0"+min;
		}
		if(sec.length()==1){
			sec="0"+sec;
		}
		return hr+":"+min+":"+sec;
	}

	@Override
	public void onBackPressed()
	{
		mp.release();

		handler.removeCallbacks(runnable);
		super.onBackPressed();


	}



	public void _Back_Click(View v){
		finish();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.getItemId()==android.R.id.home){
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
				//downloadFromUrl();
				download(link);
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}

