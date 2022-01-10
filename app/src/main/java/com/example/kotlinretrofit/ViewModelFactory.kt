package com.example.kotlinretrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinretrofit.connection.ApiHelper
import com.example.kotlinretrofit.databinding.ActivityRecyclerListBinding
import com.example.kotlinretrofit.repository.NewsRepository

class ViewModelFactory(private val apiHelper: ApiHelper,private val binding: ActivityRecyclerListBinding) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(NewsRepository(apiHelper,binding)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}