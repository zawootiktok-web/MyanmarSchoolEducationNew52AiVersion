package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.view.*;
import java.util.*;
import org.json.*;
import android.widget.*;

public class EbookCategoryActivity extends EbookCategoryfeedActivity
{


    @Override
    public void _Options_Menu_Click(MenuItem item)
    {
        int id=item.getItemId();



    }

    public void processJson(String inputJson, String file){
        posts = new ArrayList<EbookCategoryItem>();
        try
        {

            JSONArray jarr_k=new JSONArray(inputJson);
			for(int j=0;j<jarr_k.length();j++){
				EbookCategoryItem p=new EbookCategoryItem();
				p.title=(jarr_k.getJSONObject(j).getString("title"));
				p.count=(jarr_k.getJSONObject(j).getString("count"));	
				p.link=(jarr_k.getJSONObject(j).getString("link"));
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

