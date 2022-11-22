package com.app.weatherzone.NetworkManager

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import android.widget.Toast
import com.app.weatherzone.MyApp.Companion.context
import com.app.weatherzone.R


// Network Check
@SuppressLint("NewApi")
fun RegisterNetworkCallback() {
    try {
        val connectivityManager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Variables.isNetworkConnected = true // Global Static Variable
                Log.e("isNetworkConnected","true")
            }

            override fun onLost(network: Network) {
                Variables.isNetworkConnected = false // Global Static Variable
                Log.e("isNetworkConnected","false")
                Toast.makeText(context,  "Sorry, there was a problem, please check your internet connection.", Toast.LENGTH_LONG).show()


            }
        }
        )
        Variables.isNetworkConnected = false
    } catch (e: Exception) {
        Variables.isNetworkConnected = false

    }
}