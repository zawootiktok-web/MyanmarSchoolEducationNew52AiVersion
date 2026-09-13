package com.mmschooledu.examresults.scrap;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BlogspotScraper {

    private static final String TAG = BlogspotScraper.class.getSimpleName();

    // Callback interface
    public interface OnScrapingCompleteListener {
        void onScrapingComplete(String result);
    }


    private ExecutorService mExecutorService;
    private OnScrapingCompleteListener mListener;
    private Context mContext;

    public BlogspotScraper(Context context, OnScrapingCompleteListener listener) {
        mExecutorService = Executors.newSingleThreadExecutor();
        mListener = listener;
        mContext = context;
    }

    public void scrape(final String url,final Boolean postlist) {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Document doc = Jsoup.connect(url).get();

                    if (postlist){
                       String jsonString = "";
                       Elements postElements = doc.select("div.post");

                       String next_url="";
                       try{
                           next_url = doc.select("div#blog-pager").select("a").attr("href");
                       }catch (Exception e){

                       }
                        // Iterate over the post elements
                        for (Element postElement : postElements) {
                            // Extract the post data
                            int index=0;
                            String title = postElement.select("h3.post-title").text();
                            String date = postElement.select("time.published").attr("title");
                            String creator = postElement.select("a.g-profile").text();
                            Elements elements = postElement.select("div.post-header");
                            elements.remove();

                            String content = postElement.select("div").get(index).text();
                            if(!content.contains("thumnail")){
                                index++;
                                content = postElement.select("div").get(index).text();
                            }
                            String thumnail=content.split("\"theme_url\"")[0].replace("{","");

                            String url =postElement.select("div.post-bottom")
                                    .select("a.comment-link")
                                    .attr("href")
                                    .replace("#comments","");
                            String commentCount =postElement.select("div.post-bottom").select("span.num_comments").text();

                            jsonString+="{\"thumnail\":\""+thumnail+"\","+
                                    "\"title\":\""+title+"\","+
                                    "\"date\":\""+date+"\","+
                                    "\"cretor\":\""+creator+"\","+
                                    "\"url\":\""+url+"\"},";
                        }

                        saveToPrefs("next_url",next_url);
                        return jsonString;
                    }else {
                        // Extract body
                        Element bodyElement = doc.select("div.post-body").first();
                        String body = bodyElement.html();

                        // Return extracted information
                        return  body;
                    }

                } catch (IOException e) {
                    Log.e(TAG, "Error scraping blog post: " + e.getMessage());
                    return null;
                }
            }
        };

        Future<String> future = mExecutorService.submit(callable);

        try {
            String result = future.get();
            if (mListener != null) {
                mListener.onScrapingComplete(result);
            }
        } catch (Exception e) {

            Log.e(TAG, "Error scraping blog post: " + e.getMessage());
        }
    }

    public void cleanup() {
        mExecutorService.shutdown();
    }
    public void saveToPrefs(String key, String result) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, result);
        editor.commit();
    }

}
