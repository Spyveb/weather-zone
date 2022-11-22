package com.app.weatherzone

import com.app.weatherzone.RetrofitResModel.ResponseWeather
import com.app.weatherzone.RetrofitResModel.ResponseWeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface ApiInterface {

    @GET("weather")
   suspend fun getWeatherByLocation(
        @Query("lat")
        latitude:String,
        @Query("lon")
        longitude:String
    ):Response<ResponseWeather>

    @GET("weather")
    suspend fun getWeatherByCityID(
        @Query("id")
        query:String
    ):Response<ResponseWeather>

    @GET("onecall")
    suspend fun getWeatherForecast(
        @Query("lat")
        latitude:String,
        @Query("lon")
        longitude:String,
        @Query("exclude")
        exclude:String
    ):Response<ResponseWeatherForecast>
}