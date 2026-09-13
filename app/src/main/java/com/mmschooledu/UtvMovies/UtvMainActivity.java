//package com.mmschooledu.UtvMovies;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.mmschooledu.R;
////import com.mmschooledu.R;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//public class UtvMainActivity extends Activity {
//    private AdView adView;
//
//    GridView lv;
//    List<UtvMovieList> posts,list;
//    ULGAdapter adapter;
//    ProgressDialog pd;
//    int i=1;
//
//    Button btnNext,btnPrev;
//    String mvType="-";
//    String mvCountry="-";
//    String actionALL="http://zegomovie.com/index.php?s=/list-select-id-1-type-"+mvType+"-area-"+mvCountry+"-year--star--state--order-addtime.html";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.umvactivity_main);
//        pd=new ProgressDialog(UtvMainActivity.this);
//
//
//        adView = (AdView)
//                findViewById(R.id.ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);
//
//
//
//        pd.setTitle("MSE Cinema");
//        pd.setMessage("Please wait ....");
//        lv = findViewById(R.id.gridView);
//        float scalefactor = getResources().getDisplayMetrics().density * 100;
//        int number = getWindowManager().getDefaultDisplay().getWidth();
//        int columns = (int) ((float) number / (float) scalefactor);
//        lv.setNumColumns(columns);
//        btnNext=findViewById(R.id.next);
//        pd.show();
//        mvType=getIntent().getStringExtra("type");
//        mvCountry=getIntent().getStringExtra("country");
//        actionALL="http://zegomovie.com/index.php?s=/list-select-id-1-type-"+mvType+"-area-"+mvCountry+"-year--star--state--order-addtime.html";
//        new WebScrapeTask().execute(actionALL);
//        btnNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                i++;
//                NextPage(i);
//            }
//        });
//        btnPrev=findViewById(R.id.prev);
//        btnPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                i--;
//                NextPage(i);
//            }
//        });
//
//
//    }
//    public void NextPage(int num){
//        String page2="http://zegomovie.com/index.php?s=/list-select-id-1-type-"+mvType+"-area-"+mvCountry+"-year--star--state--order-addtime-p-"+num+".html";
//        if(num<2){
//            new WebScrapeTask().execute(actionALL);
//            i=1;
//            Toast.makeText(UtvMainActivity.this,"No Page",Toast.LENGTH_SHORT).show();
//        }else{
//            new WebScrapeTask().execute(page2);
//            Toast.makeText(UtvMainActivity.this,"Page "+num,Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
//
//    public class WebScrapeTask extends AsyncTask<String,Void,String>
//    {
//        @Override
//        protected String doInBackground(String[] p1)
//        {
//            try
//            {
//                Document doc = Jsoup.connect(p1[0]).get();
//                return process(doc);
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//                return "NO Result";
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result)
//        {
//            super.onPostExecute(result);
//            if(result.length()>0){
//                Toast.makeText(UtvMainActivity.this,"No Data",Toast.LENGTH_LONG).show();
//                i=1;
//            }
//
//            adapter=new ULGAdapter();
//            lv.setAdapter(adapter);
//            pd.dismiss();
//            lv.setOnItemClickListener((adapterView, view, i, l) -> {
//                String mName=list.get(i).movieName;
//                String link="http://www.zegomovie.com"+list.get(i).itemLink;
//                String img="http://www.zegomovie.com"+list.get(i).imgLink;
//                Intent intent=new Intent(UtvMainActivity.this, UtvItemChoiceActivity.class);
//                intent.putExtra("link",link);
//                intent.putExtra("name",mName);
//                intent.putExtra("img",img);
//                startActivity(intent);
//                Toast.makeText(UtvMainActivity.this,mName,Toast.LENGTH_LONG).show();
//            });
//
//
//        }
//
//    }
//
//    public String process(Document doc){
//        posts= new ArrayList<>();
//        Elements elements=doc.select("li.col-sm-3.col-xs-4");
//        String res="";
//        for(Element e:elements){
//            UtvMovieList ml=new UtvMovieList();
//            ml.itemLink=e.select("a").first().attr("href");
//            ml.movieName=e.select("h2").text();
//            ml.imgLink=e.select("img").first().attr("data-original");
//            posts.add(ml);
//
//        }
//
//
//        return res;
//
//    }
//
//    public class ULGAdapter extends BaseAdapter {
//        public ULGAdapter() {
//            list = new ArrayList<>();
//            list.addAll(posts);
//        }
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//        @Override
//        public Object getItem(int p1) {
//            return list.get(p1);
//        }
//        @Override
//        public long getItemId(int p1) {
//            return 0;
//        }
//        @SuppressLint("InflateParams")
//        @Override
//        public View getView(final int p1, View p2, ViewGroup p3) {
//            if (p2 == null) {
//                p2 = getLayoutInflater().inflate(R.layout.umvitem_layout, null);
//            }
//            final TextView tv1 = p2.findViewById(R.id.tvName);
//
//            final ImageView imageView=p2.findViewById(R.id.iv);
//            if(list.get(p1).imgLink.length()>0){
//                Glide
//                        .with(UtvMainActivity.this)
//                        .load("http://www.zegomovie.com"+list.get(p1).imgLink)
//                        .into(imageView);
//            }else {
//                Glide
//                        .with(UtvMainActivity.this)
//                        .load(R.drawable.movies)
//                        .into(imageView);
//            }
//            tv1.setText(list.get(p1).movieName);
//
//            return p2;
//        }
//        public void filter(String charText) {
//            charText = charText.toLowerCase(Locale.getDefault());
//            list.clear();
//            if (charText.length() == 0) {
//                list.addAll(posts);
//            } else {
//                for (UtvMovieList td : posts) {
//                    if (td.movieName.toLowerCase(Locale.getDefault()).contains(charText)) {
//                        list.add(td);
//
//                    }
//                }
//            }
//            notifyDataSetChanged();
//        }
//    }
//}



