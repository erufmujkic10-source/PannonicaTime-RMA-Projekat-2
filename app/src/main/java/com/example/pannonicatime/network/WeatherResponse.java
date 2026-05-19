package com.example.pannonicatime.network;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("current")
    public CurrentWeather current;

    public static class CurrentWeather {
        @SerializedName("temperature_2m")
        public double temperature;

        @SerializedName("relative_humidity_2m")
        public int humidity;
    }
}