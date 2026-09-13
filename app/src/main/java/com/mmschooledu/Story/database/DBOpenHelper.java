package com.mmschooledu.Story.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.mmschooledu.Story.models.Favourite;
import com.mmschooledu.Story.models.Recent;
import com.mmschooledu.Story.models.Story;
import com.mmschooledu.Story.models.StoryType;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBOpenHelper";
    private static final String DATABASE_NAME = "newstory.db";
    private static final String DATABASE_PATH = "/data/data/com.mmschooledu/database/"; // Replace with your package name
    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No need to create tables since we're using a pre-built database.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if necessary.
    }

    public void createDatabase() {
        if (!checkDatabase()) {
            this.getReadableDatabase();
            this.close();
            copyDatabase();
        }
    }

    private boolean checkDatabase() {
        File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    private void copyDatabase() {
        try {
            InputStream input = mContext.getAssets().open("databases/" + DATABASE_NAME);
            String outputFileName = DATABASE_PATH + DATABASE_NAME;
            File databaseFolder = new File(DATABASE_PATH);

            if (!databaseFolder.exists()) {
                databaseFolder.mkdirs();
            }

            OutputStream output = new FileOutputStream(outputFileName);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            input.close();

            Log.d(TAG, "Database copied successfully to internal storage.");
        } catch (Exception e) {
            Log.e(TAG, "Error copying database: " + e.getMessage());
        }
    }


    public void openDatabase() {
        try {
            String path = DATABASE_PATH + DATABASE_NAME;
            mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
            Log.d(TAG, "Database opened successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Error opening database: " + e.getMessage());
        }
    }


    @Override
    public synchronized void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    // Method to query "story_type" table
    public Cursor getStoryTypes() {
        String query = "SELECT * FROM story_type";
        return mDatabase.rawQuery(query, null);
    }

    public Cursor getAllTableNames() {
        Cursor cursor = null;
        try {
            String query = "SELECT name FROM sqlite_master WHERE type = 'table' AND name NOT LIKE 'sqlite_%'";
            cursor = mDatabase.rawQuery(query, null);
            Log.d(TAG, "Fetched table names successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Error fetching table names: " + e.getMessage());
        }
        return cursor;
    }

    public Cursor getStoryTitles(int storyTypeId) {
        // Adjusted query with a placeholder for parameter binding
        String query = "SELECT * FROM story WHERE story_type_id = ?";
        // Return cursor
        return mDatabase.rawQuery(query, new String[]{String.valueOf(storyTypeId)});
    }
    @SuppressLint("Range")
    public String getDetail(int rowid) {
        String detail = "";
        Cursor cursor = mDatabase
                .rawQuery("SELECT description_unicode FROM story WHERE rowid = " + rowid, null);
        if (cursor != null && cursor.moveToFirst()) {
            detail = cursor.getString(cursor.getColumnIndex("description_unicode"));
        }
        cursor.close();
        return detail;
    }
    @SuppressLint("Range")
    public String getNameUnicode(int id) {
        String word = "";
        String query = "SELECT name_Unicode FROM story WHERE story_type_id = ?";
        Cursor cursor = null;

        try {
            cursor = mDatabase.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor != null && cursor.moveToFirst()) {
                word = cursor.getString(cursor.getColumnIndex("name_Unicode"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return word;
    }
    @SuppressLint("Range")
    public String getDescriptionUnicode(int id) {
        String word = "";
        String query = "SELECT description_unicode FROM story WHERE story_type_id = ?";
        Cursor cursor = null;

        try {
            cursor = mDatabase.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor != null && cursor.moveToFirst()) {
                word = cursor.getString(cursor.getColumnIndex("description_unicode"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return word;
    }
    @SuppressLint("Range")
    public String getNameUnicodeById(int id) {
        String word = "";
        String query = "SELECT name_Unicode FROM story WHERE id = ?";
        Cursor cursor = null;

        try {
            cursor = mDatabase.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor != null && cursor.moveToFirst()) {
                word = cursor.getString(cursor.getColumnIndex("name_Unicode"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return word;
    }
    @SuppressLint("Range")
    public String getDescriptionUnicodeById(int id) {
        String word = "";
        String query = "SELECT description_unicode FROM story WHERE id = ?";
        Cursor cursor = null;

        try {
            cursor = mDatabase.rawQuery(query, new String[]{String.valueOf(id)});
            if (cursor != null && cursor.moveToFirst()) {
                word = cursor.getString(cursor.getColumnIndex("description_unicode"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return word;
    }
    @SuppressLint("Range")
    public int getStoryTypeIdById(int rowId) {
        int storyTypeId = 0;
        String query = "SELECT story_type_id FROM story WHERE id = ?";
        Cursor cursor = null;

        try {
            cursor = mDatabase.rawQuery(query, new String[]{String.valueOf(rowId)});
            if (cursor != null && cursor.moveToFirst()) {
                storyTypeId = cursor.getInt(cursor.getColumnIndex("story_type_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return storyTypeId;
    }
    public void addToRecent(int storyId) {
        if (!isRecentExist(storyId)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("story_id", storyId);
            try {
                long result = mDatabase.insert("recent_stories", null, contentValues);
                if (result == -1) {
                    Log.e("DBOpenHelper", "Failed to add to recent.");
                } else {
                    Log.d("DBOpenHelper", "Successfully added to recent.");
                }
            } catch (Exception e) {
                Log.e("DBOpenHelper", "Error adding to recent: " + e.getMessage());
            }
        } else {
            Log.d("DBOpenHelper", "Story ID already exists in recent.");
        }
    }

    public boolean isRecentExist(int storyId) {
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM recent_stories WHERE story_id = ?";
            Log.d("DBOpenHelper", "Query: " + query + " with storyId: " + storyId);  // Log query and ID
            cursor = mDatabase.rawQuery(query, new String[]{String.valueOf(storyId)});

            boolean exists = cursor.moveToFirst();  // Returns true if the storyId exists
            Log.d("DBOpenHelper", "isRecentExist result: " + exists);  // Log result
            return exists;
        } catch (Exception e) {
            Log.e("DBOpenHelper", "Error checking if recent exists: " + e.getMessage());
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public ArrayList<Recent> getAllRecents() {
        ArrayList<Recent> recents = new ArrayList<>();
        String query = "SELECT * FROM recent_stories";

        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    // Safely get column indices
                    int idIndex = cursor.getColumnIndex("id");
                    int storyIdIndex = cursor.getColumnIndex("story_id");

                    // Check if columns exist
                    if (idIndex != -1 && storyIdIndex != -1) {
                        int id = cursor.getInt(idIndex);
                        int storyId = cursor.getInt(storyIdIndex);

                        // Fetch other details (if needed)
                        int storyTypeId = getStoryTypeIdById(storyId);
                        String nameUnicode = getNameUnicodeById(storyId);
                        String descriptionUnicode = getDescriptionUnicodeById(storyId);

                        // Log each recent record for debugging
                        Log.d("DBOpenHelper", "Recent ID: " + id + ", Story ID: " + storyId +
                                ", Story Type ID: " + storyTypeId + ", Name Unicode: " + nameUnicode +
                                ", Description Unicode: " + descriptionUnicode);

                        // Add to the recents list
                        recents.add(new Recent(storyId, storyTypeId, nameUnicode, descriptionUnicode));
                    } else {
                        Log.e("DBOpenHelper", "Missing expected columns in recent_stories table");
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return recents;
    }
    public ArrayList<Favourite> getAllFavourites() {
        ArrayList<Favourite> favourites = new ArrayList<>();
        String query = "SELECT * FROM favourite_stories";

        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    // Safely get column indices
                    int idIndex = cursor.getColumnIndex("id");
                    int storyIdIndex = cursor.getColumnIndex("story_id");

                    // Check if columns exist
                    if (idIndex != -1 && storyIdIndex != -1) {
                        int id = cursor.getInt(idIndex);
                        int storyId = cursor.getInt(storyIdIndex);

                        // Fetch other details (if needed)
                        int storyTypeId = getStoryTypeIdById(storyId);
                        String nameUnicode = getNameUnicodeById(storyId);
                        String descriptionUnicode = getDescriptionUnicodeById(storyId);

                        // Log each recent record for debugging
                        Log.d("DBOpenHelper", "Recent ID: " + id + ", Story ID: " + storyId +
                                ", Story Type ID: " + storyTypeId + ", Name Unicode: " + nameUnicode +
                                ", Description Unicode: " + descriptionUnicode);

                        // Add to the recents list
                        favourites.add(new Favourite(storyId, storyTypeId, nameUnicode, descriptionUnicode));
                    } else {
                        Log.e("DBOpenHelper", "Missing expected columns in recent_stories table");
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return favourites;
    }
    public boolean isFavouriteExist(int storyId) {
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM favourite_stories WHERE story_id = ?";
            Log.d("DBOpenHelper", "Query: " + query + " with storyId: " + storyId);  // Log query and ID
            cursor = mDatabase.rawQuery(query, new String[]{String.valueOf(storyId)});

            boolean exists = cursor.moveToFirst();  // Returns true if the storyId exists
            Log.d("DBOpenHelper", "isRecentExist result: " + exists);  // Log result
            return exists;
        } catch (Exception e) {
            Log.e("DBOpenHelper", "Error checking if recent exists: " + e.getMessage());
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public void addToFavourite(int storyId) {
        if (!isFavouriteExist(storyId)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("story_id", storyId);
            try {
                long result = mDatabase.insert("favourite_stories", null, contentValues);
                if (result == -1) {
                    Log.e("DBOpenHelper", "Failed to add to recent.");
                } else {
                    Log.d("DBOpenHelper", "Successfully added to recent.");
                }
            } catch (Exception e) {
                Log.e("DBOpenHelper", "Error adding to recent: " + e.getMessage());
            }
        } else {
            Log.d("DBOpenHelper", "Story ID already exists in recent.");
        }
    }

    public void removeFromFavourite(int storyId) {
        try {
            int rowsDeleted = mDatabase.delete("favourite_stories", "story_id = ?", new String[]{String.valueOf(storyId)});
            if (rowsDeleted > 0) {
                Log.d(TAG, "Successfully removed from favourites.");
            } else {
                Log.e(TAG, "Failed to remove from favourites.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error removing from favourites: " + e.getMessage());
        }
    }
    public void removeAllFavourite() {
        mDatabase.execSQL("DELETE FROM favourite_stories");
    }


    // Remove a specific record from the recent table
    public void removeFromRecent(int id) {
        String query = "DELETE FROM recent_stories WHERE id = ?";
        mDatabase.execSQL(query, new Object[]{id});
    }

    // Remove all records from the recent table
    public void removeAllRecent() {
        String query = "DELETE FROM recent_stories";
        mDatabase.execSQL(query);
    }

    public int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

}
