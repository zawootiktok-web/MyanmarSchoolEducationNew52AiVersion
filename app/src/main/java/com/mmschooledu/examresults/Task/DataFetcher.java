package com.mmschooledu.examresults.Task;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataFetcher {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final DataFetchCallback callback;

    public DataFetcher(DataFetchCallback callback) {
        this.callback = callback;
    }

    public void fetchDataInBackground(String dataApiV1) {
        executorService.execute(() -> fetchData(dataApiV1));
    }

    private void fetchData(String dataApiV1) {
        try {
            URL urlObject = new URL(dataApiV1);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String body = stringBuilder.toString();

                try {
                    JSONArray jarr = new JSONArray(body);
                    String[] titles = new String[jarr.length()];
                    String[] baseUrl = new String[jarr.length()];
                    String[] date = new String[jarr.length()];
                    String[] autoRefresh = new String[jarr.length()];
                    String[] dataUrl = new String[jarr.length()];

                    for (int j = 0; j < jarr.length(); j++) {
                        titles[j] = jarr.getJSONObject(j).getString("title");
                        baseUrl[j] = jarr.getJSONObject(j).getString("base_url");
                        date[j] = jarr.getJSONObject(j).getString("date");
                        autoRefresh[j] = jarr.getJSONObject(j).getString("auto_refresh");
                        dataUrl[j] = jarr.getJSONObject(j).getString("data_url");
                    }

                    mainHandler.post(() -> callback.onDataFetched(titles, baseUrl, date, autoRefresh, dataUrl));

                } catch (JSONException e) {
                    mainHandler.post(() -> callback.onError(e));
                }

            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("Exception", "Error fetching data", e);
            mainHandler.post(() -> callback.onError(e));
        }
    }
}

