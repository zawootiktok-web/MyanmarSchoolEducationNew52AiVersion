package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;


import android.text.*;
import android.view.*;
import java.util.*;
import org.json.*;
import android.widget.*;

/*import com.surveyor.manual.google.android.gms.ads.reward.RewardItem;
 import com.surveyor.manual.google.android.gms.ads.reward.RewardedVideoAdListener;
 import com.surveyor.manual.google.android.gms.ads.reward.RewardedVideoAd;
 */
public class EbookEBookDetailActivity extends EbookEBookIndexFeedActivity
{




	public void _Options_Menu_Click(MenuItem item)
	{



	}

	/*  public void processTitleImg(String inputJson){

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
	 if(jo2.getJSONObject("title").getString("$t").equals("Index")){
	 String content=jo2.getJSONObject("content").getString("$t");
	 String orgjson=Html.fromHtml(content).toString();

	 JSONObject obj=new JSONObject(orgjson);
	 final   String img1=obj.getString("img1");
	 final   String img2=obj.getString("img2");
	 final   String img3=obj.getString("img3");
	 final   String img4=obj.getString("img4");
	 final   String img5=obj.getString("img5");


	 showTitleImg(img1,img2,img3,img4,img5);

	 }

	 }
	 }
	 catch (JSONException e)
	 {
	 Toast.makeText(this,e.toString(),1).show();
	 }
	 }*/

	/*
	 public void processRecent(String inputJson, String category){
	 r_posts = new ArrayList<EbookRecentItem>();
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
	 if(jo2.getJSONObject("title").getString("$t").equals("Index")){
	 String content=jo2.getJSONObject("content").getString("$t");
	 String orgjson=Html.fromHtml(content).toString();

	 JSONObject obj=new JSONObject(orgjson);
	 JSONArray jarr=obj.getJSONArray(category);
	 for(int j=0;j<jarr.length();j++){
	 EbookRecentItem p=new EbookRecentItem();
	 p.thumbnail=(jarr.getJSONObject(j).getString("thumnail"));
	 p.bname=(jarr.getJSONObject(j).getString("bname"));
	 p.wname=(jarr.getJSONObject(j).getString("wname"));
	 p.res=(jarr.getJSONObject(j).getString("res"));
	 p.category=(jarr.getJSONObject(j).getString("category"));
	 addItem(p);
	 }

	 }

	 }
	 }
	 catch (JSONException e)
	 {
	 Toast.makeText(this,e.toString(),1).show();
	 }
	 }
	 public void processSuggested(String inputJson, String category){
	 s_posts = new ArrayList<SuggestedItem>();
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
	 if(jo2.getJSONObject("title").getString("$t").equals("Index")){
	 String content=jo2.getJSONObject("content").getString("$t");
	 String orgjson=Html.fromHtml(content).toString();

	 JSONObject obj=new JSONObject(orgjson);
	 JSONArray jarr=obj.getJSONArray(category);
	 for(int j=0;j<jarr.length();j++){
	 SuggestedItem p=new SuggestedItem();
	 p.thumbnail=(jarr.getJSONObject(j).getString("thumnail"));
	 p.bname=(jarr.getJSONObject(j).getString("bname"));
	 p.wname=(jarr.getJSONObject(j).getString("wname"));
	 p.res=(jarr.getJSONObject(j).getString("res"));
	 p.category=(jarr.getJSONObject(j).getString("category"));
	 addItem(p);
	 }

	 }

	 }
	 }
	 catch (JSONException e)
	 {
	 Toast.makeText(this,e.toString(),1).show();
	 }
	 }
	 public void processChild(String inputJson, String category){
	 c_posts = new ArrayList<ChildItem>();
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
	 if(jo2.getJSONObject("title").getString("$t").equals("Index")){
	 String content=jo2.getJSONObject("content").getString("$t");
	 String orgjson=Html.fromHtml(content).toString();

	 JSONObject obj=new JSONObject(orgjson);
	 JSONArray jarr=obj.getJSONArray(category);
	 for(int j=0;j<jarr.length();j++){
	 ChildItem p=new ChildItem();
	 p.thumbnail=(jarr.getJSONObject(j).getString("thumnail"));
	 p.bname=(jarr.getJSONObject(j).getString("bname"));
	 p.wname=(jarr.getJSONObject(j).getString("wname"));
	 p.res=(jarr.getJSONObject(j).getString("res"));
	 p.category=(jarr.getJSONObject(j).getString("category"));
	 addItem(p);
	 }

	 }

	 }
	 }
	 catch (JSONException e)
	 {
	 Toast.makeText(this,e.toString(),1).show();
	 }
	 }


	 */
    public void processYoung(String inputJson, String category){
        y_posts = new ArrayList<EbookVideoItem>();
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
                if(jo2.getJSONObject("title").getString("$t").equals("survey")){
                    String content=jo2.getJSONObject("content").getString("$t");
                    String orgjson=Html.fromHtml(content).toString();

                    JSONObject obj=new JSONObject(orgjson);
                    JSONArray jarr=obj.getJSONArray(category);
                    for(int j=0;j<jarr.length();j++){
                        EbookVideoItem p=new EbookVideoItem();
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

                }

            }
        }
        catch (JSONException e)
        {
            Toast.makeText(this,e.toString(),1).show();
        }
    }
	@Override
	public String getFeedAddress()
	{

		//return "https://ayetharyar.blogspot.com/feeds/posts/default?alt=json";
		//return "https://mymoviestest1.blogspot.com/feeds/posts/default?alt=json";

		return "https://surveyormanualbookchannel.blogspot.com/feeds/posts/default?alt=json";

	}






}
