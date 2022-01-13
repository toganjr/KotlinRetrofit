package com.example.kotlinretrofit.repository

import android.view.View
import android.widget.Toast
import com.example.kotlinretrofit.adapter.ListNewsAdapter
import com.example.kotlinretrofit.connection.ApiHelper
import com.example.kotlinretrofit.data.ArticlesItem
import com.example.kotlinretrofit.data.ResponseNews
import com.example.kotlinretrofit.databinding.ActivityRecyclerListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(private val apiHelper: ApiHelper) {
    suspend fun getData(pageSize: Int, page: Int) = apiHelper.getNews(pageSize, page)
}

