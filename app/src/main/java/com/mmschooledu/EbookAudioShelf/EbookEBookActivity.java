package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.view.*;
import java.util.*;
import org.json.*;
import android.widget.*;

public class EbookEBookActivity extends EbookEBookfeedActivity
{



    @Override
    public void _Options_Menu_Click(MenuItem item)
    {
        int id=item.getItemId();



    }

    public void processJson(String inputJson){
		posts = new ArrayList<EbookBItem>();

		try
        {		
			JSONArray jarr_k=new JSONArray(inputJson);
			for(int j=0;j<jarr_k.length();j++){
				EbookBItem p=new EbookBItem();
				p.thumbnail=(jarr_k.getJSONObject(j).getString("thumbnail"));
				p.bname=(jarr_k.getJSONObject(j).getString("bname"));
				p.bimg=(jarr_k.getJSONObject(j).getString("bimg"));
				p.wname=(jarr_k.getJSONObject(j).getString("wname"));
				p.wlink=(jarr_k.getJSONObject(j).getString("wlink"));
				p.info=(jarr_k.getJSONObject(j).getString("info"));
				p.link=(jarr_k.getJSONObject(j).getString("link"));
				addItem(p);
			}


			/*
			 if(jo2.getJSONObject("title").getString("$t").equals("slide.json")){
			 String content=jo2.getJSONObject("content").getString("$t");
			 String orgjson=Html.fromHtml(content).toString();
			 JSONObject obj=new JSONObject(orgjson);      	
			 showTitleImg(obj.getString("img1"),obj.getString("img2"),obj.getString("img3"),obj.getString("img4"),obj.getString("img5"),obj.getString("img6"),obj.getString("img7"));
			 }
			 */



		}


        catch (JSONException e)
        {
			Toast.makeText(this,"Error : Try again"+e.toString(),3).show();
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

		String url="https://"+"aty2021"+".blogspot.com/feeds/posts/default?alt=json";
        return url;
	}
}

