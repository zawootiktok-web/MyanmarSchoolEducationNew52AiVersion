package com.mmschooledu;


import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;

import com.mmschooledu.Item.PostItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends JSONFeedActivity3
{





    String[] links={
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/App?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Story?alt=json" ,

            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplan?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Qtype?alt=json" ,


            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Tguide?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Eeassay?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Meassay?alt=json" ,

            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Enews?alt=json" ,








    };




    //String[] titles={
    String[] titles={
            "All",
            "App Uses",
            "Story ",
            "LessonPlans",
            "Question Type",
            "Teacher Guide",
            "English Eassay​ ​များ",
            "Myanmar Eassays",
            "English Eassays"





    };
    int current=0;





    @Override
    public void _Options_Menu_Click(MenuItem item)
    {
        int id=item.getItemId();


        if (id == R.id.lessonstory)
        {

            current = 0;
        }

        else

        if (id == R.id.use)
        {


            current = 1;
        }


        else


        if (id == R.id.story)
        {


            current = 2;
        }


        else

        if (id == R.id.lessonplan)
        {

            current = 3;
        }else

        if (id == R.id.qtype)
        {

            current = 4;
        }else


        if (id == R.id.tguide)
        {

            current = 5;
        }else

        if (id == R.id.eeassay)
        {

            current = 6;
        }else

        if (id == R.id.meassay)
        {

            current = 7;
        }

        if (id == R.id.Enews)
        {

            current = 8;
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
                input=FontConverter.uni2zg(inputJson);
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
}


