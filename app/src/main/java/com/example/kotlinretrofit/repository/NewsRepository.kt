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

class NewsRepository(private val apiHelper: ApiHelper, private val binding: ActivityRecyclerListBinding) {
    suspend fun getData(id: String, key: String, onItemClicked: (ArticlesItem) -> Unit) = apiHelper.getNews().enqueue(object:
        Callback<ResponseNews> {
        override fun onResponse(call: Call<ResponseNews>, response: Response<ResponseNews>) {
            if (response.isSuccessful){
                binding.pbNews.visibility = View.GONE
                val listNewsAdapter = ListNewsAdapter(response.body()!!.articles ,onItemClicked)
                binding.rvNews.adapter = listNewsAdapter
            }
        }

        override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
            //Toast.makeText(this@NewsRepository, "${t.message}", Toast.LENGTH_LONG).show()
        }
    })
}

