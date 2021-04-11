package com.example.blisstest.presentation.ui.google

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blisstest.databinding.GoogleReposFragmentBinding
import com.example.blisstest.presentation.ui.google.adapter.GoogleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoogleReposFragment : Fragment() {

    companion object {
        fun newInstance() = GoogleReposFragment()
    }

    private var _binding: GoogleReposFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter = GoogleAdapter()

    private val viewModel by viewModels<GoogleReposViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = GoogleReposFragmentBinding.inflate(inflater, container, false)

        binding.frgGooglereposRv.layoutManager = LinearLayoutManager(context)
        binding.frgGooglereposRv.adapter = adapter

        registerObservers()

        return binding.root
    }

    override fun onDestroyView() {
        unregisterObservers()

        _binding = null
        super.onDestroyView()
    }

    private fun registerObservers() {
        viewModel.searchPagedListLiveData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.paginationStatusLiveData.observe(viewLifecycleOwner,  {
            Log.d("GoogleReposFragment", "change status $it")
        })
    }

    private fun unregisterObservers() {
        viewModel.searchPagedListLiveData.removeObservers(viewLifecycleOwner)
        viewModel.paginationStatusLiveData.removeObservers(viewLifecycleOwner)
    }
}