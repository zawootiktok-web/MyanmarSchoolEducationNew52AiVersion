package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;
import android.content.*;
import android.content.res.*;
import android.net.*;
import android.os.*;

import android.view.*;
import android.widget.*;
import java.util.*;

import android.app.AlertDialog;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.graphics.*;
import android.content.pm.*;
import android.view.View.*;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.startapp.sdk.adsbase.StartAppAd;

import android.content.Intent;

import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.Manifest;
import android.os.Build;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.pm.PackageManager;



public abstract class EbookIndexFeedActivity extends AppCompatActivity
{
	//AdView adView;
	//InterstitialAd interstitialAd;
	TextView text1a,text2a,text3a,text8a;

	TextView text001,text002,text,text01,text02,text2;
	TextView text03,text07,text3,text7;
    
  /* // private Button mBannerButton;
  //  private LinearLayout mBanner;
private String unityGameID = "123456";

  //  private String unityGameID = "4525185";//4525185//4432183
 private Boolean testMode = false;

	//private Boolean testMode = true;
    private String InterID = "Interstitial_Android";
   // private String RewardID = "Rewarded_Android";
	*/
	//Interstitial_Android
	public abstract void processTitleImg(String jsonString);
    public abstract String getFeedAddress();
	public abstract void _Options_Menu_Click(MenuItem item);
	private SwipeRefreshLayout mSwipeLayout;
	RecyclerView dhamma_rv,audiobookshelf_rv,music_rv,book_rv;

	boolean online=true;
	Toolbar tb;
	String currentLink="";
	final int FONT_ZAWGYI=1;
	final int FONT_UNI=2;
	final int FONT_NONE=0;
	int currentFont=FONT_NONE;

	DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private NavigationView navigationView;
	String FACEBOOK_URL = "https://www.facebook.com/%E1%80%A1%E1%80%B1%E1%80%B8%E1%80%9E%E1%80%AC%E1%80%9A%E1%80%AC-%E1%80%85%E1%80%AC%E1%80%80%E1%80%BC%E1%80%8A%E1%80%BA%E1%80%B7%E1%80%90%E1%80%AD%E1%80%AF%E1%80%80%E1%80%BA-102387168438840/"; 
	String FACEBOOK_PROFILE_ID = "102387168438840";
	Handler handler = new Handler();
	Runnable runnable;
    int currentImg=0;
    int maxImg=4;
    ImageView title_img;
    LinearLayout pre,next;
    TextView position,video_btads;
	Button book_bt,dhamma_bt,audiobookshelf_bt,music_bt,detective_bt,horror_bt,cassete_bt,button1,porpularmusic_bt,button2,button3,button4,button5,button6,button7,button8;
	int bookCount=0;
	int ItemClick=0;
    //AdView adview0,adview1,adview2;
	//AdRequest req0,req1,req2;
    //InterstitialAd interstitial;

	public abstract void processDhamma(String jsonString, String cateogry);
    DhammaAdapter dhamma_adapter;
    List<EbookDhammaItem> d_posts,d_filteredposts;
	
	
    public abstract void processAudioBookshelf(String jsonString, String cateogry);
    AudioBookshelfAdapter audioBookshelf_adapter;
    List<EbookAudioBookshelfItem> a_posts,a_filteredposts;

    public abstract void processMusic(String jsonString, String cateogry);
MusicAdapter music_adapter;
    List<EbookMusicItem> m_posts,m_filteredposts;

//    public abstract void processDetectiveStory(String jsonString, String cateogry);
//    DetectiveStoryAdapter detectivestory_adapter;
//    List<EbookDetectiveStoryItem> de_posts,de_filteredposts;

//	public abstract void processHorrorStory(String jsonString, String cateogry);
////    HorrorStoryAdapter horror_adapter;
    // List<EbookHorrorItem> horror_posts,h_filteredposts;

	
//	public abstract void processCassette(String jsonString, String cateogry);
    //CassetteAdapter cassette_adapter;
    //List<EbookCassetteItem> ca_posts,ca_filteredposts;
	
//	public abstract void processPorpularMusic(String jsonString, String cateogry);
//    PorpularMusicAdapter porpularmusic_adapter;
//	List<EbookPorpularMusicItem> porpularmusic_posts,porpularmusic_filteredposts;
	
	
	public abstract void processBookStory(String jsonString, String cateogry);
    BookStoryAdapter book_adapter;
	List<EbookBookItem> b_posts,b_filteredposts;

	
	
 //   CardView writer_card,category_card,ads_card,book_card;
	String about,developer;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

		setContentView(R.layout.ebook_activity_main);
		requestAppPermissions();
		
		tb=(Toolbar)findViewById(R.id.nnl_toolbar);
		setSupportActionBar(tb);
		//tb.setLogo(R.drawable.ebook_apk);
        tb.setTitle(" KG AudioShelf ");
		navigationView = (NavigationView) findViewById(R.id.nnl_navigation_view);

        title_img=(ImageView) findViewById(R.id.title_img);
        pre=(LinearLayout) findViewById(R.id.pre_l);
        next=(LinearLayout) findViewById(R.id.next_l);            
        position=(TextView) findViewById(R.id.position); 
		//video_btads=(TextView) findViewById(R.id.video_btads);            
		
        dhamma_bt=(Button) findViewById(R.id.dhama_bt);
		audiobookshelf_bt=(Button) findViewById(R.id.shortstory_bt);
		music_bt=(Button) findViewById(R.id.longstory_bt);
//        detective_bt=(Button) findViewById(R.id.detective_bt);
//		horror_bt=(Button) findViewById(R.id.horror_bt);
//		cassete_bt=(Button) findViewById(R.id.cassete_bt);
//		porpularmusic_bt=(Button) findViewById(R.id.porpularmusic_bt);
		book_bt=(Button) findViewById(R.id.book_bt);
		
		button1=(Button) findViewById(R.id.button1);            
		button2=(Button) findViewById(R.id.button2);            
		
		button3=(Button) findViewById(R.id.button3);            
//		button4=(Button) findViewById(R.id.button4);
//button5=(Button) findViewById(R.id.button5);
//		button6=(Button) findViewById(R.id.button6);
//		button7=(Button) findViewById(R.id.button7);
		button8=(Button) findViewById(R.id.button8);     
		/*button9=(Button) findViewById(R.id.button9);            
		button10=(Button) findViewById(R.id.button10);            
		button11=(Button) findViewById(R.id.button11);            
		button12=(Button) findViewById(R.id.button12); */

		
		
		
		text1a = (TextView) findViewById(R.id.text1a);
		text2a = (TextView) findViewById(R.id.text2a);
		text3a = (TextView) findViewById(R.id.text3a);
//		text4a = (TextView) findViewById(R.id.text4a);
//		text5a = (TextView) findViewById(R.id.text5a);
//		text6a = (TextView) findViewById(R.id.text6a);
//		text7a = (TextView) findViewById(R.id.text7a);
		text8a= (TextView) findViewById(R.id.text8a);
		
		
		text001 = (TextView) findViewById(R.id.text001);
		text002 = (TextView) findViewById(R.id.text002);
		
		text01 = (TextView) findViewById(R.id.text01);
		text02 = (TextView) findViewById(R.id.text02);
		text = (TextView) findViewById(R.id.text);
		text2 = (TextView) findViewById(R.id.text2);
		
		
		text03 = (TextView) findViewById(R.id.text03);
//		text04 = (TextView) findViewById(R.id.text04);
//		text3 = (TextView) findViewById(R.id.text3);
//		text4 = (TextView) findViewById(R.id.text4);
//
//		text05 = (TextView) findViewById(R.id.text05);
//		text06 = (TextView) findViewById(R.id.text06);
//		text07 = (TextView) findViewById(R.id.text07);
//		text5= (TextView) findViewById(R.id.text5);
//		text6 = (TextView) findViewById(R.id.text6);
		text3 = (TextView) findViewById(R.id.text3);
		
        mDrawerLayout = (DrawerLayout) findViewById(R.id.nnl_drawer_layout);
      //  adview0=(AdView) findViewById(R.id.adView_0); 
      //  adview1=(AdView) findViewById(R.id.adView_1); 
        //adview2=(AdView) findViewById(R.id.adView_2); 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setupNV();
		
		
				
		SharedPreferences prefs = getSharedPreferences("translate", MODE_PRIVATE); 
		
		int id1a = prefs.getInt("id", 0);
		if(id1a==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.ENGLISH,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
				getString(R.string.ebook_dhama));

            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text1a.setText(translatedText);

					}

					@Override
					public void onFailure(String ErrorText) {

					}
				});


		}else{
			text1a.setText(getString(R.string.ebook_dhama));

		}
		
		
		
		int id2a = prefs.getInt("id", 0);
		if(id2a==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
			//	EbookLanguage.MYANMAR,
				
				getString(R.string.ebook_shortstory));

            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text2a.setText(translatedText);

					}

					@Override
					public void onFailure(String ErrorText) {

					}
				});


		}else{
			text2a.setText(getString(R.string.ebook_shortstory));

		}
		
		
		int id3a = prefs.getInt("id", 0);
		if(id3a==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
				getString(R.string.ebook_longstory));

            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text3a.setText(translatedText);

					}

					@Override
					public void onFailure(String ErrorText) {

					}
				});


		}else{
			text3a.setText(getString(R.string.ebook_longstory));

		}
		
