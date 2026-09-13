package com.mmschooledu;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdsActivity extends AppCompatActivity {
    private static final int TIMER_DELAY = 8000; // 3 seconds
    Button adsBt;
    GridView gv;
    ArrayList<String> names, links, images;
    MyAdapter adapter;
//    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_main);
        gv = findViewById(R.id.gridview);
       // pd = new ProgressDialog(this);
//        pd.setMessage("Please wait...");
//        adsBt = findViewById(R.id.adsBt);
//        adsBt.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View p1) {
//
//
////                new Admob(new onDismiss() {
////                    @Override
////                    public void onDismiss() {
//                openPlayStore();
//
//            }
//
//            private void openPlayStore() {
//
//
//                // Replace "com.your.package.name" with your app's package name
//                String appPackageName = "com.thaw.skm";
//
//                try {
//                    // Open the app's page on the Play Store
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                } catch (android.content.ActivityNotFoundException e) {
//                    // If the Play Store app is not installed, open the Play Store website
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                }
////                    }
////                }).ShowInterstitial(AdsActivity.this, true);
//            };});


        // Close button to dismiss the ad
        ImageButton closeButton = findViewById(R.id.btnClose);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close AdsActivity
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Finish the activity
                finish();
            }
        }, TIMER_DELAY);



        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(AdsActivity.this, AdsActivity2.class);
//                intent.putExtra("link", links.get(position));
//                startActivity(intent);
              // openPlayStore();
              //  openYouTubeLink(AdsActivity.this);





                String webLink = links.get(position);

                // Create intent to open the web link in a web browser
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(webLink));

                // Verify that there is a web browser available to handle the intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    // Start the intent to open the web link in the default web browser
                    startActivity(intent);
                } else {
                    // Handle the case where no web browser is available
                    Toast.makeText(AdsActivity.this, "No web browser found", Toast.LENGTH_SHORT).show();
                }
            }
        });


//            private void openPlayStore() {
//
//
//                // Replace "com.your.package.name" with your app's package name
//                //String appPackageName = "com.thaw.skm";
//              //  String appPackageName = link;
//
//                try {
//                    // Open the app's page on the Play Store
//                  //  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                } catch (android.content.ActivityNotFoundException e) {
//                    // If the Play Store app is not installed, open the Play Store website
//                 //  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                }
//
//            }
       // });

//            private void openYouTubeLink(Context context) {
//                // Replace "YOUR_YOUTUBE_VIDEO_ID" with the actual video ID or the complete video URL
//                String youtubeLink = "https://youtube.com/@myanmarschooleducation9624?si=h9sOaO7Yl1n7aK6t";
//             //   String youtubeLink = "link;
//
//                Intent openYouTube = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink));
//
//                // Ensure it opens in the YouTube app if available, otherwise open in browser
//                openYouTube.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                try {
//                    startActivity(openYouTube);
//                } catch (ActivityNotFoundException e) {
//                    // If YouTube app is not available, open the YouTube website
//                    Uri websiteUri = Uri.parse(youtubeLink);
//                    Intent openBrowser = new Intent(Intent.ACTION_VIEW, websiteUri);
//                    startActivity(openBrowser);
//                }
//            }
//       // }).ShowInterstitial(SettingsActivity.this, true);
//            });

        refresh();
    }

    private void refresh() {
        // new DownloadTask().execute("http://aidenyinyilwin.blogspot.com/feeds/posts/default?alt=json");

        new DownloadTask().execute("https://myanmarschoolpassward.blogspot.com/feeds/posts/default?alt=json");
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
          //  pd.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            return JSONDownloader.download(urls[0]);
        }

        @Override
        public void onPostExecute(String result) {
           // pd.dismiss();
            processJSON(result);
        }
    }

    private void processJSON(String input) {
        names = new ArrayList<>();
        links = new ArrayList<>();
        images = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(input);
            JSONArray ja = jo.getJSONObject("feed").getJSONArray("entry");
            JSONObject jo2;
            for (int i = 0; i < ja.length(); i++) {
                jo2 = ja.getJSONObject(i);
                if (jo2.getJSONObject("title").getString("$t").equals("Movies1")) {
                    String content = jo2.getJSONObject("content").getString("$t");
                    JSONObject obj = new JSONObject(Html.fromHtml(content).toString());
                    JSONArray jarr = obj.getJSONArray("movies");
                    for (int j = 0; j < jarr.length(); j++) {
                        names.add(jarr.getJSONObject(j).getString("title"));
                        links.add(jarr.getJSONObject(j).getString("link"));
                        images.add(jarr.getJSONObject(j).getString("image") + "");
                    }
                    adapter = new MyAdapter();
                    gv.setAdapter(adapter);
                }
            }
        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int position) {
            return names.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.adsitem_layout, parent, false);
            }
            TextView tv1 = convertView.findViewById(R.id.tv);
            ImageView iv = convertView.findViewById(R.id.iv);

            if (images.get(position).length() > 0) {
                //Glide.with(this).load(Html.fromHtml(images.get(position)).toString()).into(iv);
                Glide
                        .with(getApplicationContext())
                        .load(Html.fromHtml(images.get(position)).toString())
                        .into(iv);
            } else {
                iv.setImageResource(R.drawable.ic_launcher);
            }
            tv1.setText(names.get(position));
            return convertView;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 0, 0, "Refresh").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case 0:
//                refresh();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}









//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bdtopcoder.quickadmob.Admob;
//import com.bdtopcoder.quickadmob.onDismiss;
//
//public class AdsActivity extends AppCompatActivity {
//Button adsBt;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.slide_0);
//        adsBt = findViewById(R.id.adsBt);
//        adsBt.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View p1) {
////                new Admob(new onDismiss() {
////                    @Override
////                    public void onDismiss() {
//                openPlayStore();
//
//            }
//
//            private void openPlayStore() {
//
//
//                // Replace "com.your.package.name" with your app's package name
//                String appPackageName = "com.thaw.skm";
//
//                try {
//                    // Open the app's page on the Play Store
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                } catch (android.content.ActivityNotFoundException e) {
//                    // If the Play Store app is not installed, open the Play Store website
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                }
//                    }
////                }).ShowInterstitial(AdsActivity.this, true);
////            }
//
//            ;
//        });
//    }}
//
