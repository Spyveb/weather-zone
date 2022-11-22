package com.app.weatherzone.Viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.weatherzone.RetrofitResModel.LocationData
import com.app.weatherzone.RetrofitResModel.ResponseWeather
import com.app.weatherzone.RetrofitResModel.ResponseWeatherForecast
import com.app.weatherzone.utils.LocationProvider
import com.app.weatherzone.utils.RequestCompleteListener
import com.app.weatherzone.utils.Resource




class MyViewModel:ViewModel() {

    private val tag = "ViewModel"

    //location live data
    val locationLiveData = MutableLiveData<LocationData>()
    val locationLiveDataFailure = MutableLiveData<String>()

    //weatherByLocation live data
    val weatherByLocation = MutableLiveData<Resource<ResponseWeather>>()


    //weatherByCityID live data
    val weatherByCityID = MutableLiveData<Resource<ResponseWeather>>()

    //weatherForecast live data
    val weatherForecast = MutableLiveData<Resource<ResponseWeatherForecast>>()

    fun getCurrentLocation(model: LocationProvider){
        model.getUserCurrentLocation(object : RequestCompleteListener<LocationData> {
            override fun onRequestCompleted(data: LocationData) {
                locationLiveData.postValue(data)
            }

            override fun onRequestFailed(errorMessage: String?) {
                locationLiveDataFailure.postValue(errorMessage)
            }
        })
    }



}