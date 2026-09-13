package com.mmschooledu;

import android.Manifest;
import android.app.Activity;
import android.content.*;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.*;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bdtopcoder.quickadmob.Admob;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mmschooledu.examresults.ExamMainActivity;
import com.startapp.sdk.adsbase.StartAppAd;

public class Main3Fragment extends Fragment {

    private static final int STORAGE_REQUEST_CODE = 123;
    private static final int RESULT_CODE = 0;

    private NoInternetDialog noInternetDialog;
    private AdView adView;
    private BlogspotAPIManagerStartAds blogspotAPIManagerStardads;

    private CardView kg, kg2, kg3, kg4, kg5, kg6;

    private final BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            boolean show = intent.getBooleanExtra("show", true);
            if (show && getContext() != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main3, container, false);

        noInternetDialog = new NoInternetDialog((Activity) requireContext());
        checkInternetConnection();

        requestAppPermissions();

        blogspotAPIManagerStardads = new BlogspotAPIManagerStartAds(requireContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireContext().registerReceiver(messageStatusReceiver, new IntentFilter("MESSAGE_STATUS_ACTION"),
                    Context.RECEIVER_NOT_EXPORTED);
        }

        blogspotAPIManagerStardads.checkAdsStatus(enabled -> {
            if (enabled) StartAppAd.showAd(requireContext());
           // else Toast.makeText(getContext(), "Lucky Day No Ads for you", Toast.LENGTH_SHORT).show();
        });

        

        adView = view.findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        kg = view.findViewById(R.id.kg);
        kg2 = view.findViewById(R.id.kg2);
        kg3 = view.findViewById(R.id.kg3);
        kg4 = view.findViewById(R.id.kg4);
        kg5 = view.findViewById(R.id.kg5);
        kg6 = view.findViewById(R.id.kg6);

        setupClickListeners();

        Admob.loadInterstitialAds(requireContext());
        Admob.loadRewardedInterstitialAds(requireContext());

        return view;
    }

    private void setupClickListeners() {
        kg.setOnClickListener(v -> new Admob(() -> openWeb("https://www.moe.gov.mm/")).ShowInterstitial(requireActivity(), true));
        kg2.setOnClickListener(v -> new Admob(() -> startActivity(new Intent(requireContext(), ExamMainActivity.class)))
                .ShowRewardedInterstitial(requireActivity(), true));
        kg3.setOnClickListener(v -> new Admob(() -> openWeb("https://sites.google.com/view/haytha-yu-mon/CTK/")).ShowInterstitial(requireActivity(), true));
        kg4.setOnClickListener(v -> new Admob(() -> openWeb("https://www.myanmar-dictionary.org/")).ShowInterstitial(requireActivity(), true));
        kg5.setOnClickListener(v -> new Admob(() -> openWeb("https://www.google.com/search?q=google+translate+Myanmar+to+English"))
                .ShowInterstitial(requireActivity(), true));
        kg6.setOnClickListener(v -> new Admob(() -> openWeb("https://www.mathpapa.com/algebra-calculator.html"))
                .ShowRewardedInterstitial(requireActivity(), true));
    }

    private void openWeb(String url) {
        Intent intent = new Intent(requireContext(), TextBookAll.class);
        intent.putExtra("link", url);
        startActivity(intent);
    }

    

    private void requestAppPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO
            }, RESULT_CODE);
        } else {
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, RESULT_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_REQUEST_CODE) {
            boolean granted = true;
            for (int res : grantResults) {
                if (res != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (!granted) {
                showAlert();
            }
        }
    }

    private void showAlert() {
        new android.app.AlertDialog.Builder(requireContext())
                .setTitle("Alert for Permission")
                .setMessage("Go to Settings for Permissions")
                .setPositiveButton("Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", requireContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    dialog.dismiss();
                })
                .setNegativeButton("Exit", (dialog, which) -> {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Setting မှ Storage Permission ဖွင့်​ပေးမှ စာအုပ်များ Down ယူရရှိနိုင်ပါမည်...", Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
                })
                .show();
    }

    private void checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {
                noInternetDialog.showNoInternet();
            }
        }
    }
}
