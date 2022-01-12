package com.example.kotlinretrofit.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.kotlinretrofit.Resource
import com.example.kotlinretrofit.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class NewsViewModel(private val newsRepo: NewsRepository, private val status: Boolean) : ViewModel() {
    var isRefresh = MutableLiveData(status)
    private var job = Job()

    fun getData() = Transformations.switchMap(isRefresh) {
        job.cancel() // Cancel this job instance before returning liveData for new query
        job = Job()
        liveData(job + Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                val response = newsRepo.getData()
                emit(Resource.success(data = response.body()))
            } catch (ex: Exception) {
                emit(Resource.error(data = null, message = ex.message ?: "Error Occurred!"))
            }
        }
    }

    fun setIsRefresh(){
        this.isRefresh.value = true
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}