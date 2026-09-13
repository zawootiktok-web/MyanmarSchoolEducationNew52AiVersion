package com.mmschooledu;

import android.app.Activity;
import android.util.Log;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class AudienceNetwork {
    private static final String TAG = "AdNetwork";
    private final Activity activity;

    InterstitialAd interstitialAd;
    private int counter = 1;
    private Boolean fullScreenStatus = false;
    private Boolean bannerStatus = false;
    private Dismissed dismissed;
    private String interstitialAdId = "";
    private String bannerAdId = "";
    private int interval = 3;
    private int bannerLayId;

    private boolean loaded = false;
    private boolean loading = false;
    private boolean withClick;

    public interface Dismissed {
        void onclick();
    }

    public AudienceNetwork(Activity activity) {
        this.activity = activity;
    }

    public AudienceNetwork build() {
        if (isActivityUnavailable()) {
            return this;
        }
        loadInterstitialAd();
        loadBannerAd(bannerLayId);
        return this;
    }

    public AudienceNetwork init() {
        if (!isActivityUnavailable()) {
            AudienceNetworkAds.initialize(activity);
        }
        return this;
    }

    public void show(Dismissed dismissed) {
        withClick = dismissed != null;
        this.dismissed = dismissed;
        showInterstitialAd();
    }

    public void show() {
        withClick = false;
        showInterstitialAd();
    }

    public AudienceNetwork setFullScreenStatus(Boolean status) {
        this.fullScreenStatus = status;
        return this;
    }

    public AudienceNetwork setBannerStatus(Boolean status) {
        this.bannerStatus = status;
        return this;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public AudienceNetwork setInterstitialId(String interstitialId) {
        this.interstitialAdId = interstitialId;
        return this;
    }

    public AudienceNetwork setBannerId(String bannerId) {
        this.bannerAdId = bannerId;
        return this;
    }

    public AudienceNetwork setClick(int interval) {
        this.interval = interval;
        return this;
    }

    public AudienceNetwork setBannerLayoutId(int bannerLayoutId) {
        this.bannerLayId = bannerLayoutId;
        return this;
    }

    private boolean isActivityUnavailable() {
        return activity == null || activity.isFinishing() || activity.isDestroyed();
    }

    private void loadInterstitialAd() {
        if (!Boolean.TRUE.equals(fullScreenStatus)
                || isActivityUnavailable()
                || interstitialAdId == null
                || interstitialAdId.trim().isEmpty()
                || loading) {
            return;
        }

        loading = true;
        loaded = false;
        interstitialAd = new InterstitialAd(activity, interstitialAdId);

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // No-op.
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                loaded = false;
                loading = false;
                if (withClick && dismissed != null) {
                    dismissed.onclick();
                }
                loadInterstitialAd();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // A provider/network timeout must not block app navigation.
                loaded = false;
                loading = false;
                Log.w(TAG, "Facebook ad unavailable; continuing without interstitial");
            }

            @Override
            public void onAdLoaded(Ad ad) {
                loaded = true;
                loading = false;
            }

            @Override
            public void onAdClicked(Ad ad) {
                // No-op.
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // No-op.
            }
        };

        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build()
        );
    }

    private void showInterstitialAd() {
        if (!Boolean.TRUE.equals(fullScreenStatus) || isActivityUnavailable()) {
            return;
        }

        if (counter == interval) {
            if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                interstitialAd.show();
            } else {
                invokeDismissedCallback();
                loadInterstitialAd();
            }
            counter = 1;
        } else {
            counter++;
            invokeDismissedCallback();
        }
        Log.d(TAG, "Current counter : " + counter);
    }

    private void invokeDismissedCallback() {
        if (withClick && dismissed != null) {
            dismissed.onclick();
        }
    }

    private void loadBannerAd(int bannerLayoutId) {
        if (!Boolean.TRUE.equals(bannerStatus) || isActivityUnavailable()) {
            return;
        }

        AdView adView = new AdView(activity, bannerAdId, AdSize.BANNER_HEIGHT_50);
        LinearLayout adContainer = activity.findViewById(bannerLayoutId);
        if (adContainer == null) {
            return;
        }
        adContainer.addView(adView);
        adView.loadAd();
    }
}
