package com.mmschooledu.examresults.Task;

public interface RegionDataFetchCallback {
    void onRegionDataFetched(String json);
    void onRegionError(Exception e);
}

