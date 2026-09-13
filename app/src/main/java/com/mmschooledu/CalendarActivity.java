package com.mmschooledu;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    // Time to show the ad before auto-closing (e.g., 10 seconds)
    private static final int TIMER_DELAY = 10000;

    // Blogger Feed URL
    private static final String BLOGGER_URL = "https://myanmarschoolpassward.blogspot.com/feeds/posts/default?alt=json";

    private TextView tvMessage;
    private ImageView ivStatus;
    private String targetLink = ""; // Link to open when image is clicked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);

        // 1. Initialize UI
        tvMessage = findViewById(R.id.tvMessage);
        ivStatus = findViewById(R.id.ivStatus);
        ImageButton closeButton = findViewById(R.id.btnClose);

        // 2. Close Button Logic
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 3. Auto Close Logic
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    finish();
                }
            }
        }, TIMER_DELAY);

        // 4. Click Image Logic
        ivStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (targetLink != null && !targetLink.isEmpty()) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(targetLink));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(CalendarActivity.this, "Cannot open link", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 5. Start Data Loading
        refresh();
    }

    private void refresh() {
        tvMessage.setText("Checking Date...");
        new DownloadTask().execute(BLOGGER_URL);
    }

    // *** CRITICAL FIX: Date Formatting ***
    private String getTodayDate() {
        // Locale.US ensures the numbers are always 0-9 (English)
        // This prevents errors on phones using Myanmar/Zawgyi numbering system
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.format(new Date());
    }

    // Background Task to download JSON string
    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return JSONDownloader.download(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                processJSON(result);
            } else {
                tvMessage.setText("Internet Connection Error");
            }
        }
    }

    private void processJSON(String input) {
        String todayStr = getTodayDate();
        boolean matchFound = false;

        // Debugging: Show toast of what date the app thinks it is
        // Toast.makeText(this, "System Date: " + todayStr, Toast.LENGTH_LONG).show();

        try {
            JSONObject jo = new JSONObject(input);
            JSONArray ja = jo.getJSONObject("feed").getJSONArray("entry");

            for (int i = 0; i < ja.length(); i++) {
                JSONObject post = ja.getJSONObject(i);

                // Check Post Title strictly
                String title = post.getJSONObject("title").getString("$t");
                if (title.equals("School")) {

                    String content = post.getJSONObject("content").getString("$t");

                    // Parse the internal JSON inside the HTML content
                    JSONObject contentJson = new JSONObject(Html.fromHtml(content).toString());

                    if (contentJson.has("calendar")) {
                        JSONArray calendarArr = contentJson.getJSONArray("calendar");

                        for (int j = 0; j < calendarArr.length(); j++) {
                            JSONObject dayObj = calendarArr.getJSONObject(j);

                            // Get date from JSON and trim spaces
                            String jsonDate = dayObj.getString("date").trim();

                            // Compare Dates
                            if (jsonDate.equals(todayStr)) {

                                // --- Match Found! ---
                                String message = dayObj.getString("message");
                                String imageUrl = dayObj.optString("image", "");
                                targetLink = dayObj.optString("link", "");

                                // Update UI
                                tvMessage.setText(message);

                                if (!imageUrl.isEmpty()) {
                                    Glide.with(getApplicationContext())
                                            .load(imageUrl)
                                            .into(ivStatus);
                                }

                                matchFound = true;
                                break; // Stop looping days
                            }
                        }
                    }
                }
                if (matchFound) break; // Stop looping posts
            }

            if (!matchFound) {
                // If no date matched in the JSON
                tvMessage.setText("No announcements for today (" + todayStr + ")");
                ivStatus.setVisibility(View.GONE); // Hide image if no data
            }

        } catch (JSONException e) {
            e.printStackTrace();
            // Show exact error for debugging
            tvMessage.setText("Data Error: Please check Blogger JSON format.");
            Log.e("AdsActivity", "JSON Parsing Error: " + e.getMessage());
        }
    }
}