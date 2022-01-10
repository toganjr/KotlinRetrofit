package com.example.kotlinretrofit.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinretrofit.data.ResponseNews


class AdvancedViewHolder(view: View, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view) {

    init {
        itemView.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(model: ResponseNews) {
    }
}