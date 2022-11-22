package com.app.weatherzone

import android.app.Application
import android.content.Context
import com.app.weatherzone.NetworkManager.RegisterNetworkCallback


class MyApp:Application() {
    companion object{
         lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context =applicationContext

        RegisterNetworkCallback()

    }
}