package com.mmschooledu;

import android.os.AsyncTask;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import org.json.JSONArray;
import android.util.Log;
import org.json.JSONException;


public class TranslateAPI {

    String resp = null;
    String url = null;
    String langFrom = null;
    String langTo = null;
    String word = null;

    public TranslateAPI(String langFrom, String langTo, String text){
        this.langFrom=langFrom;
        this.langTo=langTo;
        this.word=text;

        
    }


        class Async extends AsyncTask<Void,Void,String>{

        @Override
                protected String doInBackground(Void... ignored) {
            HttpURLConnection con = null;
            try {

                url = "https://translate.googleapis.com/translate_a/single?"+"client=gtx&"+"sl="+
                        langFrom +"&tl=" + langTo +"&dt=t&q=" + URLEncoder.encode(word, "UTF-8");
                                URL obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();
                con.setConnectTimeout(8000);
                con.setReadTimeout(8000);
                con.setRequestProperty("User-Agent", "Mozilla/5.0");

                BufferedReader in = new BufferedReader(     new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null)
                {    response.append(inputLine);   }
                in.close();
                                resp = response.toString();
                return resp;
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                        } catch (Exception e) {
                Log.w("TranslateAPI", "Translation request failed", e);
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return null;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
                protected void onPostExecute(String s) {
            if (listener == null) {
                return;
            }
            String temp = "";

            if (s == null || s.isEmpty()){listener.onFailure("Network Error");}else {

                try {
                                        JSONArray main = new JSONArray(s);

                    JSONArray total = (JSONArray) main.get(0);
                    for (int i = 0; i < total.length(); i++) {
                        JSONArray currentLine = (JSONArray) total.get(i);
                        temp = temp + currentLine.get(0).toString();
                    }
                    Log.d("my-test", "onPostExecute: "+temp);

                    if(temp.length()>2)
                    {
                        listener.onSuccess(temp);
                    }else {listener.onFailure("Invalid Input String");}
                } catch (JSONException e) {
                    listener.onFailure(e.getLocalizedMessage());
                    e.printStackTrace();
                }}
            super.onPostExecute(s);
        }

        

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }
    }

    private TranslateListener listener;
    private Async async;

    public void setTranslateListener(TranslateListener listener)
    {
        this.listener=listener;
        if (listener != null && async == null) {
            async = new Async();
            async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public interface TranslateListener
    {
        public void onSuccess(String translatedText);

        public void onFailure(String ErrorText);
    }

}



