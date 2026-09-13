////package com.mmschooledu;
////
////import android.app.Activity;
////import android.content.Context;
////import android.content.DialogInterface;
////import android.content.Intent;
////import android.content.SharedPreferences;
////import android.content.res.Configuration;
////import android.content.res.Resources;
////import android.graphics.drawable.ColorDrawable;
////import android.net.ConnectivityManager;
////import android.net.NetworkInfo;
////import android.net.Uri;
////import android.os.Bundle;
////import android.text.Editable;
////import android.text.TextWatcher;
////import android.view.LayoutInflater;
////import android.view.Menu;
////import android.view.MenuInflater;
////import android.view.MenuItem;
////import android.view.View;
////import android.widget.ImageView;
////import android.widget.LinearLayout;
////import android.widget.ProgressBar;
////import android.widget.TextView;
////
////import androidx.appcompat.app.AlertDialog;
////import androidx.appcompat.app.AppCompatActivity;
////import androidx.appcompat.app.AppCompatDelegate;
////import androidx.appcompat.widget.Toolbar;
////import androidx.cardview.widget.CardView;
////import androidx.fragment.app.Fragment;
////import androidx.fragment.app.FragmentManager;
////import androidx.fragment.app.FragmentPagerAdapter;
////import androidx.viewpager.widget.ViewPager;
////
////import com.bumptech.glide.Glide;
////import com.google.android.material.bottomnavigation.BottomNavigationView;
////import com.google.android.material.textfield.TextInputEditText;
////import com.mmschooledu.MyanmarSpelling.fragment.SettingFragment;
//////import com.google.firebase.analytics.FirebaseAnalytics;
//////import com.techkipon.zozotv.ui.fragment.HomeFragment;
//////import com.techkipon.zozotv.ui.fragment.MoviesFragment;
//////import com.techkipon.zozotv.ui.fragment.Series2Fragment;
//////import com.techkipon.zozotv.ui.fragment.SettingFragment;
//////import com.thecode.aestheticdialogs.AestheticDialog;
//////import com.thecode.aestheticdialogs.DialogStyle;
//////import com.thecode.aestheticdialogs.DialogType;
//////import com.thecode.aestheticdialogs.OnDialogClickListener;
////
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Locale;
////import java.util.Objects;
////
////;
////
////public class MainFragmentActivity extends AppCompatActivity {
//////    private FirebaseAnalytics mFirebaseAnalytics;
////    BottomNavigationView navigationView;
////    //ActionBar actionBar ;
////    Toolbar tb;
////    ViewPager viewPager3;
////    boolean showSplash=true;
////    LinearLayout l_search;
////    List<Fragment> fragments;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////      //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//////        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
//////        if (sharedPreferences.contains("lang_code")) {
//////            setLocal(this,sharedPreferences.getString("lang_code","en"));
//////        }
////        setContentView(R.layout.fragmentactivity_main);
////
//////        tb = (Toolbar) findViewById(R.id.nnl_toolbar);
//////        setSupportActionBar(tb);
//////        getSupportActionBar().setTitle(null);
//////        tb.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_actionbar)));
////        prepareView();
////      //  makeComfriming();
////    }
////
////    void prepareView() {
////
////        navigationView = findViewById(R.id.navigation);
////        viewPager3= findViewById(R.id.viewPager);
////       // l_search= findViewById(R.id.l_search);
////   // }
//////    void actionView() {
//////       // mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//////        if(getFromPrefs("comfrim").equals("true")){
//////            navigationView.setVisibility(View.VISIBLE);
//////            viewPager3.setVisibility(View.VISIBLE);
//////            l_search.setVisi//bility(View.VISIBLE);
//////        }
////        fragments = new ArrayList<>();
////        fragments.add(new HomeFragment());
////        fragments.add(new HomeFragment());
////        fragments.add(new HomeFragment());
////        fragments.add(new SettingFragment());
////
////
////        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
////        viewPager3.setAdapter(pagerAdapter);
////        viewPager3.setOffscreenPageLimit(3);
////
////
////
////// Set the ViewPager's page change listener to update the selected item in the BottomNavigationView
////        viewPager3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
////            @Override
////            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////            }
////
////            @Override
////            public void onPageSelected(int position) {
////                navigationView.getMenu().getItem(position).setChecked(true);
////
////                if(position==0) {
////                    l_search.setVisibility(View.VISIBLE);
////                }else{
////                    l_search.setVisibility(View.GONE);
////                }
////                if(position==0||position==3) {
////                    Objects.requireNonNull(getSupportActionBar()).show();
////                }else{
////                    getSupportActionBar().hide();
////                }
////
////            }
////
////            @Override
////            public void onPageScrollStateChanged(int state) {
////            }
////        });
////
////        // Set the BottomNavigationView's item click listener to update the current page in the ViewPager
////        navigationView.setOnNavigationItemSelectedListener(item -> {
////
//////            switch (item.getItemId()) {
//////                case R.id.navigation_home:
//////                    viewPager.setCurrentItem(0);
//////                    return true;
//////                case R.id.navigation_movies:
//////                    viewPager.setCurrentItem(1);
//////                    return true;
//////                case R.id.navigation_series:
//////                    viewPager.setCurrentItem(2);
//////                    return true;
//////                case R.id.navigation_setting:
//////                    viewPager.setCurrentItem(3);
//////                    return true;
//////            }
////
////            int itemId = item.getItemId();
////            if (itemId == R.id.navigation_home) {
////                viewPager3.setCurrentItem(0, true);
////            } else if (itemId == R.id.navigation_movies) {
////                viewPager3.setCurrentItem(1, true);
////            } else if (itemId == R.id.navigation_series) {
////                viewPager3.setCurrentItem(2, true);
////            }
////            else if (itemId == R.id.navigation_setting) {
////                viewPager3.setCurrentItem(2, true);
////            }
////
////
////            return false;
////        });
////    }
////    void actionClick() {
////        l_search.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////               // startActivity(new Intent(MainFragmentActivity.this,SearchMoviesActivity.class));
////
////            }
////        });
////    }
////    public class PagerAdapter extends FragmentPagerAdapter {
////
////        private List<Fragment> fragments;
////
////        public PagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
////            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
////            this.fragments = fragments;
////        }
////
////        @Override
////        public Fragment getItem(int position) {
////            return fragments.get(position);
////        }
////
////        @Override
////        public int getCount() {
////            return fragments.size();
////        }
////    }
////
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        MenuInflater inflater = getMenuInflater();
////        inflater.inflate(R.menu.newbottom_navigation_menu, menu);
////
////
////
////        return super.onCreateOptionsMenu(menu);
////    }
////
////    @Override
////    public boolean onOptionsItemSelected(MenuItem item) {
////        int id = item.getItemId();
////
////
////
////        return super.onOptionsItemSelected(item);
////    }
////
////    @Override
////    public void onBackPressed() {
////
////        super.onBackPressed();
////        if (viewPager3.getCurrentItem() != 0) {
////            viewPager3.setCurrentItem(0);
////        } else {
////            final AlertDialog dialog = new AlertDialog.Builder(this).create();
////            View parent_view = LayoutInflater.from(this).inflate(R.layout.dia_two_button, null);
////            dialog.setView(parent_view);
////            dialog.setCancelable(true);
////            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
////            dialog.show();
////            LinearLayout dia_background_layout = dialog.findViewById(R.id.dia_background_layout);
////
////            ImageView iv = (ImageView) dialog.findViewById(R.id.icon);
////            TextView tv_title = (TextView) dialog.findViewById(R.id.title);
////            ImageView iv_done = (ImageView) dialog.findViewById(R.id.iv_done);
////            TextView tv_done = (TextView) dialog.findViewById(R.id.tv_done);
////            TextView tv_message = (TextView) dialog.findViewById(R.id.message);
////            final CardView done = (CardView) dialog.findViewById(R.id.done_card);
////            final CardView cancel = (CardView) dialog.findViewById(R.id.cancel_card);
////            assert dia_background_layout != null;
////            dia_background_layout.setBackground(getDrawable(R.drawable.default_rating_dia_background));
////            assert iv != null;
////            iv.setImageResource(R.drawable.star_48px);
////            tv_title.setText("5Stars/Exit");
////            tv_message.setText(getString(R.string.exit_message));
////            assert cancel != null;
////            cancel.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View p1) {
////                    finish();
////                    dialog.dismiss();
////
////                }
////            });
////            tv_done.setText("5Stars");
////            tv_done.setTextColor(getColor(R.color.ratingcolor));
////            iv_done.setImageDrawable(getDrawable(R.drawable.star_imbb_48px));
////            done.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View p1) {
////                    startActivity(new Intent(Intent.ACTION_VIEW,
////                            Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
////
////                    dialog.dismiss();
////                }
////            });
////
////
////        }
////
////    }
////
////    @Override
////    protected void onStart() {
////        if(showSplash){
////            Intent intent = new Intent(MainFragmentActivity.this, SplashScreenActivity.class);
////            startActivity(intent);
////            showSplash=false;
////        }
////        super.onStart();
////    }
////    protected boolean isOnline() {
////        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
////        NetworkInfo netInfo = cm.getActiveNetworkInfo();
////        return netInfo != null && netInfo.isConnectedOrConnecting();
////    }
////    void showComfrim(){
////
////        final android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this)
////                .create();
////        View parent_view = LayoutInflater.from(this).inflate(R.layout.dia_input_comfrim_app,
////                null);
////        dialog.setView(parent_view);
////        dialog.setCancelable(false);
////        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
////        dialog.show();
////        ImageView vip_dia_iv = (ImageView) dialog.findViewById(R.id.icon_iv);
////        TextView  vip_dia_tv_title = (TextView) dialog.findViewById(R.id.title_tv);
////        TextView tv_loading = (TextView) dialog.findViewById(R.id.tv_loading);
////        TextInputEditText et_add_key = (TextInputEditText) dialog.findViewById(R.id.tiet_add_key);
////        ProgressBar pb_loading= (ProgressBar) dialog.findViewById(R.id.pb_loading);
////        LinearLayout l_loading=dialog.findViewById(R.id.l_loading);
////        ImageView iv_comfrim_hint=dialog.findViewById(R.id.iv_add_omfrim);
////        ImageView iv_successful=dialog.findViewById(R.id.iv_success);
////
////        TextView tv_login=dialog.findViewById(R.id.tv_login);
////        ImageView iv_login=dialog.findViewById(R.id.iv_login);
////        CardView login = dialog.findViewById(R.id.login_card);
////        CardView cancel_card = dialog.findViewById(R.id.cancel_card);
////        Glide.with(this).load(R.drawable.verified_24px).into(vip_dia_iv);
////        Glide.with(this).load(R.drawable.comfrim_hint).into(iv_comfrim_hint);
////        vip_dia_tv_title.setText(getString(R.string.add_comfrim));
////
////        // Set an OnDismissListener for the dialog
////        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
////            @Override
////            public void onDismiss(DialogInterface dialog) {
////                if(!getFromPrefs("comfrim").equals("true")){
////                    // Finish the activity when the dialog is dismissed
////                    startActivity(new Intent(MainFragmentActivity.this,DownloadToolActivity.class));
////                    finish();
////                }
////
////            }
////        });
////        et_add_key.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////                String text=s.toString();
////                if (text.contains("၁၁၁၁၁")){
////                    saveToPrefs("comfrim","true");
////                    dialog.dismiss();
////                    makeComfriming();
////                }
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////
////            }
////        });
////
////        login.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View p1) {
////                String text=et_add_key.getText().toString();
////                String text2=et_add_key.getText().toString();
////                if (text.contains("၁၁၁၁၁")) {
////                    saveToPrefs("comfrim", "true");
////                    makeComfriming();
//////                }else{
//////                    showToast(getString(R.string.wrong_comfrim),1);
//////                }
////                }
////                else
////                if (text.contains("11111")){
////                    saveToPrefs("comfrim","true");
////                    makeComfriming();
////                }else{
////                    showToast(getString(R.string.wrong_comfrim),1);
////                }
////
////            }
////        });
////
////
////        cancel_card.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View p1) {
////                dialog.dismiss();
////            }
////        });
////
////
////
////        // startActivity(new Intent(this,DownloadToolActivity.class));
////        // finish();
////    }
////    public void saveToPrefs(String key, String result) {
////        SharedPreferences sharedPreferences =  getSharedPreferences("MyData", Context.MODE_PRIVATE);
////        SharedPreferences.Editor editor = sharedPreferences.edit();
////        editor.putString(key, result);
////        editor.commit();
////    }
////
////    public String getFromPrefs(String key) {
////
////        SharedPreferences sharedPreferences =  getSharedPreferences("MyData", Context.MODE_PRIVATE);
////        String lastData = sharedPreferences.getString(key, "");
////        return lastData;
////    }
////
////    void makeComfriming(){
////     /*   if(getFromPrefs("comfrim").equals("true")){
////            actionView();
////            actionClick();
////              }else{
////            showComfrim();
////            navigationView.setVisibility(View.GONE);
////            viewPager.setVisibility(View.GONE);
////            l_search.setVisibility(View.GONE);
////        }*/
////        actionView();
////        actionClick();
////    }
////    void showToast(String message, int sec) {
////        new AestheticDialog.Builder(this, DialogStyle.TOASTER, DialogType.INFO)
////                .setTitle(getString(R.string.noti))
////                .setMessage(message)
////                .setDarkMode(true)
////                .setOnClickListener(new OnDialogClickListener() {
////                    @Override
////                    public void onClick(AestheticDialog.Builder dialog) {
////                        dialog.dismiss();
////                    }
////                })
////                .show();
////
////    }
////    public void setLocal(Activity activity, String langCode){
////        Locale locale= new Locale(langCode);
////        locale.setDefault(locale);
////        Resources resources = activity.getResources();
////        Configuration config = resources.getConfiguration();
////        ((Configuration) config).setLocale(locale); resources.updateConfiguration(config, resources.getDisplayMetrics());
////    }
////
////    @Override
////    protected void onResume() {
////        if(getFromPrefs("comfrim").equals("true")){
////            if(viewPager3.getVisibility()==View.GONE){
////                actionView();
////                actionClick();}
////        }
////        super.onResume();
////
////
////    }
////}
//
////
////package com.mmschooledu;
////
////import android.content.Context;
////import android.content.Intent;
////import android.net.ConnectivityManager;
////import android.net.NetworkInfo;
////import android.os.Bundle;
////import android.view.Menu;
////import android.view.MenuInflater;
////import android.view.View;
////import android.widget.LinearLayout;
////
////import androidx.appcompat.app.AppCompatActivity;
////import androidx.appcompat.widget.Toolbar;
////import androidx.fragment.app.Fragment;
////import androidx.fragment.app.FragmentManager;
////import androidx.fragment.app.FragmentPagerAdapter;
////import androidx.viewpager.widget.ViewPager;
////
////import com.google.android.material.bottomnavigation.BottomNavigationView;
////import com.mmschooledu.MyanmarSpelling.fragment.SettingFragment;
////
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Objects;
////
////public class MainFragmentActivity extends AppCompatActivity {
////    private BottomNavigationView navigationView;
////    private Toolbar toolbar;
////    private ViewPager viewPager;
////    private LinearLayout searchLayout;
////    private boolean showSplash = true;
////    private List<Fragment> fragments = new ArrayList<>();
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.fragmentactivity_main);
////
////        initializeViews();
////        setupViewPager();
////        setupNavigation();
////    }
////
////    private void initializeViews() {
////        navigationView = findViewById(R.id.navigation);
////        viewPager = findViewById(R.id.viewPager);
////     //   searchLayout = findViewById(R.id.l_search);
////       // toolbar = findViewById(R.id.nnl_toolbar);
////
//////        setSupportActionBar(toolbar);
//////        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
////    }
////
////    private void setupViewPager() {
////        fragments.add(new HomeFragment());
////        fragments.add(new HomeFragment()); // Replace with actual fragment
////        fragments.add(new HomeFragment()); // Replace with actual fragment
////        fragments.add(new SettingFragment());
////
////        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
////        viewPager.setAdapter(pagerAdapter);
////        viewPager.setOffscreenPageLimit(fragments.size());
////
////        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
////            @Override
////            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
////
////            @Override
////            public void onPageSelected(int position) {
////                navigationView.getMenu().getItem(position).setChecked(true);
////
////                // Show/hide search layout based on page
////                searchLayout.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
////
////                // Show/hide action bar based on page
////                if (position == 0 || position == 3) {
////                    Objects.requireNonNull(getSupportActionBar()).show();
////                } else {
////                    Objects.requireNonNull(getSupportActionBar()).hide();
////                }
////            }
////
////            @Override
////            public void onPageScrollStateChanged(int state) {}
////        });
////    }
////
////    private void setupNavigation() {
////        navigationView.setOnNavigationItemSelectedListener(item -> {
////            int itemId = item.getItemId();
////
////            if (itemId == R.id.navigation_home) {
////                viewPager.setCurrentItem(0, true);
////            } else if (itemId == R.id.navigation_movies) {
////                viewPager.setCurrentItem(1, true);
////            } else if (itemId == R.id.navigation_series) {
////                viewPager.setCurrentItem(2, true);
////            } else if (itemId == R.id.navigation_setting) {
////                viewPager.setCurrentItem(3, true);
////            }
////            return true;
////        });
////    }
////
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        MenuInflater inflater = getMenuInflater();
////        inflater.inflate(R.menu.newbottom_navigation_menu, menu);
////        return super.onCreateOptionsMenu(menu);
////    }
////
////    @Override
////    protected void onStart() {
////        if (showSplash) {
////            startActivity(new Intent(this, SplashScreenActivity.class));
////            showSplash = false;
////        }
////        super.onStart();
////    }
////
////    protected boolean isOnline() {
////        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
////        NetworkInfo netInfo = cm.getActiveNetworkInfo();
////        return netInfo != null && netInfo.isConnectedOrConnecting();
////    }
////
////    // Implement the OnWordListSelectedListener interface
//////    @Override
//////    public void onWordListSelected(String selectedWord) {
//////        // Handle word selection from SettingFragment
//////    }
////
////    private static class PagerAdapter extends FragmentPagerAdapter {
////        private final List<Fragment> fragments;
////
////        public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
////            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
////            this.fragments = fragments;
////        }
////
////        @Override
////        public Fragment getItem(int position) {
////            return fragments.get(position);
////        }
////
////        @Override
////        public int getCount() {
////            return fragments.size();
////        }
////    }
////}
//
//
////
////package com.mmschooledu;
////
////import android.content.Context;
////import android.content.Intent;
////import android.net.ConnectivityManager;
////import android.net.NetworkInfo;
////import android.os.Bundle;
////import android.view.Menu;
////import android.view.MenuInflater;
////import android.view.View;
////
////import androidx.appcompat.app.AppCompatActivity;
////import androidx.cardview.widget.CardView;
////import androidx.fragment.app.Fragment;
////import androidx.fragment.app.FragmentManager;
////import androidx.fragment.app.FragmentPagerAdapter;
////import androidx.viewpager.widget.ViewPager;
////
////import com.google.android.material.bottomnavigation.BottomNavigationView;
////import com.mmschooledu.MyanmarSpelling.fragment.SettingFragment;
////
////import java.util.ArrayList;
////import java.util.List;
////public class MainFragmentActivity extends AppCompatActivity {
////    private ViewPager viewPager;
////    private BottomNavigationView bottomNavigationView;
////    private CardView bottomCardView;
////    private boolean showSplash = true;
////    private List<Fragment> fragmentList = new ArrayList<>();
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.fragmentactivity_main);
////        initializeViews();
////        setupFragments();
////        setupViewPager();
////        setupNavigation();
////    }
////
////    private void initializeViews() {
////        viewPager = findViewById(R.id.viewPager);
////        bottomNavigationView = findViewById(R.id.navigation);
////        bottomCardView = findViewById(R.id.cardView);
////    }
////
////    private void setupFragments() {
////        fragmentList.add(new HomeFragment());
////        fragmentList.add(new HomeFragment());
////        fragmentList.add(new HomeFragment());
////        fragmentList.add(new SettingFragment()); // Now works without interface
////    }
////
////    private void setupViewPager() {
////        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
////        viewPager.setAdapter(adapter);
////        viewPager.setOffscreenPageLimit(fragmentList.size());
////
////        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
////            @Override
////            public void onPageSelected(int position) {
////                bottomNavigationView.getMenu().getItem(position).setChecked(true);
////                updateUIForPosition(position);
////            }
////        });
////    }
////
////    private void updateUIForPosition(int position) {
////        bottomCardView.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
////    }
////
////    private void setupNavigation() {
////        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
////            int itemId = item.getItemId();
////            if (itemId == R.id.navigation_home) {
////                viewPager.setCurrentItem(0);
////            } else if (itemId == R.id.navigation_movies) {
////                viewPager.setCurrentItem(1);
////            } else if (itemId == R.id.navigation_series) {
////                viewPager.setCurrentItem(2);
////            } else if (itemId == R.id.navigation_setting) {
////                viewPager.setCurrentItem(3);
////            }
////            return true;
////        });
////    }
////
////
////    private static class ViewPagerAdapter extends FragmentPagerAdapter {
////        private final List<Fragment> fragments;
////
////        ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
////            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
////            this.fragments = fragments;
////        }
////
////        @Override
////        public Fragment getItem(int position) {
////            return fragments.get(position);
////        }
////
////        @Override
////        public int getCount() {
////            return fragments.size();
////        }
////    }
////}
//
//
//
//
//package com.mmschooledu;
//
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.mmschooledu.Story.fragment.FavouriteFragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MainFragmentActivity extends AppCompatActivity {
//    private ViewPager viewPager;
//    private BottomNavigationView bottomNavigationView;
//    private CardView bottomCardView;
//    private final List<Fragment> fragmentList = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragmentactivity_main);
//
//        initializeViews();
//        setupFragments();
//        setupViewPager();
//        setupNavigation();
//    }
//
//    private void initializeViews() {
//        viewPager = findViewById(R.id.viewPager);
//        bottomNavigationView = findViewById(R.id.navigation);
//        bottomCardView = findViewById(R.id.cardView);
//    }
//
//    private void setupFragments() {
//        fragmentList.add(new HomeFragment());
//        fragmentList.add(new Main1Fragment());
//        fragmentList.add(new Main3Fragment());
//        fragmentList.add(new SettingsFragment());
//    }
//
//    private void setupViewPager() {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
//        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(fragmentList.size());
//
//        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                bottomNavigationView.getMenu().getItem(position).setChecked(true);
//              //  updateUIForPosition(position);
//            }
//        });
//    }
//
//    private void setupNavigation() {
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.navigation_home) {
//                viewPager.setCurrentItem(0);
//            } else if (itemId == R.id.navigation_movies) {
//                viewPager.setCurrentItem(1);
//            } else if (itemId == R.id.navigation_series) {
//                viewPager.setCurrentItem(2);
//            } else if (itemId == R.id.navigation_setting) {
//                viewPager.setCurrentItem(3);
//            }
//            return true;
//        });
//    }
//
//    private static class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> fragments;
//
//        ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
//            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//            this.fragments = fragments;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragments.size();
//        }
//    }
//}


package com.mmschooledu;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private CardView bottomCardView;
    private final List<Fragment> fragmentList = new ArrayList<>();
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLocale.applySaved(this);
        setContentView(R.layout.fragmentactivity_main);

        initializeViews();
        setupFragments();
        setupViewPager();
        setupNavigation();
    }

    private void initializeViews() {
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomCardView = findViewById(R.id.cardView);
    }

    private void setupFragments() {
        fragmentList.add(new HomeFragment());
        fragmentList.add(new Main1Fragment());
        fragmentList.add(new Main3Fragment());
        fragmentList.add(new SettingsFragment());
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
    }

    private void setupNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                viewPager.setCurrentItem(0);
            } else if (itemId == R.id.navigation_movies) {
                viewPager.setCurrentItem(1);
            } else if (itemId == R.id.navigation_series) {
                viewPager.setCurrentItem(2);
            } else if (itemId == R.id.navigation_setting) {
                viewPager.setCurrentItem(3);
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0) {
            // If not on home screen, go to home screen
            viewPager.setCurrentItem(0);
        } else {
            // If on home screen, implement double back to exit
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                finishAffinity();
                return;
            } else {
                backToast = Toast.makeText(this, R.string.press_back_again_to_exit, Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragments;

        ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}