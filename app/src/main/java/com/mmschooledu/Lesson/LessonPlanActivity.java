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


public class LessonPlanActivity extends LessonPlanFeedActivity
{





    String[] links={

           // "https://onlinemyanmarschool.blogspot.com/feeds/posts/default?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplan?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanallkg?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall1?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall2?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall3?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall4?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall5?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall6?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall7?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall8?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall9?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall10?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall11?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplanall12?alt=json" ,











    };





    String[] titles={
            "All LessonPlans",

            "Kg LessonPlans",
            "Grade 1 Lesson Plans",
            "Grade 2 Lesson Plans",
            "Grade 3 Lesson Plans",
            "Grade 4 Lesson Plans",
            "Grade 5 Lesson Plans",
            "Grade 6 Lesson Plans",
            "Grade 7 Lesson Plans",
            "Grade 8 Lesson Plans",
            "Grade 9 Lesson Plans",
            "Grade 10 Lesson Plans",
            "Grade 11 Lesson Plans",
            "Grade 12 Lesson Plans",




    };
    int current=0;





    @Override
    public void _Options_Menu_Click(MenuItem item)
    {
        int id=item.getItemId();


        if (id == R.id.newlessonplan)
        {

            current = 0;
        }


        else


        if (id == R.id.kg)
        {


            current = 1;
        }


        else

        if (id == R.id.grade1)
        {

            current = 2;
        }else

        if (id == R.id.grade2)
        {

            current = 3;
        }else

        if (id == R.id.grade3)
        {

            current = 4;
        }else

        if (id == R.id.grade4)
        {

            current = 5;
        }else

        if (id == R.id.grade5)
        {

            current = 6;
        }else


        if (id == R.id.grade6)
        {

            current = 7;
        }else
        if (id == R.id.grade7)
        {

            current = 8;
        }else

        if (id == R.id.grade8)
        {

            current = 9;
        }else
        if (id == R.id.grade9)
        {

            current = 10;
        }else

        if (id == R.id.grade10)
        {

            current = 11;
        }
        else

        if (id == R.id.grade11)
        {

            current = 12;
        }



        else

        if (id == R.id.grade12)
        {

            current = 13;
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






