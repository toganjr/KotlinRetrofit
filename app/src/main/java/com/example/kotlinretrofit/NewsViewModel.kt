package com.example.kotlinretrofit

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinretrofit.adapter.ListNewsAdapter
import com.example.kotlinretrofit.data.ArticlesItem
import com.example.kotlinretrofit.databinding.ActivityRecyclerListBinding
import com.example.kotlinretrofit.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class NewsViewModel(private val newsRepo: NewsRepository) : ViewModel() {
    fun getData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
            try {
                val response = newsRepo.getData().awaitResponse()
                emit(Resource.success(data = response.body()!!.articles))
            } catch (ex: Exception) {
                emit(Resource.error(data = null, message = ex.message ?: "Error Occurred!"))
            }
        }
    }