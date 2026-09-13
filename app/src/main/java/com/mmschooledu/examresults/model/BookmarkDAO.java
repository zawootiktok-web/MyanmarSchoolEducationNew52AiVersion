package com.mmschooledu.examresults.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//import com.lotee.examresults.db.DBHelper;

import com.mmschooledu.examresults.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class BookmarkDAO {
    private DBHelper dbHelper;

    public BookmarkDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addBookmark(Bookmark bookmark) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Check if a bookmark with the same title and URL already exists
        String whereClause = DBHelper.COLUMN_TITLE + "=? AND " + DBHelper.COLUMN_URL + "=?";
        String[] whereArgs = {bookmark.getTitle(), bookmark.getUrl()};
        db.delete(DBHelper.TABLE_NAME, whereClause, whereArgs);

        // Insert the new bookmark
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DATE, bookmark.getDate());
        values.put(DBHelper.COLUMN_TITLE, bookmark.getTitle());
        values.put(DBHelper.COLUMN_URL, bookmark.getUrl());

        db.insert(DBHelper.TABLE_NAME, null, values);
        db.close();
    }


    public void deleteBookmark(Bookmark bookmark) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DBHelper.COLUMN_ID + "=?";
        String[] whereArgs = {String.valueOf(bookmark.getId())};
        db.delete(DBHelper.TABLE_NAME, whereClause, whereArgs);
        db.close();
    }
    @SuppressLint("Range")
    public List<Bookmark> getAllBookmarks() {
        List<Bookmark> bookmarkList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DBHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                Bookmark bookmark = new Bookmark();
                bookmark.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
                bookmark.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE)));
                bookmark.setTitle(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE)));
                bookmark.setUrl(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_URL)));

                bookmarkList.add(bookmark);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return bookmarkList;
    }
}
