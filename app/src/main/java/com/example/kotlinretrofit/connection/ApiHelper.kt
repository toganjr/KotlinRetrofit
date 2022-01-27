package com.example.kotlinretrofit.connection

import javax.inject.Inject

class ApiHelper @Inject constructor (private val apiService: ApiService, private val id: String, private val key: String) {

    suspend fun getNews(pageSize:Int, page:Int?) = apiService.getListNews(id,key,pageSize,page)
}