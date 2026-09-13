package com.mmschooledu.UtvMovies;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.mmschooledu.JSONDownloader;
import com.mmschooledu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class UtvFrontActivity extends Activity  {
    AdRequest adRequest;
    private InterstitialAd interstitialAd;
    private AdView adView;

    TextView tv;

    String mvType="-";
    String mvCountry="-";
    Button btnAll,btnAction,btnAdv,btnAni,btnCrime,btnDrama,btnFamily,btnHorror,
    btnUSA,btnKorea,btnIndia,btnChina,btnThai,btnUK,btnFrance,btnRussia,btnSpain,btnHK,
    btnMalay,btnJapan,btnCanada,btnOthers;
    List<TVData> list,posts;
    GridView gridView;
    ULGAdapter adapter;

    ProgressDialog pd;

    LinearLayout tvLayout,mvLayout,countryLayout;
    String tvLink="https://script.google.com/macros/s/AKfycbxFLL536lOAdpCSGqsJwmbZM8nYU2XpYpBvl8f7EauhtXYM0l6LqW8MplMDgK_6lF12/exec";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.umvmain_page);


        adView = (AdView)
                findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        gridView=findViewById(R.id.gridView1);
        tvLayout=findViewById(R.id.linearTV);
        mvLayout=findViewById(R.id.linearMovie);
        countryLayout=findViewById(R.id.linearCountry);
        btnAll=findViewById(R.id.btnAll);
        btnAction=findViewById(R.id.btnAction);
        btnAdv=findViewById(R.id.btnAdv);
        btnAni=findViewById(R.id.btnAni);
        btnCrime=findViewById(R.id.btnCrime);
        btnFamily=findViewById(R.id.btnFamily);
        btnHorror=findViewById(R.id.btnHorror);
        btnDrama=findViewById(R.id.btnDrama);
        btnUSA=findViewById(R.id.btnUSA);
        btnUK=findViewById(R.id.btnUK);
        btnKorea=findViewById(R.id.btnKorea);
        btnIndia=findViewById(R.id.btnIndia);
        btnChina=findViewById(R.id.btnChina);
        btnThai=findViewById(R.id.btnThai);
        btnFrance=findViewById(R.id.btnFrance);
        btnRussia=findViewById(R.id.btnRussia);
        btnSpain=findViewById(R.id.btnSpain);
        btnHK=findViewById(R.id.btnHK);
        btnMalay=findViewById(R.id.btnMalay);
        btnJapan=findViewById(R.id.btnJapan);
        btnCanada=findViewById(R.id.btnCanada);
        btnOthers=findViewById(R.id.btnOthers);
        tv=findViewById(R.id.tvDonate);

        pd=new ProgressDialog(this);
        pd.setMessage("Please wait....");
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
            new DownloadTask().execute(tvLink);
            pd.show();
        }else {
            DataAlert();
        }


    }


    public class ULGAdapter extends BaseAdapter {
        public ULGAdapter() {
            list = new ArrayList<>();
            list.addAll(posts);
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int p1) {
            return list.get(p1);
        }
        @Override
        public long getItemId(int p1) {
            return 0;
        }
        @SuppressLint("InflateParams")
        @Override
        public View getView(final int p1, View p2, ViewGroup p3) {
            if (p2 == null) {
                p2 = getLayoutInflater().inflate(R.layout.umvitem_main, null);
            }
            final TextView tv1 = p2.findViewById(R.id.tvCh);

            final ImageView imageView=p2.findViewById(R.id.imgTV);
            if(list.get(p1).imgLink.length()>0){
                Glide
                        .with(UtvFrontActivity.this)
                        .load(list.get(p1).imgLink)
                        .into(imageView);
            }else {
                Glide
                        .with(UtvFrontActivity.this)
                        .load(R.drawable.apk)
                        .into(imageView);
            }
            tv1.setText(list.get(p1).chName);

            return p2;
        }
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            list.clear();
            if (charText.length() == 0) {
                list.addAll(posts);
            } else {
                for (TVData td : posts) {
                    if (td.chName.toLowerCase(Locale.getDefault()).contains(charText)) {
                        list.add(td);

                    }
                }
            }
            notifyDataSetChanged();
        }
    }
    private class DownloadTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... p1)
        {
            return JSONDownloader.download(p1[0]);
        }
        @Override
        public void onPostExecute(String result) {

                processJSON(result);
                if(!(pd == null)){
                    pd.dismiss();
                }
                tvLayout.setVisibility(View.VISIBLE);
                mvLayout.setVisibility(View.VISIBLE);
            countryLayout.setVisibility(View.VISIBLE);
            adapter=new ULGAdapter();
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    playActivity(list.get(position).chLink,"Play","","");
                //    Toast.makeText(FrontActivity.this,list.get(position).chLink,Toast.LENGTH_LONG).show();
                }
            });

            btnAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","-");
                }
            });
            btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","Action","-");
                }
            });
            btnAdv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","Adventure","-");
                }
            });
            btnAni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","Animation","-");
                }
            });
            btnCrime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","Crime","-");
                }
            });
            btnDrama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","Drama","-");
                }
            });
            btnFamily.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","Family","-");
                }
            });
            btnHorror.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","Horror","-");
                }
            });
            btnUSA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","USA");
                }
            });
            btnKorea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","Korea");
                }
            });
            btnChina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","China");
                }
            });
            btnIndia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","India");
                }
            });
            btnThai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","Thailand");
                }
            });
            btnUK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","UK");
                }
            });
            btnFrance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","France");
                }
            });
            btnRussia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","Russia");
                }
            });
            btnSpain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","Spain");
                }
            });
            btnHK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","Hong+Kong");
                }
            });
            btnMalay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","Malaysia");
                }
            });
            btnJapan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","Japan");
                }
            });
            btnCanada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","Canada");
                }
            });
            btnOthers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playActivity("","","-","Others");
                }
            });

            tv.setVisibility(View.VISIBLE);
            tv.setSelected(true);
            if(list.get(0).adText.length()>0){

               tv.setText(list.get(0).adText);
            }



        }
    }
    private void processJSON(String input){
        posts= new ArrayList<>();
        try
        {
            JSONObject jo=new JSONObject(input);
            JSONArray jarray = jo.getJSONArray("data");

            for(int j=0;j<jarray.length();j++){
                TVData td=new TVData();

                td.chName=(jarray.getJSONObject(j).getString("Channel"));
                td.chLink=(jarray.getJSONObject(j).getString("Link"));
                td.imgLink=(jarray.getJSONObject(j).getString("Image"));
                td.adText=(jarray.getJSONObject(j).getString("AD Text"));
                posts.add(td);

            }

        }catch (JSONException ignored){
            Toast.makeText(UtvFrontActivity.this,ignored.toString(),Toast.LENGTH_LONG).show();
        }

    }
    public void playActivity(String s1,String s2,String s3,String s4){
        Intent intent=new Intent();
        if(s2.equals("Play")){

             intent=new Intent(UtvFrontActivity.this,UtvVlcPlayer.class);
            intent.putExtra("link",s1);

        }else{
            intent=new Intent(UtvFrontActivity.this,UtvMainActivity.class);
            intent.putExtra("type",s3);
            intent.putExtra("country",s4);
        }

        startActivity(intent);
    }
    public void DataAlert(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("No Internet");
        builder1.setMessage("Please turn on mobile data or wifi.");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                (dialog, id) -> {

                    dialog.cancel();
                    finish();
                });

        AlertDialog alert = builder1.create();
        alert.show();
    }
}
