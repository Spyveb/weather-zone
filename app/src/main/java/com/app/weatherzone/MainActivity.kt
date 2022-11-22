package com.app.weatherzone

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.weatherzone.NetworkManager.Variables
import com.app.weatherzone.RetrofitResModel.LocationData
import com.app.weatherzone.Viewmodel.MyViewModel
import com.app.weatherzone.Viewmodel.WeatherViewModel
import com.app.weatherzone.utils.GpsUtils
import com.app.weatherzone.utils.LOCATION_REQUEST
import com.app.weatherzone.utils.LocationProvider
import com.app.weatherzone.utils.Utility.unixTimestampToTimeString
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }
    private lateinit var viewModel: MyViewModel
    private lateinit var model: LocationProvider

    private var isGPSEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = LocationProvider(this)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)


        GpsUtils(this).turnGPSOn(object : GpsUtils.OnGpsListener {

            override fun gpsStatus(isGPSEnable: Boolean) {
                this@MainActivity.isGPSEnabled = isGPSEnable
            }
        })

        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.locationLiveData.observe(this, {

            getData(it)

        })
    }
    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }
    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> Toast.makeText(MyApp.context,  "Enable GPS", Toast.LENGTH_LONG).show()


            isPermissionsGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() -> requestLocationPermission()

            else -> requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_REQUEST
        )
    }

    private fun startLocationUpdate() {
        viewModel.getCurrentLocation(model)
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }
    private fun getData(it: LocationData?) {

        if (!Variables.isNetworkConnected) {
            Toast.makeText(MyApp.context,  "Sorry, there was a problem, please check your internet connection.", Toast.LENGTH_LONG).show()
            return
        }


        lifecycleScope.launchWhenCreated {

            weatherViewModel.getWeatherByLocation(it?.latitude.toString(),it?.longitude.toString())
                .observeForever { data ->


                    tv_temp.text = data?.main?.temp.toString()
                    tv_city_name.text = data?.name
                    tv_weather_condition.text = data?.weather!![0].main
                    tv_sunrise_time.text = data.sys.sunrise.unixTimestampToTimeString()
                    tv_sunset_time.text = data.sys.sunset.unixTimestampToTimeString()
                    tv_real_feel_text.text = "${data.main.feelsLike}${getString(R.string.degree_celsius_symbol)}"
                    tv_cloudiness_text.text = "${data.clouds.all}%"
                    tv_wind_speed_text.text = "${data.wind.speed}m/s"
                    tv_humidity_text.text = "${data.main.humidity}%"
                    tv_pressure_text.text = "${data.main.pressure}hPa"
                    tv_visibility_text.text = "${data.visibility}M"

                }
        }

    }
}