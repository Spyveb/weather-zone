package com.app.weatherzone.Repositories

import androidx.lifecycle.MutableLiveData
import com.app.weatherzone.RetrofitResModel.ResponseWeather
import com.soumik.weatherzone.network.RetrofitClient

import org.json.JSONObject


class WeatherRepository {

    suspend fun getWeatherByLocation(lat: String,lon: String): MutableLiveData<ResponseWeather?> {

        val data = MutableLiveData<ResponseWeather?>()
        val response = RetrofitClient.api.getWeatherByLocation(lat, lon)


        if (response.code() == 404){

            val jObjError = JSONObject(response.errorBody()?.string() ?: "")

        }else{

            if (response.isSuccessful()) {
                // var json = Gson().toJson(response.body())

                data.setValue(response.body())

            } else {

                data.setValue(null)

            }
        }
        return data
    }


}