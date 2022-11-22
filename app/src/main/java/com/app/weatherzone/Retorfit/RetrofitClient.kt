package com.soumik.weatherzone.network

import com.app.weatherzone.ApiInterface
import com.app.weatherzone.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors


class RetrofitClient {

    companion object {

        private val retrofit by lazy {

            val httpClient = OkHttpClient.Builder().addInterceptor(QueryParameterAddInterceptor()).apply {
            }.build()

            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build()
        }

        val api by lazy {
            retrofit.create(ApiInterface::class.java)
        }
    }
}