package com.mmschooledu.Cinema;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bdtopcoder.quickadmob.onDismiss;
import com.bumptech.glide.Glide;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.htetznaing.xgetter.Model.XModel;
import com.htetznaing.xgetter.XGetter;
import com.mmschooledu.Item.VideoItem;
import com.mmschooledu.JSONDownloader;
import com.mmschooledu.Player.MyExoPlayer;
import com.mmschooledu.R;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public abstract class MoviesIndexFeedActivity extends AppCompatActivity
{

    private InterstitialAd interstitial;
    private AdView adView;


    XGetter xGetter;
    String org;
    TextView tv,tv1,tv2,tv3,tv4;
    ImageView iv;
    //String id="link";
    String link,sdlink,hdlink,bname,thumbnail,category,wname,mb1,mb2,time;
    ProgressDialog pd,progressDialog;
    private DownloadManager.Request mRequest;
    private String mBaseFolderPath;
    private DownloadManager mDownloadManager;
    private long mDownloadedFileID;
    private Intent intent;




    //public abstract void processRecent(String jsonString, String cateogry);

    //public abstract void processTitleImg(String jsonString);
    public abstract String getFeedAddress();
    public abstract void _Options_Menu_Click(MenuItem item);
    private SwipeRefreshLayout mSwipeLayout;
    RecyclerView recent_rv,suggested_rv,child_rv,young_rv;
    //RecentAdapter recent_adapter;

    //List<RecentItem> r_posts,r_filteredposts;

    boolean online=true;
    Toolbar tb;
    String currentLink="";
    final int FONT_ZAWGYI=1;
    final int FONT_UNI=2;
    final int FONT_NONE=0;
    int currentFont=FONT_NONE;

    //DrawerLayout mDrawerLayout;
    //private ActionBarDrawerToggle mDrawerToggle;
    //private NavigationView navigationView;
    String FACEBOOK_URL = "https://www.facebook.com/%E1%80%A1%E1%80%B1%E1%80%B8%E1%80%9E%E1%80%AC%E1%80%9A%E1%80%AC-%E1%80%85%E1%80%AC%E1%80%80%E1%80%BC%E1%80%8A%E1%80%BA%E1%80%B7%E1%80%90%E1%80%AD%E1%80%AF%E1%80%80%E1%80%BA-102387168438840/";
    String FACEBOOK_PROFILE_ID = "102387168438840";
    Handler handler = new Handler();
    Runnable runnable;
    //   int currentImg=0;
    // int maxImg=4;
    //   ImageView title_img;
    LinearLayout pre,next;
    TextView position;
    Button recent_bt,suggested_bt,child_bt,young_bt,imageview2,imageview3,textview2;
    int bookCount=0;
    int ItemClick=0;
    private FloatingActionButton play_fab;


    // AdView adview0,adview1;//,adview2
    //AdRequest req0,req1;//,req2
    //  InterstitialAd interstitial;
	/*
	 public abstract void processSuggested(String jsonString, String cateogry);
	 SuggestedAdapter suggested_adapter;
	 List<SuggestedItem> s_posts,s_filteredposts;

	 public abstract void processChild(String jsonString, String cateogry);
	 ChildAdapter child_adapter;
	 List<ChildItem> c_posts,c_filteredposts;
	 */
    public abstract void processYoung(String jsonString, String cateogry);
    YoungAdapter young_adapter;
    List<VideoItem> y_posts,y_filteredposts;

    CardView writer_card,category_card,ads_card,book_card;
    String about,developer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.movies_detail_activity);
