package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;




	import android.text.*;
	import android.view.*;
	import java.util.*;
	import org.json.*;
	import android.widget.*;
    import android.content.*;
	import android.net.*;

	import androidx.appcompat.app.AlertDialog;

public class EbookTvCategories extends EbookTvIndexActivity
	{



		public void processJson(String inputJson){
			posts = new ArrayList<EbookCategoryItem>();
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
					if(jo2.getJSONObject("title").getString("$t").equals("Index1")){
						String content=jo2.getJSONObject("content").getString("$t");
						String orgjson=Html.fromHtml(content).toString();

						JSONObject obj=new JSONObject(orgjson);
						JSONArray jarr=obj.getJSONArray("category");
						for(int j=0;j<jarr.length();j++){
							EbookCategoryItem p=new EbookCategoryItem();
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
						loadP(obj.getString("p_img"),obj.getString("p_title"),obj.getString("p_subtitle"));

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
			//return "https://mytestmovies.blogspot.com/feeds/posts/default?alt=json";
			//  return "https://topaone-computer-basic.blogspot.com/feeds/posts/default?alt=json";

			return "https://mymoviestest1.blogspot.com/feeds/posts/default?alt=json";

		}


		private void showCredit(){
			final AlertDialog dialog = new AlertDialog.Builder(this).create();
			View parent_view = LayoutInflater.from(this).inflate(R.layout.ebook_credit_dia, null);
			dialog.setView(parent_view);
			dialog.setCancelable(false);
			dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			dialog.show();
// Finding Views inside dialog
			TextView tv_title = (TextView) dialog.findViewById(R.id.dialogTextView1);
			Button button = (Button) dialog.findViewById(R.id.dialogButton1);

			tv_title.setText("Kipon Cinemaတွင် ရှိ​ေသာ Kipon တစ်ခုစီသည် Facebook​ေပါ်မှ ကူးယူ​ြပီး တစ်ဆင့်ခံ​ြပထား​ြခင်း​ြဖစ်သည်။ အကယ်၍ ​ြပသသည်ကို မလက်ခံလှျင် ​ြဖုတ်ချ​ေပးပါမည်။"+"\n"+"Contact Phone: 09895411096"+"\n"+"Gmail: kaungmyatko2318@gmail.com");
			//	tv_title.setTypeface(getPyiDaungSuTypeface());
			button.setText("..​ြပီးဆံုးပါ​ြပီ..");
			//button.setTypeface(getPyiDaungSuTypeface());
			button.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View p1)
					{
						dialog.dismiss();
					}});

		}
		private void showContact(){
			final AlertDialog dialog = new AlertDialog.Builder(this).create();
			View parent_view = LayoutInflater.from(this).inflate(R.layout.ebook_contact_dia, null);
			dialog.setView(parent_view);
			dialog.setCancelable(false);
			dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			dialog.show();
// Finding Views inside dialog
			TextView tv_title = (TextView) dialog.findViewById(R.id.dialogTextView1);
			Button call = (Button) dialog.findViewById(R.id.dialogButton1);
			Button cancel= (Button) dialog.findViewById(R.id.dialogButton2);

			tv_title.setText("Kipon Cinema ကို အသံုး​ြပုသည့်အတွက် အထူး​ေကျးဇူးတင်ရှိပါသည်။ က​ေလးများ ရသမျိုးစံုခံစား​ြကည့်ရှုနိုင်​ြကပါ​ေစ....။ ကျွန်​ေတာ်ထံဆက်သွယ်ရန်"+"\n"+"Contact Phone: 09895411096"+"\n"+"Gmail: kaungmyatko2318@gmail.com");
			//tv_title.setTypeface(getPyiDaungSuTypeface());
			cancel.setText("..​ြပီးဆံုးပါ​ြပီ..");
			//button.setTypeface(getPyiDaungSuTypeface());
			cancel.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View p1)
					{
						dialog.dismiss();
					}});
			call.setText("Phone Call");
			//button.setTypeface(getPyiDaungSuTypeface());
			call.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View p1)
					{
						Intent call = new Intent(Intent.ACTION_DIAL);
						call.setData(Uri.parse("tel:"+"09895411096"));
						startActivity(call); 

						dialog.dismiss();
					}});

		}
		private void showAbout(){
			final AlertDialog dialog = new AlertDialog.Builder(this).create();
			View parent_view = LayoutInflater.from(this).inflate(R.layout.ebook_about_dia, null);
			dialog.setView(parent_view);
			dialog.setCancelable(false);
			dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			dialog.show();
// Finding Views inside dialog
			TextView tv_title = (TextView) dialog.findViewById(R.id.dialogTextView1);
			Button button = (Button) dialog.findViewById(R.id.dialogButton1);

			tv_title.setText("Kipon Cinemaသည် က​ေလးများ ရသစံုလင်စွာ​ြကည့်ရှုခံစားနိုင်ရန်အတွက် ဖန်တီးထားသည့် Applicationတစ်ခု​ြဖစ်ပါသည်။ အသံုး​ြပုသည့်သူအားလံုး စိတ်အ​ေမာ​ေတွ​ေ​ြဖ​ေ​ဖျာက်နိုင်​ြကပါ​ေစ....."+"\n"+"  ***Developer Mg Kaung Myat(DEC)***  "+"\n"+"Contact Phone: 09895411096"+"\n"+"Gmail: kaungmyatko2318@gmail.com");
			//	tv_title.setTypeface(getPyiDaungSuTypeface());
			button.setText("..​ြပီးဆံုးပါ​ြပီ..");
			//button.setTypeface(getPyiDaungSuTypeface());
			button.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View p1)
					{
						dialog.dismiss();
					}});

		}
	}
    

