package com.mmschooledu.Lesson;


import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mmschooledu.FontConverter;
import com.mmschooledu.Item.PostItem;
import com.mmschooledu.R;
import com.mmschooledu.TGuideFeedActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EngEssayActivity extends MmEssayFeedActivity
{



    String[] links={

            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Eeassay?alt=json" ,



    };





    String[] titles={
            "English Essays",

    };
    int current=0;





    @Override
    public void _Options_Menu_Click(MenuItem item)
    {
        int id=item.getItemId();


        if (id == R.id.story)
        {

            current = 0;
        }




        refresh();
    }

    private Intent Intent(String p0, Uri parse)
    {
        // TODO: Implement this method
        return null;
    }




    //Blogger
    public void processJson(String inputJson){
        posts = new ArrayList<PostItem>();
        String input="";
        switch(currentFont){
            case FONT_ZAWGYI:
                input= FontConverter.uni2zg(inputJson);
                break;
            case FONT_UNI:
                input=FontConverter.zg2uni(inputJson);
                break;
            default:
                input=inputJson;
                break;
        }
        try
        {




            JSONObject jo=new JSONObject(input);
            JSONArray ja=jo.getJSONObject("feed").getJSONArray("entry");
            JSONObject jo2;
            for(int i=0;i<ja.length();i++){
                PostItem pi=new PostItem();
                jo2=ja.getJSONObject(i);
                pi.title= jo2.getJSONObject("title").getString("$t");
                pi.content=jo2.getJSONObject("content").getString("$t");
                pi.published= jo2.getJSONObject("published").getString("$t");

                addItem(pi);
                try{
                    String thumbnail=jo2.getJSONObject("media$thumbnail").getString("url");
                    pi.thumbnailUrl=thumbnail;
                }catch(Exception e){
                }
            }

        }
        catch (JSONException e)
        {
            showNoInternet();
        }
    }


    @Override
    public boolean useGridLayout()
    {
        return false;
    }



    @Override
    public String getFeedAddress()
    {
        setTitle(titles[current]);
        //Blogger
        //return "http://aidenyinyilwin.blogspot.com/feeds/posts/default?alt=json";
        //	return "http://bookworldnet.blogspot.com/feeds/posts/default?alt=json";

        //000webhost
        return links[current];
    }
    private void showNoInternet (){
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View parent_view = LayoutInflater.from(this).inflate(R.layout.nointernet_dia, null);
        dialog.setView(parent_view);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        // Finding Views inside dialog
        TextView tv_title = dialog.findViewById(R.id.dialogTextView1);
        Button button = dialog.findViewById(R.id.dialogButton1);


        tv_title.setText("အင်တာနက်လိုင်း\u200Bနှေး\u200Bနေပါသည်\n" +
                "Wifi မသုံးပဲ Phone  Sim card internet ဖြင့်သုံးပါ \n သို့မဟုတ်\n  Mytel Sim ဖြစ်လျှင် ဗွီပီအမ် ခံပြီးမှပြန်ဝင်ပါ \n " +
                "သို့မဟုတ် \n Phone Screen ကို လက်ဖြင့်ပွတ်ဆွဲပြီး refresh လုပ်\u200Bပေးပါ");

        button.setText("..ဟုတ်ကကဲ့..");

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View p1)
            {
                dialog.dismiss();
            }});
    }}






