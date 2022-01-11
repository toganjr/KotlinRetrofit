package com.example.kotlinretrofit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.kotlinretrofit.Resource
import com.example.kotlinretrofit.repository.NewsRepository
import kotlinx.coroutines.Dispatchers

class NewsViewModel(private val newsRepo: NewsRepository) : ViewModel() {
    fun getData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
            try {
                val response = newsRepo.getData()
                emit(Resource.success(data = response.body()))
            } catch (ex: Exception) {
                emit(Resource.error(data = null, message = ex.message ?: "Error Occurred!"))
            }
        }
    }