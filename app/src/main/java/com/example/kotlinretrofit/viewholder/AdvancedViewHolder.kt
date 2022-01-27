package com.example.kotlinretrofit.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinretrofit.data.ArticlesItem
import com.example.kotlinretrofit.data.ResponseNews


class AdvancedViewHolder(view: View, onItemClicked: (ArticlesItem) -> Unit, onItemLongClicked: (ArticlesItem) -> Boolean, retrieveUser: (Int) -> ArticlesItem) : RecyclerView.ViewHolder(view) {

    init {
        itemView.setOnClickListener {
            onItemClicked(retrieveUser(adapterPosition))
        }

        itemView.setOnLongClickListener {
            onItemLongClicked(retrieveUser(adapterPosition))
        }
    }

    fun bind(model: ResponseNews) {
    }
}