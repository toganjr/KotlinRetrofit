package com.example.kotlinretrofit.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.kotlinretrofit.Resource
import com.example.kotlinretrofit.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class NewsViewModel(private val newsRepo: NewsRepository) : ViewModel() {
    var isRefresh = MutableLiveData(false)
    private var job = Job()

    fun getData() = Transformations.switchMap(isRefresh) {
        job.cancel() // Cancel this job instance before returning liveData for new query
        job = Job()
        liveData(job + Dispatchers.IO) {
            Log.d("Status refresh", "getData: "+isRefresh.value)
            emit(Resource.loading(data = null))
            try {
                if (isRefresh.value == false) {
                    val response = newsRepo.getData(5,1)
                    emit(Resource.success(data = response.body()))
                } else {
                    val response = newsRepo.getData(5,1)
                    emit(Resource.success(data = response.body()))
                   // Log.d("Status Refresh", "getData: "+response.body()!!.articles)
                }

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