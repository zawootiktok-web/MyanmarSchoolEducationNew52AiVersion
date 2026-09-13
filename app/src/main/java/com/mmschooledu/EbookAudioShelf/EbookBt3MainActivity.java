package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;
import android.view.*;

import java.util.*;
import org.json.*;

import android.*;


public class EbookBt3MainActivity extends EbookJSONFeedActivity
{





	String[] links={
		"https://onlinebookstoreaudiobook.blogspot.com/feeds/posts/default/-/LongStory?alt=json&start-index=1&max-results=150",
		"https://onlinebookstoreaudiobook.blogspot.com/feeds/posts/default/-/LongStory?alt=json&start-index=150&max-results=300",
		"https://onlinebookstoreaudiobook.blogspot.com/feeds/posts/default/-/LongStory?alt=json&start-index=300&max-results=450",
		"https://onlinebookstoreaudiobook.blogspot.com/feeds/posts/default/-/LongStory?alt=json&start-index=450&max-results=600",
		"https://onlinebookstoreaudiobook.blogspot.com/feeds/posts/default/-/LongStory?alt=json&start-index=600&max-results=750",
		






	};





	String[] titles={
		"All Posts",
		"Page_2",
		"Page_3",
		"Page_4",
		"Page_5",





	};
	int current=0;





	@Override
	public void _Options_Menu_Click(MenuItem item)
	{
		int id=item.getItemId();


		if (id == R.id.pg)
		{

			current = 0;
		}

		else

		if (id == R.id.pg2)
		{

			current = 1;
		}

		else

		if (id == R.id. pg3)
		{


			current = 2;
		}

		else

		if (id == R.id. pg4)
		{


			current = 3;
		}
		else
		if (id == R.id. pg5)
		{


			current = 4;
		}


		refresh();
	}





	//Blogger
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
				EbookPostItem pi=new EbookPostItem();
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