//		int id4a = prefs.getInt("id", 0);
//		if(id4a==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.detectivestory));
//
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text4a.setText(translatedText);
//
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//
//		}else{
//			text4a.setText(getString(R.string.detectivestory));
//
//		}
//
//		int id5a = prefs.getInt("id", 0);
//		if(id5a==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.ebook_horrorstory));
//
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text5a.setText(translatedText);
//
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//
//		}else{
//			text5a.setText(getString(R.string.ebook_horrorstory));
//
//		}
//
//		int id6a = prefs.getInt("id", 0);
//		if(id6a==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.cassettestory));
//
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text6a.setText(translatedText);
//
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//
//		}else{
//			text6a.setText(getString(R.string.cassettestory));
//
//		}
		
//		int id7a = prefs.getInt("id", 0);
//		if(id7a==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.ebook_bookforyou));
//
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text7a.setText(translatedText);
//
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//
//		}else{
//			text7a.setText(getString(R.string.ebook_popularmusic));
//
//		}
		
		int id8a = prefs.getInt("id", 0);
		if(id8a==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
				getString(R.string.ebook_bookforyou));

            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text8a.setText(translatedText);

					}

					@Override
					public void onFailure(String ErrorText) {

					}
				});


		}else{
			text8a.setText(getString(R.string.ebook_bookforyou));

		}
		
		
		
		
		
		int id001 = prefs.getInt("id", 0);
		if(id001==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
				getString(R.string.ebook_more));
			
            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text001.setText(translatedText);
							}

					@Override
					public void onFailure(String ErrorText) {

					}
				});


		}else{
			text001.setText(getString(R.string.ebook_more));
			/*	text2.setText(getString(R.string.text2));
			 text3.setText(getString(R.string.text3));
			 text4.setText(getString(R.string.text4));
			 text5.setText(getString(R.string.text5));
			 text6.setText(getString(R.string.text6));*/

		}

		int id002 = prefs.getInt("id", 0);
		if(id002==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
				getString(R.string.ebook_dhama));
            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text002.setText(translatedText);
					}

					@Override
					public void onFailure(String ErrorText) {

					}
				});

		}else{
			text002.setText(getString(R.string.ebook_dhama));
		}
		
		
		
		
		
		int id = prefs.getInt("id", 0);
		if(id==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
				getString(R.string.ebook_more));
		
            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text.setText(translatedText);
					
					}

					@Override
					public void onFailure(String ErrorText) {

					}
				});


		}else{
			text.setText(getString(R.string.ebook_more));
		
		}

		int id01 = prefs.getInt("id", 0);
		if(id==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
				getString(R.string.ebook_shortstory));
            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text01.setText(translatedText);
					}

					@Override
					public void onFailure(String ErrorText) {

					}
				});

		}else{
			text01.setText(getString(R.string.ebook_shortstory));
		}


		int id02 = prefs.getInt("id", 0);
		if(id==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
				getString(R.string.ebook_longstory));
            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text02.setText(translatedText);
					}

					@Override
					public void onFailure(String ErrorText) {

					}
				});

		}else{
			text02.setText(getString(R.string.ebook_longstory));
		}
		
		int id2 = prefs.getInt("id", 0);
		if(id==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
				getString(R.string.ebook_more));
            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text2.setText(translatedText);
					}

					@Override
					public void onFailure(String ErrorText) {

					}
				});

		}else{
			text2.setText(getString(R.string.ebook_more));
		}
		
		
		
//
//		int id3 = prefs.getInt("id", 0);
//		if(id==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.ebook_more));
//
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text3.setText(translatedText);
//
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//
//		}else{
//			text3.setText(getString(R.string.ebook_more));
//
//		}
//
//		int id03 = prefs.getInt("id", 0);
//		if(id==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.detectivestory));
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text03.setText(translatedText);
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//		}else{
//			text03.setText(getString(R.string.detectivestory));
//		}
//
//
//		int id04 = prefs.getInt("id", 0);
//		if(id==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.ebook_horrorstory));
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text04.setText(translatedText);
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//		}else{
//			text04.setText(getString(R.string.ebook_horrorstory));
//		}
//
//		int id4 = prefs.getInt("id", 0);
//		if(id==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.ebook_more));
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text4.setText(translatedText);
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//		}else{
//			text4.setText(getString(R.string.ebook_more));
//		}
//
//
//
//		int id5 = prefs.getInt("id", 0);
//		if(id==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.ebook_more));
//
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text5.setText(translatedText);
//
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//
//		}else{
//			text5.setText(getString(R.string.ebook_more));
//
//		}
//
//		int id05 = prefs.getInt("id", 0);
//		if(id==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.cassettestory));
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text05.setText(translatedText);
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//		}else{
//			text05.setText(getString(R.string.cassettestory));
//		}
//
//
//		int id06= prefs.getInt("id", 0);
//		if(id==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.ebook_bookforyou));
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text06.setText(translatedText);
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//		}else{
//			text06.setText(getString(R.string.ebook_popularmusic));
//		}
//
//		int id6= prefs.getInt("id", 0);
//		if(id==0){
//			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
//				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
//				EbookLanguage.BURMESE,         //Target EbookLanguage
//				getString(R.string.ebook_more));
//            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
//					@Override
//					public void onSuccess(String translatedText) {
//						text6.setText(translatedText);
//					}
//
//					@Override
//					public void onFailure(String ErrorText) {
//
//					}
//				});
//
//		}else{
//			text6.setText(getString(R.string.ebook_more));
//		}
//
		
		int id03= prefs.getInt("id", 0);
		if(id==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
				getString(R.string.ebook_bookforyou));
            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text03.setText(translatedText);
					}

					@Override
					public void onFailure(String ErrorText) {

					}
				});

		}else{
			text03.setText(getString(R.string.ebook_bookforyou));
		}

	int id3= prefs.getInt("id", 0);
		if(id==0){
			EbookTranslateAPI translateAPI = new EbookTranslateAPI(
				EbookLanguage.AUTO_DETECT,   //Source EbookLanguage
				EbookLanguage.BURMESE,         //Target EbookLanguage
				getString(R.string.ebook_more));
            translateAPI.setTranslateListener(new EbookTranslateAPI.TranslateListener() {
					@Override
					public void onSuccess(String translatedText) {
						text3.setText(translatedText);
					}

					@Override
					public void onFailure(String ErrorText) {

					}
				});

		}else{
			text3.setText(getString(R.string.ebook_more));
		}
		
		
		
		
		
		
		
		
		
		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
				@Override
				public boolean onNavigationItemSelected(MenuItem menuItem)
				{
					menuItem.setChecked(true);
					mDrawerLayout.closeDrawers();
					_Options_Menu_Click(menuItem);
					return true;
				}
			});
			
		
			
			
			

		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout); 
		mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { 
				@Override 
				public void onRefresh() { 
					if(isOnline())
					{
						refresh();
					}
					else{
						mSwipeLayout.setRefreshing(false);
						Toast.makeText(getApplicationContext(),"အင်တာနက်ဆက်သွယ်မှု မရှိပါ",Toast.LENGTH_SHORT).show();
						showNoInternet();
					}
				} });

		mSwipeLayout.setColorSchemeResources(
			R.color.ebook_refresh_progress_1,
			R.color.ebook_refresh_progress_2,
			R.color.ebook_refresh_progress_3); 

		//r_posts=new ArrayList<EbookRecentItem>();
		//r_filteredposts=new ArrayList<EbookRecentItem>();

		d_posts=new ArrayList<EbookDhammaItem>();
		d_filteredposts=new ArrayList<EbookDhammaItem>();

		a_posts=new ArrayList<EbookAudioBookshelfItem>();
        a_filteredposts=new ArrayList<EbookAudioBookshelfItem>();

        m_posts=new ArrayList<EbookMusicItem>();
        m_filteredposts=new ArrayList<EbookMusicItem>();
    
//		de_posts=new ArrayList<EbookDetectiveStoryItem>();
//        de_filteredposts=new ArrayList<EbookDetectiveStoryItem>();
//
//		horror_posts=new ArrayList<EbookHorrorItem>();
//        h_filteredposts=new ArrayList<EbookHorrorItem>();
//
//		ca_posts=new ArrayList<EbookCassetteItem>();
//        ca_filteredposts=new ArrayList<EbookCassetteItem>();
//
//		porpularmusic_posts=new ArrayList<EbookPorpularMusicItem>();
//        porpularmusic_filteredposts=new ArrayList<EbookPorpularMusicItem>();
		
		b_posts=new ArrayList<EbookBookItem>();
        b_filteredposts=new ArrayList<EbookBookItem>();
		
		dhamma_rv=(RecyclerView)findViewById(R.id.dhamma_rv);
        audiobookshelf_rv=(RecyclerView)findViewById(R.id.shortstory_rv);
		music_rv=(RecyclerView)findViewById(R.id.longstory_rv);
