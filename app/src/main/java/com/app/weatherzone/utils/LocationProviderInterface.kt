package com.app.weatherzone.utils

import com.app.weatherzone.RetrofitResModel.LocationData


interface LocationProviderInterface {
    fun getUserCurrentLocation(callback:RequestCompleteListener<LocationData>)
}