package com.mmschooledu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import java
//import java.io.*;(.)io.*;

public class JSONDownloader
{
    private static final int CONNECT_TIMEOUT_MS = 15000;
    private static final int READ_TIMEOUT_MS = 20000;

    public static String download(String url){
        if (url == null || url.trim().isEmpty()) {
            return "";
        }

        HttpURLConnection httpConn = null;
        try
        {
            httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setConnectTimeout(CONNECT_TIMEOUT_MS);
            httpConn.setReadTimeout(READ_TIMEOUT_MS);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Accept", "application/json, text/plain, */*");
            httpConn.setRequestProperty("User-Agent", "MyanmarSchoolEduApp/1.0");
            httpConn.setInstanceFollowRedirects(true);

            int responseCode = httpConn.getResponseCode();
            if (responseCode < HttpURLConnection.HTTP_OK
                    || responseCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
                return "";
            }

            StringBuilder result = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(httpConn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            }
            return result.toString();
        }
        catch (IOException e)
        {
            return "";
        }
        finally
        {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
    }
}