//		detectivestory_rv=(RecyclerView)findViewById(R.id.detectivestory_rv);
//		horror_rv=(RecyclerView)findViewById(R.id.horror_rv);
//		cassete_rv=(RecyclerView)findViewById(R.id.cassete_rv);
//		porpularmusic_rv=(RecyclerView)findViewById(R.id.porpularmusic_rv);
		book_rv=(RecyclerView)findViewById(R.id.book_rv);
		
		
		
		
        horizontalRV(dhamma_rv);
        horizontalRV(audiobookshelf_rv);
        horizontalRV(music_rv);
//        horizontalRV(detectivestory_rv);
//horizontalRV(horror_rv);
//		horizontalRV(cassete_rv);
//		horizontalRV(porpularmusic_rv);
		horizontalRV(book_rv);
		//SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
		
		currentFont = sharedPreferences.getInt("font_main", 0);
		refresh();
		
		
	}

	private void requestAppPermissions() {
		if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			return;
		}

		if (hasReadPermissions() && hasWritePermissions()) {
			return;
		}

		ActivityCompat.requestPermissions(this,
										  new String[] {
											  Manifest.permission.READ_EXTERNAL_STORAGE,
											  Manifest.permission.WRITE_EXTERNAL_STORAGE
										  }, 101); // your request code
	}

	private boolean hasReadPermissions() {
		return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
	}

	private boolean hasWritePermissions() {
		return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
	}


	
	
	

	
	




   
	
	
	
	
	
	
	
	
	void horizontalRV(RecyclerView rv){
        LinearLayoutManager layoutManager
            = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(layoutManager);
        ViewCompat.setNestedScrollingEnabled(rv, false);
    }
	private void setupNV()
	{
		navigationView.getMenu().clear();
		navigationView.inflateMenu(R.menu.ebook_navigation_menu);
	}

	protected void onPostCreate(Bundle savedInstanceState)
	{
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
	{
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
/*
	void loadBanner()  {


        req0= new AdRequest.Builder().build();
        adview0.loadAd(req0); 
		req1= new AdRequest.Builder().build();
        adview1.loadAd(req1); 
		//  req2= new AdRequest.Builder().build();
		//  adview2.loadAd(req2); 
	}*/

	@Override
	protected void onStart()
	{
		super.onStart();
		CountDownTimer cdt=new CountDownTimer(2000, 1000) {
			public void onTick(long p1)
			{
				//question.startAnimation(AnimationUtils.loadAnimation(IntroActivity.this, R.anim.scrollview_out));
			}
			public void onFinish()
			{
				//loadBanner();

			}
		}.start();
	}

	public void refresh(){
		currentLink=getFeedAddress();
		tb.collapseActionView();
		if(isOnline()){
			online=true;
			try{
				new DownloadTask().execute(currentLink);
			}catch(Exception e){
				Toast.makeText(getApplicationContext(),"Can't be used Data!",Toast.LENGTH_SHORT).show();
			}	
		}else{
			online=false;

			showNoInternet();
		}
	}
	
	
	

	public void saveToPrefs(String result){
		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(currentLink,result);
		editor.commit();
	}

	public String getFromPrefs(String key){

		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		String lastData= sharedPreferences.getString(currentLink,"");
		return lastData;
	}

	private class DownloadTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected void onPreExecute() { 
			mSwipeLayout.setRefreshing(true); 
		} 

		@Override
		protected String doInBackground(String... p1)
		{
			String result=EbookJSONDownloader.download(p1[0]);
			return result;
		}
		@Override
		public void onPostExecute(String result) {
			try{


				processTitleImg(result);
				saveToPrefs(result);
				mSwipeLayout.setRefreshing(false);
				
				processDhamma(result,"dhamma");
                dhamma_adapter=new DhammaAdapter();
                dhamma_rv.setAdapter(dhamma_adapter);
             
				processAudioBookshelf(result,"audiobook");
               audioBookshelf_adapter=new  AudioBookshelfAdapter();
				audiobookshelf_rv	.setAdapter(audioBookshelf_adapter);
				
				processMusic(result,"music");
			music_adapter=new MusicAdapter();
			music_rv.setAdapter(music_adapter);

//				processDetectiveStory(result,"detective");
//				detectivestory_adapter=new DetectiveStoryAdapter();
//                detectivestory_rv.setAdapter(detectivestory_adapter);
//
//
//			processHorrorStory(result,"horror");
//				 horror_adapter=new HorrorStoryAdapter();
//                horror_rv.setAdapter(horror_adapter);
//
//
//				processCassette(result,"cassette");
//				cassette_adapter=new CassetteAdapter();
//                cassete_rv.setAdapter(cassette_adapter);
//
//				processPorpularMusic(result,"popularmusic");
//				porpularmusic_adapter=new PorpularMusicAdapter();
//                porpularmusic_rv.setAdapter(porpularmusic_adapter);

				processBookStory(result,"book");
				book_adapter=new BookStoryAdapter();
                book_rv.setAdapter(book_adapter);
				
				/*video_bt.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {

					DisplayInterstitialAd();
				}*/

						/*private void DisplayInterstitialAd() {
							if (UnityAds.isReady(InterID)) {
								UnityAds.show(this, InterID);
							}else{

								Toast.makeText(getApplicationContext(),"ads not loaded",Toast.LENGTH_SHORT).show();
							}*/
				
						/*private  void DisplayInterstitialAd() {
							if (UnityAds.isReady(InterID)) {
								//UnityAds.show(InterID);
								DisplayInterstitialAd();
								//ShowInterstitialAds();
							}else{

								Toast.makeText(getApplicationContext(),"ads not loaded",Toast.LENGTH_SHORT).show();
							}
					
				}*/
				
					
//});
				
				
				
				/*video_bt.setOnClickListener(new OnClickListener(){

						private Intent intent;

                        @Override
                        public void onClick(View p1) {
                           goBookActivity("Survey Training Video)", "video");
						

						}

						private void DisplayInterstitialAd() {
						}
					});*/
				   
				   
       dhamma_bt.setOnClickListener(new OnClickListener(){

                        @Override
                        public void onClick(View p1) {
                           // goBookActivity("Dhamma", "dhama");
//							startActivity(new Intent(EbookIndexFeedActivity.this,EbookAudioBookActivity.class));
							String title="Dhamma";
							String Index1="Index1";
//					intent.putExtra("link", link);
//					intent.putExtra("title", title);
//							intent.putExtra("DhammaAudioIndex", Index1);
//					startActivity(intent);
//
//							StartAppAd.showAd(getBaseContext());
//                        }
//
//						private void DisplayInterstitialAd() {
//						}
//                    });


							String link=" https://kgaudioshelfappdhammaaudio.blogspot.com/feeds/posts/default?alt=json";
							Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);
							intent.putExtra("link", link);
							intent.putExtra("title", title);
							intent.putExtra("AudioIndex", Index1);
							startActivity(intent);

							StartAppAd.showAd(getBaseContext());

						}
	   });

				audiobookshelf_bt.setOnClickListener(new OnClickListener(){

                        @Override
                        public void onClick(View p1) {
                          //  goBookActivity("Short Story","shortstory");
							/*String category="category";
						  String title="Short Story";
							String link1=" https://onlinebookstorereadernew.blogspot.com/feeds/posts/default?alt=json";
							Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBookActivity.class);         
							intent.putExtra("link", link1);
							intent.putExtra("title", title);
							intent.putExtra("category", category);
							startActivity(intent);		*/
							
							
							String title="Audioshelf";
							String Index2="Index2";
							String link=" https://kgaudioshelfappdhammaaudio.blogspot.com/feeds/posts/default?alt=json";
							Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);         
					intent.putExtra("link", link);
					intent.putExtra("title", title);
							intent.putExtra("AudioIndex", Index2);
					startActivity(intent);

							StartAppAd.showAd(getBaseContext());
						  
                        }
                    });

              music_bt.setOnClickListener(new OnClickListener(){

                        @Override
                        public void onClick(View p1) {
                          //  goBookActivity("Long Story","longstory")
						  
							String title="Music";
							String Index3="Index3";
							String link=" https://kgaudioshelfappdhammaaudio.blogspot.com/feeds/posts/default?alt=json";
							Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);         
							intent.putExtra("link", link);
							intent.putExtra("title", title);
							intent.putExtra("AudioIndex", Index3);
							startActivity(intent);
							StartAppAd.showAd(getBaseContext());
                        }
                    });
//                detective_bt.setOnClickListener(new OnClickListener(){
//
//                        @Override
//                        public void onClick(View p1) {
//                           // goBookActivity("Detactive","detective");
//
//							String title="Detective Story";
//							String Index4="Index4";
//							String link=" https://onlinebookstorereadernew.blogspot.com/feeds/posts/default?alt=json";
//							Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);
//							intent.putExtra("link", link);
//							intent.putExtra("title", title);
//							intent.putExtra("AudioIndex", Index4);
//							startActivity(intent);
//							StartAppAd.showAd(getBaseContext());
//						   }
//                    });
//
//				horror_bt.setOnClickListener(new OnClickListener(){
//
//                        @Override
//                        public void onClick(View p1) {
//                           // goBookActivity("Horror","horror");
//
//							String title="Horror Story";
//							String Index5="Index5";
//							String link=" https://onlinebookstorereadernew.blogspot.com/feeds/posts/default?alt=json";
//							Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);
//							intent.putExtra("link", link);
//							intent.putExtra("title", title);
//							intent.putExtra("AudioIndex", Index5);
//							startActivity(intent);
//							StartAppAd.showAd(getBaseContext());
//							}
//                    });
//
//				cassete_bt.setOnClickListener(new OnClickListener(){
//
//                        @Override
//                        public void onClick(View p1) {
//                           // goBookActivity("Cassette","cassette");
//
//							String title="Cassette Story";
//							String Index6="Index6";
//							String link=" https://onlinebookstorereadernew.blogspot.com/feeds/posts/default?alt=json";
//							Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);
//							intent.putExtra("link", link);
//							intent.putExtra("title", title);
//							intent.putExtra("AudioIndex", Index6);
//							startActivity(intent);
//							StartAppAd.showAd(getBaseContext());
//						   }
//                    });
//
//
//				porpularmusic_bt.setOnClickListener(new OnClickListener(){
//
//                        @Override
//                        public void onClick(View p1) {
//                          //  goBookActivity("Porpular Music","popularmusic");
//
//							String title="Popular Music";
//							String Index7="Index7";
//							String link=" https://onlinebookstorereadernew.blogspot.com/feeds/posts/default?alt=json";
//							Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);
//							intent.putExtra("link", link);
//							intent.putExtra("title", title);
//							intent.putExtra("AudioIndex", Index7);
//							startActivity(intent);
//							StartAppAd.showAd(getBaseContext());
//						  }
//                    });
//
			
					book_bt.setOnClickListener(new OnClickListener(){

                        @Override
                        public void onClick(View p1) {
                           // goBookActivity("Book For You","book");
                      
							String title="Book for you";
							String Index8="Index4";
							String link=" https://kgaudioshelfappdhammaaudio.blogspot.com/feeds/posts/default?alt=json";
							Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);         
							intent.putExtra("link", link);
							intent.putExtra("title", title);
							intent.putExtra("AudioIndex", Index8);
							startActivity(intent);
							StartAppAd.showAd(getBaseContext());
						   
						   }
                    });
					
					
					
			}catch(Exception e){

			}
		


			button1.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1) {
						//goBookActivity("လူငယ်များအတွက်","video");

						startActivity(new Intent(EbookIndexFeedActivity.this,EbookBt1MainActivity.class));

					}
				});

			button2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1) {
						//goBookActivity("လူငယ်များအတွက်","young");

						startActivity(new Intent(EbookIndexFeedActivity.this,EbookBt2MainActivity.class));


					}
				});

			button3.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1) {
						//goBookActivity("လူငယ်များအတွက်","young");

						startActivity(new Intent(EbookIndexFeedActivity.this,EbookBt3MainActivity.class));


					}
				});

