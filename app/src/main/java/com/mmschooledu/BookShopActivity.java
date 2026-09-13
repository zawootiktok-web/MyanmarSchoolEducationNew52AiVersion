package com.mmschooledu;


import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;

import com.mmschooledu.Item.PostItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookShopActivity extends BShopJSONFeedActivity
{





    String[] links={
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Bshop?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Other?alt=json" ,

            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Kg?alt=json" ,


            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade1?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade2?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade3?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade4?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade5?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade6?alt=json" ,

            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade7?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade8?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade9?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade10?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade11?alt=json" ,
            "https://onlinemyanmarschoolbookshop.blogspot.com/feeds/posts/default/-/Grade12?alt=json" ,












    };




    String[] titles={
            "For Sale",
            "Book Shops",
            "Other Books",

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
            "Grade(13)",





    };
    int current=0;





    @Override
    public void _Options_Menu_Click(MenuItem item)
    {
        int id=item.getItemId();

        if (id == R.id.lessonshop)
        {

            current = 0;
        }

        else

        if (id == R.id.bshop)
        {


            current = 1;
        }

        if (id == R.id.obook)
        {


            current = 2;
        }

        else


        if (id == R.id.kg)
        {


            current = 3;
        }


        else

        if (id == R.id.grade1)
        {

            current = 4;
        }else

        if (id == R.id.grade2)
        {

            current = 5;
        }else

        if (id == R.id.grade3)
        {

            current = 6;
        }else

        if (id == R.id.grade4)
        {

            current = 7;
        }else

        if (id == R.id.grade5)
        {

            current = 8;
        }else


        if (id == R.id.grade6)
        {

            current = 9;
        }else
        if (id == R.id.grade7)
        {

            current = 10;
        }else

        if (id == R.id.grade8)
        {

            current = 11;
        }else
        if (id == R.id.grade9)
        {

            current = 12;
        }else

        if (id == R.id.grade10)
        {

            current = 13;
        }
        else

        if (id == R.id.grade11)
        {

            current = 14;
        }



        else

        if (id == R.id.grade12)
        {

            current = 14;
        }
		/*else
		 if (id == R.id.grade13)
		 {

		 current = 14;
		 }
		 */


        refresh();
    }

    private Intent Intent(String p0, Uri parse)
    {
        // TODO: Implement this method
        return null;
    }




    //Blogger
    public void processJson(String inputJson){
        posts = new ArrayList<>();
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
                    pi.thumbnailUrl= jo2.getJSONObject("media$thumbnail").getString("url");
                }catch(Exception ignored){
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


