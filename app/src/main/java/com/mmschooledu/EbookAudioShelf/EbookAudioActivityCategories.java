package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;


	import android.text.*;
	import android.view.*;
	import java.util.*;
	import org.json.*;

public class EbookAudioActivityCategories extends EbookAudiofeedActivity
	{



		@Override
		public void _Options_Menu_Click(MenuItem item)
		{
			int id=item.getItemId();



		}

		public void processJson(String inputJson, String title){
			posts = new ArrayList<EbookBookItem>();
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
					if(jo2.getJSONObject("title").getString("$t").equals(title)){
						String content=jo2.getJSONObject("content").getString("$t");
						String orgjson=Html.fromHtml(content).toString();

						JSONObject obj=new JSONObject(orgjson);
						JSONArray jarr=obj.getJSONArray("books");
						for(int j=0;j<jarr.length();j++){
							EbookBookItem p=new EbookBookItem();
							p.thumbnail=(jarr.getJSONObject(j).getString("thumbnail"));
							p.bname=(jarr.getJSONObject(j).getString("bname"));
							p.wname=(jarr.getJSONObject(j).getString("wname"));
							p.link=(jarr.getJSONObject(j).getString("link"));
							p.category=(jarr.getJSONObject(j).getString("category"));
							p.mb1=(jarr.getJSONObject(j).getString("mb1"));
							p.mb2=(jarr.getJSONObject(j).getString("mb2"));
							p.time=(jarr.getJSONObject(j).getString("time"));



							addItem(p);
                        }
					}
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
			String bname=getIntent().getExtras()
				.get("bname").toString();
			//.title.setTitleText(title);	

		/*	title=getIntent().getExtras()
				.get("title").toString();*/
			/*category=getIntent().getExtras()
			 .get("category").toString();*/
			getSupportActionBar().setTitle(bname+"");
			// return "https://ayetharyar.blogspot.com/feeds/posts/default?alt=json";
			//return "https://mytestmovies.blogspot.com/feeds/posts/default?alt=json";

			//return "https://onlineaudiobook2023.blogspot.com/feeds/posts/default?alt=json";
			
			setTitle(title);
			return url;
		}

		//private void setTitleText(String title) {
		//}
	}
  

