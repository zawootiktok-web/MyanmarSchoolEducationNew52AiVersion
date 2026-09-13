package com.mmschooledu;


import android.app.*;
import android.os.*;
import android.content.*;
import java.io.*;
import org.json.*;
import android.content.pm.*;
import android.net.*;
import android.database.*;
import android.provider.*;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.text.Html;


public class CheckUpdateAsyncTask {
    private Activity activity;
    private String downloadPath;
    private DownloadManager mDownloadManager;
    private long downloadedFileID;
    private DownloadManager.Request mRequest;
    private String download_link = null;
    private int versionCode=0;
    private String json;

	ProgressDialog alert2;

	public CheckUpdateAsyncTask(Activity activity, String json) {
        this.json = json;
        this.activity = activity; 

		mDownloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/";
		File n = new File(downloadPath);
        if (!n.exists()){
            n.mkdirs();
        }

		letCheck(json);
    }


    private void letCheck(String response){

        if (response!=null) {
            try {
                JSONObject jo=new JSONObject(response);
                JSONArray ja=jo.getJSONObject("feed").getJSONArray("entry");
                JSONObject jo2;
                for(int i=0;i<ja.length();i++){
                    jo2=ja.getJSONObject(i);
                    if(jo2.getJSONObject("title").getString("$t").equals("app-pdate.json")){
                        String content=jo2.getJSONObject("content").getString("$t");
                        String orgjson=Html.fromHtml(content).toString();
                        JSONObject obj = new JSONObject(orgjson);
                        String title = obj.getString("title");
                        String msg = obj.getString("message");
                        download_link = obj.getString("link");
                        boolean force = obj.getBoolean("force");
                        versionCode = obj.getInt("versionCode");

                        PackageManager manager = activity.getPackageManager();
                        PackageInfo info;
                        int currentVersion = 0;
                        try {
                            info = manager.getPackageInfo(activity.getPackageName(), 0);
                            currentVersion = info.versionCode;
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }

                        if (versionCode > currentVersion){
                            letUpdate(title,msg, force);          
                        }else{

						}


                    }}
            } catch (JSONException e) {
                //Toast.makeText(activity,""+e.toString(),Toast.LENGTH_LONG).show();
            }
        }}

    private void letUpdate(final String title, final String message, final boolean force){
		final AlertDialog dialog = new AlertDialog.Builder(activity).create();
		View parent_view = LayoutInflater.from(activity).inflate(R.layout.two_button_dia, null);
		dialog.setView(parent_view);
		dialog.setCancelable(false);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.show();
		ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
		TextView tv_title = (TextView) dialog.findViewById(R.id.title);
		TextView tv_message = (TextView) dialog.findViewById(R.id.message);
		Button call = (Button) dialog.findViewById(R.id.bt2);
		Button cancel= (Button) dialog.findViewById(R.id.bt1);
		iv.setImageResource(R.drawable.ic_update);
		tv_title.setText(title);
		tv_message.setText(message);
		cancel.setText("Download");
		if (!force){
			cancel.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View p1)
					{
						downloadFile(download_link, activity.getString(R.string.app_name) + "_v" + Integer.toString(versionCode) + ".apk");

					}});
		}else{
			cancel.setVisibility(View.INVISIBLE);
		}

		call.setText("Get on Playstore");
		call.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					activity.startActivity(new Intent(Intent.ACTION_VIEW,
													  Uri.parse("https://play.google.com/store/apps/details?id="+activity.getPackageName())));

				}});


    }







	private void downloadFile(String url, String fileName){ 
		alert2 = new ProgressDialog(activity);
		alert2.setMessage("downloading ...");
		alert2.setCancelable(false);
		alert2.show();  
		try {
            String mBaseFolderPath = downloadPath;
            if (!new File(mBaseFolderPath).exists()) {
                new File(mBaseFolderPath).mkdir();
            }
            File myFile = new File(mBaseFolderPath+fileName);

			if (!myFile.exists()) {
                String mFilePath = "file://" + mBaseFolderPath + fileName;
                Uri downloadUri = Uri.parse(url);
                mRequest = new DownloadManager.Request(downloadUri);
                mRequest.setDestinationUri(Uri.parse(mFilePath));
                mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadedFileID = mDownloadManager.enqueue(mRequest);
                IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                activity.registerReceiver(downloadReceiver, filter);
			}else{
                openFile(myFile.toString());
            }
        } catch (Exception e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final Uri uri = mDownloadManager.getUriForDownloadedFile(downloadedFileID);
            final String apk = getRealPathFromURI(uri);
			openFile(apk);
		}
    };

    private void openFile(String apk){
		if (alert2 != null){
			alert2.dismiss();
		}
		File f = new File(apk);
		Uri fileUri = Uri.fromFile(f);
		if (Build.VERSION.SDK_INT >= 24) {
			fileUri = FileProvider.getUriForFile(activity, activity. getPackageName() + ".provider",
												 f);
		}
		Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
		intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
		intent.setDataAndType(fileUri, "application/vnd.android" + ".package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		activity.	startActivity(intent);
		activity.finish();
    }

    private String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }


}


