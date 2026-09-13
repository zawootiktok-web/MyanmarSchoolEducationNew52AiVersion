package com.bdtopcoder.quickadmob;

//
//
//import android.app.Activity;
//import android.content.Context;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.ads.AdError;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.FullScreenContentCallback;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.interstitial.InterstitialAd;
//import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
//import com.google.android.gms.ads.rewarded.RewardedAd;
//import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
//import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
//import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
//
//public class Admob {
//
//    static onDismiss onDismiss;
//
//    public Admob(com.bdtopcoder.quickadmob.onDismiss onDismiss) {
//        this.onDismiss = onDismiss;
//    }
//
//    public Admob() {
//    }
//
//
//
//
//    public static void setBanner(LinearLayout banner, Context context) {
//
//        if (AdsUnit.isAds) {
//            MobileAds.initialize(context, initializationStatus -> {
//            });
//            AdView adView = new AdView(context);
//            banner.addView(adView);
//            adView.setAdUnitId(AdsUnit.BANNER);
//            adView.setAdSize(AdSize.BANNER);
//            AdRequest adRequest = new AdRequest.Builder().build();
//            adView.loadAd(adRequest);
//        }
//
//    } // setBanner Close Here ========
//
//    public static void loadInterstitialAds(Context context) {
//        if (AdsUnit.isAds) {
//            MobileAds.initialize(context, initializationStatus -> {
//            });
//
//            AdRequest adRequest = new AdRequest.Builder().build();
//            InterstitialAd.load(context, AdsUnit.INTERSTITIAL, adRequest,
//                    new InterstitialAdLoadCallback() {
//                        @Override
//                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//
//                            AdsUnit.mInterstitialAd = interstitialAd;
//                        }
//
//                        @Override
//                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                            AdsUnit.mInterstitialAd = null;
//                        }
//                    });
//        }
//
//    } // loadInterstitialAds Close here ======
//
//    public void ShowInterstitial(Activity activity, boolean isReload) {
//
//        if (AdsUnit.mInterstitialAd != null) {
//            AdsUnit.mInterstitialAd.show(activity);
//
//            AdsUnit.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    super.onAdDismissedFullScreenContent();
//
//                    if (isReload) {
//                        AdsUnit.mInterstitialAd = null;
//                        Admob.loadInterstitialAds(activity);
//                    }
//
//                    onDismiss.onDismiss();
//                }
//
//                @Override
//                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                    super.onAdFailedToShowFullScreenContent(adError);
//                    onDismiss.onDismiss();
//                }
//            });
//
//        } else {
//            onDismiss.onDismiss();
//        }
//
//    }
//
//
//// ShowRewarded Close Here ==============
//
//
//    public static void loadRewardedInterstitialAds(Context context) {
//        if (AdsUnit.isAds) {
//            MobileAds.initialize(context, initializationStatus -> {
//            });
//
//            AdRequest adRequest = new AdRequest.Builder().build();
//            RewardedInterstitialAd.load(context, AdsUnit.REWARDED_INTERSTITIAL,
//                    adRequest, new RewardedInterstitialAdLoadCallback() {
//                        @Override
//                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                            AdsUnit.mRewardedInterstitialAd = null;
//                        }
//
//                        @Override
//                        public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
//                            AdsUnit.mRewardedInterstitialAd = rewardedInterstitialAd;
//                        }
//                    });
//        }
//    }
//
//    public void ShowRewardedInterstitial(Activity activity, boolean isReload) {
//        if (AdsUnit.mRewardedInterstitialAd != null) {
//            AdsUnit.mRewardedInterstitialAd.show(activity, rewardItem -> {
//                Toast.makeText(activity, "Reward Collected", Toast.LENGTH_SHORT).show();
//            });
//
//            AdsUnit.mRewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    super.onAdDismissedFullScreenContent();
//
//                    if (isReload) {
//                        AdsUnit.mRewardedInterstitialAd = null;
//                        loadRewardedInterstitialAds(activity);
//                    }
//
//                    onDismiss.onDismiss();
//                }
//
//                @Override
//                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                    super.onAdFailedToShowFullScreenContent(adError);
//                    Toast.makeText(activity, "Please try again later...", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        } else {
//            onDismiss.onDismiss();
//            Toast.makeText(activity, "Ads is not ready. Please try again later...", Toast.LENGTH_SHORT).show();
//        }
//    }
//}



import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

public class Admob {

    static onDismiss onDismiss;

    public Admob(com.bdtopcoder.quickadmob.onDismiss onDismiss) {
        this.onDismiss = onDismiss;
    }

    public Admob() {
    }

