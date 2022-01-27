package com.example.kotlinretrofit.repository

import android.view.View
import android.widget.Toast
import com.example.kotlinretrofit.R
import com.example.kotlinretrofit.adapter.ListNewsAdapter
import com.example.kotlinretrofit.apikey.ApiKey
import com.example.kotlinretrofit.connection.ApiHelper
import com.example.kotlinretrofit.connection.RetrofitInstance
import com.example.kotlinretrofit.data.ArticlesItem
import com.example.kotlinretrofit.data.ResponseNews
import com.example.kotlinretrofit.databinding.ActivityRecyclerListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor (private val retrofitInstance : RetrofitInstance) {

    private val apiList = ApiKey()

    suspend fun getData(pageSize: Int, page: Int?) = retrofitInstance.provideGetApiEndPoint().getListNews(
        apiList.idCountry,apiList.key,pageSize,page)
}

