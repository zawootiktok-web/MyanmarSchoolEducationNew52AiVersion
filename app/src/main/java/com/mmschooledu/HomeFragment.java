//package com.mmschooledu;
//
//import android.app.AlertDialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Handler;
//import android.preference.PreferenceManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//
//import com.bdtopcoder.quickadmob.Admob;
//import com.bdtopcoder.quickadmob.onDismiss;
//import com.mmschooledu.Cinema.CinemaLessonActivity;
//import com.mmschooledu.EbookAudioShelf.EbookMainActivity;
//import com.mmschooledu.Mp3.Mp3Mp4MainActivity;
//import com.mmschooledu.QandAnswer.QandAActivity;
//
//import java.util.Objects;
//
//public class HomeFragment extends Fragment {
//    private static final int STORAGE_REQUEST_CODE = 100;
//    private Constant constant;
//    private SharedPreferences.Editor editor;
//    private SharedPreferences app_preferences;
//    private int appTheme;
//    private int themeColor;
//    private int appColor;
//
//    private Handler handler;
//    private Runnable geolocationRunnable;
//    private Runnable dialogRunnable;
//    private Runnable showDialogRunnable;
//
//    private CardView kg, kg2, kg3, kg4, kg5, kg6, kg7, kg8, kg9, kg10;
//    private TextView text01, text02, text, text2, text3, text4, text5, text6, text7, text8, text9, text10;
//    private BlogspotAPIManager blogspotAPIManager;
//
//    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String message = intent.getStringExtra("message");
//            boolean show = intent.getBooleanExtra("show", true);
//            if (show) {
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_homenew, container, false);
//
//        // Initialize views
//        initializeViews(view);
//
//        // Setup theme and colors
//        setupTheme();
//
//        // Initialize BlogspotAPIManager
//        blogspotAPIManager = new BlogspotAPIManager(getActivity());
//
//        // Check ads status
//        checkAdsStatus();
//
//        // Setup translations
//        setupTranslations();
//
//        // Setup click listeners
//        setupClickListeners();
//
//        // Register broadcast receiver
//        registerBroadcastReceiver();
//
//        // Check geolocation
//        GeolocationUtils.checkCountryAndShowDialog(getActivity());
//
//        return view;
//    }
//
//    private void initializeViews(View view) {
//        text01 = view.findViewById(R.id.text01);
//        text02 = view.findViewById(R.id.text02);
//        text = view.findViewById(R.id.text);
//        text2 = view.findViewById(R.id.text2);
//        text3 = view.findViewById(R.id.text3);
//        text4 = view.findViewById(R.id.text4);
//        text5 = view.findViewById(R.id.text5);
//        text6 = view.findViewById(R.id.text6);
//        text7 = view.findViewById(R.id.text7);
//        text8 = view.findViewById(R.id.text8);
//        text9 = view.findViewById(R.id.text9);
//        text10 = view.findViewById(R.id.text10);
//
//        kg = view.findViewById(R.id.kg);
//        kg2 = view.findViewById(R.id.kg2);
//        kg3 = view.findViewById(R.id.kg3);
//        kg4 = view.findViewById(R.id.kg4);
//        kg5 = view.findViewById(R.id.kg5);
//        kg6 = view.findViewById(R.id.kg6);
//        kg7 = view.findViewById(R.id.kg7);
//        kg8 = view.findViewById(R.id.kg8);
//        kg9 = view.findViewById(R.id.kg9);
//        kg10 = view.findViewById(R.id.kg10);
//
//        ImageView imageview = view.findViewById(R.id.iconTopRight);
//        imageview.setOnClickListener(v -> {
//            new Admob(new onDismiss() {
//                @Override
//                public void onDismiss() {
//                    Intent intent = new Intent(getActivity(), SettingsActivity.class);
//                    startActivity(intent);
//                }
//            }).ShowInterstitial(getActivity(), true);
//        });
//    }
//
//    private void setupTheme() {
//        app_preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        appColor = app_preferences.getInt("color", 0);
//        appTheme = app_preferences.getInt("theme", 0);
//        themeColor = appColor;
//        Constant.color = appColor;
//
//        if (getActivity() != null) {
//            getActivity().getWindow().getDecorView().setBackgroundColor(Constant.color);
//
//            if (themeColor == 0) {
//                getActivity().setTheme(Constant.theme);
//            } else if (appTheme == 0) {
//                getActivity().setTheme(Constant.theme);
//            } else {
//                getActivity().setTheme(appTheme);
//            }
//        }
//    }
//
//    private void checkAdsStatus() {
//        blogspotAPIManager.checkAdsStatus(new BlogspotAPIManager.OnAdsStatusListener() {
//            @Override
//            public void onAdsStatus(boolean isEnabled) {
//                if (isEnabled) {
//                    // Show ads
//                    Intent i = new Intent(getActivity(), AdsActivity.class);
//                    startActivity(i);
//                }
//                // No action needed when ads are disabled
//            }
//        });
//    }
//
//    private void setupTranslations() {
//        SharedPreferences prefs = getActivity().getSharedPreferences("translate", Context.MODE_PRIVATE);
//        int id = prefs.getInt("id", 0);
//
//        // Setup translation for each text view
//        setupTextViewTranslation(text, R.string.textnew, id);
//        setupTextViewTranslation(text2, R.string.text2new, id);
//        setupTextViewTranslation(text3, R.string.text3new, id);
//        setupTextViewTranslation(text4, R.string.text4new, id);
//        setupTextViewTranslation(text5, R.string.text5new, id);
//        setupTextViewTranslation(text6, R.string.text6new, id);
//        setupTextViewTranslation(text01, R.string.text01new, id);
//        setupTextViewTranslation(text02, R.string.text02new, id);
//        setupTextViewTranslation(text7, R.string.text7new, id);
//        setupTextViewTranslation(text8, R.string.text8new, id);
//        setupTextViewTranslation(text9, R.string.text9new, id);
//        setupTextViewTranslation(text10, R.string.text10new, id);
//    }
//
//    private void setupTextViewTranslation(TextView textView, int stringResId, int translationId) {
//        if (translationId == 0) {
//            TranslateAPI translateAPI = new TranslateAPI(
//                    Language.AUTO_DETECT,
//                    Language.BURMESE,
//                    getString(stringResId));
//
//            translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
//                @Override
//                public void onSuccess(String translatedText) {
//                    textView.setText(translatedText);
//                }
//
//                @Override
//                public void onFailure(String ErrorText) {
//                    // Fallback to original text if translation fails
//                    textView.setText(getString(stringResId));
//                }
//            });
//        } else {
//            textView.setText(getString(stringResId));
//        }
//    }
//
//    private void setupClickListeners() {
//        // Setup click listeners for all CardViews
//        kg.setOnClickListener(v -> launchActivityWithAd(TextBook.class));
//        kg2.setOnClickListener(v -> launchActivityWithAd(TeacherGuide.class));
//        kg3.setOnClickListener(v -> launchActivityWithAd(LessonActivity.class));
//        kg4.setOnClickListener(v -> launchActivityWithAd(STeachingBook.class));
//        kg5.setOnClickListener(v -> launchActivityWithAd(QandAActivity.class));
//        kg6.setOnClickListener(v -> launchActivityWithAd(Mp3Mp4MainActivity.class));
//        kg7.setOnClickListener(v -> launchActivityWithAd(EbookMainActivity.class));
//        kg8.setOnClickListener(v -> launchActivityWithAd(CinemaLessonActivity.class));
//        kg9.setOnClickListener(v -> launchActivityWithAd(Main1.class));
//        kg10.setOnClickListener(v -> launchActivityWithAd(Main3.class));
//    }
//
//    private void launchActivityWithAd(Class<?> activityClass) {
//        new Admob(new onDismiss() {
//            @Override
//            public void onDismiss() {
//                Intent i = new Intent(getActivity(), activityClass);
//                startActivity(i);
//            }
//        }).ShowInterstitial(getActivity(), true);
//    }
//
//    private void registerBroadcastReceiver() {
//        IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
//        getActivity().registerReceiver(messageStatusReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        // Unregister broadcast receiver when fragment is destroyed
//        if (getActivity() != null) {
//            getActivity().unregisterReceiver(messageStatusReceiver);
//        }
//    }
//
//    private void showNoInternet() {
//        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
//        View parent_view = LayoutInflater.from(getActivity()).inflate(R.layout.nointernet_dia, null);
//        dialog.setView(parent_view);
//        dialog.setCancelable(false);
//        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.show();
//
//        TextView tv_title = parent_view.findViewById(R.id.dialogTextView1);
//        Button button = parent_view.findViewById(R.id.dialogButton1);
//
//        tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ခြင်းမရှိပါ။ ​\u200Bကျေးဇူး​ပြု၍ အင်တာနက် ဆက်သွယ်​\u200Bပေးပါ။");
//        button.setText("...ဟုတ်ကဲ့...");
//
//        button.setOnClickListener(v -> {
//            if (getActivity() != null) {
//                getActivity().finish();
//            }
//        });
//    }
//}


