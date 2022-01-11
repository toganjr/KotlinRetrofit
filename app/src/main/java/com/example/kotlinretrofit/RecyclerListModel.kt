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
import com.example.kotlinretrofit.connection.UtilsApi
import com.example.kotlinretrofit.data.ArticlesItem
import com.example.kotlinretrofit.databinding.ActivityRecyclerListBinding
import com.example.kotlinretrofit.viewmodel.NewsViewModel
import com.example.kotlinretrofit.viewmodel.ViewModelFactory

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

        setupOnCreate()

        binding.srlNews.setOnRefreshListener {
            binding.srlNews.isRefreshing = false
            setupOnRefresh()
        }
    }

    private fun setupOnCreate(){
        setupViewModel("id","eaf0ed5151ec425098796b4b0e862245")
        setupUI(::onItemClicked)
        setupObservers()
    }

    private fun setupOnRefresh(){
        setupObservers()
    }

    private fun setupViewModel(id: String, key: String) {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(UtilsApi.apiService,id,key))
        ).get(NewsViewModel::class.java)
    }

    private fun setupUI(onItemClick: (ArticlesItem) -> Unit) {
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        adapter = ListNewsAdapter(arrayListOf(), onItemClick)
        binding.rvNews.addItemDecoration(
            DividerItemDecoration(
                binding.rvNews.context,
                (binding.rvNews.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvNews.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getData().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.rvNews.visibility = View.VISIBLE
                        binding.pbNews.visibility = View.GONE
                        resource.data?.let { users ->
                            retrieveList(users.articles)
                            //Log.d("Coroutine test", "Response : "+users.articles)
                        }
                    }
                    Status.ERROR -> {
                        binding.rvNews.visibility = View.VISIBLE
                        binding.pbNews.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        //Log.d("Coroutine error", "Error : "+it.message)
                    }
                    Status.LOADING -> {
                        binding.pbNews.visibility = View.VISIBLE
                        binding.rvNews.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(news: List<ArticlesItem>) {
        adapter.apply {
            addUsers(news)
            notifyDataSetChanged()
        }
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