package com.example.kotlinretrofit.connection

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UtilsApi {
    val BASE_URL = "https://newsapi.org/"
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}