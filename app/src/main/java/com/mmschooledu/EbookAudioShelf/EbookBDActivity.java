package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.view.*;
import java.util.*;
import org.json.*;

public class EbookBDActivity extends EbookBookDetailActivity2
{


    @Override
    public void _Options_Menu_Click(MenuItem item)
    {
        int id=item.getItemId();



    }

    public void processJson(String inputJson, String file){
        r_posts = new ArrayList<EbookBItem>();

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

        }
        catch (JSONException e)
        {
            //Toast.makeText(this,e.toString(),1).show();
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

// String url="https://"+"aty2021"+".blogspot.com/feeds/posts/default?alt=json";

		String url="https://"+"onlinebookstorereader"+".blogspot.com/feeds/posts/default?alt=json";

		//url="https://onlinebookstorereader.blogspot.com/feeds/posts/default?alt=json";


		return url;
    }
}