package com.mmschooledu.UtvMovies;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mmschooledu.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UtvMainActivity extends Activity {
    private AdView adView;
    GridView lv;
    List<UtvMovieList> posts = new ArrayList<>(); // Prevent null
    List<UtvMovieList> list = new ArrayList<>();
    ULGAdapter adapter;
    ProgressDialog pd;
    int i = 1;
    Button btnNext, btnPrev;
    String mvType = "-";
    String mvCountry = "-";
    String actionALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umvactivity_main);
        pd = new ProgressDialog(UtvMainActivity.this);

        adView = findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        pd.setTitle("MSE Cinema");
        pd.setMessage("Please wait ....");
        lv = findViewById(R.id.gridView);
        float scalefactor = getResources().getDisplayMetrics().density * 100;
        int number = getWindowManager().getDefaultDisplay().getWidth();
        int columns = (int) ((float) number / scalefactor);
        lv.setNumColumns(columns);

        btnNext = findViewById(R.id.next);
        btnPrev = findViewById(R.id.prev);

        mvType = getIntent().getStringExtra("type");
        mvCountry = getIntent().getStringExtra("country");

        actionALL = "http://zegomovie.com/index.php?s=/list-select-id-1-type-" + mvType + "-area-" + mvCountry + "-year--star--state--order-addtime.html";
        pd.show();
        new WebScrapeTask().execute(actionALL);

        btnNext.setOnClickListener(v -> {
            i++;
            NextPage(i);
        });

        btnPrev.setOnClickListener(v -> {
            i--;
            NextPage(i);
        });
    }

    public void NextPage(int num) {
        String page2 = "http://zegomovie.com/index.php?s=/list-select-id-1-type-" + mvType + "-area-" + mvCountry + "-year--star--state--order-addtime-p-" + num + ".html";
        if (num < 2) {
            new WebScrapeTask().execute(actionALL);
            i = 1;
            Toast.makeText(UtvMainActivity.this, "No Page", Toast.LENGTH_SHORT).show();
        } else {
            new WebScrapeTask().execute(page2);
            Toast.makeText(UtvMainActivity.this, "Page " + num, Toast.LENGTH_SHORT).show();
        }
    }

    public class WebScrapeTask extends AsyncTask<String, Void, List<UtvMovieList>> {
        @Override
        protected List<UtvMovieList> doInBackground(String... urls) {
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                return process(doc);
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>(); // Return empty to avoid null
            }
        }

        @Override
        protected void onPostExecute(List<UtvMovieList> result) {
            super.onPostExecute(result);
            pd.dismiss();

            if (result == null || result.isEmpty()) {
                Toast.makeText(UtvMainActivity.this, "No Data", Toast.LENGTH_LONG).show();
                return;
            }

            posts = result;
            adapter = new ULGAdapter();
            lv.setAdapter(adapter);

            lv.setOnItemClickListener((adapterView, view, i, l) -> {
                UtvMovieList item = list.get(i);
                String link = "http://www.zegomovie.com" + item.itemLink;
                String img = "http://www.zegomovie.com" + item.imgLink;

                Intent intent = new Intent(UtvMainActivity.this, UtvItemChoiceActivity.class);
                intent.putExtra("link", link);
                intent.putExtra("name", item.movieName);
                intent.putExtra("img", img);
                startActivity(intent);
                Toast.makeText(UtvMainActivity.this, item.movieName, Toast.LENGTH_LONG).show();
            });
        }
    }

    public List<UtvMovieList> process(Document doc) {
        List<UtvMovieList> movieList = new ArrayList<>();
        Elements elements = doc.select("li.col-sm-3.col-xs-4");

        for (Element e : elements) {
            UtvMovieList ml = new UtvMovieList();
            ml.itemLink = e.select("a").first().attr("href");
            ml.movieName = e.select("h2").text();
            ml.imgLink = e.select("img").first().attr("data-original");
            movieList.add(ml);
        }

        return movieList;
    }

    public class ULGAdapter extends BaseAdapter {
        public ULGAdapter() {
            list = new ArrayList<>();
            if (posts != null) {
                list.addAll(posts);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.umvitem_layout, null);
            }

            TextView tv1 = convertView.findViewById(R.id.tvName);
            ImageView imageView = convertView.findViewById(R.id.iv);

            UtvMovieList movie = list.get(position);

            if (movie.imgLink != null && movie.imgLink.length() > 0) {
                Glide.with(UtvMainActivity.this)
                        .load("http://www.zegomovie.com" + movie.imgLink)
                        .into(imageView);
            } else {
                Glide.with(UtvMainActivity.this)
                        .load(R.drawable.movies)
                        .into(imageView);
            }

            tv1.setText(movie.movieName);
            return convertView;
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            list.clear();

            if (charText.length() == 0) {
                list.addAll(posts);
            } else {
                for (UtvMovieList td : posts) {
                    if (td.movieName.toLowerCase(Locale.getDefault()).contains(charText)) {
                        list.add(td);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
}
