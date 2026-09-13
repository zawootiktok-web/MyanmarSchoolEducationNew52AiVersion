package com.mmschooledu.DonateBook;

import android.text.Html;
import android.view.MenuItem;
import android.widget.Toast;

import com.mmschooledu.FontConverter;
import com.mmschooledu.Item.DonatePostItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DonateBookActivity extends DonateJSONFeedActivity
{

	@Override
	public void _Options_Menu_Click(MenuItem item) {
	}


	public void processJson(String inputJson){
		posts = new ArrayList<DonatePostItem>();
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
                jo2=ja.getJSONObject(i);
                if(jo2.getJSONObject("title").getString("$t").equals("Index")){
                    String content=jo2.getJSONObject("content").getString("$t");
                    String orgjson=Html.fromHtml(content).toString();

                    JSONObject obj=new JSONObject(orgjson);
                    JSONArray jarr=obj.getJSONArray("category");
                    for(int j=0;j<jarr.length();j++){
                        DonatePostItem p=new DonatePostItem();
						//p.thumbnailUrl=(jarr.getJSONObject(j).getString("thumbnail"));
						//p.title=(jarr.getJSONObject(j).getString("title"));
						//p.link=(jarr.getJSONObject(j).getString("link"));
						//p.title=(jarr.getJSONObject(j).getString("title"));

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

		return "https://onlineebooklibary.blogspot.com/feeds/posts/default?alt=json";



	}}