    // Open Ads Methods ====================================================================
    public static void loadOpenAd(Context context) {
        if (AdsUnit.isAds) {
            MobileAds.initialize(context, initializationStatus -> {
            });

            AdRequest adRequest = new AdRequest.Builder().build();
            AppOpenAd.load(
                    context,
                    AdsUnit.OPEN_AD,
                    adRequest,
                  //  AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    new AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                            AdsUnit.mOpenAd = appOpenAd;
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            AdsUnit.mOpenAd = null;
                        }
                    }
            );
        }
    }

    public void showOpenAd(Activity activity, boolean isReload) {
        if (AdsUnit.mOpenAd != null) {
            AdsUnit.mOpenAd.show(activity);

            AdsUnit.mOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();

                    if (isReload) {
                        AdsUnit.mOpenAd = null;
                        loadOpenAd(activity);
                    }

                    onDismiss.onDismiss();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    onDismiss.onDismiss();
                }
            });

        } else {
            onDismiss.onDismiss();
        }
    }

    // Existing Methods =====================================================================
    public static void setBanner(LinearLayout banner, Context context) {
        if (AdsUnit.isAds) {
            MobileAds.initialize(context, initializationStatus -> {
            });
            AdView adView = new AdView(context);
            banner.addView(adView);
            adView.setAdUnitId(AdsUnit.BANNER);
            adView.setAdSize(AdSize.BANNER);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
    }

    public static void loadInterstitialAds(Context context) {
        if (AdsUnit.isAds) {
            MobileAds.initialize(context, initializationStatus -> {
            });

            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(context, AdsUnit.INTERSTITIAL, adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            AdsUnit.mInterstitialAd = interstitialAd;
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            AdsUnit.mInterstitialAd = null;
                        }
                    });
        }
    }

    public void ShowInterstitial(Activity activity, boolean isReload) {
        if (AdsUnit.mInterstitialAd != null) {
            AdsUnit.mInterstitialAd.show(activity);

            AdsUnit.mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();

                    if (isReload) {
                        AdsUnit.mInterstitialAd = null;
                        Admob.loadInterstitialAds(activity);
                    }

                    onDismiss.onDismiss();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    onDismiss.onDismiss();
                }
            });

        } else {
            onDismiss.onDismiss();
        }
    }

    public static void loadRewardedInterstitialAds(Context context) {
        if (AdsUnit.isAds) {
            MobileAds.initialize(context, initializationStatus -> {
            });

            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedInterstitialAd.load(context, AdsUnit.REWARDED_INTERSTITIAL,
                    adRequest, new RewardedInterstitialAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            AdsUnit.mRewardedInterstitialAd = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                            AdsUnit.mRewardedInterstitialAd = rewardedInterstitialAd;
                        }
                    });
        }
    }

    public void ShowRewardedInterstitial(Activity activity, boolean isReload) {
        if (AdsUnit.mRewardedInterstitialAd != null) {
            AdsUnit.mRewardedInterstitialAd.show(activity, rewardItem -> {
                Toast.makeText(activity, "Reward Collected", Toast.LENGTH_SHORT).show();
            });

            AdsUnit.mRewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();

                    if (isReload) {
                        AdsUnit.mRewardedInterstitialAd = null;
                        loadRewardedInterstitialAds(activity);
                    }

                    onDismiss.onDismiss();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    Toast.makeText(activity, "Please try again later...", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            onDismiss.onDismiss();
            Toast.makeText(activity, "Ads is not ready. Please try again later...", Toast.LENGTH_SHORT).show();
        }
    }


//
//    public static void loadRewordedAds(Context context) {
//        if (AdsUnit.isAds) {
//            MobileAds.initialize(context, initializationStatus -> {
//            });
//
//            AdRequest adRequest = new AdRequest.Builder().build();
//            RewardedAd.load(context, AdsUnit.REWARDED,
//                    adRequest, new RewardedAdLoadCallback() {
//                        @Override
//                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                            AdsUnit.mRewardedAd = null;
//                        }
//
//                        @Override
//                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
//                            AdsUnit.mRewardedAd = rewardedAd;
//                        }
//                    });
//        }
//    }
//
//    public void ShowRewarded(Activity activity, boolean isReload) {
//        if (AdsUnit.mRewardedAd != null) {
//            AdsUnit.mRewardedAd.show(activity, rewardItem -> {
//                Toast.makeText(activity, "Reward Collected", Toast.LENGTH_SHORT).show();
//            });
//
//            AdsUnit.mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                @Override
//                public void onAdDismissedFullScreenContent() {
//                    super.onAdDismissedFullScreenContent();
//
//                    if (isReload) {
//                        AdsUnit.mRewardedAd = null;
//                        loadRewordedAds(activity);
//                    }
//
//                    onDismiss.onDismiss();
//                }
//
//                @Override
//                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                    super.onAdFailedToShowFullScreenContent(adError);
//                    Toast.makeText(activity, "Please try again later...", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        } else {
//            onDismiss.onDismiss();
//            Toast.makeText(activity, "Ads is not ready. Please try again later...", Toast.LENGTH_SHORT).show();
//        }
//    }
//


}