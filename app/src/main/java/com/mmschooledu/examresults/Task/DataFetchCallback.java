package com.mmschooledu.examresults.Task;

public interface DataFetchCallback {
    void onDataFetched(String[] titles, String[] baseUrl, String[] date, String[] autoRefresh, String[] dataUrl);
    void onError(Exception e);
}
