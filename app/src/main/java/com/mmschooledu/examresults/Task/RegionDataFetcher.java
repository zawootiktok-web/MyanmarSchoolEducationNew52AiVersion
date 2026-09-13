package com.mmschooledu.examresults.Task;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegionDataFetcher {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final RegionDataFetchCallback callback;

    public RegionDataFetcher(RegionDataFetchCallback callback) {
        this.callback = callback;
    }

    public void fetchDataInBackground(String dataUrl) {
        executorService.execute(() -> fetchData(dataUrl));
    }

    private void fetchData(String dataUrl) {
        try {
            URL urlObject = new URL(dataUrl);
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
                parseAndHandleJson(body);

            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("Exception", "Error fetching data", e);
            mainHandler.post(() -> callback.onRegionError(e));
        }
    }

    private void parseAndHandleJson(String body) {
        try {


            mainHandler.post(() -> callback.onRegionDataFetched(body));
        } catch (Exception e) {
            mainHandler.post(() -> callback.onRegionError(e));
        }
    }
}
