package com.bdtopcoder.quickadmob;

/*
 * Created By Atikul Software
 * Website : https://bdtopcoder.xyz
 * */

import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;

public class AdsUnit {



    public static boolean isAds = true;
    public static InterstitialAd mInterstitialAd;
    public static RewardedAd mRewardedAd;
    public static RewardedInterstitialAd mRewardedInterstitialAd;

    public static AppOpenAd mOpenAd; // Added for Open Ads

//    public static String BANNER = "ca-app-pub-2481530783933136/6268884128";
//    public static String INTERSTITIAL ="ca-app-pub-2481530783933136/8706526965";
//    public static String REWARDED = "ca-app-pub-2481530783933136/7274997931";
//    public static String REWARDED_INTERSTITIAL = "ca-app-pub-2481530783933136/3415282583";
//    public static String OPEN_AD = "ca-app-pub-2481530783933136/7507773164"; // Added Open Ad Unit


    public static String BANNER = "ca-app-pub-3940256099942544/6300978111A";
    public static String INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712A";
    public static String REWARDED = "ca-app-pub-3940256099942544/5224354917A";
    public static String REWARDED_INTERSTITIAL = "ca-app-pub-3940256099942544/5354046379A";

    public static String OPEN_AD = "ca-app-pub-3940256099942544/3419835294A"; // Added Open Ad Unit
} // AdsUnit Close Here =========
