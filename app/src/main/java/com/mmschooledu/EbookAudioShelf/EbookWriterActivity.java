package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.view.*;
import java.util.*;
import org.json.*;
import android.widget.*;

public class EbookWriterActivity extends EbookWriterfeedActivity
{


    @Override
    public void _Options_Menu_Click(MenuItem item)
    {
        int id=item.getItemId();



    }

    public void processJson(String inputJson, String file){
        posts = new ArrayList<EbookWriterItem>();

        try
        {

			JSONArray jarr=new JSONArray(inputJson);

			for(int j=0;j<jarr.length();j++){
				EbookWriterItem p=new EbookWriterItem();
				p.img=(jarr.getJSONObject(j).getString("img"));
				p.link=(jarr.getJSONObject(j).getString("link"));

				addItem(p);
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

		String url="";
		//url="https://indexaty2021.blogspot.com/feeds/posts/default?alt=json";
		url="https://onlinebookstorereader.blogspot.com/feeds/posts/default?alt=json";


		return url;
	}
}

