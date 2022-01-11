package com.example.kotlinretrofit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinretrofit.data.ArticlesItem
import com.example.kotlinretrofit.repository.NewsRepository
import kotlinx.coroutines.*
import retrofit2.awaitResponse

class NewsViewModel (private val newsRepository: NewsRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val newsList = MutableLiveData<List<ArticlesItem>>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllNews() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = newsRepository.getData().awaitResponse()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    newsList.postValue(response.body()!!.articles)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared(){
        super.onCleared()
        job?.cancel()
    }

}