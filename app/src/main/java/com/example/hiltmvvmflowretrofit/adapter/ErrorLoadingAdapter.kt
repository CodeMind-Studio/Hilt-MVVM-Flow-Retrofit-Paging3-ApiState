package com.example.hiltmvvmflowretrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hiltmvvmflowretrofit.databinding.ErrorStateBinding

class ErrorLoadingAdapter constructor(private val retry: () -> Unit) :
    LoadStateAdapter<ErrorLoadingAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(ErrorStateBinding.inflate(LayoutInflater.from(parent.context),parent,false),retry)
    }

    class ViewHolder(private val binding: ErrorStateBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.apply {
                errorRetry.setOnClickListener {
                    retry()
                }
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                errorProgress.isVisible = loadState is LoadState.Loading
                errorText.isVisible = loadState !is LoadState.Loading
                errorRetry.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}