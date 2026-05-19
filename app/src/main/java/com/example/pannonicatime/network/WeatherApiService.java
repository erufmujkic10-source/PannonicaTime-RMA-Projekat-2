package com.example.pannonicatime.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("v1/forecast")
    Call<WeatherResponse> getCurrentWeather(
            @Query("latitude") double lat,
            @Query("longitude") double lon,
            @Query("current") String currentParams
    );
}