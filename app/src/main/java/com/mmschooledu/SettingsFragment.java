//package com.mmschooledu;
//
//import android.app.Activity;
//import android.content.ActivityNotFoundException;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.graphics.Paint;
//import android.graphics.drawable.ShapeDrawable;
//import android.graphics.drawable.shapes.OvalShape;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.bdtopcoder.quickadmob.Admob;
//import com.bdtopcoder.quickadmob.onDismiss;
//import com.mmschooledu.EbookAudioShelf.EbookGalleryActivity;
//import com.mmschooledu.Mp3.AudioOpenActivity;
//import com.mmschooledu.Mp4.VideoOpenActivity;
//import com.startapp.sdk.adsbase.StartAppAd;
//
//import java.util.Objects;
//
//public class SettingsFragment extends Fragment {
//
//    private SharedPreferences sharedPreferences, app_preferences;
//    private SharedPreferences.Editor editor;
//    private Button button;
//    private ImageView[] imageViews = new ImageView[9];
//    private Methods methods;
//    private int appTheme;
//    private int themeColor;
//    private int appColor;
//    private BlogspotAPIManagerStartAds blogspotAPIManagerStardads;
//
//    private final BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String message = intent.getStringExtra("message");
//            boolean show = intent.getBooleanExtra("show", true);
//            if (show) {
//                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        initializePreferences();
//        return inflater.inflate(R.layout.activity_settings, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        initializeComponents(view);
//        setupToolbar(view);
//        setupImageViews(view);
//        setupButton(view);
//        checkAdsStatus();
//    }
//
//    private void initializePreferences() {
//        app_preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
//        appColor = app_preferences.getInt("color", 0);
//        appTheme = app_preferences.getInt("theme", 0);
//        themeColor = appColor;
//        Constant.color = appColor;
//    }
//
//    private void initializeComponents(View view) {
//        blogspotAPIManagerStardads = new BlogspotAPIManagerStartAds(requireContext());
//        methods = new Methods();
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
//        editor = sharedPreferences.edit();
//        button = view.findViewById(R.id.button_color);
//    }
//
//    private void setupToolbar(View view) {
//        Toolbar toolbar = view.findViewById(R.id.toolbar_setting);
//        toolbar.setTitle("Settings");
//        toolbar.setBackgroundColor(Constant.color);
//        if (getActivity() instanceof AppCompatActivity) {
//            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    private void setupImageViews(View view) {
//        imageViews[0] = view.findViewById(R.id.image);
//        imageViews[1] = view.findViewById(R.id.image2);
//        imageViews[2] = view.findViewById(R.id.image3);
//        imageViews[3] = view.findViewById(R.id.image4);
//        imageViews[4] = view.findViewById(R.id.image5);
//        imageViews[5] = view.findViewById(R.id.image6);
//        imageViews[6] = view.findViewById(R.id.image7);
//        imageViews[7] = view.findViewById(R.id.image22);
//        imageViews[8] = view.findViewById(R.id.image33);
//
//        setImageClickListeners();
//    }
//
//    private void setImageClickListeners() {
//        imageViews[0].setOnClickListener(v -> openActivity(ChatActivity.class));
//        imageViews[1].setOnClickListener(v -> openActivity(EbookGalleryActivity.class));
//        imageViews[2].setOnClickListener(v -> openActivity(ImageViewerActivity.class));
//        imageViews[6].setOnClickListener(v -> openActivity(AboutUsActivity.class));
//        imageViews[7].setOnClickListener(v -> openActivity(VideoOpenActivity.class));
//        imageViews[8].setOnClickListener(v -> openActivity(AudioOpenActivity.class));
//
//        imageViews[3].setOnClickListener(v -> showAdAndOpenLink("https://t.me/myanmarschooleducation"));
//        imageViews[4].setOnClickListener(v -> showAdAndOpenLink("https://youtube.com/@myanmarschooleducation9624?si=h9sOaO7Yl1n7aK6t"));
//        imageViews[5].setOnClickListener(v -> showAdAndOpenLink("https://www.facebook.com/profile.php?id=100088521429730&mibextid=ZbWKwL"));
//    }
//
//    private void setupButton(View view) {
//        button = view.findViewById(R.id.button_color);
//        colorize();
//        button.setOnClickListener(v -> showColorPickerDialog());
//    }
//
//    private void checkAdsStatus() {
//        IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            requireContext().registerReceiver(messageStatusReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
//        }
//
//        blogspotAPIManagerStardads.checkAdsStatus(isEnabled -> {
//            if (isEnabled) {
//                StartAppAd.showAd(requireContext());
//            }
//        });
//    }
//
//    private void showColorPickerDialog() {
//        ColorChooserDialog dialog = new ColorChooserDialog(requireContext());
//        dialog.setTitle("Select");
//        dialog.setColorListener((v, color) -> {
//            Constant.color = color;
//            methods.setColorTheme();
//            editor.putInt("color", color);
//            editor.putInt("theme", Constant.theme);
//            editor.apply();
//
//            restartActivity();
//        });
//        dialog.show();
//    }
//
//    private void restartActivity() {
////        Intent intent = new Intent(requireContext(), HomeFragment.class);
////        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        startActivity(intent);
//
////        Intent intent = new Intent(getActivity(), HomeFragment.class);
////        startActivity(intent);
//
//
//    }
//
//    private void openActivity(Class<?> activityClass) {
//        startActivity(new Intent(requireContext(), activityClass));
//    }
////
////    private void restartActivity() {
////        // Navigate to HomeFragment
////        navigateToFragment(new HomeFragment());
////    }
////
////    private void navigateToFragment(Fragment fragment) {
////        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
////        transaction.replace(R.id.fragment_container, fragment);
////        transaction.addToBackStack(null); // Optional: Add to back stack
////        transaction.commit();
////    }
//
//    private void showAdAndOpenLink(String url) {
//        new Admob(() -> openLink(url)).ShowInterstitial((Activity) requireContext(), true);
//    }
//
//    private void openLink(String url) {
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        try {
//            startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//        }
//    }
//
//    private void colorize() {
//        ShapeDrawable d = new ShapeDrawable(new OvalShape());
//        d.setBounds(58, 58, 58, 58);
//        d.getPaint().setStyle(Paint.Style.FILL);
//        d.getPaint().setColor(Constant.color);
//        button.setBackground(d);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        requireContext().unregisterReceiver(messageStatusReceiver);
//    }
//}


package com.mmschooledu;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bdtopcoder.quickadmob.Admob;
import com.mmschooledu.EbookAudioShelf.EbookGalleryActivity;
import com.mmschooledu.Mp3.AudioOpenActivity;
import com.mmschooledu.Mp4.VideoOpenActivity;
import com.startapp.sdk.adsbase.StartAppAd;

import java.util.Objects;

public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPreferences, appPreferences;
    private SharedPreferences.Editor editor;
    private Button colorButton;
    private ImageView[] imageViews = new ImageView[9];
    private Methods methods;
    private int appTheme, themeColor, appColor;
    private BlogspotAPIManagerStartAds adsManager;

    private final BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            if (intent.getBooleanExtra("show", true)) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initPreferences();
        return inflater.inflate(R.layout.activity_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupToolbar(view);
        setupListeners(view);
        observeAds();
    }

    private void initPreferences() {
        appPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        appColor = appPreferences.getInt("color", 0);
        appTheme = appPreferences.getInt("theme", 0);
        themeColor = appColor;
        Constant.color = appColor;
    }

    private void initViews(View view) {
        methods = new Methods();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        editor = sharedPreferences.edit();
        adsManager = new BlogspotAPIManagerStartAds(requireContext());
        colorButton = view.findViewById(R.id.button_color);

        // ImageView assignments
        int[] imageIds = {
                R.id.image, R.id.image2, R.id.image3, R.id.image4, R.id.image5,
                R.id.image6, R.id.image7, R.id.image22, R.id.image33
        };

        for (int i = 0; i < imageIds.length; i++) {
            imageViews[i] = view.findViewById(imageIds[i]);
        }

        colorize();
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar_setting);
        toolbar.setTitle(R.string.settings_title);
        toolbar.setBackgroundColor(Constant.color);
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupListeners(View view) {
        colorButton.setOnClickListener(v -> showColorPickerDialog());

        imageViews[0].setOnClickListener(v -> openActivity(ChatActivity.class));
        imageViews[1].setOnClickListener(v -> openActivity(EbookGalleryActivity.class));
        imageViews[2].setOnClickListener(v -> openActivity(ImageViewerActivity.class));
        imageViews[3].setOnClickListener(v -> showAdAndOpenLink("https://t.me/myanmarschooleducation"));
        imageViews[4].setOnClickListener(v -> showAdAndOpenLink("https://youtube.com/@myanmarschooleducation9624"));
        imageViews[5].setOnClickListener(v -> showAdAndOpenLink("https://www.facebook.com/profile.php?id=100088521429730"));
        imageViews[6].setOnClickListener(v -> openActivity(AboutUsActivity.class));
        imageViews[7].setOnClickListener(v -> openActivity(VideoOpenActivity.class));
        imageViews[8].setOnClickListener(v -> openActivity(AudioOpenActivity.class));
    }

    private void observeAds() {
        IntentFilter filter = new IntentFilter("MESSAGE_STATUS_ACTION");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireContext().registerReceiver(messageStatusReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            requireContext().registerReceiver(messageStatusReceiver, filter);
        }

        adsManager.checkAdsStatus(isEnabled -> {
            if (isEnabled) {
                StartAppAd.showAd(requireContext());
            }
        });
    }

    private void showColorPickerDialog() {
        ColorChooserDialog dialog = new ColorChooserDialog(requireContext());
        dialog.setTitle(R.string.select_language);
        dialog.setColorListener((v, color) -> {
            Constant.color = color;
            methods.setColorTheme();
            editor.putInt("color", color);
            editor.putInt("theme", Constant.theme);
            editor.apply();
            restartActivity();
        });
        dialog.show();
    }

    private void restartActivity() {
        requireActivity().recreate(); // Safely restart the current Activity hosting the fragment
    }

    private void openActivity(Class<?> activityClass) {
        startActivity(new Intent(requireContext(), activityClass));
    }

    private void showAdAndOpenLink(String url) {
        new Admob(() -> openLink(url)).ShowInterstitial((Activity) requireContext(), true);
    }

    private void openLink(String url) {
        if (!isAdded() || url == null || url.trim().isEmpty()) {
            return;
        }
        Uri uri = Uri.parse(url.trim());
        if (!"https".equalsIgnoreCase(uri.getScheme())) {
            Toast.makeText(requireContext(), R.string.link_not_secure, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(), R.string.browser_telegram_missing, Toast.LENGTH_SHORT).show();
        }
    }

    private void colorize() {
        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setStyle(Paint.Style.FILL);
        drawable.getPaint().setColor(Constant.color);
        colorButton.setBackground(drawable);
    }

    @Override
    public void onDestroyView() {
        Context context = getContext();
        if (context != null) {
            try {
                context.unregisterReceiver(messageStatusReceiver);
            } catch (IllegalArgumentException ignored) {
                // Receiver may already be unregistered during host teardown.
            }
        }
        super.onDestroyView();
    }
}
