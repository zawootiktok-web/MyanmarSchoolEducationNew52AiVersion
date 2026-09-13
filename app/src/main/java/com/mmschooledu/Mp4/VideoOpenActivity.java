package com.mmschooledu.Mp4;
//
//import android.Manifest;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.Settings;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.AdapterView.OnItemLongClickListener;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.mmschooledu.FileProvider;
//import com.mmschooledu.R;
//
//import java.io.File;
////import com.google.android.gms.ads.*;
//
//public class VideoOpenActivity extends AppCompatActivity
//{  final int storageRequestCode=123;
//    private static final int resultCode = 0;
//    static String[] titles,patch;
//    private ListView lv;
//    com.mmschooledu.EbookAudioShelf.EbookGalleryActivity.MyAdapter adapter;
//    androidx.appcompat.widget.Toolbar tb;
//    //AdView adview0;
//    //AdRequest req0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_gallery);
//        tb=(androidx.appcompat.widget.Toolbar)findViewById(R.id.nnl_toolbar);
//        setSupportActionBar(tb);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        //  adview0=(AdView) findViewById(R.id.adView);
//        //checkPermission();
//
//        requestAppPermissions();
//        lv = (ListView) findViewById(R.id.lv);
//        //loadBanner();
//        movieList();
//        adapter=new .MyAdapter();
//        lv.setAdapter(adapter);
//        lv.setOnItemClickListener(new OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
//            {
//                try{
//                    File f = new File(patch[p3]);
//                    Uri fileUri = Uri.fromFile(f);
//                    if (Build.VERSION.SDK_INT >= 24) {
//                        fileUri = FileProvider.getUriForFile(VideoOpenActivity.this, VideoOpenActivity.this. getPackageName() + ".provider",
//                                f);
//                    }
//                    Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
//                    intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
//                    intent.setDataAndType(fileUri, "application/pdf");
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    startActivity(intent);
//                }catch(Exception e) {
//
//                }
//                //	checkPermission();
////requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
//            }
//        });
//
//
//        lv.setOnItemLongClickListener(new OnItemLongClickListener(){
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> p1, View p2, final int p3, long p4)
//            {
//
//
//                final AlertDialog dialog = new AlertDialog.Builder(VideoOpenActivity.this).create();
//                View parent_view = LayoutInflater.from(VideoOpenActivity.this).inflate(R.layout.two_button_dia, null);
//                dialog.setView(parent_view);
//                dialog.setCancelable(true);
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                dialog.show();
//                ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
//                TextView tv_title = (TextView) dialog.findViewById(R.id.title);
//                TextView tv_message = (TextView) dialog.findViewById(R.id.message);
//                final Button call = (Button) dialog.findViewById(R.id.bt2);
//                final Button cancel= (Button) dialog.findViewById(R.id.bt1);
//                iv.setImageResource(R.drawable.delete_icon);
//                tv_title.setText("Book");
//                tv_message.setText(titles[p3]+"What do this");
//                cancel.setText("Delete");
//                cancel.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View p1)
//                    {
//                        try{
//
//                            File file = new File(patch[p3]);
//                            file.delete();
//                            movieList();
//                            adapter=new .MyAdapter();
//                            lv.setAdapter(adapter);
//                        }catch(Exception e){
//                            Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_SHORT).show();
//
//                        }
//                        dialog.dismiss();
//                    }});
//                call.setText("Read");
//                call.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View p1)
//                    {
//                        try{
//                            File f = new File(patch[p3]);
//                            Uri fileUri = Uri.fromFile(f);
//                            if (Build.VERSION.SDK_INT >= 24) {
//                                fileUri = FileProvider.getUriForFile(VideoOpenActivity.this, VideoOpenActivity.this. getPackageName() + ".provider",
//                                        f);
//                            }
//                            Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
//                            intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
//                            intent.setDataAndType(fileUri, "application/pdf");
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                            startActivity(intent);
//                        }catch(Exception e) {
//
//                        }
//                        dialog.dismiss();
//                    }});
//                // TODO: Implement this method
//                return true;
//            }
//        });
//    }
//
//
//
//    private void requestAppPermissions() {
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//            ActivityCompat.requestPermissions(VideoOpenActivity.this,new String[]{
//                    android.Manifest.permission.READ_MEDIA_AUDIO,
//                    android.Manifest.permission.READ_MEDIA_IMAGES,
//                    android.Manifest.permission.READ_MEDIA_VIDEO
//            },resultCode);
//            //request13Permission();
//            Toast.makeText(this, "You Need To Accept Storage Permission.", Toast.LENGTH_SHORT).show();
//
//
//        }else{
//            ActivityCompat.requestPermissions(VideoOpenActivity.this,new String[]{
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//            },resultCode);
//            //requestPermission();
//
//            Toast.makeText(this, "You Need To Accept Storage Permission.", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
//
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
//    public void request13Permission(){
//
//        if( ContextCompat.checkSelfPermission(VideoOpenActivity.this, android.Manifest.permission.READ_MEDIA_AUDIO)== PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(VideoOpenActivity.this, android.Manifest.permission.READ_MEDIA_IMAGES)== PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(VideoOpenActivity.this, android.Manifest.permission.READ_MEDIA_VIDEO)== PackageManager.PERMISSION_GRANTED
//        ){
//            showAlert("Great!!","You did made permission request.");
//        }else{
//            ActivityCompat.requestPermissions(VideoOpenActivity.this,new String[]{android.Manifest.permission.READ_MEDIA_AUDIO, android.Manifest.permission.READ_MEDIA_IMAGES, android.Manifest.permission.READ_MEDIA_VIDEO},storageRequestCode);
//        }
//
//    }
//    public void requestPermission(){
//        if(ContextCompat.checkSelfPermission(VideoOpenActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(VideoOpenActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
//        ){
//            showAlert("Great!!","You did made permission request.");
//        }else{
//            ActivityCompat.requestPermissions(VideoOpenActivity.this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},storageRequestCode);
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==storageRequestCode) {
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                if (grantResults.length == 3 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED &&  grantResults[1] == PackageManager.PERMISSION_GRANTED &&  grantResults[2] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "You can press Button now.", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(this, "You Need To Accept 13 Permission.", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "You can press Button now.", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(this, "You Need To Accept Storage Permission.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }
//    //	public void showAlert(String title, String message){
////		MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder(this);
////		builder1.setTitle(title);
////		builder1.setMessage(message);
////		builder1.setCancelable(true);
////		builder1.setPositiveButton(
////				"OK",
////				(dialog, id) -> {
////					dialog.cancel();
////				});
////		builder1.show();
////	}
////
////}
//    public void showAlert(String title, String message){
//        new android.app.AlertDialog.Builder(VideoOpenActivity.this)
//                .setTitle("Alert for Permission")
//                .setMessage("Go to Settings for Permissions")
//                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ///  code to go to settings of application
//                        Intent intent = new Intent();
//                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", getPackageName(), null);
//                        intent.setData(uri);
//                        startActivity(intent);
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//
//                        Toast.makeText(VideoOpenActivity.this, "Setting မှ Storage Permission ဖွင့်\u200Bပေးမှ စာအုပ်များ Down ယူရရှိနိုင်ပါမည်...", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                }).show();
//
//
//    }
//
//	/*void loadBanner()  {
//	 req0= new AdRequest.Builder().build();
//	 adview0.loadAd(req0);
//
//
//	 }*/
//
//	 /*
//	private static final int MY_PERMISSION_REQUEST_CODE = 123;
//    protected void checkPermission() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//				if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//					AlertDialog.Builder builder = new AlertDialog.Builder(this);
//					builder.setMessage("Write external storage permission is required.");
//					builder.setTitle("Please grant permission");
//					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialogInterface, int i) {
//								ActivityCompat.requestPermissions(GalleryActivity.this,
//																  new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
//																  MY_PERMISSION_REQUEST_CODE);
//							}
//						});
//					builder.setNeutralButton("Cancel", null);
//					AlertDialog dialog = builder.create();
//					dialog.show();
//				} else {
//					ActivityCompat.requestPermissions(GalleryActivity.this,
//													  new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, MY_PERMISSION_REQUEST_CODE);
//				}
//			}}} */
//
//
//
//    public void movieList() {
//        try {
//            //String file=bname+".pdf";
//
//
//            //String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//            //+ File.separator
//            //+ "MyanmarSchoolEducation" + File.separator;
//
//            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "MyanmarSchoolEducation");
//            File[] files = file.listFiles();
//
//
//            //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "MyanmarSchoolEducation");
//            //File[] files = file.listFiles();
//
//            patch = new String[files.length];
//            titles = new String[files.length];
//            for (int i = 0; i < files.length; i++) {
//                patch[i] = files[i].getAbsolutePath();
//                titles[i] = files[i].getName();
//            }
//        }catch (Exception e){
//
//        }
//    }
//    public class MyAdapter extends BaseAdapter
//    {
//        @Override
//        public int getCount()
//        {
//// TODO: Implement this method
//            return titles.length;
//        }
//        @Override
//        public Object getItem(int p1)
//        {
//// TODO: Implement this method
//            return titles[p1];
//        }
//        @Override
//        public long getItemId(int p1)
//        {
//// TODO: Implement this method
//            return p1;
//        }
//        @Override
//        public View getView(final int p1, View p2, ViewGroup p3)
//        {
//            View view= VideoOpenActivity.this.getLayoutInflater().inflate(R.layout.gallery_item,p3,false);
//            ImageView iv=view.findViewById(R.id.iv_item);
//            final TextView tv=view.findViewById(R.id.tv_item);
//            iv.setImageResource(R.drawable.ic_pdf);
//            String[] ta=titles[p1].split(".mp4");
//            String fn=ta[0];
//            tv.setText(fn);
//            return view;
//        }
//
//
//
//    }
//
//
//    public void playVideofile(final String path){
//        final File file=new File(path);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        int id = item.getItemId();
//        if(id==android.R.id.home){
//            finish();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//}



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.mmschooledu.R;

import java.io.File;

public class VideoOpenActivity extends AppCompatActivity {


    final int storageRequestCode = 123;
    private static final int resultCode = 0;
    static String[] titles, patch;
    private ListView lv;
    MyAdapter adapter;
    androidx.appcompat.widget.Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);


        tb = (androidx.appcompat.widget.Toolbar) findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestAppPermissions();

        lv = (ListView) findViewById(R.id.lv);
        movieList();
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = new File(patch[position]);
                if (file.getName().endsWith(".mp4") || file.getName().endsWith(".avi")) {
                    playVideoFile(file.getAbsolutePath());
                }
            }
        });

        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle long click actions here
                return true;
            }
        });
    }

    private void movieList() {
        try {
            //String file=bname+".pdf";


            //String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            //+ File.separator
            //+ "MyanmarSchoolEducation" + File.separator;

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "MyanmarSchoolEducationVideo");
            File[] files = file.listFiles();


            //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "MyanmarSchoolEducation");
            //File[] files = file.listFiles();

            patch = new String[files.length];
            titles = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                patch[i] = files[i].getAbsolutePath();
                titles[i] = files[i].getName();
            }
        } catch (Exception e) {

        }
    }

    private void requestAppPermissions() {
        ActivityCompat.requestPermissions(VideoOpenActivity.this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                resultCode);
    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(VideoOpenActivity.this).inflate(R.layout.gallery_item, parent, false);
            ImageView iv = view.findViewById(R.id.iv_item);
            TextView tv = view.findViewById(R.id.tv_item);

            File file = new File(patch[position]);
            if (file.getName().endsWith(".pdf")) {
                iv.setImageResource(R.drawable.ic_pdf);
            } else if (file.getName().endsWith(".mp4") || file.getName().endsWith(".avi")) {
                iv.setImageResource(R.drawable.movieseries);
            }

            tv.setText(file.getName());
            return view;
        }
    }

    public void playVideoFile(String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(path);
        Uri videoUri = Uri.fromFile(file);
        intent.setDataAndType(videoUri, "video/*");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == storageRequestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage permission granted. You can now access files.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Storage permission denied. You cannot access files.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    }
