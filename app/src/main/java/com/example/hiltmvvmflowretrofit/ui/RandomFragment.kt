package com.example.hiltmvvmflowretrofit.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiltmvvmflowretrofit.adapter.QuoteAdapter
import com.example.hiltmvvmflowretrofit.databinding.FragmentRandomBinding
import com.example.hiltmvvmflowretrofit.utils.ApiState
import com.example.hiltmvvmflowretrofit.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class RandomFragment : Fragment() {
    private var _binding: FragmentRandomBinding? = null
    private val binding get() = _binding!!
    private lateinit var quoteAdapter: QuoteAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRandomBinding.inflate(inflater, container, false)
        initRecyclerView()

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainViewModel.getQuotes()
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.quoteDataFlow.collect {
                when (it) {
                    is ApiState.Error -> {
                        binding.progressBarMain.visibility = View.GONE
                        binding.recyclerMain.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error ; ${it.msg}", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("ErrorMain", "Error ${it.msg}")
                    }

                    is ApiState.Success -> {
                        binding.progressBarMain.visibility = View.GONE
                        binding.recyclerMain.visibility = View.VISIBLE
                        quoteAdapter.saveData(listData = it.data)
                    }

                    is ApiState.Empty -> {

                    }

                    is ApiState.Loading -> {
                        Toast.makeText(
                            requireContext(),
                            "Getting Data From Server Please Wait",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }
            }
        }


        return binding.root
    }

    private fun initRecyclerView() {
        quoteAdapter = QuoteAdapter(ArrayList())
        binding.recyclerMain.apply {
            setHasFixedSize(true)
            adapter = quoteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}