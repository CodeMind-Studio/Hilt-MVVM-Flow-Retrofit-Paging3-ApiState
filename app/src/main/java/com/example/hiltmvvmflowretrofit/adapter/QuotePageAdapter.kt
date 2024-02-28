package com.example.hiltmvvmflowretrofit.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.hiltmvvmflowretrofit.data.QuoteData
import com.example.hiltmvvmflowretrofit.databinding.EachQuoteItemBinding
import javax.inject.Inject

class QuotePageAdapter @Inject constructor() :
    PagingDataAdapter<QuoteData, QuotePageAdapter.ViewHolder>(diffCallback = Diff) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)

        if (current != null) {
            holder.bind(current)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            EachQuoteItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class ViewHolder(private val binding: EachQuoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(list: QuoteData) {
            binding.apply {
                textQuoteContent.text = list.content
                textQuoteAuthor.text = "--- ${list.author}"
            }
        }
    }

    object Diff : DiffUtil.ItemCallback<QuoteData>() {
        override fun areItemsTheSame(oldItem: QuoteData, newItem: QuoteData): Boolean =
            oldItem.content == newItem.content


        override fun areContentsTheSame(oldItem: QuoteData, newItem: QuoteData): Boolean =
            oldItem == newItem

    }
}