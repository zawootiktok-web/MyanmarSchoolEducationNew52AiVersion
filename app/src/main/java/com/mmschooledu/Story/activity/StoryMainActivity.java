package com.mmschooledu.Story.activity;

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


import com.mmschooledu.R;
import com.mmschooledu.Story.database.DBOpenHelper;
import com.mmschooledu.Story.fragment.DamaTitleFragment;
import com.mmschooledu.Story.fragment.FavouriteFragment;
import com.mmschooledu.Story.fragment.HomeTabFragment;
import com.mmschooledu.Story.fragment.InfoFragment;
import com.mmschooledu.Story.fragment.RecentFragment;
import com.mmschooledu.Story.fragment.SettingFragment;
import com.mmschooledu.Story.fragment.StoryTitleFragment;
import com.mmschooledu.Story.models.Favourite;
import com.mmschooledu.Story.models.Recent;
import com.mmschooledu.Story.models.Story;
import com.mmschooledu.Story.utils.MDetect;

public class StoryMainActivity extends AppCompatActivity implements

        StoryTitleFragment.OnWordClickListener,
        DamaTitleFragment.OnWordClickListener,
        FavouriteFragment.OnFavouriteCallbackListener,
        RecentFragment.OnRecentCallbackListener,
        SettingFragment.OnSettingChangeListener {

    private static final String TAG = "StoryMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        // load theme
//        if (SharePref.getInstance(this).getPrefNightModeState()) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_main);
        MDetect.init(this);

        setSupportActionBar(findViewById(R.id.toolbar));
        setTitle(MDetect.getDeviceEncodedText(getString(R.string.app_name_story)));
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
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment = new HomeTabFragment();
            } else if (item.getItemId() == R.id.navigation_favourite) {
                selectedFragment = new FavouriteFragment();
            } else if (item.getItemId() == R.id.navigation_recent) {
                selectedFragment = new RecentFragment();
            } else if (item.getItemId() == R.id.navigation_setting) {
                selectedFragment = new SettingFragment();
            } else if (item.getItemId() == R.id.navigation_info) {
                selectedFragment = new InfoFragment();
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
        Intent intent = new Intent(this, StoryDetailActivity.class);
        intent.putExtra("detail", word);
        startActivity(intent);
    }

    @Override
    public void onChangeListener() {
        //       openFragment(new HomeFragment());
        recreate();
//        openFragment(new SettingFragment());
    }

    @Override
    public void onFavouriteClick(Favourite favourite) {
        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.openDatabase();
        Story story = new Story(favourite.getId(),favourite.getStoryTypeId(), favourite.getNameUnicode(),favourite.getDescriptionUnicode());
        story.setStoryTypeId(dbHelper.getStoryTypeIdById(story.getId()));
        story.setNameUnicode(dbHelper.getNameUnicodeById(story.getId()));
        story.setDescriptionUnicode(dbHelper.getDescriptionUnicodeById(story.getId()));
        Log.d(TAG, "onWordClick Favourite: " + story.getNameUnicode());
        Intent intent = new Intent(this, StoryDetailActivity.class);
        intent.putExtra("detail", story);
        startActivity(intent);

    }

    @Override
    public void onRecentClick(Recent recent) {
        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.openDatabase();
        Story story = new Story(recent.getId(),recent.getStoryTypeId(), recent.getNameUnicode(),recent.getDescriptionUnicode());
        story.setStoryTypeId(dbHelper.getStoryTypeIdById(story.getId()));
        story.setNameUnicode(dbHelper.getNameUnicodeById(story.getId()));
        story.setDescriptionUnicode(dbHelper.getDescriptionUnicodeById(story.getId()));
        Log.d(TAG, "onWordClick Recent : " + story.getNameUnicode());
        Intent intent = new Intent(this, StoryDetailActivity.class);
        intent.putExtra("detail", story);
        startActivity(intent);
    }

}
