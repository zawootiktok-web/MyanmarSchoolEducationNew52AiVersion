package com.mmschooledu.MyanmarSpelling.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;



import com.mmschooledu.MyanmarSpelling.database.DBOpenHelper;
import com.mmschooledu.MyanmarSpelling.models.Recent;
import com.mmschooledu.MyanmarSpelling.models.Story;
import com.mmschooledu.MyanmarSpelling.utils.MDetect;
import com.mmschooledu.MyanmarSpelling.utils.SharePref;
import com.mmschooledu.R;

public class MmSpellingDetailActivity extends AppCompatActivity {

    private static final String TAG = "MmSpellingDetailActivity";
    private TextView tv_detail;
    private Story story;

    private int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_activity_detail);
        MDetect.init(this);
        setTitle(MDetect.getDeviceEncodedText(getString(R.string.title_detail)));
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the Story object from Intent
        story = getIntent().getParcelableExtra("detail");

        // Log the details of the Story object
        if (story != null) {
            currentId = story.getId();
            Log.d(TAG, "Detail ID: " + story.getId());
            Log.d(TAG, "Detail Story Type Id: " + story.getStoryTypeId());
            Log.d(TAG, "Detail Description (Unicode): " + story.getDescriptionUnicode());
        } else {
            Log.e(TAG, "No detail object found in Intent");
        }

        tv_detail = findViewById(R.id.tv_detail);

        if (story != null) {
            tv_detail.setText(MDetect.getDeviceEncodedText(story.getDescriptionUnicode()));
            tv_detail.setTextSize(SharePref.getInstance(this).getPrefFontSize());
        }

        tv_detail.setTextIsSelectable(true);
        manageRecent(story != null ? currentId : -1); // Pass -1 if detail is null

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tools, menu);
        MenuItem fav = menu.findItem(R.id.menu_favourite);
        setIcon(fav); // set icon based on bookmark exist ot not
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
////            case R.id.menu_copynew:
////                copyToClipboard();
////                return true;
////            case R.id.menu_favouritenew:
////                manageFavourites(item);
////                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_copy) {
            copyToClipboard();
            return true;
        } else if (id == R.id.menu_favourite) {
            manageFavourites(item);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    private void copyToClipboard() {
        String textToCopy = tv_detail.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copy", textToCopy);
        clipboard.setPrimaryClip(clip);
        Snackbar.make(tv_detail, MDetect.getDeviceEncodedText("ကော်ပီကူးယူပြီးပါပြီ"), Snackbar.LENGTH_SHORT).show();

    }
    private void setIcon(MenuItem item) {
        item.setIcon(isFavouriteExist(currentId) ? R.drawable.ic_added_favorite : R.drawable.ic_add_to_favourite);
    }
    private boolean isFavouriteExist(int id) {
        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.openDatabase();
        return dbHelper.isFavouriteExist(id);
    }
    private void manageFavourites(MenuItem item) {
        if (isFavouriteExist(currentId)) {
            removeFromFavourite(currentId);
            item.setIcon(R.drawable.ic_add_to_favourite);
        } else {
            addToFavourite(currentId);
            item.setIcon(R.drawable.ic_added_favorite);
        }
    }

    private void addToFavourite(int id) {
        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.openDatabase();
        dbHelper.addToFavourite(id);
        Snackbar.make(tv_detail, MDetect.getDeviceEncodedText("စိတ်ကြိုက်စာရင်းသို့ ထည့်လိုက်ပါပြီ။"), Snackbar.LENGTH_SHORT).show();
    }

    private void removeFromFavourite(int id) {
        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.openDatabase();
        dbHelper.removeFromFavourite(id);
        Snackbar.make(tv_detail, MDetect.getDeviceEncodedText("စိတ်ကြိုက်စာရင်းမှ ပယ်ဖျက်လိုက်ပါပြီ"), Snackbar.LENGTH_SHORT).show();
    }

    private void fetchRecentData() {
        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.openDatabase();
        Log.d(TAG, "Fetching recent data...");
        List<Recent> recentIds = dbHelper.getAllRecents(); // Fetch all IDs

    }

    private void manageRecent(int storyId) {

        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.openDatabase(); // Open the database
        boolean exists = dbHelper.isRecentExist(storyId);
        Log.d(TAG, "Checking if StoryId exists: " + storyId + " | Exists: " + exists);
        if (!exists) {
            dbHelper.addToRecent(storyId);
            Log.d(TAG, "Added to recent: StoryId=" + storyId );
        } else {
            Log.d(TAG, "StoryId already exists in recent: " + storyId);
        }
        dbHelper.close();
    }
    /* if (!exists) {
            dbHelper.addToRecent(id);
            Log.d(TAG, "Added to recent: ID=" + id );
        } else {
            Log.d(TAG, "ID already exists in recent: " + id);
        }
        /* if (!isRecentExist(id)) {
            addToRecent(id);
            Log.d(TAG, "ManageRecent Id: " + id);
        }*/

   /* private boolean isRecentExist(int id) {
        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.openDatabase();
        return dbHelper.isRecentExist(id);
    }

    private void addToRecent(int id) {
        DBOpenHelper dbHelper = new DBOpenHelper(this);
        dbHelper.openDatabase();
       // dbHelper.addToRecent(id);
    }*/

}