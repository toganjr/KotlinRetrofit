package com.example.kotlinretrofit.connection

class ApiHelper(private val apiService: ApiService, private val id: String, private val key: String) {

    suspend fun getNews() = apiService.getListNews(id,key)
}