package com.example.hiltmvvmflowretrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiltmvvmflowretrofit.adapter.ErrorLoadingAdapter
import com.example.hiltmvvmflowretrofit.adapter.QuotePageAdapter
import com.example.hiltmvvmflowretrofit.databinding.FragmentPageBinding
import com.example.hiltmvvmflowretrofit.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PageFragment : Fragment() {
    private var _binding: FragmentPageBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapterQuote: QuotePageAdapter
    private lateinit var mainViewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPageBinding.inflate(inflater, container, false)

        setUpRecycler()
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.getQuotePageData().collectLatest {
                binding.apply {
                    progressBarMain.visibility = GONE
                    recyclerMain.visibility = VISIBLE
                }
                adapterQuote.submitData(it)
            }
        }

        return binding.root
    }

    private fun setUpRecycler() {
        binding.recyclerMain.apply {
            setHasFixedSize(true)
            adapter =
                adapterQuote.withLoadStateHeaderAndFooter(header = ErrorLoadingAdapter { adapterQuote.retry() },
                    footer = ErrorLoadingAdapter { adapterQuote.retry() })
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}