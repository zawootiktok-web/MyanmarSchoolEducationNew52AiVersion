package com.mmschooledu.examresults;

//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
////import com.lotee.examresults.ui.fragment.AboutFragment;
////import com.lotee.examresults.ui.fragment.RecnetFragment;
////import com.lotee.examresults.ui.fragment.TabFragment;
//import com.mmschooledu.R;
//import com.mmschooledu.examresults.ui.fragment.AboutFragment;
//import com.mmschooledu.examresults.ui.fragment.RecnetFragment;
//import com.mmschooledu.examresults.ui.fragment.TabFragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ExamMainActivity extends AppCompatActivity {
//    BottomNavigationView navigationView;
//    ActionBar actionBar ;
//    ViewPager viewPager;
//    boolean disDhowed=false;
//
//    private static final int APP_UPDATE_REQUEST_CODE = 123;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.examactivity_main);
//        navigationView = findViewById(R.id.navigation);
//        viewPager= findViewById(R.id.viewPager);
//        viewPager.setOffscreenPageLimit(2);
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new TabFragment());
//        fragments.add(new RecnetFragment());
//        fragments.add(new AboutFragment());
//
//        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
//        viewPager.setAdapter(pagerAdapter);
//
//// Set the ViewPager's page change listener to update the selected item in the BottomNavigationView
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                navigationView.getMenu().getItem(position).setChecked(true);
//
//               showTitle();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
////
////        actionBar= getSupportActionBar();
////// Set the BottomNavigationView's item click listener to update the current page in the ViewPager
////        navigationView.setOnNavigationItemSelectedListener(item -> {
////            switch (item.getItemId()) {
////                case R.id.navigation_exam_result:
////                    viewPager.setCurrentItem(0);
////                    return true;
////
////                case R.id.navigation_exam_recent:
////                    viewPager.setCurrentItem(1);
////                    return true;
////
////                case R.id.navigation_about:
////                    viewPager.setCurrentItem(2);
////                    return true;
////            }
////            return false;
////        });
////       showTitle();
////    }
//
//
//        ActionBar actionBar = getSupportActionBar();
//
//// Set the BottomNavigationView's item selected listener to update the current page in the ViewPager
//        navigationView.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//
//            if (itemId == R.id.navigation_exam_result) {
//                viewPager.setCurrentItem(0);
//                showTitle();
//                return true;
//            } else if (itemId == R.id.navigation_exam_recent) {
//                viewPager.setCurrentItem(1);
//                showTitle();
//                return true;
//            } else if (itemId == R.id.navigation_about) {
//                viewPager.setCurrentItem(2);
//                showTitle();
//                return true;
//            }
//
//            return false;
//        });
//        showTitle();
//    }
//
//
//
//        void showTitle() {
//        if (actionBar != null) {
//            int selectedItemId = navigationView.getSelectedItemId();
//            Menu menu = navigationView.getMenu();
//            MenuItem selectedItem = menu.findItem(selectedItemId);
//            String title = (String) selectedItem.getTitle();
//            actionBar.setTitle(title);
//            // actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
//        }
//    }
//    public class PagerAdapter extends FragmentPagerAdapter {
//
//        private List<Fragment> fragments;
//
//        public PagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
//            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
//    @Override
//    public void onBackPressed() {
//
//        if(viewPager.getCurrentItem()!=0) {
//            viewPager.setCurrentItem(0);
//        }else {
//            final AlertDialog dialog = new AlertDialog.Builder(this).create();
//            View parent_view = LayoutInflater.from(this).inflate(R.layout.two_button_dia, null);
//            dialog.setView(parent_view);
//            dialog.setCancelable(true);
//            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            dialog.show();
//            ImageView iv = (ImageView) dialog.findViewById(R.id.icon);
//            TextView tv_title = (TextView) dialog.findViewById(R.id.title);
//            TextView tv_message = (TextView) dialog.findViewById(R.id.message);
//            final Button call = (Button) dialog.findViewById(R.id.bt2);
//            final Button cancel = (Button) dialog.findViewById(R.id.bt1);
//            iv.setImageResource(R.drawable.apk);
//            tv_title.setText("5Stars/Exit");
//            tv_message.setText(getString(R.string.exit_message));
//            cancel.setText("Exit");
//            cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View p1) {
//                    finish();
//                    dialog.dismiss();
//
//                }
//            });
//            call.setText("5Stars");
//            call.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View p1) {
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
//
//                    dialog.dismiss();
//                }
//            });
//        }
//
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        showDisc();
//
//    }
//    // Load the App Open Ad
//void showDisc(){
//        if(!disDhowed){
//
//
//    final AlertDialog dialog = new AlertDialog.Builder(this).create();
//    View parent_view = LayoutInflater.from(this).inflate(R.layout.two_button_dia, null);
//    dialog.setView(parent_view);
//    dialog.setCancelable(true);
//    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//    dialog.show();
//    ImageView iv = (ImageView) dialog.findViewById(R.id.icon);
//    TextView tv_title = (TextView) dialog.findViewById(R.id.title);
//    TextView tv_message = (TextView) dialog.findViewById(R.id.message);
//    final Button call = (Button) dialog.findViewById(R.id.bt2);
//    final Button cancel = (Button) dialog.findViewById(R.id.bt1);
//    iv.setImageResource(R.drawable.apk);
//    tv_title.setText("Disclaimer: ");
//    tv_message.setText(getString(R.string.exit_message));
//    //cancel.setText("Exit");
//    cancel.setVisibility(View.GONE);
//    call.setText("Understand");
//    call.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View p1) {
//            dialog.dismiss();
//        }
//    });
//    disDhowed=true;
//        }
//}
//
//
//}




