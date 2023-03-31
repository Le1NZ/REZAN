package com.example.rezan;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    final String BASE_URL = "https://api.openweathermap.org/";
    final String CITY = "Ryazan";
    final String API_KEY = "75833c35ecc2d8eacf172dd6953b5755";
    final String LANGUAGE = "ru";

    @GET("data/2.5/weather")
    Observable<Weather> getWeather(@Query("q") String city, @Query("appid") String appId, @Query("lang") String lang);

}
