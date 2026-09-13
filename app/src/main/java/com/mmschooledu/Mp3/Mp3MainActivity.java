package com.mmschooledu.Mp3;


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


public class Mp3MainActivity extends Mp3JSONFeedActivity
{






    String[] links={
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default?alt=json" ,
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/App?alt=json" ,

            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/kg?alt=json" ,

            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade1?alt=json" ,

            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade1?alt=json" ,
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade2?alt=json" ,
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade3?alt=json" ,
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade4?alt=json" ,
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade5?alt=json" ,
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade6?alt=json" ,

            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade7?alt=json" ,
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade8?alt=json" ,
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade9?alt=json" ,
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade10?alt=json" ,
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade11?alt=json" ,
            "https://myanmarschoolmp3.blogspot.com/feeds/posts/default/-/grade12?alt=json" ,



    };


    String[] titles={
            "All Lessons",
            "App Usage",

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



        if (id == R.id.lessonaudio)
        {


            current = 0;
        }else


        if (id == R.id.use)
        {


            current = 1;
        }
        else

        if (id == R.id.kg)
        {


            current = 2;
        }


        else

        if (id == R.id.grade1)
        {

            current = 3;
        }else

        if (id == R.id.grade2)
        {

            current = 4;
        }else

        if (id == R.id.grade3)
        {

            current = 5;
        }else

        if (id == R.id.grade4)
        {

            current = 6;
        }else

        if (id == R.id.grade5)
        {

            current = 7;
        }else


        if (id == R.id.grade6)
        {

            current = 8;
        }else
        if (id == R.id.grade7)
        {

            current = 9;
        }else

        if (id == R.id.grade8)
        {

            current = 10;
        }else
        if (id == R.id.grade9)
        {

            current = 11;
        }else

        if (id == R.id.grade10)
        {

            current = 12;
        }
        else

        if (id == R.id.grade11)
        {

            current = 13;
        }



        else

        if (id == R.id.grade12)
        {

            current = 14;
        }
		/*else
		 if (id == R.id.tree6)
		 {

		 current = 17;
		 }*/



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

	/*@Override
	 public String getFeedAddress()
	 {
	 return "http://www.bookworldnet.blogspot.com/feeds/posts/default?alt=json";
	 //return "http://tech-rg.blogspot.com/feeds/posts/default?alt=json";
	 }*/


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



