package com.example.kotlinretrofit

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinretrofit.adapter.ListNewsAdapter
import com.example.kotlinretrofit.connection.ApiHelper
import com.example.kotlinretrofit.connection.ApiService
import com.example.kotlinretrofit.connection.UtilsApi
import com.example.kotlinretrofit.data.ArticlesItem
import com.example.kotlinretrofit.databinding.ActivityRecyclerListBinding
import java.lang.Exception

class RecyclerListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerListBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar
        supportActionBar?.elevation = 0f
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.image_white)))

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)

        binding.rvNews.layoutManager = LinearLayoutManager(this)

        setupViewModel("id","eaf0ed5151ec425098796b4b0e862245")
//        setupUI(::onItemClicked)
        setupObservers("id","eaf0ed5151ec425098796b4b0e862245",::onItemClicked)

        // val news = mApiService.getListNews("id","eaf0ed5151ec425098796b4b0e862245")

//        binding.rvNews.layoutManager = LinearLayoutManager(this)
//
//        viewModel.getData("id","eaf0ed5151ec425098796b4b0e862245").observe(this, Observer { a ->
//            a?.let { resource ->
//            when (resource.status) {
//                Status.LOADING -> {
//                    binding.pbNews.visibility = View.VISIBLE
//                    binding.rvNews.visibility = View.GONE
//
//
//                }
//                Status.SUCCESS -> {
//                    binding.pbNews.visibility = View.GONE
//                    binding.rvNews.visibility = View.VISIBLE
//                    resource.data?.let {
//                        try {
//                            Log.d("Author", "onCreate: test")
//                            val item = it
//                            Log.d("Author", "onCreate: ${item.toString()}o")
////                            val listNewsAdapter = ListNewsAdapter(item ,::onItemClicked)
////                            binding.rvNews.adapter = listNewsAdapter
////                            Log.d("Author2", "onCreate: "+ item[1].author)
//                        } catch (ex: Exception) {
//                            Log.d("AUTHOR", "onCreate: "+ex)
//                        }
//                    }
//                }
//            }
//            }
//        })
    }

    private fun setupViewModel(id: String, key: String) {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(UtilsApi.apiService,id,key),binding)
        ).get(NewsViewModel::class.java)
    }

//    private fun setupUI(onItemClick: (ArticlesItem) -> Unit) {
//        binding.rvNews.layoutManager = LinearLayoutManager(this)
//        adapter = ListNewsAdapter(arrayListOf(), onItemClick)
//        binding.rvNews.addItemDecoration(
//            DividerItemDecoration(
//                binding.rvNews.context,
//                (binding.rvNews.layoutManager as LinearLayoutManager).orientation
//            )
//        )
//        binding.rvNews.adapter = adapter
//    }

    private fun setupObservers(id: String, key: String, onItemClick: (ArticlesItem) -> Unit) {
        viewModel.getData(id,key,onItemClick).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.rvNews.visibility = View.VISIBLE
//                        resource.data?.let { users -> retrieveList(users.execute().body()!!.articles)
//                        }
                    }
                    Status.ERROR -> {
                        binding.rvNews.visibility = View.VISIBLE
                        binding.pbNews.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.pbNews.visibility = View.VISIBLE
                        binding.rvNews.visibility = View.GONE
                    }
                }
            }
        })
    }

//    private fun retrieveList(news: List<ArticlesItem>) {
//        adapter.apply {
//            addUsers(news)
//            notifyDataSetChanged()
//        }
//    }

    private fun onItemClicked(data: ArticlesItem) {
        val moveIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
        startActivity(moveIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



}