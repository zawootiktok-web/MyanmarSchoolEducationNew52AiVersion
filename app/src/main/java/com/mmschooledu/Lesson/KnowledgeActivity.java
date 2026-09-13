package com.mmschooledu.Lesson;


import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;

import com.mmschooledu.FontConverter;
import com.mmschooledu.Item.PostItem;
import com.mmschooledu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class KnowledgeActivity extends KnowledgeFeedActivity
{


    String[] links={
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/TJob?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Tguide?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Enews?alt=json" ,



            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledgekg?alt=json" ,


            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge1?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge2?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge3?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge4?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge5?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge6?alt=json" ,

            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge7?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge8?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge9?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge10?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge11?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Knowledge12?alt=json" ,











    };





    String[] titles={
            "All Lessons",
            "Teacher Jobs",
            "Teacher Guide",
            "Education News",
            "Kg",
            "Grade(1)",
            "Grade(2)",
            "Grade(3)",
            "Grade(4)",
            "Grade(5)",
            "Grade(6)",
            "Grade(7)",
            "Grade(8)",

            "Grade(9)",
            "Grade(10)",
            "Grade(11)",
            "Grade(12)",




    };
    int current=0;





    @Override
    public void _Options_Menu_Click(MenuItem item)
    {
        int id=item.getItemId();


        if (id == R.id.lessonnew)
        {

            current = 0;
        }



        else

        if (id == R.id. tjob)
        {


            current = 1;
        }
        else
        if (id == R.id. tguide)
        {


            current = 2;
        }




        else

        if (id == R.id.enews)
        {


            current = 3;
        }





        else


        if (id == R.id.kg)
        {


            current = 4;
        }


        else

        if (id == R.id.grade1)
        {

            current = 5;
        }else

        if (id == R.id.grade2)
        {

            current = 6;
        }else

        if (id == R.id.grade3)
        {

            current = 7;
        }else

        if (id == R.id.grade4)
        {

            current = 8;
        }else

        if (id == R.id.grade5)
        {

            current = 9;
        }else


        if (id == R.id.grade6)
        {

            current = 10;
        }else
        if (id == R.id.grade7)
        {

            current = 11;
        }else

        if (id == R.id.grade8)
        {

            current = 12;
        }else
        if (id == R.id.grade9)
        {

            current = 13;
        }else

        if (id == R.id.grade10)
        {

            current = 14;
        }
        else

        if (id == R.id.grade11)
        {

            current = 15;
        }



        else

        if (id == R.id.grade12)
        {

            current = 16;
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


