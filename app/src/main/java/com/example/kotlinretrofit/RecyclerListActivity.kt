package com.example.kotlinretrofit

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinretrofit.adapter.ListNewsAdapter
import com.example.kotlinretrofit.daggersetup.DaggerAppComponent
import com.example.kotlinretrofit.data.ArticlesItem
import com.example.kotlinretrofit.databinding.ActivityRecyclerListBinding
import com.example.kotlinretrofit.viewmodel.NewsViewModel
import com.example.kotlinretrofit.viewmodel.ViewModelFactory
import javax.inject.Inject

class RecyclerListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerListBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: ListNewsAdapter
    companion object var page = 2

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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

        binding.rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val position = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                if (position + 1 == adapter.itemCount) {
                    setupNextPage(page)
                } else {
                    //Toast.makeText(baseContext, "first", Toast.LENGTH_LONG).show();
                }
            }
        })
    }

    private fun setupOnCreate(){
        setupViewModel()
        setupUI(::onItemClicked,::onItemLongClicked)
        setupObservers()
    }

    private fun setupOnRefresh(){
        viewModel.setRefreshed()
        viewModel.setPageNumber(1)
        this.page = 2
    }

    private fun setupNextPage(page: Int){
        viewModel.setPageNumber(page)
        this.page++
    }

    private fun setupViewModel() {
        DaggerAppComponent.builder().build().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }

    private fun setupUI(onItemClick: (ArticlesItem) -> Unit, onItemLongClicked: (ArticlesItem) -> Boolean) {
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        adapter = ListNewsAdapter(arrayListOf(), onItemClick, onItemLongClicked)
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
                        binding.pbNews2.visibility = View.GONE
                        resource.data?.let { users ->
                            retrieveList(users.articles)
                            //Log.d("Coroutine test", "Response : "+users.articles)
                        }
                    }
                    Status.ERROR -> {
                        binding.rvNews.visibility = View.VISIBLE
                        binding.pbNews.visibility = View.GONE
                        binding.pbNews2.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        //Log.d("Coroutine error", "Error : "+it.message)
                    }
                    Status.LOADING -> {
                        binding.pbNews.visibility = View.VISIBLE
                        binding.rvNews.visibility = View.GONE
                        binding.pbNews2.visibility = View.GONE
                    }
                    Status.NEXTPAGE -> {
                        binding.rvNews.visibility = View.VISIBLE
                        binding.pbNews.visibility = View.GONE
                        binding.pbNews2.visibility = View.GONE
                        resource.data?.let { users ->
                            addList(users.articles)
                        }
                    }
                    Status.NEXTLOADING -> {
                        binding.rvNews.visibility = View.VISIBLE
                        binding.pbNews.visibility = View.GONE
                        binding.pbNews2.visibility = View.VISIBLE
                    }
                    Status.ENDPAGE -> {
                        binding.rvNews.visibility = View.VISIBLE
                        binding.pbNews.visibility = View.GONE
                        binding.pbNews2.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun retrieveList(news: List<ArticlesItem>) {
        adapter.apply {
            retrieveNews(news)
        }
    }

    private fun addList(news: List<ArticlesItem>) {
        adapter.apply {
            addNews(news)
        }
    }

    private fun onItemClicked(data: ArticlesItem) {

        val moveToDetail = Intent(this, DetailActivity::class.java).apply {
            putExtra("image", data.urlToImage)
            putExtra("description", data.description)
            putExtra("title", data.title)
        }
        startActivity(moveToDetail)
    }

    private fun onItemLongClicked(data: ArticlesItem): Boolean {
        val moveIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
        startActivity(moveIntent)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}