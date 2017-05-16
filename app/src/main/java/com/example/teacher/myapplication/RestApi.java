package com.example.teacher.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface RestApi {
    @GET("api/v1/predict.json/complete")
    Call<Model> predict(
            @Query("key") String key,
            @Query("q") String q,
            @Query("lang") String lang
    );
}