//package com.mmschooledu;
//
//import android.app.AlertDialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Handler;
//import android.preference.PreferenceManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//
//import com.bdtopcoder.quickadmob.Admob;
//import com.bdtopcoder.quickadmob.onDismiss;
//import com.mmschooledu.Cinema.CinemaLessonActivity;
//import com.mmschooledu.EbookAudioShelf.EbookMainActivity;
//import com.mmschooledu.Mp3.Mp3Mp4MainActivity;
//import com.mmschooledu.QandAnswer.QandAActivity;
//
//import java.util.Objects;
//
//public class HomeFragment extends Fragment {
//
//    private static final int STORAGE_REQUEST_CODE = 100;
//    private SharedPreferences app_preferences;
//    private int themeColor;
//    private View rootView;
//
//    private CardView kg, kg2, kg3, kg4, kg5, kg6, kg7, kg8, kg9, kg10;
//    private TextView text01, text02, greeting,text, text2, text3, text4, text5, text6, text7, text8, text9, text10;
//    private BlogspotAPIManager blogspotAPIManager;
//
//    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String message = intent.getStringExtra("message");
//            boolean show = intent.getBooleanExtra("show", true);
//            if (show) {
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.fragment_homenew, container, false);
//
//        initializeViews(rootView);
//        setupTheme();
//        blogspotAPIManager = new BlogspotAPIManager(getActivity());
//        checkAdsStatus();
//        setupTranslations();
//        setupClickListeners();
//        registerBroadcastReceiver();
//        GeolocationUtils.checkCountryAndShowDialog(getActivity());
//
//        return rootView;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        applyThemeColor();  // Refresh color when fragment becomes visible
//    }
//
//    private void applyThemeColor() {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
//        int color = preferences.getInt("color", getResources().getColor(R.color.default_color));
//        Constant.color = color;
//
//        if (getActivity() != null) {
//            getActivity().getWindow().getDecorView().setBackgroundColor(Constant.color);
//        }
//
//        if (rootView != null) {
//            rootView.setBackgroundColor(Constant.color);
//        }
//    }
//
//    private void initializeViews(View view) {
//        text01 = view.findViewById(R.id.text01);
//        text02 = view.findViewById(R.id.text02);
//        greeting = view.findViewById(R.id.greeting);
//        text = view.findViewById(R.id.text);
//        text2 = view.findViewById(R.id.text2);
//        text3 = view.findViewById(R.id.text3);
//        text4 = view.findViewById(R.id.text4);
//        text5 = view.findViewById(R.id.text5);
//        text6 = view.findViewById(R.id.text6);
//        text7 = view.findViewById(R.id.text7);
//        text8 = view.findViewById(R.id.text8);
//        text9 = view.findViewById(R.id.text9);
//        text10 = view.findViewById(R.id.text10);
//
//        kg = view.findViewById(R.id.kg);
//        kg2 = view.findViewById(R.id.kg2);
//        kg3 = view.findViewById(R.id.kg3);
//        kg4 = view.findViewById(R.id.kg4);
//        kg5 = view.findViewById(R.id.kg5);
//        kg6 = view.findViewById(R.id.kg6);
//        kg7 = view.findViewById(R.id.kg7);
//        kg8 = view.findViewById(R.id.kg8);
//        kg9 = view.findViewById(R.id.kg9);
//        kg10 = view.findViewById(R.id.kg10);
////
////        ImageView imageview = view.findViewById(R.id.iconTopRight);
////        imageview.setOnClickListener(v -> {
////            new Admob(() -> {
//////                Intent intent = new Intent(getActivity(), SettingsActivity.class);
//////                startActivity(intent);
////            }).ShowInterstitial(getActivity(), true);
////        });
//    }
//
//    private void setupTheme() {
//        app_preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        int appColor = app_preferences.getInt("color", getResources().getColor(R.color.default_color));
//        int appTheme = app_preferences.getInt("theme", Constant.theme);
//        themeColor = appColor;
//        Constant.color = appColor;
//
//        if (getActivity() != null) {
//            getActivity().getWindow().getDecorView().setBackgroundColor(Constant.color);
//            getActivity().setTheme(appTheme);
//        }
//    }
//
//    private void checkAdsStatus() {
//        blogspotAPIManager.checkAdsStatus(isEnabled -> {
//            if (isEnabled) {
////              Intent i = new Intent(getActivity(), AdsActivity.class);
//                Intent i = new Intent(getActivity(), CalendarActivity.class);
//                startActivity(i);
//            }
//        });
//    }
//
//    private void setupTranslations() {
//        SharedPreferences prefs = getActivity().getSharedPreferences("translate", Context.MODE_PRIVATE);
//        int id = prefs.getInt("id", 0);
//
//        setupTextViewTranslation(text, R.string.textnew, id);
//        setupTextViewTranslation(text2, R.string.text2new, id);
//        setupTextViewTranslation(greeting, R.string.greetingText, id);
//        setupTextViewTranslation(text3, R.string.text3new, id);
//        setupTextViewTranslation(text4, R.string.text4new, id);
//        setupTextViewTranslation(text5, R.string.text5new, id);
//        setupTextViewTranslation(text6, R.string.text6new, id);
//        setupTextViewTranslation(text01, R.string.text01new, id);
//        setupTextViewTranslation(text02, R.string.text02new, id);
//        setupTextViewTranslation(text7, R.string.text7new, id);
//        setupTextViewTranslation(text8, R.string.text8new, id);
//        setupTextViewTranslation(text9, R.string.text9new, id);
//        setupTextViewTranslation(text10, R.string.text10new, id);
//    }
//
//    private void setupTextViewTranslation(TextView textView, int stringResId, int translationId) {
//        if (translationId == 0) {
//            TranslateAPI translateAPI = new TranslateAPI(
//                    Language.AUTO_DETECT,
//                    Language.BURMESE,
//                    getString(stringResId));
//
//            translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
//                @Override
//                public void onSuccess(String translatedText) {
//                    textView.setText(translatedText);
//                }
//
//                @Override
//                public void onFailure(String ErrorText) {
//                    textView.setText(getString(stringResId));
//                }
//            });
//        } else {
//            textView.setText(getString(stringResId));
//        }
//    }
//
//    private void setupClickListeners() {
//        kg.setOnClickListener(v -> launchActivityWithAd(TextBook.class));
//        kg2.setOnClickListener(v -> launchActivityWithAd(TeacherGuide.class));
//        kg3.setOnClickListener(v -> launchActivityWithAd(LessonActivity.class));
//        kg4.setOnClickListener(v -> launchActivityWithAd(STeachingBook.class));
//        kg5.setOnClickListener(v -> launchActivityWithAd(QandAActivity.class));
//        kg6.setOnClickListener(v -> launchActivityWithAd(Mp3Mp4MainActivity.class));
//        kg7.setOnClickListener(v -> launchActivityWithAd(EbookMainActivity.class));
//        kg8.setOnClickListener(v -> launchActivityWithAd(CinemaLessonActivity.class));
//        kg9.setOnClickListener(v -> launchActivityWithAd(Main1.class));
//        kg10.setOnClickListener(v -> launchActivityWithAd(Main3.class));
//    }
//
//    private void launchActivityWithAd(Class<?> activityClass) {
//        new Admob(() -> {
//            Intent i = new Intent(getActivity(), activityClass);
//            startActivity(i);
//        }).ShowInterstitial(getActivity(), true);
//    }
//
//    private void registerBroadcastReceiver() {
//        IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
//        getActivity().registerReceiver(messageStatusReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (getActivity() != null) {
//            getActivity().unregisterReceiver(messageStatusReceiver);
//        }
//    }
//
//    private void showNoInternet() {
//        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
//        View parent_view = LayoutInflater.from(getActivity()).inflate(R.layout.nointernet_dia, null);
//        dialog.setView(parent_view);
//        dialog.setCancelable(false);
//        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.show();
//
//        TextView tv_title = parent_view.findViewById(R.id.dialogTextView1);
//        Button button = parent_view.findViewById(R.id.dialogButton1);
//
//        tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ခြင်းမရှိပါ။ ​ကျေးဇူး​ပြု၍ အင်တာနက် ဆက်သွယ်​ပေးပါ။");
//        button.setText("...ဟုတ်ကဲ့...");
//
//        button.setOnClickListener(v -> {
//            if (getActivity() != null) {
//                getActivity().finish();
//            }
//        });
//    }
//}


