package com.mmschooledu.MyanmarSpelling.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.mmschooledu.MyanmarSpelling.database.DBOpenHelper;
import com.mmschooledu.MyanmarSpelling.fragment.DictionaryTitleFragment;
import com.mmschooledu.MyanmarSpelling.fragment.DictionaryTitleFragment2;
import com.mmschooledu.MyanmarSpelling.fragment.DifferentInfo;
import com.mmschooledu.MyanmarSpelling.fragment.HomeTabFragment;
import com.mmschooledu.MyanmarSpelling.fragment.InfoFragment;
import com.mmschooledu.MyanmarSpelling.fragment.SameInfoFragment;
import com.mmschooledu.MyanmarSpelling.fragment.SettingFragment;
import com.mmschooledu.MyanmarSpelling.fragment.StoryTitleFragment;
import com.mmschooledu.MyanmarSpelling.fragment.StoryTitleFragment2;
//import com.mmschooledu.MyanmarSpelling.models.Favourite;
import com.mmschooledu.MyanmarSpelling.models.Favourite;
import com.mmschooledu.MyanmarSpelling.models.Recent;
import com.mmschooledu.MyanmarSpelling.models.Story;
import com.mmschooledu.MyanmarSpelling.utils.MDetect;
import com.mmschooledu.R;

public class MmSpellingMainActivity extends AppCompatActivity implements
        DictionaryTitleFragment.OnWordClickListener,
        DictionaryTitleFragment2.OnWordClickListener,
        StoryTitleFragment.OnWordClickListener,
        StoryTitleFragment2.OnWordClickListener,
//        InfoFragment.OnFavouriteCallbackListener,
//        RecentFragment.OnRecentCallbackListener,
        SettingFragment.OnSettingChangeListener {

    private static final String TAG = "MmSpellingMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        // load theme
//        if (SharePref.getInstance(this).getPrefNightModeState()) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mmspelling_activity_main);
        MDetect.init(this);

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle(MDetect.getDeviceEncodedText(getString(R.string.mmspelling)));
        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.createDatabase();
        dbHelper.openDatabase();

        Cursor cursor = dbHelper.getAllTableNames();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String tableName = cursor.getString(cursor.getColumnIndex("name"));
                Log.d("TableName", "Table: " + tableName);
            } while (cursor.moveToNext());
            cursor.close();
        }

        dbHelper.close();


//        openFragment(new HomeFragment());
        if (savedInstanceState == null) {
            openFragment(new HomeTabFragment());
        }

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);

        navView.setOnNavigationItemSelectedListener(item -> {

            Fragment selectedFragment;

            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment = new HomeTabFragment();
            } else if (item.getItemId() == R.id.navigation_favourite) {
                selectedFragment = new SameInfoFragment();
            } else if (item.getItemId() == R.id.navigation_recent) {
                selectedFragment = new DifferentInfo();
            } else if (item.getItemId() == R.id.navigation_setting) {
                selectedFragment = new SettingFragment();
            } else if (item.getItemId() == R.id.navigation_info) {
                selectedFragment = new InfoFragment();
            } else {
                return false; // No valid selection
            }

            openFragment(selectedFragment);
            return true;
        });
    }

        @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void openFragment(Fragment selectedFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, selectedFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onWordClick(Story word) {
        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.openDatabase();
        word.setDescriptionUnicode(dbHelper.getDetail(word.getId()));
        Log.d(TAG, "onWordClick: " + word.getId());
        Log.d(TAG, "onWordClick: " + word.getNameUnicode());
        Intent intent = new Intent(this, MmSpellingDetailActivity.class);
        intent.putExtra("detail", word);
        startActivity(intent);
    }

    @Override
    public void onChangeListener() {
        //       openFragment(new HomeFragment());
        recreate();
//        openFragment(new SettingFragment());
    }
//
//    @Override
//    public void onFavouriteClick(Favourite favourite) {
//        DBOpenHelper dbHelper = new DBOpenHelper(this);
//        dbHelper.openDatabase();
//        Story story = new Story(favourite.getId(),favourite.getStoryTypeId(), favourite.getNameUnicode(),favourite.getDescriptionUnicode());
//        story.setStoryTypeId(dbHelper.getStoryTypeIdById(story.getId()));
//        story.setNameUnicode(dbHelper.getNameUnicodeById(story.getId()));
//        story.setDescriptionUnicode(dbHelper.getDescriptionUnicodeById(story.getId()));
//        Log.d(TAG, "onWordClick Favourite: " + story.getNameUnicode());
//        Intent intent = new Intent(this, MmSpellingDetailActivity.class);
//        intent.putExtra("detail", story);
//        startActivity(intent);
//
//    }
//
//    @Override
//    public void onRecentClick(Recent recent) {
//        DBOpenHelper dbHelper = new DBOpenHelper(this);
//        dbHelper.openDatabase();
//        Story story = new Story(recent.getId(),recent.getStoryTypeId(), recent.getNameUnicode(),recent.getDescriptionUnicode());
//        story.setStoryTypeId(dbHelper.getStoryTypeIdById(story.getId()));
//        story.setNameUnicode(dbHelper.getNameUnicodeById(story.getId()));
//        story.setDescriptionUnicode(dbHelper.getDescriptionUnicodeById(story.getId()));
//        Log.d(TAG, "onWordClick Recent : " + story.getNameUnicode());
//        Intent intent = new Intent(this, MmSpellingDetailActivity.class);
//        intent.putExtra("detail", story);
//        startActivity(intent);
//    }

}
