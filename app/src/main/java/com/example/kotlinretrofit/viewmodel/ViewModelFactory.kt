package com.example.kotlinretrofit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinretrofit.connection.ApiHelper
import com.example.kotlinretrofit.repository.NewsRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(NewsRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}