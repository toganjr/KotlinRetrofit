package com.example.kotlinretrofit.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.kotlinretrofit.DoubleTrigger
import com.example.kotlinretrofit.Resource
import com.example.kotlinretrofit.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class NewsViewModel @Inject constructor (private val newsRepo: NewsRepository) : ViewModel() {
    var isRefresh = MutableLiveData(false)
    var pageNumber = MutableLiveData(1)
    private var job = Job()

    fun getData() = Transformations.switchMap(DoubleTrigger(isRefresh, pageNumber)) {
        job.cancel() // Cancel this job instance before returning liveData for new query
        job = Job()
        liveData(job + Dispatchers.IO) {

            if (pageNumber.value == 1) {
                emit(Resource.loading(data = null))
            } else {
                emit(Resource.nextloading(data = null))
            }
            try {

                if(pageNumber.value != 1) {
                    val response = newsRepo.getData(6,pageNumber.value)
                    emit(Resource.next(data = response.body()))
                } else {
                    val response = newsRepo.getData(6,1)
                    emit(Resource.success(data = response.body()))
                }
            } catch (ex: Exception) {
                emit(Resource.error(data = null, message = ex.message ?: "Error Occurred!"))
            }
        }
    }

    fun setRefreshed(){
        this.isRefresh.value = this.isRefresh.value != true
    }

    fun setPageNumber(page: Int){
        this.pageNumber.value = page
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}