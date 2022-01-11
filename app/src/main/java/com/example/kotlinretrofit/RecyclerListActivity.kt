package com.example.kotlinretrofit

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinretrofit.adapter.ListNewsAdapter
import com.example.kotlinretrofit.connection.ApiHelper
import com.example.kotlinretrofit.connection.ApiService
import com.example.kotlinretrofit.data.ArticlesItem
import com.example.kotlinretrofit.databinding.ActivityRecyclerListBinding
import com.example.kotlinretrofit.repository.NewsRepository

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

        val apiService = ApiService
        val apiHelper = ApiHelper(apiService.getInstance(),"bitcoin", "eaf0ed5151ec425098796b4b0e862245")
        val mainRepository = NewsRepository(apiHelper)

        viewModel = ViewModelProvider(this, ViewModelFactory(mainRepository)).get(NewsViewModel::class.java)

        viewModel.newsList.observe(this, {
            val listNewsAdapter = ListNewsAdapter(it ,::onItemClicked)
            binding.rvNews.adapter = listNewsAdapter
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer {
            if (it) {
                binding.pbNews.visibility = View.VISIBLE
            } else {
                binding.pbNews.visibility = View.GONE
            }
        })
        viewModel.getAllNews()
    }

    private fun onItemClicked(data: ArticlesItem) {
        val moveIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
        startActivity(moveIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



}