//			button4.setOnClickListener(new OnClickListener(){
//
//					@Override
//					public void onClick(View p1) {
//						//goBookActivity("လူငယ်များအတွက်","young");
//
//						startActivity(new Intent(EbookIndexFeedActivity.this,EbookBt4MainActivity.class));
//
//
//					}
//				});
//
//			button5.setOnClickListener(new OnClickListener(){
//
//					@Override
//					public void onClick(View p1) {
//						//goBookActivity("လူငယ်များအတွက်","young");
//
//						startActivity(new Intent(EbookIndexFeedActivity.this,EbookBt5MainActivity.class));
//
//
//					}
//				});
//
//			button6.setOnClickListener(new OnClickListener(){
//
//					@Override
//					public void onClick(View p1) {
//						//goBookActivity("လူငယ်များအတွက်","young");
//
//						startActivity(new Intent(EbookIndexFeedActivity.this,EbookBt6MainActivity.class));
//
//
//					}
//				});
//
//
//			button7.setOnClickListener(new OnClickListener(){
//
//					@Override
//					public void onClick(View p1) {
//						//goBookActivity("လူငယ်များအတွက်","young");
//
//						startActivity(new Intent(EbookIndexFeedActivity.this,EbookBt7MainActivity.class));
//
//
//					}
//				});

			button8.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1) {
						//goBookActivity("လူငယ်များအတွက်","young");

						startActivity(new Intent(EbookIndexFeedActivity.this,EbookAudioActivityCategories.class));


					}
				});
			
}
	


}
		
	
	public void goBookActivity(String title,String category){
        Intent i=new Intent(EbookIndexFeedActivity.this,EbookBookActivity.class);         
      
		i.putExtra("title",title);
		// i.putExtra("bname",bname);
        i.putExtra("category",category);
        startActivity(i);
	}




	void showGlide(ImageView iv,String src){
		Glide
			.with(getApplicationContext())
			.load(src).placeholder(R.drawable.ebook_ic_launcher)
			.into(iv);
	}

	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.ebook_main_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if(id==android.R.id.home){
			mDrawerLayout.openDrawer(GravityCompat.START);
			return true;
		}
		/*if(item.getItemId()==R.id.menu_font){
		 changeFont();
		 refresh();
		 }

		 else
		 */
		if(item.getItemId() == R.id.menu_search)
		{
			//startActivity(new Intent(EbookMainActivity.this,Downloaded.class));

			//startActivity(new Intent(EbookMainActivity.this,EbookSearchActivity.class));

			final AlertDialog dialog = new AlertDialog.Builder(EbookIndexFeedActivity.this).create();
			LayoutInflater inflater = getLayoutInflater();

			View convertView = inflater.inflate(R.layout.ebook_search_dialog, null);
			dialog.setView(convertView);

			Button btn1 = convertView.findViewById(R.id.button1);
			dialog.show();
			android.graphics.drawable.GradientDrawable miz1 = new android.graphics.drawable.GradientDrawable();

			miz1.setColor(Color.parseColor("#FF0700"));
			miz1.setCornerRadius(20f);
			miz1.setStroke(0, Color.parseColor("#ffffd6ff"));
			btn1.setElevation(0f);
			btn1.setBackground(miz1);
			btn1.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick (View v){


						//main_menu.setClass(getApplicationContext(), EbookBookActivity.class);
						//startActivity(search);
						//startActivity(new Intent(EbookIndexFeedActivity.this,EbookBookActivity.class));

						//goBookActivity("Video Lesson","video");
						//goBookActivity("Dhama","dhama");
						
						String title="Dhamma";
							String Index1="Index1";
							String link=" https://kgaudioshelfindex.blogspot.com/feeds/posts/default?alt=json";
							Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);         
					intent.putExtra("link", link);
					intent.putExtra("title", title);
							intent.putExtra("AudioIndex", Index1);
					startActivity(intent);		
						
						
						dialog.dismiss();
					}
				});
			Button btn2 = convertView.findViewById(R.id.button2);

			android.graphics.drawable.GradientDrawable miz2 = new android.graphics.drawable.GradientDrawable();

			miz2.setColor(Color.parseColor("#8BC34A"));
			miz2.setCornerRadius(20f);
			miz2.setStroke(0, Color.parseColor("#ffffd6ff"));
			btn2.setElevation(0f);
			btn2.setBackground(miz2);
			btn2.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick (View v){
						//	search.setClass(getApplicationContext(), EbookSearchActivity.class);
						//	startActivity(search);
//startActivity(new Intent(EbookIndexFeedActivity.this,EbookBookActivity.class));
						//goBookActivity("Short Story","shortstory");

						String title="Audioshelf";
						String Index2="Index2";
						String link=" https://kgaudioshelfindex.blogspot.com/feeds/posts/default?alt=json";
						Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);         
						intent.putExtra("link", link);
						intent.putExtra("title", title);
						intent.putExtra("AudioIndex", Index2);
						startActivity(intent);		
						
						
						
						dialog.dismiss();
					}
				});





			Button btn3 = convertView.findViewById(R.id.button3);
			dialog.show();
			android.graphics.drawable.GradientDrawable miz3 = new android.graphics.drawable.GradientDrawable();

			miz3.setColor(Color.parseColor("#FF0700"));
			miz3.setCornerRadius(20f);
			miz3.setStroke(0, Color.parseColor("#ffffd6ff"));
			btn3.setElevation(0f);
			btn3.setBackground(miz3);
			btn3.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick (View v){


						//main_menu.setClass(getApplicationContext(), EbookBookActivity.class);
						//startActivity(search);
						//startActivity(new Intent(EbookIndexFeedActivity.this,EbookBookActivity.class));

						//goBookActivity("Long Story","longstory");
						
						String title="Music";
						String Index3="Index3";
						String link=" https://kgaudioshelf.blogspot.com/feeds/posts/default?alt=json";
						Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);         
						intent.putExtra("link", link);
						intent.putExtra("title", title);
						intent.putExtra("AudioIndex", Index3);
						startActivity(intent);		
						
						
						dialog.dismiss();
					}
				});
