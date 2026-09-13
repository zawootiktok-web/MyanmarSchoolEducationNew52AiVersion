package com.mmschooledu.examresults.Task;



import com.mmschooledu.examresults.model.MainData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MainApiService {
    @GET("data-api-v1")
    Call<List<MainData>> getData();
}

