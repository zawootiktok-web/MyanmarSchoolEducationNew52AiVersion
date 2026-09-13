package com.mmschooledu;
//import android.content.Context;
//import android.util.Log;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class BlogspotAPIManager {
//
//    private static final String TAG = "BlogspotAPIManager";
//   // private static final String BLOGSPOT_JSON_URL = "https://your-blogspot-url.blogspot.com/your-json-post-url.html";
//    private static final String BLOGSPOT_JSON_URL = "https://myanmarschoolimage.blogspot.com/2024/03/blog-post_58.html";
//
//    private Context context;
//    private final RequestQueue requestQueue;
//
//    public BlogspotAPIManager(Context context) {
//        this.context = context;
//        this.requestQueue = Volley.newRequestQueue(context);
//    }
//
//    public void checkStartIOAdsStatus(final OnStartIOAdsStatusListener listener) {
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BLOGSPOT_JSON_URL, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            boolean isStartIOAdsEnabled = response.getBoolean("start_io_ads_enabled");
//                            listener.onStartIOAdsStatus(isStartIOAdsEnabled);
//                        } catch (JSONException e) {
//                            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
//                            listener.onStartIOAdsStatus(false); // Default to disabling ads
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e(TAG, "Error fetching JSON data: " + error.getMessage());
//                        listener.onStartIOAdsStatus(false); // Default to disabling ads
//                    }
//                });
//        requestQueue.add(jsonObjectRequest);
//    }
//
//    public interface OnStartIOAdsStatusListener {
//        void onStartIOAdsStatus(boolean isEnabled);
//    }
//}


//
//
//
//import android.content.Context;
//import android.util.Log;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class BlogspotAPIManager {
//
//    private static final String TAG = "BlogspotAPIManager";
//   // private static final String BLOGSPOT_JSON_URL = "https://your-blogspot-url.blogspot.com/your-json-post-url.html";
//   private static final String BLOGSPOT_JSON_URL = "https://myanmarschoolimage.blogspot.com/2024/03/blog-post_58.html";
//
//    private Context context;
//    private RequestQueue requestQueue;
//
//    public BlogspotAPIManager(Context context) {
//        this.context = context;
//        this.requestQueue = Volley.newRequestQueue(context);
//    }
//
//    public void checkAdsStatus(final OnAdsStatusListener listener) {
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BLOGSPOT_JSON_URL, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            boolean adsEnabled = response.getBoolean("ads_enabled");
//                            listener.onAdsStatus(adsEnabled);
//                            if (adsEnabled) {
//                                Log.d(TAG, "Ads Enabled");
//                            } else {
//                                Log.d(TAG, "Ads Disabled");
//                            }
//                        } catch (JSONException e) {
//                            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
//                            listener.onAdsStatus(false); // Default to ads disabled
//                            Log.d(TAG, "Ads Disabled");
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e(TAG, "Error fetching JSON data: " + error.getMessage());
//                        listener.onAdsStatus(false); // Default to ads disabled
//                        Log.d(TAG, "Ads Disabled");
//                    }
//                });
//        requestQueue.add(jsonObjectRequest);
//    }
//
//    public interface OnAdsStatusListener {
//        void onAdsStatus(boolean isEnabled);
//    }
//}

//
//import android.content.Context;
//import android.util.Log;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class BlogspotAPIManager {
//
//    private static final String TAG = "BlogspotAPIManager";
//    private static final String BLOGSPOT_JSON_URL ="https://myanmarschoolimage.blogspot.com/2024/03/adstest.html";
//
//    private Context context;
//    private RequestQueue requestQueue;
//
//    public BlogspotAPIManager(Context context) {
//        this.context = context;
//        this.requestQueue = Volley.newRequestQueue(context);
//    }
//
//    public void checkAdsStatus(final OnAdsStatusListener listener) {
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BLOGSPOT_JSON_URL, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            boolean adsEnabled = response.getBoolean("ads_enabled");
//                            String adsVisibility = response.getString("ads_visibility");
//                            if (!adsEnabled || "hide".equals(adsVisibility)) {
//                                // Ads are disabled or visibility is set to hide
//                                listener.onAdsStatus(false);
//                            } else {
//                                // Ads are enabled and visibility is set to show
//                                listener.onAdsStatus(true);
//                            }
//                        } catch (JSONException e) {
//                            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
//                            listener.onAdsStatus(false); // Default to ads disabled
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e(TAG, "Error fetching JSON data: " + error.getMessage());
//                        listener.onAdsStatus(false); // Default to ads disabled
//                    }
//                });
//        requestQueue.add(jsonObjectRequest);
//    }
//
//    public interface OnAdsStatusListener {
//        void onAdsStatus(boolean isEnabled);
//    }
//}


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

public class BlogspotAPIManager {

    private static final String TAG = "BlogspotAPIManager";
    private static final String BLOGSPOT_JSON_URL = "https://myanmarschoolimage.blogspot.com/feeds/posts/default?alt=json";

    private Context context;
    private RequestQueue requestQueue;

    public BlogspotAPIManager(Context context) {
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
