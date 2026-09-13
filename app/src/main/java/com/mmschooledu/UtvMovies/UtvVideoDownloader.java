package com.mmschooledu.UtvMovies;


import static android.content.Context.DOWNLOAD_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mmschooledu.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class UtvVideoDownloader extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    public TextView tv;

    DownloadManager dm;
    DownloadManager.Request req;
    String dLink="";
    String name="";

    private static final int REQUEST_WRITE_STORAGE_PERMISSION = 1;
    public UtvVideoDownloader(Activity a,String link) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.dLink=link;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.umvvideo_download);
        tv=findViewById(R.id.tvTitle);

        yes = (Button) findViewById(R.id.btnOK);
        no = (Button) findViewById(R.id.btnCancel);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        new WebScrapeTask().execute(dLink);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnOK) {
            downloadMedia(name);
              //  new WebScrapeTask().execute(dLink);

           // c.finish();
        } else if (v.getId() == R.id.btnCancel) {

            dismiss();

        }
        dismiss();
    }

    public class WebScrapeTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String[] p1) {
            try {
                Document doc = Jsoup.connect(p1[0]).get();
                return process(doc);
            } catch (IOException e) {
                e.printStackTrace();
                return "NO Result";
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.length()>0){
                name=result;
                tv.setText("Do you want to download "+result+" ?");
            }else{
                dismiss();
                Toast.makeText(getContext(), "Can't Download", Toast.LENGTH_LONG).show();
            }




        }

    }

    public String process(Document doc) {

        Elements elements = doc.select("li.col-sm-4.col-xs-3");
        String res = "";
        for (Element e : elements) {
            String link = e.select("div").first().attr("onclick");
            String playLink = link.substring(44);
            String name = playLink.substring(0, playLink.indexOf("/"));
            //res+=playLink+"\n";
            res = name;


        }
        return res;

    }

    public void downloadMedia(String name) {
        String downloadLink = "http://www.zegomovie.com/Download/" + name + "/" + name + ".mp4";
       // File dir = new File(Environment.getExternalStorageDirectory() + "/UMV");
       // File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES+"/UMV");


        if (!dir.exists()) {
            boolean created = dir.mkdir();

            if (created) {
                Toast.makeText(getContext(), "Folder Created ", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getContext(), "Can't create folder ", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Folder already exists.", Toast.LENGTH_LONG).show();
        }

        String mFilePath = "file://" + dir + "/" + name + ".mp4";

        Uri downloadUri = Uri.parse(downloadLink);
        req = new DownloadManager.Request(downloadUri);
        req.setDestinationUri(Uri.parse(mFilePath));
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        try {

            dm = (DownloadManager) getContext().getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(req);
            Toast.makeText(getContext(), name+" is Downloading", Toast.LENGTH_LONG).show();
            dismiss();

        } catch (Exception e) {
            Toast.makeText(getContext(), "Download Failed", Toast.LENGTH_LONG).show();
            dismiss();
        }
    }


    private void writeToFile() {
        // Perform the file writing operation here
        // Make sure to handle storage-related exceptions
    }

}