import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.mmschooledu.R;
import com.mmschooledu.examresults.ui.fragment.AboutFragment;
import com.mmschooledu.examresults.ui.fragment.RecnetFragment;
import com.mmschooledu.examresults.ui.fragment.ResultFragment;
import com.mmschooledu.examresults.ui.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

public class ExamMainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    ActionBar actionBar ;
    ViewPager viewPager;
    boolean disDhowed=false;

    private static final int APP_UPDATE_REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examactivity_main);
        navigationView = findViewById(R.id.navigation);
        viewPager= findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TabFragment());
        fragments.add(new RecnetFragment());
        fragments.add(new AboutFragment());

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);

// Set the ViewPager's page change listener to update the selected item in the BottomNavigationView
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                navigationView.getMenu().getItem(position).setChecked(true);

                showTitle();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
//
//        actionBar= getSupportActionBar();
//// Set the BottomNavigationView's item click listener to update the current page in the ViewPager
//        navigationView.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.navigation_exam_result:
//                    viewPager.setCurrentItem(0);
//                    return true;
//
//                case R.id.navigation_exam_recent:
//                    viewPager.setCurrentItem(1);
//                    return true;
//
//                case R.id.navigation_about:
//                    viewPager.setCurrentItem(2);
//                    return true;
//            }
//            return false;
//        });
//       showTitle();
//    }


        ActionBar actionBar = getSupportActionBar();

// Set the BottomNavigationView's item selected listener to update the current page in the ViewPager
        navigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_exam_result) {
                viewPager.setCurrentItem(0);
                showTitle();
                return true;
            } else if (itemId == R.id.navigation_exam_recent) {
                viewPager.setCurrentItem(1);
                showTitle();
                return true;
            } else if (itemId == R.id.navigation_about) {
                viewPager.setCurrentItem(2);
                showTitle();
                return true;
            }

            return false;
        });
        showTitle();
    }



    void showTitle() {
        if (actionBar != null) {
            int selectedItemId = navigationView.getSelectedItemId();
            Menu menu = navigationView.getMenu();
            MenuItem selectedItem = menu.findItem(selectedItemId);
            String title = (String) selectedItem.getTitle();
            actionBar.setTitle(title);
            // actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }
    public class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public PagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
    @Override
    public void onBackPressed() {

        if(viewPager.getCurrentItem()!=0) {
            viewPager.setCurrentItem(0);
        }else {
            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            View parent_view = LayoutInflater.from(this).inflate(R.layout.two_button_dia, null);
            dialog.setView(parent_view);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
            ImageView iv = (ImageView) dialog.findViewById(R.id.icon);
            TextView tv_title = (TextView) dialog.findViewById(R.id.title);
            TextView tv_message = (TextView) dialog.findViewById(R.id.message);
            final Button call = (Button) dialog.findViewById(R.id.bt2);
            final Button cancel = (Button) dialog.findViewById(R.id.bt1);
            iv.setImageResource(R.drawable.outline_star_white_20);
            tv_title.setText("5Stars/Exit");
            tv_message.setText(getString(R.string.exit_message));
            cancel.setText("Exit");
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p1) {
                    finish();
                    dialog.dismiss();

                }
            });
            call.setText("5Stars");
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p1) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));

                    dialog.dismiss();
                }
            });
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        showDisc();

    }
    // Load the App Open Ad
    void showDisc(){
        if(!disDhowed){


            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            View parent_view = LayoutInflater.from(this).inflate(R.layout.two_button_dia, null);
            dialog.setView(parent_view);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
            ImageView iv = (ImageView) dialog.findViewById(R.id.icon);
            TextView tv_title = (TextView) dialog.findViewById(R.id.title);
            TextView tv_message = (TextView) dialog.findViewById(R.id.message);
            final Button call = (Button) dialog.findViewById(R.id.bt2);
            final Button cancel = (Button) dialog.findViewById(R.id.bt1);
            iv.setImageResource(R.drawable.warning_24px);
            tv_title.setText("Disclaimer: ");
            tv_message.setText(getString(R.string.disc_message));
            //cancel.setText("Exit");
            cancel.setVisibility(View.GONE);
            call.setText("Understand");
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p1) {
                    dialog.dismiss();
                }
            });
            disDhowed=true;
        }
    }


}