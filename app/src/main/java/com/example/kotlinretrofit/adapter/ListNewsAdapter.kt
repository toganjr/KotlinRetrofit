package com.example.kotlinretrofit.adapter

import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinretrofit.data.ArticlesItem
import com.example.kotlinretrofit.data.ResponseNews
import com.example.kotlinretrofit.data.Source
import com.example.kotlinretrofit.databinding.CardviewNewsBinding
import com.example.kotlinretrofit.viewholder.AdvancedViewHolder
import com.google.gson.annotations.SerializedName

class ListNewsAdapter(private val listNews: ArrayList<ArticlesItem>, private val onItemClick: (ArticlesItem) -> Unit) : RecyclerView.Adapter<AdvancedViewHolder>() {

    private lateinit var binding: CardviewNewsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvancedViewHolder {
        binding = CardviewNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // Code for Higher Order Function with Listener called in onCreateViewHolder with Binding
        return AdvancedViewHolder(binding.root) {
            onItemClick(listNews[it])
        }


    }

    override fun onBindViewHolder(holder: AdvancedViewHolder, position: Int) {
        val (publishedAt, author, urlToImage, description, source, title, url, content) = listNews[position]

        Glide.with(binding.root)
            .load(urlToImage)
            .into(binding.newsImage)

        binding.newsAuthor.text = author
        binding.newsSource.text = source.name
        binding.newsTitle.text = title

    }

    // Code to fix bug position for recyclerview
    override fun getItemViewType(position: Int): Int {
        return position
    }

    // Code to fix bug position for recyclerview
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int = listNews.size

    fun addUsers(news: List<ArticlesItem>) {
        this.listNews.apply {
            clear()
            addAll(news)

        }

    }
}
