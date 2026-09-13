package com.mmschooledu.Viewer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.mmschooledu.R;

//import com.mmschooledu.R;

public class VideoViewer extends Activity
{
	MediaController mc;

	String link;

	VideoView vv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);

		ImageButton rotate = findViewById(R.id.rotatevideo);
		rotate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int rotation = getWindowManager().getDefaultDisplay().getRotation();
					if (rotation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					}else{
						setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
					}
				}
			});





		vv=findViewById(R.id.vv);
		link=getIntent().getStringExtra("link");
		vv.setVideoURI(Uri.parse(link));

		MediaController mc=new MediaController(this);
		vv.setMediaController(mc);

		vv.requestFocus();
		vv.start();

	}


}
