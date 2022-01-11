package com.example.kotlinretrofit.connection

import com.example.kotlinretrofit.data.ResponseNews
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/top-headlines")
    suspend fun getListNews(@Query("country") country: String, @Query("apiKey") apiKey: String): Call<ResponseNews>

    companion object {
    val BASE_URL = "https://newsapi.org/"
    var apiService: ApiService? = null
    fun getInstance() : ApiService {
        if (apiService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService!!
    }
    }
}