//
//
//
//package com.mmschooledu;
//
//import static androidx.core.content.ContextCompat.registerReceiver;
//
//import android.app.AlertDialog;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//
//import com.bdtopcoder.quickadmob.Admob;
//import com.bumptech.glide.Glide;
//import com.mmschooledu.Cinema.CinemaLessonActivity;
//import com.mmschooledu.EbookAudioShelf.EbookMainActivity;
//import com.mmschooledu.Mp3.Mp3Mp4MainActivity;
//import com.mmschooledu.QandAnswer.QandAActivity;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//import java.util.Objects;
//
//public class HomeFragment extends Fragment {
//
//    private static final int STORAGE_REQUEST_CODE = 100;
//    private SharedPreferences app_preferences;
//    private int themeColor;
//    private View rootView;
//
//    // Original UI Elements
//    private CardView kg, kg2, kg3, kg4, kg5, kg6, kg7, kg8, kg9, kg10;
//    private TextView text01, text02, greeting, text, text2, text3, text4, text5, text6, text7, text8, text9, text10;
//    private BlogspotAPIManager blogspotAPIManager;
//
//    // *** NEW: Daily Status UI Elements (အသစ်ထပ်ထည့်ထားသော အပိုင်း) ***
//    private CardView cardDailyStatus;
//    private TextView tvStatusText;
//    private ImageView imgStatusIcon;
//    private String dailyLink = "";
//
//    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String message = intent.getStringExtra("message");
//            boolean show = intent.getBooleanExtra("show", true);
//            if (show) {
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.fragment_homenew, container, false);
//
//        initializeViews(rootView);
//        setupTheme();
//
//        blogspotAPIManager = new BlogspotAPIManager(getActivity());
//
//        // Original Logic
//        checkAdsStatus();
//        setupTranslations();
//        setupClickListeners();
//        registerBroadcastReceiver();
//        GeolocationUtils.checkCountryAndShowDialog(getActivity());
//
//        // *** NEW: Start Fetching Daily Status (Blogger မှ Data စဆွဲမည်) ***
//        fetchDailyStatus();
//
//
//
//
//// Initialize BlogspotAPIManager
//        blogspotAPIManager = new BlogspotAPIManager(this);
//
//
//
//        // requestConsform();
//
//        String fillViewport;
//        android:
//        fillViewport = "true";
//
//        //  new CountryCheckAsyncTask().execute();
//
//        String check_update_json = "http://2020goodblog.blogspot.com/2022/07/myanmarschooleducationapk.html";
//        AppUpdater appUpdater = new AppUpdater(this, check_update_json);
//        boolean Context = false;
//        appUpdater.check(Context);
//
//        //  requestAppPermissions();
//
//
//        // Register the BroadcastReceiver to receive message status updates
//        IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
//        registerReceiver(messageStatusReceiver, intentFilter, android.content.Context.RECEIVER_NOT_EXPORTED);
//
//
//
//
//
//        return rootView;
//    }
//
//
//
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        applyThemeColor();  // Refresh color when fragment becomes visible
//    }
//
//    private void applyThemeColor() {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
//        int color = preferences.getInt("color", getResources().getColor(R.color.default_color));
//        Constant.color = color;
//
//        if (getActivity() != null) {
//            getActivity().getWindow().getDecorView().setBackgroundColor(Constant.color);
//        }
//
//        if (rootView != null) {
//            rootView.setBackgroundColor(Constant.color);
//        }
//    }
//
//    private void initializeViews(View view) {
//        // Existing Views
//        text01 = view.findViewById(R.id.text01);
//        text02 = view.findViewById(R.id.text02);
//        greeting = view.findViewById(R.id.greeting);
//        text = view.findViewById(R.id.text);
//        text2 = view.findViewById(R.id.text2);
//        text3 = view.findViewById(R.id.text3);
//        text4 = view.findViewById(R.id.text4);
//        text5 = view.findViewById(R.id.text5);
//        text6 = view.findViewById(R.id.text6);
//        text7 = view.findViewById(R.id.text7);
//        text8 = view.findViewById(R.id.text8);
//        text9 = view.findViewById(R.id.text9);
//        text10 = view.findViewById(R.id.text10);
//
//        kg = view.findViewById(R.id.kg);
//        kg2 = view.findViewById(R.id.kg2);
//        kg3 = view.findViewById(R.id.kg3);
//        kg4 = view.findViewById(R.id.kg4);
//        kg5 = view.findViewById(R.id.kg5);
//        kg6 = view.findViewById(R.id.kg6);
//        kg7 = view.findViewById(R.id.kg7);
//        kg8 = view.findViewById(R.id.kg8);
//        kg9 = view.findViewById(R.id.kg9);
//        kg10 = view.findViewById(R.id.kg10);
//
////        // *** NEW: Initialize Daily Status Card Elements ***
////        cardDailyStatus = view.findViewById(R.id.card_daily_status);
////        tvStatusText = view.findViewById(R.id.tv_status_text);
////        imgStatusIcon = view.findViewById(R.id.img_status_icon);
////
////        // Status Card နှိပ်လိုက်ရင် Link သို့မဟုတ် CalendarActivity သွားမယ်
////        if (cardDailyStatus != null) {
////            cardDailyStatus.setOnClickListener(v -> {
////                if (!dailyLink.isEmpty()) {
////                    try {
////                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dailyLink));
////                        startActivity(intent);
////                    } catch (Exception e) {
////                        Toast.makeText(getActivity(), "Cannot open link", Toast.LENGTH_SHORT).show();
////                    }
////                } else {
////                    // Link မပါရင် CalendarActivity ကို ဖွင့်ပြမယ်
////                    Intent intent = new Intent(getActivity(), CalendarActivity.class);
////                    startActivity(intent);
////                }
////            });
////        }
////    }
//
//
//        // *** NEW: Initialize Daily Status Card Elements ***
//        cardDailyStatus = view.findViewById(R.id.card_daily_status);
//        tvStatusText = view.findViewById(R.id.tv_status_text);
//        imgStatusIcon = view.findViewById(R.id.img_status_icon);
//
//        // Status Card နှိပ်လိုက်ရင် Activity သို့မဟုတ် Web Link ဖွင့်မည့် Logic
//        if (cardDailyStatus != null) {
//            cardDailyStatus.setOnClickListener(v -> {
//                if (dailyLink != null && !dailyLink.isEmpty()) {
//
//                    // (၁) Activity များဖွင့်ရန် စစ်ဆေးခြင်း
//                    if (dailyLink.equals("OPEN_TEXTBOOK")) {
//                        // "OPEN_TEXTBOOK" လို့ရေးထားရင် TextBook activity ကိုဖွင့်မယ်
//                        launchActivityWithAd(TextBook.class);
//                    }
//                    else if (dailyLink.equals("OPEN_TEACHER")) {
//                        // "OPEN_TEACHER" လို့ရေးထားရင် TeacherGuide activity ကိုဖွင့်မယ်
//                        launchActivityWithAd(TeacherGuide.class);
//                    }
//                    else if (dailyLink.equals("OPEN_QNA")) {
//                        // "OPEN_QNA" လို့ရေးထားရင် QandAActivity activity ကိုဖွင့်မယ်
//                        launchActivityWithAd(QandAActivity.class);
//                    }
//                    // (၂) Web Link ဖြစ်ပါက Browser ဖြင့်ဖွင့်ခြင်း (http သို့မဟုတ် https ပါလျှင်)
//                    else if (dailyLink.startsWith("http")) {
//                        try {
//                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dailyLink));
//                            startActivity(intent);
//                        } catch (Exception e) {
//                            Toast.makeText(getActivity(), "Cannot open link", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    // (၃) ဘာမှမကိုက်ညီရင် CalendarActivity ကိုဖွင့်မယ်
//                    else {
//                        Intent intent = new Intent(getActivity(), CalendarActivity.class);
//                        startActivity(intent);
//                    }
//
//                } else {
//                    // Link မပါရင် CalendarActivity ကို ဖွင့်ပြမယ်
//                    Intent intent = new Intent(getActivity(), CalendarActivity.class);
//                    startActivity(intent);
//                }
//            });
//        }
//    }
//
//
//    private void setupTheme() {
//        app_preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        int appColor = app_preferences.getInt("color", getResources().getColor(R.color.default_color));
//        int appTheme = app_preferences.getInt("theme", Constant.theme);
//        themeColor = appColor;
//        Constant.color = appColor;
//
//        if (getActivity() != null) {
//            getActivity().getWindow().getDecorView().setBackgroundColor(Constant.color);
//            getActivity().setTheme(appTheme);
//        }
//    }
//
//    private void checkAdsStatus() {
//        // မူရင်းအတိုင်း Ads စစ်ပြီး CalendarActivity ကို ခေါ်တဲ့ function
//        blogspotAPIManager.checkAdsStatus(isEnabled -> {
//            if (isEnabled) {
//               // Intent i = new Intent(getActivity(), CalendarActivity.class);
//                Intent i = new Intent(getActivity(), AdsActivity.class);
//                startActivity(i);
//            }
//        });
//    }
//
//    // *** NEW LOGIC: Fetch Data from Blogger inside Fragment ***
//    private void fetchDailyStatus() {
//        // Blogger URL
//        String bloggerUrl = "https://myanmarschoolpassward.blogspot.com/feeds/posts/default?alt=json";
//        new DailyStatusTask().execute(bloggerUrl);
//    }
//
//    // Background Task for downloading JSON
//    private class DailyStatusTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//            // JSONDownloader class ရှိနေရပါမယ်
//            return JSONDownloader.download(urls[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (getActivity() == null) return;
//
//            if (result != null) {
//                processDailyJSON(result);
//            } else {
//                // Internet မရှိရင် Card ကို ဖျောက်ထားမယ်
//                if (cardDailyStatus != null) cardDailyStatus.setVisibility(View.GONE);
//            }
//        }
//    }
//
//    private void processDailyJSON(String input) {
//        // ဒီနေ့ရက်စွဲကို ယူမယ် (Locale.US သုံးထားလို့ ဖုန်း Setting ဘာဖြစ်ဖြစ် အဆင်ပြေမယ်)
//        String todayStr = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
//        boolean matchFound = false;
//
//        try {
//            JSONObject jo = new JSONObject(input);
//            JSONArray ja = jo.getJSONObject("feed").getJSONArray("entry");
//
//            for (int i = 0; i < ja.length(); i++) {
//                JSONObject post = ja.getJSONObject(i);
//                String title = post.getJSONObject("title").getString("$t");
//
//                // "Movies1" ဆိုတဲ့ Post ကိုပဲ ရှာမယ်
//                if (title.equals("School")) {
//                    String content = post.getJSONObject("content").getString("$t");
//                    JSONObject contentJson = new JSONObject(Html.fromHtml(content).toString());
//
//                    if (contentJson.has("calendar")) {
//                        JSONArray calendarArr = contentJson.getJSONArray("calendar");
//
//                        for (int j = 0; j < calendarArr.length(); j++) {
//                            JSONObject dayObj = calendarArr.getJSONObject(j);
//                            String jsonDate = dayObj.getString("date").trim();
//
//                            if (jsonDate.equals(todayStr)) {
//                                // *** MATCH FOUND (ဒီနေ့နဲ့တူသော Data တွေ့ပြီ) ***
//                                String message = dayObj.getString("message");
//                                String imageUrl = dayObj.optString("image", "");
//                                dailyLink = dayObj.optString("link", "");
//
//                                // UI Update
//                                if (tvStatusText != null) tvStatusText.setText(message);
//
//                                if (!imageUrl.isEmpty() && imgStatusIcon != null) {
//                                    Glide.with(getActivity())
//                                            .load(imageUrl)
//                                            .placeholder(R.drawable.ic_launcher_background)
//                                            .into(imgStatusIcon);
//                                }
//
//                                // Card ကို ဖော်လိုက်မယ်
//                                if (cardDailyStatus != null) cardDailyStatus.setVisibility(View.VISIBLE);
//                                matchFound = true;
//                                break;
//                            }
//                        }
//                    }
//                }
//                if (matchFound) break;
//            }
//
//            if (!matchFound) {
//                // ဒီနေ့အတွက် Data မရှိရင် Card ကို ဖျောက်ထားမယ်
//                if (cardDailyStatus != null) cardDailyStatus.setVisibility(View.GONE);
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            if (cardDailyStatus != null) cardDailyStatus.setVisibility(View.GONE);
//        }
//    }
//    // *** END NEW LOGIC ***
//
//    private void setupTranslations() {
//        SharedPreferences prefs = getActivity().getSharedPreferences("translate", Context.MODE_PRIVATE);
//        int id = prefs.getInt("id", 0);
//
//        setupTextViewTranslation(text, R.string.textnew, id);
//        setupTextViewTranslation(text2, R.string.text2new, id);
//        setupTextViewTranslation(greeting, R.string.greetingText, id);
//        setupTextViewTranslation(text3, R.string.text3new, id);
//        setupTextViewTranslation(text4, R.string.text4new, id);
//        setupTextViewTranslation(text5, R.string.text5new, id);
//        setupTextViewTranslation(text6, R.string.text6new, id);
//        setupTextViewTranslation(text01, R.string.text01new, id);
//        setupTextViewTranslation(text02, R.string.text02new, id);
//        setupTextViewTranslation(text7, R.string.text7new, id);
//        setupTextViewTranslation(text8, R.string.text8new, id);
//        setupTextViewTranslation(text9, R.string.text9new, id);
//        setupTextViewTranslation(text10, R.string.text10new, id);
//    }
//
//    private void setupTextViewTranslation(TextView textView, int stringResId, int translationId) {
//        if (translationId == 0) {
//            TranslateAPI translateAPI = new TranslateAPI(
//                    Language.AUTO_DETECT,
//                    Language.BURMESE,
//                    getString(stringResId));
//
//            translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
//                @Override
//                public void onSuccess(String translatedText) {
//                    textView.setText(translatedText);
//                }
//
//                @Override
//                public void onFailure(String ErrorText) {
//                    textView.setText(getString(stringResId));
//                }
//            });
//        } else {
//            textView.setText(getString(stringResId));
//        }
//    }
//
//    private void setupClickListeners() {
//        if(kg != null) kg.setOnClickListener(v -> launchActivityWithAd(TextBook.class));
//        if(kg2 != null) kg2.setOnClickListener(v -> launchActivityWithAd(TeacherGuide.class));
//        if(kg3 != null) kg3.setOnClickListener(v -> launchActivityWithAd(LessonActivity.class));
//        if(kg4 != null) kg4.setOnClickListener(v -> launchActivityWithAd(STeachingBook.class));
//        if(kg5 != null) kg5.setOnClickListener(v -> launchActivityWithAd(QandAActivity.class));
//        if(kg6 != null) kg6.setOnClickListener(v -> launchActivityWithAd(Mp3Mp4MainActivity.class));
//        if(kg7 != null) kg7.setOnClickListener(v -> launchActivityWithAd(EbookMainActivity.class));
//        if(kg8 != null) kg8.setOnClickListener(v -> launchActivityWithAd(CinemaLessonActivity.class));
//        if(kg9 != null) kg9.setOnClickListener(v -> launchActivityWithAd(Main1.class));
//        if(kg10 != null) kg10.setOnClickListener(v -> launchActivityWithAd(Main3.class));
//    }
//
//    private void launchActivityWithAd(Class<?> activityClass) {
//        new Admob(() -> {
//            Intent i = new Intent(getActivity(), activityClass);
//            startActivity(i);
//        }).ShowInterstitial(getActivity(), true);
//    }
//
//    private void registerBroadcastReceiver() {
//        IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
//        getActivity().registerReceiver(messageStatusReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (getActivity() != null) {
//            getActivity().unregisterReceiver(messageStatusReceiver);
//        }
//    }
//
//    private void showNoInternet() {
//        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
//        View parent_view = LayoutInflater.from(getActivity()).inflate(R.layout.nointernet_dia, null);
//        dialog.setView(parent_view);
//        dialog.setCancelable(false);
//        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.show();
//
//        TextView tv_title = parent_view.findViewById(R.id.dialogTextView1);
//        Button button = parent_view.findViewById(R.id.dialogButton1);
//
//        tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ခြင်းမရှိပါ။ ​ကျေးဇူး​ပြု၍ အင်တာနက် ဆက်သွယ်​ပေးပါ။");
//        button.setText("...ဟုတ်ကဲ့...");
//
//        button.setOnClickListener(v -> {
//            if (getActivity() != null) {
//                getActivity().finish();
//            }
//        });
//    }
//}