//        	adView = (AdView)
//        findViewById(R.id.ad_view);
//       AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
/*
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(MoviesIndexFeedActivity.this) {
            @Nullable
            @Override
            public FullScreenContentCallback getFullScreenContentCallback() {
                return null;
            }

            @Nullable
            @Override
            public OnPaidEventListener getOnPaidEventListener() {
                return null;
            }

            @NonNull
            @Override
            public ResponseInfo getResponseInfo() {
                return null;
            }

            @NonNull
            @Override
            public String getAdUnitId() {
                return null;
            }

            @Override
            public void setFullScreenContentCallback(@Nullable FullScreenContentCallback fullScreenContentCallback) {

            }

            @Override
            public void setImmersiveMode(boolean b) {

            }

            @Override
            public void setOnPaidEventListener(@Nullable OnPaidEventListener onPaidEventListener) {

            }

            @Override
            public void show(@NonNull Activity activity) {

            }
        };
        // Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

        interstitial.loadAd(adRequest);
        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });
*/
        tb=(Toolbar)findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        tb.setLogo(R.drawable.apk);
        tb.setTitle("ATy");
        //navigationView = (NavigationView) findViewById(R.id.nnl_navigation_view);

        //  title_img=(ImageView) findViewById(R.id.title_img);
        //pre=(LinearLayout) findViewById(R.id.pre_l);
        //  next=(LinearLayout) findViewById(R.id.next_l);
        //     position=(TextView) findViewById(R.id.position);
        //    recent_bt=(Button) findViewById(R.id.recent_bt);
        //	suggested_bt=(Button) findViewById(R.id.suggested_bt);
        //	child_bt=(Button) findViewById(R.id.child_bt);
        young_bt=(Button) findViewById(R.id.young_bt);
        //  mDrawerLayout = (DrawerLayout) findViewById(R.id.nnl_drawer_layout);
        //  adview0=(AdView) findViewById(R.id.adView_0);
        //  adview1=(AdView) findViewById(R.id.adView_1);
        //adview2=(AdView) findViewById(R.id.adView_2);
		/*   mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
		 mDrawerLayout.setDrawerListener(mDrawerToggle);
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 setupNV();
		 navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
		 @Override
		 public boolean onNavigationItemSelected(MenuItem menuItem)
		 {
		 menuItem.setChecked(true);
		 mDrawerLayout.closeDrawers();
		 _Options_Menu_Click(menuItem);
		 return true;
		 }
		 });
		 */




        requestAppPermissions();
        link = getIntent().getStringExtra("link");
        iv = (ImageView) findViewById(R.id.ivDetail);
        //id = getIntent().getStringExtra("link");
        thumbnail = getIntent().getStringExtra("thumbnail");
        bname = getIntent().getStringExtra("bname");
        wname = getIntent().getStringExtra("wname");
        mb1 = getIntent().getStringExtra("mb1");
        mb2 = getIntent().getStringExtra("mb2");
        time = getIntent().getStringExtra("time");

        //category = getIntent().getStringExtra("category");
        tv = (TextView) findViewById(R.id.tvTitle);
        tv.setText(bname);
        tv1 = (TextView) findViewById(R.id.detail_movie_desc);
        tv1.setText(wname);

        tv2 = (TextView) findViewById(R.id.mb1);
        tv2.setText(mb1);

        tv3 = (TextView) findViewById(R.id.mb2);
        tv3.setText(mb2);

        tv4 = (TextView) findViewById(R.id.time);
        tv4.setText(time);
        Glide.with(this)
                .load(thumbnail)
                .into(iv);
        //tv.setText(bname + "\n" + category);
        tv.setText(bname );
        tv1.setText(wname );
        tv2.setText(mb1 );
        tv3.setText(mb2 );
        tv4.setText(time );

        //tv.setTextColor(Color.rgb(30, 9, 203));
        tv.setTextColor(Color.rgb(225, 248, 250));
        //android:textColor="#E0E0E0"

        //Button imageview3 =  (Button) findViewById(R.id.imageview3);

        //imageview2=(Button) findViewById(R.id.imageview2);
        //imageview3= (Button) findViewById(R.id.imageview3);

        ImageView imageview2=(ImageView) findViewById(R.id.imageview2);
        ImageView imageview3 =(ImageView) findViewById(R.id.imageview3);
        TextView textview2 = (TextView) findViewById(R.id.textview2);


        pd = new ProgressDialog(this);
        pd.setTitle("Please wailt...\n"+"Link ယူနေပါသဖြင် ခဏစောင့်ပါ ");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        mDownloadManager = (DownloadManager) getApplicationContext().getSystemService(DOWNLOAD_SERVICE);
        mBaseFolderPath = android.os.Environment.getExternalStorageDirectory() + File.separator + "MyanmarSchoolEduMovies" + File.separator;
        FloatingActionButton play_fab = (FloatingActionButton) findViewById(R.id.floating_play_btn);



        play_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                XGetter xGetter = new XGetter(getApplicationContext());
                xGetter.onFinish(new XGetter.OnTaskCompleted() {

                    @Override
                    public void onTaskCompleted(ArrayList<XModel> vidURL, boolean multiple_quality) {

                        //progressDialog
                        pd.dismiss();
                        if (multiple_quality) {
                            //This video you can choose qualities
                            for (XModel model : vidURL) {
                                String url = model.getUrl();
                                //If google drive video you need to set cookie for play or download
                                String cookie = model.getCookie();
                            }
                            multipleQualityDialog(vidURL);
                        } else {

                            //If single
                            done(vidURL.get(0));
                        }
                    }

                    @Override
                    public void onError() {
                        //Error
                    }
                });

                //progressDialog
                pd.show();
                //xGetter.find(id);

                xGetter.find(link);
            }





            private void done(final XModel xModel) {
                String url = null;
                if (xModel != null) {
                    url = xModel.getUrl();
                }
                final MaterialStyledDialog.Builder builder = new MaterialStyledDialog.Builder(MoviesIndexFeedActivity.this);
                if (url != null) {
                    final String finalUrl = url;
                    builder.setTitle("Education Movies")
                            .setDescription("Now,you can Play or download.")
                            .setHeaderColor(R.color.colorPrimary)
                            .setStyle(Style.HEADER_WITH_ICON)
                            .setIcon(R.drawable.right)
                            .withDialogAnimation(true)
                            .setPositiveText("Play")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    Intent intent = new Intent(getApplicationContext(), MyExoPlayer.class);
                                    intent.putExtra("url", finalUrl);
                                    //If google drive you need to put cookie
                                    if (xModel.getCookie() != null) {
                                        intent.putExtra("cookie", xModel.getCookie());
                                    }
                                    startActivity(intent);
//                                    StartAppAd.showAd(getBaseContext());
                                }
                            })

                            .setNegativeText("Download")
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
//                                @Override
//                                public void onClick(@android.support.annotation.NonNull @NonNull MaterialDialog dialog, @android.support.annotation.NonNull @NonNull DialogAction which) {
//
//                                }

                                @SuppressLint("WrongConstant")
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                    try {


                                        //String fileName =bname + ".mp4";
                                        String file =bname + ".mp4";

                                        if (!new File(mBaseFolderPath).exists()) {
                                            new File(mBaseFolderPath).mkdir();
                                        }
                                        //	String mFilePath = "file://" + mBaseFolderPath + fileName;
                                        //String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

                                        String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                                + File.separator
                                                + "MyanmarSchoolEducationVideo" + File.separator;



                                        Uri downloadUri = Uri.parse(xModel.getUrl());
                                        mRequest = new DownloadManager.Request(downloadUri);
                                        //	mRequest.setDestinationUri(Uri.parse(mFilePath));

                                        //If google drive you need to set cookie
                                        if (xModel.getCookie() != null) {
                                            mRequest.addRequestHeader("cookie", xModel.getCookie());
                                        }

                                        mRequest.setMimeType("video/*");
                                        mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                        mDownloadedFileID = mDownloadManager.enqueue(mRequest);
                                        IntentFilter downloaded = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                                        Toast.makeText(getApplicationContext(), "Starting Download : " + file, Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_VIEW);
                                        try {
                                            intent.setDataAndType(Uri.parse(URLDecoder.decode(xModel.getUrl(), "UTF-8")), "video/mp4");
                                            getApplicationContext().startActivity(Intent.createChooser(intent, "Download with..."));
                                        } catch (UnsupportedEncodingException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }

                            });

                } else {
                    builder.setTitle("Sorry!")
                            .setDescription("Error")
                            .setHeaderColor(R.color.colorPrimary)
                            .setStyle(Style.HEADER_WITH_ICON)
                            .setIcon(R.drawable.wrong)
                            .withDialogAnimation(true)
                            .setPositiveText("OK")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            });
                }
                MaterialStyledDialog dialog = builder.build();
                dialog.show();
            }

            private void multipleQualityDialog(final ArrayList<XModel> model) {
                CharSequence[] name = new CharSequence[model.size()];

                for (int i = 0; i < model.size(); i++) {
                    name[i] = model.get(i).getQuality();

                }


                AlertDialog.Builder builder = new AlertDialog.Builder(MoviesIndexFeedActivity.this)
                        .setTitle("Please Choose Quality!")
                        .setItems(name, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                done(model.get(which));
                            }
                        })
                        .setPositiveButton("Cancel", null);
                builder.show();
            }
        }	);

        textview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                XGetter xGetter = new XGetter(getApplicationContext());
                xGetter.onFinish(new XGetter.OnTaskCompleted() {

                    @Override
                    public void onTaskCompleted(ArrayList<XModel> vidURL, boolean multiple_quality) {

                        //progressDialog
                        pd.dismiss();
                        if (multiple_quality) {
                            //This video you can choose qualities
                            for (XModel model : vidURL) {
                                String url = model.getUrl();
                                //If google drive video you need to set cookie for play or download
                                String cookie = model.getCookie();
                            }
                            multipleQualityDialog(vidURL);
                        } else {

                            //If single
                            done(vidURL.get(0));
                        }
                    }


                    private void done(final XModel xModel) {
                        String url = null;
                        if (xModel != null) {
                            url = xModel.getUrl();
                        }
                        final MaterialStyledDialog.Builder builder = new MaterialStyledDialog.Builder(MoviesIndexFeedActivity.this);
                        if (url != null) {
                            final String finalUrl = url;
                            builder.setTitle("Education Movies")
                                    .setDescription("Now,you can Play.")
                                    .setHeaderColor(R.color.colorPrimary)
                                    .setStyle(Style.HEADER_WITH_ICON)
                                    .setIcon(R.drawable.right)
                                    .withDialogAnimation(true)
                                    .setNegativeText("Play")
                                    //.setPositiveText("Play")
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            Intent intent = new Intent(getApplicationContext(), MyExoPlayer.class);
                                            intent.putExtra("url", finalUrl);
                                            //If google drive you need to put cookie
                                            if (xModel.getCookie() != null) {
                                                intent.putExtra("cookie", xModel.getCookie());
                                            }
                                            startActivity(intent);
                                        }
                                    })

                                    //		.setNegativeText("Download")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @SuppressLint("WrongConstant")
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                            try {


                                                //String fileName =bname + ".mp4";
                                                String file =bname + ".mp4";
                                                // String mFilePath = "file://" + mBaseFolderPath + "/" + bname + ".mp4" ;

                                                if (!new File(mBaseFolderPath).exists()) {
                                                    new File(mBaseFolderPath).mkdir();
                                                }
                                                //	String mFilePath = "file://" + mBaseFolderPath + fileName;
                                                //String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

                                                String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                                        + File.separator
                                                        + "MyanmarSchoolEducationVideo" + File.separator;



                                                Uri downloadUri = Uri.parse(xModel.getUrl());
                                                mRequest = new DownloadManager.Request(downloadUri);
                                                //	mRequest.setDestinationUri(Uri.parse(mFilePath));

                                                //If google drive you need to set cookie
                                                if (xModel.getCookie() != null) {
                                                    mRequest.addRequestHeader("cookie", xModel.getCookie());
                                                }

                                                mRequest.setMimeType("video/*");
                                                mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                                mDownloadedFileID = mDownloadManager.enqueue(mRequest);
                                                IntentFilter downloaded = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                                                Toast.makeText(getApplicationContext(), "Starting Download : " + file, Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                Intent intent = new Intent();
                                                intent.setAction(Intent.ACTION_VIEW);
                                                try {
                                                    intent.setDataAndType(Uri.parse(URLDecoder.decode(xModel.getUrl(), "UTF-8")), "video/mp4");
                                                    getApplicationContext().startActivity(Intent.createChooser(intent, "Download with..."));
                                                } catch (UnsupportedEncodingException e1) {
                                                    e1.printStackTrace();
                                                }
                                            }
                                        }

                                    });

                        } else {
                            builder.setTitle("Sorry!")
                                    .setDescription("Error")
                                    .setHeaderColor(R.color.colorPrimary)
                                    .setStyle(Style.HEADER_WITH_ICON)
                                    .setIcon(R.drawable.wrong)
                                    .withDialogAnimation(true)
                                    .setPositiveText("OK")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                        }
                                    });
                        }
                        MaterialStyledDialog dialog = builder.build();
                        dialog.show();
                    }

                    private void multipleQualityDialog(final ArrayList<XModel> model) {
                        CharSequence[] name = new CharSequence[model.size()];

                        for (int i = 0; i < model.size(); i++) {
                            name[i] = model.get(i).getQuality();

                        }


                        AlertDialog.Builder builder = new AlertDialog.Builder(MoviesIndexFeedActivity.this)
                                .setTitle("Please Choose Quality!")
                                .setItems(name, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        done(model.get(which));
                                    }
                                })
                                .setPositiveButton("Cancel", null);
                        builder.show();
                    }




							/*private void requestAppPermissions() {
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

							 */

                    @Override
                    public void onError() {
                        //Error
                    }
                });

                //progressDialog
                pd.show();
                xGetter.find(link);


            }


        });



        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                XGetter xGetter = new XGetter(getApplicationContext());
                xGetter.onFinish(new XGetter.OnTaskCompleted() {

                    @Override
                    public void onTaskCompleted(ArrayList<XModel> vidURL, boolean multiple_quality) {

                        //progressDialog
                        pd.dismiss();
                        if (multiple_quality) {
                            //This video you can choose qualities
                            for (XModel model : vidURL) {
                                String url = model.getUrl();
                                //If google drive video you need to set cookie for play or download
                                String cookie = model.getCookie();
                            }
                            multipleQualityDialog(vidURL);
                        } else {

                            //If single
                            done(vidURL.get(0));
                        }
                    }

                    //private void multipleQualityDialog(ArrayList<XModel> vidURL) {
                    //}


                    private void done(final XModel xModel) {
                        String url = null;
                        if (xModel != null) {
                            url = xModel.getUrl();
                        }
                        final MaterialStyledDialog.Builder builder = new MaterialStyledDialog.Builder(MoviesIndexFeedActivity.this);
                        if (url != null) {
                            final String finalUrl = url;
                            builder.setTitle("Education Movies")
                                    .setDescription("Now,you can Play.")
                                    .setHeaderColor(R.color.colorPrimary)
                                    .setStyle(Style.HEADER_WITH_ICON)
                                    .setIcon(R.drawable.right)
                                    .withDialogAnimation(true)
                                    .setNegativeText("Play")
                                    //.setPositiveText("Play")
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            Intent intent = new Intent(getApplicationContext(), MyExoPlayer.class);
                                            intent.putExtra("url", finalUrl);
                                            //If google drive you need to put cookie
                                            if (xModel.getCookie() != null) {
                                                intent.putExtra("cookie", xModel.getCookie());
                                            }
                                            startActivity(intent);
                                        }
                                    })

                                    //		.setNegativeText("Download")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @SuppressLint("WrongConstant")
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                            try {


                                                //String fileName =bname + ".mp4";
                                                String mFilePath = "file://" + mBaseFolderPath + "/" + bname + ".mp4" ;
                                                // String file =bname + ".mp4";

                                                if (!new File(mBaseFolderPath).exists()) {
                                                    new File(mBaseFolderPath).mkdir();
                                                }
                                                //	String mFilePath = "file://" + mBaseFolderPath + fileName;

                                                String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

                                                        + File.separator
                                                        + "MyanmarSchoolEducationVideo" + File.separator;



                                                Uri downloadUri = Uri.parse(xModel.getUrl());
                                                mRequest = new DownloadManager.Request(downloadUri);
                                                //	mRequest.setDestinationUri(Uri.parse(mFilePath));
                                                mRequest.setDestinationUri(Uri.parse(mFilePath));
                                                //If google drive you need to set cookie
                                                if (xModel.getCookie() != null) {
                                                    mRequest.addRequestHeader("cookie", xModel.getCookie());
                                                }

                                                mRequest.setMimeType("video/*");
                                                mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                                mDownloadedFileID = mDownloadManager.enqueue(mRequest);
                                                IntentFilter downloaded = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                                                Toast.makeText(getApplicationContext(), "Starting Download : " + mFilePath, Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                Intent intent = new Intent();
                                                intent.setAction(Intent.ACTION_VIEW);
                                                try {
                                                    intent.setDataAndType(Uri.parse(URLDecoder.decode(xModel.getUrl(), "UTF-8")), "video/mp4");
                                                    getApplicationContext().startActivity(Intent.createChooser(intent, "Download with..."));
                                                } catch (UnsupportedEncodingException e1) {
                                                    e1.printStackTrace();
                                                }
                                            }
                                        }

                                    });

                        } else {
                            builder.setTitle("Sorry!")
                                    .setDescription("Error")
                                    .setHeaderColor(R.color.colorPrimary)
                                    .setStyle(Style.HEADER_WITH_ICON)
                                    .setIcon(R.drawable.wrong)
                                    .withDialogAnimation(true)
                                    .setPositiveText("OK")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                        }
                                    });
                        }
                        MaterialStyledDialog dialog = builder.build();
                        dialog.show();
                    }

                    private void multipleQualityDialog(final ArrayList<XModel> model) {
                        CharSequence[] name = new CharSequence[model.size()];

                        for (int i = 0; i < model.size(); i++) {
                            name[i] = model.get(i).getQuality();

                        }


                        AlertDialog.Builder builder = new AlertDialog.Builder(MoviesIndexFeedActivity.this)
                                .setTitle("Please Choose Quality!")
                                .setItems(name, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        done(model.get(which));
                                    }
                                })
                                .setPositiveButton("Cancel", null);
                        builder.show();
                    }




                    @Override
                    public void onError() {
                        //Error
                    }
                });

                //progressDialog
                pd.show();
                xGetter.find(link);


            }


        });



        imageview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                XGetter xGetter = new XGetter(getApplicationContext());
                xGetter.onFinish(new XGetter.OnTaskCompleted() {

                    @Override
                    public void onTaskCompleted(ArrayList<XModel> vidURL, boolean multiple_quality) {

                        //progressDialog
                        pd.dismiss();
                        if (multiple_quality) {
                            //This video you can choose qualities
                            for (XModel model : vidURL) {
                                String url = model.getUrl();
                                //If google drive video you need to set cookie for play or download
                                String cookie = model.getCookie();
                            }
                            multipleQualityDialog(vidURL);
                        } else {

                            //If single
                            done(vidURL.get(0));
                        }
                    }


                    private void done(final XModel xModel) {
                        String url = null;
                        if (xModel != null) {
                            url = xModel.getUrl();
                        }
                        final MaterialStyledDialog.Builder builder = new MaterialStyledDialog.Builder(MoviesIndexFeedActivity.this);
                        if (url != null) {
//                            final String finalUrl = url;
                            final String finalUrl = link;
                            builder.setTitle("Education Movies")
                                    .setDescription("Now,you can  download.")
                                    .setHeaderColor(R.color.colorPrimary)
                                    .setStyle(Style.HEADER_WITH_ICON)
                                    .setIcon(R.drawable.right)
                                    .withDialogAnimation(true)
                                    /*.setPositiveText("Play")
                                     .onPositive(new MaterialDialog.SingleButtonCallback() {
                                     @Override
                                     public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                     Intent intent = new Intent(getApplicationContext(), XPlayer.class);
                                     intent.putExtra("url", finalUrl);
                                     //If google drive you need to put cookie
                                     if (xModel.getCookie() != null) {
                                     intent.putExtra("cookie", xModel.getCookie());
                                     }
                                     startActivity(intent);

                                     }
                                     })	*/

                                    //.setNegativeText("Download")
                                    .setPositiveText("Download")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @SuppressLint("WrongConstant")
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                            try {


                                                //String fileName =bname + ".mp4";



                                                //String fileName =bname + ".mp4";
                                                String file =bname + ".mp4";

                                                if (!new File(mBaseFolderPath).exists()) {
                                                    new File(mBaseFolderPath).mkdir();
                                                }
                                                //	String mFilePath = "file://" + mBaseFolderPath + fileName;
                                                //String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

                                                String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                                        + File.separator
                                                        + "MyanmarSchoolEducationVideo" + File.separator;



                                                Uri downloadUri = Uri.parse(xModel.getUrl());
                                                mRequest = new DownloadManager.Request(downloadUri);
                                                //	mRequest.setDestinationUri(Uri.parse(mFilePath));

                                                //If google drive you need to set cookie
                                                if (xModel.getCookie() != null) {
                                                    mRequest.addRequestHeader("cookie", xModel.getCookie());
                                                }

                                                mRequest.setMimeType("video/*");
                                                mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                                mDownloadedFileID = mDownloadManager.enqueue(mRequest);
                                                IntentFilter downloaded = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                                                Toast.makeText(getApplicationContext(), "Starting Download : " + file, Toast.LENGTH_SHORT).show();
                                            } catch (Exception e) {
                                                Intent intent = new Intent();
                                                intent.setAction(Intent.ACTION_VIEW);
                                                try {
                                                    intent.setDataAndType(Uri.parse(URLDecoder.decode(xModel.getUrl(), "UTF-8")), "video/mp4");
                                                    getApplicationContext().startActivity(Intent.createChooser(intent, "Download with..."));
                                                } catch (UnsupportedEncodingException e1) {
                                                    e1.printStackTrace();
                                                }
                                            }
                                        }

                                    });

                        } else {
                            builder.setTitle("Sorry!")
                                    .setDescription("Error")
                                    .setHeaderColor(R.color.colorPrimary)
                                    .setStyle(Style.HEADER_WITH_ICON)
                                    .setIcon(R.drawable.wrong)
                                    .withDialogAnimation(true)
                                    .setPositiveText("OK")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                        }
                                    });
                        }
                        MaterialStyledDialog dialog = builder.build();
                        dialog.show();
                    }

                    private void multipleQualityDialog(final ArrayList<XModel> model) {
                        CharSequence[] name = new CharSequence[model.size()];

                        for (int i = 0; i < model.size(); i++) {
                            name[i] = model.get(i).getQuality();

                        }


                        AlertDialog.Builder builder = new AlertDialog.Builder(MoviesIndexFeedActivity.this)
                                .setTitle("Please Choose Quality!")
                                .setItems(name, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        done(model.get(which));
                                    }
                                })
                                .setPositiveButton("Cancel", null);
                        builder.show();
                    }





                    @Override
                    public void onError() {
                        //Error
                    }
                });

                //progressDialog
                pd.show();
                xGetter.find(link);


            }


        });









        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isOnline())
                {
                    refresh();
                }
                else{
                    mSwipeLayout.setRefreshing(false);
                    Toast.makeText(getApplicationContext(),"အင်တာနက်ဆက်သွယ်မှု မရှိပါ",Toast.LENGTH_SHORT).show();
                    showNoInternet();
                }
            } });

        mSwipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        //r_posts=new ArrayList<RecentItem>();
        //r_filteredposts=new ArrayList<RecentItem>();
        //s_posts=new ArrayList<SuggestedItem>();
        //    s_filteredposts=new ArrayList<SuggestedItem>();

        //   c_posts=new ArrayList<ChildItem>();
        //    c_filteredposts=new ArrayList<ChildItem>();
        y_posts=new ArrayList<VideoItem>();
        y_filteredposts=new ArrayList<VideoItem>();

        //	recent_rv=(RecyclerView)findViewById(R.id.recent_rv);
        //   suggested_rv=(RecyclerView)findViewById(R.id.suggested_rv);
        //child_rv=(RecyclerView)findViewById(R.id.child_rv);
        young_rv=(RecyclerView)findViewById(R.id.young_rv);

        //writer_card=(CardView)findViewById(R.id.person_card);
        // category_card=(CardView)findViewById(R.id.category_card);
        //book_card=(CardView)findViewById(R.id.all_card);
        //ads_card=(CardView)findViewById(R.id.ads_card);

        //    horizontalRV(recent_rv);
        //     horizontalRV(suggested_rv);
        //     horizontalRV(child_rv);
        horizontalRV(young_rv);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        currentFont = sharedPreferences.getInt("font_main", 0);
        refresh();


    }

    private void requestAppPermissions() {

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this, new String[] {
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




    void horizontalRV(RecyclerView rv){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(layoutManager);
        ViewCompat.setNestedScrollingEnabled(rv, false);
    }
	/*private void setupNV()
	 {
	 navigationView.getMenu().clear();
	 navigationView.inflateMenu(R.menu.navigation_menu);
	 }

	 protected void onPostCreate(Bundle savedInstanceState)
	 {
	 super.onPostCreate(savedInstanceState);
	 mDrawerToggle.syncState();
	 }

	 @Override
	 public void onConfigurationChanged(Configuration newConfig)
	 {
	 super.onConfigurationChanged(newConfig);
	 mDrawerToggle.onConfigurationChanged(newConfig);
	 }*/

	/*void loadBanner()  {


	 req0= new AdRequest.Builder().build();
	 adview0.loadAd(req0);
	 req1= new AdRequest.Builder().build();
	 adview1.loadAd(req1);
	 //req2= new AdRequest.Builder().build();
	 //adview2.loadAd(req2);
	 }
	 */
	/*@Override
	 protected void onStart()
	 {
	 super.onStart();
	 CountDownTimer cdt=new CountDownTimer(2000, 1000) {
	 public void onTick(long p1)
	 {
	 //question.startAnimation(AnimationUtils.loadAnimation(IntroActivity.this, R.anim.scrollview_out));
	 }
	 public void onFinish()
	 {
	 loadBanner();

	 }
	 }.start();
	 }*/

	/*@Override
	 public void onPause() {
	 if (adview0 != null) {
	 adview0.pause();
	 }
	 if (adview1 != null) {
	 adview1.pause();
	 }
	 //if (adview2 != null) {
	 // adview2.pause();
	 //   }
	 super.onPause();
	 }

	 @Override
	 public void onResume() {
	 super.onResume();
	 if (adview0!= null) {
	 adview0.resume();
	 }
	 if (adview1!= null) {
	 adview1.resume();
	 }
	 //  if (adview2!= null) {
	 //    adview2.resume();
	 //  }
	 }

	 @Override
	 public void onDestroy() {
	 if (adview0 != null) {
	 adview0.destroy();
	 }
	 if (adview1 != null) {
	 adview1.destroy();
	 }
	 //  if (adview2 != null) {
	 //    adview2.destroy();
	 //  }
	 super.onDestroy();
	 } */



    public void refresh(){
        currentLink=getFeedAddress();
        tb.collapseActionView();
        if(isOnline()){
            online=true;
            try{
                new DownloadTask().execute(currentLink);
            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Can't be used Data!",Toast.LENGTH_SHORT).show();
            }
        }else{
            online=false;

            showNoInternet();
        }
    }




    public void saveToPrefs(String result){
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(currentLink,result);
        editor.apply();
    }

    public String getFromPrefs(String key){

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        return sharedPreferences.getString(currentLink,"");
    }

    private class DownloadTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... p1)
        {
            return JSONDownloader.download(p1[0]);
        }








        @Override
        public void onPostExecute(String result) {
            try{


                saveToPrefs(result);
                mSwipeLayout.setRefreshing(false);

                processYoung(result,"edu");
                young_adapter=new YoungAdapter();
                young_rv.setAdapter(young_adapter);



                young_bt.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1) {
                        //goBookActivity("လူငယ်များအတွက်","young");

                        goBookActivity("Survey Training Video)", "edu");

                    }
                });


            }catch(Exception ignored){

            }



        }
    }
    public void goBookActivity(String title,String category){

        new com.bdtopcoder.quickadmob.Admob(new onDismiss() {
            @Override
            public void onDismiss() {


        Intent i=new Intent(MoviesIndexFeedActivity.this,CinemaLessonActivityCategories.class);

        i.putExtra("thumbnail",thumbnail);
        i.putExtra("bname",bname);
        i.putExtra("title",title);
        i.putExtra("wname",wname);
        i.putExtra("link",link);
        i.putExtra("category",category);
        i.putExtra("mb1",mb1);
        i.putExtra("mb2",mb2);
        i.putExtra("time",time);

        startActivity(i);

//        StartAppAd.showAd(getBaseContext());


    }

    }).ShowInterstitial(MoviesIndexFeedActivity.this, true);
}


	/*
	 void showGlide(ImageView iv,String src){
	 Glide
	 .with(getApplicationContext())
	 .load(src).placeholder(R.drawable.ic_launcher)
	 .into(iv);
	 }*/

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


	/*

	 private void done(final XModel xModel) {
	 String url = null;
	 if (xModel != null) {
	 url = xModel.getUrl();
	 }
	 final MaterialStyledDialog.Builder builder = new MaterialStyledDialog.Builder(IndexFeedActivity2.this);
	 if (url != null) {
	 final String finalUrl = url;
	 builder.setTitle("Cinema")
	 .setDescription("Now,you can Play or download.")
	 .setStyle(Style.HEADER_WITH_ICON)
	 .setIcon(R.drawable.right)
	 .withDialogAnimation(true)
	 .setPositiveText("Play")
	 .onPositive(new MaterialDialog.SingleButtonCallback() {
	 @Override
	 public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
	 Intent intent = new Intent(getApplicationContext(), XPlayer.class);
	 intent.putExtra("link", finalUrl);
	 //If google drive you need to put cookie
	 if (xModel.getCookie() != null) {
	 intent.putExtra("cookie", xModel.getCookie());
	 }
	 startActivity(intent);

	 }
	 })

	 .setNegativeText("Download")
	 .onNegative(new MaterialDialog.SingleButtonCallback() {
	 @SuppressLint("WrongConstant")
	 @Override
	 public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


	 try {


	 String fileName =bname + ".mp4";

	 if (!new File(mBaseFolderPath).exists()) {
	 new File(mBaseFolderPath).mkdir();
	 }
	 String mFilePath = "file://" + mBaseFolderPath + fileName;
	 Uri downloadUri = Uri.parse(xModel.getUrl());
	 mRequest = new DownloadManager.Request(downloadUri);
	 mRequest.setDestinationUri(Uri.parse(mFilePath));

	 //If google drive you need to set cookie
	 if (xModel.getCookie() != null) {
	 mRequest.addRequestHeader("cookie", xModel.getCookie());
	 }

	 mRequest.setMimeType("video/*");
	 mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
	 mDownloadedFileID = mDownloadManager.enqueue(mRequest);
	 IntentFilter downloaded = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
	 Toast.makeText(getApplicationContext(), "Starting Download : " + fileName, Toast.LENGTH_SHORT).show();
	 } catch (Exception e) {
	 Intent intent = new Intent();
	 intent.setAction(Intent.ACTION_VIEW);
	 try {
	 intent.setDataAndType(Uri.parse(URLDecoder.decode(xModel.getUrl(), "UTF-8")), "video/mp4");
	 getApplicationContext().startActivity(Intent.createChooser(intent, "Download with..."));
	 } catch (UnsupportedEncodingException e1) {
	 e1.printStackTrace();
	 }
	 }
	 }

	 });

	 } else {
	 builder.setTitle("Sorry!")
	 .setDescription("Error")
	 .setStyle(Style.HEADER_WITH_ICON)
	 .setIcon(R.drawable.wrong)
	 .withDialogAnimation(true)
	 .setPositiveText("OK")
	 .onPositive(new MaterialDialog.SingleButtonCallback() {
	 @Override
	 public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
	 dialog.dismiss();
	 }
	 });
	 }
	 MaterialStyledDialog dialog = builder.build();
	 dialog.show();
	 }

	 private void multipleQualityDialog(final ArrayList<XModel> model) {
	 CharSequence[] name = new CharSequence[model.size()];

	 for (int i = 0; i < model.size(); i++) {
	 name[i] = model.get(i).getQuality();

	 }


	 AlertDialog.Builder builder = new AlertDialog.Builder(IndexFeedActivity2.this)
	 .setTitle("Please Choose Quality!")
	 .setItems(name, new DialogInterface.OnClickListener() {
	 @Override
	 public void onClick(DialogInterface dialog, int which) {
	 done(model.get(which));
	 }
	 })
	 .setPositiveButton("Cancel", null);
	 builder.show();
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


	 */




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id==android.R.id.home){
            //mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        if(item.getItemId()==R.id.menu_font){
            changeFont();
            refresh();
        }
        else{
            _Options_Menu_Click(item);
        }
        return super.onOptionsItemSelected(item);
    }

	/*public void addItem(RecentItem item){
	 r_posts.add(item);
	 }

	 public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder>
	 {
	 @Override
	 public RecentAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	 {
	 View v=getLayoutInflater().inflate(R.layout.image_item,p1,false);
	 return new ViewHolder(v);
	 }

	 public void filter(String charText){
	 charText =charText.toLowerCase();
	 r_filteredposts.clear();
	 if (charText.length()==0){
	 r_filteredposts.addAll(r_posts);
	 }else{
	 for (RecentItem pi : r_posts){
	 if(pi.bname.toLowerCase().contains(charText))
	 {
	 r_filteredposts.add(pi);
	 }
	 }
	 }
	 notifyDataSetChanged();
	 }

	 @Override
	 public int getItemCount()
	 {
	 bookCount=r_filteredposts.size();
	 if(bookCount>10){
	 bookCount=10;
	 }else{
	 bookCount=r_filteredposts.size();
	 }
	 return bookCount;
	 }

	 @Override
	 public void onBindViewHolder(RecentAdapter.ViewHolder p1,final int p2)
	 {
	 if((r_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
	 Glide
	 .with(getApplicationContext())
	 .load(r_filteredposts.get(p2).thumbnail).placeholder(R.drawable.loading_book)
	 .into(p1.iv);

	 }else{
	 p1.iv.setImageResource(R.drawable.loading_book);
	 }
	 p1.card.setOnClickListener(new OnClickListener(){

	 @Override
	 public void onClick(View p1) {
	 gotoBDActivity(r_filteredposts.get(p2).thumbnail,r_filteredposts.get(p2).bname,r_filteredposts.get(p2).wname,r_filteredposts.get(p2).res,r_filteredposts.get(p2).category);

	 }
	 });
	 }


	 public RecentItem getItem(int pos){
	 return r_filteredposts.get(pos);
	 }

	 public void reset()
	 {
	 r_posts.clear();
	 r_filteredposts.clear();
	 notifyDataSetChanged();
	 }

	 public RecentAdapter()
	 {
	 super();
	 r_filteredposts=new ArrayList<RecentItem>();
	 r_filteredposts.addAll(r_posts);
	 }

	 public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	 {
	 ImageView iv;
	 CardView card;

	 @Override
	 public void onClick(View view)
	 {


	 }
	 public ViewHolder(View view)
	 {
	 super(view);
	 iv=view.findViewById(R.id.image);
	 card=view.findViewById(R.id.card);
	 view.setOnClickListener(this);
	 }
	 }
	 }
	 */

	/*
	 //for suggested
	 public void addItem(SuggestedItem item){
	 s_posts.add(item);
	 }
	 public class SuggestedAdapter extends RecyclerView.Adapter<SuggestedAdapter.ViewHolder>
	 {
	 @Override
	 public SuggestedAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	 {
	 View v=getLayoutInflater().inflate(R.layout.image_item,p1,false);
	 return new ViewHolder(v);
	 }

	 public void filter(String charText){
	 charText =charText.toLowerCase();
	 s_filteredposts.clear();
	 if (charText.length()==0){
	 s_filteredposts.addAll(s_posts);
	 }else{
	 for (SuggestedItem pi : s_posts){
	 if(pi.bname.toLowerCase().contains(charText))
	 {
	 s_filteredposts.add(pi);
	 }
	 }
	 }
	 notifyDataSetChanged();
	 }

	 @Override
	 public int getItemCount()
	 {
	 bookCount=s_filteredposts.size();
	 if(bookCount>10){
	 bookCount=10;
	 }else{
	 bookCount=s_filteredposts.size();
	 }
	 return bookCount;
	 }

	 @Override
	 public void onBindViewHolder(SuggestedAdapter.ViewHolder p1, final int p2)
	 {
	 if((s_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
	 Glide
	 .with(getApplicationContext())
	 .load(s_filteredposts.get(p2).thumbnail).placeholder(R.drawable.loading_book)
	 .into(p1.iv);

	 }else{
	 p1.iv.setImageResource(R.drawable.loading_book);
	 }
	 p1.card.setOnClickListener(new OnClickListener(){

	 @Override
	 public void onClick(View p1) {
	 gotoBDActivity(s_filteredposts.get(p2).thumbnail,s_filteredposts.get(p2).bname,s_filteredposts.get(p2).wname,s_filteredposts.get(p2).res,s_filteredposts.get(p2).category);

	 }
	 });
	 }

	 public SuggestedItem getItem(int pos){
	 return s_filteredposts.get(pos);
	 }

	 public void reset()
	 {
	 s_posts.clear();
	 s_filteredposts.clear();
	 notifyDataSetChanged();
	 }

	 public SuggestedAdapter()
	 {
	 super();
	 s_filteredposts=new ArrayList<SuggestedItem>();
	 s_filteredposts.addAll(s_posts);
	 }

	 public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	 {
	 ImageView iv;
	 CardView card;

	 @Override
	 public void onClick(View view)
	 {



	 }
	 public ViewHolder(View view)
	 {
	 super(view);
	 iv=view.findViewById(R.id.image);
	 card=view.findViewById(R.id.card);
	 view.setOnClickListener(this);
	 }
	 }
	 }

	 //for child
	 public void addItem(ChildItem item){
	 c_posts.add(item);
	 }
	 public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder>
	 {
	 @Override
	 public ChildAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	 {
	 View v=getLayoutInflater().inflate(R.layout.image_item,p1,false);
	 return new ViewHolder(v);
	 }

	 public void filter(String charText){
	 charText =charText.toLowerCase();
	 c_filteredposts.clear();
	 if (charText.length()==0){
	 c_filteredposts.addAll(c_posts);
	 }else{
	 for (ChildItem pi : c_posts){
	 if(pi.bname.toLowerCase().contains(charText))
	 {
	 c_filteredposts.add(pi);
	 }
	 }
	 }
	 notifyDataSetChanged();
	 }

	 @Override
	 public int getItemCount()
	 {

	 bookCount=c_filteredposts.size();
	 if(bookCount>10){
	 bookCount=10;
	 }else{
	 bookCount=c_filteredposts.size();
	 }
	 return bookCount;
	 }

	 @Override
	 public void onBindViewHolder(ChildAdapter.ViewHolder p1, final int p2)
	 {
	 if((c_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
	 Glide
	 .with(getApplicationContext())
	 .load(c_filteredposts.get(p2).thumbnail).placeholder(R.drawable.loading_book)
	 .into(p1.iv);

	 }else{
	 p1.iv.setImageResource(R.drawable.loading_book);
	 }
	 p1.card.setOnClickListener(new OnClickListener(){

	 @Override
	 public void onClick(View p1) {
	 gotoBDActivity(c_filteredposts.get(p2).thumbnail,c_filteredposts.get(p2).bname,c_filteredposts.get(p2).wname,c_filteredposts.get(p2).res,c_filteredposts.get(p2).category);

	 }
	 });
	 }

	 public ChildItem getItem(int pos){
	 return c_filteredposts.get(pos);
	 }

	 public void reset()
	 {
	 c_posts.clear();
	 c_filteredposts.clear();
	 notifyDataSetChanged();
	 }

	 public ChildAdapter()
	 {
	 super();
	 c_filteredposts=new ArrayList<ChildItem>();
	 c_filteredposts.addAll(c_posts);
	 }

	 public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	 {
	 ImageView iv;
	 CardView card;

	 @Override
	 public void onClick(View view)
	 {



	 }
	 public ViewHolder(View view)
	 {
	 super(view);
	 iv=view.findViewById(R.id.image);
	 card=view.findViewById(R.id.card);
	 view.setOnClickListener(this);
	 }
	 }
	 }*/
    //for young




    public void addItem(VideoItem item){
        y_posts.add(item);
    }
    public class YoungAdapter extends RecyclerView.Adapter<YoungAdapter.ViewHolder>
    {
        @Override
        public YoungAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
        {
            View v=getLayoutInflater().inflate(R.layout.image_item,p1,false);
            return new ViewHolder(v);
        }

        public void filter(String charText){
            charText =charText.toLowerCase();
            y_filteredposts.clear();
            if (charText.length()==0){
                y_filteredposts.addAll(y_posts);
            }else{
                for (VideoItem  pi : y_posts){
                    if(pi.bname.toLowerCase().contains(charText))
                    {
                        y_filteredposts.add(pi);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount()
        {

            bookCount=y_filteredposts.size();
            if(bookCount>10){
                bookCount=10;
            }else{
                y_filteredposts.size();
            }
            return bookCount;
        }

        @Override
        public void onBindViewHolder(@NonNull YoungAdapter.ViewHolder p1, final int p2)
        {
            if((y_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
                Glide
                        .with(getApplicationContext())
                        .load(y_filteredposts.get(p2).thumbnail).placeholder(R.drawable.loading_book)
                        .into(p1.iv);

            }else{

                p1.iv.setImageResource(R.drawable.loading_book);
            }
            p1.bname.setText(y_filteredposts.get(p2).bname);


            p1.card.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1) {

                    gotoBDActivity(y_filteredposts.get(p2).thumbnail,y_filteredposts.get(p2).bname,y_filteredposts.get(p2).wname,y_filteredposts.get(p2).link,y_filteredposts.get(p2).category);
                }
            });
        }

        public VideoItem getItem(int pos){
            return y_filteredposts.get(pos);
        }

        public void reset()
        {
            y_posts.clear();
            y_filteredposts.clear();
            notifyDataSetChanged();
        }

        public YoungAdapter()
        {
            super();
            y_filteredposts=new ArrayList<VideoItem>();
            y_filteredposts.addAll(y_posts);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            ImageView iv;
            CardView card;
            TextView bname;
            @Override
            public void onClick(View view)
            {



            }
            public ViewHolder(View view)
            {
                super(view);
                iv=view.findViewById(R.id.image);
                card=view.findViewById(R.id.card);
                bname=view.findViewById(R.id.bname);

                view.setOnClickListener(this);
            }
        }
    }
    void gotoBDActivity(String thumbnail,String bname,String wname,String link,String category ){
        if(isOnline()){
            online=true;


            new com.bdtopcoder.quickadmob.Admob(new onDismiss() {
                @Override
                public void onDismiss() {
            Intent i=new Intent(MoviesIndexFeedActivity.this,MoviesDetailActivity.class);
            i.putExtra("thumbnail",thumbnail);
            i.putExtra("bname",bname);
            i.putExtra("wname",wname);
            i.putExtra("link",link);
            //   i.putExtra("category",category);
            i.putExtra("mb1",mb1);
            i.putExtra("mb2",mb2);
            i.putExtra("time",time);
            startActivity(i);

//            StartAppAd.showAd(getBaseContext());

        }
    }).ShowInterstitial(MoviesIndexFeedActivity.this, true);

        }else{
            online=false;

            showNoInternet();

        }

    }
	/*  public void showTitleImg(String img1,String img2,String img3,String img4,String img5){
	 final  String[] images={
	 img1,
	 img2,
	 img3,
	 img4,
	 img5
	 };

	 showTI(images[currentImg]);
	 handler.postDelayed(runnable = new Runnable() {
	 public void run() {

	 handler.postDelayed(runnable, 3000);
	 currentImg++;
	 if(currentImg==maxImg+1){
	 currentImg=0;
	 }
	 showTI(images[currentImg]);

	 }
	 }, 3000);

	 pre.setOnClickListener(new OnClickListener(){

	 @Override
	 public void onClick(View p1) {
	 currentImg--;
	 if(currentImg==-1){
	 currentImg=maxImg;
	 }
	 showTI(images[currentImg]);
	 }
	 });
	 next.setOnClickListener(new OnClickListener(){

	 @Override
	 public void onClick(View p1) {
	 currentImg++;
	 if(currentImg==maxImg+1){
	 currentImg=0;
	 }
	 showTI(images[currentImg]);
	 }
	 });
	 }
	 void showTI(String res){

	 if((res.length()>5)&&(online)){
	 Glide
	 .with(getApplicationContext())
	 .load(res).placeholder(R.drawable.header_image)
	 .into(title_img);
	 double current=currentImg+1;
	 String pot=current+"/5";
	 position.setText(pot.replaceAll(".0",""));
	 }else{
	 title_img.setImageResource(R.drawable.loading_book);
	 }

	 }*/





	/*

	 private void LoadInterstitialAds() {
	 IUnityAdsListener InterListener = new IUnityAdsListener() {

	 public void onUnityAdsReady(String adUnitId) {
	 // Implement functionality for an ad being ready to show.
	 //Toast.makeText(getApplicationContext(),"Interstitial ads loaded",Toast.LENGTH_SHORT).show();
	 DisplayInterstitialAd();

	 }

	 @Override
	 public void onUnityAdsStart(String adUnitId) {
	 // Implement functionality for a user starting to watch an ad.


	 }

	 @Override
	 public void onUnityAdsFinish(String adUnitId, UnityAds.FinishState finishState) {
	 // Implement conditional logic for each ad completion status:
	 LoadInterstitialAds();

	 }


	 //@Override
	 public void onInitializationComplete() {
	 DisplayInterstitialAd();
	 }

	 @Override
	 public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
	 // Implement functionality for a Unity Ads service error occurring.
	 Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
	 LoadInterstitialAds();

	 }
	 };





	 UnityAds.setListener(InterListener);
	 UnityAds.load(InterID);

	 //DisplayInterstitialAd();

	 }


	 */



    private void changeFont()
    {
        currentFont++;
        if (currentFont == 3)
        {
            currentFont = 0;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("font_main", currentFont);
        editor.commit();
        try{
            // processTitleImg(getFromPrefs(currentLink));
            //processRecent(getFromPrefs(currentLink),"recent");
            //	recent_adapter=new RecentAdapter();
            //recent_rv.setAdapter(recent_adapter);
            //    processSuggested(getFromPrefs(currentLink),"suggested");
            //   suggested_adapter=new SuggestedAdapter();
            //     suggested_rv.setAdapter(suggested_adapter);
            //   processChild(getFromPrefs(currentLink),"child");
            //     child_adapter=new ChildAdapter();
            //    child_rv.setAdapter(child_adapter);

            processYoung(getFromPrefs(currentLink),"video");
            young_adapter=new YoungAdapter();
            young_rv.setAdapter(young_adapter);

        }catch(Exception e){
//            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();

            Toast.makeText(MoviesIndexFeedActivity.this,
                    "\"သတိ လိုင်း\u200Bနှေး\u200Bနေပါသည်\"\\n\"Screen  ကို လက်နဲ့ပွတ်ဆွဲပြီး refresh လုပ်\u200Bပေးပါ စာ\u200Bတွေ\u200Bပေါ်လာပါမည်။\"\\n \"မ\u200Bပေါ်လာပါက ဗွီ ပီ အန် ကို အဖွင့် အပိတ်လုပ်ပြီးစမ်းကြည့်ပါ\"", Toast.LENGTH_LONG).show();
        }

    }




    public void gotoFacebook()
    {
        final String urlFb = "fb://page/"+FACEBOOK_PROFILE_ID;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser

        final PackageManager packageManager = getPackageManager();

        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() == 0) {
            final String urlBrowser = FACEBOOK_URL;
            intent.setData(Uri.parse(urlBrowser));
        }

        startActivity(intent);
    }
    public void gotoFbAccount()
    {
        final String urlFb = "fb://profile/"+"100029607351728";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser

        final PackageManager packageManager = getPackageManager();

        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() == 0) {
            final String urlBrowser = "https://www.facebook.com/techkipon";
            intent.setData(Uri.parse(urlBrowser));
        }

        startActivity(intent);
    }
    private void showNoInternet (){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View parent_view = LayoutInflater.from(this).inflate(R.layout.one_button_dia, null);
        dialog.setView(parent_view);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
        TextView tv_title = (TextView) dialog.findViewById(R.id.title);
        TextView tv_message = (TextView) dialog.findViewById(R.id.message);
        final Button cancel= (Button) dialog.findViewById(R.id.bt1);
        iv.setImageResource(R.drawable.no_internet_icon);
        tv_title.setText("အင်တာနက်မရှိခ​ြင်း");
        tv_message.setText("မင်္ဂလာပါ....သူငယ်ချင်းတို့​​ေရ။ video ကြည့်နိုင်ရန် အင်တာနက်ဖွင့်ထားရန် လိုအပ်ပါတယ်။");
        cancel.setText("ဟုတ်​ပ​ြီ");
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {

                dialog.dismiss();
            }});


    }}

	/*void loadInter(){
		MobileAds.initialize(IndexFeedActivity2.this);
		AdRequest adIRequest = new AdRequest.Builder().build();

		// Prepare the Interstitial Ad Activity
		interstitial = new InterstitialAd(IndexFeedActivity2.this);

		// Insert the Ad Unit ID
		interstitial.setAdUnitId(getString(R.string.interstitial_id));

		// Interstitial Ad load Request
		interstitial.loadAd(adIRequest);

		// Prepare an Interstitial Ad Listener
		interstitial.setAdListener(new AdListener()
			{
				public void onAdLoaded()
				{
					if (interstitial.isLoaded()) {
						interstitial.show();
					}

				}
			});
	}
}

*/
