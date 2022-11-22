package com.app.weatherzone.Viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.weatherzone.Repositories.WeatherRepository
import com.app.weatherzone.RetrofitResModel.ResponseWeather


class WeatherViewModel() :  ViewModel() {

    private val weatherRepository: WeatherRepository = WeatherRepository()

    suspend fun getWeatherByLocation(lat: String,lon: String): MutableLiveData<ResponseWeather?> {
        return weatherRepository.getWeatherByLocation(lat, lon)
    }


}