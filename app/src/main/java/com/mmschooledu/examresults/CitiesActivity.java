package com.mmschooledu.examresults;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.myanmartools.TransliterateZ2U;
import com.google.myanmartools.ZawgyiDetector;
import com.mmschooledu.R;
import com.mmschooledu.examresults.model.Bookmark;
import com.mmschooledu.examresults.model.BookmarkDAO;
import com.mmschooledu.examresults.model.CitiesModel;
//import com.unity3d.ads.IUnityAdsInitializationListener;
//import com.unity3d.ads.IUnityAdsLoadListener;
//import com.unity3d.ads.IUnityAdsShowListener;
//import com.unity3d.ads.UnityAds;
//import com.unity3d.ads.UnityAdsShowOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CitiesActivity  extends AppCompatActivity {//implements IUnityAdsInitializationListener {

    //    private String unityGameID = "5634362";
//    private Boolean testMode = false;
//    private String adUnitId = "Interstitial_Android";
//
//    private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
//        @Override
//        public void onUnityAdsAdLoaded(String placementId) {
//
//        }
//
//        @Override
//        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
//            Log.e("UnityAdsExample", "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
//        }
//    };
//
//    private IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
//        @Override
//        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
//            Log.e("UnityAdsExample", "Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
//        }
//
//        @Override
//        public void onUnityAdsShowStart(String placementId) {
//            Log.v("UnityAdsExample", "onUnityAdsShowStart: " + placementId);
//        }
//
//        @Override
//        public void onUnityAdsShowClick(String placementId) {
//            Log.v("UnityAdsExample", "onUnityAdsShowClick: " + placementId);
//        }
//
//        @Override
//        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
//            UnityAds.load(adUnitId, loadListener);
//
//        }
//    };
    private SwipeRefreshLayout mSwipeLayout;
    RecyclerView rv_cities;
    FeedAdapter adapter;
    List<CitiesModel> posts, filteredposts;
    JSONArray citiesArray;
    String region,year, addtion_url;

    ZawgyiDetector detector = new ZawgyiDetector();
    private static final TransliterateZ2U converter = new TransliterateZ2U("Zawgyi to Unicode");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cities);

        prepareView();
        actionData();
        actionView();
        // Initialize the SDK:
        // UnityAds.initialize(getApplicationContext(), unityGameID, testMode, this);
        // Start a background thread to process the JSON data
        new Thread(() -> {
            String citiesArrayString = getIntent().getStringExtra("citiesArray");
            posts.clear();
            filteredposts.clear();
            mSwipeLayout.setRefreshing(true);
            try {
                // Your JSON parsing code
                JSONArray jarr = new JSONArray(citiesArrayString);

                for (int j = 0; j < jarr.length(); j++) {
                    CitiesModel p = new CitiesModel();
                    JSONObject jsonObject = jarr.getJSONObject(j);
                    String title = jsonObject.getString("title");

                    // Detect Zawgyi encoding and convert if necessary
                    double score2 = detector.getZawgyiProbability(title);
                    if (score2 > 0.999) {
                        title = converter.convert(title);
                    }

                    p.table_no = title;
                    p.pdf_url = addtion_url+jsonObject.getString("src");
                    posts.add(p);
                    filteredposts.add(p);
                }

                // Update UI if necessary
                runOnUiThread(() -> {
                    adapter = new FeedAdapter();
                    rv_cities.setAdapter(adapter);
                    mSwipeLayout.setRefreshing(false);
                    // Update UI here
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
    void prepareView() {

        rv_cities= findViewById(R.id.rv_cities);
        mSwipeLayout= findViewById(R.id.swipeRefreshLayout);

    }
    void actionData() {
        String citiesArrayString = getIntent().getStringExtra("citiesArray");
        addtion_url= getIntent().getStringExtra("base_url");
        year= getIntent().getStringExtra("year");
        region= getIntent().getStringExtra("region");
        try {
            citiesArray = new JSONArray(citiesArrayString);
            //   Toast.makeText(this,citiesArray.length(), Toast.LENGTH_SHORT).show();
        }catch (JSONException e) {
            Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    void actionView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setTitle((R.string.app_title)+year);
            actionBar.setTitle((region+" - "+year));
            // actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        posts = new ArrayList<CitiesModel>();
        filteredposts = new ArrayList<CitiesModel>();
        rv_cities.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Start a background thread to process the JSON data
                new Thread(() -> {
                    String citiesArrayString = getIntent().getStringExtra("citiesArray");
                    posts.clear();
                    filteredposts.clear();
                    mSwipeLayout.setRefreshing(true);
                    try {
                        // Your JSON parsing code
                        JSONArray jarr = new JSONArray(citiesArrayString);

                        for (int j = 0; j < jarr.length(); j++) {
                            CitiesModel p = new CitiesModel();
                            JSONObject jsonObject = jarr.getJSONObject(j);
                            String title = jsonObject.getString("title");

                            // Detect Zawgyi encoding and convert if necessary
                            double score2 = detector.getZawgyiProbability(title);
                            if (score2 > 0.999) {
                                title = converter.convert(title);
                            }

                            p.table_no = title;
                            p.pdf_url = addtion_url+jsonObject.getString("src");
                            posts.add(p);
                            filteredposts.add(p);
                        }

                        // Update UI if necessary
                        runOnUiThread(() -> {
                            adapter = new FeedAdapter();
                            rv_cities.setAdapter(adapter);
                            mSwipeLayout.setRefreshing(false);
                            // Update UI here
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        });

        if (citiesArray!=null) {
            // loadJsonandShow(citiesArray);
        }else{
            Toast.makeText(getApplicationContext(),"Data error",Toast.LENGTH_LONG).show();
        }
        SearchView simpleSearchView =findViewById(R.id.simpleSearchView); // inititate a search view

// perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
// do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
// do something when text changes
                adapter.filter(newText.toString());
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed(); // or handle the back/home navigation as per your requirements
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup p1, int p2) {

            View v = getLayoutInflater().inflate(R.layout.custom_items_cities, p1, false);
            return new  FeedAdapter.ViewHolder(v);
        }
        public void filter(String charText) {
            charText = charText.toLowerCase();
            filteredposts.clear();
            if (charText.length() == 0) {
                filteredposts.addAll(posts);
            } else {
                for (CitiesModel pi : posts) {
                    if ((pi.table_no.toLowerCase()).contains(charText)) {
                        filteredposts.add(pi);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return filteredposts.size();

        }

        @Override
        public void onBindViewHolder(final ViewHolder p1, @SuppressLint("RecyclerView") int p2) {

            if(filteredposts.get(p2).table_no.contains("၁")&&filteredposts.get(p2).table_no.contains("။")) {
                p1.tv_no.setVisibility(View.GONE);
            }else if(filteredposts.get(p2).table_no.contains("၂")&&filteredposts.get(p2).table_no.contains("။")){
                p1.tv_no.setVisibility(View.GONE);
            } else if (filteredposts.get(p2).table_no.contains("၃")&&filteredposts.get(p2).table_no.contains("။")) {
                p1.tv_no.setVisibility(View.GONE);
            }else if(filteredposts.get(p2).table_no.contains("၄")&&filteredposts.get(p2).table_no.contains("။")){
                p1.tv_no.setVisibility(View.GONE);
            } else if (filteredposts.get(p2).table_no.contains("၅")&&filteredposts.get(p2).table_no.contains("။")) {
                p1.tv_no.setVisibility(View.GONE);
            }else if(filteredposts.get(p2).table_no.contains("၆")&&filteredposts.get(p2).table_no.contains("။")){
                p1.tv_no.setVisibility(View.GONE);
            } else if (filteredposts.get(p2).table_no.contains("၇")&&filteredposts.get(p2).table_no.contains("။")) {
                p1.tv_no.setVisibility(View.GONE);
            }else if(filteredposts.get(p2).table_no.contains("၈")&&filteredposts.get(p2).table_no.contains("။")){
                p1.tv_no.setVisibility(View.GONE);
            } else if (filteredposts.get(p2).table_no.contains("၉")&&filteredposts.get(p2).table_no.contains("။")) {
                p1.tv_no.setVisibility(View.GONE);
            }else if(filteredposts.get(p2).table_no.contains("၀")&&filteredposts.get(p2).table_no.contains("။")){
                p1.tv_no.setVisibility(View.GONE);
            } else {
                p1.tv_no.setText(EngtoMmnumber(p2+1)+"။");
            }
            p1.tv_table_no.setText(filteredposts.get(p2).table_no);
            p1.cv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p1) {
                    if(getFromPrefs("ad").equals("add")){
                        //    UnityAds.show(CitiesActivity.this, adUnitId, new UnityAdsShowOptions(), showListener);
                    }

                    gotoPdf(filteredposts.get(p2).table_no,filteredposts.get(p2).pdf_url);
                }
            });

        }
        public CitiesModel getItem(int pos) {
            return filteredposts.get(pos);
        }
        public void reset() {
            posts.clear();
            filteredposts.clear();
            notifyDataSetChanged();
        }
        public FeedAdapter() {
            super();
            filteredposts = new ArrayList<CitiesModel>();
            filteredposts.addAll(posts);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            CardView cv_item;
            TextView tv_table_no,tv_no;
            @Override
            public void onClick(View view) {

            }

            public ViewHolder(View view) {
                super(view);
                cv_item = view.findViewById(R.id.cv_item);
                tv_no=view.findViewById(R.id.tv_no);
                tv_table_no = view.findViewById(R.id.tv_table_no);
                view.setOnClickListener(this);
            }
        }
    }
/*    void loadJsonandShow(JSONArray ja){
        posts.clear();
        filteredposts.clear();

        try {
            JSONArray jarr = ja;
            for (int j = 0; j < jarr.length(); j++) {
                CitiesModel p = new CitiesModel();
                String title=(jarr.getJSONObject(j).getString("title"));
                double score2 = detector.getZawgyiProbability(title);
                if(score2>0.999){
                    title = converter.convert(title);
                }
                p.table_no =title;
                p.pdf_url = (jarr.getJSONObject(j).getString("src"));
                posts.add(p);
                filteredposts.add(p);
            }
        }catch (JSONException e){
            Log.e(TAG, "Error show blog post: " + e.getMessage());
            Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
        }
        adapter = new FeedAdapter();
        rv_cities.setAdapter(adapter);
        mSwipeLayout.setRefreshing(false);
    }*/

    @Override
    protected void onResume() {
        super.onResume();

    }
    void gotoPdf(String title, String pdfurl) {
        Intent intent = null;
        if (pdfurl.contains("drive.google")){
            intent=new Intent(CitiesActivity.this,WebViewActivity.class);
        }else{
            intent=new Intent(CitiesActivity.this,BookViewerActivity.class);
        }
        intent.putExtra("url",pdfurl );
        intent.putExtra("year",year);
        intent.putExtra("title",title);
        startActivity(intent);

        BookmarkDAO bookmarkDAO = new BookmarkDAO(this);
// Create a new bookmark instance with the current date and time
        Bookmark newBookmark = new Bookmark();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault()).format(new Date());
        newBookmark.setDate(currentDate);
        newBookmark.setTitle(year+",,"+title);
        newBookmark.setUrl(pdfurl);

// Add the bookmark to the database
        bookmarkDAO.addBookmark(newBookmark);

    }
    public String EngtoMmnumber(int num){
        String Mmnum;
        Mmnum=(num+"").replace("1","၁").replace("2","၂").replace("3","၃").replace("4","၄").replace("5","၅")
                .replace("6","၆").replace("7","၇").replace("8","၈").replace("9","၉").replace("0","၀");
        return Mmnum;
    }
    //    @Override
//    public void onInitializationComplete() {
//        UnityAds.load(adUnitId, loadListener);
//    }
//
//    @Override
//    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
//
//    }
    public String getFromPrefs(String key) {

        SharedPreferences sharedPreferences =getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String lastData = sharedPreferences.getString(key, "");
        return lastData;
    }

}
