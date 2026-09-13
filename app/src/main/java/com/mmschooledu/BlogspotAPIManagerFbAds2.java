package com.mmschooledu;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BlogspotAPIManagerFbAds2 {

    private static final String TAG = "BlogspotAPIManagerStartAdsNew";
    private static final String BLOGSPOT_JSON_URL = "https://myanmarschooleducationfbads2.blogspot.com/feeds/posts/default?alt=json";

    private Context context;
    private RequestQueue requestQueue;

    public BlogspotAPIManagerFbAds2(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void checkAdsStatus(final OnAdsStatusListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BLOGSPOT_JSON_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject feed = response.getJSONObject("feed");
                            JSONObject entry = feed.getJSONArray("entry").getJSONObject(0); // Assuming only one entry
                            JSONObject jsonContent = entry.getJSONObject("content");
                            String content = jsonContent.getString("$t");
                            boolean showAds = parseAdsVisibility(content);
                            listener.onAdsStatus(showAds);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
                            listener.onAdsStatus(false); // Default to hiding ads in case of error
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching JSON data: " + error.getMessage());
                        listener.onAdsStatus(false); // Default to hiding ads in case of error
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private boolean parseAdsVisibility(String content) {
        // Parse the content to extract the visibility of ads
        // For example, you might search for a specific keyword or pattern in the content
        // Here, I'm assuming the content contains "show" or "hide" to determine ad visibility
        return content.contains("show");
    }

    public interface OnAdsStatusListener {
        void onAdsStatus(boolean isEnabled);
    }

    public interface OnStatusListener {
        void onStatus(String message, boolean show);
    }
}
