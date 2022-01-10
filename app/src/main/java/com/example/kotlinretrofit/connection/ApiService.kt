package com.example.kotlinretrofit.connection

import com.example.kotlinretrofit.data.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/top-headlines")
    fun getListNews(@Query("country") country: String, @Query("apiKey") apiKey: String): Call<ResponseNews>
}