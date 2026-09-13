package com.mmschooledu.QandAnswer;

import android.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mmschooledu.FontConverter;
import com.mmschooledu.Item.CategoryItem;
import com.mmschooledu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QandAActivity extends QandAIndexActivity
{



	public void processJson(String inputJson){
		posts = new ArrayList<CategoryItem>();
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
                if(jo2.getJSONObject("title").getString("$t").equals("QuestionBook Index")){
                    String content=jo2.getJSONObject("content").getString("$t");
                    String orgjson=Html.fromHtml(content).toString();

                    JSONObject obj=new JSONObject(orgjson);
                    JSONArray jarr=obj.getJSONArray("category");
                    for(int j=0;j<jarr.length();j++){
                        CategoryItem p=new CategoryItem();
								p.thumbnail=(jarr.getJSONObject(j).getString("thumbnail"));
                        p.bname=(jarr.getJSONObject(j).getString("bname"));
						//  p.wname=(jarr.getJSONObject(j).getString("wname"));
                        p.link=(jarr.getJSONObject(j).getString("link"));
						p.category=(jarr.getJSONObject(j).getString("category"));
						//p.wname=(jarr.getJSONObject(j).getString("mb1"));
						//p.wname=(jarr.getJSONObject(j).getString("mb2"));
						//p.wname=(jarr.getJSONObject(j).getString("time"));


						addItem(p);

                    }
					loadP(obj.getString("p_img"),obj.getString("p_title"),obj.getString("p_subtitle"));

                }

            }
        }
        catch (JSONException e)
        {
           //Toast.makeText(this,e.toString(),1).show();
			showNoInternet();
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
			return "https://myanmarschoolquestionbook.blogspot.com/feeds/posts/default?alt=json";
	}

	private void showNoInternet (){
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		View parent_view = LayoutInflater.from(this).inflate(R.layout.nointernet_dia, null);
		dialog.setView(parent_view);
		dialog.setCancelable(false);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.show();

		// Finding Views inside dialog
		TextView tv_title = dialog.findViewById(R.id.dialogTextView1);
		Button button = dialog.findViewById(R.id.dialogButton1);


		tv_title.setText("အင်တာနက်လိုင်း\u200Bနှေး\u200Bနေပါသည်\n" +
				"Wifi မသုံးပဲ Phone  Sim card internet ဖြင့်သုံးပါ \n သို့မဟုတ်\n  Mytel Sim ဖြစ်လျှင် ဗွီပီအမ် ခံပြီးမှပြန်ဝင်ပါ \n " +
				"သို့မဟုတ် \n Phone Screen ကို လက်ဖြင့်ပွတ်ဆွဲပြီး refresh လုပ်\u200Bပေးပါ");

		button.setText("..ဟုတ်ကကဲ့..");

		button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View p1)
			{
				dialog.dismiss();
			}});
	}}




	
