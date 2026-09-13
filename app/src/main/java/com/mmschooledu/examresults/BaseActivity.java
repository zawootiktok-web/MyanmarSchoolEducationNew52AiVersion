package com.mmschooledu.examresults;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class BaseActivity extends AppCompatActivity
{
	private ProgressDialog pd;
	private SharedPreferences shp;
	private SharedPreferences.Editor editor;
	public List<String>allList;
	private AlertDialog ad;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		pd=new ProgressDialog(this);
		shp=getSharedPreferences("MyFile",MODE_PRIVATE);
		editor=shp.edit();
	}

	public void saveData(String s1,int i1){
		editor.putInt(s1,i1);
		editor.commit();
	}

	public void saveData(String s1,boolean i1){
		editor.putBoolean(s1,i1);
		editor.commit();
	}

	public void saveData(String s1,String i1){
		editor.putString(s1,i1);
		editor.commit();
	}

	public int $getInt(String s1){
		return shp.getInt(s1,0);
	}

	public boolean $getBoolean(String s1){
		return shp.getBoolean(s1,false);
	}

	public String $getString(String s1){
		return shp.getString(s1,"");
	}

	public String getDate(){
        String pattern = "dd-MM-yyyy";
        String dateInString =new SimpleDateFormat(pattern).format(new Date());  
        return dateInString;
    }

	public String getTime(){
        String pattern = "hh:mm:ss a";
        String dateInString =new SimpleDateFormat(pattern).format(new Date());  
        return dateInString;
    }

    public String $amount(String str1,String str2){
        String[]array=str1.split(",");
        String str3="";
        for(int i=0;i<array.length;i++){
            str3+=str2+",";
        }
        return str3.substring(0,str3.length()-1);  
    } 

	public String $balance(String str){
		String s1=str.replace("0","၀").replace("1","၁").replace("2","၂").replace("3","၃").replace("4","၄").replace("5","၅").replace("6","၆").replace("7","၇").replace("8","၈").replace("9","၉");
		return s1;
	}

	public String $email(String s1){
		//String s2=s1.toLowerCase();
		String s3=s1.trim().replaceAll("\\s","");
		return s3;
	}

	public String subString(String s1){
		String s="";
		
		return s;
	}
	public void shareText(String uriString){
		try{
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			sharingIntent.putExtra(Intent.EXTRA_TEXT,uriString );
			sharingIntent.setPackage("mate.bluetoothprint");
			//sharingIntent.setPackage("com.viber.voip");
			startActivity(sharingIntent);
			//com.noble.activity.printerperipage
			//cz.eetplus.print
		}catch (Exception e){
			Intent intent = new Intent(Intent.ACTION_VIEW); intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setData(Uri.parse("market://details?id=" + "mate.bluetoothprint"));
			startActivity(intent);
		}
	}

	public float txtSize(float f){
        return f * getResources().getDisplayMetrics().density;
    }

    public void textColorAnimation(TextView tv){
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(tv, "textColor",                                                                                                       
														Color.RED, Color.GREEN,Color.YELLOW,Color.BLUE,Color.CYAN,Color.BLACK,Color.LTGRAY,Color.DKGRAY,Color.MAGENTA,Color.RED,Color.WHITE);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setDuration(1500);
        colorAnim.setRepeatCount(Animation.INFINITE);
        colorAnim.start();
    }

    public void textScroll(TextView tv){
        tv.setSingleLine(true);
        tv.setHorizontallyScrolling(true);
        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv.setMarqueeRepeatLimit(-1);
        tv.setSelected(true);
    }

	public String versionName(){
		try{
			String versionName = this.getPackageManager()
				.getPackageInfo(this.getPackageName(), 0).versionName;
			return versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	public void showMsg(String title, String msg)
	{
		ad=new AlertDialog.Builder(this).create();
		ad.setTitle(title);
		ad.setMessage(msg);
		ad.setButton(
			AlertDialog.BUTTON_POSITIVE,
			"Ok", 
			new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					ad.dismiss();
				}
			});
		ad.show();
	}

	public void $toast(String msg){
		Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
	}

	public void showSnackbar(View view, String message)
    {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

	public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }

	public void showProgressDialog(String msg){
		pd.setMessage(msg);
		pd.show();
	}

	public void hideProgressDialog(){
		pd.hide();
	}

	public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
