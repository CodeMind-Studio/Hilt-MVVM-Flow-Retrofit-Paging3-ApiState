package com.example.hiltmvvmflowretrofit.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hiltmvvmflowretrofit.data.QuoteData
import com.example.hiltmvvmflowretrofit.databinding.EachQuoteItemBinding
import javax.inject.Inject

class QuoteAdapter (private var list: List<QuoteData>) :
    RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    private lateinit var binding: EachQuoteItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = EachQuoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = list[position]

        binding.textQuoteContent.text = current.content
        binding.textQuoteAuthor.text = "--- ${current.author}"

    }

    @SuppressLint("NotifyDataSetChanged")
    fun saveData(listData: List<QuoteData>) {
        this.list = listData
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}