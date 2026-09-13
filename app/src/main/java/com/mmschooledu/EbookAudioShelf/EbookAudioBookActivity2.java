package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.text.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import org.json.*;

public class EbookAudioBookActivity2 extends EbookAudioJSONFeedActivity2
{


	/*String[] links={
	 "https://mymoviestest1.blogspot.com/feeds/posts/default?alt=json",
	 "https://mymoviestest.blogspot.com/feeds/posts/default?alt=json",
	 "https://mymoviestest1.blogspot.com/feeds/posts/default?alt=json",


	 "Books3",
	 "Books4"
	 };

	String[] titles={
		"Index",
		"young",
		"English",
		"Novel"
	};*/

    int current=0;

    @Override
    public void _Options_Menu_Click(MenuItem item)
    {
        int id=item.getItemId();
        if (id == R.id.menu_about)
        {
            MsgBox("About",
                    "This project is created by Purple Snake for the members of AIDE Networking group.\n\n" +

                            "😎");
        }
        else if (id == R.id.navigation_item_contact)
        {
            //open contact Activity
        }
		/*else if (id == R.id.navigation_item_facebook)
		{
			//open facebook
		}
		else
		{
			if (id == R.id.menu_graphics)
			{
				current = 0;
			}
			else if (id == R.id.menu_programming)
			{
				current = 1;
			}
			else if (id == R.id.menu_english)
			{
				current = 2;
			}
			else if (id == R.id.menu_novel)
			{
				current = 3;
			}*/

        refresh();
    }




    public void processJson(String inputJson){
        posts = new ArrayList<EbookPostItem>();
        String input="";
        switch(currentFont){
            case FONT_ZAWGYI:
                input=EbookFontConverter.uni2zg(inputJson);
                break;
            case FONT_UNI:
                input=EbookFontConverter.zg2uni(inputJson);
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
                jo2=ja.getJSONObject(i);
                if(jo2.getJSONObject("title").getString("$t").equals(category)){
                    String content=jo2.getJSONObject("content").getString("$t");
                    String orgjson=Html.fromHtml(content).toString();

                    JSONObject obj=new JSONObject(orgjson);
                    JSONArray jarr=obj.getJSONArray("category");
                    for(int j=0;j<jarr.length();j++){
                        EbookPostItem p=new EbookPostItem();

                        p.thumbnail=(jarr.getJSONObject(j).getString("thumbnail"));
                        p.bname=(jarr.getJSONObject(j).getString("bname"));
                        p.wname=(jarr.getJSONObject(j).getString("wname"));
                        p.link=(jarr.getJSONObject(j).getString("link"));
                        p.category=(jarr.getJSONObject(j).getString("category"));
                        p.wname=(jarr.getJSONObject(j).getString("mb1"));
                        p.wname=(jarr.getJSONObject(j).getString("mb2"));
                        p.wname=(jarr.getJSONObject(j).getString("time"));


                        addItem(p);

                    }
                    //loadP(obj.getString("p_img"),obj.getString("p_title"),obj.getString("p_subtitle"));

                }

            }
        }
        catch (JSONException e)
        {
            Toast.makeText(this,e.toString(),1).show();
        }

    }
    @Override
    public boolean useGridLayout()
    {
        return true;
    }

    @Override
    public String getFeedAddress()
    {
        String bname=getIntent().getExtras()
                .get("bname").toString();

        getSupportActionBar().setTitle(bname+"");

        //return "https://mytestmovies.blogspot.com/feeds/posts/default?alt=json";
        //  return "https://topaone-computer-basic.blogspot.com/feeds/posts/default?alt=json";

        //return "https://mymoviestest1.blogspot.com/feeds/posts/default?alt=json";
        //return "https://surveyormanualbooks.blogspot.com/feeds/posts/default?alt=json";


        //setTitle(titles[current]);
        setTitle(title);

        //return links[current];
        //return  "https://onlinebookstorereadernew.blogspot.com/feeds/posts/default?alt=json";
        //return  "https://onlineaudiobook2023.blogspot.com/feeds/posts/default?alt=json";


        return url;

    }}

