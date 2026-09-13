package com.mmschooledu.Dictionary;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mmschooledu.R;

public class DictionaryDetailActivity extends AppCompatActivity
{static String[] titles,patch;
  //  Button button;



    TextView tv,tv2;
    ImageView iv;
    String bname,type,link,link2,thumbnail,wname;
    //androidx.appcompat.widget.Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionarydetail_layout);


        tv2 = (TextView)findViewById(R.id.tvViewerTitle);

        tv = (TextView)findViewById(R.id.tvDetail);
        //iv = (ImageView)findViewById(R.id.ivDetail);
        Intent intent=getIntent();
        bname = intent.getStringExtra("bname");
        type = intent.getStringExtra("type");
        link = intent.getStringExtra("link");
        link2 = intent.getStringExtra("link2");
        //thumbnail = intent.getStringExtra("thumbnail");
        wname = intent.getStringExtra("wname");
        setTitle(bname);
//        Glide.with(this)
//                .load(thumbnail)
//                .into(iv);
       //v.setText(bname + "\n\n" + wname);
        tv.setText(wname);
        tv2.setText(bname);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void _Back_Click(View v){
        finish();


    }}

//