//			Button btn4 = convertView.findViewById(R.id.button4);
//
//			android.graphics.drawable.GradientDrawable miz4 = new android.graphics.drawable.GradientDrawable();
//
//			miz4.setColor(Color.parseColor("#8BC34A"));
//			miz4.setCornerRadius(20f);
//			miz3.setStroke(0, Color.parseColor("#ffffd6ff"));
//			btn4.setElevation(0f);
//			btn4.setBackground(miz4);
//			btn4.setOnClickListener(new View.OnClickListener(){
//					@Override
//					public void onClick (View v){
//						//	search.setClass(getApplicationContext(), EbookSearchActivity.class);
//						//	startActivity(search);
////startActivity(new Intent(EbookIndexFeedActivity.this,EbookBookActivity.class));
//						//goBookActivity("Detactive","detective");
//
//						String title="Detective Story";
//						String Index4="Index4";
//						String link=" https://kgaudioshelfindex.blogspot.com/feeds/posts/default?alt=json";
//						Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);
//						intent.putExtra("link", link);
//						intent.putExtra("title", title);
//						intent.putExtra("AudioIndex", Index4);
//						startActivity(intent);
//
//						dialog.dismiss();
//					}
//				});
//
//
//
//			Button btn5 = convertView.findViewById(R.id.button5);
//			dialog.show();
//			android.graphics.drawable.GradientDrawable mizo5 = new android.graphics.drawable.GradientDrawable();
//
//			mizo5.setColor(Color.parseColor("#FF0700"));
//			mizo5.setCornerRadius(20f);
//			mizo5.setStroke(0, Color.parseColor("#ffffd6ff"));
//			btn5.setElevation(0f);
//			btn5.setBackground(mizo5);
//			btn5.setOnClickListener(new View.OnClickListener(){
//					@Override
//					public void onClick (View v){
//
//
//						//main_menu.setClass(getApplicationContext(), EbookBookActivity.class);
//						//startActivity(search);
//						//startActivity(new Intent(EbookIndexFeedActivity.this,EbookBookActivity.class));
//
//						//goBookActivity("Horror","horror");
//
//
//						String title="Horror Story";
//						String Index5="Index5";
//						String link=" https://kgaudioshelfindex.blogspot.com/feeds/posts/default?alt=json";
//						Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);
//						intent.putExtra("link", link);
//						intent.putExtra("title", title);
//						intent.putExtra("AudioIndex", Index5);
//						startActivity(intent);
//						dialog.dismiss();
//					}
//				});
//			Button btn6 = convertView.findViewById(R.id.button6);
//
//			android.graphics.drawable.GradientDrawable miz6 = new android.graphics.drawable.GradientDrawable();
//
//			miz6.setColor(Color.parseColor("#8BC34A"));
//			miz6.setCornerRadius(20f);
//			miz6.setStroke(0, Color.parseColor("#ffffd6ff"));
//			btn6.setElevation(0f);
//			btn6.setBackground(miz6);
//			btn6.setOnClickListener(new View.OnClickListener(){
//					@Override
//					public void onClick (View v){
//						//	search.setClass(getApplicationContext(), EbookSearchActivity.class);
//						//	startActivity(search);
////startActivity(new Intent(EbookIndexFeedActivity.this,EbookBookActivity.class));
//						//goBookActivity("Cassette","cassette");
//
//						String title="Cassette Story";
//						String Index6="Index6";
//						String link=" https://kgaudioshelfindex.blogspot.com/feeds/posts/default?alt=json";
//						Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);
//						intent.putExtra("link", link);
//						intent.putExtra("title", title);
//						intent.putExtra("AudioIndex", Index6);
//						startActivity(intent);
//
//						dialog.dismiss();
//					}
//				});
//
//
//
//			Button btn7 = convertView.findViewById(R.id.button7);
//			dialog.show();
//			android.graphics.drawable.GradientDrawable miz7 = new android.graphics.drawable.GradientDrawable();
//
//			miz7.setColor(Color.parseColor("#FF0700"));
//			miz7.setCornerRadius(20f);
//			miz7.setStroke(0, Color.parseColor("#ffffd6ff"));
//			btn7.setElevation(0f);
//			btn7.setBackground(miz7);
//			btn7.setOnClickListener(new View.OnClickListener(){
//					@Override
//					public void onClick (View v){
//
//
//						//main_menu.setClass(getApplicationContext(), EbookBookActivity.class);
//						//startActivity(search);
//						//startActivity(new Intent(EbookIndexFeedActivity.this,EbookBookActivity.class));
//
//						//goBookActivity("Video Lesson","video");
//						//goBookActivity(" Porpular Music","porpularmusic");
//						String title="Popular Music";
//						String Index7="Index7";
//						String link=" https://kgaudioshelfindex.blogspot.com/feeds/posts/default?alt=json";
//						Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);
//						intent.putExtra("link", link);
//						intent.putExtra("title", title);
//						intent.putExtra("AudioIndex", Index7);
//						startActivity(intent);
//
//
//						dialog.dismiss();
//					}
//				});



			Button btn8 = convertView.findViewById(R.id.button8);
			dialog.show();
			android.graphics.drawable.GradientDrawable miz8 = new android.graphics.drawable.GradientDrawable();

			miz8.setColor(Color.parseColor("#8BC34A"));
			miz8.setCornerRadius(20f);
			miz8.setStroke(0, Color.parseColor("#ffffd6ff"));
			btn8.setElevation(0f);
			btn8.setBackground(miz8);
			btn8.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick (View v){


						//main_menu.setClass(getApplicationContext(), EbookBookActivity.class);
						//startActivity(search);
						//startActivity(new Intent(EbookIndexFeedActivity.this,EbookBookActivity.class));

						//goBookActivity("Video Lesson","video");
						//goBookActivity(" Books for you","book");

						
						String title="Book for you";
						String Index8="Index4";
						String link=" https://kgaudioshelfindex.blogspot.com/feeds/posts/default?alt=json";
						Intent intent=new Intent(EbookIndexFeedActivity.this,EbookAudioBtBookActivity.class);         
						intent.putExtra("link", link);
						intent.putExtra("title", title);
						intent.putExtra("AudioIndex", Index8);
						startActivity(intent);		

						dialog.dismiss();
					}
				});


		}

		

		else{
			_Options_Menu_Click(item);
		}
		return super.onOptionsItemSelected(item);
	}

	



	public void addItem(EbookDhammaItem item){
		d_posts.add(item);
	}

	public class DhammaAdapter extends RecyclerView.Adapter<DhammaAdapter.ViewHolder>
	{
		@Override
		public DhammaAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
		{
			View v=getLayoutInflater().inflate(R.layout.ebook_image_item,p1,false);
			return new ViewHolder(v);
		}

		public void filter(String charText){
			charText =charText.toLowerCase();
			d_filteredposts.clear();
			if (charText.length()==0){
				d_filteredposts.addAll(d_posts);
			}else{
				for (EbookDhammaItem pi : d_posts){
					if(pi.bname.toLowerCase().contains(charText))
					{ 
						d_filteredposts.add(pi);
					}
				}
			}
			notifyDataSetChanged();
		}

		@Override
		public int getItemCount()
		{
			bookCount=d_filteredposts.size();
			if(bookCount>10){
				bookCount=10;
			}else{
				bookCount=d_filteredposts.size();
			}
			return bookCount;
		}

		@Override
		public void onBindViewHolder(DhammaAdapter.ViewHolder p1,final int p2)
		{
			if((d_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
				Glide
					.with(getApplicationContext())
					.load(d_filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
					.into(p1.iv);

			}else{
				p1.iv.setImageResource(R.drawable.ebook_loading_book);
			}
			p1.bname.setText(d_filteredposts.get(p2).bname);

			p1.card.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View p1) {
						//gotoBDtvActivity(tv_filteredposts.get(p2).thumbnail,tv_filteredposts.get(p2).bname,tv_filteredposts.get(p2).wname,tv_filteredposts.get(p2).link,tv_filteredposts.get(p2).category);
						//gotoBDtvActivity
						gotoBDActivity(d_filteredposts.get(p2).thumbnail,d_filteredposts.get(p2).bname,d_filteredposts.get(p2).wname,d_filteredposts.get(p2).link,d_filteredposts.get(p2).category,d_filteredposts.get(p2).mb1,d_filteredposts.get(p2).mb2,d_filteredposts.get(p2).time);
                 
					}
				});
		}


		public EbookDhammaItem getItem(int pos){
			return d_filteredposts.get(pos);
		}

		public void reset()
		{
			d_posts.clear();
			d_filteredposts.clear();
			notifyDataSetChanged();
		}

		public DhammaAdapter()
		{
			super();
			d_filteredposts=new ArrayList<EbookDhammaItem>();
			d_filteredposts.addAll(d_posts);
		}

		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
		{
			ImageView iv;
			CardView card;
			TextView bname;
			@Override
			public void onClick(View view)
			{
				//DisplayInterstitialAd();

			}
			public ViewHolder(View view)
			{
				super(view);
				iv=view.findViewById(R.id.image);
				card=view.findViewById(R.id.card);

				bname=view.findViewById(R.id.bname);

				view.setOnClickListener(this);
			}
		}
	}

