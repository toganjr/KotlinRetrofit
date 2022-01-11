package com.example.kotlinretrofit.connection

import com.example.kotlinretrofit.data.ArticlesItem
import com.example.kotlinretrofit.data.ResponseNews
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MyAPI
{
    @GET("/v2/top-headlines")
    suspend fun getListNews(@Query("country") country: String, @Query("apiKey") apiKey: String): Call<ResponseNews>

    companion object
    {
        private val BASE_URL = "https://newsapi.org/"
        fun create(): MyAPI
        {
            val gson = GsonBuilder()
                .create()

            val retrofit = Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create( gson ) )
                .baseUrl( BASE_URL )
                .build()

            return retrofit.create( MyAPI::class.java )
        }
    }
}