package com.mmschooledu.Lesson;

import android.view.MenuItem;

import com.mmschooledu.FontConverter;
import com.mmschooledu.Item.PostItem;
import com.mmschooledu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Grade2Activity extends JSONFeedActivity1
{





    String[] links={

            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/grade2?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lesson2?alt=json" ,

            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Lplan2?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Qtype2?alt=json" ,

            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Itype2?alt=json" ,


            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Tguide?alt=json" ,

            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Eeassay2?alt=json" ,
            "https://onlinemyanmarschool.blogspot.com/feeds/posts/default/-/Meassay2?alt=json" ,

            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Myanmar2?alt=json" ,


            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/English2?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Math2?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Science2?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Moral2?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/LSkill2?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Social2?alt=json" ,

            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Physcial2?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Art2?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Music2?alt=json" ,
            "https://myanmarschool2023.blogspot.com/feeds/posts/default/-/Geography2?alt=json" ,









    };





    String[] titles={
            "All Posts",

            "All Lessons",

            "Lesson Plans",
            "Question Types",
            "Item/RubicType",

            "Teacher Guide",
            "English Eassay​s",
            "Myanmar Eassays",

            "Myanmar",
            "English",


            "Math",
            "Science",

            "Moral",
            "Life Skill",

            "Social",
            "Physcial",

            "Art",
            "Music",
            "Geography",




    };
    int current=0;





    @Override
    public void _Options_Menu_Click(MenuItem item)
    {
        int id=item.getItemId();


        if (id == R.id.post)
        {

            current = 0;
        }

        else

        if (id == R.id.lessonnew)
        {

            current = 1;
        }

        else

        if (id == R.id. lessonplan)
        {


            current = 2;
        }

        else

        if (id == R.id. qtype)
        {


            current = 3;
        }
        else
        if (id == R.id. itype)
        {


            current = 4;
        }

        else

        if (id == R.id.tguide)
        {


            current = 5;
        }



        else

        if (id == R.id.eeassay)
        {


            current = 6;
        }



        else

        if (id == R.id.meassay)
        {


            current = 7;
        }


        else


        if (id == R.id.mm)
        {


            current = 8;
        }


        else

        if (id == R.id.eng)
        {

            current = 9;
        }else

        if (id == R.id.mathnew)
        {

            current = 10;
        }else

        if (id == R.id.science)
        {

            current = 11;
        }else

        if (id == R.id.moral)
        {

            current = 12;
        }else

        if (id == R.id.lskill)
        {

            current = 13;
        }else


        if (id == R.id.social)
        {

            current = 14;
        }else
        if (id == R.id.physical)
        {

            current = 15;
        }else

        if (id == R.id.art)
        {

            current = 16;
        }else
        if (id == R.id.musicnew)
        {

            current = 17;
        }

        else
        if (id == R.id.geo)
        {

            current = 18;
        }


        refresh();
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

        return links[current];
    }
}


