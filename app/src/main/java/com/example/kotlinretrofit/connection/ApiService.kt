package com.example.kotlinretrofit.connection

import com.example.kotlinretrofit.data.ResponseNews
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/top-headlines")
    suspend fun getListNews(@Query("country") country: String,
                            @Query("apiKey") apiKey: String,
                            @Query("pageSize") pageSize: Int,
                            @Query("page") page: Int?): Response<ResponseNews>
}