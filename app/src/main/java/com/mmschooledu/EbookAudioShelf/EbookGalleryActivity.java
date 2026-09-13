package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.os.*;
import android.widget.*;
import android.view.*;
import java.io.*;
import android.content.*;
import android.net.*;
import android.widget.AdapterView.*;
import android.Manifest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
//import com.google.android.gms.ads.*;

public class EbookGalleryActivity extends AppCompatActivity
{
	static String[] titles,patch;
    private ListView lv;
    MyAdapter adapter;
	androidx.appcompat.widget.Toolbar tb;
	//AdView adview0;
	//AdRequest req0;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.ebook_activity_gallery);
		tb=(androidx.appcompat.widget.Toolbar)findViewById(R.id.nnl_toolbar);
		setSupportActionBar(tb);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//  adview0=(AdView) findViewById(R.id.adView); 
		//checkPermission();
		
		requestAppPermissions();
		lv = (ListView) findViewById(R.id.lv);
		//loadBanner();
		movieList();
		adapter=new MyAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{	
					try{
						File f = new File(patch[p3]);
						Uri fileUri = Uri.fromFile(f);
						if (Build.VERSION.SDK_INT >= 24) {
							fileUri = EbookFileProvider.getUriForFile(EbookGalleryActivity.this, EbookGalleryActivity.this. getPackageName() + ".provider",
																 f);
						}
						Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
						intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
						intent.setDataAndType(fileUri, "application/pdf");
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
						startActivity(intent);
					}catch(Exception e) {

					} 
				//	checkPermission();
//requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
				}
			});
			
	
		lv.setOnItemLongClickListener(new OnItemLongClickListener(){

				@Override
				public boolean onItemLongClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{


					final AlertDialog dialog = new AlertDialog.Builder(EbookGalleryActivity.this).create();
					View parent_view = LayoutInflater.from(EbookGalleryActivity.this).inflate(R.layout.ebook_two_button_dia, null);
					dialog.setView(parent_view);
					dialog.setCancelable(true);
					dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
					dialog.show();
					ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
					TextView tv_title = (TextView) dialog.findViewById(R.id.title);
					TextView tv_message = (TextView) dialog.findViewById(R.id.message);
					final Button call = (Button) dialog.findViewById(R.id.bt2);
					final Button cancel= (Button) dialog.findViewById(R.id.bt1);
					iv.setImageResource(R.drawable.ebook_delete_icon);
					tv_title.setText("Book");
					tv_message.setText(titles[p3]+"What do this");
					cancel.setText("Delete");
					cancel.setOnClickListener(new View.OnClickListener(){
							@Override
							public void onClick(View p1)
							{
								try{

									File file = new File(patch[p3]);
									file.delete();
									movieList();
									adapter=new MyAdapter();
									lv.setAdapter(adapter);
								}catch(Exception e){
									Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_SHORT).show();

								}
								dialog.dismiss();
							}});
					call.setText("Read");
					call.setOnClickListener(new View.OnClickListener(){
							@Override
							public void onClick(View p1)
							{
								try{
									File f = new File(patch[p3]);
									Uri fileUri = Uri.fromFile(f);
									if (Build.VERSION.SDK_INT >= 24) {
										fileUri = EbookFileProvider.getUriForFile(EbookGalleryActivity.this, EbookGalleryActivity.this. getPackageName() + ".provider",
																			 f);
									}
									Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
									intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
									intent.setDataAndType(fileUri, "application/pdf");
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
									intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
									startActivity(intent);
								}catch(Exception e) {

								} 
								dialog.dismiss();
							}});
					// TODO: Implement this method
					return true;
				}
			});
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


	
	/*void loadBanner()  {
	 req0= new AdRequest.Builder().build();
	 adview0.loadAd(req0); 


	 }*/
	 
	 /*
	private static final int MY_PERMISSION_REQUEST_CODE = 123;
    protected void checkPermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("Write external storage permission is required.");
					builder.setTitle("Please grant permission");
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								ActivityCompat.requestPermissions(EbookGalleryActivity.this,
																  new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
																  MY_PERMISSION_REQUEST_CODE);
							}
						});
					builder.setNeutralButton("Cancel", null);
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {
					ActivityCompat.requestPermissions(EbookGalleryActivity.this,
													  new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, MY_PERMISSION_REQUEST_CODE);
				}
			}}} */
		
	
	 
	public void movieList() {
		try {
			File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "MyanmarSchoolEducation");  
			File[] files = file.listFiles();

			patch = new String[files.length];
			titles = new String[files.length];
			for (int i = 0; i < files.length; i++) {
				patch[i] = files[i].getAbsolutePath();
				titles[i] = files[i].getName();
			}
		}catch (Exception e){

		}
	}
	public class MyAdapter extends BaseAdapter
	{
		@Override
		public int getCount()
		{
// TODO: Implement this method
			return titles.length;
		}
		@Override
		public Object getItem(int p1)
		{
// TODO: Implement this method
			return titles[p1];
		}
		@Override
		public long getItemId(int p1)
		{
// TODO: Implement this method
			return p1;
		}
		@Override
		public View getView(final int p1, View p2, ViewGroup p3)
		{
			View view=EbookGalleryActivity.this.getLayoutInflater().inflate(R.layout.ebook_gallery_item,p3,false);
			ImageView iv=view.findViewById(R.id.iv_item);
			final TextView tv=view.findViewById(R.id.tv_item);
			iv.setImageResource(R.drawable.ebook_ic_pdf);
			String[] ta=titles[p1].split(".pdf");
			String fn=ta[0];
			tv.setText(fn);
			return view;
		}
		
		
		
	}
	
	
	public void playVideofile(final String path){
		final File file=new File(path);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        return super.onCreateOptionsMenu(menu);
    }
	
	

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
