package com.mmschooledu.examresults.Task;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RegionApiService {
    @GET // Empty string as the endpoint
    Call<String> getData(); // Assuming the response is a JSON string
}

