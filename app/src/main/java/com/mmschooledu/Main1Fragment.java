package com.mmschooledu;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bdtopcoder.quickadmob.Admob;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mmschooledu.Dictionary.OnlineDictionary;
import com.mmschooledu.EbookAudioShelf.EbookGalleryActivity;
import com.mmschooledu.MmToEnglishName.MmToEngActivity;
import com.mmschooledu.MyanmarSpelling.activity.MmSpellingMainActivity;
import com.mmschooledu.Story.activity.StoryMainActivity;
import com.mmschooledu.UnitConverter.UnitConverterActivity;
import com.startapp.sdk.adsbase.StartAppAd;

import java.util.ArrayList;
import java.util.List;

public class Main1Fragment extends Fragment {

    private NoInternetDialog noInternetDialog;
    private AdView adView;
    private String TAG = "HomeFragment";
    private CardView kg, kg2, kg3, kg4, kg5, kg6, kg7, kg8;
    private BlogspotAPIManagerFbAds blogspotAPIManagerFbads;
    private InterstitialAd interstitialAd;
    private BlogspotAPIManagerStartAds blogspotAPIManagerStardads;

    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            boolean show = intent.getBooleanExtra("show", true);
            if (show) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permission -> {
                boolean allGranted = true;

                for (Boolean isGranted : permission.values()) {
                    if (!isGranted) {
                        allGranted = false;
                        break;
                    }
                }

                if (allGranted) {
                    // All is granted
                } else {
                    // All is not granted
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main1, container, false);

        initializeViews(view);
        setupAdManager();
        setupClickListeners();
        checkInternetConnection();
        myPermissions();

        // Register the BroadcastReceiver to receive message status updates
        IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().registerReceiver(messageStatusReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
        }

        return view;
    }

