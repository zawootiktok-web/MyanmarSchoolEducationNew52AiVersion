package com.mmschooledu.MyanmarSpelling.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WordWizdb.db"; // ชื่อไฟล์ฐานข้อมูล
    private static final int DATABASE_VERSION = 1;
    // private static final String TABLE_NAME = "Vocab1";
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        copyDatabase(context); // คัดลอกฐานข้อมูลจาก assets
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // ฐานข้อมูลโหลดจาก assets ไม่ต้องสร้างใหม่
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // สำหรับการอัปเกรดฐานข้อมูลในอนาคต
    }

    public void copyDatabase(Context context) {
        String dbPath = context.getDatabasePath("WordWizdb.db").getPath();

        try {
            InputStream inputStream = context.getAssets().open("databases/WordWizdb.db");
            OutputStream outputStream = new FileOutputStream(dbPath);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            Log.d("DatabaseHelper", "Database copied successfully to " + dbPath);
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error copying database: ", e);
        }
    }


    public List<String[]> getRandomWords(String tableName, List<Integer> usedIds) {
        List<String[]> wordList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        try {
            String query;
            String[] queryArgs;

            if (usedIds.isEmpty()) {
                // ถ้าไม่มีคำที่เคยใช้ ให้ดึงข้อมูลทั้งหมด
                query = "SELECT id, EnVocab, THright, THwrong FROM " + tableName;
                queryArgs = null;
            } else {
                // ดึงข้อมูลที่ยังไม่เคยใช้
                String placeholders = makePlaceholders(usedIds.size());
                query = "SELECT id, EnVocab, THright, THwrong FROM " + tableName + " WHERE id NOT IN (" + placeholders + ")";
                queryArgs = usedIds.stream().map(String::valueOf).toArray(String[]::new);
            }

            cursor = db.rawQuery(query, queryArgs);

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String englishWord = cursor.getString(1);
                    String correctTranslation = cursor.getString(2);
                    String incorrectTranslation = cursor.getString(3);

                    wordList.add(new String[]{String.valueOf(id), englishWord, correctTranslation, incorrectTranslation});
                } while (cursor.moveToNext());
            } else {
                Log.d("DatabaseHelper", "No words available for selection in table " + tableName);
            }

        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error fetching words from table " + tableName + ": ", e);
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return wordList;
    }



    private String makePlaceholders(int count) {
        if (count == 0) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append("?,");
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }
}