package com.mmschooledu;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bdtopcoder.quickadmob.Admob;
import com.bumptech.glide.Glide;
import com.mmschooledu.Cinema.CinemaLessonActivity;
import com.mmschooledu.EbookAudioShelf.EbookMainActivity;
import com.mmschooledu.Mp3.Mp3Mp4MainActivity;
import com.mmschooledu.QandAnswer.QandAActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private static final int STORAGE_REQUEST_CODE = 100;
    private SharedPreferences app_preferences;
    private int themeColor;
    private View rootView;

    // Original UI Elements
    private CardView kg, kg2, kg3, kg4, kg5, kg6, kg7, kg8, kg9, kg10;
    private TextView text01, text02, greeting, text, text2, text3, text4, text5, text6, text7, text8, text9, text10;
    private BlogspotAPIManager blogspotAPIManager;

    // Daily Status UI Elements
    private CardView cardDailyStatus;
    private TextView tvStatusText;
    private ImageView imgStatusIcon;
    private String dailyLink = "";
    private DailyStatusTask dailyStatusTask;
    private boolean receiverRegistered;

    private BroadcastReceiver messageStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            boolean show = intent.getBooleanExtra("show", true);
            Context host = getActivity();
            if (show && host != null) {
                Toast.makeText(host, message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_homenew, container, false);

        initializeViews(rootView);
        setupTheme();

        blogspotAPIManager = new BlogspotAPIManager(getActivity());

        // 1. Ads Check
        checkAdsStatus();

        // 2. Translations
        setupTranslations();

        // 3. Click Listeners
        setupClickListeners();

        // 4. VPN/country verification must pass before dashboard use.
        GeolocationUtils.checkCountryAndShowDialog(getActivity());

        // 5. Fetch Daily Status (School Open/Close)
        fetchDailyStatus();

        // *** NEW: App Update Checker Code ***
        if (getActivity() != null) {
            String check_update_json = "https://2020goodblog.blogspot.com/2022/07/myanmarschooleducationapk.html";
            // Passed 'getActivity()' instead of 'this'
            AppUpdater appUpdater = new AppUpdater(getActivity(), check_update_json);

            // Renamed variable from 'Context' to 'shouldShowDialog' to avoid errors
            boolean shouldShowDialog = false;
            appUpdater.check(shouldShowDialog);
        }

        // 6. Register BroadcastReceiver
        if (getActivity() != null) {
            IntentFilter intentFilter = new IntentFilter("MESSAGE_STATUS_ACTION");
            ContextCompat.registerReceiver(getActivity(), messageStatusReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
            receiverRegistered = true;
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        applyThemeColor();
    }

    private void applyThemeColor() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        int color = preferences.getInt("color", getResources().getColor(R.color.default_color));
        Constant.color = color;

        if (getActivity() != null) {
            getActivity().getWindow().getDecorView().setBackgroundColor(Constant.color);
        }

        if (rootView != null) {
            rootView.setBackgroundColor(Constant.color);
        }
    }

    private void initializeViews(View view) {
        text01 = view.findViewById(R.id.text01);
        text02 = view.findViewById(R.id.text02);
        greeting = view.findViewById(R.id.greeting);
        text = view.findViewById(R.id.text);
        text2 = view.findViewById(R.id.text2);
        text3 = view.findViewById(R.id.text3);
        text4 = view.findViewById(R.id.text4);
        text5 = view.findViewById(R.id.text5);
        text6 = view.findViewById(R.id.text6);
        text7 = view.findViewById(R.id.text7);
        text8 = view.findViewById(R.id.text8);
        text9 = view.findViewById(R.id.text9);
        text10 = view.findViewById(R.id.text10);

        kg = view.findViewById(R.id.kg);
        kg2 = view.findViewById(R.id.kg2);
        kg3 = view.findViewById(R.id.kg3);
        kg4 = view.findViewById(R.id.kg4);
        kg5 = view.findViewById(R.id.kg5);
        kg6 = view.findViewById(R.id.kg6);
        kg7 = view.findViewById(R.id.kg7);
        kg8 = view.findViewById(R.id.kg8);
        kg9 = view.findViewById(R.id.kg9);
        kg10 = view.findViewById(R.id.kg10);

        // Daily Status Card
        cardDailyStatus = view.findViewById(R.id.card_daily_status);
        tvStatusText = view.findViewById(R.id.tv_status_text);
        imgStatusIcon = view.findViewById(R.id.img_status_icon);

        if (cardDailyStatus != null) {
            cardDailyStatus.setOnClickListener(v -> {
                if (dailyLink != null && !dailyLink.isEmpty()) {

                    // Check for specific keywords
                    if (dailyLink.equals("OPEN_TEXTBOOK")) {
                        launchActivityWithAd(TextBook.class);
                    }
                    else if (dailyLink.equals("OPEN_TEACHER")) {
                        launchActivityWithAd(TeacherGuide.class);
                    }
                    else if (dailyLink.equals("OPEN_QNA")) {
                        launchActivityWithAd(QandAActivity.class);
                    }
                    // Check for web links
                    else if (dailyLink.startsWith("http")) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dailyLink));
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Cannot open link", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // Fallback to CalendarActivity
                    else {
                        Intent intent = new Intent(getActivity(), CalendarActivity.class);
                        startActivity(intent);
                    }

                } else {
                    Intent intent = new Intent(getActivity(), CalendarActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void setupTheme() {
        app_preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int appColor = app_preferences.getInt("color", getResources().getColor(R.color.default_color));
        int appTheme = app_preferences.getInt("theme", Constant.theme);
        themeColor = appColor;
        Constant.color = appColor;

        if (getActivity() != null) {
            getActivity().getWindow().getDecorView().setBackgroundColor(Constant.color);
            getActivity().setTheme(appTheme);
        }
    }

    private void checkAdsStatus() {
        blogspotAPIManager.checkAdsStatus(isEnabled -> {
            if (isEnabled && isAdded() && getActivity() != null) {
                Intent i = new Intent(requireContext(), AdsActivity.class);
                startActivity(i);
            }
        });
    }

    // Daily Status Logic
    private void fetchDailyStatus() {
        if (dailyStatusTask != null) {
            dailyStatusTask.cancel(true);
        }
        String bloggerUrl = "https://myanmarschoolpassward.blogspot.com/feeds/posts/default?alt=json";
        dailyStatusTask = new DailyStatusTask();
        dailyStatusTask.execute(bloggerUrl);
    }

    private class DailyStatusTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return JSONDownloader.download(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (isCancelled() || !isAdded() || getActivity() == null || rootView == null) return;

            if (result != null) {
                processDailyJSON(result);
            } else {
                if (cardDailyStatus != null) cardDailyStatus.setVisibility(View.GONE);
            }
        }
    }

    private void processDailyJSON(String input) {
        String todayStr = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
        boolean matchFound = false;

        try {
            JSONObject jo = new JSONObject(input);
            JSONArray ja = jo.getJSONObject("feed").getJSONArray("entry");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject post = ja.getJSONObject(i);
                String title = post.getJSONObject("title").getString("$t");

                if (title.equals("School")) {
                    String content = post.getJSONObject("content").getString("$t");
                    JSONObject contentJson = new JSONObject(Html.fromHtml(content).toString());

                    if (contentJson.has("calendar")) {
                        JSONArray calendarArr = contentJson.getJSONArray("calendar");

                        for (int j = 0; j < calendarArr.length(); j++) {
                            JSONObject dayObj = calendarArr.getJSONObject(j);
                            String jsonDate = dayObj.getString("date").trim();

                            if (jsonDate.equals(todayStr)) {
                                String message = dayObj.getString("message");
                                String imageUrl = dayObj.optString("image", "");
                                dailyLink = dayObj.optString("link", "");

                                if (tvStatusText != null) tvStatusText.setText(message);

                                if (!imageUrl.isEmpty() && imgStatusIcon != null) {
                                    Glide.with(requireContext())
                                            .load(imageUrl)
                                            .placeholder(R.drawable.apk)
                                            .into(imgStatusIcon);
                                }

                                if (cardDailyStatus != null) cardDailyStatus.setVisibility(View.VISIBLE);
                                matchFound = true;
                                break;
                            }
                        }
                    }
                }
                if (matchFound) break;
            }

            if (!matchFound) {
                if (cardDailyStatus != null) cardDailyStatus.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            if (cardDailyStatus != null) cardDailyStatus.setVisibility(View.GONE);
        }
    }

    private void setupTranslations() {
        Context host = getContext();
        if (host == null || rootView == null || !isAdded()) return;
        int id = AppLocale.getLanguageId(host);

        setupTextViewTranslation(text, R.string.textnew, id);
        setupTextViewTranslation(text2, R.string.text2new, id);
        setupTextViewTranslation(greeting, R.string.greetingText, id);
        setupTextViewTranslation(text3, R.string.text3new, id);
        setupTextViewTranslation(text4, R.string.text4new, id);
        setupTextViewTranslation(text5, R.string.text5new, id);
        setupTextViewTranslation(text6, R.string.text6new, id);
        setupTextViewTranslation(text01, R.string.text01new, id);
        setupTextViewTranslation(text02, R.string.text02new, id);
        setupTextViewTranslation(text7, R.string.text7new, id);
        setupTextViewTranslation(text8, R.string.text8new, id);
        setupTextViewTranslation(text9, R.string.text9new, id);
        setupTextViewTranslation(text10, R.string.text10new, id);
    }

    private void setupTextViewTranslation(TextView textView, int stringResId, int translationId) {
        if (textView == null) {
            return;
        }
        // The original language flow uses the saved id and Android resources. Do not
        // wait for a remote translation request to render the dashboard labels.
        textView.setText(getString(stringResId));
    }

    private void setupClickListeners() {
        if(kg != null) kg.setOnClickListener(v -> launchActivityWithAd(TextBook.class));
        if(kg2 != null) kg2.setOnClickListener(v -> launchActivityWithAd(TeacherGuide.class));
        if(kg3 != null) kg3.setOnClickListener(v -> launchActivityWithAd(LessonActivity.class));
        if(kg4 != null) kg4.setOnClickListener(v -> launchActivityWithAd(STeachingBook.class));
        if(kg5 != null) kg5.setOnClickListener(v -> launchActivityWithAd(QandAActivity.class));
        if(kg6 != null) kg6.setOnClickListener(v -> launchActivityWithAd(Mp3Mp4MainActivity.class));
        if(kg7 != null) kg7.setOnClickListener(v -> launchActivityWithAd(EbookMainActivity.class));
        if(kg8 != null) kg8.setOnClickListener(v -> launchActivityWithAd(CinemaLessonActivity.class));
        if(kg9 != null) kg9.setOnClickListener(v -> launchActivityWithAd(Main1.class));
        if(kg10 != null) kg10.setOnClickListener(v -> launchActivityWithAd(Main3.class));
    }

    private void launchActivityWithAd(Class<?> activityClass) {
        android.app.Activity hostActivity = getActivity();
        if (hostActivity == null || !isAdded()) return;
        new Admob(() -> {
            if (!isAdded() || getActivity() == null) return;
            Intent i = new Intent(hostActivity, activityClass);
            startActivity(i);
        }).ShowInterstitial(hostActivity, true);
    }

    @Override
    public void onDestroyView() {
        if (dailyStatusTask != null) {
            dailyStatusTask.cancel(true);
            dailyStatusTask = null;
        }
        if (receiverRegistered && getActivity() != null) {
            try {
                getActivity().unregisterReceiver(messageStatusReceiver);
            } catch (IllegalArgumentException e) {
                // Receiver not registered
            }
            receiverRegistered = false;
        }
        rootView = null;
        cardDailyStatus = null;
        tvStatusText = null;
        imgStatusIcon = null;
        super.onDestroyView();
    }

    private void showNoInternet() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        View parent_view = LayoutInflater.from(getActivity()).inflate(R.layout.nointernet_dia, null);
        dialog.setView(parent_view);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        TextView tv_title = parent_view.findViewById(R.id.dialogTextView1);
        Button button = parent_view.findViewById(R.id.dialogButton1);

        tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ခြင်းမရှိပါ။ ​ကျေးဇူး​ပြု၍ အင်တာနက် ဆက်သွယ်​ပေးပါ။");
        button.setText("...ဟုတ်ကဲ့...");

        button.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
    }
}