    private void initializeViews(View view) {
        adView = view.findViewById(R.id.ad_view);
        kg = view.findViewById(R.id.kg);
        kg2 = view.findViewById(R.id.kg2);
        kg3 = view.findViewById(R.id.kg3);
        kg4 = view.findViewById(R.id.kg4);
        kg5 = view.findViewById(R.id.kg5);
        kg6 = view.findViewById(R.id.kg6);
        kg7 = view.findViewById(R.id.kg7);
        kg8 = view.findViewById(R.id.kg8);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void setupAdManager() {
        blogspotAPIManagerStardads = new BlogspotAPIManagerStartAds(requireActivity());
        blogspotAPIManagerFbads = new BlogspotAPIManagerFbAds(requireActivity());

        // Check ads status
        blogspotAPIManagerStardads.checkAdsStatus(new BlogspotAPIManagerStartAds.OnAdsStatusListener() {
            @Override
            public void onAdsStatus(boolean isEnabled) {
                if (isEnabled) {
                    showAds();
                } else {
                    hideAds();
                }
            }

            private void showAds() {
                StartAppAd.showAd(requireContext());
            }

            private void hideAds() {
                // Hide ads
            }
        });

        // Check FB ads status
        blogspotAPIManagerFbads.checkAdsStatus(new BlogspotAPIManagerFbAds.OnAdsStatusListener() {
            @Override
            public void onAdsStatus(boolean isEnabled) {
                if (isEnabled) {
                    showAds();
                } else {
                    hideAds();
                }
            }

            private void showAds() {
                loadInterstitialAd();
            }

            private void hideAds() {
              //  Toast.makeText(requireActivity(), "Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
            }
        });

        // SDK Initialize
        Admob.loadInterstitialAds(requireActivity());
        Admob.loadRewardedInterstitialAds(requireActivity());
    }

    private void setupClickListeners() {
        kg.setOnClickListener(v -> new Admob(() -> {
            Intent i = new Intent(requireActivity(), OnlineDictionary.class);
            startActivity(i);
            StartAppAd.showAd(requireContext());
        }).ShowInterstitial(requireActivity(), true));

        kg2.setOnClickListener(v -> new Admob(() -> {
            Intent i = new Intent(requireActivity(), MmSpellingMainActivity.class);
            startActivity(i);
        }).ShowInterstitial(requireActivity(), true));

        kg3.setOnClickListener(v -> new Admob(() -> {
            Intent i = new Intent(requireActivity(), StoryMainActivity.class);
            startActivity(i);
        }).ShowInterstitial(requireActivity(), true));

        kg4.setOnClickListener(v -> new Admob(() -> {
            Intent i = new Intent(requireActivity(), AgeCalculatorActivity.class);
            startActivity(i);
        }).ShowInterstitial(requireActivity(), true));

        kg5.setOnClickListener(v -> new Admob(() -> {
            Intent i = new Intent(requireActivity(), UnitConverterActivity.class);
            startActivity(i);
        }).ShowInterstitial(requireActivity(), true));

        kg6.setOnClickListener(v -> new Admob(() -> {
            Intent i = new Intent(requireActivity(), EbookGalleryActivity.class);
            startActivity(i);
        }).ShowRewardedInterstitial(requireActivity(), true));

        kg7.setOnClickListener(v -> new Admob(() -> {
            Intent i = new Intent(requireActivity(), ChatActivity.class);
            startActivity(i);
        }).ShowInterstitial(requireActivity(), true));

        kg8.setOnClickListener(v -> new Admob(() -> {
            Intent i = new Intent(requireActivity(), MmToEngActivity.class);
            startActivity(i);
        }).ShowInterstitial(requireActivity(), true));
    }

    private void loadInterstitialAd() {
        interstitialAd = new InterstitialAd(requireActivity(), "1072549720831975_1325325095554435");
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(new InterstitialAdListener() {
                            @Override
                            public void onInterstitialDisplayed(Ad ad) {
                                Log.d(TAG, "Interstitial ad displayed.");
                            }

                            @Override
                            public void onInterstitialDismissed(Ad ad) {
                                Log.d(TAG, "Interstitial ad dismissed.");
                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {
                                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {
                                Log.d(TAG, "Interstitial ad loaded.");
                                interstitialAd.show();
                            }

                            @Override
                            public void onAdClicked(Ad ad) {
                                Log.d(TAG, "Interstitial ad clicked.");
                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {
                                Log.d(TAG, "Interstitial ad impression logged.");
                            }
                        })
                        .build());
    }

    private void checkInternetConnection() {
        noInternetDialog = new NoInternetDialog(requireActivity());
    }

    private void myPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            String[] permissions = new String[]{
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_AUDIO,
                    android.Manifest.permission.READ_MEDIA_VIDEO,
                    android.Manifest.permission.CAMERA,
            };

            handlePermissionRequest(permissions);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
            };

            handlePermissionRequest(permissions);
        }
    }

    private void handlePermissionRequest(String[] permissions) {
        List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(requireActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (permissionsToRequest.isEmpty()) {
            Toast.makeText(requireActivity(), "All permissions are already granted", Toast.LENGTH_SHORT).show();
        } else {
            String[] permissionsArray = permissionsToRequest.toArray(new String[0]);
            boolean shouldShowRationale = false;

            for (String permission : permissionsArray) {
                if (shouldShowRequestPermissionRationale(permission)) {
                    shouldShowRationale = true;
                    break;
                }
            }

            if (shouldShowRationale) {
                new AlertDialog.Builder(requireActivity())
                        .setMessage("Please allow all permissions")
                        .setCancelable(false)
                        .setPositiveButton("YES", (dialogInterface, i) -> requestPermissionLauncher.launch(permissionsArray))
                        .setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss())
                        .show();
            } else {
                requestPermissionLauncher.launch(permissionsArray);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (noInternetDialog != null) {
          //  noInternetDialog.onDestroy();
        }
        if (messageStatusReceiver != null) {
            requireActivity().unregisterReceiver(messageStatusReceiver);
        }
        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
    }
}