//for survey
    public void addItem(EbookAudioBookshelfItem item){
        a_posts.add(item);
    }
    public class AudioBookshelfAdapter extends RecyclerView.Adapter<AudioBookshelfAdapter.ViewHolder>
    {
        @Override
        public AudioBookshelfAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
        {
            View v=getLayoutInflater().inflate(R.layout.ebook_image_item,p1,false);
            return new ViewHolder(v);
        }

        public void filter(String charText){
            charText =charText.toLowerCase();
            a_filteredposts.clear();
            if (charText.length()==0){
                a_filteredposts.addAll(a_posts);
            }else{
                for (EbookAudioBookshelfItem pi : a_posts){
                    if(pi.bname.toLowerCase().contains(charText))
                    { 
                        a_filteredposts.add(pi);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount()
        {
            bookCount=a_filteredposts.size();
            if(bookCount>10){
                bookCount=10;
            }else{
                bookCount=a_filteredposts.size();
            }
			return bookCount;
        }

        @Override
        public void onBindViewHolder(AudioBookshelfAdapter.ViewHolder p1, final int p2)
        {
            if((a_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
                Glide
                    .with(getApplicationContext())
                    .load(a_filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
                    .into(p1.iv);

            }else{
                p1.iv.setImageResource(R.drawable.ebook_loading_book);
            }


			p1.bname.setText(a_filteredposts.get(p2).bname);

            p1.card.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1) {
                        gotoBDActivity(a_filteredposts.get(p2).thumbnail,a_filteredposts.get(p2).bname,a_filteredposts.get(p2).wname,a_filteredposts.get(p2).link,a_filteredposts.get(p2).category,a_filteredposts.get(p2).mb1,a_filteredposts.get(p2).mb2,a_filteredposts.get(p2).time);

                    }
                });
        }

        public EbookAudioBookshelfItem getItem(int pos){
            return a_filteredposts.get(pos);
        }

        public void reset()
        {
            a_posts.clear();
            a_filteredposts.clear();
            notifyDataSetChanged();
        }

        public AudioBookshelfAdapter()
        {
            super();
            a_filteredposts=new ArrayList<EbookAudioBookshelfItem>();
            a_filteredposts.addAll(a_posts);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            ImageView iv;
            CardView card;
			TextView bname;
            @Override
            public void onClick(View view)
            {



            }
            public ViewHolder(View view)
            {
                super(view);
                iv=view.findViewById(R.id.image);
                card=view.findViewById(R.id.card);
				bname=view.findViewById(R.id.bname);

                view.setOnClickListener(this);
            }
        }
    }
//for level
    public void addItem(EbookMusicItem item){
        m_posts.add(item);
    }
    public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder>
    {
        @Override
        public MusicAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
        {
            View v=getLayoutInflater().inflate(R.layout.ebook_image_item,p1,false);
            return new ViewHolder(v);
        }

        public void filter(String charText){
            charText =charText.toLowerCase();
            m_filteredposts.clear();
            if (charText.length()==0){
                m_filteredposts.addAll(m_posts);
            }else{
                for (EbookMusicItem pi : m_posts){
                    if(pi.bname.toLowerCase().contains(charText))
                    { 
                        m_filteredposts.add(pi);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount()
        {

            bookCount=m_filteredposts.size();
            if(bookCount>10){
                bookCount=10;
            }else{
                bookCount=m_filteredposts.size();
            }
			return bookCount;
        }

        @Override
        public void onBindViewHolder(MusicAdapter.ViewHolder p1, final int p2)
        {
            if((m_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
                Glide
                    .with(getApplicationContext())
                    .load(m_filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
                    .into(p1.iv);

            }else{
                p1.iv.setImageResource(R.drawable.ebook_loading_book);
            }
			
			p1.bname.setText(m_filteredposts.get(p2).bname);


            p1.card.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1) {

                        gotoBDActivity(m_filteredposts.get(p2).thumbnail,m_filteredposts.get(p2).bname,m_filteredposts.get(p2).wname,m_filteredposts.get(p2).link,m_filteredposts.get(p2).category,m_filteredposts.get(p2).mb1,m_filteredposts.get(p2).mb2,m_filteredposts.get(p2).time);
                    }
                });
        }

        public EbookMusicItem getItem(int pos){
            return m_filteredposts.get(pos);
        }

        public void reset()
        {
            m_posts.clear();
            m_filteredposts.clear();
            notifyDataSetChanged();
        }

        public MusicAdapter()
        {
            super();
            m_filteredposts=new ArrayList<EbookMusicItem>();
            m_filteredposts.addAll(m_posts);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            ImageView iv;
            CardView card;
			TextView bname;
            @Override
            public void onClick(View view)
            {



            }
            public ViewHolder(View view)
            {
                super(view);
                iv=view.findViewById(R.id.image);
                card=view.findViewById(R.id.card);
				bname=view.findViewById(R.id.bname);

                view.setOnClickListener(this);
            }
        }
    }

//
//    //for total
//    public void addItem(EbookDetectiveStoryItem item){
//        de_posts.add(item);
//    }
//    public class DetectiveStoryAdapter extends RecyclerView.Adapter<DetectiveStoryAdapter.ViewHolder>
//    {
//        @Override
//        public DetectiveStoryAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
//        {
//            View v=getLayoutInflater().inflate(R.layout.ebook_image_item,p1,false);
//            return new ViewHolder(v);
//        }
//
//        public void filter(String charText){
//            charText =charText.toLowerCase();
//            de_filteredposts.clear();
//            if (charText.length()==0){
//                de_filteredposts.addAll(de_posts);
//            }else{
//                for (EbookDetectiveStoryItem pi : de_posts){
//                    if(pi.bname.toLowerCase().contains(charText))
//                    {
//                        de_filteredposts.add(pi);
//                    }
//                }
//            }
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public int getItemCount()
//        {
//
//            bookCount=de_filteredposts.size();
//            if(bookCount>10){
//                bookCount=10;
//            }else{
//                bookCount=de_filteredposts.size();
//            }
//			return bookCount;
//        }
//
//        @Override
//        public void onBindViewHolder(DetectiveStoryAdapter.ViewHolder p1, final int p2)
//        {
//            if((de_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
//                Glide
//                    .with(getApplicationContext())
//                    .load(de_filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
//                    .into(p1.iv);
//
//            }else{
//
//                p1.iv.setImageResource(R.drawable.ebook_loading_book);
//            }
//			p1.bname.setText(de_filteredposts.get(p2).bname);
//
//
//            p1.card.setOnClickListener(new OnClickListener(){
//
//                    @Override
//                    public void onClick(View p1) {
//
//                        gotoBDActivity(de_filteredposts.get(p2).thumbnail,de_filteredposts.get(p2).bname,de_filteredposts.get(p2).wname,de_filteredposts.get(p2).link,de_filteredposts.get(p2).category,de_filteredposts.get(p2).mb1,de_filteredposts.get(p2).mb2,de_filteredposts.get(p2).time);
//                    }
//                });
//        }
//
//        public EbookDetectiveStoryItem getItem(int pos){
//            return de_filteredposts.get(pos);
//        }
//
//        public void reset()
//        {
//            de_posts.clear();
//            de_filteredposts.clear();
//            notifyDataSetChanged();
//        }
//
//        public DetectiveStoryAdapter()
//        {
//            super();
//            de_filteredposts=new ArrayList<EbookDetectiveStoryItem>();
//            de_filteredposts.addAll(de_posts);
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
//        {
//            ImageView iv;
//            CardView card;
//			TextView bname;
//            @Override
//            public void onClick(View view)
//            {
//
//
//
//            }
//            public ViewHolder(View view)
//            {
//                super(view);
//                iv=view.findViewById(R.id.image);
//                card=view.findViewById(R.id.card);
//				bname=view.findViewById(R.id.bname);
//
//                view.setOnClickListener(this);
//            }
//        }
//    }
//
//
//
//
//	//for Gps and Gis
//    public void addItem(EbookHorrorItem item){
//        horror_posts.add(item);
//    }
//    public class HorrorStoryAdapter extends RecyclerView.Adapter<HorrorStoryAdapter.ViewHolder>
//    {
//        @Override
//        public HorrorStoryAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
//        {
//            View v=getLayoutInflater().inflate(R.layout.ebook_image_item,p1,false);
//            return new ViewHolder(v);
//        }
//
//        public void filter(String charText){
//            charText =charText.toLowerCase();
//            h_filteredposts.clear();
//            if (charText.length()==0){
//               h_filteredposts.addAll(horror_posts);
//            }else{
//                for (EbookHorrorItem pi : horror_posts){
//                    if(pi.bname.toLowerCase().contains(charText))
//                    {
//                        h_filteredposts.add(pi);
//                    }
//                }
//            }
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public int getItemCount()
//        {
//
//            bookCount=h_filteredposts.size();
//            if(bookCount>10){
//                bookCount=10;
//            }else{
//                bookCount=h_filteredposts.size();
//            }
//			return bookCount;
//        }
//
//        @Override
//        public void onBindViewHolder(HorrorStoryAdapter.ViewHolder p1, final int p2)
//        {
//            if((h_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
//                Glide
//                    .with(getApplicationContext())
//                    .load(h_filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
//                    .into(p1.iv);
//
//            }else{
//
//                p1.iv.setImageResource(R.drawable.ebook_loading_book);
//            }
//			p1.bname.setText(h_filteredposts.get(p2).bname);
//
//
//            p1.card.setOnClickListener(new OnClickListener(){
//
//                    @Override
//                    public void onClick(View p1) {
//
//                        gotoBDActivity(h_filteredposts.get(p2).thumbnail,h_filteredposts.get(p2).bname,h_filteredposts.get(p2).wname,h_filteredposts.get(p2).link,h_filteredposts.get(p2).category,h_filteredposts.get(p2).mb1,h_filteredposts.get(p2).mb2,h_filteredposts.get(p2).time);
//                    }
//                });
//        }
//
//        public EbookHorrorItem getItem(int pos){
//            return h_filteredposts.get(pos);
//        }
//
//        public void reset()
//        {
//            horror_posts.clear();
//            h_filteredposts.clear();
//            notifyDataSetChanged();
//        }
//
//        public HorrorStoryAdapter()
//        {
//            super();
//            h_filteredposts=new ArrayList<EbookHorrorItem>();
//            h_filteredposts.addAll(horror_posts);
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
//        {
//            ImageView iv;
//            CardView card;
//			TextView bname;
//            @Override
//            public void onClick(View view)
//            {
//
//
//
//            }
//            public ViewHolder(View view)
//            {
//                super(view);
//                iv=view.findViewById(R.id.image);
//                card=view.findViewById(R.id.card);
//				bname=view.findViewById(R.id.bname);
//
//                view.setOnClickListener(this);
//            }
//        }
//    }
//
//
//	//for Autocad
//    public void addItem(EbookCassetteItem item){
//        ca_posts.add(item);
//    }
//    public class CassetteAdapter extends RecyclerView.Adapter<CassetteAdapter.ViewHolder>
//    {
//        @Override
//        public CassetteAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
//        {
//            View v=getLayoutInflater().inflate(R.layout.ebook_image_item,p1,false);
//            return new ViewHolder(v);
//        }
//
//        public void filter(String charText){
//            charText =charText.toLowerCase();
//            ca_filteredposts.clear();
//            if (charText.length()==0){
//                ca_filteredposts.addAll(ca_posts);
//            }else{
//                for (EbookCassetteItem pi : ca_posts){
//                    if(pi.bname.toLowerCase().contains(charText))
//                    {
//                        ca_filteredposts.add(pi);
//                    }
//                }
//            }
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public int getItemCount()
//        {
//
//            bookCount=ca_filteredposts.size();
//            if(bookCount>10){
//                bookCount=10;
//            }else{
//                bookCount=ca_filteredposts.size();
//            }
//			return bookCount;
//        }
//
//        @Override
//        public void onBindViewHolder(CassetteAdapter.ViewHolder p1, final int p2)
//        {
//            if((ca_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
//                Glide
//                    .with(getApplicationContext())
//                    .load(ca_filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
//                    .into(p1.iv);
//
//            }else{
//
//                p1.iv.setImageResource(R.drawable.ebook_loading_book);
//            }
//			p1.bname.setText(ca_filteredposts.get(p2).bname);
//
//
//            p1.card.setOnClickListener(new OnClickListener(){
//
//                    @Override
//                    public void onClick(View p1) {
//
//                        gotoBDActivity(ca_filteredposts.get(p2).thumbnail,ca_filteredposts.get(p2).bname,ca_filteredposts.get(p2).wname,ca_filteredposts.get(p2).link,ca_filteredposts.get(p2).category,ca_filteredposts.get(p2).mb1,ca_filteredposts.get(p2).mb2,ca_filteredposts.get(p2).time);
//                    }
//                });
//        }
//
//        public EbookCassetteItem getItem(int pos){
//            return ca_filteredposts.get(pos);
//        }
//
//        public void reset()
//        {
//            ca_posts.clear();
//            ca_filteredposts.clear();
//            notifyDataSetChanged();
//        }
//
//        public CassetteAdapter()
//        {
//            super();
//            ca_filteredposts=new ArrayList<EbookCassetteItem>();
//            ca_filteredposts.addAll(ca_posts);
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
//        {
//            ImageView iv;
//            CardView card;
//			TextView bname;
//            @Override
//            public void onClick(View view)
//            {
//
//
//
//            }
//            public ViewHolder(View view)
//            {
//                super(view);
//                iv=view.findViewById(R.id.image);
//                card=view.findViewById(R.id.card);
//				bname=view.findViewById(R.id.bname);
//
//                view.setOnClickListener(this);
//            }
//        }
//    }
//
//
//	//for excel
//    public void addItem(EbookPorpularMusicItem item){
//        porpularmusic_posts.add(item);
//    }
//    public class PorpularMusicAdapter extends RecyclerView.Adapter<PorpularMusicAdapter.ViewHolder>
//    {
//        @Override
//        public PorpularMusicAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
//        {
//            View v=getLayoutInflater().inflate(R.layout.ebook_image_item,p1,false);
//            return new ViewHolder(v);
//        }
//
//        public void filter(String charText){
//            charText =charText.toLowerCase();
//            porpularmusic_filteredposts.clear();
//            if (charText.length()==0){
//				porpularmusic_filteredposts.addAll(porpularmusic_posts);
//            }else{
//                for (EbookPorpularMusicItem pi : porpularmusic_posts){
//                    if(pi.bname.toLowerCase().contains(charText))
//                    {
//                        porpularmusic_filteredposts.add(pi);
//                    }
//                }
//            }
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public int getItemCount()
//        {
//
//            bookCount=porpularmusic_filteredposts.size();
//            if(bookCount>10){
//                bookCount=10;
//            }else{
//                bookCount=porpularmusic_filteredposts.size();
//            }
//			return bookCount;
//        }
//
//        @Override
//        public void onBindViewHolder(PorpularMusicAdapter.ViewHolder p1, final int p2)
//        {
//            if((porpularmusic_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
//                Glide
//                    .with(getApplicationContext())
//                    .load(porpularmusic_filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
//                    .into(p1.iv);
//
//            }else{
//
//                p1.iv.setImageResource(R.drawable.ebook_loading_book);
//            }
//			p1.bname.setText(porpularmusic_filteredposts.get(p2).bname);
//
//
//            p1.card.setOnClickListener(new OnClickListener(){
//
//                    @Override
//                    public void onClick(View p1) {
//
//                        gotoBDActivity(porpularmusic_filteredposts.get(p2).thumbnail,porpularmusic_filteredposts.get(p2).bname,porpularmusic_filteredposts.get(p2).wname,porpularmusic_filteredposts.get(p2).link,porpularmusic_filteredposts.get(p2).category,porpularmusic_filteredposts.get(p2).mb1,porpularmusic_filteredposts.get(p2).mb2,porpularmusic_filteredposts.get(p2).time);
//                    }
//                });
//        }

//        public EbookPorpularMusicItem getItem(int pos){
//            return porpularmusic_filteredposts.get(pos);
//        }
//
//        public void reset()
//        {
//            porpularmusic_posts.clear();
//            porpularmusic_filteredposts.clear();
//            notifyDataSetChanged();
//        }
//
//        public PorpularMusicAdapter()
//        {
//            super();
//            porpularmusic_filteredposts=new ArrayList<EbookPorpularMusicItem>();
//            porpularmusic_filteredposts.addAll(porpularmusic_posts);
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
//        {
//            ImageView iv;
//            CardView card;
//			TextView bname;
//            @Override
//            public void onClick(View view)
//            {
//
//
//
//            }
//            public ViewHolder(View view)
//            {
//                super(view);
//                iv=view.findViewById(R.id.image);
//                card=view.findViewById(R.id.card);
//				bname=view.findViewById(R.id.bname);
//
//                view.setOnClickListener(this);
//            }
//        }
//    }
//
//
//
	

	public void addItem(EbookBookItem item){
        b_posts.add(item);
    }
    public class BookStoryAdapter extends RecyclerView.Adapter<BookStoryAdapter.ViewHolder>
    {
        @Override
        public BookStoryAdapter.ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
        {
            View v=getLayoutInflater().inflate(R.layout.ebook_image_item,p1,false);
            return new ViewHolder(v);
        }

        public void filter(String charText){
            charText =charText.toLowerCase();
            b_filteredposts.clear();
            if (charText.length()==0){
				b_filteredposts.addAll(b_posts);
            }else{
                for (EbookBookItem pi : b_posts){
                    if(pi.bname.toLowerCase().contains(charText))
                    { 
                        b_filteredposts.add(pi);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount()
        {

            bookCount=b_filteredposts.size();
            if(bookCount>10){
                bookCount=10;
            }else{
                bookCount=b_filteredposts.size();
            }
			return bookCount;
        }

        @Override
        public void onBindViewHolder(BookStoryAdapter.ViewHolder p1, final int p2)
        {
            if((b_filteredposts.get(p2).thumbnail.length()>5)&&(online)){
                Glide
                    .with(getApplicationContext())
                    .load(b_filteredposts.get(p2).thumbnail).placeholder(R.drawable.ebook_loading_book)
                    .into(p1.iv);

            }else{

                p1.iv.setImageResource(R.drawable.ebook_loading_book);
            }
			p1.bname.setText(b_filteredposts.get(p2).bname);


            p1.card.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View p1) {

                        gotoBFUDActivity(b_filteredposts.get(p2).thumbnail,b_filteredposts.get(p2).bname,b_filteredposts.get(p2).wname,b_filteredposts.get(p2).link,b_filteredposts.get(p2).category,b_filteredposts.get(p2).mb1,b_filteredposts.get(p2).mb2,b_filteredposts.get(p2).time);
                    }
                });
        }

        public EbookBookItem getItem(int pos){
            return b_filteredposts.get(pos);
        }

        public void reset()
        {
            b_posts.clear();
            b_filteredposts.clear();
            notifyDataSetChanged();
        }

        public BookStoryAdapter()
        {
            super();
            b_filteredposts=new ArrayList<EbookBookItem>();
            b_filteredposts.addAll(b_posts);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            ImageView iv;
            CardView card;
			TextView bname;
            @Override
            public void onClick(View view)
            {



            }
            public ViewHolder(View view)
            {
                super(view);
                iv=view.findViewById(R.id.image);
                card=view.findViewById(R.id.card);
				bname=view.findViewById(R.id.bname);

                view.setOnClickListener(this);
            }
        }
    }

	
	
	
	
	
	
	

	void gotoBFUDActivity(String thumbnail,String bname,String wname,String link,String category, String mb1,String mb2,String time){
		if(isOnline()){
            online=true;
			//Intent i=new Intent(EbookIndexFeedActivity.this,EbookBookActivity.class);         
			//DisplayInterstitialAd();
			//Intent i=new Intent(EbookIndexFeedActivity.this,MoviesDetailActivity.class);         

			Intent i=new Intent(EbookIndexFeedActivity.this,EbookPdfBookActivity.class);


			i.putExtra("title",bname);
			i.putExtra("thumbnail",thumbnail);
			i.putExtra("bname",bname);
			i.putExtra("wname",wname);
			i.putExtra("link",link);
			i.putExtra("category",category);
			i.putExtra("mb1",mb1);
			i.putExtra("mb2",mb2);
			i.putExtra("time",time);
			
            startActivity(i);
			StartAppAd.showAd(getBaseContext());

        }else{
            online=false;

            showNoInternet();

        }
	}

	/* void gotoBDActivity(String thumbnail,String bname,String wname,String link,String category, String mb1,String mb2,String time){
	 if(isOnline()){
	 online=true;
	 //Intent i=new Intent(EbookIndexFeedActivity.this,BookDetailActivity.class);         

	 //Intent i=new Intent(EbookIndexFeedActivity.this,MoviesDetailActivity.class);         

	 //DisplayInterstitialAd();

	 Intent i=new Intent(EbookIndexFeedActivity.this,EbookDetailActivity2.class);         

	 i.putExtra("thumbnail",thumbnail);
	 i.putExtra("bname",bname);
	 i.putExtra("wname",wname);
	 i.putExtra("link",link);
	 //   i.putExtra("category",category);
	 i.putExtra("mb1",mb1);
	 i.putExtra("mb2",mb2);
	 i.putExtra("time",time);


	 startActivity(i);
	 }else{
	 online=false;

	 showNoInternet();

	 }

	 }*/


    void gotoBDActivity(String thumbnail,String bname,String wname,String link,String category, String mb1,String mb2,String time){
        if(isOnline()){
            online=true;
			//Intent i=new Intent(EbookIndexFeedActivity.this,BookDetailActivity.class);         

			//Intent i=new Intent(EbookIndexFeedActivity.this,MoviesDetailActivity.class);         

			//DisplayInterstitialAd();
			
			Intent i=new Intent(EbookIndexFeedActivity.this,EbookAudioBookActivity.class);


			i.putExtra("title",bname);
			i.putExtra("thumbnail",thumbnail);
			i.putExtra("bname",bname);
			i.putExtra("wname",wname);
			i.putExtra("link",link);
			i.putExtra("category",category);
			i.putExtra("mb1",mb1);
			i.putExtra("mb2",mb2);
			i.putExtra("time",time);
		//	i.putExtra("title",title);
			
			
            startActivity(i);
        }else{
            online=false;

            showNoInternet();

        }

    }
	/*
	
	void gotoBDActivity(String thumbnail,String bname,String wname,String link,String category, String mb1,String mb2,String time,String title){
        if(isOnline()){
            online=true;

			//Intent i=new Intent(EbookIndexFeedActivity.this,EbookDetailActivity2.class);         
			Intent i=new Intent(EbookIndexFeedActivity.this,EbookBookActivity.class);


			i.putExtra("title",bname);
			i.putExtra("thumbnail",thumbnail);
			i.putExtra("bname",bname);
			i.putExtra("wname",wname);
			i.putExtra("link",link);
			i.putExtra("category",category);
			i.putExtra("mb1",mb1);
			i.putExtra("mb2",mb2);
			i.putExtra("time",time);
			i.putExtra("title",title);



            startActivity(i);
        }else{
            online=false;

            showNoInternet();

        }

    }
	*/
	
	
    public void showTitleImg(String img1,String img2,String img3,String img4,String img5){
		final  String[] images={
            img1,
            img2,
            img3,
            img4,
            img5
        };

        showTI(images[currentImg]);
        handler.postDelayed(runnable = new Runnable() {
                                public void run() {

                                    handler.postDelayed(runnable, 5000);
                                    currentImg++;
                                    if(currentImg==maxImg+1){
                                        currentImg=0;
                                    }
                                    showTI(images[currentImg]);

                                }
                            }, 5000);

        pre.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1) {
                    currentImg--;
                    if(currentImg==-1){
                        currentImg=maxImg;
                    }
                    showTI(images[currentImg]);
                }
            });
        next.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View p1) {
                    currentImg++;
                    if(currentImg==maxImg+1){
                        currentImg=0;
                    }
                    showTI(images[currentImg]);
                }
            });
    }
    void showTI(String res){

        if((res.length()>5)&&(online)){
            Glide
                .with(getApplicationContext())
                .load(res).placeholder(R.drawable.ebook_apk)
                .into(title_img);
            double current=currentImg+1;
            String pot=current+"/5";
            position.setText(pot.replaceAll(".0",""));
        }else{
            title_img.setImageResource(R.drawable.ebook_loading_book);
        }

    }
	
	
	

	private void changeFont()
	{
		currentFont++;
		if (currentFont == 3)
		{
			currentFont = 0;
		}
		SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt("font_main", currentFont);
		editor.commit();
		try{
            processTitleImg(getFromPrefs(currentLink));
			processDhamma(getFromPrefs(currentLink),"dhamma");
			dhamma_adapter=new DhammaAdapter();
			dhamma_rv.setAdapter(dhamma_adapter);
			
			
            processAudioBookshelf(getFromPrefs(currentLink),"audiobook");
           audioBookshelf_adapter=new AudioBookshelfAdapter();
            audiobookshelf_rv.setAdapter(audioBookshelf_adapter);
         
			processMusic(getFromPrefs(currentLink),"music");
			music_adapter=new MusicAdapter();
       music_rv.setAdapter(music_adapter);
			

//            processDetectiveStory(getFromPrefs(currentLink),"detective");
//            detectivestory_adapter=new DetectiveStoryAdapter();
//            detectivestory_rv.setAdapter(detectivestory_adapter);
//
//
//			processHorrorStory(getFromPrefs(currentLink),"horror");
//          	horror_adapter=new HorrorStoryAdapter();
//		  horror_rv.setAdapter(horror_adapter);
//
//			processCassette(getFromPrefs(currentLink),"cassette");
//          	cassette_adapter=new CassetteAdapter();
//			cassete_rv.setAdapter(cassette_adapter);
//
//			processPorpularMusic(getFromPrefs(currentLink),"popularmusic");
//          	porpularmusic_adapter=new PorpularMusicAdapter();
//			porpularmusic_rv.setAdapter(porpularmusic_adapter);
			
			
			processBookStory(getFromPrefs(currentLink),"book");
          	book_adapter=new BookStoryAdapter();
		  book_rv.setAdapter(book_adapter);

			
		}catch(Exception e){
			Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show();
		}

	}








	public void gotoFacebook()
	{
		final String urlFb = "fb://page/"+FACEBOOK_PROFILE_ID;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser

        final PackageManager packageManager = getPackageManager();

        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
																	  PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() == 0) {
            final String urlBrowser = FACEBOOK_URL;
            intent.setData(Uri.parse(urlBrowser));
        }

        startActivity(intent);
	}
	public void gotoFbAccount()
	{
		final String urlFb = "fb://profile/"+"100029607351728";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser

        final PackageManager packageManager = getPackageManager();

        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
																	  PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() == 0) {
            final String urlBrowser = "https://www.facebook.com/techkipon";
            intent.setData(Uri.parse(urlBrowser));
        }

        startActivity(intent);
	}
	private void showNoInternet (){
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		View parent_view = LayoutInflater.from(this).inflate(R.layout.ebook_one_button_dia, null);
		dialog.setView(parent_view);
		dialog.setCancelable(true);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.show();
		ImageView iv=(ImageView) dialog.findViewById(R.id.icon);
		TextView tv_title = (TextView) dialog.findViewById(R.id.title);
		TextView tv_message = (TextView) dialog.findViewById(R.id.message);
		final Button cancel= (Button) dialog.findViewById(R.id.bt1);
		iv.setImageResource(R.drawable.ebook_no_internet_icon);
		tv_title.setText("No internet");
		tv_message.setText("Welcome....my friend။ Need internet connection for use app.");
		cancel.setText("Yes");
		cancel.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{

					dialog.dismiss();
				}});

		
		
	}
	
	
	public void MsgBox(String title, String msg){

		AlertDialog alertDialog = new AlertDialog.Builder(this).create();

		alertDialog.setTitle(title);

		alertDialog.setMessage(msg); 

		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() { 
				public void onClick(DialogInterface dialog, int which) { 
					dialog.dismiss(); 
				} 
			}); 
		alertDialog.show();

	}


}
	
